package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.utils.Utils;

public class Prize implements ITextTag {

    String value = null;
    Boolean wrongAssumption = false;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        if (null == value) {
            //Rozpatrywane przypadki: 1000 zł, 1000 złoty, 1000 (słownie: tysiąc) zł, 1 000 zł
            if ((Utils.isStringContainingOnlyDigits(content.replaceAll(" ", "")) && (sign == 'z' || sign == '(')) || (Utils.isStringContainingOnlyDigits(content.trim()) && Character.isDigit(sign))) {
                value = content;
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public Boolean isEnd(String content, char sign) {
        String[] parts = content.replaceAll("(|)", "").split(" ");
        if (Utils.areStringsSame(parts[parts.length - 1].replaceAll("\\.|,",""), "zł") || Utils.areStringsSame(parts[parts.length - 1].replaceAll("\\.|,",""), "złoty")) {
            value = content;
            return true;
        }
        return false;
    }

    @Override
    public String getValue() {
        String[] parts1 = value.split("\\(");
        if (parts1.length > 1) {
            String[] parts2 = value.split("\\)");
            if (parts2.length > 1) {
                value = parts1[0] + parts2[1];
            }
        }
        String[] parts = value.split(" ");
        value = "";
        for (int i = 0; i < parts.length - 1; i++) {
            value += parts[i];
        }
        value += " ";
        value += parts[parts.length-1];
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
