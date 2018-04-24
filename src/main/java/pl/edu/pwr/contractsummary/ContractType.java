package pl.edu.pwr.contractsummary;

public enum ContractType {
    contractWork("umowa o dzieło"), //umowa o dzieło
    contractWorkTermination("zerwanie umowy o dzieło"), // zerwanie umowy o dzieło
    leaseAgreement("umowa najmu"), //umowa najmu/dzierżawy/leasingu
    leaseAgreementTermination("zerwanie umowy najmu"), // zerwanie umowy najmu
    contractOfMandate("umowa zlecenia"), // umowa zlecenia
    contractOfMandateTermination("zerwanie umowy zlecenia"), // zerwanie umowy zlecenia
    employmentContract("umowa o pracę"), // umowa o pracę
    employmentContractTermination("zerwanie umowy o pracę"), // zerwanie umowy o pracę
    other("inny");


    private String value;

    ContractType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
