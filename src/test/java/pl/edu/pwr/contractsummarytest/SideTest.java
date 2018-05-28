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
                                "zwanym dalej Najemcą.", new String[] {"false", "Adam Nowak", "ul. Kwiatowa 18/5", "Wrocław", "", "wynajmujący"},
                new String[] {"false", "Artur Żmijewski", "pl. Powstaniec Śląski 23/4", "", "", "najemca"}},
                {
                    "Umowa najmu lokalu mieszkalnego\n" +
                            "Zawarta w dniu 13.04.2008 r. w Poznaniu pomiędzy:\n" +
                            "1) Janiną Boroń, zamieszkałą w Poznaniu przy ul. Głównej 8, legitymującą się dowodem\n" +
                            "osobistym ABB 879809, zwaną dalej Wynajmującą,\n" +
                            "a\n" +
                            "2) Moniką Konecką, zamieszkałą w Poznaniu, ul. Dworcowa 17, legitymującą się dowodem\n" +
                            "osobistym AAA 0000000, zwaną dalej Najemcą.",
                        new String[] {"false", "Janina Boroń", "ul. Główna 8", "Poznań", "ABB 879809", "wynajmująca"},
                        new String[] {"false", "Monika Konecka", "ul. Dworcowa 17", "Poznań", "AAA 0000000", "najemca"}
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
                        new String[] {"false", "Kazimierz Boroń", "ul. Główna 8", "Poznań", "AAA 0000001", "zamawiający"},
                        new String[] {"true", "ABC", "ul. Dolna 8", "Poznań", "Tomasz Wierzbowski", "przyjmujący"}
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
                        new String[] {"false", "Kazimierz Boroń", "ul. Główna 8", "Poznań", "AAA0000001", "zamawiający"},
                        new String[] {"true", "Company", "ul. Dolna 8", "Poznań", "Tomasz Wierzbowski", "przyjmujący"}
                },
                {
                    "Umowa o dzieło\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "Zawarta w dniu 21.05.2018 r. w Warszawie pomiędzy:\n" +
                            "Krzysztofem Ibiszem, zamieszkałym w Warszawie, przy ulicy Jagiellończyka 18/5, zwanym dalej Zamawiającym, \n" +
                            "a \n" +
                            "Arturem Żmijewskim, zamieszkałym w Warszawie, przy ulicy Fiołkowej 57/8, zwanym dalej Wykonawcą. ",
                        new String[] {"false", "Krzysztof Ibisz", "ul. Jagiellończyk 18/5", "Warszawa", "", "zamawiający"},
                        new String[] {"false", "Artur Żmijewski", "ul. Fiołkowa 57/8", "Warszawa", "", "wykonawca"}
                },
                {
                    " UMOWA O DZIEŁO NR 1\n" +
                            "\n" +
                            "zawarta w Kielcach w dniu 15.06.2014 r. pomiędzy:\n" +
                            "1.\tUniwersytetem Jana Kochanowskiego w Kielcach, \n" +
                            "ul. Żeromskiego 5 zwanym dalej „Zamawiającym” reprezentowanym przez:\n" +
                            "dr inż. Stefana Batorego\n" +
                            "a Panem Antonim Bieleckim\n" +
                            "legitymującym/ą  się dowodem osobistym nr AWG 22346\n" +
                            "PESEL: 70081908193\t           \t\n" +
                            "zwanym dalej „Wykonawcą”."
                        ,
                        new String[] {"true", "Uniwersytet Jana Kochanowski", "ul. Żeromski 5", "Kielce", "Stefan Batory", "zamawiający"},
                        new String[] {"false", "Antoni Bielecki", "", "", "AWG 22346", "wykonawca"}
                },
                {
                        "UMOWA O DZIEŁO\n" +
                                " \n" +
                                "Umowa zawarta w dniu 17.04.2015 r., w Świdnicy, pomiędzy:\n" +
                                "Felicjanem Andrzejczakiem, zamieszkałym w Świdnicy przy placu Wojska Polskiego 7,\n" +
                                "(oznaczenie strony zlecającej wykonanie dzieła)\n" +
                                " \n" +
                                "zwanym dalej Zamawiającym,\n" +
                                " \n" +
                                "a\n" +
                                "Katarzyną Nosowską, zamieszkałą w Świdnicy przy ulicy Armii Krajowej 17/6,\n" +
                                "(oznaczenie strony przyjmującej zlecenie wykonania dzieła)\n" +
                                " \n" +
                                "zwaną dalej Wykonawcą.",
                        new String[] {"false", "Felicjan Andrzejczak", "pl. Wojska Polskiego 7", "Świdnica", "", "zamawiający"},
                        new String[] {"false", "Katarzyna Nosowska", "ul. Armii Krajowej 17/6", "Świdnica", "", "wykonawca"}
                },
                {
                    "UMOWA O DZIEŁO\n" +
                            " \n" +
                            "Umowa zawarta w dniu 05.08.2013 r. w Łodzi, pomiędzy:\n" +
                            "Anną Nowak\n" +
                            "(oznaczenie strony zlecającej wykonanie dzieła)\n" +
                            " \n" +
                            "zwaną dalej Zamawiającym,\n" +
                            " \n" +
                            "a\n" +
                            "Janem Kowalskim\n" +
                            "(oznaczenie strony przyjmującej zlecenie wykonania dzieła)\n" +
                            " \n" +
                            "zwanym dalej Wykonawcą.",
                        new String[] {"false", "Anna Nowak", "", "", "", "zamawiający"},
                        new String[] {"false", "Jan Kowalski", "", "", "", "wykonawca"}

                },
                {
                        "Umowa najmu lokalu mieszkalnego (1)\n" +
                            "\n" +
                            "Zawarta w dniu 1 września 2009 roku w Krakowie pomiędzy:\n" +
                            "\n" +
                            "Janem Kowalskim, legitymującym się dowodem osobistym Nr AIS 12345, zamieszkałym w Krakowie przy ul. Dzielnej 5, kod pocztowy 31-254, zwanym dalej Wynajmującym\n" +
                            "\n" +
                            "a\n" +
                            "\n" +
                            "Andrzejem Nowakiem, legitymującym się dowodem osobistym Nr AID 54321, zamieszkałym w Poznaniu przy ul. Walecznej 58/15, kod pocztowy 61-841, zwanym dalej Najemcą.",
                        new String[] {"false", "Jan Kowalski", "ul. Dzielna 5", "Kraków", "AIS 12345", "wynajmujący"},
                        new String[] {"false", "Andrzej Nowak", "ul. Waleczna 58/15", "Poznań", "AID 54321", "najemca"}
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
