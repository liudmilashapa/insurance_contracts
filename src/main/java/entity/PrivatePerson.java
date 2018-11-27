package entity;

import api.ICustomer;
import api.IPrivatePerson;

public class PrivatePerson implements IPrivatePerson {

	private int id;
	private String lastName;
	private String firstName;
	private String middleName;
	private String address;
	private PersonStatus status;

	public PrivatePerson(
			int id
			, String lastName
			, String firstName
			, String middleName
			, String address) {

		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.address = address;
		this.status = PersonStatus.privatePerson;
	}

	@Override
	public int getId()
	{
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

	public String getAddress() {

		return address;
	}

	public PersonStatus getStatus() {

		return status;
	}

/*	void argumentsIsValid(String address) {
		if (address == "")
			throw new IllegalArgumentException("Invalid address");
	}*/

}
