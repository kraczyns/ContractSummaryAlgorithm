package pl.edu.pwr.contractsummary.types;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.edu.pwr.contractsummary.Contract;
import pl.edu.pwr.contractsummary.Sentence;
import pl.edu.pwr.contractsummary.Text;
import pl.edu.pwr.contractsummary.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class EmploymentContract extends Contract {

    private ContractPeriod contractPeriod;
    private String definitePeriod;
    private HourlyDimension hourlyDimension;
    private int partTimeDimension;
    private String position;
    private String workingPlace;
    private String startDate;
    private String workingHours;
    private String salary;

    public EmploymentContract() {
    }

    public ContractPeriod getContractPeriod() {
        return contractPeriod;
    }

    public String getDefinitePeriod() {
        return definitePeriod;
    }

    public String getPosition() {
        return position;
    }

    public HourlyDimension getHourlyDimension() {
        return hourlyDimension;
    }

    public int getPartTimeDimension() {
        return partTimeDimension;
    }

    public String getSalary() {
        return salary;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setEmploymentContractFields() {
            definitePeriod = "1 rok";
            contractPeriod = findContractPeriod(getText());
            hourlyDimension = findHourlyDimension(getText());
            partTimeDimension = 1;
            position = findPosition(getText());
            workingPlace = findWorkingPlace(getText());
            startDate = findStartDate(getText());
            workingHours = findWorkingHours(getText());
            salary = findSalary(getText());
    }

    private String findWorkingHours(Text text) {
        return "";
    }

    private String findSalary(Text text) {

        if (null != text.getSentences()) {
            for (Sentence sentence : text.getSentences()) {
                for (int i = 0; i < sentence.getWords().size(); i++) {
                    Word word = sentence.getWords().get(i);
                    if (Utils.isPrize(word.getContent())) {
                        if (sentence.getWords().get(i - 1).getContent().toLowerCase().contains(Constants.BRUTTO) ||
                                sentence.getWords().get(i + 1).getContent().toLowerCase().contains(Constants.BRUTTO)) {
                            return word.getContent();
                        }
                    }
                }
            }
        }
        return "";
    }

    private String findStartDate(Text text) {
        int potentialStarDatePart = 0;
        if (null != text.getSentences()) {
            for (Sentence sentence : text.getSentences()) {
                for (Word word : sentence.getWords()) {
                    if (potentialStarDatePart > 1 && Utils.isDate(word.getContent())) {
                        return word.getContent();
                    }
                    if (Utils.isOnTheList(word.getContent(), Constants.START_DATE)) {
                        potentialStarDatePart++;
                    }
                }
            }
        }
        return "";
    }

    private String findWorkingPlace(Text text) {
        int potentialWorkingPlacePart = 0;
        if (null != text.getSentences()) {
            for (Sentence sentence : text.getSentences()) {
                for (Word word : sentence.getWords()) {
                    if (potentialWorkingPlacePart > 1 && Utils.isAddress(word.getContent())) {
                        return word.getContent();
                    }
                    if (Utils.isOnTheList(word.getContent(), Constants.WORKING_PLACE)) {
                        potentialWorkingPlacePart++;
                    }
                }
            }
        }
        return "";
    }

    private String findPosition(Text text) {
        Boolean isPosition = false;
        if (null != text.getSentences()) {
            for (Sentence sentence : text.getSentences()) {
                for (Word word : sentence.getWords()) {
                    if (isPosition) {
                        return word.getContent();
                    }
                    if (Utils.areStringsSame(word.getContent(), Constants.POSITION)) {
                        isPosition = true;
                    }
                }
            }
        }
        return "";
    }

    private HourlyDimension findHourlyDimension(Text text) {
        return HourlyDimension.fulltime;
    }

    private ContractPeriod findContractPeriod(Text text) {
            if (null != text.getSentences()) {
                for (Word word : text.getSentences().get(0).getWords()) {
                    if (Utils.areStringsSame(word.getContent(), Constants.DEFINITE_PERIOD)) {
                        return ContractPeriod.definitePeriod;
                    }
                    else if (Utils.areStringsSame(word.getContent(), Constants.INDEFINITE_PERIOD)) {
                        definitePeriod = "";
                        return ContractPeriod.indefinitePeriod;
                    }
                    else if (Utils.areStringsSame(word.getContent(), Constants.PROBATIONARY_PERIOD)) {
                        return ContractPeriod.probationaryPeriod;
                    }
                }
            }

            return ContractPeriod.other;
    }

public String[] getEmploymentContractSumarizationFields() {
    String[] values = {
            contractPeriod.toString(), definitePeriod, hourlyDimension.toString(), Integer.toString(partTimeDimension),
            position, workingPlace, startDate, workingHours, salary
            };
    return values;
}
}
