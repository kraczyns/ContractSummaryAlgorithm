package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Word;

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
        this.placeDate = null != args[0] ? args[0].replaceAll("r\\.", "").trim() : null;
        this.sideOne = null != args[1] ? args[1] : null;
        this.sideTwo = null != args[2] ? args[2] : null;
    }

    public String extractDate() {
        String[] pd = placeDate.replaceAll(",","").split(" ");
        Word first = new Word(pd[0]);
        Word second = new Word(pd[1]);
        if (first.getTag() == Tag.date) {
            return first.getContent();
        } else {
            return second.getContent();
        }
    }


    public String extractPlace() {
        String[] pd = placeDate.replaceAll(",","").split(" ");
        Word first = new Word(pd[0]);
        Word second = new Word(pd[1]);
        if (first.getTag() == Tag.city) {
            return first.getContent();
        } else {
            return second.getContent();
        }
    }
}
