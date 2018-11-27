package validate;

import api.ILegalPerson;

import java.util.HashMap;
import java.util.Map;

public class ValidateLegalPerson implements IValidate {

	@Override
	public Map<String, String> validate(Object entity) {
		Map<String, String> legalPersonValidate = new HashMap<>();
		ILegalPerson customer = (ILegalPerson) entity;
		if (customer.getOrganizationName() == "") {
			legalPersonValidate.put("organizationName", "empty organizationName");
		}
		return legalPersonValidate;
	}
}
