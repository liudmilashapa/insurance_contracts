
//*--------------------------------------------------------------------------------------------------------------*//

public class Customer {
//*--------------------------------------------------------------------------------------------------------------*//

    private Name name;
    private String organizationName;
    private String address;
    private PersonStatus status;

//*--------------------------------------------------------------------------------------------------------------*//

    Customer( String firstName
            , String lastName
            , String organizationName
            , String address
            , PersonStatus status){
    this.name = new Name (firstName, lastName);
    this.organizationName = organizationName;
    this.address = address;
    this.status = status;
    }

//*--------------------------------------------------------------------------------------------------------------*//

    public String getFirstName() {
        if (this.status == PersonStatus.legalPerson)
            throw new RuntimeException("Legal person hasn't name");
        return name.getFirstName();
    }

    public String getLastName() {
        if (this.status == PersonStatus.legalPerson)
            throw new RuntimeException("Legal person hasn't name");
        return name.getLastName();
    }

    public String getOrganizationName()
    {
        if (this.status == PersonStatus.privatePerson)
            throw new RuntimeException("Private person hasn't organization name");
        return organizationName;
    }

    public String getAddress() {
        return address;
    }

    public PersonStatus getStatus() {
        return status;
    }

//*--------------------------------------------------------------------------------------------------------------*//

}

//*--------------------------------------------------------------------------------------------------------------*//

enum PersonStatus { privatePerson, legalPerson }

//*--------------------------------------------------------------------------------------------------------------*//
