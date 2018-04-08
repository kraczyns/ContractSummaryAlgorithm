package pl.edu.pwr.contractsummary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence {

    String content;

    public Sentence(String content) {
        this.content = content;
    }

    public static List<String> sentencesToString(List<Sentence> sentences) {
        return sentences.stream().map(x -> x.content).collect(Collectors.toList());
    }

}
