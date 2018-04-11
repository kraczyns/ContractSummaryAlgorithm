package pl.edu.pwr.contractsummary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence {

    String content;
    List<Word> words;

    public Sentence(String content) {
        this.content = content;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public static List<String> sentencesToString(List<Sentence> sentences) {
        return sentences.stream().map(x -> x.content).collect(Collectors.toList());
    }

}
