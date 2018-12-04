package service;

import api.ICustomer;
import api.IInsuranceContract;
import api.IIndemnifiedPerson;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class InsuranceContract implements IInsuranceContract {

	private long contractId;
	private LocalDate contractDate;
	private LocalDate contractEffectiveDate;
	private LocalDate contractExpireDate;
	private ICustomer ICustomer;
	private HashMap<Long, IIndemnifiedPerson> indemnifiedPersonCollection;

	public InsuranceContract(
			long contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer customer
			, HashMap indemnifiedPersonCollection) {

		this.contractId = contractId;
		this.contractDate = contractDate;
		this.contractEffectiveDate = contractEffectiveDate;
		this.contractExpireDate = contractExpireDate;
		this.ICustomer = customer;
		this.indemnifiedPersonCollection = indemnifiedPersonCollection;
	}

	public InsuranceContract(
			long contractId
			, LocalDate contractDate
			, LocalDate contractEffectiveDate
			, LocalDate contractExpireDate
			, ICustomer customer) {

		this.contractId = contractId;
		this.contractDate = contractDate;
		this.contractEffectiveDate = contractEffectiveDate;
		this.contractExpireDate = contractExpireDate;
		this.ICustomer = customer;
		this.indemnifiedPersonCollection = new HashMap<>();
	}

	public long getId() {

		return contractId;
	}

	public LocalDate getContractDate() {

		return contractDate;
	}

	public LocalDate getContractEffectiveDate() {

		return contractEffectiveDate;
	}

	public LocalDate getContractExpireDate() {

		return contractExpireDate;
	}

	public ICustomer getCustomer() {

		return ICustomer;
	}

	public HashMap<Long, IIndemnifiedPerson> getIndemnifiedPersonCollection() {

		return indemnifiedPersonCollection;
	}

	public void addPerson(IIndemnifiedPerson person) {
		indemnifiedPersonDateIsValid(person);
		indemnifiedPersonIdIsValid(person);
		indemnifiedPersonCollection.put(person.getId(), person);
	}

	public IIndemnifiedPerson findPerson(long id) {
		IIndemnifiedPerson personValue = indemnifiedPersonCollection.get(id);
		if (personValue != null) {
			return personValue;
		} else {
			return null;
		}
	}

	public List<IIndemnifiedPerson> sortInsurancePersonByName() {
		List<IIndemnifiedPerson> sortList = new ArrayList<>(indemnifiedPersonCollection.values());
		Comparator<IIndemnifiedPerson> byName = new Comparator<IIndemnifiedPerson>() {
			@Override
			public int compare(IIndemnifiedPerson o1, IIndemnifiedPerson o2) {
				return o1.getFullName().compareTo(o2.getFullName());
			}
		};
		Collections.sort(sortList, byName);
		return sortList;
	}

	public List<IIndemnifiedPerson> sortInsurancePersonBirthDate() {
		List<IIndemnifiedPerson> sortList = new ArrayList<>(indemnifiedPersonCollection.values());
		Comparator<IIndemnifiedPerson> byBirth = new Comparator<IIndemnifiedPerson>() {
			@Override
			public int compare(IIndemnifiedPerson o1, IIndemnifiedPerson o2) {
				return o1.getBirthDate().compareTo(o2.getBirthDate());
			}
		};
		Collections.sort(sortList, byBirth);
		return sortList;
	}

	private void indemnifiedPersonDateIsValid(IIndemnifiedPerson person) {
		boolean cond1 = person.getBirthDate().isAfter(contractDate);
		if (cond1) {
			throw new DateTimeException("Date of birth should be succeed contract date");
		}
	}

	private void indemnifiedPersonIdIsValid(IIndemnifiedPerson person) {
		if (indemnifiedPersonCollection.containsKey(person.getId())) {
			throw new IllegalArgumentException("This person has been added");
		}
	}

	public double insuredSum() {
		double sum = 0;
		for (IIndemnifiedPerson person : indemnifiedPersonCollection.values()) {
			sum += person.getCost();
		}
		return sum;
	}

	public double insuredSumByLambda() {
		double[] sum = new double[1];

		indemnifiedPersonCollection.forEach(
				(k, v) -> {
					sum[0] += v.getCost();
				}

		);

		return sum[0];
	}
}


