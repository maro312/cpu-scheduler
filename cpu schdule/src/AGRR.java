import java.util.*;

public class AGRR {
    ArrayList<Process> current = new ArrayList<>();
    ArrayList<Process> readyQueue = new ArrayList<>();
    ArrayList<Process> ArrivalProcess = new ArrayList<>();
    ArrayList<Process> dieList = new ArrayList<>();
    double averageWaiting,averageTurnAround;

    public double getAverageWaiting() {
        return averageWaiting;
    }


    public double getAverageTurnAround() {
        return averageTurnAround;
    }

    int currTime = 0;

    public void CalcAG() {
        for (Process p : current) {
            Random random = new Random();
            int rand = random.nextInt(21);

            if (rand < 10) {
                p.setAGFactor(rand + p.get_arrival_time() + p.getBurst_time());
            } else if (rand > 10) {
                p.setAGFactor(10 + p.get_arrival_time() + p.getBurst_time());
            } else {
                p.setAGFactor(p.getPriority() + p.get_arrival_time() + p.getBurst_time());
            }
        }
    }

    public double calculateMeanQuantumTime() {
        double sum = 0;
        int count = 0;

        for (Process p : current) {
            if (p.get_arrival_time() <= currTime && p.getBurst_time() > 0) {
                sum += p.getQuantum();
                count++;
            }
        }

        return count > 0 ? sum / count : 0;
    }

    public void execute() {
       CalcAG();
        ArrivalProcess.sort(Comparator.comparingInt(Process::get_arrival_time));

        while (!current.isEmpty() || !ArrivalProcess.isEmpty() || !readyQueue.isEmpty()) {
            // Simulate arriving processes
            Iterator<Process> iterator = ArrivalProcess.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p.get_arrival_time() <= currTime) {
                    current.add(p);
                    iterator.remove();
                }
            }
            // sort based on AG factor then arrival time to handle equal AG factors
            Comparator<Process> comparator = Comparator.comparing(Process::getAGFactor).thenComparingInt(Process::get_arrival_time);
            current.sort(comparator);

            if (!current.isEmpty()) {
                Process currProcess = current.getFirst();

                // Non-preemptive execution for half the quantum time

                currProcess.setBurst_time(currProcess.getBurst_time() - (int) Math.ceil((double) currProcess.getQuantum() / 2));
                currProcess.setRemainingQuantum(currProcess.getQuantum() - (int) Math.ceil((double) currProcess.getQuantum() / 2));
                currTime += (int) Math.ceil((double) currProcess.getQuantum() / 2);

                // Start preemptive execution
                while (currProcess.getBurst_time() > 0) {
                    // Check for new processes
                    iterator = ArrivalProcess.iterator();
                    // Simulate arriving processes
                    while (iterator.hasNext()) {
                        Process p = iterator.next();
                        if (p.get_arrival_time() <= currTime) {
                            current.add(p);
                            iterator.remove();
                        }
                    }

                    //sort the queue again
                    current.sort(comparator);
                    Process newProcess;

                    if(current.isEmpty()|| current.getFirst().getAGFactor() > currProcess.getAGFactor()){
                        //if no new Processes have arrived or the process that arrived has a bigger AG factor
                        //get the first in ready queue
                        newProcess = readyQueue.getFirst();
                    }
                    else {
                        // Get the first process in execution
                        newProcess = current.getFirst();
                    }

                    // Check if new processes have appeared
                    if (newProcess.getAGFactor() < currProcess.getAGFactor()) {
                        // Update the quantum time by increasing the original quantum time before execution by the remaining quantum time
                        int remainingQuantum = currProcess.getRemainingQuantum();
                        currProcess.setQuantum(currProcess.getQuantum() + remainingQuantum);

                        readyQueue.add(currProcess);

                        if(current.isEmpty()){
                            currProcess = readyQueue.removeFirst();
                        }
                        else{
                            currProcess = current.removeFirst();
                        }

                        int nonPre = (int) Math.ceil((double) currProcess.getQuantum() / 2);
                        currProcess.setBurst_time(currProcess.getBurst_time() - nonPre);
                        currProcess.setRemainingQuantum(currProcess.getQuantum() - nonPre);

                        currTime += nonPre;

                        break;
                    }
                    else {
                        // execute for one second
                        currProcess.setRemainingQuantum(currProcess.getRemainingQuantum() - 1);
                        currProcess.setBurst_time(currProcess.getBurst_time() - 1);
                        readyQueue.add(newProcess);
                        currTime++;

                    }

                    // Check if process finished its quantum time
                    if (currProcess.getRemainingQuantum() <= 0) {
                        currProcess.setQuantum((int) Math.ceil(0.1 * calculateMeanQuantumTime()));

                        // Add old process to ready queue
                        readyQueue.add(currProcess);

                        if(current.isEmpty() || current.getFirst().getAGFactor() > currProcess.getAGFactor()){
                            currProcess = readyQueue.removeFirst();
                        }

                        else {
                            // Move to work on the new process
                            currProcess = current.removeFirst();
                        }

                    }

                    // Check if process finished its execution (burst time is DONE)
                    if (currProcess.getBurst_time() <= 0) {
                        // Update old process's quantum time to 0
                        currProcess.setQuantum(0);

                        // Add old process to the die list
                        dieList.add(currProcess);
                        currProcess.setEnd(currTime);

                        // Move to work on the new process
                        if(current.isEmpty() || current.getFirst().getAGFactor() > currProcess.getAGFactor()){
                            currProcess = readyQueue.removeFirst();
                        }

                        else {
                            // Move to work on the new process
                            currProcess = current.removeFirst();
                        }

                    }
                }
            } else {
                currTime++; // No process is currently running, so just increment the time
                System.out.println("idle cpu");
            }
        }

        for (Process p: dieList) {
            System.out.println(p.getName() +"End Time"+ p.getEnd());
            averageWaiting += p.getWaiting_time();
            averageTurnAround+=p.getTurnaround_time();
        }
        averageWaiting /= dieList.size();
        averageTurnAround /= dieList.size();

    }



}
