package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.utils.SummaryXML;
import pl.edu.pwr.utils.Utils;

import javax.xml.transform.TransformerException;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class IntegrationTests {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"dzieło", 1, 20, 0, 12, 3, 5},
                {"dzieło", 2, 18, 0, 10, 3, 5},
                {"dzieło", 3, 18, 0, 10, 3, 5},
                {"dzieło", 4, 17, 0, 10, 3, 4},
                {"dzieło", 5, 14, 0, 6, 3, 5},
                {"zlecenie", 1, 19, 0, 12, 3, 4},
                {"zlecenie", 2, 18, 0, 11, 3, 4},
                {"zlecenie", 3, 18, 0, 11, 3, 4},
                {"zlecenie", 4, 19, 0, 12, 3, 4},
                {"zlecenie", 5, 19, 0, 12, 3, 4},
                {"najem", 1, 20, 0, 9, 3, 8},
                {"najem", 2, 21, 0, 12, 3, 6},
                {"najem", 3, 23, 0, 12, 3, 8},
                {"najem", 4, 20, 0, 12, 3, 5},
                {"najem", 5, 20, 0, 12, 3, 5},
                {"praca", 1, 22, 1, 11, 3, 7},
                {"praca", 2, 21, 0, 11, 3, 7},
                {"praca", 3, 21, 0, 11, 3, 7},
                {"praca", 4, 18, 0, 8, 3, 7},
                {"praca", 5, 21, 0, 11, 3, 7},
                {"rozwiązanie", 1, 13, 3, 8, 3, 2},
                {"rozwiązanie", 2, 14, 3, 8, 3, 3},
                {"rozwiązanie", 3, 14, 3, 8, 3, 3},
                {"rozwiązanie", 4, 15, 3, 9, 3, 3 },
                {"rozwiązanie", 5, 15, 3, 10, 3, 2},

        });
    }

    String inputName;
    int inputNumber;
    int expectedInfo;
    int expectedHeaders;
    int expectedSides;
    int expectedGenerals;
    int expectedDetails;

    public IntegrationTests(String in, int inr, int ein, int eh, int es, int eg, int ed) {
        inputName = in;
        inputNumber = inr;
        expectedInfo = ein;
        expectedHeaders = eh;
        expectedSides = es;
        expectedGenerals = eg;
        expectedDetails = ed;
    }

    private int nonEmptyLength(String[] array) {
        int counter = 0;
        for (String elem : array) {
            if(!elem.equals("")) {
                counter++;
            }
        }
        return counter;
    }
    @Test
    public void testContractInfo() throws TransformerException {
        System.out.println("testContractInfo " + inputName + " " + inputNumber);
        String filepath = "C:/Users/nieop/Desktop/mgr/umowy-txt/umowa-"+inputName+"/umowa-"+inputNumber+".txt";
        String string = Utils.readFile(filepath);
        Contract contract =  new Contract(new Text(string));
        int counterHeader = nonEmptyLength(contract.getHeadersValues());
        int counterSides =  nonEmptyLength(contract.getSideValues()[0]) + nonEmptyLength(contract.getSideValues()[1]);
        int counterGenerals = nonEmptyLength(contract.getGeneralValues());
        int counterDetails = nonEmptyLength(contract.getDetailsValues());
        int counterInfo = counterHeader+counterSides+counterGenerals+counterDetails;
        System.out.println((float)counterInfo/expectedInfo*100);
        assertEquals(expectedInfo, counterInfo);
    }

    @Test
    public void testContractFlex() throws TransformerException {
        String filepath = "C:/Users/nieop/Desktop/mgr/umowy-txt/umowa-"+inputName+"/umowa-"+inputNumber+".txt";
        String string = Utils.readFile(filepath);
        String language = "PL";
        Contract contract =  new Contract(new Text(string));
        new SummaryXML(contract.getHeadersValues(), contract.getGeneralValues(), contract.getSideValues(), contract.getSideHeaders(language), contract.getDetailsValues(), contract.getDetailsHeaders(language)).writeXMLtoFile("umowa-"+inputName, inputNumber);
        String actual = Utils.readFile("/Users/nieop/Desktop/mgr/xml/umowa-"+inputName+"/umowa-"+inputNumber+".xml");
        String expected = Utils.readFile("/Users/nieop/Desktop/mgr/expected-xml/umowa-"+inputName+"/umowa-"+inputNumber+".xml");
        assertEquals(expected, actual);
    }

    @Test
    public void testContractSides() throws TransformerException {
        System.out.println("contract sides " + inputName + " " + inputNumber);
        String filepath = "C:/Users/nieop/Desktop/mgr/umowy-txt/umowa-"+inputName+"/umowa-"+inputNumber+".txt";
        String string = Utils.readFile(filepath);
        Contract contract =  new Contract(new Text(string));
        int counterSides =  nonEmptyLength(contract.getSideValues()[0]) + nonEmptyLength(contract.getSideValues()[1]);
        System.out.println((float)counterSides/expectedSides*100);
        assertEquals(expectedSides, counterSides);
    }

    @Test
    public void testContractGenerals() throws TransformerException {
        System.out.println("contract generals " + inputName + " " + inputNumber);
        String filepath = "C:/Users/nieop/Desktop/mgr/umowy-txt/umowa-"+inputName+"/umowa-"+inputNumber+".txt";
        String string = Utils.readFile(filepath);
        Contract contract =  new Contract(new Text(string));
        int counterGenerals = nonEmptyLength(contract.getGeneralValues());
        System.out.println((float)counterGenerals/expectedGenerals*100);
        assertEquals(expectedGenerals, counterGenerals);
    }

    @Test
    public void testContractDetails() throws TransformerException {
        System.out.println("contract details " + inputName + " " + inputNumber);
        String filepath = "C:/Users/nieop/Desktop/mgr/umowy-txt/umowa-"+inputName+"/umowa-"+inputNumber+".txt";
        String string = Utils.readFile(filepath);
        Contract contract =  new Contract(new Text(string));
        int counterDetails = nonEmptyLength(contract.getDetailsValues());
        System.out.println((float)counterDetails/expectedDetails*100);
        assertEquals(expectedDetails, counterDetails);
    }

    @Test
    public void testContractHeaders() throws TransformerException {
        System.out.println("contract headers " +inputName+" "+inputNumber);
        String filepath = "C:/Users/nieop/Desktop/mgr/umowy-txt/umowa-"+inputName+"/umowa-"+inputNumber+".txt";
        String string = Utils.readFile(filepath);
        Contract contract =  new Contract(new Text(string));
        int counterHeader = nonEmptyLength(contract.getHeadersValues());
        System.out.println((float)counterHeader/expectedHeaders*100);
        assertEquals(expectedHeaders, counterHeader);
    }
}
