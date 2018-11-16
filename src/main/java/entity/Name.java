package entity;

public class Name {

    private String firstName;
    private String lastName;
    private String middleName;

    public Name(  String firstName
                , String lastName
                , String middleName){
        argumentsIsValid(firstName, lastName, middleName);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){return lastName; }

    public String getMiddleName() { return middleName; }

    public String getFullName(){
        return new StringBuilder(getLastName())
                    .append(' ')
                    .append(getFirstName())
                    .append(' ')
                    .append(getMiddleName())
                    .toString();
    }

    public String getNameWithInitials(){
        return new StringBuilder(getLastName())
                      .append(' ')
                      .append(getFirstName().charAt(0))
                      .append(".")
                      .append(getMiddleName().charAt(0))
                      .append('.')
                      .toString();
    }
    public int compareTo(Name name2){
        return this.getFullName().compareTo(name2.getFullName());
    }

    void argumentsIsValid(String firstName
                        , String lastName
                        , String middleName){
        if (lastName ==""||firstName==""||middleName =="")
            throw new IllegalArgumentException("Invalid name");
    }
}