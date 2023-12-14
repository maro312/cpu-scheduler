import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class AGRR {
    ArrayList<Process> processes = new ArrayList<>();

    ArrayList<Process> ArrivalProcess = new ArrayList<>();
    int time = 0;

    public void CalcAG() {
        for (Process p : processes) {
            Random random = new Random();
            int rand = random.nextInt(21);

            if (rand < 10) {
                p.setAGFactor(rand + p.get_arrival_time() + p.getBurst_time());
            }
            else if (rand > 10) {
                p.setAGFactor(10 + p.get_arrival_time() + p.getBurst_time());
            }
            else {
                p.setAGFactor(p.getPriority() + p.get_arrival_time() + p.getBurst_time());
            }
        }
    }



    public void execute(){
        int currTime = 0;

        CalcAG();
        ArrivalProcess.sort(Comparator.comparingInt(Process::get_arrival_time));

        while (currTime <= ArrivalProcess.getLast().arrive_time || !processes.isEmpty()) {

            //simulate arriving processes
            for (Process p : ArrivalProcess) {
                if (p.get_arrival_time() <= currTime) {
                    processes.add(p);
                }

            }
            processes.sort(Comparator.comparingInt(Process::getAGFactor));

            Process p = processes.getFirst();
            processes.removeFirst();

            int nonPre = (int) Math.ceil((double) processes.getFirst().quantum / 2);
            processes.getFirst().setBurst_time(processes.getFirst().burst_time - nonPre);  //execute non preemptive

            currTime += nonPre;


        }


    }



}