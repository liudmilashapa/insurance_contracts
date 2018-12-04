package api;

public interface IPrivatePerson extends ICustomer {

	public String getFirstName();

	public String getLastName();

	public String getMiddleName();

	public void setLastName(String lastName);

	public void setFirstName(String firstName);

	public void setMiddleName(String middleName);
}
