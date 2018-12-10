package dao;

import api.IIndemnifiedPerson;
import utils.Factory;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IndemnifiedPersonMapper implements RowMapper {

    @Override
    public IIndemnifiedPerson mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("id");
        String lastName = rs.getString("lastName");
        String firstName = rs.getString("firstName");
        String middleName = rs.getString("middleName");
        LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
        Double cost = rs.getDouble("cost");

        Factory factory = new Factory();

        IIndemnifiedPerson person = factory.createIndemnifiedPerson(
                id, lastName, firstName, middleName, birthDate, cost
        );
        return person;
    }
}
