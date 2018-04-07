package pl.edu.pwr;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

     String filepath;
     Scanner S = new Scanner (System.in);
     System.out.println("Podaj ścieżkę to pliku txt z umową: ");
     filepath = S.nextLine();
     String toString = Utils.readFile(filepath);
     System.out.println(toString);

    }
}
