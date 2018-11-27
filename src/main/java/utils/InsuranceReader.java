package utils;

import api.ICustomer;
import dict.PersonStatus;
import entity.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;

import java.time.LocalDate;

import java.io.IOException;
import java.io.Reader;

//import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public class InsuranceReader {

	private Reader reader;

	public InsuranceReader(Reader reader) {
		this.reader = reader;
	}

	public ArrayList<InsuranceContract> read() {
		ArrayList<InsuranceContract> contractsList = new ArrayList<InsuranceContract>();

		try (CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT)) {
			for (CSVRecord record : parser) {
				InsuranceContract currentContract = readNextContract(record);
				if (currentContract != null) {
					contractsList.add(currentContract);
				}
			}
		} catch (IOException exception) {
		}
		return contractsList;
	}

	private InsuranceContract readNextContract(CSVRecord record) {
		int idx = 0;
		int contractId = Integer.parseInt(record.get(idx++));
		LocalDate contractDate = LocalDate.parse(record.get(idx++));
		LocalDate contractEffectiveDate = LocalDate.parse(record.get(idx++));
		LocalDate contractExpireDate = LocalDate.parse(record.get(idx++));

		AbstractMap.SimpleEntry<ICustomer, Integer> customerInfo = readCustomer(record, idx);
		if (customerInfo.getKey() == null) {
			return null;
		}
		idx = customerInfo.getValue();

		return new InsuranceContract(
				contractId
				, contractDate
				, contractEffectiveDate
				, contractExpireDate
				, customerInfo.getKey()
				, readPersonsContainer(record, idx));
	}

	private AbstractMap.SimpleEntry<ICustomer, Integer> readCustomer(
			CSVRecord record
			, int idx) {
		ICustomer ICustomer = null;
		int id = Integer.valueOf(record.get(idx++));
		PersonStatus status = PersonStatus.valueOf(record.get(idx++));
		if (status.equals(PersonStatus.privatePerson)) {
			String lastName = record.get(idx++);
			String firstName = record.get(idx++);
			String middleName = record.get(idx++);
			String address = record.get(idx++);

			ICustomer = new PrivatePerson(
					id
					, lastName
					, firstName
					, middleName
					, address);
		} else {
			ICustomer = new LegalPerson(
					id
					, record.get(idx++) // organization name
					, record.get(idx++) // address
			);
		}
		return new AbstractMap.SimpleEntry<>(ICustomer, idx);
	}

/*	private AbstractMap.SimpleEntry<Name, Integer> readName(CSVRecord record, int idx) {
		return new AbstractMap.SimpleEntry<>(
				new Name(record.get(idx)*//*first*//*, record.get(idx + 1)*//*last*//*, record.get(idx + 2)*//*middle*//*)
				, idx + 3
		);
	}*/

	private HashMap<Integer, IndemnifiedPerson> readPersonsContainer(CSVRecord record, int idx) {
		HashMap<Integer, IndemnifiedPerson> personsMap = new HashMap<>();

		int personsCount = Integer.parseInt(record.get(idx++));
		for (int i = 0; i < personsCount; ++i) {
			AbstractMap.SimpleEntry<IndemnifiedPerson, Integer> personInfo
					= readIndemnifiedPerson(record, idx);
			idx = personInfo.getValue();

			personsMap.put(personInfo.getKey().getId(), personInfo.getKey());
		}
		return personsMap;
	}

	private AbstractMap.SimpleEntry<IndemnifiedPerson, Integer> readIndemnifiedPerson(
			CSVRecord record
			, int idx) {
		int personId = Integer.parseInt(record.get(idx++));
		String lastName = record.get(idx++);
		String firstName = record.get(idx++);
		String middleName = record.get(idx++);
		double cost = Double.parseDouble(record.get(idx++));
		LocalDate birthDate = LocalDate.parse(record.get(idx++));

		return new AbstractMap.SimpleEntry<>(
				new IndemnifiedPerson(
						personId
						, lastName
						, firstName
						, middleName
						, birthDate
						, cost)
				, idx
		);
	}
}
