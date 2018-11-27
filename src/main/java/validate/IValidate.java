package validate;

import java.util.Map;

interface IValidate<T> {

	Map<String, String> validate(T entity);

}

