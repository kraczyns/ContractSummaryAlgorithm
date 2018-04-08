package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.Sentence;
import pl.edu.pwr.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Text {

    String text;

    public Text(String text) {
        this.text = text.replaceAll("\\r|\\n|\\t", " ").replaceAll("\\s+", " ");
    }

    public Text() {}

    public List<Sentence> segmentation() {

        List<Sentence> sentences = new ArrayList<Sentence>();
        Boolean PCM = false;

        int begin = 0;

        for (int i = 0; i < text.length(); i++) {
            char sign = text.charAt(i);
            if (sign != ' ') {
                if (PCM) {
                    if (isCM(sign)) {
                        String content = text.substring(begin, i);
                        if (isCMshortcut(content, sign)) {
                            sentences.add(new Sentence(content.trim()));
                            begin = i;
                        }
                        PCM = false;
                    }
                    else {
                        PCM = false;
                    }
                }

                if (isPCM(sign)) {
                    PCM = true;
                }
            }
        }

        if (PCM) {
            String content = text.substring(begin, text.length());
            sentences.add(new Sentence(content.trim()));
        }

        return sentences;
    }

    private Boolean isPCM(char sign) {
        //znaki, które mogą kończyć zdanie
        if (sign == '.' || sign == '?' || sign == '!') {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isCM(char sign) {
        //znaki przystankowe
        if (sign == ',' || sign == '.') {
            return false;
        }
        if (Character.isDigit(sign)) {
            return false;
        }
        if (!isUpperCase(sign)) {
            return false;
        }
        return true;
    }

    private Boolean isUpperCase(char sign) {
        if (String.valueOf(sign).toLowerCase().equals(String.valueOf(sign))) {
            return false;
        }
        else return true;
    }

    private Boolean isCMshortcut(String content, char sign) {
        String[] parts = content.split(" ");
        if (isShortcut(parts[parts.length - 1], Constants.SHORTCUTS_NOT_ENDING_SENTENCE)) {
            return false;
        }
        else if (isShortcut(parts[parts.length - 1], Constants.SHORTCUTS)) {
            return true;
        }

        return true;
    }

    private Boolean isShortcut(String PS, String[] shortcuts) {
        for (String shortcut : shortcuts) {
            if (areStringsSame(shortcut, PS)) {
                return true;
            }
        }
        return false;
    }

    private Boolean areStringsSame( String a, String b) {
        if (a.toLowerCase().equals(b.toLowerCase())) {
            return true;
        } else { return false; }
    }
}
