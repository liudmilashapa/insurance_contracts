package validate;

import api.IIndemnifiedPerson;
import api.IInsuranceContract;
import entity.InsuranceContract;

import java.util.HashMap;
import java.util.Map;

public class ValidateInsuranceContract implements IValidate {

	@Override
	public Map<String, String> validate(Object entity) {
		Map<String, String> contractValidate = new HashMap<>();
		InsuranceContract insuranceContract = (InsuranceContract) entity;
		if (insuranceContract.getId() <= 0) {
			contractValidate.put("id", "negative id");
		} else if (insuranceContract.getCustomer() == null) {
			contractValidate.put("customer", "empty customer");
		} else if (insuranceContract.getContractDate().isAfter(insuranceContract.getContractEffectiveDate())) {
			contractValidate.put("contractDate", "invalid contractDate");
		} else if (insuranceContract.getContractEffectiveDate().isAfter(insuranceContract.getContractExpireDate())) {
			contractValidate.put("effectiveDate", "invalid effectiveDate");
		}

		ValidateCustomer validateCustomer = new ValidateCustomer();
		contractValidate.putAll(validateCustomer.validate(insuranceContract.getCustomer()));
		if (insuranceContract.getIndemnifiedPersonCollection() != null) {
			ValidateIndemnifiedPerson validateIndemnifiedPerson = new ValidateIndemnifiedPerson();
			for (IIndemnifiedPerson person
					: insuranceContract.getIndemnifiedPersonCollection().values()) {
				contractValidate.putAll(validateIndemnifiedPerson.validate(person));
			}
		}
		return contractValidate;
	}
}
