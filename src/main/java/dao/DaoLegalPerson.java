package dao;

import api.ILegalPerson;
import utils.Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DaoLegalPerson implements IDao<ILegalPerson> {

    String dbName = "insurance_contracts";
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/" + dbName;
    String name = "root";
    String password = "root";
    String legalPersonsTableName = "legalPersonsTable";

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
        try {
            Statement statement = null;
            statement = connection.createStatement();
            String rawQuery = "INSERT INTO "
                    + legalPersonsTableName
                    + "(id"
                    + ",organizationName"
                    + ",address)"
                    + " VALUES ("
                    + legalPerson.getId() + ","
                    + "\"" + legalPerson.getOrganizationName() + "\","
                    + "\"" + legalPerson.getAddress() + "\""
                    + ");";

            statement.executeUpdate(rawQuery);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ILegalPerson read(long id) {
        try {
            Statement statement = null;
            statement = connection.createStatement();

            if (!hasLegalPersonById(id)) {
                throw new Exception("Didn't find legalPerson in db");
            }

            ResultSet result = statement.executeQuery("SELECT "
                    + " id "
                    + ",organizationName"
                    + ",address"
                    + " FROM "
                    + legalPersonsTableName
                    + " WHERE id ="
                    + id
                    + ";"
            );
            result.next();
            String organizationName = result.getString("organizationName");
            String address = result.getString("address");
            Factory factory = new Factory();
            return (ILegalPerson) factory.createCustomer(
                    id
                    , organizationName
                    ,address);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(ILegalPerson legalPerson) {
        try {
            Statement statement = null;
            statement = connection.createStatement();

            if (!hasLegalPersonById(legalPerson.getId())) {
                throw new Exception("Didn't find legalPerson in db");
            }

            statement.executeUpdate(" UPDATE "
                    + legalPersonsTableName
                    + " SET "
                    + "organizationName=\"" + legalPerson.getOrganizationName() + "\","
                    + "address=\"" + legalPerson.getAddress() + "\""
                    + " WHERE id = " + legalPerson.getId()
                    + ";"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            Statement statement = null;
            statement = connection.createStatement();

            if (!hasLegalPersonById(id)) {
                throw new Exception("Didn't find legalPerson in db");
            }

            statement.executeUpdate(" DELETE FROM "
                    + legalPersonsTableName
                    + " WHERE id = "
                    + id
                    + ";"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasLegalPersonById(long id) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(" SELECT* "
                    + " FROM "
                    + legalPersonsTableName
                    + " WHERE id ="
                    + id
                    + ";"
            );
            return result.next();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
