import java.util.Optional;

class WaitceptionEvent extends Event {
    private final Server server; 
    private final int queuePosition;
    private final double customerArrivalTime; //ie waitEvent eventTime

    WaitceptionEvent(Customer customer, double eventTime, Server server,
        int queuePosition, double customerArrivalTime) {
        super(eventTime, customer);
        this.server = server;
        this.queuePosition = queuePosition;
        this.customerArrivalTime = customerArrivalTime;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    Optional<Pair<Event, Shop>> next(Shop shop) {
        if (queuePosition == 0) {
            return shop.findMostUpdatedServer(server)
            .map(i -> {
                return new Pair<Event, Shop>(new ServeEvent(this.customer, 
                    this.eventTime, this.server, this.customerArrivalTime), 
                    shop.update(i.decrementationOfQueueLength()));
            });
        } else {
            return shop.findMostUpdatedServer(this.server)
            .map(i -> {
                Pair<Server, Pair<Double, Integer>> serverInfoPair = i.waitceptionInfo(
                    this.customer, this.eventTime, this.queuePosition);
                
                Server updatedServer = serverInfoPair.t();
                double nextFreeTime = serverInfoPair.u().t();
                int newQueuePosition = serverInfoPair.u().u();
                
                Event waitceptionEvent = new WaitceptionEvent(this.customer, 
                    nextFreeTime, updatedServer, newQueuePosition, this.customerArrivalTime);
                
                return new Pair<Event, Shop>(
                    waitceptionEvent,
                    shop.update(updatedServer)
                );
            });
        }
    }

    @Override
    Statistics update(Statistics statistics) {
        // WaitEvent doesn't update statistics itself (all handled in ServeEvent)
        return statistics;
    }
}