package pl.edu.pwr;

import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.utils.Utils;
import pl.edu.pwr.contractsummary.Text;

import javax.xml.transform.TransformerException;
import java.util.Scanner;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;

public class Main {

    public static void main(String[] args) {

     String filepath;
     Scanner S = new Scanner (System.in);
     System.out.println("Podaj ścieżkę to pliku txt z umową: ");
     filepath = S.nextLine();
     String toString = Utils.readFile(filepath);
     Contract contract =  new Contract(new Text(toString));
     try {
            contract.writeXMLtoFile();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
