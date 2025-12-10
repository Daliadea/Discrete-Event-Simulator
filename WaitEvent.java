import java.util.Optional;

class WaitEvent extends Event {
    private final Server server; 
    private final double serverNextFreeTime;
    private final int queuePosition;

    WaitEvent(Customer customer, double eventTime, Server server, 
        double serverNextFreeTime, int queuePosition) {
        super(eventTime, customer);
        this.server = server;
        this.serverNextFreeTime = serverNextFreeTime;
        this.queuePosition = queuePosition;
    }


    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s", this.eventTime, 
        this.customer, this.server);
    }

    @Override
    Optional<Pair<Event, Shop>> next(Shop shop) {
        return Optional.of(new Pair<Event, Shop>(new WaitceptionEvent(this.customer, 
            this.serverNextFreeTime, this.server, this.queuePosition, this.eventTime),shop));     
    }    
    
    @Override
    Statistics update(Statistics statistics) {
        return statistics;
    }
}
