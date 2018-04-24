package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.contractsummary.types.ContractTermination;
import pl.edu.pwr.contractsummary.types.EmploymentContract;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Contract {

    private IContractTypes iContractType;
    private ContractType contractType;
    private String conclusionDate;
    private String conclusionPlace;
    private List<String> sides;
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

    private ArrayList<String> initializeArray() {
        List<String> sides = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            sides.add("");
        }
        return (ArrayList<String>)sides;
    }

    public String[] getHeadersValues() {
        return new String[]{text.getHeader().getPlaceDate(), text.getHeader().getSideOne(), text.getHeader().getSideTwo()};
    }

    public String[] getGeneralValues() {
        return new String[] {contractType.toString(), null != this.sides ? this.sides.get(0) : "", this.sides.size() == 2 ? this.sides.get(1) : "",
                conclusionPlace, conclusionDate};
    }

    public String[] getDetailsValues() {
        return iContractType.getContractTypeFields();
    }

    public String[] getDetailsHeaders() { return iContractType.getDetailsHeaders(); }

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

    public List<String> getSides() {
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
                    }
                    break;
                } else if (Utils.areStringsSame(word.getContent(), Constants.CONTRACT_WORK)) {
                    if (isContractTermination) {
                        contractType = ContractType.contractWorkTermination;
                        iContractType = new ContractTermination(text);
                    } else {
                        contractType = ContractType.contractWork;
                    }
                    break;
                } else if (Utils.isOnTheList(word.getContent(), Constants.LEASE_AGREEMENT)){
                            if (isContractTermination) {
                                contractType = ContractType.leaseAgreementTermination;
                                iContractType = new ContractTermination(text);
                            } else {
                                contractType = ContractType.leaseAgreement;
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
        int side = 0;
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.firstNameLastName || word.getTag() == Tag.otherName) {
                        sides.set(side, sides.get(side)  + word.getContent() + " ");
                }
                else if (word.getContent().equals("a")) {
                    side++;
                    if (side == 2) {
                        break;
                    }
                } else if (word.getContent().equals("zwać")) {
                    if (side == 1) {
                        break;
                    }
                }
            }
            sides.set(0, sides.get(0).trim());
            sides.set(1, sides.get(1).trim());

        }
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
