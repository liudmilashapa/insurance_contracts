package dao;

import api.IIndemnifiedPerson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DaoIndemnifiedPerson implements IDao<IIndemnifiedPerson> {

    String dbName = "insurance_contracts";
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/" + dbName;
    String name = "root";
    String password = "root";
    String indemnifiedPersonsTableName = "IndemnifiedPersonsTable";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DaoIndemnifiedPerson() {
        try {
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + indemnifiedPersonsTableName
                    + "("
                    + "id int NOT NULL,"
                    + "lastName varchar(255),"
                    + "firstName varchar(255),"
                    + "middleName varchar(255),"
                    + "birthDate date ,"
                    + "cost real,"
                    + "PRIMARY KEY(id)"
                    + ");"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void create(IIndemnifiedPerson person) {

        String rawQuery = "INSERT INTO "
                + indemnifiedPersonsTableName
                + "(id"
                + ",LastName"
                + ",FirstName"
                + ",MiddleName"
                + ",birthDate"
                + ",cost)"
                + " VALUES ( ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(
                rawQuery
                , person.getId()
                , person.getLastName()
                , person.getFirstName()
                , person.getMiddleName()
                , person.getBirthDate().toString()
                , person.getCost()
        );

    }

    @Override
    public IIndemnifiedPerson read(long id) {

        String rawQuery = "SELECT "
                + " id, "
                + " lastName, "
                + " firstName, "
                + " middleName, "
                + " birthDate, "
                + " cost "
                + " FROM "
                + indemnifiedPersonsTableName
                + " WHERE id = ?;";

        IIndemnifiedPerson person = (IIndemnifiedPerson) jdbcTemplate.queryForObject(
                rawQuery, new Object[]{id}, new IndemnifiedPersonMapper()
        );
        return person;
    }

    @Override
    public void update(IIndemnifiedPerson person) {
        try {
//            Statement statement = null;
//            statement = connection.createStatement();

            if (!hasPersonById(person.getId())) {
                throw new Exception("Didn't find person in db");
            }

            String rawSql = " UPDATE ?"
                    + " SET lastName=?, "
                    + "firstName=?, "
                    + "middleName=?, "
                    + "birthDate=?, cost=?"
                    + " WHERE id = ?;";

            jdbcTemplate.update(
                    rawSql
                    , indemnifiedPersonsTableName
                    , person.getLastName()
                    , person.getFirstName()
                    , person.getMiddleName()
                    , person.getBirthDate().toString()
                    , person.getCost()
                    , person.getId()
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
//            Statement statement = null;
//            statement = connection.createStatement();

            if (!hasPersonById(id)) {
                throw new Exception("Didn't find person in db");
            }

            String rawSql = " DELETE FROM ? WHERE id = ?;";

            jdbcTemplate.update(rawSql, indemnifiedPersonsTableName, id);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasPersonById(long id) {
        try {

            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(" SELECT* "
                    + " FROM "
                    + indemnifiedPersonsTableName
                    + " WHERE id = ?;"
            );
            return result.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
