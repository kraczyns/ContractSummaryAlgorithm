package pl.edu.pwr.contractsummarytest;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.Text;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ContractTest {
    enum Field {CONTRACT_TYPE, CONTRACT_SIDES, CONCLUSION_DATE, CONCLUSION_PLACE}

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {Field.CONTRACT_TYPE, "Umowa o pracę zawarta na czas nieokreślony między Adamem Kowalskim a Janiną Nowak w Poznaniu dnia 15.10.2018.", "umowa o pracę"},
                {Field.CONTRACT_TYPE, "Umowa - zlecenie zawarta na czas nieokreślony między Adamem Kowalskim a Janiną Nowak w Poznaniu dnia 15.10.2018.", "umowa zlecenia"},
                {Field.CONTRACT_SIDES, "Umowa o pracę zawarta na czas nieokreślony między Adamem Kowalskim a Janiną Nowak w Poznaniu dnia 15.10.2018.", "Adam Kowalski Janina Nowak"},
                {Field.CONTRACT_SIDES, "Umowa o pracę zawarta na czas nieokreślony między firmą Polinvest a Janiną Nowak w Poznaniu dnia 15.10.2018.", "Polinvest Janina Nowak"},
                {Field.CONTRACT_SIDES, "Umowa o pracę zawarta na czas nieokreślony między firmą Polinvest, zwanym dalej Pracodawcą a Janiną Nowak, zwaną dalej Pracownikiem, w Poznaniu dnia 15.10.2018.", "Polinvest Janina Nowak"},
                {Field.CONCLUSION_DATE, "Umowa o pracę zawarta na czas nieokreślony między Adamem Kowalskim a Janiną Nowak w Poznaniu dnia 15.10.2018.", "15.10.2018"},
                {Field.CONCLUSION_PLACE, "Umowa o pracę zawarta na czas nieokreślony między Adamem Kowalskim a Janiną Nowak w Poznaniu dnia 15.10.2018.", "Poznań"},
                {Field.CONTRACT_SIDES, "..........................................................\t\t\t\t\t07.08.2018 r.  \n" +
                        "\n" +
                        "Umowa o pracę zawarta na czas nieokreślony\n" +
                        "\n" +
                        "zawarta w dniu 07.08.2018 r. w Poznaniu pomiędzy:\n" +
                        "1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu.\n" +
                        "a\n" +
                        "2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem.", "Polinvest Kazimierz Boroń Prezes Zarządu Adam Nowak"},
                {Field.CONCLUSION_DATE, "..........................................................\t\t\t\t\t07.08.2018 r.  \n" +
                        "\n" +
                        "Umowa o pracę zawarta na czas nieokreślony\n" +
                        "\n" +
                        "zawarta w dniu 07.08.2018 r. w Poznaniu pomiędzy:\n" +
                        "1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu.\n" +
                        "a\n" +
                        "2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem.", "07.08.2018"}
        });
    }

    private Field field;
    private String input;
    private String expected;

    public ContractTest(Field f, String i, String e) {
        field = f;
        input = i;
        expected = e;
    }

    @Test
    public void testFindingContractTypes() {
        Assume.assumeTrue(field == Field.CONTRACT_TYPE);
        Contract contract = new Contract(new Text(input));
        assertEquals(expected, contract.getContractType());
    }

    @Test
    public void testFindingContractSides() {
        Assume.assumeTrue(field == Field.CONTRACT_SIDES);
        Contract contract = new Contract(new Text(input));
        String tmp = "";
        for (String side : contract.getSides())
        {
            tmp += side + " ";
        }
        assertEquals(expected, tmp.trim());
    }

    @Test
    public void testFindingConclusionDate() {
        Assume.assumeTrue(field == Field.CONCLUSION_DATE);
        Contract contract = new Contract(new Text(input));
        assertEquals(expected, contract.getConclusionDate());
    }
    @Test
    public void testFindingConclusionPlace() {
        Assume.assumeTrue(field == Field.CONCLUSION_PLACE);
        Contract contract = new Contract(new Text(input));
        assertEquals(expected, contract.getConclusionPlace());
    }
}
