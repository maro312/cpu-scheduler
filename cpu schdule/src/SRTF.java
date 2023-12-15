import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class SRTF {

    ArrayList<Process> readyQueue = new ArrayList<>();
    ArrayList<Process>arrivalProcesses = new ArrayList<>();

    ArrayList<Process> dieList = new ArrayList<>();

    double averageWaiting,averageTurnAround;

    public double getAverageWaiting() {
        return averageWaiting;
    }


    public double getAverageTurnAround() {
        return averageTurnAround;
    }


    public void execute() {
        int currTime = 0;

        arrivalProcesses.sort(Comparator.comparingInt(Process::get_arrival_time));

        while (!arrivalProcesses.isEmpty() || !readyQueue.isEmpty()) {
            //simulate the arrival of Processes
            Iterator<Process> iterator = arrivalProcesses.iterator();
            while (iterator.hasNext()) {
                Process p = iterator.next();
                if (p.get_arrival_time() <= currTime) {
                    readyQueue.add(p);
                    iterator.remove();
                }
            }
            if(readyQueue.isEmpty()){
                System.out.println("idle cpu");
                currTime++;
                continue;
            }

            //sort on burst time
            Comparator<Process> comparator = Comparator.comparing(Process::getRemainingBurst).thenComparingInt(Process::get_arrival_time);
            readyQueue.sort(comparator);

            Process currProcess = readyQueue.getFirst();

            //my algorithm to solve starvation
            //if Process gets a priority of zero make it execute for 10% of it's burst
            for (Process p: readyQueue) {
                if(p != currProcess) {
                    if (p.getPriority() == 0) {
                        p.setRemainingBurst(p.getRemainingBurst() - (int) Math.ceil((double)p.getRemainingBurst()/10));
                        p.setPriority(p.getPriority() + 4);
                        currTime += (int) Math.ceil((double)p.getRemainingBurst()/10) ;
                    }
                    p.setPriority(p.getPriority() - 1);
                }
            }

            //if the current Process still didn't finish
            if(currProcess.getRemainingBurst() > 0) {
                //execute it
                currProcess.setRemainingBurst(currProcess.getRemainingBurst() - 1);
                currTime++;

                //if the Process finished the execution
                if(currProcess.getRemainingBurst()== 0){
                    //calc the End, Waiting and Turn around Time
                    currProcess.setEnd(currTime);
                    currProcess.setTurnaround_time(currTime - currProcess.get_arrival_time());
                    currProcess.setWaiting_time(currTime - currProcess.get_arrival_time()- currProcess.getBurst_time());

                    dieList.add(readyQueue.getFirst());
                    readyQueue.removeFirst();

                }
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
