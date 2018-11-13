import org.junit.Assert;
import org.junit.Test;
import entity.*;
import org.junit.rules.ExpectedException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;

public class AppTest {


    @Test
    public void nameCreationTest() {

        Name name = new Name( "First", "Last", "Middle" );
        Assert.assertEquals( name.getFullName(), "Last First Middle" );
        Assert.assertEquals( name.getNameWithInitials(), "Last F.M." );
    }


    @Test
    public void legalPersonCreationTest() {
        LegalPerson legalPerson1 = new LegalPerson("BBC", "London");
        Assert.assertEquals(legalPerson1.getAddress(), "London");
        Assert.assertEquals(legalPerson1.getName(), "BBC");


        try{
            LegalPerson legalPerson2 = new LegalPerson("", "");
            LegalPerson legalPerson3 = new LegalPerson("BBC", "London");
        }
        catch (IllegalArgumentException ex){
            Assert.assertEquals(ex.getMessage() ,"Invalid arguments");
        }
    }

    @Test
    public void privatePersonCreationTest() {
            PrivatePerson privatePerson1= new PrivatePerson("Bob", "Ros", "Endy", "Tokyo");
            Assert.assertEquals(privatePerson1.getNameWithInitials(), "Ros B.E.");
            Assert.assertEquals(privatePerson1.getAddress(), "Tokyo");
        }


    @Test
    public void indemnifiedPersonCreationTest() {
        LocalDate date1 = LocalDate.of(1997, 10, 10);
        LocalDate date2 = LocalDate.of(1995, 12, 9);
        IndemnifiedPerson person1 = new IndemnifiedPerson(1, "Bob", "Ros", "Endy", 1000, date1);
        Assert.assertEquals(person1.getId(), 1);
        Assert.assertEquals(person1.getBirthDate(), date1);
        Assert.assertEquals(person1.getCost(), 1000, 0.01);
        Assert.assertEquals(person1.getName().getFullName(), "Ros Bob Endy");
        try{
        IndemnifiedPerson person2 = new IndemnifiedPerson(2, "", "", "", 2000, date2);
        }
        catch (IllegalArgumentException ex){
            Assert.assertEquals(ex.getMessage() ,"Invalid name");
        }
    }

    @Test
    public void insuranceContractCreationTest() {
        LocalDate date1 = LocalDate.of(1999, 10, 10);
        LocalDate date2 = LocalDate.of(1999, 12, 9);
        LocalDate date3 = LocalDate.of(2000, 10, 10);
        LocalDate date4 = LocalDate.of(2010, 12, 9);
        LocalDate date5 = LocalDate.of(2018, 12, 9);

        LegalPerson customer1 = new LegalPerson("BBC", "London");

        IndemnifiedPerson indemnifiedPerson1 = new IndemnifiedPerson(1, "Bob", "Ros", "Endy", 1000, date1);
        IndemnifiedPerson indemnifiedPerson2 = new IndemnifiedPerson(2, "Liz", "Ros", "Bob", 2000, date2);
        IndemnifiedPerson indemnifiedPerson3 = new IndemnifiedPerson(3, "Emi", "Ros", "Endy", 4000, date3);
        IndemnifiedPerson indemnifiedPerson4 = new IndemnifiedPerson(2, "Min", "Ros", "Bob", 0.1, date4);

        HashMap<Integer, IndemnifiedPerson> map1 = new HashMap<Integer, IndemnifiedPerson>();

        map1.put(indemnifiedPerson1.getId(), indemnifiedPerson1);

        InsuranceContract insuranceContract1 = new InsuranceContract(1, date3, date4, date5, customer1, map1);
        Assert.assertEquals(insuranceContract1.insuredSum(), 1000, 0.1);
        Assert.assertEquals(insuranceContract1.insuredSumByLambda(), 1000, 0.1);

        insuranceContract1.addPerson(indemnifiedPerson2);
       Assert.assertEquals(insuranceContract1.insuredSum(), 3000, 0.1);
        Assert.assertEquals(insuranceContract1.insuredSumByLambda(), 3000, 0.1);



    }
    @Test
    public void contractDateTest() {

            LocalDate date1 = LocalDate.of(1999, 10, 10);
            LocalDate date2 = LocalDate.of(1999, 12, 9);
            LocalDate date3 = LocalDate.of(2000, 10, 10);
            LocalDate date4 = LocalDate.of(2010, 12, 9);
            LocalDate date5 = LocalDate.of(2018, 12, 9);

            LegalPerson customer1 = new LegalPerson("BBC", "London");
            IndemnifiedPerson indemnifiedPerson1 = new IndemnifiedPerson(1, "Bob", "Ros", "Endy", 1000, date1);
            HashMap<Integer, IndemnifiedPerson> map1 = new HashMap<Integer, IndemnifiedPerson>();
            map1.put(indemnifiedPerson1.getId(), indemnifiedPerson1);

            try {
                InsuranceContract insuranceContract2 = new InsuranceContract(1, date4, date5, date3, customer1, map1);
            } catch (IllegalArgumentException ex) {
                Assert.assertEquals(ex.getMessage(), "Invalid date");
            }

            try {
                InsuranceContract insuranceContract3 = new InsuranceContract(1, date1, date2, date3, customer1, map1);
            } catch (DateTimeException ex) {
                Assert.assertEquals(ex.getMessage(), "Date of birth should be succeed contract date");
            }
        }

    @Test
    public void addPersonTest() {

        LocalDate date1 = LocalDate.of(1999, 10, 10);
        LocalDate date2 = LocalDate.of(1999, 12, 9);
        LocalDate date3 = LocalDate.of(2000, 10, 10);
        LocalDate date4 = LocalDate.of(2010, 12, 9);
        LocalDate date5 = LocalDate.of(2018, 12, 9);

        IndemnifiedPerson indemnifiedPerson1 = new IndemnifiedPerson(1, "Bob", "Ros", "Endy", 1000, date1);
        IndemnifiedPerson indemnifiedPerson2 = new IndemnifiedPerson(2, "Liz", "Ros", "Bob", 2000, date2);
        IndemnifiedPerson indemnifiedPerson3 = new IndemnifiedPerson(3, "Emi", "Ros", "Endy", 4000, date3);
        IndemnifiedPerson indemnifiedPerson4 = new IndemnifiedPerson(2, "Min", "Ros", "Bob", 0.1, date4);


        HashMap<Integer, IndemnifiedPerson> map1 = new HashMap<Integer, IndemnifiedPerson>();
        map1.put(indemnifiedPerson1.getId(), indemnifiedPerson1);
        Assert.assertEquals(map1.size(), 1);

        LegalPerson customer1 = new LegalPerson("BBC", "London");


        InsuranceContract insuranceContract1 = new InsuranceContract(1, date3, date4, date5, customer1, map1);

        try {
            insuranceContract1.addPerson(indemnifiedPerson2);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals(ex.getMessage(), "This person has been added");
        }


        Assert.assertEquals(insuranceContract1.findPerson(2), indemnifiedPerson2);

        Assert.assertEquals(insuranceContract1.findPerson(3), null);

        Assert.assertEquals(map1.size(), 2);

    }


    }




