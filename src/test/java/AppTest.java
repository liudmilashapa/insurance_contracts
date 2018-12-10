import api.*;
import dao.DaoIndemnifiedPerson;
import dao.DaoInsuranceContract;
import dao.DaoLegalPerson;
import dao.DaoPrivatePerson;
import data.IndemnifiedPerson;
import dict.PersonStatus;
import org.junit.*;

import service.InsuranceContract;
import utils.*;
import org.junit.rules.ExpectedException;
import validate.ValidateCustomer;
import validate.ValidateIndemnifiedPerson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

import java.io.Reader;
import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppTest {


    IFactory factory = new Factory();

    LocalDate date1;
    LocalDate date2;
    LocalDate date3;
    LocalDate date4;
    LocalDate date5;

    ICustomer customer1;
    ICustomer customer2;
    ICustomer customer3;

    IIndemnifiedPerson indemnifiedPerson1;
    IIndemnifiedPerson indemnifiedPerson2;
    IIndemnifiedPerson indemnifiedPerson3;
    IIndemnifiedPerson indemnifiedPerson4;


    HashMap<Long, IndemnifiedPerson> map1;

    IInsuranceContract insuranceContract1;
    IInsuranceContract insuranceContract2;

    String path;

    HashMap<String, String> validateMap;

    ValidateCustomer validateCustomer;
    ValidateIndemnifiedPerson validateIndemnifiedPerson;

    DaoIndemnifiedPerson daoIndemnifiedPerson;
    DaoLegalPerson daoLegalPerson;
    DaoPrivatePerson daoPrivatePerson;
    DaoInsuranceContract daoInsuranceContract;

    /*compare entity*/
    public boolean compareIndemnifiedPerson(IIndemnifiedPerson person1
            , IIndemnifiedPerson person2) {
        return (person1.getId() == person2.getId()
                && person1.getLastName().equals(person2.getLastName())
                && person1.getFirstName().equals(person2.getFirstName())
                && person1.getMiddleName().equals(person2.getMiddleName())
                && person1.getBirthDate().equals(person2.getBirthDate())
                && Double.compare(person1.getCost(), person2.getCost()) == 0);
    }

    public boolean compareLegalPerson(ILegalPerson legalPerson1
            , ILegalPerson legalPerson2) {
        return (legalPerson1.getId() == legalPerson2.getId()
                && legalPerson1.getOrganizationName().equals(legalPerson2.getOrganizationName())
                && legalPerson1.getAddress().equals(legalPerson2.getAddress()));
    }

    public boolean comparePrivatePerson(IPrivatePerson privatePerson1
            , IPrivatePerson privatePerson2) {
        return (privatePerson1.getId() == privatePerson2.getId()
                && privatePerson1.getLastName().equals(privatePerson2.getLastName())
                && privatePerson1.getFirstName().equals(privatePerson2.getFirstName())
                && privatePerson1.getMiddleName().equals(privatePerson2.getMiddleName())
                && privatePerson1.getAddress().equals(privatePerson2.getAddress()));
    }


    @Before
    public void initialize() {

        /*clean db tables*/

        try {
            String dbName = "insurance_contracts";
            Connection connection = null;
            String url = "jdbc:mysql://localhost:3306/" + dbName;
            String name = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(" DROP TABLE IF EXISTS insurance_contracts.privatePersonsTable ");
            statement.executeUpdate(" DROP TABLE IF EXISTS insurance_contracts.legalPersonsTable");
            statement.executeUpdate(" DROP TABLE IF EXISTS insurance_contracts.insuranceContractsIndemnifiedPersonsTable");
            statement.executeUpdate(" DROP TABLE IF EXISTS insurance_contracts.insuranceContractsTable");
            statement.executeUpdate(" DROP TABLE IF EXISTS insurance_contracts.IndemnifiedPersonsTable");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        date1 = LocalDate.of(1999, 10, 10);
        date2 = LocalDate.of(1999, 12, 9);
        date3 = LocalDate.of(2000, 10, 10);
        date4 = LocalDate.of(2010, 12, 9);
        date5 = LocalDate.now().plusDays(2);

        indemnifiedPerson1 = factory.createIndemnifiedPerson(1, "Ros", "Bob", "Endy", date1, 1000);
        indemnifiedPerson2 = factory.createIndemnifiedPerson(2, "Ros", "Liz", "Bob", date2, 2000);
        indemnifiedPerson3 = factory.createIndemnifiedPerson(3, "Ros", "Emi", "Endy", date3, 4000);
        indemnifiedPerson4 = factory.createIndemnifiedPerson(2, "Ros", "Min", "Bob", date4, 0.1);

        customer1 = factory.createCustomer(1, "BBC", "London");
        customer2 = factory.createCustomer(2, "Ann", "Brawn", "Bob", "London");
        customer3 = factory.createCustomer(3, "Nic", "Brawn", "Ros", "Paris");

        map1 = new HashMap<>();

        insuranceContract1 = factory.createInsuranceContract(1, date2, date4, date5, customer1, map1);
        insuranceContract2 = factory.createInsuranceContract(2, date3, date4, date5, customer2, map1);

        path = "./test.csv";

        validateMap = new HashMap<>();

        validateCustomer = new ValidateCustomer();
        validateIndemnifiedPerson = new ValidateIndemnifiedPerson();

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidLegalPersonCreationTest() throws IllegalArgumentException {
        ICustomer legalPerson = factory.createCustomer(1, "", "HTZ");

        validateMap.putAll(validateCustomer.validate(legalPerson));
        Assert.assertEquals(validateMap.get("organizationName"), "empty organizationName");
    }


    @Test
    public void invalidPrivatePersonCreationTest() throws IllegalArgumentException {

        ICustomer privatePerson = factory.createCustomer(2, "Bob", "Ros", "Endy", "");

        validateMap.putAll(validateCustomer.validate(privatePerson));
        Assert.assertEquals(validateMap.get("address"), "empty address");
    }

    @Test
    public void invalidDataIndemnifiedPersonCreationTest() throws IllegalArgumentException {

        IIndemnifiedPerson person = factory.createIndemnifiedPerson(2, "Bob", "Ros", "Endy", date5, 2000);
        validateMap.putAll(validateIndemnifiedPerson.validate(person));
        Assert.assertEquals(validateMap.get("birthDate"), "birthDate doesn't exist");
    }

    @Test
    public void invalidCostIndemnifiedPersonCreationTest() throws IllegalArgumentException {

        IIndemnifiedPerson person = factory.createIndemnifiedPerson(2, "Bob", "Ros", "Endy", date3, -1);
        validateMap.putAll(validateIndemnifiedPerson.validate(person));
        Assert.assertEquals(validateMap.get("cost"), "negative cost");

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

/*    @Test
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
    }*/

  /*  @Test
    public void invalidBirthDateContractDateTest() throws DateTimeException {

        thrown.expect(DateTimeException.class);
        thrown.expectMessage("Date of birth should be succeed contract date");

        InsuranceContract insuranceContract = new InsuranceContract(1, date1, date2, date3, customer1, map1);
        insuranceContract.addPerson(indemnifiedPerson3);
    }*/

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

    @Test
    public void writeAndReadTest() {


        insuranceContract1.addPerson(indemnifiedPerson1);
        insuranceContract1.addPerson(indemnifiedPerson2);

        {
            try {
                BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(path));
                InsuranceWriter writer = new InsuranceWriter(bufferedWriter);

                writer.write(insuranceContract1);
            } catch (IOException exception) {
                Assert.assertTrue(false);
            }
        }

        {
            try {
                Reader bufferedReader = Files.newBufferedReader(Paths.get(path));
                InsuranceReader reader = new InsuranceReader(bufferedReader);

                ArrayList<InsuranceContract> contractsList = reader.read();

                Assert.assertEquals(contractsList.size(), 1);

                InsuranceContract resultContract = contractsList.get(0);

                Assert.assertEquals(insuranceContract1.getId(), resultContract.getId());
                Assert.assertEquals(insuranceContract1.getContractDate(), resultContract.getContractDate());
                Assert.assertEquals(
                        insuranceContract1.getContractEffectiveDate()
                        , resultContract.getContractEffectiveDate()
                );
                Assert.assertEquals(
                        insuranceContract1.getContractExpireDate()
                        , resultContract.getContractExpireDate()
                );


                ICustomer expectedICustomer = insuranceContract1.getCustomer();
                ICustomer resultICustomer = resultContract.getCustomer();

                Assert.assertEquals(expectedICustomer.getStatus(), resultICustomer.getStatus());
                if (resultICustomer.getStatus().equals(PersonStatus.privatePerson)) {
                    Assert.assertEquals(((IPrivatePerson) expectedICustomer).getLastName(), ((IPrivatePerson) resultICustomer).getLastName());
                    Assert.assertEquals(((IPrivatePerson) expectedICustomer).getFirstName(), ((IPrivatePerson) resultICustomer).getFirstName());
                    Assert.assertEquals(((IPrivatePerson) expectedICustomer).getMiddleName(), ((IPrivatePerson) resultICustomer).getMiddleName());

                } else {
                    Assert.assertEquals(((ILegalPerson) expectedICustomer).getOrganizationName(), ((ILegalPerson) resultICustomer).getOrganizationName());
                }


                Assert.assertEquals(expectedICustomer.getAddress(), resultICustomer.getAddress());

                Collection<IIndemnifiedPerson> expectedContainer = insuranceContract1.getIndemnifiedPersonCollection().values();
                Collection<IIndemnifiedPerson> resultContainer = resultContract.getIndemnifiedPersonCollection().values();

                Assert.assertEquals(expectedContainer.size(), resultContainer.size());

                Iterator<IIndemnifiedPerson> expectedIterator = expectedContainer.iterator();
                Iterator<IIndemnifiedPerson> resultIterator = resultContainer.iterator();

                IIndemnifiedPerson expectedPerson1 = expectedIterator.next();
                IIndemnifiedPerson resultPerson1 = resultIterator.next();

                Assert.assertEquals(expectedPerson1.getId(), resultPerson1.getId());
                Assert.assertEquals(expectedPerson1.getFullName(), resultPerson1.getFullName());
                Assert.assertEquals(expectedPerson1.getBirthDate(), resultPerson1.getBirthDate());
                Assert.assertTrue(Math.abs(expectedPerson1.getCost() - resultPerson1.getCost()) < 0.01);

                IIndemnifiedPerson expectedPerson2 = expectedIterator.next();
                IIndemnifiedPerson resultPerson2 = resultIterator.next();

                Assert.assertEquals(expectedPerson2.getId(), resultPerson2.getId());
                Assert.assertEquals(expectedPerson1.getFullName(), resultPerson1.getFullName());
                Assert.assertEquals(expectedPerson2.getBirthDate(), resultPerson2.getBirthDate());
                Assert.assertTrue(Math.abs(expectedPerson2.getCost() - resultPerson2.getCost()) < 0.01);
            } catch (IOException exception) {
                Assert.assertTrue(false);
            }
        }
    }

    @Test
    public void DaoIndemnifiedPersonCreate() {

        daoIndemnifiedPerson = new DaoIndemnifiedPerson();

        daoIndemnifiedPerson.create(indemnifiedPerson4);
        IIndemnifiedPerson readPerson = daoIndemnifiedPerson.read(indemnifiedPerson4.getId());
        Assert.assertTrue(compareIndemnifiedPerson(indemnifiedPerson4, readPerson));
        daoIndemnifiedPerson.delete(indemnifiedPerson4.getId());
    }

    @Test
    public void DaoIndemnifiedPersonUpdate() {

        daoIndemnifiedPerson = new DaoIndemnifiedPerson();

        daoIndemnifiedPerson.create(indemnifiedPerson3);
        indemnifiedPerson3.setMiddleName("Ivanov");
        daoIndemnifiedPerson.update(indemnifiedPerson3);
        IIndemnifiedPerson readPersonAfterUp = daoIndemnifiedPerson.read(indemnifiedPerson3.getId());
        Assert.assertTrue(compareIndemnifiedPerson(indemnifiedPerson3, readPersonAfterUp));
        daoIndemnifiedPerson.delete(indemnifiedPerson3.getId());
    }

    @Test
    public void DaoLegalPersonCreate() {

        daoLegalPerson = new DaoLegalPerson();

        daoLegalPerson.create((ILegalPerson) customer1);
        ILegalPerson readLegalPerson = daoLegalPerson.read(customer1.getId());
        Assert.assertTrue(compareLegalPerson((ILegalPerson) customer1, readLegalPerson));
        daoLegalPerson.delete(customer1.getId());
    }

    @Test
    public void DaoLegalPersonUpdate() {

        daoLegalPerson = new DaoLegalPerson();

        daoLegalPerson.create((ILegalPerson) customer1);
        customer1.setAddress("HTZ");
        daoLegalPerson.update((ILegalPerson) customer1);
        ILegalPerson readLegalPerson = daoLegalPerson.read(customer1.getId());
        Assert.assertTrue(compareLegalPerson((ILegalPerson) customer1, readLegalPerson));
        daoLegalPerson.delete(customer1.getId());
    }


    @Test
    public void DaoPrivatePersonCreate() {

        daoPrivatePerson = new DaoPrivatePerson();

        daoPrivatePerson.create((IPrivatePerson) customer2);
        IPrivatePerson readPrivatePerson = daoPrivatePerson.read(customer2.getId());
        Assert.assertTrue(comparePrivatePerson((IPrivatePerson) customer2, readPrivatePerson));
        daoPrivatePerson.delete(customer2.getId());
    }

    @Test
    public void DaoPrivatePersonUpdate() {

        daoPrivatePerson = new DaoPrivatePerson();

        daoPrivatePerson.create((IPrivatePerson) customer2);
        customer2.setAddress("HTZ");
        daoPrivatePerson.update((IPrivatePerson) customer2);
        IPrivatePerson readPrivatePerson = daoPrivatePerson.read(customer2.getId());
        Assert.assertTrue(comparePrivatePerson((IPrivatePerson) customer2, readPrivatePerson));
        daoPrivatePerson.delete(customer2.getId());
    }

    @Test
    public void DaoInsuranceContractCreate() {

        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-template-config.xml");

        daoIndemnifiedPerson = (DaoIndemnifiedPerson) context.getBean("jdbcTemplateIndemnifiedPersonDao");

        daoIndemnifiedPerson.create(indemnifiedPerson1);
        IIndemnifiedPerson resultPerson = daoIndemnifiedPerson.read(indemnifiedPerson1.getId());
        Assert.assertEquals( indemnifiedPerson1.getId(), resultPerson.getId() );
        Assert.assertEquals( indemnifiedPerson1.getFirstName(), resultPerson.getFirstName() );
        Assert.assertEquals( indemnifiedPerson1.getLastName(), resultPerson.getLastName() );
        Assert.assertEquals( indemnifiedPerson1.getMiddleName(), resultPerson.getMiddleName() );
        Assert.assertEquals( indemnifiedPerson1.getBirthDate(), resultPerson.getBirthDate() );
        Assert.assertEquals( Double.compare( indemnifiedPerson1.getCost(), resultPerson.getCost() ), 0 );

        daoLegalPerson = new DaoLegalPerson();
        daoPrivatePerson = new DaoPrivatePerson();
        daoInsuranceContract = new DaoInsuranceContract();

        insuranceContract1.addPerson(indemnifiedPerson1);
        daoInsuranceContract.create(insuranceContract1);
        IInsuranceContract readContract = daoInsuranceContract.read(insuranceContract1.getId());
        Assert.assertEquals(insuranceContract1.getId(), readContract.getId());
        Assert.assertTrue(insuranceContract1.getContractDate().equals(readContract.getContractDate()));
        Assert.assertTrue(insuranceContract1.getContractEffectiveDate().equals(readContract.getContractEffectiveDate()));
        Assert.assertTrue(insuranceContract1.getContractExpireDate().equals(readContract.getContractExpireDate()));
        Assert.assertEquals(insuranceContract1.getCustomer().getId(), readContract.getCustomer().getId());
        Assert.assertEquals(insuranceContract1.getIndemnifiedPersonCollection().size(), readContract.getIndemnifiedPersonCollection().size());
        daoIndemnifiedPerson.delete(indemnifiedPerson1.getId());
        daoInsuranceContract.delete(readContract.getId());
    }

    @Test
    public void DaoInsuranceContractUpdate() {

        daoIndemnifiedPerson = new DaoIndemnifiedPerson();
        daoLegalPerson = new DaoLegalPerson();
        daoPrivatePerson = new DaoPrivatePerson();
        daoInsuranceContract = new DaoInsuranceContract();

        insuranceContract2.addPerson(indemnifiedPerson1);
        daoInsuranceContract.create(insuranceContract2);
        insuranceContract2.addPerson(indemnifiedPerson2);
        daoInsuranceContract.update(insuranceContract2);
        IInsuranceContract readContract = daoInsuranceContract.read(insuranceContract2.getId());

        Assert.assertTrue(insuranceContract2.getContractDate().equals(readContract.getContractDate()));
        Assert.assertTrue(insuranceContract2.getContractEffectiveDate().equals(readContract.getContractEffectiveDate()));
        Assert.assertTrue(insuranceContract2.getContractExpireDate().equals(readContract.getContractExpireDate()));
        Assert.assertEquals(insuranceContract2.getCustomer().getId(), readContract.getCustomer().getId());
        Assert.assertEquals(insuranceContract2.getIndemnifiedPersonCollection().size(), readContract.getIndemnifiedPersonCollection().size());
        daoIndemnifiedPerson.delete(insuranceContract2.getId());
        daoInsuranceContract.delete(readContract.getId());
    }

}

