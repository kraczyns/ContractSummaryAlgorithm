package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class Address implements ITags {

    String value = null;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (null == value) {
            if (content.contains(".") && Utils.isUpperCase(sign)) {
                content = content.replaceAll("\\.", "");
                if (Utils.isOnTheList(content.trim(), Constants.ADDRESS)) {
                    value = content;
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public Boolean isEnd(String content) {
        String[] parts = content.split(" ");
        if (Utils.isStringContainingDigitsAndSigns(parts[parts.length - 1])) {
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
}

