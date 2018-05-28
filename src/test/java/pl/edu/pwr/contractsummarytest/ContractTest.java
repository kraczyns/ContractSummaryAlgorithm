package pl.edu.pwr.contractsummarytest;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.segmentation.Text;

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
                {Field.CONTRACT_TYPE, "Bolesławiec, \n" +
                        "07.04.2018\n" +
                        "\n" +
                        "Jan Kowalski\t\t\t\t\t\t\t\n" +
                        "Ul. Kwiatowa 7/6, \n" +
                        "Bolesławiec\n" +
                        "\n" +
                        "\tFIRMEX S.A.\n" +
                        "\tPl. Fiołkowy 123 - 167\n" +
                        "\tBolesławiec\n" +
                        "\n" +
                        "\n" +
                        "Rozwiązanie umowy o pracę na mocy porozumienia stron\n" +
                        "\n" +
                        "Zwracam się z prośbą o rozwiązanie umowy o pracę zawartej w dniu 07.04.2017 r. pomiędzy Janem Kowalskim a FIRMEX S.A., na mocy porozumienia stron. Jako termin rozwiązania umowy proponuję dzień 20.05.2018 r.", "zerwanie umowy o pracę"},
                {Field.CONTRACT_TYPE, "Olsztyn dnia 31.01.2008 r.\n" +
                        "Piekarnia sp. j. Hanna Buła, Roman Kajzerka\n" +
                        "Ul. Warmińska 90\n" +
                        "09-890 Olsztyn\n" +
                        "Lidia Fijałkowska\n" +
                        "Ul. Piekarnicza 8\n" +
                        "30-530 Suwałki\n" +
                        "(dane pracownika)\n" +
                        "Rozwiązanie umowy o pracę bez wypowiedzenia\n" +
                        "Na podstawie art. 52 kodeksu pracy rozwiązuje umowę o pracę zawartą dnia\n" +
                        "1.12.2007 r. w Olsztynie pomiędzy „Piekarnia” Hanna Buła, Roman Kajzerka sp. j. a Lidią\n" +
                        "Fijałkowską bez zachowania okresu wypowiedzenia.\n" +
                        "Przyczyną rozwiązania umowy o pracę jest:\n" +
                        "Stawienie się pracownika w dniu 30.01.2008 r. w pracy pod wpływem alkoholu.\n" +
                        "Informuję jednocześnie, że przysługuje Pani prawo do wniesienia odwołania do Sądu\n" +
                        "Rejonowego w Olsztynie- VII Wydział Pracy i Ubezpieczeń Społecznych. Termin na\n" +
                        "wniesienie odwołania wynosi 14 dni od dnia otrzymania niniejszego pisma.\n" +
                        "Pracodawca\n" +
                        "……………………………\n" +
                        "(podpis pracodawcy)\n" +
                        "Niniejsze pismo otrzymałem\n" +
                        "……………………………………\n" +
                        "(podpis pracownika) ", "zerwanie umowy o pracę"},
                {Field.CONTRACT_TYPE, "Olsztyn dnia 31.01.2008 r.\n" +
                        "Piekarnia sp. j. Hanna Buła, Roman Kajzerka\n" +
                        "Ul. Warmińska 90\n" +
                        "09-890 Olsztyn\n" +
                        "Lidia Fijałkowska\n" +
                        "Ul. Piekarnicza 8\n" +
                        "30-530 Suwałki\n" +
                        "(dane pracownika)\n" +
                        "Rozwiązanie umowy o pracę bez wypowiedzenia\n" +
                        "Na podstawie art. 52 kodeksu pracy rozwiązuje umowę o pracę zawartą dnia\n" +
                        "1.12.2007 r. w Olsztynie pomiędzy „Piekarnia” Hanna Buła, Roman Kajzerka sp. j. a Lidią\n" +
                        "Fijałkowską bez zachowania okresu wypowiedzenia.\n" +
                        "Przyczyną rozwiązania umowy o pracę jest:\n" +
                        "Stawienie się pracownika w dniu 30.01.2008 r. w pracy pod wpływem alkoholu.\n" +
                        "Informuję jednocześnie, że przysługuje Pani prawo do wniesienia odwołania do Sądu\n" +
                        "Rejonowego w Olsztynie- VII Wydział Pracy i Ubezpieczeń Społecznych. Termin na\n" +
                        "wniesienie odwołania wynosi 14 dni od dnia otrzymania niniejszego pisma.\n" +
                        "Pracodawca\n" +
                        "……………………………\n" +
                        "(podpis pracodawcy)\n" +
                        "Niniejsze pismo otrzymałem\n" +
                        "……………………………………\n" +
                        "(podpis pracownika) ", "zerwanie umowy o pracę"},
                {Field.CONTRACT_TYPE, "Warszawa, dn. 18.09.2006 r.\n" +
                        "Wynajmujący:\n" +
                        "Kazimierz Boroń\n" +
                        "Ul. Konwaliowa 3 m. 10\n" +
                        "00-965 Warszawa\n" +
                        "Do Najemcy\n" +
                        "Heleny Ludowskiej\n" +
                        "Ul. Pułkowników 8 m. 10\n" +
                        "00-873 Warszawa\n" +
                        "Wypowiedzenie umowy najmu\n" +
                        "Na podstawie art. 685 Kodeksu cywilnego:\n" +
                        "– wypowiadam umowę najmu zawartą dnia 14.04.2005 r. dotyczącą lokalu użytkowego\n" +
                        "położonego w Warszawie przy ul. Pułkowników 8 lok. 10 – najemcy, pani Helenie Ludowskiej, ze\n" +
                        "skutkiem od 1.10.2006 r.\n" +
                        "Uzasadnienie\n" +
                        "Najemca – pomimo pisemnego upomnienia – nadal używa lokalu w sposób sprzeczny z umową.\n" +
                        "W lokalu miała być prowadzona „cicha” działalność gospodarcza, tymczasem odbywają się tam\n" +
                        "całonocne głośne spotkania towarzyskie, które przeszkadzają innym lokatorom. Kilkakrotnie\n" +
                        "interweniowała w tej sprawie policja. Przed miesiącem goście najemcy wybili szybę na klatce\n" +
                        "schodowej. Szkoda do tej pory nie została naprawiona.\n" +
                        " Wynajmujący\n" +
                        "……………………………..\n" +
                        " (własnoręczny podpis)", "zerwanie umowy najmu"},
                {Field.CONTRACT_TYPE, "Koszalin dnia 17.09.2008 r.\n" +
                        "Katarzyna Strzelecka\n" +
                        "Ul. Nadmorska 9\n" +
                        "70-095 Koszalin\n" +
                        "„Piękne włosy” sp. z o.o.\n" +
                        "Ul. B. Chrobrego 110\n" +
                        "70-100 Koszalin\n" +
                        "Rozwiązanie umowy o pracę bez wypowiedzenia\n" +
                        "Zgodnie z art. 55 kodeksu pracy rozwiązuje umowę o pracę zawartą dnia 1.03.2008 w\n" +
                        "Koszalinie pomiędzy Piękne włosy sp. z o.o. a Katarzyną Strzelecką bez zachowania okresu\n" +
                        "wypowiedzenia.\n" +
                        "Przyczyną rozwiązania umowy jest:\n" +
                        "Nie wypłacanie przez pracodawcę wynagrodzenia za 4 kolejne miesiące.\n" +
                        "Z poważaniem\n" +
                        "…………………….\n" +
                        "(podpis pracownika)\n" +
                        "Niniejsze pismo otrzymałem\n" +
                        "…………………………………\n" +
                        "(podpis pracodawcy)", "zerwanie umowy o pracę"},
                {Field.CONTRACT_TYPE, "Gniezno dnia 1.01.2009 r.\n" +
                        "\n" +
                        "Hurt-Detal S.A.\n" +
                        "Ul. Kwitnąca 9\n" +
                        "20-489 Gniezno\n" +
                        "Jan Kowalski\n" +
                        "Ul. Katedralna 20\n" +
                        "20-489 Gniezno\n" +
                        "Rozwiązanie umowy o pracę za porozumieniem stron\n" +
                        "Na podstawie art. 30 §1 pkt 1kodeksu pracy rozwiązujemy umowę o pracę zawartą\n" +
                        "dnia 5 października 2008 r. w Gnieźnie pomiędzy Hurt-Detal S.A. a Janem Kowalskim.\n" +
                        " Pracodawca Pracownik\n" +
                        " …………………………… ..…………………………..\n" +
                        " (podpis pracodawcy) (podpis pracownika)", "zerwanie umowy o pracę"},
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
     /*   Assume.assumeTrue(field == Field.CONTRACT_SIDES);
        Contract contract = new Contract(new Text(input));
        String tmp = "";
        for (String side : contract.getSides())
        {
            tmp += side + " ";
        }
        assertEquals(expected, tmp.trim());
    */
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
