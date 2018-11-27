package entity;

import api.IIndemnifiedPerson;

import java.time.LocalDate;

public class IndemnifiedPerson implements IIndemnifiedPerson {

	private int id;
	private String lastName;
	private String firstName;
	private String middleName;
	private LocalDate birthDate;
	private double cost;

	public IndemnifiedPerson(
			int id
			, String lastName
			, String firstName
			, String middleName
			, LocalDate birthDate
			, double cost) {

		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.cost = cost;
		this.birthDate = birthDate;
	}

	public int getId() {
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

	public LocalDate getBirthDate() {

		return birthDate;
	}

	public double getCost() {

		return cost;
	}

	public String getFullName() {
		return new StringBuilder(getLastName())
				.append(' ')
				.append(getFirstName())
				.append(' ')
				.append(getMiddleName())
				.toString();
	}

	public String getNameWithInitials() {
		return new StringBuilder(getLastName())
				.append(' ')
				.append(getFirstName().charAt(0))
				.append(".")
				.append(getMiddleName().charAt(0))
				.append('.')
				.toString();
	}

	public void setCost(double cost) {

		this.cost = cost;
	}

	/*private void birthDateIsValid(LocalDate birthDate) {
		if (birthDate.isEqual(LocalDate.now()) || birthDate.isAfter(LocalDate.now())){
			throw new IllegalArgumentException("Invalid birth date");
		}
	}

	private void costIsValid(double cost) {
		if (cost <= 0){
			throw new IllegalArgumentException("Invalid cost");
		}
	}

	private void idIsValid(int id) {
		if (id <= 0){
			throw new IllegalArgumentException("Invalid id");
		}
	}
*/

}