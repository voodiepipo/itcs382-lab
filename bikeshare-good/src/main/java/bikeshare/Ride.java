package bikeshare;

public record Ride(String memberId, MemberType memberType, Bike bike,
                   String startStation, String returnStation, int minutes) {
    public String bikeId() {
        return bike.id();
    }

    public double fee() {
        return memberType.feeFor(minutes);
    }
}
