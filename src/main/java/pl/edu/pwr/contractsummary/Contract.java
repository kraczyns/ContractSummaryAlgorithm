package pl.edu.pwr.contractsummary;

import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;

enum ContractType {
    preliminaryAgreement, //umowa przedstępna
    salesAgreement, //umowa sprzedaży
    exchangeContract, //umowa zamiany
    deliveryContract, //umowa dostawy
    contractingContract, //umowa kontraktacji
    contractWork, //umowa o dzieło
    contractForConstructionWorks, //umowa o roboty budowlane
    leaseAgreement, //umowa najmu/dzierżawy/leasingu
    lendingAgreement, // umowa użyczenia
    loanAgreement, // umowa pożyczki
    bankAccountAgreement, // umowa rachunku bankowego
    contractOfMandate, // umowa zlecenia
    agencyAgreement, // umowa agencyjna
    commissionAgreement, // umowa komisu
    contractOfCarriage, // umowa przewozu
    forwardingContract, // umowa spedycji
    insuranceContract, // umowa ubezpieczenia
    storageAgreement, // umowa przechowania
    compositionContract, // umowa składu
    companyAgreement, // umowa spółki
    suretyAgreement, // umowa poręczenia
    donationAgreement, // umowa darowizny
    propertyTransferAgreement, // umowa przekazania nieruchomości
    rentContract, // umowa renty
    adhesiveAgreement, // umowa adhezyjna
    lifeContract, // umowa o dożywocie
    employmentContract, // umowa o pracę
    other;

    @Override
    public String toString() {
        switch(this) {
            case preliminaryAgreement: return "umowa przedstępna";
            case salesAgreement: return "umowa sprzedaży";
            case exchangeContract: return "umowa zamiany";
            case deliveryContract: return "umowa dostawy";
            case contractingContract: return "umowa kontraktacji";
            case contractWork: return "umowa o dzieło";
            case contractForConstructionWorks: return "umowa o roboty budowlane";
            case leaseAgreement: return "umowa najmu/dzierżawy/leasingu";
            case lendingAgreement: return "umowa użyczenia";
            case loanAgreement: return "umowa pożyczki";
            case bankAccountAgreement: return "umowa rachunku bankowego";
            case contractOfMandate: return "umowa zlecenia";
            case agencyAgreement: return "umowa agencyjna";
            case commissionAgreement: return "umowa komisu";
            case contractOfCarriage: return "umowa przewozu";
            case forwardingContract: return "umowa spedycji";
            case insuranceContract: return "umowa ubezpieczenia";
            case storageAgreement: return "umowa przechowania";
            case compositionContract: return "umowa składu";
            case companyAgreement: return "umowa spółki";
            case suretyAgreement: return "umowa poręczenia";
            case donationAgreement: return "umowa darowizny";
            case propertyTransferAgreement: return "umowa przekazania nieruchomości";
            case rentContract: return "umowa renty";
            case adhesiveAgreement: return "umowa adhezyjna";
            case lifeContract: return "umowa o dożywocie";
            case employmentContract: return "umowa o pracę";
            case other: return "inny tekst";
            default: throw new IllegalArgumentException();
        }
    }
}

public class Contract {

    ContractType contractType;
    String conclusionDate;
    String conclusionPlace;
    List<String> sides;
    Text text;

    public Contract(Text text) {
        this.text = text;
        Sentence sentence = selectSentenceWhenContractWordAppearsFirst();
        findContractType(sentence);
        this.sides = new ArrayList<String>();
        findContractSides(sentence);
        findConclusionDate(sentence);
        findConclusionPlace(sentence);
    }

    public Contract() {

    }

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
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (Utils.areStringsSame(word.getContent(), Constants.EMPLOYMENT_CONTRACT)) {
                    this.contractType = ContractType.employmentContract;
                    break;
                } else if (Utils.areStringsSame(word.getContent(), Constants.CONTRACT_OF_MANDATE)) {
                    this.contractType = ContractType.contractOfMandate;
                    break;
                } else if (Utils.areStringsSame(word.getContent(), Constants.CONTRACT_WORK)) {
                    this.contractType = ContractType.contractWork;
                    break;
                } else {
                    for (String element : Constants.LEASE_AGREEMENT) {
                        if (Utils.areStringsSame(word.getContent(), element)) {
                            this.contractType = ContractType.contractWork;
                            break;
                        }
                    }
                }
            }
        }

        if (null == this.contractType) {
            this.contractType = ContractType.other;
        }
    }

    private void findContractSides(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.firstNameLastName || word.getTag() == Tag.otherName) {
                    if (!ignore(word)) {
                        this.sides.add(word.getContent());
                        if (sides.size() == 2) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private Boolean ignore(Word word) {
        if (this.contractType == ContractType.contractOfMandate || this.contractType == ContractType.contractWork || this.contractType == ContractType.employmentContract) {
            if (Utils.areStringsSame(word.getContent(), "pracodawca") || Utils.areStringsSame(word.getContent(), "pracownik")) {
                return true;
            }
        }
        return false;
    }
    private void findConclusionDate(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.date) {
                        this.conclusionDate = word.getContent();
                }
            }
        }
    }

    private void findConclusionPlace(Sentence sentence) {
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.city) {
                    this.conclusionPlace = word.getContent();
                }
            }
        }
    }

    private Sentence selectSentenceWhenContractWordAppearsFirst() {
        List<Sentence> textSentences = text.getSentences();
        for (Sentence sentence : textSentences) {
            for (Word word : sentence.getWords()) {
                if (Utils.areStringsSame(word.getContent(), Constants.CONTRACT)) {
                    return sentence;
                }
            }
        }
        return null;
    }
}
