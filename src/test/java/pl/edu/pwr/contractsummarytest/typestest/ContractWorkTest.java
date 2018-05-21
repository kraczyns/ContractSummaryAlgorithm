package pl.edu.pwr.contractsummarytest.typestest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.types.ContractWork;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ContractWorkTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                    "Umowa o dzieło\n" +
                            "zawarta w Poznaniu w dniu 14.04.2009 r.\n" +
                            "pomiędzy:\n" +
                            "Kazimierzem Boroniem, synem Romana i Stanisławy zamieszkałym w Poznaniu przy ul.\n" +
                            "Głównej 8, posługującym się dowodem osobistym AAA 0000001\n" +
                            "zwanym dalej Zamawiającym,\n" +
                            "a\n" +
                            "„ABC” spółka z o.o. w Poznaniu przy ul. Dolnej 8, KRS 000000002 - reprezentowaną przez\n" +
                            "Tomasza Wierzbowskiego -prokurenta,\n" +
                            "zwanym dalej Przyjmującym zamówienie.\n" +
                            "§ 1\n" +
                            "Zamawiający zamawia, a Przyjmujący zamówienie zobowiązuje się do wykonania dzieła\n" +
                            "w postaci: szafy wnękowej.\n" +
                            "Szczegółowy opis dzieła przedstawia załącznik numer 1.\n" +
                            "§ 2\n" +
                            "Przyjmujący zamówienie wykona dzieło, korzystając z własnych narzędzi i materiałów.\n" +
                            "§ 3\n" +
                            "Przyjmujący zamówienie zobowiązuje się rozpocząć wykonanie dzieła do 1.05.2009 r.\n" +
                            "i ukończyć je do 1.06.2009 r.\n" +
                            "§ 4\n" +
                            "Przyjmujący zamówienie nie może powierzyć wykonania dzieła innej osobie.\n" +
                            "§ 5\n" +
                            "Zamawiający odbierze dzieło w miejscu jego wykonania, najpóźniej w ciągu 10 dni od\n" +
                            "§ 6\n" +
                            "1. Za wykonanie dzieła strony ustalają wynagrodzenie w wysokości 4 000 zł (słownie: czterech\n" +
                            "tysięcy).\n" +
                            "2. W dniu podpisania umowy Przyjmujący zamówienie otrzyma 20% zaliczki na poczet\n" +
                            "wynagrodzenia. Pozostałą część wynagrodzenia Zamawiający przekaże na rachunek\n" +
                            "Przyjmującego zamówienie nr 88 0000 0000 0000 0000 0000 0000 w ciągu dni od dnia\n" +
                            "odebrania dzieła.\n" +
                            "§ 7\n" +
                            "W przypadku niewykonania dzieła w terminie, Przyjmujący zamówienie zapłaci karę umowną\n" +
                            "w wysokości 500 zł (słownie: pięćset) za każdy dzień zwłoki.\n" +
                            "§ 8\n" +
                            "W sprawach nieuregulowanych w niniejszej umowie stosuje się przepisy Kodeksu cywilnego.\n" +
                            "§ 9\n" +
                            "Umowa została spisana w dwóch jednobrzmiących egzemplarzach, po jednym dla każdej ze\n" +
                            "stron.\n" +
                            "......................................... ….........................................\n" +
                            " Zamawiający Przyjmujący zamówienie\n" +
                            "Załącznik:\n" +
                            "Specyfikacja dla szafy wnękowej będącej przedmiotem umowy", "szafa wnękowa", "1.06.2009", "1.05.2009", "4000 złoty", "500 złoty"
                }
        });
    }

    private String input;
    private String expectedDescription;
    private String expectedDeadline;
    private String expectedStartDate;
    private String expectedSalary;
    private String expectedDelayPenalty;
    private ContractWork contractWork;

    public ContractWorkTest(String i, String dscp, String ddln, String stdt, String sal, String dlpn) {
        input = i;
        expectedDescription = dscp;
        expectedDeadline = ddln;
        expectedStartDate = stdt;
        expectedSalary = sal;
        expectedDelayPenalty = dlpn;
    }

    @Before
    public void init() {
        Contract contract = new Contract(new Text(input));
        contractWork = new ContractWork(contract.getText());
    }

    @Test
    public void testFindingDescription() {
        Assert.assertEquals(expectedDescription, contractWork.getDescription());
    }

    @Test
    public void testFindingDeadline() {
        Assert.assertEquals(expectedDeadline, contractWork.getDeadline());
    }

    @Test
    public void testFindingStartDate() {
        Assert.assertEquals(expectedStartDate, contractWork.getStartDate());
    }

    @Test
    public void testFindingSalary() {
        Assert.assertEquals(expectedSalary, contractWork.getSalary());
    }

    @Test
    public void testFindingDelayPenalty() {
        Assert.assertEquals(expectedDelayPenalty, contractWork.getDelayPenalty());
    }
}
