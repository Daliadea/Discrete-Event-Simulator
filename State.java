import java.util.Optional;

class State {
    private final PQ<Event> pq;
    private final Shop shop;
    private final String log;
    private final Statistics statistics;

    State(PQ<Event> pq, Shop shop) {
        this.pq = pq;
        this.shop = shop;
        this.log = "";
        this.statistics = new Statistics();
    }

    private State(PQ<Event> pq, Shop shop, String log, Statistics statistics) {
        this.pq = pq;
        this.shop = shop;
        this.log = log;
        this.statistics = statistics;
    }

    Optional<State> next() {
        Pair<Optional<Event>, PQ<Event>> polledPQ = this.pq.poll();
        
        return polledPQ.t()
            .flatMap(event -> {
                Optional<Pair<Event, Shop>> next = event.next(this.shop);
                
                return next.map(pair -> {
                    Event nextEvent = pair.t();
                    Shop nextShop = pair.u();
                    
                    PQ<Event> newPQ = polledPQ.u();
                    
                    if (nextEvent != event) {
                        newPQ = newPQ.add(nextEvent);
                    }
                    
                    Statistics updatedStats = event.update(statistics);
                    
                    return new State(newPQ, nextShop, event.toString(), updatedStats);
                });
            });
    }

    String getStatistics() {
        return statistics.toString();
    }

    @Override
    public String toString() {
        return this.log;
    }
}