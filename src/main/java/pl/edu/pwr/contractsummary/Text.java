package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.Sentence;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Text {

    String text;
    List<Sentence> sentences;
    List<List<Word>> words;

    public List<Sentence> getSentences() {
        return this.sentences;
    }

    public List<List<Word>> getWords() {
        return this.words;
    }

    public Text(String text) {
        this.sentences = new ArrayList<Sentence>();
        this.words = new ArrayList<List<Word>>();
        this.text = text.replaceAll("\\r|\\n|\\t", " ").replaceAll("\\s+", " ");
        sentencesSegmentation();
        wordsSegmentation();
    }

    public Text() {}

    public void wordsSegmentation() {

        Boolean PCM, PA, PALP;

        for (Sentence sentence: sentences) {
            int begin = 0;
            PCM = PA = PALP = false;
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
                                tmp.add(new Word(content));
                                begin = i;
                            } else {
                                PA = true;
                            }
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
            words.add(tmp);
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
        String[] parts = content.split(" ");
        if (isOnTheList(parts[parts.length - 1], Constants.SHORTCUTS_NOT_ENDING_SENTENCE)) {
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
