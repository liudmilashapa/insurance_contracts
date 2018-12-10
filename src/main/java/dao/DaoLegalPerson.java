package dao;

import api.ILegalPerson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DaoLegalPerson implements IDao<ILegalPerson> {

    String dbName = "insurance_contracts";
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/" + dbName;
    String name = "root";
    String password = "root";
    String legalPersonsTableName = "legalPersonsTable";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DaoLegalPerson() {
        try {
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + legalPersonsTableName
                    + "("
                    + "id int NOT NULL,"
                    + "organizationName varchar(255),"
                    + "address varchar(255),"
                    + "PRIMARY KEY(id)"
                    + ");"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void create(ILegalPerson legalPerson) {

        String rawQuery = "INSERT INTO "
                + legalPersonsTableName
                + "(id"
                + ",organizationName"
                + ",address)"
                + " VALUES ( ?, ?, ?, ?);";


        jdbcTemplate.update(
                rawQuery
                , legalPerson.getId()
                , legalPerson.getOrganizationName()
                , legalPerson.getAddress()
        );

    }

    @Override
    public ILegalPerson read(long id) {

        String rawQuery = "SELECT "
                + " id "
                + ",organizationName"
                + ",address"
                + " FROM "
                + legalPersonsTableName
                + " WHERE id = ?;";

        ILegalPerson person = (ILegalPerson) jdbcTemplate.queryForObject(
                rawQuery, new Object[]{id}, new LegalPersonMapper()
        );

        return person;

    }

    @Override
    public void update(ILegalPerson legalPerson) {
        try {
//                Statement statement = null;
//                statement = connection.createStatement();

            if (!hasLegalPersonById(legalPerson.getId())) {
                throw new Exception("Didn't find legalPerson in db");
            }

            String rawSql = " UPDATE ?"
                    + " SET organizationName = ?, "
                    + "address = ?"
                    + " WHERE id = ?;";
            jdbcTemplate.update(
                    rawSql
                    , legalPersonsTableName
                    , legalPerson.getId()
                    , legalPerson.getOrganizationName()
                    , legalPerson.getAddress()
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
//                Statement statement = null;
//                statement = connection.createStatement();

            if (!hasLegalPersonById(id)) {
                throw new Exception("Didn't find legalPerson in db");
            }

            String rawSql = " DELETE FROM ? WHERE id = ?;";

            jdbcTemplate.update(rawSql, legalPersonsTableName, id);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasLegalPersonById(long id) {
        try {

            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(" SELECT* "
                    + " FROM "
                    + legalPersonsTableName
                    + " WHERE id = ?;"
            );
            return result.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
