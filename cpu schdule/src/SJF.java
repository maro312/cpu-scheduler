
//import Process;
//import Main.duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.io.*;
public class SJF {
    //PriorityQueue<Process> pq = new PriorityQueue<>();
    //Process [] arr ;
    ArrayList<Process> arr = new ArrayList<>();
    void execute(){
        int minA = 1000000000,minB = 1000000000,minID = -1;
        /*
        this.name = name;
        this.arrive_time = arrive;
        this.burst_time = burst;
        this.priority = priorityNumber;
        this.quantum = quantum;
        this.start = -1;
        this.end = -1;
         */
        //Process process = new Process();
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).arrive_time <= minA){
                minA = arr.get(i).arrive_time;
                minID = arr.get(i).id;
                if (arr.get(i).burst_time <= minB){
                    minB = arr.get(i).burst_time;
                    minID = arr.get(i).id;
                }
            }
        }

        Collections.sort(arr,Comparator.comparingInt(Process::getBurst_time));

        int curr_time = -1;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).id == minID){
                arr.get(i).end = arr.get(i).arrive_time + arr.get(i).burst_time;
                curr_time = arr.get(i).end;

                arr.remove(arr.get(i));
                break;
            }

        }








//        for (int i = 0; i < arr.size(); i++){
//            System.out.println(arr.get(i).burst_time);
//        }

    }



}
