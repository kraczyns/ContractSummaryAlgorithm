package pl.edu.pwr.contractsummary.segmentation;

import pl.edu.pwr.contractsummary.Header;
import pl.edu.pwr.contractsummary.segmentation.tags.Address;
import pl.edu.pwr.contractsummary.segmentation.tags.Name;
import pl.edu.pwr.contractsummary.segmentation.tags.Period;
import pl.edu.pwr.contractsummary.segmentation.tags.Prize;
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

    private String arrayToString(String[] array) {
        String returnString = "";
        for (String string : array) {
            returnString += string + " ";
        }
        return returnString;
    }

    public void wordsSegmentation(Sentence sentence) {

        Boolean PCM, noDoubleCheck;

            int begin = 0;
            PCM = noDoubleCheck = false;
            Address address = new Address();
            Name name = new Name();
            Period period = new Period();
            Prize prize = new Prize();

            for (int i = 0; i < sentence.content.length(); i++) {
                char sign = sentence.content.charAt(i);
                if (PCM) {
                    String content = sentence.content.substring(begin, i);
                    if (noDoubleCheck ||
                            name.isPotential(content, sign, sentence.getWords().isEmpty()) ||
                            period.isPotential(content, sign) ||
                            prize.isPotential(content, sign) ||
                            address.isPotential(content, sign)) {
                        noDoubleCheck = true;
                        if(name.isValid() && name.isEnd(Character.toString(sign)) ||
                                period.isValid() && period.isEnd(content) ||
                                prize.isValid() && prize.isEnd(content) ||
                                address.isValid() && address.isEnd(content)) {
                                name.clear();
                                period.clear();
                                prize.clear();
                                address.clear();
                                noDoubleCheck = false;
                                sentence.addWord(content);
                                begin = i;

                        }
                    } else {
                        sentence.addWord(content.toLowerCase());
                        begin = i;
                    }
                    PCM = false;
                }
                if (sign == ' ') {
                    PCM = true;
                }
            }
                String content = sentence.content.substring(begin, sentence.content.length() - 1).trim();
                sentence.addWord(content);
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
