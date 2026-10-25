package bikeshare;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        BikeShareService service = new BikeShareService(new CsvRideRepository(Path.of("rides.csv")));
        System.out.println("Commands: rent <memberId> <student|staff> <station>, return <memberId> <station> <minutes>, quit");
        try (Scanner input = new Scanner(System.in)) {
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.equals("quit")) {
                    break;
                }
                handle(line, service);
            }
        }
    }

    private static void handle(String line, BikeShareService service) {
        String[] words = line.split("\\s+");
        try {
            if (words.length == 4 && words[0].equals("rent")) {
                Bike bike = service.rent(words[1], MemberType.valueOf(words[2].toUpperCase()), words[3]);
                System.out.println("Rented " + bike.id() + " to " + words[1]);
            } else if (words.length == 4 && words[0].equals("return")) {
                Ride ride = service.returnBike(words[1], words[2], Integer.parseInt(words[3]));
                System.out.println("Receipt: bike=" + ride.bike().id() + ", minutes=" + ride.minutes()
                        + ", fee=" + ride.fee() + " baht");
            } else {
                System.out.println("Invalid command");
            }
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
