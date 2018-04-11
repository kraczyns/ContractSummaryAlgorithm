package pl.edu.pwr.contractsummary;

import morfologik.stemming.IStemmer;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

    }

    private String deleteUnnecessarySigns(String string) {
        return string.trim().replaceAll(",", "");
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
                    stringAfter += Utils.capitalizeFirstLetter(morfologik[0]) + " ";
                }
            } else {
                stringAfter += morfologik[0] + " ";
            }
        }
        return stringAfter.trim();
    }

    Tag selectTag() {
        String text = this.content;
        if (text.contains(".")) {
            if(Utils.isDate(text)) {
                return Tag.date;
            } else if(Utils.isAddress(text)) {
                return Tag.address;
            }
        } else if (Utils.isPrize(text)) {
            return Tag.prize;
        } else if (Utils.arePartsUpperCase(text)) {
            if (Utils.isFirstNameLastName(text)) {
                return Tag.firstNameLastName;
            } else if (Utils.isCity(text)) {
                return Tag.city;
            }
                return Tag.otherName;
        }
        return Tag.text;
    }

    public static List<String> wordsToString(List<Word> words) {
        return words.stream().map(x -> x.content).collect(Collectors.toList());
    }
}
