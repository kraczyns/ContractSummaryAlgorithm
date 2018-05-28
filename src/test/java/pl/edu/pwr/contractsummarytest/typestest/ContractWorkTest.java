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
                },
                {
                    "Umowa o dzieło\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "Zawarta w dniu 21.05.2018 r. w Warszawie pomiędzy:\n" +
                            "Krzysztofem Ibiszem, zamieszkałym w Warszawie, przy ulicy Jagiellończyka 18/5, zwanym dalej Zamawiającym, \n" +
                            "a \n" +
                            "Arturem Żmijewskim, zamieszkałym w Warszawie, przy ulicy Fiołkowej 57/8, zwanym dalej Wykonawcą. \n" +
                            "\n" +
                            "\n" +
                            "§ 1\n" +
                            "1. \tWykonawca przyjmuje do wykonania zamówienie Zamawiającego polegające na: pomalowaniu płotu, zwane dalej dziełem. \n" +
                            "2. \tDo wykonania dzieła Wykonawca użyje własnych materiałów i narzędzi.*) \n" +
                            "\n" +
                            "\n" +
                            "§ 2\n" +
                            "1. \tWykonawca przystąpi do wykonywania dzieła w dniu 22.05.2018 r. Zakończenie prac nastąpi w dniu 25.05.2018 r.  *) \n" +
                            "2. \tDzieło zostanie wykonane w terminie do 25.05.2018 r. *) \n" +
                            "\n" +
                            "\n" +
                            "§ 3\n" +
                            "Wykonawca może powierzyć realizację dzieła osobom trzecim tylko za zgodą Zamawiającego wyrażoną na piśmie. \n" +
                            "\n" +
                            "§ 4\n" +
                            "1.\tZa wykonanie dzieła Wykonawca otrzyma wynagrodzenie ryczałtowe \n" +
                            "w wysokości 150 zł (słownie: sto pięćdziesiąt złoty) płatne na podstawie rachunku w terminie 7 dni od dnia wykonania dzieła i przyjęcia go przez Zamawiającego. \n" +
                            "2. \tWykonawca nie może żądać podwyższenia wynagrodzenia, jeżeli wykonał prace dodatkowe bez uzyskania zgody Zamawiającego \n" +
                            "3. \tJeżeli strony umówiły się o wynagrodzenie ryczałtowe, Wykonawca nie może żądać podwyższenia wynagrodzenia, chociażby w czasie zawarcia umowy nie można było przewidzieć rozmiaru lub kosztów powierzonych prac.*)\n" +
                            "\n" +
                            "\n" +
                            "§ 5\n" +
                            "W razie zwłoki w wykonaniu dzieła Wykonawca zapłaci Zamawiającemu karę umowną \n" +
                            "w  wysokości 20 zł. *) \n" +
                            "\n" +
                            "\n" +
                            "§ 6\n" +
                            "W razie gdy zwłoka w wykonaniu dzieła przekroczy 7 dni, Zamawiający może od umowy odstąpić z zachowaniem prawa do kar umownych i odszkodowania. \n" +
                            "\n" +
                            "\n" +
                            "§ 7\n" +
                            "W sprawach nieuregulowanych niniejszą umową mają zastosowanie przepisy Kodeksu cywilnego. \n" +
                            "\n" +
                            "§ 8\n" +
                            "Umowę sporządzono w dwóch jednobrzmiących egzemplarzach po jednym dla każdej ze stron. \n" +
                            "  \n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            ". . . . . . . . . . . . . . . . . . . . . . . .   \t. . . . . . . . . . . . . . . . . . . . . . . . \n" +
                            "\t(zamawiający)\t(wykonawca)\n" +
                            "\n" +
                            "\n" +
                            "\n",
                        "pomalowanie płotu", "25.05.2018", "22.05.2018", "150 złoty", "20 złoty"
                }
                        ,
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
                                        "zwanym dalej „Wykonawcą”.\n" +
                                        "\n" +
                                        "Pozostałe dane niezbędne do celów podatkowych zawiera oświadczenie Wykonawcy stanowiące załącznik do umowy.\n" +
                                        "\n" +
                                        "\n" +
                                        "§ 1\n" +
                                        "Zamawiający zleca a Wykonawca zobowiązuje się do wykonania dzieła polegającego na:\n" +
                                        "\n" +
                                        "stworzeniu serwisu internetowego.\n" +
                                        "§2\n" +
                                        "\n" +
                                        "1. Wykonawca oświadcza, że posiada odpowiednie kwalifikacje zapewniające wykonanie dzieła \n" +
                                        "     objętego umową na najwyższym poziomie, w sposób staranny i sumienny, według \n" +
                                        "     standardów i norm stosowanych w tym zakresie, tak aby umowa została zrealizowana \n" +
                                        "     zgodnie z celem, dla którego została zawarta.\n" +
                                        "2. Wykonawca zobowiązany jest do wykonania dzieła osobiście.\n" +
                                        "\n" +
                                        "\n" +
                                        "§ 3\n" +
                                        "1. Dzieło powstałe w wyniku realizacji umowy stanowić będzie utwór w rozumieniu ustawy o prawie autorskim i prawach pokrewnych.\n" +
                                        "2. Wykonawca oświadcza, że dzieło stanowi utwór oryginalny, Wykonawca jest autorem utworu, a jego prawa autorskie do utworu nie są przez nikogo ograniczone.\n" +
                                        "\n" +
                                        "§ 4\n" +
                                        "\n" +
                                        "1.  Rozpoczęcie wykonywania dzieła nastąpi w dniu 15.07.2014 r.\n" +
                                        "2.  Strony ustalają termin wykonania dzieła na dzień 30.09.2014 r.\n" +
                                        "3. W przypadku niemożności rozpoczęcia, kontynuowania lub zakończenia dzieła w \n" +
                                        "      terminie Wykonawca bezzwłocznie poinformuje Zamawiającego o zaistniałej przeszkodzie.\n" +
                                        "4.  W przypadku określonym w ust.2 Zamawiający, jest uprawniony do:\n" +
                                        "a)\todstąpienia od umowy,\n" +
                                        "b)\tpowierzenia dalszego wykonywania dzieła innemu wykonawcy,\n" +
                                        "c)\tzmiany terminu wykonania dzieła.\n" +
                                        "5.  Zamawiający bezzwłocznie zawiadamia Wykonawcę o podjętej decyzji.\n" +
                                        "\n" +
                                        "§ 5\n" +
                                        "\n" +
                                        "1.  Tytułem wynagrodzenia za wykonane dzieło Wykonawca otrzyma kwotę 10 000 złotych brutto/ słownie: dziesięć tysięcy złotych \n" +
                                        "2.  Wynagrodzenie określone w ust.1 obejmuje wszelkie czynności określone w §1 umowy \n" +
                                        "     oraz wszelkie wydatki Wykonawcy poniesione przez niego w związku lub w wykonaniu \n" +
                                        "     umowy.\n" +
                                        "3.  Wynagrodzenie jest płatne przelewem:\n" +
                                        "a)\tpo dokonaniu odbioru wykonanego dzieła lub umówionej jego części,\n" +
                                        "b)\tpo przedłożeniu rachunku zaakceptowanego przez Zamawiającego,\n" +
                                        "c)\tw terminie 30 dni od przyjęcia prawidłowo wystawionego rachunku na konto wskazane przez Wykonawcę.\n" +
                                        "4.  Wzór rachunku stanowi załącznik do umowy.\n" +
                                        "\n" +
                                        "§ 6\n" +
                                        "1.  Z chwilą odebrania dzieła Wykonawca przenosi na Zamawiającego przysługujące mu \n" +
                                        "     autorskie prawa majątkowe do wykonanego dzieła na następujących polach eksploatacji:\n" +
                                        "a)\tw zakresie utrwalania i zwielokrotniania utworu – wytwarzanie określoną techniką egzemplarzy utworu, w tym techniką drukarską, reprograficzną, zapisu magnetycznego oraz techniką cyfrową  \n" +
                                        "b)\tw zakresie obrotu oryginałem albo egzemplarzami, na których utwór utrwalono – wprowadzanie do obrotu, prezentacja – w tym na stronach internetowych, użyczenie lub najem oryginału albo egzemplarzy\n" +
                                        "c)\tw zakresie rozpowszechniania utworu w inny sposób – publiczne wykonanie, wystawienie, wyświetlenie, odtworzenie oraz nadawanie i reemitowanie, a także publiczne udostępnianie utworu w taki sposób, aby każdy mógł mieć do niego dostęp w miejscu i czasie przez siebie wybranym. \n" +
                                        "2.  Wykonawca nie zachowuje wyłącznego prawa zezwalania na wykonywanie zależnego prawa\n" +
                                        "     autorskiego\n" +
                                        "3.  Kwota określona w § 5 ust.1 umowy obejmuje również wynagrodzenie z tytułu \n" +
                                        "     przeniesienia autorskich praw majątkowych na Zamawiającego.\n" +
                                        "\n" +
                                        "\n" +
                                        "\n" +
                                        "§ 7\n" +
                                        "\n" +
                                        "Jeżeli Wykonawca wykonuje dzieło w sposób wadliwy lub sprzeczny z umowa Zamawiający może skorzystać z uprawnień określonych w art. 636 §1 kodeksu cywilnego.\n" +
                                        "\n" +
                                        "\n" +
                                        "§ 8\n" +
                                        "\n" +
                                        "Wszelkie zmiany niniejszej umowy wymagają formy pisemnej pod rygorem nieważności.\n" +
                                        "\n" +
                                        "\n" +
                                        "§ 9\n" +
                                        "\n" +
                                        "W sprawach spornych właściwym miejscowo jest sąd siedziby Zamawiającego.\n" +
                                        "\n" +
                                        "\n" +
                                        "§ 10\n" +
                                        "Umowę sporządzono w trzech jednobrzmiących egzemplarzach, z których jeden otrzymuje Wykonawca.\n" +
                                        "\n" +
                                        "\n" +
                                        "Wyrażam zgodę na przetwarzanie danych osobowych, a w wypadku umów do projektów UE zgodę na ujawnienie do rozliczeń wartości umowy.                                                                      ",
                                "stworzenie serwisu internetowego", "30.09.2014", "15.07.2014", "10000 złoty", ""
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
                            "zwanym dalej Wykonawcą\n" +
                            " \n" +
                            "o następującej treści:\n" +
                            "§ 1\n" +
                            "Zamawiający powierza wykonanie, a Wykonawca zobowiązuje się wykonać dzieło polegające na:\n" +
                            "wykończeniu łazienki\n" +
                            "(opis zlecanego dzieła)\n" +
                            "\n" +
                            "§ 2 \n" +
                            "(fakultatywne)\n" +
                            "1. Dla wykonania dzieła Zamawiający zobowiązuje się wydać Wykonawcy w terminie \n" +
                            "do dnia 06.08.2013 r. następujące materiały i narzędzia:\n" +
                            "1) kafelki podłogowe\n" +
                            "2) klej\n" +
                            "3) farbę\n" +
                            "2. Wykonawca zobowiązany jest przedstawić rozliczenie z otrzymanych materiałów i narzędzi, \n" +
                            "a niezużyte zwrócić Zamawiającemu w dniu wydania dzieła. \n" +
                            "§ 3\n" +
                            "Termin rozpoczęcia dzieła strony ustaliły na dzień 06.03.2013, a wykonania na dzień 06.04.2013 r.\n" +
                            "§ 4\n" +
                            "(fakultatywne)\n" +
                            "Wykonawca ma prawo powierzyć wykonanie dzieła innej osobie, jednakże jest on odpowiedzialny wobec Zamawiającego za jej działania, jak za własne\n" +
                            "§ 5\n" +
                            "Wykonawcy przysługuje wynagrodzenie za wykonanie dzieła w wysokości 7000 zł (słownie: siedem tysięcy złotych). Zamawiający wypłaci Wykonawcy wynagrodzenie w formie przelewu/gotówki w dniu odbioru wykonanego dzieła, na podstawie wystawionego przez Wykonawcę rachunku.\n" +
                            "§ 6 \n" +
                            "(fakultatywne)\n" +
                            "1. W przypadku jakichkolwiek opóźnień w wykonaniu dzieła Wykonawca zapłaci Zamawiającemu karę umowną w wysokości 1000 zł.\n" +
                            "2. W razie zwłoki w wykonaniu dzieła Zamawiający może odstąpić od umowy bez konieczności wyznaczania dodatkowego terminu.\n" +
                            "§ 7\n" +
                            "Zmiany umowy wymagają formy pisemnej pod rygorem nieważności.\n" +
                            "\n" +
                            "§ 8\n" +
                            "W sprawach nieuregulowanych niniejszą umową będą miały zastosowanie przepisy kodeksu cywilnego.\n" +
                            "§ 9\n" +
                            "Umowę sporządzono w 4 jednobrzmiących egzemplarzach, po 2 dla każdej ze stron.\n" +
                            " \n" +
                            "\n" +
                            " \n" +
                            " ............................................                                                               ……………………………\n" +
                            "Zamawiający                                                                                \tWykonawca\n" +
                            "\n",
                        "wykończenie łazienki", "06.04.2013", "06.03.2013", "7000 złoty", "1000 złoty"
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
