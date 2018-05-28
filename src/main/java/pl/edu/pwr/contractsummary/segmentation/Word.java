package pl.edu.pwr.contractsummary.segmentation;

import morfologik.stemming.polish.PolishStemmer;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class Word {

    String content;
    Tag tag;
    String morfologikOutput;

    public String getMorfologikOutput() {
        return morfologikOutput;
    }
    public Tag getTag() {
        return this.tag;
    }

    public String getContent() { return this.content; }

    public Word(String c) {
        morfologik(deleteUnnecessarySigns(c));
        this.tag = selectTag();
        if (!tag.equals(Tag.address) && !tag.equals(Tag.date)) {
            content = content.replaceAll("\\.|:", "");
        }
    }

    private String deleteUnnecessarySigns(String string) {
        return string.trim().replaceAll(",", "").replaceAll("\\(", "")
                .replaceAll("\\)", "").replaceAll("\"|„|”", "").replaceAll("\\*","");
    }

    public void morfologik(String string) {
        String stringAfter = "";
        PolishStemmer s = new PolishStemmer();
        String[] parts = string.split(" ");
            for (String part : parts) {
                String[] morfologik = Utils.stem(s, part);
                if (morfologik.length == 0) {
                    morfologik = Utils.stem(s, part.toLowerCase());
                    if (morfologik.length == 0) {
                        stringAfter += part + " ";
                    } else {
                        this.morfologikOutput = morfologik[1];
                        if (morfologik[1].contains("verb")) {
                            stringAfter += morfologik[0].toLowerCase() + " ";
                        } else {
                            stringAfter += Utils.capitalizeFirstLetter(morfologik[0]) + " ";
                        }
                    }
                } else {
                    this.morfologikOutput = morfologik[1];
                    if (morfologik[1].contains("verb")) {
                        stringAfter += morfologik[0].toLowerCase() + " ";
                    } else {
                        stringAfter += morfologik[0] + " ";
                    }
                }
            }
        if (Utils.isAddress(stringAfter) && stringAfter.contains("ul")) {
            String[] addressParts = stringAfter.split(" ");
            if (addressParts.length > 1) {
                if (addressParts[1].charAt(addressParts[1].length() - 1) == 'y') {
                    String sub = addressParts[1].substring(0, addressParts[1].length() - 1);
                    stringAfter = addressParts[0] + " " + sub + "a " + addressParts[2];
                }
            }
        }
        this.content = stringAfter.trim();
    }

    public static String useMorfologik(String string) {
        String stringAfter = "";
        PolishStemmer s = new PolishStemmer();
        String[] parts = string.split(" ");
        for (String part : parts) {
            String[] morfologik = Utils.stem(s, part);
            if (morfologik.length == 0) {
                morfologik = Utils.stem(s, part.toLowerCase());
                if (morfologik.length == 0) {
                    stringAfter += part + " ";
                } else {
                    if (morfologik[1].contains("verb")) {
                        stringAfter += morfologik[0].toLowerCase() + " ";
                    } else {
                        stringAfter += Utils.capitalizeFirstLetter(morfologik[0]) + " ";
                    }
                }
            } else {
                if (morfologik[1].contains("verb")) {
                    stringAfter += morfologik[0].toLowerCase() + " ";
                } else {
                    stringAfter += morfologik[0] + " ";
                }

            }
        }
        return stringAfter.trim();
    }

    Tag selectTag() {
        String text = this.content;
        if (text.contains(".") && Utils.isStringContainingDigits(text) || Utils.isAddress(text) ) {
            if(Utils.isDate(text)) {
                if (text.charAt(text.length()-1) == '.') {
                    content = content.substring(0, content.length() - 1);
                }
                return Tag.date;
            } else if(Utils.isAddress(text)) {
                return Tag.address;
            } else if (Utils.isOrdinalNumber(text)){
                return Tag.ordinalNumber;
            }
        } else if(text.contains(":") && (Utils.areStringsSame(text.replaceAll(":",""),"na") || Utils.areStringsSame(text.replaceAll(":",""),"postać") ||
                Utils.areStringsSame(text.replaceAll(":",""),"postaci"))) {
            return Tag.descMark;
        } else if (Utils.isID(text)) {
            return Tag.id;
        } else if (Utils.isPrize(text)) {
            return Tag.prize;
        }
        else if (Utils.isPeriod(text)) {
                return Tag.period;
            }
        else if (Utils.arePartsUpperCase(text)) {
            if (Utils.isFirstNameLastName(text)) {
                return Tag.firstNameLastName;
            } else if (Utils.isCity(text)) {
                return Tag.city;
            } else if(!Utils.isOnTheList(text.toLowerCase(), Constants.IGNORE_NAME)) {
                return Tag.otherName;
            } else {
                content = content.toLowerCase();
            }
        }
        return Tag.text;
    }

    public static List<String> wordsToString(List<Word> words) {
        return words.stream().map(x -> x.content).collect(Collectors.toList());
    }
}
