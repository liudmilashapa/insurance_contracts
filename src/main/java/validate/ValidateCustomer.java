package validate;


import api.ICustomer;
import dict.PersonStatus;

import java.util.HashMap;
import java.util.Map;

public class ValidateCustomer implements IValidate {

	@Override
	public Map<String, String> validate(Object entity) {
		Map<String, String> customerValidate = new HashMap<>();
		ICustomer customer = (ICustomer) entity;
		if (customer.getId() < 0) {
			customerValidate.put("id", "negative id");
		} else if (customer.getAddress() == "") {
			customerValidate.put("address", "empty address");
		} else if (customer.getStatus() == PersonStatus.legalPerson) {
			ValidateLegalPerson validateLegalPerson = new ValidateLegalPerson();
			customerValidate.putAll(validateLegalPerson.validate(customer));
		} else if (customer.getStatus() == PersonStatus.privatePerson) {
			ValidatePrivatePerson validatePrivatePerson = new ValidatePrivatePerson();
			customerValidate.putAll(validatePrivatePerson.validate(customer));
		}

		return customerValidate;
	}
}
