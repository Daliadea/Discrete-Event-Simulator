# Discrete Event Simulator

A Java-based discrete event simulation that models a customer service shop with multiple servers, queue management, and comprehensive statistics tracking.

## Overview

This project simulates a real-world service environment where customers arrive at different times, wait in queues if servers are busy, get served, and eventually leave. The simulator uses an event-driven architecture to process customer interactions through various states.

## Features

- **Event-Driven Architecture**: Handles multiple event types including arrivals, service starts, waiting, service completion, and departures
- **Multiple Server Management**: Configurable number of servers with individual queue capacities
- **Priority Queue System**: Efficient event scheduling and processing based on event times
- **Statistical Analysis**: Tracks customers served, customers who left, and total waiting time
- **Functional Programming**: Utilizes Java Streams, Suppliers, and immutable state management
- **Customizable Service Times**: Flexible service time configuration through Supplier interface
- **Queue Capacity Control**: Configurable maximum queue length per server

## Project Structure

```
.
├── Main.java                  # Entry point and input handling
├── Simulator.java             # Core simulation logic
├── Shop.java                  # Server management
├── Server.java                # Individual server state and operations
├── Customer.java              # Customer entity
├── Event.java                 # Abstract event class
├── ArriveEvent.java           # Customer arrival event
├── ServeEvent.java            # Service start event
├── WaitEvent.java             # Queue waiting event
├── WaitceptionEvent.java      # Extended waiting event
├── DoneEvent.java             # Service completion event
├── LeaveEvent.java            # Customer departure event
├── State.java                 # Simulation state management
├── Statistics.java            # Statistics tracking
├── PQ.java                    # Priority queue implementation
├── Pair.java                  # Generic pair utility
└── DefaultServiceTime.java    # Default service time provider
```

## How It Works

1. **Customer Arrival**: Customers arrive at specified times
2. **Server Check**: System checks for available servers
3. **Service or Queue**: Customer is either served immediately or joins a queue
4. **Service Completion**: After service, the next customer in queue is processed
5. **Statistics**: System tracks all events and computes metrics

## Input Format

```
<number_of_servers>
<max_queue_length>
<number_of_customers>
<customer_id> <arrival_time>
<customer_id> <arrival_time>
...
```

## Output

The simulator provides:
- Detailed event log showing each customer's journey
- Final statistics including:
  - Average waiting time
  - Number of customers served
  - Number of customers who left without service

## Technical Highlights

- **Immutable State Management**: Uses Optional types and immutable objects
- **Stream-Based Processing**: Functional approach throughout the codebase
- **Generic Data Structures**: Reusable priority queue implementation
- **Object-Oriented Design**: Clean separation of concerns with abstract classes and interfaces

## Use Cases

This simulator can model various real-world scenarios:
- Bank teller queues
- Restaurant service operations
- Customer support centers
- Healthcare appointment systems
- Retail checkout lines

## Compilation and Execution

```bash
# Compile all Java files
javac *.java

# Run the simulator with input file
java Main < input.txt

# Or run with manual input
java Main
```

## Testing

To test the simulator, create an input file or provide input directly. Here's an example test case:

**Example Input (`test.txt`):**
```
2
1
6
1 0.000
2 0.500
3 1.000
4 1.200
5 1.500
6 2.000
```

**Run the test:**
```bash
javac *.java
java Main < test.txt
```

**Expected Output:**
The simulator will output a chronological event log showing:
- Customer arrivals
- Service start times
- Queue waiting events
- Service completion
- Customers leaving (if queue is full)
- Final statistics: `[average_waiting_time customers_served customers_left]`

Example output:
```
0.000 customer 1 arrives
0.000 customer 1 serves by server 1
0.500 customer 2 arrives
0.500 customer 2 waits at server 1
1.000 customer 1 done
1.000 customer 2 serves by server 1
...
[0.625 4 2]
```

## Extensibility

The simulator's architecture allows for various modifications and enhancements:

### 1. Custom Service Time Distributions

Replace `DefaultServiceTime` with custom implementations:

```java
// Create a custom service time supplier
Supplier<Double> customServiceTime = () -> {
    // Your custom logic here
    // Example: Exponential distribution
    return -Math.log(Math.random()) * meanServiceTime;
};

// Use it in the simulator
Simulator sim = new Simulator(numServers, qmax, customServiceTime, 
                              numCustomers, arrivals);
```

### 2. Adding New Event Types

Extend the `Event` class to create custom events:

```java
class CustomEvent extends Event {
    CustomEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }
    
    @Override
    public String toString() {
        return String.format("%.3f %s custom action", 
                            this.eventTime, this.customer);
    }
    
    @Override
    Optional<Pair<Event, Shop>> next(Shop shop) {
        // Your custom logic
        return Optional.of(new Pair<>(this, shop));
    }
    
    @Override
    Statistics update(Statistics statistics) {
        // Update statistics as needed
        return statistics;
    }
}
```

### 3. Custom Statistics Tracking

Modify the `Statistics` class to track additional metrics:

```java
class ExtendedStatistics extends Statistics {
    private final int customMetric;
    
    // Add methods to track your custom metrics
    ExtendedStatistics addCustomMetric(int value) {
        return new ExtendedStatistics(..., value);
    }
}
```

### 4. Different Queue Policies

Modify `Shop.java` and `Server.java` to implement different queueing strategies:
- Priority queues (by customer type, VIP status, etc.)
- Round-robin server selection
- Load balancing algorithms
- Multiple queue types (express lanes, etc.)

### 5. Real-Time Visualization

Integrate the simulator with visualization libraries:
- Connect event outputs to a GUI framework (JavaFX, Swing)
- Export events to CSV/JSON for external analysis
- Create real-time dashboards showing queue states

### 6. Integration with External Systems

- Connect to databases to log events
- Use as a backend for web applications
- Integrate with monitoring systems
- Export results to analytics platforms

### 7. Performance Optimization

For large-scale simulations:
- Implement parallel processing for multiple simulation runs
- Add caching mechanisms for repeated calculations
- Optimize the priority queue for your specific use case
- Use profiling tools to identify bottlenecks

### Example: Bank Teller Simulation

```java
// Customize for bank scenario
int numTellers = 5;
int maxQueueLength = 10;
Supplier<Double> bankServiceTime = () -> {
    // Normal distribution for service time
    return normalDistribution(mean: 3.0, stdDev: 0.5);
};

// Run simulation with bank-specific parameters
Simulator bankSim = new Simulator(numTellers, maxQueueLength, 
                                  bankServiceTime, customers, arrivals);
```

### Example: Restaurant Service Simulation

```java
// Different service times for different customer types
Supplier<Double> restaurantServiceTime = () -> {
    if (customer.isVIP()) {
        return 2.0; // Faster service for VIP
    }
    return 5.0; // Standard service time
};
```

## Requirements

- Java 11 or higher (uses Java Streams and functional interfaces)
