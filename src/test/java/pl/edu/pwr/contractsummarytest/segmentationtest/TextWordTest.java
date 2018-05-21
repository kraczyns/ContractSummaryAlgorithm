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
                {
                  "Wynajmujący oświadcza, że jest właścicielem lokalu położonego we Wrocławiu przy ulicy " +
                          "Bolesława Prusa 19/7 o powierzchni 30 m² składającego się z 2 pokoi, kuchni oraz łazienki.",
                        Arrays.asList("wynajmujący", "oświadczać", "że", "być", "właściciel", "lokal", "położyć", "w", "Wrocław", "przy",
                                "ul. Bolesława Prus 19/7", "o", "powierzchni", "30 m²", "składać", "się", "z", "2", "pokój", "kuchnia", "oraz", "łazienka")
                },
                {"Umowa najmu lokalu " +
                        "Zawarta w dniu 29.04.2018 r. we Wrocławiu pomiędzy: " +
                        "Adam Nowak, ul. Kwiatowa 18/5, Wrocław " +
                        "zwanym dalej Wynajmującym, " +
                        "Artur Żmijewski, pl. Powstańców Śląskich 23/4, Wrocław " +
                        "zwanym dalej Najemcą.", new ArrayList<String>(Arrays.asList(
                        "umowa", "najem", "lokal", "zawarty", "w", "dzień", "29.04.2018", "r", "w", "Wrocław", "pomiędzy", "Adam Nowak", "ul. Kwiatowy 18/5",
                        "Wrocław", "zwać", "dalej", "wynajmujący", "Artur Żmijewski", "pl. Powstaniec Śląski 23/4", "Wrocław", "zwać", "dalej", "najemca")
                )
                },
                {"1. Czynsz najmu strony określiły w wysokości 1600 zł miesięcznie (słownie: tysiąc sześćset złoty).", new ArrayList<String>(Arrays.asList(
                                "1", "Czynsz", "najem", "strona", "określić", "w", "wysokość", "1600 złoty", "miesięcznie", "słownie",
                        "tysiąc", "sześćset", "złoty"
                ))},
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
                        Arrays.asList("proponować", "wypłata", "to", "25678 złoty", "miesięcznie")
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
                                "Poznań", "pomiędzy", "1", "Polinvest", "z", "siedziba", "przy", "ul. Główny 8", "w", "Poznań", "zwać", "dalej", "pracodawca", "reprezentować",
                                "przez", "Kazimierz Boroń", "Prezes Zarządu", "a",
                                        "2", "pan", "Adam Nowak", "zamieszkały", "przy", "ul. Rybnicki 9", "w", "Poznań", "zwać", "dalej", "pracownik"))},
                {"1. Polinvest z siedzibą przy ul. Głównej 8 w Poznaniu, zwanym dalej Pracodawcą, reprezentowane przez Kazimierza Boronia – Prezesa Zarządu.\n" +
                        "a\n" +
                        "2. Panem Adamem Nowakiem, zamieszkałym przy ul. Rybnickiej 9 w Poznaniu, zwanym dalej Pracownikiem.", new ArrayList<String>(
                        Arrays.asList("1", "Polinvest", "z", "siedziba", "przy", "ul. Główny 8", "w", "Poznań", "zwać", "dalej", "pracodawca", "reprezentować",
                                "przez", "Kazimierz Boroń", "Prezes Zarządu", "a",
                                "2", "pan", "Adam Nowak", "zamieszkały", "przy", "ul. Rybnicki 9", "w", "Poznań", "zwać", "dalej", "pracownik")) }
        });
    }

    @Test
    public void testWordSegmentation() {
        Text text = new Text(input);
        assertEquals(expected, Word.wordsToString(text.getSentences().get(0).getWords()));
    }


}
