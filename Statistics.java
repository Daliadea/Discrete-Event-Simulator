class Statistics {
    private final int served;
    private final int left;
    private final double totalWaitingTime;

    Statistics() {
        this.served = 0;
        this.left = 0;
        this.totalWaitingTime = 0.0;
    }

    private Statistics(int served, int left, double totalWaitingTime) {
        this.served = served;
        this.left = left;
        this.totalWaitingTime = totalWaitingTime;
    }

    Statistics incrementServed() {
        return new Statistics(this.served + 1, this.left, this.totalWaitingTime);
    }

    Statistics incrementLeft() {
        return new Statistics(this.served, this.left + 1, this.totalWaitingTime);
    }

    Statistics addWaitTime(double waitTime) {
        return new Statistics(this.served, this.left, this.totalWaitingTime + waitTime);
    }

    @Override
    public String toString() {
        double averageWaitingTime = served > 0 ? totalWaitingTime / served : 0.0;
        return String.format("[%.3f %d %d]", averageWaitingTime, served, left);
    }
}