import java.util.function.Supplier;

class Server {

    private final int id;
    private final double nextFreeTime;
    private final Supplier<Double> serviceTimeSupplier;
    private final int queueLength;

    Server(int id, Supplier<Double> serviceTimeSupplier, int queueLength) {
        this.id = id;
        this.nextFreeTime = 0.0;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.queueLength = 0;
    }

    Server(int id, double nextFreeTime, Supplier<Double> serviceTimeSupplier, 
        int queueLength) {
        this.id = id;
        this.nextFreeTime = nextFreeTime;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.queueLength = queueLength;
    }

    private double getServiceTime() {
        return this.serviceTimeSupplier.get();
    }   

    int getId() {
        return this.id;
    }

    double getNextFreeTime() {
        return this.nextFreeTime;
    }

    int getQueueLength() {
        return this.queueLength;
    }

    Pair<Server, Double> serve(double eventTime) {
        double endTime = eventTime + this.getServiceTime();
        return new Pair<Server, Double>(
            new Server(this.id, endTime, this.serviceTimeSupplier, this.queueLength), 
            endTime
        );
    }

    Pair<Server, Pair<Double, Integer>> customerEntersWaits(Customer customer, double eventTime) {
        Server updatedServer = this.incrementationOfQueueLength();
        Pair<Double, Integer> waitInfo = new Pair<>(this.nextFreeTime, this.queueLength);
        return new Pair<>(updatedServer, waitInfo);
    }

    Pair<Server, Pair<Double, Integer>> waitceptionInfo(Customer customer, double eventTime, 
        int queuePosition) {
        Pair<Double, Integer> waitceptionInfo = new Pair<>(this.nextFreeTime, queuePosition - 1);
        return new Pair<>(this, waitceptionInfo);
    }

    Server decrementationOfQueueLength() {
        return new Server(this.id, this.nextFreeTime, this.serviceTimeSupplier, 
        this.queueLength - 1);
    }

    Server incrementationOfQueueLength() {
        return new Server(this.id, this.nextFreeTime, this.serviceTimeSupplier, 
        this.queueLength + 1);
    }

    boolean canServe(int maxQueueLength) {
        boolean result = (this.queueLength < maxQueueLength);
        return result;
    }
    
    boolean canServe(Customer customer) {
        boolean result = customer.canBeServed(this.nextFreeTime);
        return result;
    }

    boolean checkForSameServer(Server server) {
        boolean result = (this.id == server.id);
        return result;
    }

    public String toString() {
        return "server " + id;
    }
}