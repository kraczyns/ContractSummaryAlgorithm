package pl.edu.pwr.contractsummarytest.typestest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.types.ContractPeriod;
import pl.edu.pwr.contractsummary.types.EmploymentContract;
import pl.edu.pwr.contractsummary.types.HourlyDimension;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EmploymentContractTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"..........................................................\t\t\t\t\t07.08.2018 r.  \n" +
        "\n" +
        "Umowa o pracę zawarta na czas nieokreślony\n" +
        "\n" +
        "zawarta w dniu 07.08.2018 r. w Poznaniu pomiędzy:\n" +
        "1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu.\n" +
        "a\n" +
        "2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem.\n" +
        "o następującej treści:\n" +
        "\n" +
        "1.\tPracodawca zatrudnia Pracownika na podstawie umowy o pracę zawartej na czas nieokreślony na stanowisku administratora w dziale administracji w pełnym wymiarze czasu pracy i powierza obowiązki określone w zakresie zadań i czynności stanowiących załącznik do niniejszej umowy.\n" +
        "\n" +
        "2.\tPracownik obowiązany jest wykonywać powierzoną pracę w siedzibie pracodawcy, tj. w pomieszczeniach biurowych Pracodawcy mieszczących się przy ul. Dworcowej 10 w Poznaniu, począwszy od dnia 10.08.2018 r. \n" +
        "\n" +
        "3.\tW czasie trwania umowy o pracę Pracownik będzie otrzymywał wynagrodzenie płatne na warunkach przewidzianych w niniejszej umowie składające się z:\n" +
        "1)\twynagrodzenia zasadniczego – 2500 zł \n" +
        "2)\tdodatku funkcyjnego - 200 zł\n" +
        "3)\tpremii miesięcznej w wysokości 5 % wynagrodzenia zasadniczego\n" +
        "RAZEM BRUTTO: 2 852 zł (słownie: dwa tysiące osiemset dwadzieścia pięć złotych).\n" +
        "\n" +
        "4.\tInne warunki umowy:\n" +
        "W czasie trwania umowy Pracownik ma prawo korzystać ze służbowego samochodu, a także otrzymuje do dyspozycji telefon komórkowy.\n", ContractPeriod.indefinitePeriod, "", HourlyDimension.fulltime,
                1, "administrator", "ul. Dworcowy 10", "10.08.2018", "", "2 852 złoty"}
        });
    }

    private String input;
    private ContractPeriod expectedContractPeriod;
    private String expectedDefinitePeriod;
    private HourlyDimension expectedHourlyDimension;
    private int expectedPartTimeDimension;
    private String expectedPosition;
    private String expectedWorkingPlace;
    private String expectedStartDate;
    private String expectedWorkingHours;
    private String expectedSalary;

    private EmploymentContract employmentContract;

    public EmploymentContractTest(String i,
            ContractPeriod ec,
            String ed,
            HourlyDimension eh,
            int epd,
            String ep,
            String ewp,
            String esd,
            String ewh,
            String es) {

        input = i;
        expectedContractPeriod = ec;
        expectedDefinitePeriod = ed;
        expectedHourlyDimension = eh;
        expectedPartTimeDimension = epd;
        expectedPosition = ep;
        expectedWorkingPlace = ewp;
        expectedStartDate = esd;
        expectedWorkingHours = ewh;
        expectedSalary = es;

    }

    @Before
    public void init() {
        Contract contract = new Contract(new Text(input));
        employmentContract = new EmploymentContract(contract.getText());
    }

    @Test
    public void testFindingContractPeriod() {
        assertEquals(expectedContractPeriod, employmentContract.getContractPeriod());
    }

    @Test
    public void testFindingHourlyDimension() {
        assertEquals(expectedHourlyDimension, employmentContract.getHourlyDimension());
    }

    @Test
    public void testFindingPartTimeDimension() {
        assertEquals(expectedPartTimeDimension, employmentContract.getPartTimeDimension());
    }

    @Test
    public void testFindingSalary() {
        assertEquals(expectedSalary, employmentContract.getSalary());
    }

    @Test
    public void testFindingWorkingHours() {
        assertEquals(expectedWorkingHours, employmentContract.getWorkingHours());
    }

    @Test
    public void testFindingStartDate() {
        assertEquals(expectedStartDate, employmentContract.getStartDate());
    }

    @Test
    public void testFindingWorkingPlace() {
        assertEquals(expectedWorkingPlace, employmentContract.getWorkingPlace());
    }

    @Test
    public void testFindingPosition() {
        assertEquals(expectedPosition, employmentContract.getPosition());
    }

    @Test
    public void testFindingDefinitePeriod() {
        assertEquals(expectedDefinitePeriod, employmentContract.getDefinitePeriod());
    }
}
