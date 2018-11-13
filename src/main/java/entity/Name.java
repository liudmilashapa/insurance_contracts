package entity;

public class Name {

    private String firstName;
    private String lastName;
    private String middleName;

    public Name(String firstName, String lastName, String middleName){
        if (firstName==""||lastName==""||middleName=="")
            throw new IllegalArgumentException("Invalid name");
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getMiddleName() { return middleName; }

    public String getFullName(){
        StringBuilder fullName = new StringBuilder(getLastName());
        fullName.append(' ');
        fullName.append(getFirstName());
        fullName.append(' ');
        fullName.append(getMiddleName());

    return  fullName.toString();
    }

    public String getNameWithInitials(){
        StringBuilder nameWithInitials = new StringBuilder(getLastName());
        nameWithInitials.append(' ');
        nameWithInitials.append(getFirstName().charAt(0));
        nameWithInitials.append(".");
        nameWithInitials.append(getMiddleName().charAt(0));
        nameWithInitials.append('.');
        return  nameWithInitials.toString();
    }
    public int compareTo(Name name2){
        return this.getFullName().compareTo(name2.getFullName());
    }
}


