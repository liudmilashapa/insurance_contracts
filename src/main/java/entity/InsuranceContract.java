package entity;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class InsuranceContract {

    private int contractId;
    private LocalDate contractDate;
    private LocalDate contractEffectiveDate;
    private LocalDate contractExpireDate;
    private Customer customer;
    private HashMap < Integer, IndemnifiedPerson > indemnifiedPersonCollection;

    public InsuranceContract(int contractId
                    , LocalDate contractDate
                    , LocalDate contractEffectiveDate
                    , LocalDate contractExpireDate
                    , Customer customer
                    , HashMap indemnifiedPersonCollection){
        contractDateIsValid(  contractDate
                            , contractEffectiveDate
                            , contractExpireDate);
        customerIsValid(customer);
        indemnifiedPersonCollectionIsValid(indemnifiedPersonCollection, contractDate);

        this.contractId = contractId;
        this.contractDate = contractDate;
        this.contractEffectiveDate = contractEffectiveDate;
        this.contractExpireDate = contractExpireDate;
        this.customer = customer;
        this.indemnifiedPersonCollection = indemnifiedPersonCollection;
    }

    public int getContractId() {
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

    public Customer getCustomer() {
        return customer;
    }

    public HashMap<Integer, IndemnifiedPerson> getIndemnifiedPersonCollection() {
        return indemnifiedPersonCollection;
    }

    public void addPerson(IndemnifiedPerson person) {
        if(indemnifiedPersonCollection.containsKey(person.getId())){

            throw new IllegalArgumentException("This person has been added");
        }
        else {
            indemnifiedPersonCollection.put(person.getId(), person);
        }
    }

    public IndemnifiedPerson findPerson(int id){
        IndemnifiedPerson personValue = indemnifiedPersonCollection.get(id);
        if (personValue !=null)
                return personValue;
        else return null;
    }

    public void sortInsurancePersonByName(){
        List<IndemnifiedPerson> sortList = new ArrayList<IndemnifiedPerson>(indemnifiedPersonCollection.values());
        Comparator<IndemnifiedPerson> byName = new Comparator<IndemnifiedPerson>() {
            @Override
            public int compare(IndemnifiedPerson o1, IndemnifiedPerson o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        Collections.sort(sortList, byName);
        outputInsurancePerson(sortList);
    }

    public void sortInsurancePersonBirthDate(){
        List<IndemnifiedPerson> sortList = new ArrayList<IndemnifiedPerson>(indemnifiedPersonCollection.values());
        Comparator<IndemnifiedPerson> byBirth = new Comparator<IndemnifiedPerson>() {
            @Override
            public int compare(IndemnifiedPerson o1, IndemnifiedPerson o2) {
                return o1.getBirthDate().compareTo(o2.getBirthDate());
            }
        };
        Collections.sort(sortList, byBirth);
        outputInsurancePerson(sortList);
    }

    private void outputInsurancePerson(List<IndemnifiedPerson> personList){
        System.out.println("\t Indemnified people: ");
        for (IndemnifiedPerson person:personList) {
            person.outputPersonInformation();
        }
    }


    private void contractDateIsValid( LocalDate contractDate
                                    , LocalDate contractEffectiveDate
                                    , LocalDate contractExpireDate){
        boolean condition1 = contractDate.isBefore(contractExpireDate)
                          || contractDate.isEqual(contractExpireDate);
        boolean condition2 = contractEffectiveDate.isBefore(contractExpireDate)
                          || contractEffectiveDate.isEqual(contractExpireDate);
        if(!(( condition1 ) && ( condition2))) {
            throw new IllegalArgumentException ("Invalid date");
        }
    }

    private void customerIsValid( Customer customer ){
        if (customer == null)
            throw new NullPointerException ();
        }

    private void indemnifiedPersonCollectionIsValid(HashMap<Integer, IndemnifiedPerson> indemnifiedPersonCollection, LocalDate contractDate){
        if (indemnifiedPersonCollection == null){
            throw new NullPointerException ();}
        for (IndemnifiedPerson  person : indemnifiedPersonCollection.values() ){
            if(person.getBirthDate().isAfter(contractDate)) {
                throw new DateTimeException("Date of birth should be succeed contract date");
            }
        }
    }

    public double insuredSum(){
        double sum = 0;
        for (IndemnifiedPerson person : indemnifiedPersonCollection.values() ){
            sum += person.getCost();
        }
        return  sum;
    }

    public double insuredSumByLambda(){
        double[] sum = new double[1];

        indemnifiedPersonCollection.forEach(
                ( k, v) -> {
                    sum[0] += v.getCost();
                }

        );

        return  sum[0];
    }
}


