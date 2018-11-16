package entity;


public class LegalPerson implements Customer {

    private String organizationName;
    private String address;
    private PersonStatus status;

    public LegalPerson(  String organizationName
                       , String address) {
        argumentsIsValid(organizationName, address);
        this.organizationName=organizationName;
        this.address=address;
        this.status=PersonStatus.legalPerson;
    }

    public String getName()
    {
        return organizationName;
    }

    public String getAddress() {
        return address;
    }

    public PersonStatus getStatus() {
        return status;
    }

    void argumentsIsValid(String organizationName
                        , String address){
        if (organizationName==""||address=="")
            throw new IllegalArgumentException("Invalid arguments");
    }


    }



