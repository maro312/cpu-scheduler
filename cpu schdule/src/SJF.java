
//import Process;
//import Main.duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.io.*;
public class SJF {

    ArrayList<Process> arr = new ArrayList<>();
    ArrayList<Process> out = new ArrayList<>();

    double averageWaiting,averageTurnAround;

    public double getAverageWaiting() {
        return averageWaiting;
    }

    int currTime = -1;
    public double getAverageTurnAround() {
        return averageTurnAround;
    }

    void execute(){
        int minA = 1000000000,minB = 1000000000,minID = -1;

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
        ArrayList<Process> by_arrive = new ArrayList<>(arr);

        arr.sort(Comparator.comparingInt(Process::getBurst_time));
        Collections.sort(by_arrive,Comparator.comparingInt(Process::get_arrival_time));
        int curr_time = -1;
//        for (int i = 0; i < arr.size(); i++){
//            System.out.println(arr.get(i).burst_time);
//        }
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).id == minID){
                arr.get(i).end = arr.get(i).arrive_time + arr.get(i).burst_time;
                curr_time = arr.get(i).end;
                arr.get(i).waiting_time = 0; // first process is runs always at second 0
                //arr.get(i).arrive_time = curr_time;
                arr.get(i).turnaround_time = arr.get(i).burst_time;
                out.add(arr.get(i));
                arr.remove(arr.get(i));
                break;
            }
        }
        boolean ck = false;
        int i = 0;
        while (!arr.isEmpty()){
            if (i >= arr.size()){
                i = 0;
            }
            if (arr.get(i).arrive_time <= curr_time){

                arr.get(i).waiting_time = curr_time - arr.get(i).arrive_time;
                curr_time += arr.get(i).burst_time;
                arr.get(i).turnaround_time = curr_time - arr.get(i).arrive_time;
                out.add(arr.get(i));
                arr.remove(arr.get(i));
                by_arrive = new  ArrayList<>(arr);
                Collections.sort(by_arrive,Comparator.comparingInt(Process::get_arrival_time));
                //ck = true;
                i = 0;
            }
            else if (i == arr.size()-1 && arr.get(i).arrive_time > curr_time){
                i = 0;
                curr_time = by_arrive.get(0).arrive_time;
                by_arrive.get(i).waiting_time = curr_time - arr.get(i).arrive_time; // from arrival to beginning  of process
                curr_time += by_arrive.get(i).burst_time;
                by_arrive.get(i).turnaround_time = curr_time - arr.get(i).arrive_time; // from arrival to end of process
                out.add(by_arrive.get(i));
                by_arrive.remove(by_arrive.get(i));
                arr = new ArrayList<>(by_arrive);
                Collections.sort(arr,Comparator.comparingInt(Process::getBurst_time));
            }
            i++;
        }

        for (Process p: out) {
            System.out.println(p.getName() +" waiting Time "+ p.getWaiting_time());
            System.out.println(p.getName() +" Turn Around "+ p.getTurnaround_time());
            averageWaiting += p.getWaiting_time();
            averageTurnAround+=p.getTurnaround_time();
        }
        averageWaiting /= out.size();
        averageTurnAround /= out.size();
        System.out.println("average Waiting Time = " + averageWaiting);
        System.out.println("average Turn Around Time=  " +averageTurnAround);



    }


}