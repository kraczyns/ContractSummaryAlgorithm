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
        this.text = text.replaceAll("\\r|\\n|\\t", " ").replaceAll("\\s+", " ").replaceAll("\\.\\.", "");
        extractHeaders();
        sentencesSegmentation();
        wordsSegmentation();
    }

    public Text() {}

    private void extractHeaders() {
        String[] textParts = this.text.split(" ");
        Boolean isSideFirst = false;
        Boolean isSideSecond = false;
        Boolean isAddress = false;
        String headerDatePlace = "";
        String headerSideFirst = "";
        String headerSideSecond = "";

        for (String part : textParts) {

            part = part.replaceAll("\\.", "").replaceAll("\\,", "").trim();

            if (isContractTitle(part)) {
                if (headerDatePlace.isEmpty()) {
                    headerSideFirst = "";
                    headerSideSecond = "";
                } else if (!headerDatePlace.isEmpty() && headerSideSecond.isEmpty()) {
                    String tmp = arrayToString(textParts);
                    text = tmp.substring(tmp.indexOf(headerSideFirst));
                    headerSideFirst = "";
                } else if(!headerDatePlace.isEmpty() && !headerSideFirst.isEmpty() && !headerSideSecond.isEmpty()) {

                }
                break;
            }
            else if (Utils.isCity(part)) {
                if (isSideFirst) {
                    headerSideFirst += part + " ";
                    isSideFirst = false;
                    isAddress = false;
                }
                else if (isSideSecond){
                    headerSideSecond += part + " ";
                    isSideSecond = false;
                    isAddress = false;
                } else {
                  headerDatePlace += part + " ";
                }
            }
            else if (!isAddress) {
                if (isHeaderDate(part)) {
                    headerDatePlace += part + " ";
                }
                else if (isOnTheList(part, Constants.ADDRESS)) {
                    if (isSideFirst) {
                        headerSideFirst += part + " ";
                    } else {
                        headerSideSecond += part + " ";
                    }

                    isAddress = true;
                }
                else if (!part.equals(part.toLowerCase())) {
                    if (headerSideFirst.isEmpty()) {
                        isSideFirst = true;
                        headerSideFirst += part + " ";
                    }
                    else if(!headerSideFirst.isEmpty() && headerSideSecond.isEmpty() && !isSideFirst) {
                        isSideSecond = true;
                        headerSideSecond += part + " ";
                    }
                    else if(isSideFirst) {
                        headerSideFirst += part + " ";
                    }
                    else if(isSideSecond) {
                        headerSideSecond += part + " ";
                    }
                    else if(!headerSideFirst.isEmpty() && !headerSideSecond.isEmpty()){
                        String tmp = arrayToString(textParts);
                        text = tmp.substring(tmp.indexOf(part));
                        break;
                    }
            }
            }
             else if (isSideFirst || isSideSecond) {
                if(isSideFirst) {
                    headerSideFirst += part + " ";
                } else if (isSideSecond) {
                    headerSideSecond += part + " ";
                }
            }


            /* TODO:
            Funkcje w Utils - czy słowo to imię i czy to może być ulica

            * */
            //czy imię/nazwa
            //czy data
            //czy ulica
            //czy miasto -> jak nic to nie ma headersów -> break return empty headers
        }
        header = new Header(headerDatePlace.trim(), headerSideFirst.trim(), headerSideSecond.trim());
    }

    private Boolean isHeaderDate(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
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
                        }
                        if(isPotentialAddressLastPart(sign)) {
                            PALP = true;
                        }
                    } else {
                        if (!isPotentialName(content, sign, tmp.isEmpty())) {
                            if (!isPotentialAddress(content, sign)) {
                                if (begin ==0 && !PN && !isOnTheList(content.trim(), Constants.FIRSTNAMES)) {
                                    tmp.add(new Word(content.toLowerCase()));
                                } else {
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

    //potencjalna nazwa
    private Boolean isPotentialName(String string, char sign, boolean isFirst) {
        if (isUpperCase(string.charAt(0)) && isUpperCase(sign)) {
            if (isFirst && string.split(" ").length == 1) {
                if (isOnTheList(string.trim(), Constants.FIRSTNAMES)) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    //potencjalny adres
    private Boolean isPotentialAddress(String string, char sign) {
        if(string.contains(".") && isUpperCase(sign)) {
            string = string.replaceAll("\\.", "");
            if (isOnTheList(string.trim(), Constants.ADDRESS)) {
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
        else if (isOnTheList(parts[parts.length - 1], Constants.SHORTCUTS_NOT_ENDING_SENTENCE)) {
            return false;
        }
        else if (isOnTheList(parts[parts.length - 1], Constants.SHORTCUTS)) {
            return true;
        }

        return true;
    }

    private Boolean isOnTheList(String string, String[] list) {
        for (String element : list) {
            if (Utils.areStringsSame(element, string)) {
                return true;
            }
        }
        return false;
    }


}
