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
                "07.04.2017", "", "20.05.2018", "Jan Kowalski"}
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
