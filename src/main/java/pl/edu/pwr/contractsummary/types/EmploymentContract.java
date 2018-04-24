package pl.edu.pwr.contractsummary.types;

import pl.edu.pwr.contractsummary.IContractTypes;
import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class EmploymentContract implements IContractTypes{

    private ContractPeriod contractPeriod;
    private String definitePeriod;
    private HourlyDimension hourlyDimension;
    private int partTimeDimension;
    private String position;
    private String workingPlace;
    private String startDate;
    private String workingHours;
    private String salary;

    public EmploymentContract(Text text) {
        definitePeriod = "1 rok";
        contractPeriod = findContractPeriod(text);
        hourlyDimension = findHourlyDimension(text);
        partTimeDimension = 1;
        position = findPosition(text);
        workingPlace = findWorkingPlace(text);
        startDate = findStartDate(text);
        workingHours = findWorkingHours(text);
        salary = findSalary(text);
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

    @Override
    public String[] getContractTypeFields() {
        return new String[] {
                contractPeriod.toString(), definitePeriod, hourlyDimension.toString(), Integer.toString(partTimeDimension),
                position, workingPlace, startDate, workingHours, salary
        };
    }

    @Override
    public String[] getDetailsHeaders() {
        return Constants.EMPLOYMENT_CONTRACT_HEADERS;
    }
}
