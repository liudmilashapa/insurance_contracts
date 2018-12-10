package dao;


import api.ILegalPerson;
import org.springframework.jdbc.core.RowMapper;
import utils.Factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LegalPersonMapper implements RowMapper {

    @Override
    public ILegalPerson mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("personID");
        String organizationName = rs.getString("organizationName");
        String address = rs.getString("address");

        Factory factory = new Factory();

        ILegalPerson person = (ILegalPerson) factory.createCustomer(
                id, organizationName, address);
        return person;
    }
}

