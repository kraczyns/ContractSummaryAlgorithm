package pl.edu.pwr.contractsummary.segmentation.tags;

import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class Address implements ITextTag {

    String value = null;
    Boolean wrongAssumption = false;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (null == value) {
            if ((content.contains(".") ||
                    Utils.isOnTheList(Word.useMorfologik(content.trim().toLowerCase().replaceAll("\\.", "")), Constants.ADDRESS)) &&
                    Utils.isUpperCase(sign)) {
                content = content.replaceAll("\\.", "");
                if (Utils.isOnTheList(Word.useMorfologik(content.trim().toLowerCase()), Constants.ADDRESS)) {
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
    public Boolean isEnd(String content, char sign) {
        String[] parts = content.split(" ");
        if (Utils.isStringContainingDigitsAndSigns(parts[parts.length - 1])) {
            value = content;
            return true;
        }
        return false;
    }

    @Override
    public String getValue() {
        String[] parts = value.split(" ");
        if (parts[0].toLowerCase().contains("u")) {
            parts[0] = "ul.";
        } else if (parts[0].toLowerCase().contains("p")) {
            parts[0] = "pl.";
        } else if (parts[0].toLowerCase().contains("a")) {
            parts[0] = "al.";
        }
        value = parts[0];
        for (int i = 1; i < parts.length; i++) {
            value += " " + parts[i];
        }
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

