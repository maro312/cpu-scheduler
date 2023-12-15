import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        int processNum,RRQ,contextSwitch;

        System.out.println("# of processes");
        processNum = sc.nextInt();
        System.out.println("Please enter the Round Robin Time Quantum");
        RRQ = sc.nextInt();
        System.out.println("Please enter the context switching");
        contextSwitch = sc.nextInt();


        SJF sjf = new SJF();
        SRTF srtf = new SRTF();
        Priority_Scheduling p = new Priority_Scheduling();

        AGRR agrr = new AGRR();


        for (int i = 0; i < processNum; i++) {
            String processName;
            int burstTime,priority = 0,arrivalTime;
            System.out.println("Please enter the process name ");
            processName = scanner.nextLine();

           System.out.println("Please enter the Arrival time of the process");
            arrivalTime = sc.nextInt();

           System.out.println(" Please enter the Burst time");
            burstTime = sc.nextInt();

            System.out.println("Please enter the priority");
            priority = sc.nextInt();


            Process process = new Process(processName,arrivalTime,burstTime,priority,RRQ,i);

            sjf.arr.add(process);
            p.arr.add(process);
            agrr.ArrivalProcess.add(process);
            agrr.allProcesses.add(process) ;
            srtf.arrivalProcesses.add(process);
        }


        int choice;
        System.out.println("Please choose the scheduling Algorithm from the Following  ");
        System.out.println("1- Shortest Job First ( SJF ) ");
        System.out.println("2- Shortest Remaining Time First ( SRTF ) ");
        System.out.println("3- Priority scheduling");
        System.out.println("4- AG Round Robin ");
        choice = sc.nextInt();

        while (true) {
            if (choice == 1) {
                sjf.execute();
                break;
            } else if (choice == 2) {
                srtf.execute();
                break;
            } else if (choice == 3) {
                p.execute();
                break;
            } else if (choice == 4) {
                agrr.execute();
                break;

            }
        }



    }
}