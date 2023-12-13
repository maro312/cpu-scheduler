
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.io.*;

public class Priority_Scheduling {
    ArrayList<Process> arr = new ArrayList<>();

    void execute(){
        arr.sort(Comparator.comparingInt(Process::getPriority));
        int cur_time = 0;
        int op = 0;
        while (!arr.isEmpty()){
            if (op == 0){
                arr.get(0).waiting_time = arr.get(0).arrive_time;
                arr.get(0).turnaround_time = arr.get(0).burst_time + arr.get(0).arrive_time;
                
            }
        }
    }
}
