package pl.edu.pwr.contractsummarytest.typestest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.types.ContractOfMandate;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ContractOfMandateTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                    "Umowa zlecenia\n" +
                            "zawarta 14.04.2009 r. w Poznaniu, pomiędzy\n" +
                            "Michałem Majchrowskim, przedsiębiorcą zarejestrowanym w Urzędzie Miasta Poznań pod\n" +
                            "numerem 88780/DG/09, prowadzącą jednoosobowo działalność gospodarczą pod firmą Przywóz\n" +
                            "wywóz przy ul. Dolnej 7 w Poznaniu, o numerze REGON 9098989., NIP: 111-111-11-11,\n" +
                            "zwanym dalej Zleceniodawcą\n" +
                            "a\n" +
                            "Kazimierz Boroń o numerze PESEL 7809878000 , zamieszkałym w Poznaniu, przy ul. Głównej\n" +
                            "8, legitymującym się dowodem osobistym AAA 7777777, zwanym dalej Zleceniobiorcą.\n" +
                            "§ 1\n" +
                            "Zleceniodawca zleca, a Zleceniobiorca przyjmuje do wykonania usługę polegającą na:\n" +
                            "1) Przewozie mebli z siedziby Zleceniodawcy do nowego biura położonego w Poznaniu przy ul.\n" +
                            "Żwirki i Wigury 9\n" +
                            "§ 2\n" +
                            "1. Zleceniobiorca zobowiązuje się przewieźć meble od dnia 15.04.2009 r. do dnia 20.04.2009..\n" +
                            "r.\n" +
                            "§ 3\n" +
                            "1. Zleceniobiorca zobowiązuje się wykonać zlecenie osobiście, bez powierzania zadań\n" +
                            "wymienionych w § 1 pkt 1 i 2 osobom trzecim.\n" +
                            "2. Zleceniodawca zapewni do wykonania zlecenia:\n" +
                            "a) środki finansowe na paliwo do samochodu,\n" +
                            "Pobrano z portalu www.wieszjak.pl\n" +
                            "§ 4\n" +
                            "1. Za wykonanie zlecenia określonego w §1 i 2 Zleceniobiorcy przysługuje wynagrodzenie\n" +
                            "w wysokości 400 zł (słownie: czterystu) brutto.\n" +
                            "2. Zleceniobiorca otrzyma wynagrodzenie za wykonane zlecenie przelewem na rachunek\n" +
                            "prowadzony w poznańskim oddziale banku Poznański Bank Inwestycji Kapitałowej nr 99\n" +
                            "8888 8888 8888 8888 8888 8888 w ciągu 10 dni od przedłożenia rachunku w siedzibie\n" +
                            "Zleceniodawcy.\n" +
                            "§ 5\n" +
                            "Zmiany umowy wymagają formy pisemnej pod rygorem nieważności.\n" +
                            "§ 6\n" +
                            "W sprawach nieuregulowanych umową stosuje się przepisy Kodeksu cywilnego i innych\n" +
                            "obowiązujących aktów prawnych.\n" +
                            "§ 7\n" +
                            "Umowa została sporządzona w dwóch jednobrzmiących egzemplarzach, po jednym dla każdej ze\n" +
                            "stron.\n" +
                            "…………………………… …………………………….\n" +
                            " (podpis zleceniodawcy) (podpis zleceniobiorcy)",
                        "przewóz mebli", "400 złoty", "15.04.2009", "20.04.2009"
                },

        });
    }

    private String input;
    private String expectedDescription;
    private String expectedSalary;
    private String expectedStartDate;
    private String expectedEndDate;
    private ContractOfMandate contractOfMandate;

    public ContractOfMandateTest(String i, String desc, String sal, String stdt, String endt) {
        input = i;
        expectedDescription = desc;
        expectedSalary = sal;
        expectedStartDate = stdt;
        expectedEndDate = endt;
    }

    @Before
    public void init() {
        Contract contract = new Contract(new Text(input));
        contractOfMandate = new ContractOfMandate(contract.getText());
    }

    @Test
    public void testFindingDescription() {
        Assert.assertEquals(expectedDescription, contractOfMandate.getDescription());
    }

    @Test
    public void testFindingSalary() {
        Assert.assertEquals(expectedSalary, contractOfMandate.getSalary());
    }

    @Test
    public void testFindingStartDate() {
        Assert.assertEquals(expectedStartDate, contractOfMandate.getStartDate());
    }

    @Test
    public void testFindingEndDate() {
        Assert.assertEquals(expectedEndDate, contractOfMandate.getEndDate());
    }

}
