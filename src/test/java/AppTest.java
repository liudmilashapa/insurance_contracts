import api.*;
import dict.PersonStatus;
import org.junit.*;

import entity.*;
import utils.*;
import org.junit.rules.ExpectedException;
import validate.ValidateCustomer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

import java.io.Reader;
import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.nio.file.Files;

public class AppTest {


	IFactory factory = new Factory();
	LocalDate date1;
	LocalDate date2;
	LocalDate date3;
	LocalDate date4;
	LocalDate date5;

	ICustomer customer1;

	IIndemnifiedPerson indemnifiedPerson1;
	IIndemnifiedPerson indemnifiedPerson2;
	IIndemnifiedPerson indemnifiedPerson3;
	IIndemnifiedPerson indemnifiedPerson4;


	HashMap<Integer, IndemnifiedPerson> map1;

	IInsuranceContract insuranceContract1;

	String path;

	HashMap<String, String> validateMap;

	ValidateCustomer validateCustomer;

	@Before
	public void initialize() {

		date1 = LocalDate.of(1999, 10, 10);
		date2 = LocalDate.of(1999, 12, 9);
		date3 = LocalDate.of(2000, 10, 10);
		date4 = LocalDate.of(2010, 12, 9);
		date5 = LocalDate.of(2018, 12, 9);

		indemnifiedPerson1 = factory.createIndemnifiedPerson(1, "Ros", "Bob", "Endy", date1, 1000);
		indemnifiedPerson2 = factory.createIndemnifiedPerson(2, "Ros", "Liz", "Bob", date2, 2000);
		indemnifiedPerson3 = factory.createIndemnifiedPerson(3, "Ros", "Emi", "Endy", date3, 4000);
		indemnifiedPerson4 = factory.createIndemnifiedPerson(2, "Ros", "Min", "Bob", date4, 0.1);

		customer1 = factory.createCustomer(1, "BBC", "London");

		map1 = new HashMap<>();

		insuranceContract1 = factory.createInsuranceContract(1, date3, date4, date5, customer1, map1);

		path = "./test.csv";

		validateMap = new HashMap<>();

		validateCustomer = new ValidateCustomer();


	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

/*	@Test
	public void invalidLegalPersonCreationTest() throws IllegalArgumentException {
		ICustomer legalPerson = factory.createCustomer(1, "", "");

		validateMap.putAll(validateCustomer.validate(legalPerson));



	}

	@Test
	public void invalidPrivatePersonCreationTest() throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Invalid address");

		PrivatePerson privatePerson = new PrivatePerson(2, "Bob", "Ros", "Endy", "");
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
  */

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

}




