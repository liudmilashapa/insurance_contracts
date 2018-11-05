import jdk.jshell.Snippet;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

//*--------------------------------------------------------------------------------------------------------------*//

public class Test {

//*--------------------------------------------------------------------------------------------------------------*//

    public static void main ( String [] args ) {

        LocalDate date1 = LocalDate.of(1997 ,10 ,10);
        LocalDate date2 = LocalDate.of(2009 ,10 ,10);
        LocalDate date3 = LocalDate.of(2010 ,10 ,10);
        LocalDate date4 = LocalDate.of(2019 ,10 ,10);

        HashSet <IndemnifiedPerson> mySet = new HashSet<IndemnifiedPerson>();
        Customer customer = new Customer("Anna", "Bor", "BBS", "London", PersonStatus.legalPerson);
        IndemnifiedPerson person1 = new IndemnifiedPerson("Bob", "Black", 1999, date1);
        IndemnifiedPerson person2 = new IndemnifiedPerson("Pop", "Black", 7999, date1);
        mySet.add(person1);
        mySet.add(person2);
        InsuranceContract contract1 = new InsuranceContract(1, date2, date3, date4, customer, mySet);

//*--------------------------------------------------------------------------------------------------------------*//

        System.out.println("Insurance contract № " + contract1.getContractId()+":");
        System.out.println("\t Contract date: " + contract1.getContractDate());
        System.out.println("\t Contract effective date: " + contract1.getContractEffectiveDate());
        System.out.println("\t Contract expire date : " + contract1.getContractExpireDate());
        System.out.println("\t Customer:"  );
        System.out.println("\t \t Status: " + contract1.getCustomer().getStatus());
        if (contract1.getCustomer().getStatus()== PersonStatus.legalPerson){
            System.out.println("\t \t Organization name: " + contract1.getCustomer().getOrganizationName());
        }
        else {
            System.out.println("\t \t First name: " + contract1.getCustomer().getFirstName()+ "\n \t \t Last name: " + contract1.getCustomer().getLastName());
        }

        System.out.println("\t \t Address: " + contract1.getCustomer().getAddress());
        System.out.println("\t Indemnified people: " );
        int i=1;
        for (IndemnifiedPerson person: contract1.getIndemnifiedPersonCollection()){
            System.out.println("\t \t Person № "+ (i));
            System.out.println("\t \t \t First name: " + person.getName().getFirstName() + "\n \t \t \t Last name: " + person.getName().getLastName() );
            System.out.println("\t \t \t Birth date: "+ person.getBirthDate());
            System.out.println("\t \t \t Cost: "+ person.getCost());
            i++;
        }
        System.out.println("\t \t Total sum: "+ contract1.insuredSum());

//*--------------------------------------------------------------------------------------------------------------*//

    }
}

//*--------------------------------------------------------------------------------------------------------------*//
