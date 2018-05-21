package pl.edu.pwr.contractsummarytest.typestest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.types.LeasingContract;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LeasingContractTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Umowa najmu lokalu\n" +
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
                        "zwanym dalej Najemcą.\n" +
                        "\n" +
                        "§ 1\n" +
                        "Wynajmujący oświadcza, że jest właścicielem lokalu położonego we Wrocławiu przy ulicy \n" +
                        "Bolesława Prusa 19/7 o powierzchni 30 m² składającego się z 2 pokoi, kuchni oraz łazienki.\n" +
                        "\n" +
                        "§ 2\n" +
                        "1. Wynajmujący oddaje Najemcy do używania cały lokal użytkowy, o którym mowa w § 1, wraz ze znajdującymi się w nim urządzeniami i wyposażeniem, których szczegółowy wykaz znajduje się w załączniku nr 1 do niniejszej umowy.\n" +
                        "2. Najemca nie wnosi zastrzeżeń do stanu technicznego lokalu użytkowego.\n" +
                        "§ 3\n" +
                        "1.  Czynsz najmu strony określiły w wysokości 1600 zł miesięcznie \n" +
                        "(słownie: tysiąc sześćset złoty).\n" +
                        "2.  Czynsz najmu, o którym mowa w ust. 1, płatny jest przez Najemcę w terminie do 15 dnia każdego miesiąca, na rachunek bankowy Wynajmującego w Banku PKO nr 821371239214239848543465.\n" +
                        "§ 4\n" +
                        "Strony ustalają, że wszelkie koszty i świadczenia związane z eksploatacją lokalu mieszkalnego, przez czas trwania umowy, ponosić będzie najemca.\n" +
                        "§ 5\n" +
                        "1. W dniu zawarcia niniejszej umowy Najemca wpłaca kaucję w wysokości 3200 zł (słownie: trzy tysiące dwieście złoty). \n" +
                        "2. Kaucja podlega zwrotowi, w dniu zakończenia umowy, po przekazaniu lokalu Wynajmującemu w stanie niepogorszonym, wynikającym z normalnej eksploatacji.\n" +
                        "3. Wynajmujący może dokonać potrącenia z kaucji wszelkich roszczeń pieniężnych przysługujących mu przeciwko Najemcy na dzień zwrotu lokalu użytkowego stanowiącego przedmiot najmu, \n" +
                        "a w szczególności z tytułu niezapłaconego czynszu, odsetek za nieterminowe płatności czynszu, naprawienia szkód wyrządzonych przez Najemcę w przedmiocie najmu.\n" +
                        "§ 6\n" +
                        "1. Umowa najmu została zawarta na czas oznaczony do dnia 29.12.2018. Po upływie tego terminu umowa niniejsza przestaje obowiązywać.\n" +
                        "2. Stronom przysługuje prawo do wcześniejszego rozwiązania umowy z zachowaniem 1 miesięcznego okresu wypowiedzenia.\n" +
                        "§ 7\n" +
                        "1. Wszelkie adaptacje i ulepszenia przedmiotu wynajmu wymagają pisemnej zgody Wynajmującego.\n" +
                        "2. Najemca nie może, bez pisemnej zgody Wynajmującego, oddać osobie trzeciej lokalu użytkowego lub jego części do używania.  \n" +
                        "§ 8\n" +
                        "1. Wszelkie zmiany lub uzupełnienia niniejszej umowy wymagają dla swej ważności formy pisemnej w postaci aneksu.\n" +
                        "2.   W sprawach nieuregulowanych niniejszą umową stosuje się przepisy Kodeksu cywilnego.\n" +
                        "3.   Umowę sporządzono w 2 jednobrzmiących egzemplarzach, po 1 dla każdej ze stron.\n" +
                        " \n",
                        "lokal mieszkalny",
                        "2",
                        "ul. Bolesława Prus 19/7",
                        "czas określony", "29.12.2018",
                        "1600 złoty",
                        "1 miesięczny",
                        "3200 złoty"},
                {
                    "Umowa najmu lokalu mieszkalnego\n" +
                            "Zawarta w dniu 13.04.2008 r. w Poznaniu pomiędzy:\n" +
                            "1) Janiną Boroń, zamieszkałą w Poznaniu przy ul. Głównej 8, legitymującą się dowodem\n" +
                            "osobistym ABB 879809, zwaną dalej Wynajmującą,\n" +
                            "a\n" +
                            "2) Moniką Konecką, zamieszkałą w Poznaniu, ul. Dworcowa 17, legitymującą się dowodem\n" +
                            "osobistym AAA 0000000, zwaną dalej Najemcą,\n" +
                            "o treści następującej:\n" +
                            "§ 1\n" +
                            "Wynajmująca oświadcza, że jest właścicielką lokalu mieszkalnego o pow. 46 mkw., składającego\n" +
                            "się z 2 pokoi, przedpokoju, łazienki i kuchni, położonego w Warszawie przy ul. Słonecznej 4\n" +
                            "§ 2\n" +
                            "Wynajmująca oddaje Najemcy do używania ww. lokal wraz z wyposażeniem (lista przedmiotów\n" +
                            "zostaje załączona do niniejszej umowy w formie załącznika).\n" +
                            "§ 3\n" +
                            "Umowa zostaje zawarta na czas nieoznaczony.\n" +
                            "§ 4\n" +
                            "Najemca zobowiązuje się do zapłaty Wynajmującej czynszu najmu w wysokości 1 100 (słownie:\n" +
                            "tysiąc sto) zł miesięcznie. Czynsz będzie płatny z góry w terminie do 10. każdego miesiąca.\n" +
                            "§ 5\n" +
                            "Opłaty za wodę, gaz, c.o. i energię elektryczną pokrywa Najemca.\n" +
                            "§ 6\n" +
                            "Najemca zobowiązuje się do wykorzystywania lokalu wyłącznie na cele mieszkaniowe.\n" +
                            "§ 7\n" +
                            "Wydanie lokalu Najemcy nastąpiło w dniu dzisiejszym.\n" +
                            "§ 8\n" +
                            "W kwestii wypowiedzenia niniejszej umowy zastosowanie mają przepisy Kodeksu cywilnego\n" +
                            "oraz ustawy z 21 czerwca 2001 r. o ochronie praw lokatorów, mieszkaniowym zasobie gminy\n" +
                            "i o zmianie Kodeksu cywilnego.\n" +
                            "§ 9\n" +
                            "Po ustaniu stosunku najmu Najemca zobowiązany jest zwrócić lokal Wynajmującej w stanie\n" +
                            "niepogorszonym.\n" +
                            "§ 10\n" +
                            "Dla zabezpieczenia roszczeń Wynajmującej z tytułu czynszu najmu oraz z tytułu naprawienia\n" +
                            "ewentualnych szkód – Najemca wpłaca kaucję w wysokości 2 200 (słownie: dwa tysiące\n" +
                            "dwieście) zł, co Wynajmująca potwierdza.\n" +
                            "§ 11\n" +
                            "W sprawach nieuregulowanych umową zastosowanie mają przepisy ustawy o ochronie praw\n" +
                            "lokatorów, mieszkaniowym zasobie gminy i o zmianie Kodeksu cywilnego oraz przepisy\n" +
                            "Kodeksu cywilnego.\n" +
                            "§ 12\n" +
                            "Zmiana umowy wymaga formy pisemnej pod rygorem nieważności.\n" +
                            "§ 13\n" +
                            "Umowę sporządzono w dwóch jednobrzmiących egzemplarzach, po jednym dla każdej ze stron.\n" +
                            " Wynajmujący Najemca\n" +
                            "\n" +
                            "……………………………… ………………………………\n" +
                            "Załącznik:\n" +
                            "Lista przedmiotów w lokalu.",
                        "lokal mieszkalny",
                        "2",
                        "ul. Słoneczny 4",
                        "czas nieokreślony",
                        "",
                        "1100 złoty",
                        "",
                        "2200 złoty"
                }
        });
    }

    private String input;
    private String leaseSubject;
    private String numberOfRooms;
    private String leaseSubjectAddress;
    private String contractPeriod;
    private String definedPeriod;
    private String rent;
    private String denunciationTime;
    private String deposit;

    private LeasingContract leasingContract;

    public LeasingContractTest(String i, String ls, String nr, String lsa, String cp, String dp, String r, String dt, String d ) {
        input = i;
        leaseSubject = ls;
        numberOfRooms = nr;
        leaseSubjectAddress = lsa;
        contractPeriod = cp;
        definedPeriod = dp;
        rent = r;
        denunciationTime = dt;
        deposit = d;

    }

    @Before
    public void init() {
        Contract contract = new Contract(new Text(input));
        leasingContract = new LeasingContract(contract.getText());
    }

    @Test
    public void testFindingLeaseSubject() {
        Assert.assertEquals(leaseSubject, leasingContract.getLeaseSubject());
    }

    @Test
    public void testFindingNumberOfRooms() { Assert.assertEquals(numberOfRooms, Integer.toString(leasingContract.getNumberOfRooms()));}

    @Test
    public void testFindingLeaseSubjectAddress() { Assert.assertEquals(leaseSubjectAddress, leasingContract.getLeaseSubjectAddress());}

    @Test
    public void testFindingContractPeriod() { Assert.assertEquals(contractPeriod, leasingContract.getContractPeriod().toString()); }

    @Test
    public void testFindingDefinedPeriod() {
        Assert.assertEquals(definedPeriod, leasingContract.getDefinedPeriod());
    }

    @Test
    public void testFindingRent() {
        Assert.assertEquals(rent, leasingContract.getRent());
    }

    @Test
    public void testFindingDenunciationTime() {
        Assert.assertEquals(denunciationTime, leasingContract.getDenunciationTime());
    }

    @Test
    public void testFindingDeposit() {
        Assert.assertEquals(deposit, leasingContract.getDeposit());
    }
}
