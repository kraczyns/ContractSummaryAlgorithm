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
    contractWork("umowa o dzieło"), //umowa o dzieło
    leaseAgreement("umowa najmu"), //umowa najmu/dzierżawy/leasingu
    contractOfMandate("umowa zlecenia"), // umowa zlecenia
    employmentContract("umowa o pracę"), // umowa o pracę
    other("inny");

    private String value;

    ContractType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
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
        String[] vals = null;
        if (getContractType().equals(ContractType.employmentContract.toString())) {
            EmploymentContract ec = new EmploymentContract();
            ec.setText(text);
            ec.setDocument(getDocument());
            ec.setEmploymentContractFields();
            vals = ec.getEmploymentContractSumarizationFields();
        }
        if ( null != vals) {
            createContractSumarizationXML(vals);
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

    private void createContractSumarizationXML(String[] values) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.newDocument();

            String[] contractHeadersValues = {text.getHeader().getPlaceDate(), text.getHeader().getSideOne(), text.getHeader().getSideTwo() };

            Element rootElement = document.createElement("CONTRACT");
            document.appendChild(rootElement);

            Element headers = document.createElement("HEADERS");
            rootElement.appendChild(headers);
            for (int i = 0; i < Constants.CONTRACT_HEADERS.length; i++){
                Element elem = document.createElement(Constants.CONTRACT_HEADERS[i]);
                elem.appendChild(document.createTextNode(contractHeadersValues[i]));
                headers.appendChild(elem);
            }

            String[] contractGeneralValues = {contractType.toString(), null != this.sides ? this.sides.get(0) : "", this.sides.size() == 2 ? this.sides.get(1) : "",
                    conclusionPlace, conclusionDate};

            Element general = document.createElement("GENERAL");
            rootElement.appendChild(general);

            for (int i = 0; i < Constants.CONTRACT_GENERAL.length; i++){
                Element elem = document.createElement(Constants.CONTRACT_GENERAL[i]);
                elem.appendChild(document.createTextNode(contractGeneralValues[i]));
                general.appendChild(elem);
            }

            Element details = document.createElement("DETAILS");
            rootElement.appendChild(details);

            switch (contractType) {
                case employmentContract: {
                    for (int i = 0; i < Constants.EMPLOYMENT_CONTRACT_HEADERS.length; i++){
                        Element elem = document.createElement(Constants.EMPLOYMENT_CONTRACT_HEADERS[i]);
                        elem.appendChild(document.createTextNode(values[i]));
                        details.appendChild(elem);
                    }
                    break;
                }
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    public void writeXMLtoFile() throws TransformerException {
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("/Users/nieop/Desktop/mgr/xml/summary.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
        System.out.println("File saved!");
    }

}
