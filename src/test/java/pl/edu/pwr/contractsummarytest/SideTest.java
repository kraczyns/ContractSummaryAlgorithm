package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Side;
import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Text;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SideTest {

    private String input;
    private String[] expectedFirst;
    private String[] expectedSecond;

    public SideTest(String i, String[] e, String[] e2) {
        input = i;
        expectedFirst = e;
        expectedSecond = e2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> sentencesSegmentationData() {
        return Arrays.asList(new Object[][] {
                {
                    "Umowa najmu lokalu\n" +
                                " \n" +
                                " \n" +
                                "Zawarta w dniu 29.04.2018 r. we Wrocławiu pomiędzy:\n" +
                                "\n" +
                                "Adam Nowak, \n" +
                                "Ul. Kwiatowa 18/5, \n" +
                                "Wrocław\t     \t\n" +
                                "\n" +
                                "zwanym dalej Wynajmującym,\n" +
                                "a\n" +
                                "Artur Żmijewski,\n" +
                                "Pl. Powstańców Śląskich 23/4\n" +
                                "zwanym dalej Najemcą.", new String[] {"false", "Adam Nowak", "ul. Kwiatowy 18/5", "Wrocław", "", "", "wynajmujący"},
                new String[] {"false", "Artur Żmijewski", "pl. Powstaniec Śląski 23/4", "", "", "", "najemca"}},
                {
                    "Umowa najmu lokalu mieszkalnego\n" +
                            "Zawarta w dniu 13.04.2008 r. w Poznaniu pomiędzy:\n" +
                            "1) Janiną Boroń, zamieszkałą w Poznaniu przy ul. Głównej 8, legitymującą się dowodem\n" +
                            "osobistym ABB 879809, zwaną dalej Wynajmującą,\n" +
                            "a\n" +
                            "2) Moniką Konecką, zamieszkałą w Poznaniu, ul. Dworcowa 17, legitymującą się dowodem\n" +
                            "osobistym AAA 0000000, zwaną dalej Najemcą.",
                        new String[] {"false", "Janina Boroń", "ul. Główny 8", "Poznań", "", "ABB 879809", "wynajmująca"},
                        new String[] {"false", "Monika Konecka", "ul. Dworcowy 17", "Poznań", "", "AAA 0000000", "najemca"}
                },
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
                                "zwanym dalej Przyjmującym zamówienie.\n",
                        new String[] {"false", "Kazimierz Boroń", "ul. Główny 8", "Poznań", "", "AAA 0000001", "zamawiający"},
                        new String[] {"true", "ABC", "ul. Dolny 8", "Poznań", "", "", "Tomasz Wierzbowski", "przyjmujący"}
                },
                { "Umowa o dzieło\n" +
                        "zawarta w Poznaniu w dniu 14.04.2009 r.\n" +
                        "pomiędzy:\n" +
                        "Kazimierzem Boroniem, synem Romana i Stanisławy zamieszkałym w Poznaniu przy ul. Głównej 8, posługującym się dowodem osobistym AAA0000001\n" +
                        "zwanym dalej Zamawiającym,\n" +
                        "a\n" +
                        "Company spółka z o.o. w Poznaniu przy ul. Dolnej 8, KRS 000000002 - reprezentowaną przez Tomasza Wierzbowskiego -prokurenta,\n" +
                        "zwanym dalej Przyjmującym zamówienie."
                    ,
                        new String[] {"false", "Kazimierz Boroń", "ul. Główna 8", "Poznań", "", "AAA0000001", "zamawiający"},
                        new String[] {"true", "Company", "ul. Dolna 8", "", "", "Tomasz Wierzbowski", "przyjmujący"}
                }
                });
    }

    @Test
    public void testSidesExtraction() {
        Text text = new Text(input);
        Sentence sentence = text.getSentences().get(0);
        String[] firstSide = Side.extractSides(sentence).get(0).getContractTypeFields();
        String[] secondSide = Side.extractSides(sentence).get(1).getContractTypeFields();

        assertEquals(expectedFirst, firstSide);
        assertEquals(expectedSecond, secondSide);
    }
}
