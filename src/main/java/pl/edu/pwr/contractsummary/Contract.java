package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.contractsummary.types.*;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Contract {

    private IContractTypes iContractType;
    private ContractType contractType;
    private String conclusionDate;
    private String conclusionPlace;
    private List<Side> sides;
    private Text text;

    public void setText(Text text) {
        this.text = text;
    }

    public Contract(Text txt) {
        text = txt;
        Sentence sentence = selectSentenceWhenContractWordAppearsFirst();
        findContractType(sentence);
        sides = initializeArray();
        conclusionDate = conclusionPlace = "";
        findContractSides(sentence);
        findConclusionDate(sentence);
        findConclusionPlace(sentence);
    }

    private ArrayList<Side> initializeArray() {
        List<Side> sides = new ArrayList<Side>();
        for (int i = 0; i < 2; i++) {
            sides.add(null);
        }
        return (ArrayList<Side>)sides;
    }

    public String[] getHeadersValues() {
        return new String[]{text.getHeader().getPlaceDate(), text.getHeader().getSideOne(), text.getHeader().getSideTwo()};
    }

    public String[] getGeneralValues() {
        return new String[] {contractType.toString(), "",
                conclusionPlace, conclusionDate};
    }

    public String[][] getSideValues() { return new String[][] {sides.get(0).getContractTypeFields(), sides.get(1).getContractTypeFields()};}

    public String[][] getSideHeaders(String language) { return new String[][] {sides.get(0).getDetailsHeaders(language), sides.get(1).getDetailsHeaders(language)};}

    public String[] getDetailsValues() {
        if (null != iContractType) {
            return iContractType.getContractTypeFields();
        } else {
            return new String[]{""};
        }
    }

    public String[] getDetailsHeaders(String language) {
        if (null != iContractType) {
        return iContractType.getDetailsHeaders(language);
    } else {
        return new String[]{"null"};
    } }

    public Text getText() { return text; }

    public String getContractType() {
        return contractType.toString();
    }

    public String getConclusionDate() {
        return conclusionDate;
    }

    public String getConclusionPlace() {
        return conclusionPlace;
    }

    public List<Side> getSides() {
        return sides;
    }

    private void findContractType(Sentence sentence) {

        Boolean isContractTermination = false;
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent(), Constants.DENUNCIATION)) {
                    isContractTermination = true;
                }
                else if (Utils.areStringsSame(word.getContent(), Constants.EMPLOYMENT_CONTRACT)) {
                    if (isContractTermination) {
                        contractType = ContractType.employmentContractTermination;
                        iContractType = new ContractTermination(text);
                    } else {
                        contractType = ContractType.employmentContract;
                        iContractType = new EmploymentContract(text);
                    }
                    break;
                } else if (Utils.areStringsSame(word.getContent(), Constants.CONTRACT_OF_MANDATE)) {
                    if (isContractTermination) {
                        contractType = ContractType.contractOfMandateTermination;
                        iContractType = new ContractTermination(text);
                    } else {
                        contractType = ContractType.contractOfMandate;
                        iContractType = new ContractOfMandate(text);
                    }
                    break;
                } else if (Utils.areStringsSame(word.getContent(), Constants.CONTRACT_WORK)) {
                    if (isContractTermination) {
                        contractType = ContractType.contractWorkTermination;
                        iContractType = new ContractTermination(text);
                    } else {
                        contractType = ContractType.contractWork;
                        iContractType = new ContractWork(text);
                    }
                    break;
                } else if (Utils.isOnTheList(word.getContent(), Constants.LEASE_AGREEMENT)){
                            if (isContractTermination) {
                                contractType = ContractType.leaseAgreementTermination;
                                iContractType = new ContractTermination(text);
                            } else {
                                contractType = ContractType.leaseAgreement;
                                iContractType = new LeasingContract(text);
                            }
                            break;
                }
            }
        }

        if (null == this.contractType) {
            this.contractType = ContractType.other;
        }

    }

    private void findContractSides(Sentence sentence) {
            sides.set(0, Side.extractSides(sentence).get(0));
            sides.set(1, Side.extractSides(sentence).get(1));
    }

    private void findConclusionDate(Sentence sentence) {
         if (null != sentence && !contractType.toString().contains("zerwanie")) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.date) {
                    conclusionDate = word.getContent();
                }
            }
        }
        if (conclusionDate.isEmpty() && !text.getHeader().placeDate.isEmpty()) {
            conclusionDate = text.getHeader().extractDate();
        }
    }

    private void findConclusionPlace(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.city) {
                    conclusionPlace = word.getContent();
                }
            }
        }
        if (conclusionPlace.isEmpty() && !text.getHeader().placeDate.isEmpty()) {
            conclusionPlace = text.getHeader().extractPlace();
        }
    }

    private Sentence selectSentenceWhenContractWordAppearsFirst() {
        List<Sentence> textSentences = text.getSentences();
        for (Sentence sentence : textSentences) {
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent(), Constants.CONTRACT)) {
                    return sentence;
                }
            }
        }
        return null;
    }
}
