package pl.edu.pwr.utils;

import morfologik.stemming.IStemmer;
import morfologik.stemming.WordData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    public static String readFile(String filename) {
        String toString = "";
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            toString = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toString;
    }

    public static Boolean areStringsSame( String a, String b) {
        if (a.toLowerCase().equals(b.toLowerCase())) {
            return true;
        } else { return false; }
    }

    public static Boolean isStringContainingOnlyDigits(String string) {
        for (int i = 0; i < string.length(); i++) {
            if(!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static Boolean arePartsUpperCase(String string) {
        String[] parts = string.split(" ");
        for (String part : parts) {
            if (part.toLowerCase().equals(part)) {
                return false;
            }
        }
        return true;
    }

    public static String capitalizeFirstLetter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
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
    public static Boolean isDate(String string) {
        if (string.contains(".")) {
            String[] parts = string.split("\\.");
            for (String part : parts) {
                if (!Utils.isStringContainingOnlyDigits(part)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isFirstNameLastName(String string) {
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

    public static Boolean isPrize(String string) {
        String[] parts = string.split(" ");
        if(parts.length == 2 && Utils.isStringContainingOnlyDigits(parts[0]) && Utils.areStringsSame(parts[1],"złoty")) {
            return true;
        }
        return false;
    }

    public static Boolean isAddress(String string) {
        String[] parts = string.split("\\.");
        for (String address : Constants.ADDRESS) {
            if (Utils.areStringsSame(address, parts[0])) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isCity(String string) {
        if(string.split(" ").length == 1) {
            for (String city : Constants.CITIES) {
                if (Utils.areStringsSame(city, string)) {
                    return true;
                }
            }
        }
        return false;
    }

}
