package validate;

import api.IPrivatePerson;

import java.util.HashMap;
import java.util.Map;

public class ValidatePrivatePerson implements IValidate<IPrivatePerson> {

	@Override
	public Map<String, String> validate(IPrivatePerson customer) {
		Map<String, String> legalPersonValidate = new HashMap<>();

		if (customer.getLastName() == "") {
			legalPersonValidate.put("lastName", "empty lastName");
		} else if (customer.getFirstName() == "") {
			legalPersonValidate.put("firstName", "empty firstName");
		} else if (customer.getMiddleName() == "") {
			legalPersonValidate.put("middleName", "empty middleName");
		}
		return legalPersonValidate;
	}
}
