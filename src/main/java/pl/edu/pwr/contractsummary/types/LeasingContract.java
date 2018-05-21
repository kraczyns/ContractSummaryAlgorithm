package pl.edu.pwr.contractsummary.types;

import pl.edu.pwr.contractsummary.IContractTypes;
import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LeasingContract implements IContractTypes {

    private String leaseSubject;
    private int numberOfRooms;
    private String leaseSubjectAddress;
    private ContractPeriod contractPeriod;
    private String definedPeriod;
    private String rent;
    private String denunciationTime;
    private String deposit;

    public LeasingContract(Text text) {
        List<Sentence> chosenSentences = new ArrayList<Sentence>(text.getSentences());
        chosenSentences.remove(0);
        findLeaseSubject(findSentence(chosenSentences, Constants.PLACE_DEFINITION));
        findNumberOfRooms(findSentence(chosenSentences, Constants.ROOMS));
        findLeaseSubjectAddress(findSentence(chosenSentences, Constants.PLACE));
        findContractPeriodAndDefinedPeriod(text);
        findRent(findSentence(chosenSentences, Constants.RENT));
        findDenunciationTime(findSentence(chosenSentences, Constants.DENUNCIATION));
        findDeposit(findSentence(chosenSentences, Constants.DEPOSIT));
    }

    private Sentence findSentence(List<Sentence> sentences, String[] words) {
        for (Sentence sentence : sentences) {
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent(), words)) {
                    return sentence;
                }
            }
        }
        return null;
    }

    private void findLeaseSubject(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if( word.getContent().equals("mieszkalny")) {
                    leaseSubject = "lokal mieszkalny";
                } else if ( word.getContent().equals("użytkowy")) {
                    leaseSubject = "lokal użytkowy";
                }
            }
        }
            leaseSubject = "lokal";
    }

    private void findNumberOfRooms(Sentence sentence) {
        if(null != sentence) {
            int possibleRoomNumber = 0;
            for (Word word : sentence.getWords()) {
                if (Utils.isStringContainingOnlyDigits(word.getContent())) {
                    possibleRoomNumber = Integer.parseInt(word.getContent());
                } else if(possibleRoomNumber!=0 && Utils.isOnTheList(word.getContent(), new String[] {"pokój", "pokoje"} )) {
                    numberOfRooms = possibleRoomNumber;
                    leaseSubject = "lokal mieszkalny";
                    return;
                } else if (possibleRoomNumber!=0 && !Utils.isOnTheList(word.getContent(), new String[] {"pokój", "pokoje"} )) {
                    possibleRoomNumber = 0;
                }
            }
        }
    }

    private void findLeaseSubjectAddress(Sentence sentence) {
        if (null != sentence)
        {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.address) {
                    leaseSubjectAddress = word.getContent();
                    return;
                }
            }
        }
            leaseSubjectAddress = "";
    }

    private void findDenunciationTime(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.period) {
                    denunciationTime = word.getContent();
                    return;
                }
            }
        }
        denunciationTime = "";
    }

    private void findRent(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.prize) {
                    rent = word.getContent();
                    return;
                }
            }
        }
        rent = "";
    }

    private void findDeposit(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.prize) {
                    deposit = word.getContent();
                    return;
                }
            }
        }
        deposit = "";
    }

    private void findContractPeriodAndDefinedPeriod(Text text) {
        Boolean potentialDefinedDatePlace = false;
        for (Sentence sentence : text.getSentences()) {
            for (Word word : sentence.getWords()) {
                // umowa na czas określony
                if (Utils.isOnTheList(word.getContent().toLowerCase(), Constants.DEFINITE_PERIOD_LIST)) {
                    potentialDefinedDatePlace = true;
                } else if (Utils.isOnTheList(word.getContent().toLowerCase(), Constants.INDEFINITE_PERIOD_LIST)) {
                    definedPeriod = "";
                    contractPeriod = ContractPeriod.indefinitePeriod;
                    return;
                }
                if (potentialDefinedDatePlace && word.getTag() == Tag.date) {
                    definedPeriod = word.getContent();
                    contractPeriod = ContractPeriod.definitePeriod;
                    return;
                }
            }
        }
        definedPeriod = "";
        contractPeriod = ContractPeriod.other;
    }

    @Override
    public String[] getContractTypeFields() {
        return new String[] {
            leaseSubject, Integer.toString(numberOfRooms), leaseSubjectAddress, contractPeriod.toString(), definedPeriod, rent, denunciationTime, deposit
        };
    }

    @Override
    public String[] getDetailsHeaders() {
        return Constants.LEASE_AGREEMENT_HEADERS;
    }

    public String getLeaseSubject() {
        return leaseSubject;
    }

    public String getLeaseSubjectAddress() { return leaseSubjectAddress; }

    public int getNumberOfRooms() { return numberOfRooms; }

    public ContractPeriod getContractPeriod() {
        return contractPeriod;
    }

    public String getDefinedPeriod() {
        return definedPeriod;
    }

    public String getRent() {
        return rent;
    }

    public String getDenunciationTime() {
        return denunciationTime;
    }

    public String getDeposit() { return deposit; }
}
