package pl.edu.pwr.contractsummary.segmentation;

import pl.edu.pwr.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence {

    String content;
    List<Word> words;

    public Sentence(String content) {
        words = new ArrayList<Word>();
        this.content = content;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public void addWord(String value) {
        if (Utils.isThatWord(value)) {
            words.add(new Word(value));
        }
    }
    public static List<String> sentencesToString(List<Sentence> sentences) {
        return sentences.stream().map(x -> x.content).collect(Collectors.toList());
    }

}
