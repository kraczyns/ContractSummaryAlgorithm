package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Sentence;
import pl.edu.pwr.contractsummary.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TextTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
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
                        Arrays.asList("Umowa o pracę zawarta na czas nieokreślony zawarta w dniu 07.08.2018 r. w Poznaniu pomiędzy: 1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu. a 2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem."))}
        });
    }

    private String input;

    private List<String> expected;

    public TextTest(String i, List<String> e) {
        input = i;
        expected = e;
    }

    @Test
    public void testSentenceSegmentation() {
        Text text = new Text(input);
        assertEquals(expected, Sentence.sentencesToString(text.segmentation()));

}


}
