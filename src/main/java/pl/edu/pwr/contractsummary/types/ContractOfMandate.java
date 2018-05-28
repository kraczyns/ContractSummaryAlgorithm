package pl.edu.pwr.contractsummary.types;

import com.sun.org.apache.xpath.internal.operations.Bool;
import pl.edu.pwr.contractsummary.IContractTypes;
import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.contractsummary.segmentation.tags.Date;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ContractOfMandate implements IContractTypes {

    private String description;
    private String startDate;
    private String endDate;
    private String salary;

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSalary() {
        return salary;
    }

    public ContractOfMandate(Text text) {
        findDescription(text);
        findStartEndDate(text);
        findSalary(text);
    }

    private Sentence findSentenceWithCounter(List<Sentence> sentences, String[] words, int max) {
        for (Sentence sentence : sentences) {
            int counter = 0;
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent(), words)) {
                    counter ++;
                }
            }
            if (counter >= max) {
                return sentence;
            }
        }
        return null;
    }

    private void findSalary(Text text) {
        Sentence sentence = Sentence.findSentence(text, Constants.SALARY, Tag.prize);

        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.prize) {
                    salary = word.getContent();
                    return;
                }
            }
        }
        salary = "";
    }

    private void findStartEndDate(Text text) {

        Sentence sentence = Sentence.findSentence(text, Constants.START_DATE, Tag.date);
        List<String> dates = new ArrayList<String>();
        startDate = "";
        endDate = "";
        if (null == sentence) {
            sentence = Sentence.findSentenceWithCounter(text.getSentences(), new String[] {"zobowiązywać", "termin", "dzień"}, 2);
        }
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.date ) {
                    dates.add(word.getContent());
                }
                if (dates.size() == 2) {
                    if (Date.isEarlier(new Date(dates.get(0)), new Date(dates.get(1))))
                    {
                        startDate = dates.get(0);
                        endDate = dates.get(1);
                    } else {
                        startDate = dates.get(1);
                        endDate = dates.get(0);
                    }
                    return;
                }
            }
        }
        if (dates.size() != 2) {
            if (dates.size() == 1) {
                startDate = dates.get(0);
                Sentence tryOneMoreTime = Sentence.findSentence(text, Constants.END_DATE, Tag.date);
                if (null != tryOneMoreTime) {
                    for (Word wordAgain : tryOneMoreTime.getWords()) {
                        if (wordAgain.getTag() == Tag.date) {
                            endDate = wordAgain.getContent();
                            return;
                        }
                    }
                }
            } else {
                Sentence tryOneMoreTime = Sentence.findSentence(text, Constants.END_DATE, Tag.date);
                if (null != tryOneMoreTime) {
                    for (Word wordAgain : tryOneMoreTime.getWords()) {
                        if (wordAgain.getTag() == Tag.date) {
                            endDate = wordAgain.getContent();
                            return;
                        }
                    }
                }
            }
        }
    }

    private void findDescription(Text text) {
        Sentence sentence = findSentenceWithCounter(text.getSentences(), Constants.CONTRACT_OF_MANDATE_DESC, 4);
        description = "";
        if(null != sentence) {
            String[] parts = sentence.getContent().split(":");
            if (parts.length > 1) {
                String[] otherParts = parts[1].split(" ");
                for (String part : otherParts) {
                    Word word = new Word(part);
                    if (!(null != word.getMorfologikOutput() && word.getMorfologikOutput().contains("subst")) && description.split(" ").length == 3) {
                        description = description.trim().replaceAll("\\.|,","");
                        return;
                    }
                    if (!Utils.isStringContainingDigits(part.trim()) && part != "") {
                        description += Word.useMorfologik(part) + " ";
                    }

                }
            }
        }

    }

    @Override
    public String[] getContractTypeFields() {
        return new String[] { description, startDate, endDate, salary };
    }

    @Override
    public String[] getDetailsHeaders(String language) {
        return language.equals("ENG") ? Constants.CONTRACT_OF_MANDATE_DETAILS_ENG : Constants.CONTRACT_OF_MANDATE_DETAILS_PL;
    }
}
