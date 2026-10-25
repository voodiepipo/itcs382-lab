package bikeshare;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BikeShareService {
    private record Rental(MemberType memberType, Bike bike, String startStation) {
    }

    private final Map<String, Station> stations = new LinkedHashMap<>();
    private final Map<String, Rental> rentals = new LinkedHashMap<>();
    private final RideRepository rideRepository;

    public BikeShareService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
        addStation("Library");
        addStation("Canteen");
        addStation("Dorm");
    }

    private void addStation(String name) {
        Station station = new Station(name);
        station.addBike(new Bike(name + "-1"));
        station.addBike(new Bike(name + "-2"));
        stations.put(name, station);
    }

    public Bike rent(String memberId, MemberType memberType, String stationName) {
        Station station = station(stationName);
        if (rentals.containsKey(memberId)) {
            throw new IllegalStateException("Member already has a bike");
        }
        Bike bike = station.takeBike();
        if (bike == null) {
            throw new IllegalStateException("No bike available at " + stationName);
        }
        rentals.put(memberId, new Rental(memberType, bike, stationName));
        return bike;
    }

    public Ride returnBike(String memberId, String stationName, int minutes) throws IOException {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        Station destination = station(stationName);
        Rental rental = rentals.get(memberId);
        if (rental == null) {
            throw new IllegalStateException("Member has no bike to return");
        }
        Ride ride = new Ride(memberId, rental.memberType(), rental.bike(), rental.startStation(),
                stationName, minutes);
        rideRepository.save(ride);
        destination.addBike(rental.bike());
        rentals.remove(memberId);
        return ride;
    }

    private Station station(String name) {
        Station station = stations.get(name);
        if (station == null) {
            throw new IllegalArgumentException("Unknown station: " + name);
        }
        return station;
    }
}
