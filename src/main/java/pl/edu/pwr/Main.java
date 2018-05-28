package pl.edu.pwr;

import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.utils.SummaryXML;
import pl.edu.pwr.utils.Utils;
import pl.edu.pwr.contractsummary.segmentation.Text;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws TransformerException {

        String[] dirs = new String[] {"dzieło", "najem", "praca", "rozwiązanie", "zlecenie"};
        List<Long> elapsedTimes = new ArrayList<Long>();
        List<Integer> lengths = new ArrayList<Integer>();
        String string = "";
        String tmp = "c(";
        for (String dir : dirs) {
            for (int i = 1; i < 6; i++) {
                String filepath = "C:/Users/nieop/Desktop/mgr/umowy-txt/umowa-" + dir + "/umowa-" + i + ".txt";
                string = Utils.readFile(filepath);

                long start = System.nanoTime();
                run(string).writeXMLtoFile("umowa-" + dir, i);
                long elapsedTime = System.nanoTime() - start;
                elapsedTimes.add(elapsedTime);
                lengths.add(string.length());
            }
        }

        System.out.println("Algorithm duration:");
        for (Long elem : elapsedTimes) {
            tmp += elem + ", ";
        }
        String betterTmp = tmp.substring(0, tmp.length() - 2);
        betterTmp += ")";
        System.out.println(betterTmp);
        tmp = "c(";
        betterTmp = "";
        System.out.println("Contract length:");
        for (int elem : lengths) {
            tmp += elem + ", ";
        }
        betterTmp = tmp.substring(0, tmp.length() - 2);
        betterTmp += ")";
        System.out.println(betterTmp);
    }

    private static SummaryXML run(String string) {
        String language = "PL";
        Contract contract =  new Contract(new Text(string));
        return new SummaryXML(contract.getHeadersValues(), contract.getGeneralValues(), contract.getSideValues(), contract.getSideHeaders(language), contract.getDetailsValues(), contract.getDetailsHeaders(language));

    }
}
