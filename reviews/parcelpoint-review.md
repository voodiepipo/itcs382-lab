Files read: Locker.java, Main.java, Parcel.java, ParcelService.java.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 1 | Main.java:8 | The Scanner wrapping System.in is never closed. | Should fix | Close it when the command loop ends, while considering that closing it also closes System.in. |
| 2 | 1 | Main.java:11-17 | The command parser assumes each command has all required tokens; for example, `store` with missing arguments causes an array-index exception, and invalid sizes cause a number-format exception. | Should fix | Validate the command name, token count, and numeric arguments before using them. |
| 3 | 1 | ParcelService.java:6 | `java.util.Date` is imported but never used. | Note | Remove the unused import. |
| 4 | 1 | ParcelService.java:12 | `maxParcels` is never read and does not limit the number of parcels. | Should fix | Remove the field or enforce the intended capacity when storing parcels. |
| 5 | 1 | ParcelService.java:42 | Parcel codes are compared with `==`, which compares String object identity and can fail for equal text entered separately. | Must fix | Compare with `parcel.getCode().equals(code)` (with a null-safe form if needed). |
| 6 | 1 | ParcelService.java:76-79 | The `FileWriter` is not closed, so buffered CSV output may not be flushed; the observed `parcels.csv` was empty. | Must fix | Use try-with-resources for the writer. |
| 7 | 1 | ParcelService.java:80-81 | The IOException catch block is empty, hiding file-write failures. | Should fix | Report or propagate the exception so the caller knows the save failed. |
| 8 | 2 | ParcelService.java:64-70 | `printParcels` has logical cohesion: a boolean selects between two different output behaviors (staff listing or waiting-only listing). | Should fix | Split the staff and waiting-list behavior into separately named methods. |
| 9 | 2 | ParcelService.java:9-12, 24-82 | `ParcelService` combines locker allocation, parcel state, fee calculation, SMS text, console output, and file persistence, indicating weak class cohesion. | Should fix | Move independent output and persistence responsibilities into separate collaborators. |
| 10 | 2 | Main.java:13-14 | `Main` passes a whole `Parcel` record to `ParcelService.storeParcel`, a stamp-coupled interaction. | Note | Pass only the values needed if the service does not need to own the Parcel object. |
| 11 | 2 | ParcelService.java:26-28 | `ParcelService` coordinates with `Locker` through the `Locker` object and its operations, a stamp-coupled interaction. | Note | Keep the locker operations behind a smaller operation that accepts only the required parcel data if this coupling becomes hard to change. |
| 12 | 2 | Main.java:22 | `Main` reaches through `service.getLockers()` to operate on the returned Locker collection, creating strong coupling to ParcelService internals and Locker. | Should fix | Have ParcelService provide a method that returns the free-locker count. |
| 13 | 2 | ParcelService.java:28, 44, 67 | `ParcelService` directly reads and writes the public `Parcel.status` field, which is content coupling. | Should fix | Make the field private and expose behavior or a controlled accessor. |
| 14 | 2 | Parcel.java:7 | `Parcel.status` is public, so any caller can change an object's internal state. | Should fix | Make the field private and provide controlled state-changing methods. |
| 15 | 2 | ParcelService.java:20-22 | `getLockers()` returns the mutable internal collection, allowing callers to add or remove lockers. | Should fix | Return an unmodifiable view or a defensive copy. |
| 16 | 2 | Parcel.java:13; ParcelService.java:28, 44, 68 | Status values such as `STORED` and `PICKED_UP` are repeated string conventions across classes; a spelling change in one place can break behavior. | Should fix | Represent statuses with an enum or shared constants. |
| 17 | 2 | Locker.java:5; Parcel.java:6; ParcelService.java:15-17, 26, 56-60 | Locker and parcel sizes use integer codes whose meanings (1-3) must stay consistent across classes, a type connascence. | Should fix | Use a named size enum or value type instead of bare integer codes. |

Findings by check: Check 1: 7. Check 2: 10.
