package utils;

import api.ICustomer;
import data.IndemnifiedPerson;
import data.LegalPerson;
import data.PrivatePerson;
import api.IIndemnifiedPerson;
import api.IInsuranceContract;
import service.InsuranceContract;

import java.time.LocalDate;
import java.util.HashMap;

public class Factory implements IFactory {

	public IInsuranceContract createInsuranceContract(
			long contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer ICustomer
			, HashMap indemnifiedPersonCollection) {

		return new InsuranceContract(
				contractId
				, contractDate
				, contractEffectiveDate
				, contractExpireDate
				, ICustomer
				, indemnifiedPersonCollection);
	}

	;

	public IInsuranceContract createInsuranceContract(
			long contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer ICustomer) {

		return new InsuranceContract(
				contractId
				, contractDate
				, contractEffectiveDate
				, contractExpireDate
				, ICustomer);
	}

	;

	public ICustomer createCustomer(
			long id
			, String organizationName
			, String address) {

		return new LegalPerson(
				id
				, organizationName
				, address);
	}

	;

	public ICustomer createCustomer(
			long id
			, String lastName
			, String firstName
			, String middleName
			, String address) {

		return new PrivatePerson(
				id
				, lastName
				, firstName
				, middleName
				, address);
	}

	;

	public IIndemnifiedPerson createIndemnifiedPerson(
			long id
			, String lastName
			, String firstName
			, String middleName
			, LocalDate birthDate
			, double cost) {

		return new IndemnifiedPerson(
				id
				, lastName
				, firstName
				, middleName
				, birthDate
				, cost);
	}

	;
}
