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

enum Tag {
    date,
    address,
    prize,
    city,
    firstNameLastName,
    otherName,
    text
}

public class Word {

    String content;
    Tag tag;

    public String getTag() {
        return this.tag.toString();
    }

    public String getContent() { return this.content; }

    public Word(String c) {
        this.content = useMorfologik(deleteUnnecessarySigns(c));
        this.tag = selectTag();

    }

    private String deleteUnnecessarySigns(String string) {
        return string.trim().replaceAll(",", "");
    }

    private String useMorfologik(String string) {
        String stringAfter = "";
        PolishStemmer s = new PolishStemmer();
        String[] parts = string.split(" ");
        for (String part : parts) {
            String[] morfologik = stem(s, part);
            if (morfologik.length == 0) {
                stringAfter += part + " ";
            } else {
                stringAfter += morfologik[0] + " ";
            }
        }
        return stringAfter.trim();
    }

    public static String asString(CharSequence s) {
        if (s == null)
            return null;
        return s.toString();
    }

   public static String[] stem(IStemmer s, String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (WordData wd : s.lookup(word)) {
            result.add(asString(wd.getStem()));
            result.add(asString(wd.getTag()));
        }
        return result.toArray(new String[result.size()]);
    }

    Tag selectTag() {
        String text = this.content;
        if (text.contains(".")) {
            if(isDate(text)) {
                return Tag.date;
            } else if(isAddress(text)) {
                return Tag.address;
            }
        } else if (isPrize(text)) {
            return Tag.prize;
        } else if (Utils.arePartsUpperCase(text)) {
            if (isFirstNameLastName(text)) {
                return Tag.firstNameLastName;
            } else if (isCity(text)) {
                return Tag.city;
            }
            return Tag.otherName;
        }
        return Tag.text;
    }

    Boolean isDate(String string) {
            String[] parts = string.split("\\.");
            for (String part : parts) {
                if(!Utils.isStringContainingOnlyDigits(part)) {
                    return false;
                }
            }
        return true;
    }

    Boolean isFirstNameLastName(String string) {
        String[] parts = string.split(" ");
        if(parts.length == 2) {
            for (String name : Constants.FIRSTNAMES) {
                if (Utils.areStringsSame(name, parts[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    Boolean isPrize(String string) {
        String[] parts = string.split(" ");
        if(parts.length == 2 && Utils.isStringContainingOnlyDigits(parts[0]) && Utils.areStringsSame(parts[1],"złoty")) {
            return true;
        }
        return false;
    }

    Boolean isAddress(String string) {
        String[] parts = string.split("\\.");
        for (String address : Constants.ADDRESS) {
            if (Utils.areStringsSame(address, parts[0])) {
                return true;
            }
        }
        return false;
    }

    Boolean isCity(String string) {
        if(string.split(" ").length == 1) {
            for (String city : Constants.CITIES) {
                if (Utils.areStringsSame(city, string)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> wordsToString(List<Word> words) {
        return words.stream().map(x -> x.content).collect(Collectors.toList());
    }
}
