package bikeshare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static Map<String, Queue<String>> bikes = new LinkedHashMap<>();
    public static Map<String, String[]> rentals = new LinkedHashMap<>();
    public static final Path RIDES_FILE = Path.of("rides.csv");

    public static void main(String[] args) {
        initializeBikes();
        System.out.println("Commands: rent <memberId> <student|staff> <station>, return <memberId> <station> <minutes>, quit");
        try (Scanner input = new Scanner(System.in)) {
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] words = line.split("\\s+");
                try {
                    if (words[0].equals("quit")) {
                        break;
                    } else if (words[0].equals("rent") && words.length == 4) {
                        rent(words[1], words[2], words[3]);
                    } else if (words[0].equals("return") && words.length == 4) {
                        returnBike(words[1], words[2], Integer.parseInt(words[3]));
                    } else {
                        System.out.println("Invalid command");
                    }
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private static void initializeBikes() {
        for (String station : new String[] {"Library", "Canteen", "Dorm"}) {
            Queue<String> stationBikes = new ArrayDeque<>();
            stationBikes.add(station + "-1");
            stationBikes.add(station + "-2");
            bikes.put(station, stationBikes);
        }
    }

    private static void rent(String memberId, String memberType, String stationName) {
        Queue<String> stationBikes = bikes.get(stationName);
        if (stationBikes == null || stationBikes.isEmpty()) {
            throw new IllegalStateException("No bike available at " + stationName);
        }
        if (rentals.containsKey(memberId)) {
            throw new IllegalStateException("Member already has a bike");
        }
        int typeCode;
        if (memberType.equalsIgnoreCase("student")) {
            typeCode = 0;
        } else if (memberType.equalsIgnoreCase("staff")) {
            typeCode = 1;
        } else {
            throw new IllegalArgumentException("Member type must be student or staff");
        }
        String bike = stationBikes.remove();
        rentals.put(memberId, new String[] {bike, Integer.toString(typeCode), stationName});
        System.out.println("Rented " + bike + " to " + memberId);
    }

    private static void returnBike(String memberId, String stationName, int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        Queue<String> returnStation = bikes.get(stationName);
        String[] rental = rentals.get(memberId);
        if (returnStation == null || rental == null) {
            throw new IllegalStateException("Unknown station or member has no bike");
        }
        boolean isStaff = Integer.parseInt(rental[1]) == 1;
        double fee = calculateFee(minutes, isStaff);
        saveRide(memberId, rental[0], rental[2], stationName, minutes, fee);
        returnStation.add(rental[0]);
        rentals.remove(memberId);
        System.out.println("Receipt: bike=" + rental[0] + ", minutes=" + minutes + ", fee=" + fee + " baht");
    }

    private static double calculateFee(int minutes, boolean isStaff) {
        return minutes * (isStaff ? 3.0 : 2.0);
    }

    private static void saveRide(String memberId, String bike, String from, String to, int minutes, double fee) {
        String row = memberId + "," + bike + "," + from + "," + to + "," + minutes + "," + fee + System.lineSeparator();
        try {
            Files.writeString(RIDES_FILE, row, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save rides.csv", e);
        }
    }
}
