
//import Process;
//import Main.duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;
import java.io.*;
public class SJF {

    ArrayList<Process> arr = new ArrayList<>();
    ArrayList<Process> out = new ArrayList<>();

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
        ArrayList<Process> by_arrive = new ArrayList<>(arr);
        //by_arrive = arr;
        //Collections.copy(by_arrive,arr);
        //ArrayList<Process>ar = new ArrayList<>();
        arr.sort(Comparator.comparingInt(Process::getBurst_time));
        Collections.sort(by_arrive,Comparator.comparingInt(Process::get_arrival_time));
        int curr_time = -1;
        for (int i = 0; i < arr.size(); i++){
            System.out.println(arr.get(i).burst_time);
        }
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).id == minID){
                arr.get(i).end = arr.get(i).arrive_time + arr.get(i).burst_time;
                curr_time = arr.get(i).end;
                arr.get(i).waiting_time = arr.get(i).arrive_time;
                //arr.get(i).arrive_time = curr_time;
                arr.get(i).turnaround_time = curr_time;
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
                //if (ck){
                  //  i = 0;
                    //ck = false;
                //}
                arr.get(i).waiting_time = curr_time;
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
                by_arrive.get(i).waiting_time = curr_time;
                curr_time += by_arrive.get(i).burst_time;
                by_arrive.get(i).turnaround_time = curr_time;
                out.add(by_arrive.get(i));
                by_arrive.remove(by_arrive.get(i));
                arr = new ArrayList<>(by_arrive);
                Collections.sort(arr,Comparator.comparingInt(Process::getBurst_time));
            }
            i++;
        }
        /*for (int i = 0;i<arr.size();i++){
            if (arr.get(i).arrive_time <= curr_time){
                if (ck){
                    i = 0;
                    ck = false;
                }
                arr.get(i).waiting_time = curr_time;
                curr_time += arr.get(i).burst_time;
                arr.get(i).turnaround_time = curr_time - arr.get(i).arrive_time;
                out.add(arr.get(i));
                arr.remove(arr.get(i));
                by_arrive = new  ArrayList<>(arr);
                Collections.sort(by_arrive,Comparator.comparingInt(Process::get_arrival_time));
                ck = true;
            }
            if (i == arr.size()-1 && arr.get(i).arrive_time > curr_time){
                i = 0;
                curr_time = by_arrive.get(0).arrive_time;
                by_arrive.get(i).waiting_time = curr_time;
                curr_time += by_arrive.get(i).burst_time;
                by_arrive.get(i).turnaround_time = curr_time;
                out.add(by_arrive.get(i));
                by_arrive.remove(by_arrive.get(i));
                arr = new ArrayList<>(by_arrive);
                Collections.sort(arr,Comparator.comparingInt(Process::getBurst_time));
            }
        }*/

        for ( i = 0; i < out.size(); i++) {
            System.out.print("Waiting time: ");
            System.out.println(out.get(i).waiting_time);
        }



    }
    /*void print(){

    }*/


}