# Lab Report, Code Quality and Design Quality

Repository URL: https://github.com/voodiepipo/itcs382-lab

## Tools

| Item | Version or name |
| --- | --- |
| JDK | `java -version`: 24.0.2; Maven runtime: OpenJDK 27.0.1.0.3 |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.92.1 |
| SonarQube for IDE | 4.29.0 installed; analysis could not be triggered |
| AI tool and exact model | OpenAI Codex, GPT-6 (exact runtime variant not shown in this interface) |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 49843f6

Table 1. SonarQube issues in the starter code

SonarQube for IDE 4.29.0 was installed and visible in VS Code. The command `SonarQube: Connect to SonarQube Server` failed with `SonarLint.ConnectToSonarQube not found`, and `SonarQube: Enable Automatic Analysis` did not appear in the Command Palette. Problems showed only Java extension warnings, so no SonarQube analysis results could be collected.

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| — | — | — | No SonarQube issues could be confirmed because analysis could not be triggered. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 2026-10-25 |
| AI tool and exact model | OpenAI Codex, GPT-6 (the exact runtime variant is not shown in this interface) |
| Commit reviewed | 49843f6 |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Opened the repository folder and read the four Java source files |
| Output file in the reviews folder | reviews/parcelpoint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:8 | Scanner wrapping System.in is not closed | AI | Resource leak | yes; no close call or try-with-resources |
| 2 | Main.java:11-17 | Missing or invalid command arguments can cause runtime exceptions | AI | Input validation | yes; token indexes and numeric conversion are unchecked |
| 3 | ParcelService.java:6 | Unused Date import | AI | Unused import | yes; no use of Date |
| 4 | ParcelService.java:12 | maxParcels is unused and is not enforced | AI | Unused field | yes; it is only declared |
| 5 | ParcelService.java:42 | String codes compared with == | AI | String comparison bug | yes; separate input strings with equal contents need not share identity |
| 6 | ParcelService.java:76-79 | FileWriter is never closed; CSV output may not flush | AI | Resource leak | yes; writer is not closed (the observed CSV was empty) |
| 7 | ParcelService.java:80-81 | IOException is swallowed by an empty catch | AI | Exception handling | yes; save failures are silently ignored |
| 8 | ParcelService.java:64-70 | Boolean flag selects different listing behavior (logical cohesion) | AI | Logical cohesion | yes; staff and waiting-list output are different behaviors |
| 9 | ParcelService.java:9-12, 24-82 | Class combines storage, locker, fee, notification, output, and persistence responsibilities | AI | Weak class cohesion | yes; these responsibilities are implemented in one class |
| 10 | Main.java:13-14 | Whole Parcel object passed to storeParcel (stamp coupling) | AI | Stamp coupling | yes; the complete object is passed across the class boundary |
| 11 | ParcelService.java:26-28 | ParcelService coordinates through Locker objects and their operations (stamp coupling) | AI | Stamp coupling | yes; service depends on the Locker object interface |
| 12 | Main.java:22 | Main reaches through service.getLockers() to operate on Locker objects | AI | Strong coupling / reach-through | yes; Main depends on the service's collection and Locker API |
| 13 | ParcelService.java:28, 44, 67 | ParcelService directly reads/writes Parcel.status | AI | Content coupling | yes; the service accesses another class's field directly |
| 14 | Parcel.java:7 | status is a public field | AI | Encapsulation | yes; callers can change it directly |
| 15 | ParcelService.java:20-22 | getLockers returns the mutable internal collection | AI | Encapsulation | yes; callers can add or remove lockers |
| 16 | Parcel.java:13; ParcelService.java:28, 44, 68 | Status strings must match across classes (connascence of convention) | AI | Connascence of convention | yes; inconsistent spelling would break comparisons |
| 17 | Locker.java:5; Parcel.java:6; ParcelService.java:15-17, 26, 56-60 | Integer size codes must keep the same meanings across classes (connascence of type) | AI | Connascence of type | yes; code assumes 1, 2, and 3 have shared meanings |

