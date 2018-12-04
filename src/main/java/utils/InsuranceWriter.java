package utils;

import api.*;
import dict.PersonStatus;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.util.ArrayList;

import java.io.IOException;
import java.io.Writer;

public class InsuranceWriter {


	private Writer writer;

	public InsuranceWriter(Writer writer) {
		this.writer = writer;
	}

	public void write(IInsuranceContract contract) {

		try {
			CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
			ArrayList<String> record = new ArrayList<>();
			record.add(Long.toString(contract.getId()));
			record.add(contract.getContractDate().toString());
			record.add(contract.getContractEffectiveDate().toString());
			record.add(contract.getContractExpireDate().toString());
			addCustomerToRecord(contract.getCustomer(), record);
			record.add(Integer.toString(contract.getIndemnifiedPersonCollection().size()));
			for (IIndemnifiedPerson person : contract.getIndemnifiedPersonCollection().values()) {
				addIndemnifiedPersonToRecord(person, record);
			}

			printer.printRecord(record);
			printer.flush();
		} catch (IOException exception) {
		}
	}

	private void addCustomerToRecord(ICustomer customer, ArrayList<String> record) {
		record.add(Long.toString(customer.getId()));
		record.add(customer.getStatus().toString());

		if (customer.getStatus().equals(PersonStatus.privatePerson)) {
			IPrivatePerson privatePerson = (IPrivatePerson) customer;
			privatePerson.getLastName();
			privatePerson.getFirstName();
			privatePerson.getMiddleName();

		} else {
			ILegalPerson legalPerson = (ILegalPerson) customer;
			record.add(legalPerson.getOrganizationName());
		}
		record.add(customer.getAddress());
	}

	private void addIndemnifiedPersonToRecord(IIndemnifiedPerson person, ArrayList<String> record) {
		record.add(Long.toString(person.getId()));
		record.add(person.getLastName());
		record.add(person.getFirstName());
		record.add(person.getMiddleName());
		record.add(Double.toString(person.getCost()));
		record.add(person.getBirthDate().toString());
	}

}
