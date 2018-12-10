
package dao;

import api.ICustomer;
import api.IInsuranceContract;
import dict.PersonStatus;
import org.springframework.jdbc.core.RowMapper;
import utils.Factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InsuranceContractMapper  implements RowMapper {

    @Override
    public IInsuranceContract mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("id");
        LocalDate contractDate = rs.getDate("contractDate").toLocalDate();
        LocalDate contractEffectiveDate = rs.getDate("contractEffectiveDate").toLocalDate();
        LocalDate contractExpireDate = rs.getDate("contractExpireDate").toLocalDate();
        Long customerId = rs.getLong("customerId");
        PersonStatus customerStatus = PersonStatus.valueOf("customerStatus");
        ICustomer customer = readCustomerFromDb(customerId, customerStatus);

        Factory factory = new Factory();

        IInsuranceContract insuranceContract = factory.createInsuranceContract(
                  id
                , contractDate
                , contractEffectiveDate
                , contractExpireDate
                , customer
        );
        return insuranceContract;
    }

    private ICustomer readCustomerFromDb(Long customerId, PersonStatus status) {
        if (status == PersonStatus.privatePerson) {
            DaoPrivatePerson privatePerson = new DaoPrivatePerson();
            return privatePerson.read(customerId);
        } else {
            DaoLegalPerson legalPerson = new DaoLegalPerson();
            return legalPerson.read(customerId);
        }

    }


}
