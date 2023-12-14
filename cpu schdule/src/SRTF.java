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

    public void setAverageWaiting(double averageWaiting) {
        this.averageWaiting = averageWaiting;
    }

    public double getAverageTurnAround() {
        return averageTurnAround;
    }

    public void setAverageTurnAround(double averageTurnAround) {
        this.averageTurnAround = averageTurnAround;
    }

    public void execute() {
        int currTime = 0;

        arrivalProcesses.sort(Comparator.comparingInt(Process::get_arrival_time));

        while (!arrivalProcesses.isEmpty() || !readyQueue.isEmpty()) {

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


            Comparator<Process> comparator = Comparator.comparing(Process::getRemainingBurst).thenComparingInt(Process::get_arrival_time);
            readyQueue.sort(comparator);

            Process currProcess = readyQueue.getFirst();

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


            if(currProcess.getRemainingBurst() > 0) {

                currProcess.setRemainingBurst(currProcess.getRemainingBurst() - 1);
                currTime++;

                if(currProcess.getRemainingBurst()== 0){

                    currProcess.setEnd(currTime);
                    currProcess.setTurnaround_time(currTime - currProcess.get_arrival_time());
                    currProcess.setWaiting_time(currTime - currProcess.get_arrival_time()- currProcess.getBurst_time());

                    dieList.add(readyQueue.getFirst());
                    readyQueue.removeFirst();

                }
            }



        }


        for (Process p: dieList) {
            System.out.println(p.getName() + "waiting time = "+ p.getWaiting_time());
            averageWaiting += p.getWaiting_time();
            averageTurnAround+=p.getTurnaround_time();
        }
        averageWaiting /= dieList.size();
        averageTurnAround /= dieList.size();


    }




}
