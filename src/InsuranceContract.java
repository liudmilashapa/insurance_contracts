import javax.management.BadAttributeValueExpException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

//*--------------------------------------------------------------------------------------------------------------*//

public class InsuranceContract {

//*--------------------------------------------------------------------------------------------------------------*//

    private int contractId;
    private LocalDate contractDate;
    private LocalDate contractEffectiveDate;
    private LocalDate contractExpireDate;
    private Customer customer;
    private HashSet< IndemnifiedPerson > indemnifiedPersonCollection;

//*--------------------------------------------------------------------------------------------------------------*//

    InsuranceContract(int contractId
                    , LocalDate contractDate
                    , LocalDate contractEffectiveDate
                    , LocalDate contractExpireDate
                    , Customer customer
                    , HashSet indemnifiedPersonCollection){
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

//*--------------------------------------------------------------------------------------------------------------*//

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

    public HashSet<IndemnifiedPerson> getIndemnifiedPersonCollection() {
        return indemnifiedPersonCollection;
    }

//*--------------------------------------------------------------------------------------------------------------*//

    private void contractDateIsValid( LocalDate contractDate
                            , LocalDate contractEffectiveDate
            , LocalDate contractExpireDate){
        if(!(( contractDate.isBefore(contractEffectiveDate)
                    || contractDate.isEqual(contractEffectiveDate))
                && ( contractEffectiveDate.isBefore(contractExpireDate)
                    || contractEffectiveDate.isEqual(contractExpireDate))))

        throw new IllegalArgumentException ("Invalid date");

    }

    private void customerIsValid( Customer customer ){
        if (customer == null)
            throw new NullPointerException ();
        }

    private void indemnifiedPersonCollectionIsValid(HashSet<IndemnifiedPerson> indemnifiedPersonCollection, LocalDate contractDate){
        if (indemnifiedPersonCollection == null){
            throw new NullPointerException ();}
        for (IndemnifiedPerson  person : indemnifiedPersonCollection ){
            if(person.getBirthDate().isAfter(contractDate)) {
                throw new DateTimeException("Date of birth should be succeed contract date");
            }
        }
    }

//*--------------------------------------------------------------------------------------------------------------*//

    public double insuredSum(){
        double sum = 0;
        for (IndemnifiedPerson  person : indemnifiedPersonCollection ){
            sum += person.getCost();
        }
        return  sum;
    }
}

//*--------------------------------------------------------------------------------------------------------------*//

