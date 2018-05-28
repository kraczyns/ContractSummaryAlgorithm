package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class Name implements ITextTag {

    String value = null;
    Boolean wrongAssumption = false;
    Boolean citationStart = false;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (content.length() != 0) {
            if (Utils.isUpperCase(content.charAt(0)) && Utils.isUpperCase(sign)) {
                if (isFirst[0]) {
                    String[] parts = content.split(" ");
                    if (Utils.isOnTheList(parts[0], Constants.FIRSTNAMES)) {
                        value = content;
                        return true;
                    }
                } else {
                    if (Utils.isAllUpperCase(content) && Character.isUpperCase(sign)) {
                        return false;
                    } else if (!Utils.isOnTheList(Word.useMorfologik(content.trim().toLowerCase().replaceAll(",", "")), Constants.IGNORE_NAME)) {
                        value = content;
                        return true;
                    }
                }
            } else if (Utils.isUpperCase(content.charAt(0)) && isFirst[0]) {
                if (Utils.isOnTheList(content.split(" ")[0], Constants.FIRSTNAMES)) {
                    value = content;
                    return true;
                }

            } else if (Utils.isUpperCase(content.charAt(0))) {
                value = content;
                return true;
            }
            if ((content.contains("„") || content.contains("\"")) && Character.isUpperCase(content.charAt(1))) {
                value = content;
                citationStart = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isEnd(String content, char sign) {
        if (citationStart && (content.contains("”") || content.contains("\""))) {
            value = content;
            return true;
        }
        if (!Utils.isUpperCase(sign) || content.contains(",")) {
            value = content;
            return true;
        } else {
            String[] parts = content.split(" ");
            if (parts.length == 2 && Utils.isOnTheList(Word.useMorfologik(parts[0].trim()), Constants.FIRSTNAMES)) {
                value = content;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getValue() {
        value = value.replaceAll("\"|„|”", "");
        return value;
    }

    @Override
    public void clear() {
        wrongAssumption = false;
        citationStart = false;
        value = null;
    }

    @Override
    public Boolean back() {
        return wrongAssumption;
    }

}
