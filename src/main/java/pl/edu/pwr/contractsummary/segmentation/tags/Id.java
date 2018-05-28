package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.utils.Utils;

public class Id implements  ITextTag {

    String value = null;
    Boolean wrongAssumption = false;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (Utils.isAllUpperCase(content) && Character.isDigit(sign)) {
            value = content;
            return true;
        }
        return false;
    }

    @Override
    public Boolean isEnd(String content, char sign) {
        content = content.replaceAll(",|\\.", "");
        if (Character.isUpperCase(content.charAt(0)) && Character.isDigit(content.charAt(content.length() - 1))) {
            value = content;
            return true;
        }
        return false;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void clear() {
        value = null;
    }

    @Override
    public Boolean back() {
        return wrongAssumption;
    }
}
