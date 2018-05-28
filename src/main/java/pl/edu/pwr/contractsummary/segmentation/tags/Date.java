package pl.edu.pwr.contractsummary.segmentation.tags;

import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class Date{

    private int day;
    private int month;
    private int year;

    public Date()
    {

    }

    public Date(String date) {
        String[] dateParts = date.split("\\.");
        day = Integer.parseInt(dateParts[0]);
        month = Integer.parseInt(dateParts[1]);
        year = Integer.parseInt(dateParts[2]);
    }

    public String stringToDate(String string) {
        String[] parts = string.split(" ");
        if (parts.length == 3) {
            for (int i = 0; i < Constants.MONTHS.length; i++) {
                if (Utils.areStringsSame(parts[1], Constants.MONTHS[i])) {
                    return dateToString(Integer.parseInt(parts[0]), i+1, Integer.parseInt(parts[2]));
                }
            }
        }
        return string;
    }

    private String dateToString(int d, int m, int y) {
        return ((d < 10) ? "0" + d : d) + "." + ((m < 10) ? "0" + m : m) + "." + y;
    }

    private int getMonthDays(int month) {
        if (month == 2) {
            return 28;
        } else if (month%2 == 0) {
            return 30;
        }
            return 31;
    }

    private int getPeriodDays(String period) {
        String[] parts = period.split(" ");
        int number = Integer.parseInt(parts[0]);
        switch(parts[1]) {
            case "dniowy":
                return 1*number;
            case "tygodniowy":
                return 7*number;
            case "miesięczny":
                return 30*number;
        }
        return number;
    }

    public static Boolean isEarlier(Date firstDate, Date secondDate) {
        if (firstDate.year == secondDate.year) {
            if (firstDate.month == secondDate.month) {
                if (firstDate.day < secondDate.day) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (firstDate.month < secondDate.month) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (firstDate.year < secondDate.year) {
                return true;
            } else {
                return false;
            }
        }
    }

    public String addPeriodToDate(String content) {
        int daysCounter = getPeriodDays(content);
        int newDay = day + daysCounter;
        if (newDay < getMonthDays(month)) {
            return dateToString(newDay, month, year);
        } else {
            int monthsDays = getMonthDays(month);
            int newMonth = month;
            int newYear = year;
            int newnewDay = newDay;
            while(newDay > monthsDays) {
                newnewDay -= monthsDays;
                monthsDays += getMonthDays(newMonth == 12 ? 1 : newMonth+1);
                newYear = newMonth == 12 ? newYear + 1 : newYear;
                newMonth = newMonth == 12 ? 1 : newMonth + 1;
            }
            return dateToString(newnewDay, newMonth, newYear );
        }
    }
}
