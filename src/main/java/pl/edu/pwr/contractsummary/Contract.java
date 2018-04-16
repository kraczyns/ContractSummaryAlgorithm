package pl.edu.pwr.contractsummary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.edu.pwr.contractsummary.types.EmploymentContract;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
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
    Document document;

    public void setText(Text text) {
        this.text = text;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Contract(Text text) {
        this.text = text;
        Sentence sentence = selectSentenceWhenContractWordAppearsFirst();
        findContractType(sentence);
        this.sides = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            this.sides.add("");
        }
        conclusionDate = conclusionPlace = "";
        findContractSides(sentence);
        findConclusionDate(sentence);
        findConclusionPlace(sentence);
        createContractSumarizationXML();
        if (getContractType().equals(ContractType.employmentContract.toString())) {
            EmploymentContract ec = new EmploymentContract();
            ec.setText(text);
            ec.setDocument(getDocument());
            ec.setEmploymentContractFields();
        }
    }

    public Contract() {

    }

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
                if (Utils.isOnTheList(word.getContent(), Constants.CONTRACT)) {
                    return sentence;
                }
            }
        }
        return null;
    }

    private void createContractSumarizationXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("CONTRACT");
            doc.appendChild(rootElement);
            Element headers = doc.createElement("HEADERS");
            rootElement.appendChild(headers);
            Element placeDate = doc.createElement("PLACE_DATE_H");
            placeDate.appendChild(doc.createTextNode(this.text.getHeader().getPlaceDate()));
            headers.appendChild(placeDate);
            Element hFirstSide = doc.createElement("FIRST_SIDE_H");
            hFirstSide.appendChild(doc.createTextNode(this.text.getHeader().getSideOne()));
            headers.appendChild(hFirstSide);
            Element hSecondSide = doc.createElement("SECOND_SIDE_H");
            hSecondSide.appendChild(doc.createTextNode(this.text.getHeader().getSideTwo()));
            headers.appendChild(hSecondSide);
            Element general = doc.createElement("GENERAL");
            rootElement.appendChild(general);
            Element type = doc.createElement("TYPE");
            type.appendChild(doc.createTextNode(this.contractType.toString()));
            general.appendChild(type);
            Element firstSide = doc.createElement("FIRST_SIDE");
            firstSide.appendChild(doc.createTextNode(null != this.sides ? this.sides.get(0) : ""));
            general.appendChild(firstSide);
            Element secondSide = doc.createElement("SECOND_SIDE");
            secondSide.appendChild(doc.createTextNode(this.sides.size() == 2 ? this.sides.get(1) : ""));
            general.appendChild(secondSide);
            Element conclusionPlace = doc.createElement("CONCLUSION_PLACE");
            conclusionPlace.appendChild(doc.createTextNode(this.conclusionPlace));
            general.appendChild(conclusionPlace);
            Element conclusionDate = doc.createElement("CONCLUSION_DATE");
            conclusionDate.appendChild(doc.createTextNode(this.conclusionDate));
            general.appendChild(conclusionDate);
            document = doc;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    public void writeXMLtoFile() throws TransformerException {
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("/Users/nieop/myXML/summary.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
        System.out.println("File saved!");
    }

}
