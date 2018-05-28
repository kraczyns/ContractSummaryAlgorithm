package pl.edu.pwr.contractsummary.segmentation;

import pl.edu.pwr.contractsummary.Header;
import pl.edu.pwr.contractsummary.segmentation.tags.*;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Text {

    String text;
    List<Sentence> sentences;
    Header header;

    public List<Sentence> getSentences() {
        return this.sentences;
    }

    public String getText() {
        return text;
    }

    public Header getHeader() { return this.header;  }

    public Text(String text) {
        this.sentences = new ArrayList<Sentence>();
        this.text = text.replaceAll("\\r|\\n|\\t", " ").replaceAll("\\s+", " ").replaceAll("\\.\\.", "").replaceAll("\uFEFF", "");
        extractHeaders();
        sentencesSegmentation();
        for (Sentence sentence : sentences) {
            wordsSegmentation(sentence);
        }

    }

    public Text() {}

    private void extractHeaders() {

        // 0 - datePlace, 1 - sideFirst, 2 - sideSecond
        int side = 0;
        // headers[0] = datePlaceHeaders, headers[1] = sideFirstHeaders, headers[2] = sideSceondHeaders
        String[] headers = {"", "", ""};
        int index = 0;

        for (String part : text.split(" ")) {

            String _part = part.replaceAll("\\.", "").replaceAll("\\,", "").trim();

            //Sprawdzenie czy zaczęła się już umowa właściwa.
            if (isContractTitle(_part)) {
                // Scenariuz 1 - nie ma headerów
                if(headers[0].isEmpty() && headers[2].isEmpty()) {
                    headers[1] = "";
                }
                // Scenariusz 2 - nie ma daty oraz miasta, są dane osobowe
                else if (headers[0].isEmpty() && (!headers[1].isEmpty() && !headers[2].isEmpty())) {
                    index = headers[1].length() + headers[2].length();
                }
                // Scenariusz 3 - jest tylko data z miastem, nie ma danych osobowych
                else if (!headers[0].isEmpty() && headers[2].isEmpty()) {
                    index = headers[0].length();
                    headers[1] = "";
                    // Scenariusz 4 - wszystkie headery obecne
                } else if(!headers[0].isEmpty() && !headers[1].isEmpty() && !headers[2].isEmpty()) {
                    index = headers[0].length() + headers[1].length() + headers[2].length();
                }
                break;
            }
            /*
            Sprawdzenie czy miasto
            TODO :
            sprawdzić czy morfologik nie określa czy coś jest miastem - nie będzie trzeba przeszukiwać po liście - sprawdzić, które rozwiązanie szybsze
             */
            else if (Utils.isCity(_part)) {
                    headers[side] += part + " ";
                    side = 0;
            }
            // Sprawdzenie czy data
                else if (Utils.isDate(part) || Utils.areStringsSame("r.", part.trim())) {
                    headers[0] += part + " ";
                }
            // Sprawdzenie czy nazwa/imię -> początek danych jedenej strony
                else if (!part.equals(part.toLowerCase()) && side == 0 && headers[2].isEmpty()) {
                    side = headers[1].isEmpty() ? 1 : 2;
                    headers[side] += part + " ";
                }
                // Kontynuacja dopisywania do danych osobowych - adresów itd., dopóki nie wystąpi miasto, ono powinno być ostatnie
             else if (side != 0) {
                    headers[side] += part + " ";
            }
        }
        header = new Header(headers[0].trim(), headers[1].trim(), headers[2].trim());
        text = text.substring(index);
    }

    private Boolean isContractTitle(String string) {
        if (Utils.areStringsSame(Word.useMorfologik(string), "umowa") || Utils.areStringsSame(Word.useMorfologik(string), "umowy")) {
            return true;
        }
        return false;
    }

    public void wordsSegmentation(Sentence sentence) {

        Boolean PCM = false;
        int begin = 0;
        ITextTag textTag = null;
        TextTagImpl textTagImpl = new TextTagImpl();

            for (int i = 0; i < sentence.content.length(); i++) {
                char sign = sentence.content.charAt(i);
                if (PCM) {
                    String content = sentence.content.substring(begin, i);
                    textTag = textTagImpl.getTag(content.trim(), sign, sentence.getWords().isEmpty());
                    if (null == textTag) {
                        sentence.addWord(content.toLowerCase());
                        begin = i;
                    } else if (textTag.isEnd(content.trim(), sign)){
                            if (textTag.back()) {
                                String[] parts = content.split(" ");
                                for (String part : parts) {
                                    sentence.addWord(part.toLowerCase());
                                }
                            } else {
                                sentence.addWord(textTag.getValue());
                            }
                            begin = i;
                            textTagImpl.clear();
                            textTag.clear();
                    }
                    PCM = false;
                }
                if (Character.isWhitespace(sign)) {
                    PCM = true;
                }
            }
            if (sentence.content.length() > 0) {
                String content = sentence.content.substring(begin, sentence.content.length() - 1);
                if (null != textTag && textTag.isEnd(content, ' ') && textTag.back()) {
                    String[] parts = content.split(" ");
                    for (String part : parts) {
                        sentence.addWord(part.toLowerCase());
                    }
                } else {
                    sentence.addWord(content);
                }
            }
    }

    public void sentencesSegmentation() {

        Boolean PCM = false;
        Boolean PON = false; //Potential Ordinal Number
        int begin = 0;

        for (int i = 0; i < text.length(); i++) {
            char sign = text.charAt(i);
            if (sign != ' ') {
                if (PCM) {
                    if (isCM(sign)) {
                        String content = text.substring(begin, i);
                        if (!isCMDate(content, sign) && isCMshortcut(content, sign)) {
                            sentences.add(new Sentence(content.trim()));
                            begin = i;
                        }
                    }
                        PCM = false;
                } else if(PON) {
                     if (isON(sign)) {
                        String content = text.substring(begin, i);
                        sentences.add(new Sentence(content.trim()));
                        begin = i;
                    }
                     PON = false;
                }

                if (isPCM(sign)) {
                    PCM = true;
                } else if (isPON(sign)) {
                    PON = true;
                }
            }
        }

        if (PCM) {
            String content = text.substring(begin, text.length());
            if (!content.equals("")) {
                sentences.add(new Sentence(content.trim()));
            }
        }
    }

    private Boolean isPON(char sign) {
        if (sign == ':') {
            return true;
        } else {
            return false;
        }
    }

    private Boolean isON(char sign) {
        if (sign == '§') {
            return true;
        } else {
            return false;
        }
    }
    private boolean isCMDate(String content, char sign) {
        String[] parts = content.split(" ");
        if (parts.length > 0) {
            if (Utils.isStringContainingOnlyDigits(parts[parts.length - 1].replaceAll("\\.",""))) {
                return true;
            }
        }
        return false;
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
        if (sign == '§') {
            return true;
        }
        if (Character.isDigit(sign)) {
            return true;
        }
        if (!Utils.isUpperCase(sign)) {
            return false;
        }
        return true;
    }

    private Boolean isCMshortcut(String content, char sign) {
        char lastSign = content.charAt(content.length() - 2);
        String[] parts = content.split(" ");
        if(Character.isUpperCase(lastSign) && Character.isUpperCase(sign)) {
            return false;
        }
        else if (Utils.isOnTheList(parts[parts.length - 1], Constants.SHORTCUTS_NOT_ENDING_SENTENCE)) {
            return false;
        }
        else if (Utils.isOnTheList(parts[parts.length - 1], Constants.SHORTCUTS)) {
            return true;
        }

        return true;
    }

}
