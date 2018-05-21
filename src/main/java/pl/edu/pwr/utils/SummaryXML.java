package pl.edu.pwr.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SummaryXML {

    private Document document;
    private String[] contractHeadersValues;
    private String[] contractGeneralValues;
    private String[][] contractSidesValues;
    private String[][] contractSidesHeaders;
    private String[] contractDetailsValues;
    private String[] contractDetailsHeaders;

    public SummaryXML(String[] hValues, String[] gValues, String[][] sValues, String[][] sHeaders, String[] dValues, String[] dHeaders) {
        contractHeadersValues = hValues;
        contractGeneralValues = gValues;
        contractSidesValues = sValues;
        contractSidesHeaders = sHeaders;
        contractDetailsValues = dValues;
        contractDetailsHeaders = dHeaders;
    }

    private void createContractSumarizationXML() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.newDocument();

            Element rootElement = document.createElement("CONTRACT");
            document.appendChild(rootElement);

            Element headers = document.createElement("HEADERS");
            rootElement.appendChild(headers);
            for (int i = 0; i < Constants.CONTRACT_HEADERS.length; i++){
                Element elem = document.createElement(Constants.CONTRACT_HEADERS[i]);
                elem.appendChild(document.createTextNode(contractHeadersValues[i]));
                headers.appendChild(elem);
            }

            Element general = document.createElement("GENERAL");
            rootElement.appendChild(general);

            for (int i = 0; i < Constants.CONTRACT_GENERAL.length; i++){
                Element elem = document.createElement(Constants.CONTRACT_GENERAL[i]);
                elem.appendChild(document.createTextNode(contractGeneralValues[i]));
                general.appendChild(elem);
                if (Constants.CONTRACT_GENERAL[i] == "SIDES") {
                    Element side1 = document.createElement("FIRST_SIDE");
                    elem.appendChild(side1);
                    for (int j = 0; j < contractSidesHeaders[0].length; j++) {
                        Element element = document.createElement(contractSidesHeaders[0][j]);
                        element.appendChild(document.createTextNode(contractSidesValues[0][j]));
                        side1.appendChild(element);
                    }
                    Element side2 = document.createElement("SECOND_SIDE");
                    elem.appendChild(side2);
                    for (int j = 0; j < contractSidesHeaders[1].length; j++) {
                        Element element = document.createElement(contractSidesHeaders[1][j]);
                        element.appendChild(document.createTextNode(contractSidesValues[1][j]));
                        side2.appendChild(element);
                    }
                }
            }

            Element details = document.createElement("DETAILS");
            rootElement.appendChild(details);

            for (int i = 0; i < contractDetailsHeaders.length; i++){
                Element elem = document.createElement(contractDetailsHeaders[i]);
                elem.appendChild(document.createTextNode(contractDetailsValues[i]));
                details.appendChild(elem);
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    public void writeXMLtoFile(String dir, int number) throws TransformerException {
        createContractSumarizationXML();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("/Users/nieop/Desktop/mgr/xml/" + dir + "/umowa-" + number + ".xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);
        System.out.println("umowa-" + number + " zapisana w folderze: " + dir);
    }
}
