package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Sentence;
import pl.edu.pwr.contractsummary.Text;
import pl.edu.pwr.contractsummary.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TextSentenceTest {

    private String input;

    private List<String> expected;

    public TextSentenceTest(String i, List<String> e) {
        input = i;
        expected = e;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> sentencesSegmentationData() {
        return Arrays.asList(new Object[][] {
                {"Ala ma kota. Kot Ali ma na imię Mruczek.", new ArrayList<String>(
                        Arrays.asList("Ala ma kota.","Kot Ali ma na imię Mruczek."))},
                {"Ala ma kota. Kot Ali ma na imię Mruczek", new ArrayList<String>(
                        Arrays.asList("Ala ma kota."))},
                {"Umowa o pracę zawarta na czas nieokreślony\n" +
                        "\n" +
                        "zawarta w dniu 07.08.2018 r. w Poznaniu pomiędzy:\n" +
                        "1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu.\n" +
                        "a\n" +
                        "2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem.", new ArrayList<String>(
                        Arrays.asList("Umowa o pracę zawarta na czas nieokreślony zawarta w dniu 07.08.2018 r. w Poznaniu pomiędzy: 1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu. a 2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem."))},
                {
                        "Bolesławiec, \n" +
                                "07.04.2018\n" +
                                "\n" +
                                "Jan Kowalski\t\t\t\t\t\t\t\n" +
                                "Ul. Kwiatowa 7/6, \n" +
                                "Bolesławiec\n" +
                                "\n" +
                                "\tFIRMEX S.A.\n" +
                                "\tPl. Fiołkowy 123 - 167, Bolesławiec \n" +
                            "Rozwiązanie umowy o pracę na mocy porozumienia stron.", new ArrayList<String>(
                        Arrays.asList("Rozwiązanie umowy o pracę na mocy porozumienia stron."))
                }
        });
    }
    @Test
    public void testSentenceSegmentation() {
        Text text = new Text(input);
        assertEquals(expected, Sentence.sentencesToString(text.getSentences()));
}
}
