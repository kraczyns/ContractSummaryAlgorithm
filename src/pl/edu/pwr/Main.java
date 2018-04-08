package pl.edu.pwr;

import pl.edu.pwr.contractsummary.Text;
import pl.edu.pwr.utils.Utils;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

     String filepath;
     Scanner S = new Scanner (System.in);
     System.out.println("Podaj ścieżkę to pliku txt z umową: ");
     filepath = S.nextLine();
     String toString = Utils.readFile(filepath);
     Text text = new Text(toString);
     System.out.println(text.segmentation());

    }
}
