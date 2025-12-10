import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class Shop {
    private final List<Server> servers;
    private final int maxQueueLength;
    private final Supplier<Double> serviceTimeSupplier;

    Shop(List<Server> updatedServers, Supplier<Double> serviceTimeSupplier, int maxQueueLength) {
        this.servers = updatedServers;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.maxQueueLength = maxQueueLength;
    }
    
    Shop(int numberOfServers, Supplier<Double> serviceTimeSupplier, int maxQueueLength) {
        this.servers = (numberOfServers > 0) ?
            IntStream.rangeClosed(1, numberOfServers)
            .mapToObj(i -> new Server(i, serviceTimeSupplier, 0))
            .toList() :
            List.of();
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.maxQueueLength = maxQueueLength;
    }

    public String toString() {
        return servers.toString();
    }

    Optional<Server> findServerCheckingForQueue(Customer customer) {
        return IntStream.range(0, this.servers.size())
            .mapToObj(i -> this.servers.get(i))
            .filter(x -> x.canServe(this.maxQueueLength))
            .filter(x -> !x.canServe(customer))
            .findFirst();
    }

    Optional<Server> findServerWithoutCheckingForQueue(Customer customer) {
        return IntStream.range(0, this.servers.size())
            .mapToObj(i -> this.servers.get(i))
            .filter(x -> x.canServe(customer))
            .findFirst();
    }

    Optional<Server> findMostUpdatedServer(Server newServer) {
        return IntStream.range(0, this.servers.size())
            .mapToObj(x -> this.servers.get(x))
            .filter(server -> server.checkForSameServer(newServer))
            .findFirst();
    }

    Shop update(Server newServer) {
        return new Shop(IntStream.range(0, this.servers.size())
                .mapToObj(x -> this.servers.get(x))
                .map(server -> server.checkForSameServer(newServer) ? newServer : server)
                .toList(), this.serviceTimeSupplier, this.maxQueueLength);
    }

    
}