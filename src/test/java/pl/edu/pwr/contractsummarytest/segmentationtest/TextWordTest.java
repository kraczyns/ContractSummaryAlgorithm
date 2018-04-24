package pl.edu.pwr.contractsummarytest.segmentationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TextWordTest {

    private String input;

    private List<String> expected;

    public TextWordTest(String i, List<String> e) {
        input = i;
        expected = e;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> wordsSegmentationData() {
        return Arrays.asList(new Object[][] {
                {"Z zastosowaniem 2 dniowego okresu wypowiedzenia.", new ArrayList<String>(
                    Arrays.asList("z", "zastosowanie", "2 dniowy", "okres", "wypowiedzenie")
                )},
                {"Zofia ma 2 myszy.", new ArrayList<String>(
                        Arrays.asList("Zofia", "mieć", "2", "myszy")
                )},
                    {"Zofia ma psa.", new ArrayList<String>(
                        Arrays.asList("Zofia", "mieć", "pies"))},
                {"Kot ma na imię Mruczek.", new ArrayList<String>(
                        Arrays.asList("kot", "mieć", "na", "imię", "Mruczek"))},
                {"Zofia ma kotkę, a Adam psa.", new ArrayList<String>(
                    Arrays.asList("Zofia", "mieć", "kotka", "a", "Adam", "pies"))},
                {"Zofia Kowalska ma zwierzątko.", new ArrayList<String>(
                        Arrays.asList("Zofia Kowalska", "mieć", "zwierzątko")
                )},
                {"Urodzona 15.10.1994 r.", new ArrayList<String>(
                        Arrays.asList("urodzona", "15.10.1994", "rok")
                )},
                {"Proponowana wypłata to 25 678 złoty miesięcznie.", new ArrayList<String>(
                        Arrays.asList("proponować", "wypłata", "to", "25 678 złoty", "miesięcznie")
                )},
                {"Zofia mieszka przy ul. Grabiszyńskiej 18/5.", new ArrayList<String>(
                        Arrays.asList("Zofia", "mieszkać", "przy", "ul. Grabiszyńska 18/5")
                )},
                {"..........................................................\t\t\t\t\t07.08.2018 r.  \n" +
                        "\n" +
                        "Umowa o pracę zawarta na czas nieokreślony\n" +
                        "\n" +
                        "zawarta w dniu 07.08.2018 r. w Poznaniu pomiędzy:\n" +
                        "1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu.\n" +
                        "a\n" +
                        "2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem.", new ArrayList<String>(
                        Arrays.asList("umowa", "o", "praca", "zawarty", "na", "czas", "nieokreślony", "zawarty", "w", "dzień", "07.08.2018", "r", "w",
                                "Poznań", "pomiędzy", "1", "Polinvest", "z", "siedziba", "przy", "ul. Główny 8", "w", "Poznań", "zwać", "dalej", "Pracodawca", "reprezentować",
                                "przez", "Kazimierz Boroń", "Prezes Zarządu", "a",
                                        "2", "pan", "Adam Nowak", "zamieszkały", "przy", "ul. Rybnicki 9", "w", "Poznań", "zwać", "dalej", "Pracownik"))},
                {"1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu.\n" +
                        "a\n" +
                        "2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem.", new ArrayList<String>(
                        Arrays.asList("1", "Polinvest", "z", "siedziba", "przy", "ul. Główny 8", "w", "Poznań", "zwać", "dalej", "Pracodawca", "reprezentować",
                                "przez", "Kazimierz Boroń", "Prezes Zarządu", "a",
                                "2", "pan", "Adam Nowak", "zamieszkały", "przy", "ul. Rybnicki 9", "w", "Poznań", "zwać", "dalej", "Pracownik")) }
        });
    }

    @Test
    public void testWordSegmentation() {
        Text text = new Text(input);
        assertEquals(expected, Word.wordsToString(text.getSentences().get(0).getWords()));
    }


}
