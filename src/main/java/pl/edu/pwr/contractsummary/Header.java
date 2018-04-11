package pl.edu.pwr.contractsummary;

public class Header {
    String placeDate;
    String sideOne;
    String sideTwo;

    public String getPlaceDate() {
        return placeDate;
    }

    public String getSideOne() {
        return sideOne;
    }

    public String getSideTwo() {
        return sideTwo;
    }

    public Header(String... args) {
        this.placeDate = null != args[0] ? args[0] : null;
        this.sideOne = null != args[1] ? args[1] : null;
        this.sideTwo = null != args[2] ? args[2] : null;
    }
}
