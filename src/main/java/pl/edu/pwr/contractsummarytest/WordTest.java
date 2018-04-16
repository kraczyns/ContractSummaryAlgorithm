package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Word;
import pl.edu.pwr.contractsummary.Tag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class WordTest {

    private String input;
    private Tag expected;

    public WordTest(String i, Tag e) {
        input = i;
        expected = e;
    }

    @Test
    public void testWordTagging() {
        Word word = new Word(input);
        assertEquals(expected, word.getTag());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> tagSelectionData() {
        return Arrays.asList(new Object[][] {
                {"10.08.2018", Tag.date},
                {"2500 zł", Tag.prize},
                {"ul. Dworcowa 10", Tag.address},
                {"Kazimierz Boroń", Tag.firstNameLastName},
                {"Poznań", Tag.city},
                {"Polinvest", Tag.otherName},
                {"Pracodawca", Tag.text},
                {"kot", Tag.text}
        });
    }


}
