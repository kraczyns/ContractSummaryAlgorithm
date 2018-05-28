package pl.edu.pwr.contractsummarytest.typestest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.types.ContractTermination;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ContractTerminationTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Bolesławiec, \n" +
                        "07.04.2018\n" +
                        "\n" +
                        "Jan Kowalski\t\t\t\t\t\t\t\n" +
                        "Ul. Kwiatowa 7/6, \n" +
                        "Bolesławiec\n" +
                        "\n" +
                        "\tFIRMEX S.A.\n" +
                        "\tPl. Fiołkowy 123 - 167\n" +
                        "\tBolesławiec\n" +
                        "\n" +
                        "\n" +
                        "Rozwiązanie umowy o pracę na mocy porozumienia stron\n" +
                        "\n" +
                        "Zwracam się z prośbą o rozwiązanie umowy o pracę zawartej w dniu 07.04.2017 r. pomiędzy Janem Kowalskim a FIRMEX S.A., na mocy porozumienia stron. Jako termin rozwiązania umowy proponuję dzień 20.05.2018 r.\n",
                        "07.04.2017", "", "20.05.2018", "Jan Kowalski"},
                {
                    "Koszalin dnia 17.09.2008 r.\n" +
                            "Katarzyna Strzelecka\n" +
                            "Ul. Nadmorska 9\n" +
                            "70-095 Koszalin\n" +
                            "„Piękne włosy” sp. z o.o.\n" +
                            "Ul. B. Chrobrego 110\n" +
                            "70-100 Koszalin\n" +
                            "Rozwiązanie umowy o pracę bez wypowiedzenia\n" +
                            "Zgodnie z art. 55 kodeksu pracy rozwiązuje umowę o pracę zawartą dnia 1.03.2008 w\n" +
                            "Koszalinie pomiędzy Piękne włosy sp. z o.o. a Katarzyną Strzelecką bez zachowania okresu\n" +
                            "wypowiedzenia.\n" +
                            "Przyczyną rozwiązania umowy jest:\n" +
                            "Nie wypłacanie przez pracodawcę wynagrodzenia za 4 kolejne miesiące.\n" +
                            "Z poważaniem\n" +
                            "…………………….\n" +
                            "(podpis pracownika)\n" +
                            "Niniejsze pismo otrzymałem\n" +
                            "…………………………………\n" +
                            "(podpis pracodawcy)", "1.03.2008", "Koszalin", "", "Katarzyna Strzelecka"
                },
                {
                    "Gniezno dnia 1.01.2009 r.\n" +
                            "\n" +
                            "Hurt-Detal S.A.\n" +
                            "Ul. Kwitnąca 9\n" +
                            "20-489 Gniezno\n" +
                            "Jan Kowalski\n" +
                            "Ul. Katedralna 20\n" +
                            "20-489 Gniezno\n" +
                            "Rozwiązanie umowy o pracę za porozumieniem stron\n" +
                            "Na podstawie art. 30 §1 pkt 1kodeksu pracy rozwiązujemy umowę o pracę zawartą\n" +
                            "dnia 5 października 2008 r. w Gnieźnie pomiędzy Hurt-Detal S.A. a Janem Kowalskim.\n" +
                            " Pracodawca Pracownik\n" +
                            " …………………………… ..…………………………..\n" +
                            " (podpis pracodawcy) (podpis pracownika)", "5.10.2008", "Gniezno", "", ""
                }
        });
    }

    private String input;
    private String expectedPreviousContractDate;
    private String expectedPreviousContractPlace;
    private String expectedContractEffectDate;
    private String expectedRequestingParty;
    private ContractTermination contractTermination;

    public ContractTerminationTest(String i, String epcd, String epcp, String eced, String erp) {
        input = i;
        expectedPreviousContractDate = epcd;
        expectedPreviousContractPlace = epcp;
        expectedContractEffectDate = eced;
        expectedRequestingParty = erp;
    }

    @Before
    public void init() {
        Contract contract = new Contract(new Text(input));
        contractTermination = new ContractTermination(contract.getText());
    }

    @Test
    public void testFindingPreviousContractDate() {
        Assert.assertEquals(expectedPreviousContractDate, contractTermination.getPreviousContractDate());
    }

    @Test
    public void testFindingPreviousContractPlace() {
        Assert.assertEquals(expectedPreviousContractPlace, contractTermination.getPreviousContractPlace());
    }

    @Test
    public void testFindingContractEffectDate() {
        Assert.assertEquals(expectedContractEffectDate, contractTermination.getContractEffectDate());
    }

    @Test
    public void testFindingRequestingParty() {
        Assert.assertEquals(expectedRequestingParty, contractTermination.getRequestingParty());
    }

}
