package pl.edu.pwr;

import pl.edu.pwr.utils.Utils;
import pl.edu.pwr.contractsummary.Text;

import java.util.Scanner;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;

public class Main {

    public static void main(String[] args) {

     String filepath;
     Scanner S = new Scanner (System.in);
     System.out.println("Podaj ścieżkę to pliku txt z umową: ");
     filepath = S.nextLine();
     String toString = Utils.readFile(filepath);
     Text text = new Text(toString);
    }
}
