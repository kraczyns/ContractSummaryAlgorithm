package pl.edu.pwr.contractsummary.types;

public enum HourlyDimension {
    fulltime("pełny etat"),
    parttime("część etatu");

    private String value;

    HourlyDimension(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
