package validate;

import api.ILegalPerson;

import java.util.HashMap;
import java.util.Map;

public class ValidateLegalPerson implements IValidate<ILegalPerson> {

	@Override
	public Map<String, String> validate(ILegalPerson customer) {
		Map<String, String> legalPersonValidate = new HashMap<>();

		if (customer.getOrganizationName() == "") {
			legalPersonValidate.put("organizationName", "empty organizationName");
		}
		return legalPersonValidate;
	}
}
