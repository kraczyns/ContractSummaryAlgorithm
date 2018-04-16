package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.Sentence;
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
        wordsSegmentation();
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

    private String arrayToString(String[] array) {
        String returnString = "";
        for (String string : array) {
            returnString += string + " ";
        }
        return returnString;
    }
    public void wordsSegmentation() {

        Boolean PCM, PA, PALP, PN;

        for (Sentence sentence: sentences) {
            int begin = 0;
            PCM = PA = PALP = PN = false;
            List<Word> tmp = new ArrayList<Word>();
            for (int i = 0; i < sentence.content.length(); i++) {
                char sign = sentence.content.charAt(i);
                if (PCM) {
                    String content = sentence.content.substring(begin, i);
                    if (PA) {
                        if(PALP) {
                            tmp.add(new Word(content));
                            begin = i;
                            PALP = false;
                            PA = false;
                        }
                        if(isPotentialAddressLastPart(sign)) {
                            PALP = true;
                        }
                    } else {
                        if (!isPotentialName(content, sign, tmp.isEmpty()) && !isPrize(content, sign)) {
                            if (!isPotentialAddress(content, sign)) {
                                if (begin == 0 && !PN && !Utils.isOnTheList(content.trim(), Constants.FIRSTNAMES)) {
                                    tmp.add(new Word(content.toLowerCase()));
                                } else if (Constants.DASH.indexOf(content.trim()) == -1 ) {
                                        tmp.add(new Word(content));
                                 }
                                begin = i;
                                PN = false;
                            } else {
                                PA = true;
                            }

                        } else {
                            PN = true;
                        }
                    }
                    PCM = false;
                }
                if (sign == ' ') {
                    PCM = true;
                }
            }
                String content = sentence.content.substring(begin, sentence.content.length() - 1).trim();
                tmp.add(new Word(content));
                sentence.setWords(tmp);
        }
    }

    public void sentencesSegmentation() {

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
    }

    private Boolean isPrize(String content, char sign) {
        if ((Utils.isStringContainingOnlyDigits(content.replaceAll(" ", "")) && sign == 'z') || (Utils.isStringContainingOnlyDigits(content.trim()) && Character.isDigit(sign))) {
            return true;
        }
        return false;
    }

    //potencjalna nazwa
    private Boolean isPotentialName(String string, char sign, boolean isFirst) {
        if (isUpperCase(string.charAt(0)) && isUpperCase(sign)) {
            if (isFirst && string.split(" ").length == 1) {
                if (Utils.isOnTheList(string.trim(), Constants.FIRSTNAMES)) {
                    return true;
                }
            }
            else if (Utils.isOnTheList(Word.useMorfologik(string.trim().toLowerCase()), Constants.IGNORE_NAME)) {
                return false;
            }
            else{
                return true;
            }
        }
        return false;
    }

    //potencjalny adres
    private Boolean isPotentialAddress(String string, char sign) {
        if(string.contains(".") && isUpperCase(sign)) {
            string = string.replaceAll("\\.", "");
            if (Utils.isOnTheList(string.trim(), Constants.ADDRESS)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    //koniec adresu
    private Boolean isPotentialAddressLastPart(char sign) {
        if (Character.isDigit(sign)) {
            return true;
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
