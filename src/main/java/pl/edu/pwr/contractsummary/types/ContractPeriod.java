package pl.edu.pwr.contractsummary.types;

public enum ContractPeriod {
    definitePeriod("czas określony"),
    indefinitePeriod("czas nieokreślony"),
    probationaryPeriod("okres próbny"),
    other("inny");

    private String value;

    ContractPeriod(String value) {
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
