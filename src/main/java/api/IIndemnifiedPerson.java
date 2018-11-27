package api;

import java.time.LocalDate;

public interface IIndemnifiedPerson extends IEntity {

	public String getFirstName();

	public String getLastName();

	public String getMiddleName();

	public LocalDate getBirthDate();

	public double getCost();

	public String getFullName();

	public String getNameWithInitials();

	public void setCost(double cost);
}
