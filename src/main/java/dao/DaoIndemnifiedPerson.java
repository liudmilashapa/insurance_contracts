package dao;

import api.IIndemnifiedPerson;
import utils.Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DaoIndemnifiedPerson implements IDao<IIndemnifiedPerson> {

    String dbName = "insurance_contracts";
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/" + dbName;
    String name = "root";
    String password = "root";
    String indemnifiedPersonsTableName = "IndemnifiedPersonsTable";


    public DaoIndemnifiedPerson() {
        try {
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + indemnifiedPersonsTableName
                    + "("
                    + "personID int NOT NULL,"
                    + "lastName varchar(255),"
                    + "firstName varchar(255),"
                    + "middleName varchar(255),"
                    + "birthDate date ,"
                    + "cost real,"
                    + "PRIMARY KEY(PersonID)"
                    + ");"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void create(IIndemnifiedPerson person) {
        try {
            Statement statement = null;
            statement = connection.createStatement();

            if (hasPersonById(person.getId())) {
                throw new Exception("Person already exists in db");
            }

            String rawQuery = "INSERT INTO "
                    + indemnifiedPersonsTableName
                    + "(PersonID"
                    + ",LastName"
                    + ",FirstName"
                    + ",MiddleName"
                    + ",birthDate"
                    + ",cost)"
                    + " VALUES ("
                    + person.getId() + ","
                    + "\"" + person.getLastName() + "\","
                    + "\"" + person.getFirstName() + "\","
                    + "\"" + person.getMiddleName() + "\","
                    + "\"" + person.getBirthDate().toString() + "\","
                    + person.getCost()
                    + ");";

            statement.executeUpdate(rawQuery);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public IIndemnifiedPerson read(long id) {
        try {
            Statement statement = null;
            statement = connection.createStatement();

            if (!hasPersonById(id)) {
                throw new Exception("Didn't find person in db");
            }

            ResultSet result = statement.executeQuery("SELECT "
                    + " personID, "
                    + " lastName, "
                    + " firstName, "
                    + " middleName, "
                    + " birthDate, "
                    + " cost "
                    + " FROM "
                    + indemnifiedPersonsTableName
                    + " WHERE personID ="
                    + id
                    + ";"
            );
            result.next();
            String lastName = result.getString("lastName");
            String firstName = result.getString("firstName");
            String middleName = result.getString("middleName");
            LocalDate birthDate = result.getDate("birthDate").toLocalDate();
            Double cost = result.getDouble("cost");

            Factory factory = new Factory();
            return factory.createIndemnifiedPerson(
                    id
                    , lastName
                    , firstName
                    , middleName
                    , birthDate
                    , cost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(IIndemnifiedPerson person) {
        try {
            Statement statement = null;
            statement = connection.createStatement();

            if (!hasPersonById(person.getId())) {
                throw new Exception("Didn't find person in db");
            }

            statement.executeUpdate(" UPDATE "
                    + indemnifiedPersonsTableName
                    + " SET "
                    + "lastName=\"" + person.getLastName() + "\","
                    + "firstName=\"" + person.getFirstName() + "\","
                    + "middleName=\"" + person.getMiddleName() + "\","
                    + "birthDate=\"" + person.getBirthDate().toString() + "\","
                    + "cost=" + person.getCost()
                    + " WHERE personID = " + person.getId()
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

            if (!hasPersonById(id)) {
                throw new Exception("Didn't find person in db");
            }

            statement.executeUpdate(" DELETE FROM "
                    + indemnifiedPersonsTableName
                    + " WHERE personID = "
                    + id
                    + ";"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasPersonById(long id) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(" SELECT* "
                    + " FROM "
                    + indemnifiedPersonsTableName
                    + " WHERE personID ="
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
