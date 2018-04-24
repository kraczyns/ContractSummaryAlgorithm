package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class Period implements ITags {

    String value = null;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (null == value) {
            if ((Utils.isStringContainingOnlyDigits(content.replaceAll(" ", "")) && (sign == 'm' || sign == 'd' || sign == 't'))) {
                value = content;
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public Boolean isEnd(String content) {
        String[] parts = content.split(" ");
        if (Utils.isOnTheList(Word.useMorfologik(parts[parts.length - 1]), Constants.PERIOD)) {
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
