package pl.edu.pwr.contractsummary.segmentation.tags;

public interface ITags {
    
    Boolean isPotential(String content, char sign, Boolean... isFirst);

    default Boolean isValid() {
        if (null == getValue()) {
            return false;
        } else {
            return true;
        }
    }
    Boolean isEnd(String content);
    String getValue();
    void clear();
    
}
