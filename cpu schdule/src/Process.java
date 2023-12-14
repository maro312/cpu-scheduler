public class Process {
    String name;
    int arrive_time;
    int burst_time;
    int remainingBurst;
    int priority;
    int quantum;

    int remainingQuantum;
    int AGFactor;
    int id;
    int start;
    int end;
    int waiting_time;
    int turnaround_time;
    Process(String name, int arrive, int burst, int priorityNumber, int quantum, int id) {
        this.name = name;
        this.arrive_time = arrive;
        this.burst_time = burst;
        this.remainingBurst = burst;
        this.priority = priorityNumber;
        this.quantum = quantum;
        this.id = id;
        this.start = -1;
        this.end = -1;
        this.waiting_time = -1;
        this.turnaround_time = -1;
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

    public void setAGFactor(int AGFActor) {
        this.AGFactor = AGFActor;
    }

    public void decreaseBurst(){
        this.burst_time --;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getTurnaround_time() {
        return turnaround_time;
    }

    public void setTurnaround_time(int turnaround_time) {
        this.turnaround_time = turnaround_time;
    }

    public int getRemainingBurst() {
        return remainingBurst;
    }

    public void setRemainingBurst(int remainingBurst) {
        this.remainingBurst = remainingBurst;
    }

    public int getRemainingQuantum() {
        return remainingQuantum;
    }

    public void setRemainingQuantum(int remainingQuantum) {
        this.remainingQuantum = remainingQuantum;
    }
}
