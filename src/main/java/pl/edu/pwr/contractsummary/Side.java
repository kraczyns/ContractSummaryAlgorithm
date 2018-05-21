package pl.edu.pwr.contractsummary;

import pl.edu.pwr.contractsummary.segmentation.Sentence;
import pl.edu.pwr.contractsummary.segmentation.Tag;
import pl.edu.pwr.contractsummary.segmentation.Word;
import pl.edu.pwr.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Side implements IContractTypes{
    private String name;
    private String address;
    private String city;
    private String PESEL;
    private String id;
    private String REGON;
    private String NIP;
    private String representative;
    private Boolean company;
    private String role;

    Side() {
        name = "";
        address = "";
        city = "";
        PESEL = "";
        id = "";
        REGON = "";
        NIP = "";
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
        Boolean id = false;
        for (Word word : side) {
            if (role) {
                role = false;
                newSide.role = word.getContent();
            } else if (id) {
                if (newSide.id.equals("")) {
                    newSide.id = word.getContent();
                } else {
                    id = false;
                    newSide.id += " " + word.getContent();
                }
            }
            else if(word.getTag() == Tag.otherName) {
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
            } else if (word.getContent().equals("osobisty")) {
                id = true;
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

    public String getNIP() {
        return NIP;
    }

    public String getPESEL() {
        return PESEL;
    }

    public String getREGON() {
        return REGON;
    }

    public String getRepresentative() {
        return representative;
    }

    @Override
    public String[] getContractTypeFields() {
        if(company) {
            return new String[] {company.toString(), name, address, city,
            REGON, NIP, representative, role};
        } else {
            return new String[] {company.toString(), name, address, city,
                    PESEL, id, role};
        }
    }

    @Override
    public String[] getDetailsHeaders() {
        if(company) {
            return Constants.SIDE_HEADERS_COMPANY;
        } else {
            return Constants.SIDE_HEADERS_NONCOMPANY;
        }
    }
}
