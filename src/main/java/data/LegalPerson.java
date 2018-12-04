package data;


import api.ILegalPerson;
import dict.PersonStatus;

public class LegalPerson implements ILegalPerson {

	private long id;
	private String organizationName;
	private String address;
	private PersonStatus status;

	public LegalPerson(
			long id
			, String organizationName
			, String address) {
		this.id = id;
		this.organizationName = organizationName;
		this.address = address;
		this.status = PersonStatus.legalPerson;
	}

	public long getId() {

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

	public void setId(long id) {
		this.id = id;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(PersonStatus status) {
		this.status = status;
	}
}



