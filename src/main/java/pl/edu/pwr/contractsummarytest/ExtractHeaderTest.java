package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Header;
import pl.edu.pwr.contractsummary.Sentence;
import pl.edu.pwr.contractsummary.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ExtractHeaderTest {


    private String input;

    private List<String> expected;

    private String expected2;

    public ExtractHeaderTest(String i, List<String> e, String e2) {
        input = i;
        expected = e;
        expected2 = e2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> sentencesSegmentationData() {
        return Arrays.asList(new Object[][] {
                {
                        "Bolesławiec, \n" +
                                "07.04.2018\n" +
                                "\n" +
                                "Jan Kowalski\t\t\t\t\t\t\t\n" +
                                "Ul. Kwiatowa 7/6, \n" +
                                "Bolesławiec\n" +
                                "\n" +
                                "\tFIRMEX S.A.\n" +
                                "\tPl. Fiołkowy 123 - 167, Bolesławiec" +
                                "\nRozwiązanie umowy o pracę na mocy porozumienia stron.", new ArrayList<String>(
                        Arrays.asList("Bolesławiec, 07.04.2018", "Jan Kowalski Ul. Kwiatowa 7/6, Bolesławiec", "FIRMEX S.A. Pl. Fiołkowy 123 - 167, Bolesławiec")),
                        "Rozwiązanie umowy o pracę na mocy porozumienia stron."
                },
                {
                        "Bolesławiec, \n" +
                                "07.04.2018\n" +
                                "\nRozwiązanie umowy o pracę na mocy porozumienia stron.", new ArrayList<String>(
                        Arrays.asList("Bolesławiec, 07.04.2018", "", "")),
                        "Rozwiązanie umowy o pracę na mocy porozumienia stron."
                },
                {
                        "Rozwiązanie umowy o pracę na mocy porozumienia stron.", new ArrayList<String>(
                        Arrays.asList("", "", "")),
                        "Rozwiązanie umowy o pracę na mocy porozumienia stron."
                }
        });
    }
    @Test
    public void testHeaderExtraction() {
        Text text = new Text(input);
        Header header = text.getHeader();

        assertEquals(expected.get(0), header.getPlaceDate());
        assertEquals(expected.get(1), header.getSideOne());
        assertEquals(expected.get(2), header.getSideTwo());
        assertEquals(expected2, text.getText().trim());
    }

}
