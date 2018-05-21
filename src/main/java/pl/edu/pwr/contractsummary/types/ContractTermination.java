package pl.edu.pwr.contractsummary.types;

import pl.edu.pwr.contractsummary.IContractTypes;
import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Text;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.contractsummary.segmentation.tags.Date;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

public class ContractTermination implements IContractTypes {

    private String previousContractDate;
    private String previousContractPlace;
    private String contractEffectDate;
    private String requestingParty;

    public ContractTermination(Text text) {
        Sentence datePlaceSentence = sentenceWithInfo(text, addToStringArray(Constants.DENUNCIATION, "zawarty"));
        previousContractDate = findPreviousContractDate(datePlaceSentence);
        previousContractPlace = findPreviousContractPlace(datePlaceSentence);
        Sentence effectDateSentence = sentenceWithInfo(text, addToStringArray(null,"okres", "termin", "zachować", "proponować"));
        contractEffectDate = findContractEffectDate(effectDateSentence, text.getHeader().extractDate());
        requestingParty = findRequestingParty(text);
    }

    private String[] addToStringArray(String[] array, String... args) {

        String[] newArray  = new String[(null == array ? 0 : array.length) + (null == args ? 0 : args.length)];

        int index = 0;
        if (null != array) {
            for (String element : array) {
                newArray[index] = element;
                index++;
            }
        }
        if (null != args) {
            for (String element : args) {
                newArray[index] = element;
                index++;
            }
        }

        return newArray;
    }
    public String getContractEffectDate() {
        return contractEffectDate;
    }

    public String getPreviousContractDate() {
        return previousContractDate;
    }

    public String getPreviousContractPlace() {
        return previousContractPlace;
    }

    public String getRequestingParty() {
        return requestingParty;
    }

    private String findRequestingParty(Text txt) {
        return "";
    }

    private String findContractEffectDate(Sentence sentence, String date) {
        // CZAS WYPOWIEDZENIA
        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.date) {
                    return word.getContent();
                } else if (word.getTag() == Tag.period) {
                    Date dateobj = new Date(date);
                    return dateobj.addPeriodToDate(word.getContent());
                }
            }
        }
        return "";

    }

    private String findPreviousContractPlace(Sentence sentence) {

        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.city) {
                    return word.getContent();
                }
            }
        }
        return "";
    }

    private String findPreviousContractDate(Sentence sentence) {

        if (null != sentence) {
            for (Word word : sentence.getWords()) {
                if (word.getTag() == Tag.date) {
                    return word.getContent();
                }
            }
        }
        return "";
    }

    private Sentence sentenceWithInfo(Text txt, String[] list) {

        for (Sentence sentence : txt.getSentences()) {
            int counter = 0;
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent(), list)) {
                    counter++;
                    if (counter == 2) {
                        return sentence;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public String[] getContractTypeFields() {
        return new String[] {previousContractDate, previousContractPlace, contractEffectDate, requestingParty};
    }

    @Override
    public String[] getDetailsHeaders() {
        return Constants.TERMINATION_CONTRACT_HEADERS;
    }
}
