package dao;

import api.IPrivatePerson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DaoPrivatePerson implements IDao<IPrivatePerson> {

    String dbName = "insurance_contracts";
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/" + dbName;
    String name = "root";
    String password = "root";
    String privatePersonsTableName = "privatePersonsTable";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DaoPrivatePerson() {
        try {
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + privatePersonsTableName
                    + "("
                    + "id int NOT NULL,"
                    + "lastName varchar(255) NOT NULL,"
                    + "firstName varchar(255) NOT NULL,"
                    + "middleName varchar(255) NOT NULL,"
                    + "address varchar(255) NOT NULL,"
                    + "PRIMARY KEY(id)"
                    + ");"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void create(IPrivatePerson privatePerson) {
//        try {
//            Statement statement = null;
//            statement = connection.createStatement();
        String rawQuery = "INSERT INTO "
                + privatePersonsTableName
                + "(id"
                + ",LastName"
                + ",FirstName"
                + ",MiddleName"
                + ",address)"
                + " VALUES (?, ?, ?, ?, ?, ?);";
                   /* + privatePerson.getId() + ","
                    + "\"" + privatePerson.getLastName() + "\","
                    + "\"" + privatePerson.getFirstName() + "\","
                    + "\"" + privatePerson.getMiddleName() + "\","
                    + "\"" + privatePerson.getAddress() + "\""
                    + ");";

            statement.executeUpdate(rawQuery);*/

        jdbcTemplate.update(
                rawQuery
                , privatePerson.getId()
                , privatePerson.getLastName()
                , privatePerson.getFirstName()
                , privatePerson.getMiddleName()
                , privatePerson.getAddress()
        );

/*        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }

    @Override
    public IPrivatePerson read(long id) {

 /*       try {
            Statement statement = null;
            statement = connection.createStatement();

            if (!hasPrivatePersonById(id)) {
                throw new Exception("Didn't find privatePerson in db");
            }
*/
        String rawQuery = "SELECT "
                + " id "
                + ",LastName"
                + ",FirstName"
                + ",MiddleName"
                + ",address"
                + " FROM "
                + privatePersonsTableName
                + " WHERE id = ?;";

        IPrivatePerson person = (IPrivatePerson) jdbcTemplate.queryForObject(
                rawQuery, new Object[]{id}, new PrivatePersonMapper()
        );
        return person;

         /*   result.next();
            String lastName = result.getString("lastName");
            String firstName = result.getString("firstName");
            String middleName = result.getString("middleName");
            String address = result.getString("address");
            Factory factory = new Factory();
            return (IPrivatePerson) factory.createCustomer(
                    id
                    , lastName
                    , firstName
                    , middleName
                    , address);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;*/
    }

    @Override
    public void update(IPrivatePerson privatePerson) {
        try {
//            Statement statement = null;
//            statement = connection.createStatement();

            if (!hasPrivatePersonById(privatePerson.getId())) {
                throw new Exception("Didn't find privatePerson in db");
            }

            String rawSql = " UPDATE ?"
                    + " SET lastName = ?,"
                    + "firstName = ?,"
                    + "middleName = ?,"
                    + "address = ?"
                    + " WHERE id = ?;";
            jdbcTemplate.update(
                    rawSql
                    , privatePersonsTableName
                    , privatePerson.getLastName()
                    , privatePerson.getFirstName()
                    , privatePerson.getMiddleName()
                    , privatePerson.getAddress()
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

            if (!hasPrivatePersonById(id)) {
                throw new IllegalStateException("Didn't find privatePerson in db");
            }

            String rawSql = " DELETE FROM ? WHERE id = ?";

            jdbcTemplate.update(rawSql, privatePersonsTableName, id);

        } catch (IllegalStateException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean hasPrivatePersonById(long id) {
        try {

            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(" SELECT* "
                    + " FROM "
                    + privatePersonsTableName
                    + " WHERE id = ?;"
            );
            return result.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }


}
