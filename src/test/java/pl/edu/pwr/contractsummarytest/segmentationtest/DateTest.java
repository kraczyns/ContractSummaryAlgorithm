package pl.edu.pwr.contractsummarytest.segmentationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.edu.pwr.contractsummary.segmentation.tags.Date;
import pl.edu.pwr.utils.Utils;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DateTest {
    private String inputPeriod;
    private String inputDate;
    private String expected;

    public DateTest(String ip, String id, String e) {
        inputPeriod = ip;
        inputDate = id;
        expected = e;
    }

    @Test
    public void testAddingPeriodToDate() {
        Date date = new Date(inputDate);
        assertEquals(expected, date.addPeriodToDate(inputPeriod));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> tagSelectionData() {
        return Arrays.asList(new Object[][] {
                {"2 tygodniowy", "01.01.2000", "15.01.2000"},
                {"2 tygodniowy", "18.01.2000", "01.02.2000"},
                {"1 miesięczny", "15.12.1994", "15.01.1995"}
        });
    }
}
