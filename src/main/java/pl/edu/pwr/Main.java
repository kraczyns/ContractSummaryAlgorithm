package pl.edu.pwr;

import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.utils.SummaryXML;
import pl.edu.pwr.utils.Utils;
import pl.edu.pwr.contractsummary.segmentation.Text;

import javax.xml.transform.TransformerException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws TransformerException {

        System.out.println("Podaj ścieżkę to pliku txt z umową: ");
        String filepath =  new Scanner (System.in).nextLine();
        String string = Utils.readFile(filepath);

        run(string).writeXMLtoFile();

    }

    private static SummaryXML run(String string) {

        Contract contract =  new Contract(new Text(string));
        return new SummaryXML(contract.getHeadersValues(), contract.getGeneralValues(), contract.getDetailsValues(), contract.getDetailsHeaders());

    }
}
