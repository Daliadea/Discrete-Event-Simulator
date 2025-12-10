import java.util.Optional;

abstract class Event implements Comparable<Event> {
    protected final double eventTime;
    protected final Customer customer;
    
    Event(double eventTime, Customer customer) {
        this.eventTime = eventTime;
        this.customer = customer;
    }


    @Override
    public int compareTo(Event event) {
        if (this.eventTime == event.eventTime) {
            return this.customer.getTieBreaker(event.customer);
        }
        return Double.compare(this.eventTime, event.eventTime);
    }
    
    abstract Optional<Pair<Event, Shop>> next(Shop shop);

    Statistics update(Statistics statistics) {
        return statistics; // Default implementation if statistwics dun change
    }

}
