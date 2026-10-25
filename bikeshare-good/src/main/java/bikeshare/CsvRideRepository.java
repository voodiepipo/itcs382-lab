package bikeshare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class CsvRideRepository implements RideRepository {
    private final Path file;

    public CsvRideRepository(Path file) {
        this.file = file;
    }

    @Override
    public void save(Ride ride) throws IOException {
        String row = ride.memberId() + "," + ride.bikeId() + "," + ride.startStation()
                + "," + ride.returnStation() + "," + ride.minutes() + "," + ride.fee()
                + System.lineSeparator();
        Files.writeString(file, row, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
