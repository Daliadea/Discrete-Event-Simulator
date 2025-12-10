import java.util.Optional;

class ArriveEvent extends Event {
    
    ArriveEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    public String toString() {
        return String.format("%.3f %s arrives", this.eventTime, this.customer);
    }

    @Override
    Optional<Pair<Event, Shop>> next(Shop shop) {
        return shop.findServerWithoutCheckingForQueue(this.customer)
        .map(i -> { 
            return new Pair<Event, Shop>(
                new ServeEvent(this.customer, this.eventTime, i),            
                shop
            );
        })
        .or(() -> shop.findServerCheckingForQueue(this.customer)
            .map(i -> { 
                Pair<Server, Pair<Double, Integer>> serverInfoPair = i.customerEntersWaits(
                    this.customer, this.eventTime);
                
                Server updatedServer = serverInfoPair.t();
                double nextFreeTime = serverInfoPair.u().t();
                int queuePosition = serverInfoPair.u().u();
                
                Event waitEvent = new WaitEvent(this.customer, this.eventTime, 
                    updatedServer, nextFreeTime, queuePosition);
                
                return new Pair<Event, Shop>(
                    waitEvent,
                    shop.update(updatedServer)
                );
            })
        )
        .or(() -> Optional.of(new Pair<Event, Shop>(
            new LeaveEvent(this.customer, this.eventTime),
            shop
        )));
    }
}
