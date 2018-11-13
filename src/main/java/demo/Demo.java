package demo;

import lombok.*;
import java.time.*;
import java.util.HashMap;
import java.util.HashSet;
import entity.*;

//*--------------------------------------------------------------------------------------------------------------*//

public class Demo {

//*--------------------------------------------------------------------------------------------------------------*//



    static void outputContractInfo(InsuranceContract contract1) {
        System.out.println("Insurance contract № " + contract1.getContractId() + ":");
        System.out.println("\t Contract date: " + contract1.getContractDate());
        System.out.println("\t Contract effective date: " + contract1.getContractEffectiveDate());
        System.out.println("\t Contract expire date : " + contract1.getContractExpireDate());
    }
    static void outputCustomer(InsuranceContract contract1){
        System.out.println("\t Customer:"  );
        System.out.println("\t \t Status: " + contract1.getCustomer().getStatus());
        if (contract1.getCustomer().getStatus()== PersonStatus.legalPerson){
            System.out.println("\t \t Organization name: " + contract1.getCustomer().getName());
        }
        else {
            System.out.println("\t \t Name: " + contract1.getCustomer().getName());
        }

        System.out.println("\t \t Address: " + contract1.getCustomer().getAddress());
    }

    public static void main ( String [] args ) {

        LocalDate date1 = LocalDate.of(1997 ,10 ,10);
        LocalDate date2 = LocalDate.of(2009 ,10 ,10);
        LocalDate date3 = LocalDate.of(2010 ,10 ,10);
        LocalDate date4 = LocalDate.of(2019 ,10 ,10);

        HashMap<Integer, IndemnifiedPerson> myMap = new HashMap();
        PrivatePerson customer  = new PrivatePerson ("Anna", "Bor", "Henry",  "London");
        IndemnifiedPerson person1 = new IndemnifiedPerson(1,"Bob", "Black", "Ron", 1999, date1);
        IndemnifiedPerson person2 = new IndemnifiedPerson(2,"Pop", "Black", "Ron", 7999, date1);
        myMap.put(person1.getId(),person1);
        myMap.put(person2.getId(),person2);
        InsuranceContract contract1 = new InsuranceContract(1, date2, date3, date4, customer, myMap);

//*--------------------------------------------------------------------------------------------------------------*//

        outputContractInfo(contract1);
        outputCustomer(contract1);
        contract1.sortInsurancePersonBirthDate();
        System.out.println("\t \t Total sum by ranged for: " + contract1.insuredSum());
        System.out.println("\t \t Total sum by foreach: " + contract1.insuredSumByLambda());

    }
}
