package entity;


import api.ICustomer;
import api.ILegalPerson;

public class LegalPerson implements ILegalPerson {

	private int id;
	private String organizationName;
	private String address;
	private PersonStatus status;

	public LegalPerson(
			int id
			, String organizationName
			, String address) {
		this.id = id;
		this.organizationName = organizationName;
		this.address = address;
		this.status = PersonStatus.legalPerson;
	}

	public int getId() {

		return id;
	}

	public String getOrganizationName() {

		return organizationName;
	}

	public String getAddress() {

		return address;
	}

	public PersonStatus getStatus() {

		return status;
	}

/*	void argumentsIsValid(String organizationName
			, String address) {
		if (organizationName == "" || address == "")
			throw new IllegalArgumentException("Invalid arguments");
	}*/


}



