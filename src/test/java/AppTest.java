import org.junit.*;
/*Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Before;*/
import entity.*;
import org.junit.rules.ExpectedException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;

public class AppTest {

    LocalDate date1;
    LocalDate date2;
    LocalDate date3;
    LocalDate date4;
    LocalDate date5;

    LegalPerson customer1;

    IndemnifiedPerson indemnifiedPerson1;
    IndemnifiedPerson indemnifiedPerson2;
    IndemnifiedPerson indemnifiedPerson3;
    IndemnifiedPerson indemnifiedPerson4;



    HashMap<Integer, IndemnifiedPerson> map1;

    InsuranceContract insuranceContract1;

    @Before
    public void initialize() {

        date1 = LocalDate.of(1999, 10, 10);
        date2 = LocalDate.of(1999, 12, 9);
        date3 = LocalDate.of(2000, 10, 10);
        date4 = LocalDate.of(2010, 12, 9);
        date5 = LocalDate.of(2018, 12, 9);

        indemnifiedPerson1 = new IndemnifiedPerson(1, "Bob", "Ros", "Endy", 1000, date1);
        indemnifiedPerson2 = new IndemnifiedPerson(2, "Liz", "Ros", "Bob", 2000, date2);
        indemnifiedPerson3 = new IndemnifiedPerson(3, "Emi", "Ros", "Endy", 4000, date3);
        indemnifiedPerson4 = new IndemnifiedPerson(2, "Min", "Ros", "Bob", 0.1, date4);

        customer1 = new LegalPerson("BBC", "London");

        map1 = new HashMap<Integer, IndemnifiedPerson>();

        insuranceContract1 = new InsuranceContract(1, date3, date4, date5, customer1, map1);
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void nameCreationTest() {

        Name name1 = new Name( "First", "Last", "Middle" );
        Assert.assertEquals( name1.getFullName(), "Last First Middle" );
        Assert.assertEquals( name1.getNameWithInitials(), "Last F.M." );
    }

    @Test
    public void invalidFirstNameCreationTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid name");
        Name name = new Name("", "Last", "Middle");
    }

    @Test
    public void invalidLastNameCreationTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid name");
        Name name = new Name("First", "", "Middle");
    }


    @Test
    public void invalidLegalPersonCreationTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid arguments");
        LegalPerson legalPerson = new LegalPerson("", "");
    }

    @Test
    public void invalidPrivatePersonCreationTest() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid address");
        PrivatePerson privatePerson = new PrivatePerson("Bob", "Ros", "Endy", "");
        }


    @Test
    public void invalidDataIndemnifiedPersonCreationTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid birth date");
        IndemnifiedPerson person = new IndemnifiedPerson(2, "Bob", "Ros", "Endy", 2000, date5);
    }

    @Test
    public void invalidCostIndemnifiedPersonCreationTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid cost");
        IndemnifiedPerson person = new IndemnifiedPerson(2, "Bob", "Ros", "Endy", -1, date3);
    }

    @Test
    public void invalidIdIndemnifiedPersonCreationTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid id");
        IndemnifiedPerson person = new IndemnifiedPerson(-1, "Bob", "Ros", "Endy", 2000, date3);
    }

    @Test
    public void insuranceContractSumTest() {

        insuranceContract1.addPerson(indemnifiedPerson1);
        Assert.assertEquals(insuranceContract1.insuredSum(), 1000, 0.1);
        Assert.assertEquals(insuranceContract1.insuredSumByLambda(), 1000, 0.1);

        insuranceContract1.addPerson(indemnifiedPerson2);
        Assert.assertEquals(insuranceContract1.insuredSum(), 3000, 0.1);
        Assert.assertEquals(insuranceContract1.insuredSumByLambda(), 3000, 0.1);
    }

    @Test
    public void invalidContractDateTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid date");
        InsuranceContract insuranceContract = new InsuranceContract(1, date5, date2, date3, customer1, map1);
    }

    @Test
    public void invalidContractEffectiveDateTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid date");
        InsuranceContract insuranceContract = new InsuranceContract(1, date4, date3, date5, customer1, map1);
    }

    @Test
    public void invalidContractExpireDateTest() throws IllegalArgumentException {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid date");
        InsuranceContract insuranceContract = new InsuranceContract(1, date4, date5, date3, customer1, map1);
    }

    @Test
    public void invalidBirthDateContractDateTest() throws DateTimeException {

        thrown.expect(DateTimeException.class);
        thrown.expectMessage("Date of birth should be succeed contract date");
        InsuranceContract insuranceContract = new InsuranceContract(1, date1, date2, date3, customer1, map1);
        insuranceContract.addPerson(indemnifiedPerson3);
    }

    @Test
    public void addPersonTest() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("This person has been added");
        insuranceContract1.addPerson(indemnifiedPerson1);
        insuranceContract1.addPerson(indemnifiedPerson1);
        }

    @Test
    public void findPersonTest() {

        insuranceContract1.addPerson(indemnifiedPerson1);
        insuranceContract1.addPerson(indemnifiedPerson2);
        Assert.assertEquals(insuranceContract1.findPerson(2), indemnifiedPerson2);
        Assert.assertEquals(insuranceContract1.findPerson(3), null);
        Assert.assertEquals(map1.size(), 2);
    }
}




