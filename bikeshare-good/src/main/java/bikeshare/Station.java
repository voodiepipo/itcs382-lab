package bikeshare;

import java.util.ArrayDeque;
import java.util.Queue;

public final class Station {
    private final String name;
    private final Queue<Bike> availableBikes = new ArrayDeque<>();

    public Station(String name) {
        this.name = name;
    }

    public void addBike(Bike bike) {
        availableBikes.add(bike);
    }

    public Bike takeBike() {
        return availableBikes.poll();
    }
}
