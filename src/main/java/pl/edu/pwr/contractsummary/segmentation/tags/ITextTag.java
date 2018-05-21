package pl.edu.pwr.contractsummary.segmentation.tags;

public interface ITextTag {

    Boolean isPotential(String content, char sign, Boolean... isFirst);
    Boolean isEnd(String content, char sign);
    String getValue();
    void clear();
    Boolean back();
}
