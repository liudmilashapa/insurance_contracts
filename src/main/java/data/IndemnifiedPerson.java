package data;

import api.IIndemnifiedPerson;

import java.time.LocalDate;

public class IndemnifiedPerson implements IIndemnifiedPerson {

    private long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate birthDate;
    private double cost;

    public IndemnifiedPerson(
            long id
            , String lastName
            , String firstName
            , String middleName
            , LocalDate birthDate
            , double cost) {

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.cost = cost;
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {

        return lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getMiddleName() {

        return middleName;
    }

    public LocalDate getBirthDate() {

        return birthDate;
    }

    public double getCost() {

        return cost;
    }

    public String getFullName() {
        return new StringBuilder(getLastName())
                .append(' ')
                .append(getFirstName())
                .append(' ')
                .append(getMiddleName())
                .toString();
    }

    public void setId(long id) {

        this.id = id;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {

        this.middleName = middleName;
    }

    public void setBirthDate(LocalDate birthDate) {

        this.birthDate = birthDate;
    }

    public void setCost(double cost) {

        this.cost = cost;
    }

    public String getNameWithInitials() {
        return new StringBuilder(getLastName())
                .append(' ')
                .append(getFirstName().charAt(0))
                .append(".")
                .append(getMiddleName().charAt(0))
                .append('.')
                .toString();
    }

}