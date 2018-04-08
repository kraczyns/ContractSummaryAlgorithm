package pl.edu.pwr.contractsummarytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class WordTest {

    private String input;

    private String expected;

    public WordTest(String i, String e) {
        input = i;
        expected = e;
    }

    @Test
    public void testSentenceSegmentation() {
        Word word = new Word(input);
        assertEquals(expected, word.getTag());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> tagSelectionData() {
        return Arrays.asList(new Object[][] {
                {"10.08.2018", "date"},
                {"2500 zł", "prize"},
                {"ul. Dworcowa 10", "address"},
                {"Kazimierz Boroń", "firstNameLastName"},
                {"Poznań", "city"},
                {"Polinvest", "otherName"},
                {"Pracodawca", "otherName"},
                {"kot", "text"}
        });
    }


}
