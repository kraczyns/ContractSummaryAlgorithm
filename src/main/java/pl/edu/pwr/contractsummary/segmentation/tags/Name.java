package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class Name implements ITags {

    String value = null;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (Utils.isUpperCase(content.charAt(0)) && Utils.isUpperCase(sign)) {
            if (isFirst[0]) {
                String[] parts = content.split(" ");
                if (Utils.isOnTheList(parts[0], Constants.FIRSTNAMES)) {
                    value = content;
                    return true;
                }
            }
            else  {
                if (!Utils.isOnTheList(Word.useMorfologik(content.trim().toLowerCase()), Constants.IGNORE_NAME)) {
                    value = content;
                    return true;
                }
            }
        }
        else if (Utils.isUpperCase(content.charAt(0)) && isFirst[0]) {
                if (Utils.isOnTheList(content.split(" ")[0], Constants.FIRSTNAMES)) {
                    value = content;
                    return true;
                }

        } else if (Utils.isUpperCase(content.charAt(0))) {
            value = content;
            return true;
        }

        return false;
    }

    @Override
    public Boolean isEnd(String content) {
        String[] parts = content.split(" ");
        if (!Utils.isUpperCase(parts[parts.length - 1].charAt(0))) {
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
