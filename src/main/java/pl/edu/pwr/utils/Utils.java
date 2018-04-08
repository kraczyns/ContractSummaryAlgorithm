package pl.edu.pwr.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
}
