import static java.lang.StrictMath.ceil;


public class Process {
    String name;
    int arrive_time;

    int burst_time;

    int AGFactor;
    int priority;
    int quantum;
    int id;
    int start;
    int end;
    Process(String name, int arrive, int burst, int priorityNumber, int quantum, int id) {
        this.name = name;
        this.arrive_time = arrive;
        this.burst_time = burst;
        this.priority = priorityNumber;
        this.quantum  = (int) (ceil ( (double) quantum / 2));
        this.id = id;
        this.start = -1;
        this.end = -1;
    }
    Process(int burst) {

        this.burst_time = burst;

    }
    Process(String name, int arrive, int burst) {
        this.name = name;
        this.arrive_time = arrive;
        this.burst_time = burst;
        this.start = -1;
        this.end = -1;
    }



     String getName() {
        return name;
    }

     int getQuantum() {
        return quantum;
    }

    void setQuantum(int quantum) {
        this.quantum = quantum;
    }

     int getPID() {
        return id;
    }

     int get_arrival_time() {
        return arrive_time;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAGFactor() {
        return AGFactor;
    }

    public void setAGFactor(int AGFactor) {
        this.AGFactor = AGFactor;
    }

}
