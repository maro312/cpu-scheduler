
import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.io.*;

public class Priority_Scheduling {
    ArrayList<Process> arr = new ArrayList<>();
    ArrayList<Process> out = new ArrayList<>();
    void execute(){
        arr.sort(Comparator.comparingInt(Process::getPriority));
        int cur_time = 0;
        int op = 0;
        arr.get(0).waiting_time = arr.get(0).arrive_time;
        arr.get(0).turnaround_time = arr.get(0).burst_time + arr.get(0).arrive_time;
        cur_time = arr.get(0).turnaround_time;
        out.add(arr.get(0));
        arr.remove(arr.get(0));
        op++;
        int i = 0;
        while (!arr.isEmpty()){
            if (op == 3){
                arr.get(arr.size()-1).priority += op;
                arr.sort(Comparator.comparingInt(Process::getPriority));
                op = 0;
                continue;
            }
            if (arr.get(i).arrive_time <= cur_time){
                if (i == arr.size()){
                    i = 0;
                }
                arr.get(i).waiting_time = cur_time;
                arr.get(i).turnaround_time = cur_time + arr.get(0).burst_time;
                cur_time = arr.get(i).turnaround_time;
                op++;
                out.add(arr.get(i));
                arr.remove(arr.get(i));
                i++;
            }
            else{
                i++;
            }
        }

    }
}
