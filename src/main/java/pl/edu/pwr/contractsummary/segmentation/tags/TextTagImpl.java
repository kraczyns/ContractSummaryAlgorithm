package pl.edu.pwr.contractsummary.segmentation.tags;

public class TextTagImpl implements ITextTag {

    ITextTag value = null;

    @Override
    public Boolean isPotential(String content, char sign, Boolean... isFirst) {
        return null;
    }

    @Override
    public Boolean isEnd(String content, char sign) {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void clear() {
        value = null;
    }

    @Override
    public Boolean back() {
        return false;
    }

    Boolean empty() {
        if (null == value) {
            return true;
        } else {
            return false;
        }
    }

    public ITextTag getTag(String content, char sign, Boolean... isFirst) {
        if (empty()) {
            Address address = new Address();
            Name name = new Name();
            Period period = new Period();
            Prize prize = new Prize();
            Id id = new Id();
            if (id.isPotential(content, sign)) {
                value = id;
                return id;
            }
            else if (address.isPotential(content, sign)) {
                value = address;
                return address;
            } else if (name.isPotential(content, sign, isFirst)) {
                value = name;
                return name;
            } else if (prize.isPotential(content, sign)) {
                value = prize;
                return prize;
            } else if (period.isPotential(content, sign)) {
                value = period;
                return period;
            }
        }
        return value;
    }
}
