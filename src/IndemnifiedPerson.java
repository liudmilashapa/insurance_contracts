import java.time.LocalDate;

public class IndemnifiedPerson {

//*--------------------------------------------------------------------------------------------------------------*//

    private Name name;
    private double cost;
    private LocalDate birthDate;

//*--------------------------------------------------------------------------------------------------------------*//

    IndemnifiedPerson(String firstName
            , String lastName
            , double cost
            , LocalDate birthDate) {
        birthDateIsValid(birthDate);
        this.name = new Name(firstName, lastName);
        this.cost = cost;
        this.birthDate = birthDate;
    }

//*--------------------------------------------------------------------------------------------------------------*//

    public Name getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public double getCost() {
        return cost;
    }

//*--------------------------------------------------------------------------------------------------------------*//

    private void birthDateIsValid(LocalDate birthDate) {
        if (birthDate.isEqual(LocalDate.now()) || birthDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Invalid birth date");
    }
}

//*--------------------------------------------------------------------------------------------------------------*//

//*--------------------------------------------------------------------------------------------------------------*//
