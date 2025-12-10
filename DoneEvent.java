import java.util.Optional;

class DoneEvent extends Event {

    DoneEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s done", this.eventTime, this.customer);
    }

    @Override
    Optional<Pair<Event, Shop>> next(Shop shop) {
        return Optional.of(new Pair<Event, Shop>(this, shop));
    }    
}

