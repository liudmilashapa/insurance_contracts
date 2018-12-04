package api;

import java.time.LocalDate;
import java.util.*;

public interface IInsuranceContract extends IEntity {

	public LocalDate getContractDate();

	public LocalDate getContractEffectiveDate();

	public LocalDate getContractExpireDate();

	public ICustomer getCustomer();

	public HashMap<Long, IIndemnifiedPerson> getIndemnifiedPersonCollection();

	public void addPerson(IIndemnifiedPerson person);

	public IIndemnifiedPerson findPerson(long id);

	public List<IIndemnifiedPerson> sortInsurancePersonByName();

	public List<IIndemnifiedPerson> sortInsurancePersonBirthDate();

	public double insuredSum();

	public double insuredSumByLambda();

}
