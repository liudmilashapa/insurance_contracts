package entity;

public class PrivatePerson implements Customer {

    private Name name;
    private String address;
    private PersonStatus status;

    public  PrivatePerson( String firstName
                         , String lastName
                         , String middleName
                         , String address){
        argumentsIsValid(address);
        this.name = new Name (firstName, lastName, middleName);
        this.address = address;
        this.status = PersonStatus.privatePerson;
    }

    public String getName(){
        return name.getFullName();
    }

    public String getNameWithInitials(){
        return name.getNameWithInitials();
    }

    public String getAddress() {
        return address;
    }

    public PersonStatus getStatus() { return status; }

    void argumentsIsValid(String address){
        if (address=="")
            throw new IllegalArgumentException("Invalid address");
    }

}
