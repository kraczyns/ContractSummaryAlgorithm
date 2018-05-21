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
        this.content = clear(content);
    }

    public static Sentence findSentence(List<Sentence> sentences, String[] words) {
        for (Sentence sentence : sentences) {
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent(), words)) {
                    return sentence;
                }
            }
        }
        return null;
    }

    public static Sentence findSentenceWithCounter(List<Sentence> sentences, String[] words, int max) {
        for (Sentence sentence : sentences) {
            int counter = 0;
            for (Word word : sentence.getWords()) {
                if (Utils.isOnTheList(word.getContent().toLowerCase(), words)) {
                    counter ++;
                }
            }
            if (counter >= max) {
                return sentence;
            }
        }
        return null;
    }

    public String clear(String content) {
       String[] parts = content.split(" ");
       String clean = "";
       Boolean thereWillBeNumber = false;
       for (String part : parts) {
           if (!part.contains("§") && !(part.contains(".") && Utils.isStringContainingOnlyDigits(part) && !Utils.isDate(part)) &&
                   !thereWillBeNumber) {
               clean += part + " ";
           }
           if (part.contains("§") && !Utils.isStringContainingDigits(part)) {
               thereWillBeNumber = true;
           }
           if (thereWillBeNumber && Utils.isStringContainingOnlyDigits(part)) {
               thereWillBeNumber = false;
           }
       }
       return clean.trim();
    }

    public String getContent() {
        return content;
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
