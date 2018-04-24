package pl.edu.pwr.contractsummary.segmentation;

import morfologik.stemming.polish.PolishStemmer;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class Word {

    String content;
    Tag tag;

    public Tag getTag() {
        return this.tag;
    }

    public String getContent() { return this.content; }

    public Word(String c) {
        this.content = useMorfologik(deleteUnnecessarySigns(c));
        this.tag = selectTag();
        if (!tag.equals(Tag.address) && !tag.equals(Tag.date)) {
            content = content.replaceAll("\\.", "");
        }

    }

    private String deleteUnnecessarySigns(String string) {
        return string.trim().replaceAll(",", "").replaceAll(":", "");
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
        if (text.contains(".") && Utils.isStringContainingDigits(text)) {
            if(Utils.isDate(text)) {
                return Tag.date;
            } else if(Utils.isAddress(text)) {
                return Tag.address;
            } else if (Utils.isOrdinalNumber(text)){
                return Tag.ordinalNumber;
            }
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
            } else if(!Utils.isOnTheList(text, Constants.IGNORE_NAME)) {
                return Tag.otherName;
            }
        }
        return Tag.text;
    }

    public static List<String> wordsToString(List<Word> words) {
        return words.stream().map(x -> x.content).collect(Collectors.toList());
    }
}
