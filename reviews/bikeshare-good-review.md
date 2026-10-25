Files read: bikeshare-good/src/main/java/bikeshare/Bike.java, BikeShareService.java, CsvRideRepository.java, Main.java, MemberType.java, Ride.java, RideRepository.java, Station.java.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 2 | Main.java:25-41 | `handle` has logical cohesion because it selects between rent and return based on the command. | Note | Keep the command routing small; move additional command behavior into separate handlers if the menu grows. |
| 2 | 2 | BikeShareService.java:13, 15-16, 42-56 | `BikeShareService` passes a complete `Ride` object to `RideRepository.save`, which is stamp coupling. | Note | Keep the repository interface stable; pass a narrower persistence DTO only if repository implementations need less of the Ride data. |

Findings by check: Check 1: 0. Check 2: 2.
