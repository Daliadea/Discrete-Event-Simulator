import java.util.Optional;

class ServeEvent extends Event {
    private final Server server;
    private final double customerArrivalTime;

    //from arriveEvent when server is free*
    ServeEvent(Customer customer, double eventTime, Server server) {
        super(eventTime, customer);
        this.server = server;
        this.customerArrivalTime = 0; //not actually zero but set to zero for check
    }
    
    ServeEvent(Customer customer, double eventTime, Server server, double customerArrivalTime) {
        super(eventTime, customer);
        this.server = server;
        this.customerArrivalTime = customerArrivalTime;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by %s", this.eventTime, 
            this.customer, this.server);
    }

    @Override
    Optional<Pair<Event, Shop>> next(Shop shop) {
        return shop.findMostUpdatedServer(this.server)
        .map(i -> {
            Pair<Server, Double> served = i.serve(this.eventTime);
            return new Pair<Event, Shop>(
                new DoneEvent(this.customer, served.u()), 
                shop.update(served.t())
            );    
        });
    }

    @Override
    Statistics update(Statistics statistics) {
        if (this.customerArrivalTime == 0) {
            return statistics.incrementServed();
        } else {
            return statistics.incrementServed()
            .addWaitTime(this.eventTime - this.customerArrivalTime);
        }
    }

    
}

