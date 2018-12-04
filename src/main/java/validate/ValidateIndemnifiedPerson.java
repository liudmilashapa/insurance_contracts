package validate;

import api.IIndemnifiedPerson;
import api.IInsuranceContract;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ValidateIndemnifiedPerson implements IValidate<IIndemnifiedPerson> {

    @Override
    public Map<String, String> validate(IIndemnifiedPerson person) {
        Map<String, String> indemnifiedPerson = new HashMap<>();

        if (person.getId() < 0) {
            indemnifiedPerson.put("id", "negative id");
        } else if (person.getLastName() == "") {
            indemnifiedPerson.put("lastName", "empty lastName");
        } else if (person.getFirstName() == "") {
            indemnifiedPerson.put("firstName", "empty firstName");
        } else if (person.getMiddleName() == "") {
            indemnifiedPerson.put("middleName", "empty middleName");
        } else if (person.getCost() < 0) {
            indemnifiedPerson.put("cost", "negative cost");
        } else if (person.getBirthDate().isAfter(LocalDate.now())) {
            indemnifiedPerson.put("birthDate", "birthDate doesn't exist");
        }
        return indemnifiedPerson;

    }
}
