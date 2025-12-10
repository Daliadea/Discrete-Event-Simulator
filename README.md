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

## Requirements

- Java 11 or higher (uses Java Streams and functional interfaces)

## License

This project is part of an academic assignment.
