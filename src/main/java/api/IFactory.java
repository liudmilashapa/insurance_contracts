package api;

import java.time.LocalDate;
import java.util.HashMap;

public interface IFactory {

	public IInsuranceContract createInsuranceContract(
			int contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer ICustomer
			, HashMap indemnifiedPersonCollection);

	public IInsuranceContract createInsuranceContract(
			int contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer ICustomer);

	public ICustomer createCustomer(
			int id
			, String organizationName
			, String address);

	public ICustomer createCustomer(
			int id
			, String lastName
			, String firstName
			, String middleName
			, String address);

	public IIndemnifiedPerson createIndemnifiedPerson(
			int id
			, String lastName
			, String firstName
			, String middleName
			, LocalDate birthDate
			, double cost);

}
