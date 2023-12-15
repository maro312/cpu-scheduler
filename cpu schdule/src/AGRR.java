import java.util.*;

public class AGRR {
    ArrayList<Process> current = new ArrayList<>();
    ArrayList<Process> readyQueue = new ArrayList<>();
    ArrayList<Process> ArrivalProcess = new ArrayList<>();
    ArrayList<Process> dieList = new ArrayList<>();
    ArrayList<Process> allProcesses = new ArrayList<>() ;
    double averageWaiting,averageTurnAround;

    public double getAverageWaiting() {
        return averageWaiting;
    }

    int currTime = -1;
    public double getAverageTurnAround() {
        return averageTurnAround;
    }

    public void CalcAG() {
        //AG calculations
        for (Process p : current) {
            Random random = new Random();
            int rand = random.nextInt(21); // random function

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
        //sort all Processes bases on Arrival ti.e
        ArrivalProcess.sort(Comparator.comparingInt(Process::get_arrival_time));
        Process currProcess = null;


        while (!current.isEmpty() || !ArrivalProcess.isEmpty() || !readyQueue.isEmpty()) {
            currTime++;
            //execute process
            if(currProcess != null) {
                currProcess.setBurst_time(currProcess.getBurst_time() - 1);
                currProcess.setRemainingQuantum(currProcess.getRemainingQuantum() - 1);
            }

            // Simulate arriving processes
            Iterator<Process> iterator = ArrivalProcess.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p.get_arrival_time() <= currTime) {
                    current.add(p);   // current is the currently available
                    readyQueue.add(p) ; //Working
                    iterator.remove();
                }
            }

            if (!current.isEmpty() || !readyQueue.isEmpty()) {

                    //get the first in ready queue
                currProcess = readyQueue.getFirst();

                    //if that Process has already run Non-Preemptive
                if((currProcess.getQuantum() - currProcess.getRemainingQuantum()) >= (int) Math.ceil( currProcess.getQuantum() / 2.0)){
                    //hold the process
                    Process P = currProcess;
                    Comparator<Process> comparator = Comparator.comparing(Process::getAGFactor).thenComparingInt(Process::get_arrival_time);
                    current.sort(comparator);
                    //sort the currently available Processes based on AG factor then arrival time

                    if(!current.isEmpty()) {
                        if (!currProcess.getName().equals(current.get(0).getName())) {
                            //if the currently running Process does not have the lease AG in the Currently available Processes
                            //remove it from ready queue
                            for (int i = 0 ; i < readyQueue.size() ; i ++){
                                if (readyQueue.get(i).getName().equals(current.getFirst().getName())) {
                                    readyQueue.remove(i) ;
                                    i -- ;
                                }
                            }
                            //put it at the end of the ready queue
                            readyQueue.add(readyQueue.getFirst()) ;
                            readyQueue.remove(0) ;
                            //modify its quantum time by adding the remaining quantum time
                            P.setQuantum(P.getQuantum() + P.getRemainingQuantum());
                            //add the smaller Process in ready queue
                            readyQueue.add(0 , current.getFirst()) ;
                            //set the quantum time it will work on
                            readyQueue.getFirst().setRemainingQuantum(readyQueue.getFirst().getQuantum());
                            //make it the current Process
                            currProcess = readyQueue.getFirst() ;
                        }
                    }
                }
                //if the Process has finished all its Quantum time
                if (currProcess.getRemainingQuantum() <= 0){
                    // add the 10% percent of the mean of available Processes to its Quantum Time
                    double mean = Math.ceil(0.1 * calculateMeanQuantumTime());
                    currProcess.setQuantum(currProcess.getQuantum() + (int)mean);
                    //add the
                    readyQueue.add(readyQueue.getFirst()) ;
                    readyQueue.remove(0) ;
                    readyQueue.getFirst().setRemainingQuantum(readyQueue.getFirst().getQuantum());
                    currProcess = readyQueue.getFirst() ;
                }
                if (currProcess.getBurst_time() <= 0) {
                    //if process has finished all its Burst time
                    //add to die list
                    dieList.add(currProcess) ;
                    //calc end Time , waiting time , and arrival time
                    currProcess.setEnd(currTime);
                    currProcess.setTurnaround_time(currTime - currProcess.get_arrival_time());
                    currProcess.setWaiting_time(currProcess.getTurnaround_time() - currProcess.getRemainingBurst());
                    //make the quantum time == 0
                    currProcess.setQuantum(0);

                    //remove it from current
                    for (int i = 0 ; i < current.size() ; i ++){
                        if (current.get(i).getName().equals(currProcess.getName())){
                            current.remove(i) ;
                            i -- ;
                        }
                    }
                    //remove it from ready queue
                    for (int i = 0 ; i < readyQueue.size() ; i ++){
                        if (readyQueue.get(i).getName().equals(currProcess.getName())){
                            readyQueue.remove(i) ;
                            i -- ;
                        }
                    }
                    //get the first in ready Queue and work on it
                    if (!readyQueue.isEmpty()){
                        currProcess = readyQueue.getFirst() ;
                        currProcess.setRemainingQuantum(currProcess.getQuantum());
                    }

                }
                // print the quantum time of each process at the moment
                System.out.println(currTime + currProcess.getName());
                for (Process p : allProcesses){
                    System.out.print(p.getQuantum() + " ");
                }
                System.out.println();
            } else {
                currTime++; // No process is currently running, so just increment the time
                System.out.println("idle cpu");
            }
        }

        for (Process p: dieList) {
            System.out.println(p.getName() +" waiting Time "+ p.getWaiting_time());
            System.out.println(p.getName() +" Turn Around "+ p.getTurnaround_time());
            averageWaiting += p.getWaiting_time();
            averageTurnAround+=p.getTurnaround_time();
        }
        averageWaiting /= dieList.size();
        averageTurnAround /= dieList.size();
        System.out.println("average Waiting Time = " + averageWaiting);
        System.out.println("average Turn Around Time=  " +averageTurnAround);

    }



}
