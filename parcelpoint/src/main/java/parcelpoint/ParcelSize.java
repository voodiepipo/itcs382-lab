package parcelpoint;

public enum ParcelSize {
    SMALL(1, 10.0),
    MEDIUM(2, 15.0),
    LARGE(3, 20.0);

    private final int code;
    private final double feePerExtraDay;

    ParcelSize(int code, double feePerExtraDay) {
        this.code = code;
        this.feePerExtraDay = feePerExtraDay;
    }

    public double getFeePerExtraDay() {
        return feePerExtraDay;
    }

    public boolean canFit(ParcelSize parcelSize) {
        return code >= parcelSize.code;
    }

    public static ParcelSize fromCode(int code) {
        for (ParcelSize size : values()) {
            if (size.code == code) {
                return size;
            }
        }
        throw new IllegalArgumentException("Size must be between 1 and 3");
    }
}
