package utils;

import api.ICustomer;
import api.IIndemnifiedPerson;
import api.IInsuranceContract;

import java.time.LocalDate;
import java.util.HashMap;

public interface IFactory {

	public IInsuranceContract createInsuranceContract(
			long contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer ICustomer
			, HashMap indemnifiedPersonCollection);

	public IInsuranceContract createInsuranceContract(
			long contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer ICustomer);

	public ICustomer createCustomer(
			long id
			, String organizationName
			, String address);

	public ICustomer createCustomer(
			long id
			, String lastName
			, String firstName
			, String middleName
			, String address);

	public IIndemnifiedPerson createIndemnifiedPerson(
			long id
			, String lastName
			, String firstName
			, String middleName
			, LocalDate birthDate
			, double cost);

}
