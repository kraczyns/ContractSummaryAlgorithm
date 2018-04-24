package pl.edu.pwr.contractsummarytest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;

import static org.junit.Assert.assertEquals;

public class IntegrationTests {

    Text text;
    Sentence sentence;
    Word word;

    String testdata = "Jan Kowalski\t\t\t\t\t\t\tBolesławiec, 07.04.2018\n" +
            "Ul. Kwiatowa 7/6, Bolesławiec\n" +
            "\n" +
            "\tFIRMEX S.A.\n" +
            "\tPl. Fiołkowy 123 - 167, Bolesławiec\n" +
            "\n" +
            "\n" +
            "Rozwiązanie umowy o pracę na mocy porozumienia stron\n" +
            "\n" +
            "Zwracam się z prośbą o rozwiązanie umowy o pracę zawartej w dniu 07.04.2017 r. pomiędzy Janem Kowalskim a FIRMEX S.A., na mocy porozumienia stron. Jako termin rozwiązania umowy proponuję dzień 20.05.2018 r.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Z poważaniem\n" +
            "07.04.2018, Jan Kowalski\n" +
            "      (data i podpis pracownika)\n" +
            "\n"        ;

    @Before
    public void beforeTest() {
        text = new Text(testdata);
        sentence = text.getSentences().get(0);
        word = sentence.getWords().get(6);
    }
    @Ignore
    @Test
    public void testTextSegmentation() {
        assertEquals(1, text.getSentences().size());
    }

    @Ignore
    @Test
    public void testSentenceSegmentation() {
        assertEquals("umowa",word.getContent());
    }
}
