import java.util.Optional;

class LeaveEvent extends Event {

    LeaveEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves", this.eventTime, this.customer);
    }
    
    @Override
    Optional<Pair<Event,Shop>> next(Shop shop) {
        return Optional.of(new Pair<Event,Shop>(this, shop));
    }

    @Override
    Statistics update(Statistics statistics) {
        return statistics.incrementLeft();
    }

}

