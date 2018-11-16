package entity;

import java.time.LocalDate;

public class IndemnifiedPerson {

    private int id;
    private Name name;
    private double cost;
    private LocalDate birthDate;

    public IndemnifiedPerson(int id
            , String firstName
            , String lastName
            , String middleName
            , double cost
            , LocalDate birthDate) {
        birthDateIsValid(birthDate);
        costIsValid(cost);
        idIsValid(id);
        this.id=id;
        this.name = new Name(firstName, lastName, middleName);
        this.cost = cost;
        this.birthDate = birthDate;
    }

    public int getId() { return id; }

    public Name getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public double getCost() {
        return cost;
    }

    public void outputPersonNameWithInitials() {
        System.out.println("\t \t \t Name: " + this.getName().getNameWithInitials());
    }

    public void outputPersonInformation(){
        System.out.println("\t \t Person id: " + this.getId());
        System.out.println("\t \t \t Name: " + this.getName().getFullName());
        System.out.println("\t \t \t Birth date: " + this.getBirthDate());
        System.out.println("\t \t \t Cost: " + this.getCost());
    }

    private void birthDateIsValid(LocalDate birthDate) {
        if (birthDate.isEqual(LocalDate.now()) || birthDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Invalid birth date");
    }

    private void costIsValid(double cost) {
        if (cost<=0)
            throw new IllegalArgumentException("Invalid cost");
    }

    private void idIsValid(int id) {
        if (id<=0)
            throw new IllegalArgumentException( "Invalid id");
    }



}