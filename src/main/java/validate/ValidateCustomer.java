package validate;


import api.ICustomer;
import api.ILegalPerson;
import api.IPrivatePerson;
import dict.PersonStatus;

import java.util.HashMap;
import java.util.Map;

public class ValidateCustomer implements IValidate<ICustomer> {

	@Override
	public Map<String, String> validate(ICustomer customer) {
		Map<String, String> customerValidate = new HashMap<>();
		if (customer.getId() < 0) {
			customerValidate.put("id", "negative id");
		} else if (customer.getAddress() == "") {
			customerValidate.put("address", "empty address");
		} else if (customer.getStatus() == PersonStatus.legalPerson) {
			ValidateLegalPerson validateLegalPerson = new ValidateLegalPerson();
			customerValidate.putAll(validateLegalPerson.validate((ILegalPerson) customer));
		} else if (customer.getStatus() == PersonStatus.privatePerson) {
			ValidatePrivatePerson validatePrivatePerson = new ValidatePrivatePerson();
			customerValidate.putAll(validatePrivatePerson.validate((IPrivatePerson)customer));
		}

		return customerValidate;
	}
}
