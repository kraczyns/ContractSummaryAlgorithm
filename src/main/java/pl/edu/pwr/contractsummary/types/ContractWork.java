package pl.edu.pwr.contractsummary.types;

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

public class ContractWork implements IContractTypes {

    private String description;
    private String deadline;
    private String startDate;
    private String salary;
    private String delayPenalty;

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getSalary() {
        return salary;
    }

    public String getDelayPenalty() {
        return delayPenalty;
    }

    public ContractWork(Text text) {
        findDescription(text);
        findDeadlineStartDate(text);
        findSalary(text);
        findDelayPenalty(text);
    }

    private Sentence findSentence(Text text, String[] words, Tag type) {
        for (Sentence sentence : text.getSentences()) {
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent(), words)) {
                    for (Word part : sentence.getWords()) {
                        if (part.getTag() == type) {
                            return sentence;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void findDelayPenalty(Text text) {
        Sentence sentence = findSentence(text, Constants.PENALTY, Tag.prize);

        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.prize) {
                    delayPenalty = word.getContent();
                    return;
                }
            }
        }

        delayPenalty = "";
    }

    private void findSalary(Text text) {
        Sentence sentence = findSentence(text, Constants.SALARY, Tag.prize);

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

    private void findDeadlineStartDate(Text text) {
        Sentence sentence = findSentence(text, Constants.START_DATE, Tag.date);
        List<String> dates = new ArrayList<String>();
        startDate = "";
        deadline = "";
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
                        deadline = dates.get(1);
                    } else {
                        startDate = dates.get(1);
                        deadline = dates.get(0);
                    }
                    return;
                }
            }
        }

        if (dates.size() != 2) {
            if (dates.size() == 1) {
                startDate = dates.get(0);
                Sentence tryOneMoreTime = findSentence(text, Constants.END_DATE, Tag.date);
                if (null != tryOneMoreTime) {
                    for (Word wordAgain : tryOneMoreTime.getWords()) {
                        if (wordAgain.getTag() == Tag.date) {
                            deadline = wordAgain.getContent();
                            return;
                        }
                    }
                }
            } else {
                Sentence tryOneMoreTime = findSentence(text, Constants.END_DATE, Tag.date);
                if (null != tryOneMoreTime) {
                    for (Word wordAgain : tryOneMoreTime.getWords()) {
                        if (wordAgain.getTag() == Tag.date) {
                            deadline = wordAgain.getContent();
                            return;
                        }
                    }
                }
            }
        }

    }

    private void findDescription(Text text) {
        Sentence sentence = Sentence.findSentenceWithCounter(text.getSentences(), Constants.CONTRACT_WORK_DESC, 4);
        description = "";
        Boolean isDesc = false;
        if(null != sentence) {
            for (Word word : sentence.getWords()) {
                if (isDesc) {
                    if (description.split(" ").length > 3 || Utils.areStringsSame(word.getContent(), "opis")) {
                        description = description.trim().replaceAll("\\.|,", "");
                        return;
                    }
                    if (!(null != word.getMorfologikOutput() && word.getMorfologikOutput().contains("subst")) && description.split(" ").length == 3) {
                        description = description.trim().replaceAll("\\.|,", "");
                        return;
                    }
                    if (!Utils.isStringContainingDigits(word.getContent().trim()) && word.getContent() != "") {
                        description += word.getContent() + " ";
                    }
                }
                if (word.getTag() == Tag.descMark) {
                    isDesc = true;
                }
            }
        }
        description = description.trim().replaceAll("\\.","");
    }

    @Override
    public String[] getContractTypeFields() {
        return new String[] {description, deadline, startDate, salary, delayPenalty};
    }

    @Override
    public String[] getDetailsHeaders(String language) {
        return language.equals("ENG") ? Constants.CONTRACT_WORK_DETAILS_ENG : Constants.CONTRACT_WORK_DETAILS_PL;
    }
}
