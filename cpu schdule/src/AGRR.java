import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class AGRR {
    ArrayList<Process> Processes = new ArrayList<>();
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

//    public void CalcAG() {
//        for (Process p : Processes) {
//            Random random = new Random();
//            int rand = random.nextInt(21);
//
//            if (rand < 10) {
//                p.setAGFactor(rand + p.get_arrival_time() + p.getBurst_time());
//            } else if (rand > 10) {
//                p.setAGFactor(10 + p.get_arrival_time() + p.getBurst_time());
//            } else {
//                p.setAGFactor(p.getPriority() + p.get_arrival_time() + p.getBurst_time());
//            }
//        }
//    }

    public double calculateMeanQuantumTime() {
        double sum = 0;
        int count = 0;

        for (Process p : Processes) {
            if (p.get_arrival_time() <= currTime && p.getBurst_time() > 0) {
                sum += p.getQuantum();
                count++;
            }
        }

        return count > 0 ? sum / count : 0;
    }

    public void execute() {
       // CalcAG();
        ArrivalProcess.sort(Comparator.comparingInt(Process::get_arrival_time));

        while (!Processes.isEmpty() || !ArrivalProcess.isEmpty() || !readyQueue.isEmpty()) {
            // Simulate arriving processes
            Iterator<Process> iterator = ArrivalProcess.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p.get_arrival_time() <= currTime) {
                    Processes.add(p);
                    iterator.remove();
                }
            }
            // sort based on AG factor then arrival time to handle equal AG factors
            Comparator<Process> comparator = Comparator.comparing(Process::getAGFactor).thenComparingInt(Process::get_arrival_time);
            Processes.sort(comparator);

            if (!Processes.isEmpty()) {
                Process currProcess = Processes.getFirst();

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
                            Processes.add(p);
                            iterator.remove();
                        }
                    }
                    //sort the queue again
                    Processes.sort(comparator);

                    // Get the first process in execution
                    Process newProcess = Processes.getFirst();

                    // Check if new processes have appeared
                    if (newProcess.getAGFactor() < currProcess.getAGFactor()) {
                        // Update the quantum time by increasing the original quantum time before execution by the remaining quantum time
                        int remainingQuantum = currProcess.getRemainingQuantum();
                        currProcess.setQuantum(currProcess.getQuantum() + remainingQuantum);

                        readyQueue.add(currProcess);

                        currProcess = Processes.remove(0);

                        int nonPre = (int) Math.ceil((double) currProcess.getQuantum() / 2);
                        currProcess.setBurst_time(currProcess.getBurst_time() - nonPre);
                        currProcess.setRemainingQuantum(currProcess.getQuantum() - nonPre);

                        currTime += nonPre;

                        break;
                    } else {
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

                        // Move to work on the new process
                        currProcess = Processes.remove(0);
                    }

                    // Check if process finished its execution (burst time is DONE)
                    if (currProcess.getBurst_time() <= 0) {
                        // Update old process's quantum time to 0
                        currProcess.setQuantum(0);

                        // Add old process to the die list
                        dieList.add(currProcess);
                        currProcess.setEnd(currTime);

                        // Move to work on the new process
                        currProcess = Processes.remove(0);
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
