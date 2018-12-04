package api;

import java.time.LocalDate;

public interface IIndemnifiedPerson extends IEntity {

	public String getFirstName();

	public String getLastName();

	public String getMiddleName();

	public LocalDate getBirthDate();

	public double getCost();

	public String getFullName();

	public void setId(long id);

	public void setLastName(String lastName);

	public void setFirstName(String firstName);

	public void setMiddleName(String middleName);

	public void setBirthDate(LocalDate birthDate);

	public void setCost(double cost);

}

