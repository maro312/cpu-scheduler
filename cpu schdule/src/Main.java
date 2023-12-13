import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);
        int processNum,RRQ;
        System.out.println("# of processes");
        processNum = sc.nextInt();
        System.out.println("Please enter the Round Robin Time Quantum");
        RRQ = sc.nextInt();
        //System.out.println("Please enter the context switching");
        // get the context switch

        SJF sjf = new SJF();
        AGRR agrr = new AGRR();

        for (int i = 0; i < processNum; i++) {
            String processName;
            int burstTime,priority,arrivalTime;
            System.out.println("Please enter the process name ");
            processName = scanner.nextLine();

            System.out.println("Please enter the Arrival time of the process");
            arrivalTime = sc.nextInt();

            System.out.println("Burst time");
            burstTime = sc.nextInt();

            System.out.println("Priority Number");
            priority = sc.nextInt();

            Process process = new Process(processName,arrivalTime,burstTime,priority,RRQ,i);
            //sjf.pq.add(process);


            sjf.arr.add(process);
            agrr.processes.add(process);
        }
        //sjf.execute();

        agrr.execute();




    }
}