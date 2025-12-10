class Customer {

    private final int id;
    private final double arrivalTime;


    Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    boolean canBeServed(double serverFreeTime) {
        if (this.arrivalTime >= serverFreeTime) {
            return true;
        }
        return false;
    }

    int getTieBreaker(Customer customer) {
        if (this.arrivalTime > customer.arrivalTime)  {
            return 1; 
        }   else {
            return -1;
        }
    }

    public String toString() {
        return "customer " + this.id;
    }
}




