package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;
import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Side implements IContractTypes{
    private String name;
    private String address;
    private String city;
    private String id;
    private String representative;
    private Boolean company;
    private String role;

    Side() {
        name = "";
        address = "";
        city = "";
        id = "";
        representative = "";
        company = false;
        role = "";
    }

    private static List<List<Word>> splitSides(Sentence sentence) {
        List<List<Word>> sides = new ArrayList<List<Word>>();

        if (null != sentence) {
            for (int i = 0; i < sentence.getWords().size(); i++) {
                Word word = sentence.getWords().get(i);
                if (word.getContent().equals("a")) {
                    sides.add(sentence.getWords().subList(0, i));
                    sides.add(sentence.getWords().subList(i, sentence.getWords().size()));
                    return sides;
                }
            }
        }
        return sides;
    }

    public static List<Side> extractSides(Sentence sentence) {
         List<List<Word>> sides =  splitSides(sentence);
         if (null != sides && sides.size() == 2) {
             Side firstSide = extractSide(sides.get(0));
             Side secondSide = extractSide(sides.get(1));
             List<Side> sidesList = new ArrayList<Side>();
             sidesList.add(firstSide);
             sidesList.add(secondSide);
             return sidesList;
         }
         return Arrays.asList(new Side(), new Side());
    }

    private static Side extractSide(List<Word> side) {
        Side newSide = new Side();
        Boolean role = false;
        for (Word word : side) {
            if (role) {
                role = false;
                if (null != word.getMorfologikOutput()) {
                    newSide.role = word.getMorfologikOutput().contains("verb") || word.getContent().charAt(word.getContent().length()-1) == 'ć' ?
                            Utils.wordMapper(word.getContent(), Constants.LIST_SUBJ, Constants.LIST_VERB) : word.getContent();
                } else {
                    newSide.role = word.getContent().charAt(word.getContent().length()-1) == 'ć' ?
                            Utils.wordMapper(word.getContent(), Constants.LIST_SUBJ, Constants.LIST_VERB) : word.getContent();
                }
            } else if (word.getTag() == Tag.id) {
                newSide.id = word.getContent();
            } else if(word.getTag() == Tag.otherName && newSide.name == "") {
                newSide.company = true;
                newSide.name = word.getContent();
            } else if (word.getTag() == Tag.firstNameLastName) {
                if (newSide.company) {
                    newSide.representative = word.getContent();
                } else {
                    newSide.name = word.getContent();
                }
            } else if (newSide.name != "" && word.getTag() == Tag.address) {
                newSide.address = word.getContent();
            } else if (newSide.name != "" && word.getTag() == Tag.city) {
                newSide.city = word.getContent();
            } else if (word.getContent().equals("dalej")) {
                role = true;
            }
        }
        return newSide;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public Boolean getCompany() {
        return company;
    }

    public String getId() {
        return id;
    }

    public String getRepresentative() {
        return representative;
    }

    @Override
    public String[] getContractTypeFields() {
        if(company) {
            return new String[] {company.toString(), name, address, city, representative, role};
        } else {
            return new String[] {company.toString(), name, address, city, id, role};
        }
    }

    @Override
    public String[] getDetailsHeaders(String language) {
        if(company) {
            return language.equals("ENG") ? Constants.SIDE_HEADERS_COMPANY_ENG : Constants.SIDE_HEADERS_COMPANY_PL;
        } else {
            return language.equals("ENG") ? Constants.SIDE_HEADERS_NONCOMPANY_ENG : Constants.SIDE_HEADERS_NONCOMPANY_PL;
        }
    }
}
