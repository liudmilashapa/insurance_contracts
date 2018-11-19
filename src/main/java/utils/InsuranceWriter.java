package utils;

import entity.*;
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

    public void write(InsuranceContract contract){

        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            ArrayList<String> record = new ArrayList<String>();
            record.add(Integer.toString(contract.getContractId()));
            record.add(contract.getContractDate().toString());
            record.add(contract.getContractEffectiveDate().toString());
            record.add(contract.getContractExpireDate().toString());
            addCustomerToRecord(contract.getCustomer(), record);
            record.add(Integer.toString(contract.getIndemnifiedPersonCollection().size()));
            for (IndemnifiedPerson person : contract.getIndemnifiedPersonCollection().values()) {
                addIndemnifiedPersonToRecord(person, record);
            }

            printer.printRecord(record);
            printer.flush();
        }
        catch ( IOException exception )
        {}
    }

    private void addCustomerToRecord(Customer customer, ArrayList<String> record) {
        record.add( customer.getStatus().toString() );

        if( customer.getStatus().equals( PersonStatus.privatePerson ) ) {
            Name name = ((PrivatePerson)customer).getNameAsObject();
            addNameInfo(name, record);
        }
        else {
            record.add( customer.getName() );
        }
        record.add( customer.getAddress() );
    }

    private void addIndemnifiedPersonToRecord(IndemnifiedPerson person, ArrayList<String> record) {
        record.add(Integer.toString(person.getId()));
        addNameInfo(person.getName(), record);
        record.add( Double.toString(person.getCost()) );
        record.add( person.getBirthDate().toString() );
    }

    private void addNameInfo ( Name name, ArrayList<String> record) {
        record.add( name.getFirstName() );
        record.add( name.getLastName() );
        record.add( name.getMiddleName() );
    }
}
