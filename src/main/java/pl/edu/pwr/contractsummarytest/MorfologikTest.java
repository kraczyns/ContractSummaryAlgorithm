package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Sentence;
import pl.edu.pwr.contractsummary.Text;
import pl.edu.pwr.contractsummary.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MorfologikTest {

    private String input;

    private String expected;

    public MorfologikTest(String i, String e) {
        input = i;
        expected = e;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> sentencesSegmentationData() {
        return Arrays.asList(new Object[][] {
                {"Alicja Kowalska", "Alicja Kowalska"},
                {"Poznaniu", "Poznań"},
                {"pracodawcą", "pracodawca"},
                {"ulicy", "ulica"},
                {"Adamem Nowakiem", "Adam Nowak"}
});
    }
    @Test
    public void testMorfologik() {
        Word word = new Word(input);
        assertEquals(expected, word.getContent());
    }
}
