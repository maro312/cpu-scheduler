import java.util.ArrayList;
import java.util.Random;

public class AGRR {
    ArrayList<Process> processes = new ArrayList<>();
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

        CalcAG();



    }



}
