package pl.edu.pwr.contractsummary;

import java.util.ArrayList;
import java.util.List;

public class Sentence {

    String content;

    public Sentence(String content) {
        this.content = content;
    }

    public static List<String> sentencesToString(List<Sentence> sentences) {
        List<String> string = new ArrayList<String>();
        for (Sentence sentence : sentences) {
            string.add(sentence.content);
        }
        return string;
    }

}
