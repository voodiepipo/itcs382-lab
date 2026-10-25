Files read: bikeshare-poor/src/main/java/bikeshare/Main.java.

| No. | Check | Code location (file:line) | Finding | Severity | Suggested fix |
| --- | --- | --- | --- | --- | --- |
| 1 | 2 | Main.java:18-43 | `main` has logical cohesion because it selects different rent and return behaviors based on the command. | Note | Keep command dispatch in a small command-line controller, or route each command to a dedicated handler. |
| 2 | 2 | Main.java:13-103 | `Main` represents several responsibilities: command handling, station inventory, rentals, fee calculation, and CSV persistence, indicating weak class cohesion. | Should fix | Split station/rental rules and persistence into dedicated classes. |
| 3 | 2 | Main.java:14-16 | The bike inventory, rentals, and file path are public static fields, exposing shared state and allowing external code to modify it directly. | Should fix | Make fields private and expose operations instead of global data. |
| 4 | 2 | Main.java:92-94 | `calculateFee` uses a boolean flag to choose the fee rule, which is control coupling. | Should fix | Use a member-type enum with its own fee rule. |
| 5 | 2 | Main.java:62-66, 84 | The integer member-type codes rely on a shared convention (`0` student, `1` staff) across rent and return logic. | Should fix | Represent member types with an enum. |
| 6 | 2 | Main.java:71, 80, 84, 86-89 | Rental data is stored in a `String[]`; each use depends on the same field positions (bike, type code, origin), which is connascence of position. | Should fix | Use a named Rental class or record. |

Findings by check: Check 1: 0. Check 2: 6.
