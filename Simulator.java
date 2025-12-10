import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

class Simulator {
    private final int numOfServer;
    private final int qmax;
    private final int numOfCustomers;
    private final List<Pair<Integer, Double>> arrivals;
    private final Supplier<Double> serviceTime;

    Simulator(int numOfServer, int qmax, Supplier<Double> serviceTime, int numOfCustomers, 
            List<Pair<Integer, Double>> arrivals) {
        this.numOfServer = numOfServer;
        this.qmax = qmax;
        this.serviceTime = serviceTime;
        this.numOfCustomers = numOfCustomers;    
        this.arrivals = arrivals;
    }

    Pair<String, String> run() {
        // Create customers and event times
        List<Customer> customers = arrivals.stream()
            .map(x -> new Customer(x.t(), x.u()))
            .toList();

        List<Double> eventTimes = arrivals.stream()
            .map(x -> x.u())
            .toList();

        PQ<Event> pq = Stream.iterate(0, i -> i < this.numOfCustomers, i -> i + 1)
            .map(i -> new ArriveEvent(customers.get(i), eventTimes.get(i)))
            .reduce(new PQ<Event>(), (currentPQ, event) -> currentPQ.add(event), 
                (pq1, pq2) -> pq2);

        State init = new State(pq, new Shop(this.numOfServer, this.serviceTime, this.qmax));
        
        // Create a list of all states for the simulation
        List<Optional<State>> states = Stream.iterate(Optional.of(init),
            ostate -> ostate.isPresent(),
            ostate -> ostate.flatMap(st -> st.next()))
            .toList();
        
        // Get the final state
        Optional<State> finalState = states.get(states.size() - 1);
        
        String simOutput = states.stream()
            .filter(ostate -> ostate.isPresent())
            .map(ostate -> ostate.get())
            .map(state -> state.toString())
            .filter(str -> !str.isEmpty())
            .reduce("", (x, y) -> x + y + "\n");

        // Get statistics from the final state
        String statistics = finalState.map(state -> state.getStatistics()).orElse("[0.000 0 0]");

        return new Pair<>(simOutput, statistics);
    }
}
