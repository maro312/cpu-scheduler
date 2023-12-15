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

//    public void CalcAG() {
//        for (Process p : current) {
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

        for (Process p : current) {
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
        Process currProcess = null;


        while (!current.isEmpty() || !ArrivalProcess.isEmpty() || !readyQueue.isEmpty()) {
            currTime++;

            if(currProcess != null) {
                currProcess.setBurst_time(currProcess.getBurst_time() - 1);
                currProcess.setRemainingQuantum(currProcess.getRemainingQuantum() - 1);
            }

            // Simulate arriving processes
            Iterator<Process> iterator = ArrivalProcess.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p.get_arrival_time() <= currTime) {
                    current.add(p);
                    readyQueue.add(p) ;
                    iterator.remove();
                }
            }
            // sort based on AG factor then arrival time to handle equal AG factors



            if (!current.isEmpty() || !readyQueue.isEmpty()) {
                    //if no new Processes have arrived
                    //get the first in ready queue
                currProcess = readyQueue.getFirst();



                if((currProcess.getQuantum() - currProcess.getRemainingQuantum()) >= (int) Math.ceil( currProcess.getQuantum() / 2.0)){
                    Process P = currProcess;
                    Comparator<Process> comparator = Comparator.comparing(Process::getAGFactor).thenComparingInt(Process::get_arrival_time);
                    current.sort(comparator);

                    if(!current.isEmpty()) {
                        if (!currProcess.getName().equals(current.get(0).getName())) {
                            // means the current process is the smallest ag
                            // edit quantum of the out process
                            for (int i = 0 ; i < readyQueue.size() ; i ++){
                                if (readyQueue.get(i).getName().equals(current.getFirst().getName())) {
                                    readyQueue.remove(i) ;
                                    i -- ;
                                }
                            }
                            readyQueue.add(readyQueue.getFirst()) ;
                            readyQueue.remove(0) ;
                            P.setQuantum(P.getQuantum() + P.getRemainingQuantum());
                            readyQueue.add(0 , current.getFirst()) ;
                            readyQueue.getFirst().setRemainingQuantum(readyQueue.getFirst().getQuantum());
                            currProcess = readyQueue.getFirst() ;
                        }
                    }
                }
                if (currProcess.getRemainingQuantum() <= 0){
                    // out and add 10% of the mean
                    double mean = Math.ceil(0.1 * calculateMeanQuantumTime());
                    currProcess.setQuantum(currProcess.getQuantum() + (int)mean);
                    readyQueue.add(readyQueue.getFirst()) ;
                    readyQueue.remove(0) ;
                    readyQueue.getFirst().setRemainingQuantum(readyQueue.getFirst().getQuantum());
                    currProcess = readyQueue.getFirst() ;
                }
                if (currProcess.getBurst_time() <= 0) {
                    // die

                    dieList.add(currProcess) ;
                    currProcess.setEnd(currTime);
                    currProcess.setTurnaround_time(currTime - currProcess.get_arrival_time());
                    currProcess.setWaiting_time(currProcess.getTurnaround_time() - currProcess.getRemainingBurst());
                    currProcess.setQuantum(0);

                    for (int i = 0 ; i < current.size() ; i ++){
                        if (current.get(i).getName().equals(currProcess.getName())){
                            current.remove(i) ;
                            i -- ;
                        }
                    }
                    for (int i = 0 ; i < readyQueue.size() ; i ++){
                        if (readyQueue.get(i).getName().equals(currProcess.getName())){
                            readyQueue.remove(i) ;
                            i -- ;
                        }
                    }
                    if (!readyQueue.isEmpty()){
                        currProcess = readyQueue.getFirst() ;
                        currProcess.setRemainingQuantum(currProcess.getQuantum());
                    }

                }

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
        System.out.println(averageWaiting);
        System.out.println(averageTurnAround);

    }



}
