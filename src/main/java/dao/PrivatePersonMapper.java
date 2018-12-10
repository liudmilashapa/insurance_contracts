package dao;


import api.IPrivatePerson;
import org.springframework.jdbc.core.RowMapper;
import utils.Factory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrivatePersonMapper implements RowMapper {

    @Override
    public IPrivatePerson mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("personID");
        String lastName = rs.getString("lastName");
        String firstName = rs.getString("firstName");
        String middleName = rs.getString("middleName");
        String address = rs.getString("address");

        Factory factory = new Factory();

        IPrivatePerson person = (IPrivatePerson) factory.createCustomer(
                id, lastName, firstName, middleName, address);
        return person;
    }
}