2.1 Two AI-only problems: the String identity comparison at ParcelService.java:42, and the unclosed FileWriter at ParcelService.java:76-79.

2.2 No SonarQube-only problem could be confirmed. The analysis command was missing, the server connection command failed, and Problems showed only Java extension warnings.

2.3 I did not find an AI finding that was wrong. I checked each cited line in the four source files; for example, `pickUp` compares input strings with `==`, while `saveToFile` never closes its writer.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| — | None shown | The Problems panel showed no SonarQube findings, so there was no Table 1 issue to fix. |

Number of SonarQube issues that remain after your fixes: Not measurable; the Problems panel showed no SonarQube findings before the fixes.

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Connascence of type from integer parcel-size codes | Replaced size codes with the `ParcelSize` enum. | 047511e |
| Public field / content coupling for parcel status | Made status private and represented it with the `ParcelStatus` enum and state-changing methods. | 7c8ecd0 |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text
Commands: store <code> <studentId> <size 1-3>, pickup <code> <days>, list, staff, quit
SMS to 6501: parcel P1 is in locker 2
Locker 2
Fee: 30.0
P1 6501 PICKED_UP
Free lockers: 3

parcels.csv:
P1,PICKED_UP
```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | 2026-10-25 | 2026-10-25 |
| AI tool and exact model | OpenAI Codex, GPT-6 (exact runtime variant not shown in this interface) | OpenAI Codex, GPT-6 (exact runtime variant not shown in this interface) |
| Prompt (B or C as given, or your changed prompt in full) | Prompt B as given, with the BikeShare specification inserted | Prompt C as given, with the BikeShare specification inserted |
| Builds with mvn package (yes or no) | yes | yes |
| Number of classes, interfaces, enums and records | 1 | 8 |
| SonarQube issues, total | Not available; analysis command unavailable | Not available; analysis command unavailable |
| SonarQube issues, by rule | Not available; no analysis result captured | Not available; no analysis result captured |
| AI review findings in Check 1 | 0 | 0 |
| AI review findings in Check 2 | 6 | 2 |
| Review output file in the reviews folder | reviews/bikeshare-poor-review.md | reviews/bikeshare-good-review.md |

4.1 I could not verify this. The SonarQube analysis command was unavailable, so no rule or message was produced to quote for bikeshare-poor.

4.2 Yes. The AI review found that bikeshare-poor exposes shared public static data, uses integer member-type codes, and passes a boolean flag to choose the fee rule. Those issues do not appear in the bikeshare-good review, which uses private state and a `MemberType` enum.

4.3 I would extend bikeshare-good. `MemberType` holds the fee rate, so adding a visitor type with a 4-baht rate is localized to the enum and does not require changing the shared integer codes and boolean fee logic in bikeshare-poor.

## Task 5. Reflection

5.1 SonarQube checks code against language rules that can flag issues such as resource leaks, unused code, and suspicious comparisons. It does not decide whether the overall design fits the course goals; for example, choosing whether `ParcelService` has too many responsibilities needs design reasoning. In this lab I could not collect SonarQube results because its analysis command did not appear.

5.2 Do not accept an AI finding without checking the cited code and whether its reasoning fits the design. For example, the review called passing a `Ride` to `RideRepository.save` stamp coupling; that is a real coupling category, but it can be a reasonable interface when the repository needs the whole ride to persist it.

5.3 In Project Phase 2 I will represent fixed choices such as member types and parcel sizes with enums instead of integer codes, and keep persistence separate from business rules. I will build and run example commands after each change, then verify the analyzer's actual results and check each finding against the source.

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| OpenAI Codex, GPT-6 (exact runtime variant not shown in this interface) | Reviewed starter code, generated and reviewed both BikeShare designs, and implemented requested fixes | `reviews/*.md`, `LAB_REPORT.md`, ParcelPoint Java source files, and both BikeShare Maven projects | Ran `mvn package` and the sample commands after each code fix; all builds succeeded and the sample output/CSV were checked. |
