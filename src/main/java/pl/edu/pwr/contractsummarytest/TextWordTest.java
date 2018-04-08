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
                {"Ala ma psa.", new ArrayList<String>(
                        Arrays.asList("Ala", "mieć", "pies"))},
                {"Kot ma na imię Mruczek.", new ArrayList<String>(
                        Arrays.asList("Kot", "mieć", "na", "imię", "Mruczek"))},
                {"Ala ma kotkę, a Adam psa.", new ArrayList<String>(
                    Arrays.asList("Ala", "mieć", "kotka", "a", "Adam", "pies"))},
                {"Zofia Kowalska ma zwierzątko.", new ArrayList<String>(
                        Arrays.asList("Zofia Kowalska", "mieć", "zwierzątko")
                )},
                {"Urodzona 15.10.1994 r.", new ArrayList<String>(
                        Arrays.asList("Urodzona", "15.10.1994", "rok")
                )},
                {"Ala mieszka przy ul. Grabiszyńskiej 18/5.", new ArrayList<String>(
                        Arrays.asList("Ala", "mieszkać", "przy", "ul. Grabiszyńska 18/5")
                )}
        });
    }

    @Test
    public void testWordSegmentation() {
        Text text = new Text(input);
        assertEquals(expected, Word.wordsToString(text.getWords().get(0)));
    }


}
