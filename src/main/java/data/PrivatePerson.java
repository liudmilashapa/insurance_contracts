package data;

import api.IPrivatePerson;
import dict.PersonStatus;

public class PrivatePerson implements IPrivatePerson {

	private long id;
	private String lastName;
	private String firstName;
	private String middleName;
	private String address;
	private PersonStatus status;

	public PrivatePerson(
			long id
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

	public long getId()
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

	@Override
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

	@Override
	public void setAddress(String address) {
		this.address = address;
	}
}
