package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class Period implements ITextTag {

    String value = null;
    Boolean wrongAssumption = false;
    Boolean date = false;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (null == value) {
            if ((Utils.isStringContainingOnlyDigits(content.replaceAll(" ", "")) && (sign == 'm' || sign == 'd' || sign == 't' || sign == 's' || sign == 'l' || sign == 'k' || sign == 'c' || sign == 'w'
            || sign == 'p' || sign == 'g'))) {
                value = content;
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public Boolean isEnd(String content, char sign) {
        String[] parts = content.split(" ");
        if (parts.length == 2) {
            if (Utils.isOnTheList(Word.useMorfologik(parts[parts.length - 1]), Constants.PERIOD) || Utils.areStringsSame("m²", parts[parts.length -1])) {
                value = content;
                return true;
            } else if (!Utils.isOnTheList(Word.useMorfologik(parts[1]), Constants.MONTHS))  {
                wrongAssumption = true;
                value = content;
                return true;
            }
        } else if (parts.length == 3) {
            if (Utils.isOnTheList(Word.useMorfologik(parts[1]), Constants.MONTHS) && Utils.isStringContainingOnlyDigits(parts[2])) {
                value = content;
                date = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public String getValue() {
        return date ? new Date().stringToDate(value) : value;
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
