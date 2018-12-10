package dao;

import api.*;
import dict.PersonStatus;
import dao.DaoPrivatePerson;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.Factory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;

public class DaoInsuranceContract implements IDao<IInsuranceContract> {

    String dbName = "insurance_contracts";
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/" + dbName;
    String name = "root";
    String password = "root";
    String insuranceContractsTableName = "insuranceContractsTable";
    String insuranceContractsIndemnifiedPersonsTableName = "insuranceContractsIndemnifiedPersonsTable";

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DaoInsuranceContract() {
        try {
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + insuranceContractsTableName
                    + "("
                    + "id int NOT NULL,"
                    + "contractDate date NOT NULL,"
                    + "contractEffectiveDate date NOT NULL,"
                    + "contractExpireDate date NOT NULL,"
                    + "customerId int NOT NULL ,"
                    + "customerStatus varchar(255) NOT NULL ,"
                    + "PRIMARY KEY(id)"
                    + ");"
            );

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS "
                    + insuranceContractsIndemnifiedPersonsTableName
                    + "("
                    + "contractId int NOT NULL,"
                    + "personId int NOT NULL ,"
                    + "PRIMARY KEY(contractId, personId)"
                    + ");"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void create(IInsuranceContract contract) {
        addCustomerToDb(contract.getCustomer());
        addPersonsToDb(contract.getIndemnifiedPersonCollection());
//        try {
//            Statement statement = null;
//            statement = connection.createStatement();


            String rawQuery = "INSERT INTO "
                    + insuranceContractsTableName
                    + "(id"
                    + ",contractDate"
                    + ",contractEffectiveDate"
                    + ",contractExpireDate"
                    + ",customerId"
                    + ",customerStatus)"
                    + " VALUES (?, ?, ?, ?, ?, ?);";
              /*      + contract.getId() + ","
                    + "\"" + contract.getContractDate() + "\","
                    + "\"" + contract.getContractEffectiveDate() + "\","
                    + "\"" + contract.getContractExpireDate() + "\","
                    + contract.getCustomer().getId() + ","
                    + "\"" + contract.getCustomer().getStatus().toString() + "\""
                    + ");";*/

       /*     statement.executeUpdate(rawQuery);
            addInsuranceContractsIndemnifiedPersonsTable(contract);
*/
        jdbcTemplate.update(
                rawQuery
                , contract.getId()
                , contract.getContractDate().toString()
                , contract.getContractEffectiveDate().toString()
                , contract.getContractExpireDate().toString()
                , contract.getCustomer().getId()
                , contract.getCustomer().getStatus()
        );

 /*       } catch (Exception ex) {
            ex.printStackTrace();
        }*/

    }

    @Override
    public IInsuranceContract read(long id) {
//        try {
//            Statement statement = null;
//            statement = connection.createStatement();
//
//            if (!hasContractById(id)) {
//                throw new Exception("Didn't find contract in db");
//            }

        String rawQuery = "SELECT "
                + " id "
                + ",contractDate"
                + ",contractEffectiveDate"
                + ",contractExpireDate"
                + ",customerId"
                + ",customerStatus"
                + " FROM "
                + insuranceContractsTableName
                + " WHERE id = ?;"
                ;
           /* result.next();
            LocalDate contractDate = result.getDate("contractDate").toLocalDate();
            LocalDate contractEffectiveDate = result.getDate("contractEffectiveDate").toLocalDate();
            LocalDate contractExpireDate = result.getDate("contractExpireDate").toLocalDate();
            Long customerId = result.getLong("customerId");
            PersonStatus status = PersonStatus.valueOf(result.getString("customerStatus"));
            ICustomer customer = readCustomerFromDb(customerId, status);

            Factory factory = new Factory();
            IInsuranceContract contract = factory.createInsuranceContract(id
                    , contractDate
                    , contractEffectiveDate
                    , contractExpireDate
                    , customer);
            ReadPersonsFromDb(contract);
            return contract;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;*/

        IInsuranceContract insuranceContract = (IInsuranceContract) jdbcTemplate.queryForObject(
                rawQuery, new Object[]{id}, new InsuranceContractMapper()
        );

        ReadPersonsFromDb( insuranceContract);
        return insuranceContract;
    }

    @Override
    public void update(IInsuranceContract contract) {
        try {
//            Statement statement = null;
//            statement = connection.createStatement();

            if (!hasContractById(contract.getId())) {
                throw new Exception("Didn't find contract in db");
            }
            updateCustomerFromDb(contract.getCustomer());
            UpdatePersonsFromDb(contract);

            String rawSql = " UPDATE "
                    + insuranceContractsTableName
                    + " SET contractDate = ?,"
                    + "contractEffectiveDate = ?,"
                    + "contractExpireDate = ?,"
                    + "customerId = ?,"
                    + "customerStatus = ?"
                    + " WHERE id = ?;";

            jdbcTemplate.update(
                    rawSql
                    , insuranceContractsTableName
                    , contract.getContractDate().toString()
                    , contract.getContractExpireDate().toString()
                    , contract.getContractExpireDate().toString()
                    , contract.getCustomer().getId()
                    , contract.getCustomer().getStatus()
                    , contract.getId()
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

            if (!hasContractById(id)) {
                throw new Exception("Didn't find contract in db");
            }
            deleteCustomerFromDb(id);
            deleteInsuranceContractsIndemnifiedPersonsTable(id);
            String rawSql =" DELETE FROM ? WHERE id = ?;";

            jdbcTemplate.update(rawSql, insuranceContractsTableName, id);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasContractById(long id) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(" SELECT* "
                    + " FROM "
                    + insuranceContractsTableName
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


    private void addCustomerToDb(ICustomer customer) {
        if (customer.getStatus() == PersonStatus.privatePerson) {
            DaoPrivatePerson privatePerson = new DaoPrivatePerson();
            privatePerson.create((IPrivatePerson) customer);
        } else {
            DaoLegalPerson legalPerson = new DaoLegalPerson();
            legalPerson.create((ILegalPerson) customer);
        }
    }

   /* private ICustomer readCustomerFromDb(Long customerId, PersonStatus status) {
        if (status == PersonStatus.privatePerson) {
            DaoPrivatePerson privatePerson = new DaoPrivatePerson();
            return privatePerson.read(customerId);
        } else {
            DaoLegalPerson legalPerson = new DaoLegalPerson();
            return legalPerson.read(customerId);
        }

    }*/

    private void deleteCustomerFromDb(Long contractId) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT "
                    + " customerId"
                    + ",customerStatus"
                    + " FROM "
                    + insuranceContractsTableName
                    + " WHERE id ="
                    + contractId
                    + ";"
            );
            result.next();
            Long customerId = result.getLong("customerId");
            PersonStatus status = PersonStatus.valueOf(result.getString("customerStatus"));
            if (status == PersonStatus.privatePerson) {
                DaoPrivatePerson daoPrivatePerson = new DaoPrivatePerson();
                daoPrivatePerson.delete(customerId);
            } else {
                DaoLegalPerson daoLegalPerson = new DaoLegalPerson();
                daoLegalPerson.delete(customerId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void updateCustomerFromDb(ICustomer customer) {
        if (customer.getStatus() == PersonStatus.privatePerson) {
            DaoPrivatePerson daoPrivatePerson = new DaoPrivatePerson();
            if (daoPrivatePerson.hasPrivatePersonById(customer.getId())) {
                daoPrivatePerson.update((IPrivatePerson) customer);
            } else {
                daoPrivatePerson.create((IPrivatePerson) customer);
            }
        } else {
            DaoLegalPerson daoLegalPerson = new DaoLegalPerson();
            if (daoLegalPerson.hasLegalPersonById(customer.getId())) {
                daoLegalPerson.update((ILegalPerson) customer);
            } else {
                daoLegalPerson.create((ILegalPerson) customer);
            }
        }
    }

    private void addPersonsToDb(HashMap<Long, IIndemnifiedPerson> personHashMap) {
        if (personHashMap.size() != 0) {
            // context should be passed to insurance contract dao
//            ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-template-config.xml");
//            DaoIndemnifiedPerson personDao = (DaoIndemnifiedPerson) context.getBean("jdbcTemplateIndemnifiedPersonDao");
            DaoIndemnifiedPerson personDao = new DaoIndemnifiedPerson();
            for (IIndemnifiedPerson person : personHashMap.values()) {
                if (!personDao.hasPersonById(person.getId())) {
                    personDao.create(person);
                }
            }
        }
    }

    private void ReadPersonsFromDb(IInsuranceContract contract) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT "
                    + " personId"
                    + " FROM "
                    + insuranceContractsIndemnifiedPersonsTableName
                    + " WHERE contractId ="
                    + contract.getId()
                    + ";"
            );
            DaoIndemnifiedPerson daoIndemnifiedPerson = new DaoIndemnifiedPerson();
            while (result.next()) {
                contract.addPerson(daoIndemnifiedPerson.read(result.getLong("personId")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void UpdatePersonsFromDb(IInsuranceContract contract) {
        deleteInsuranceContractsIndemnifiedPersonsTable(contract.getId());
        DaoIndemnifiedPerson daoIndemnifiedPerson = new DaoIndemnifiedPerson();
        for (IIndemnifiedPerson person : contract.getIndemnifiedPersonCollection().values()
        ) {
            if (daoIndemnifiedPerson.hasPersonById(person.getId())) {
                daoIndemnifiedPerson.update(person);
            } else {
                daoIndemnifiedPerson.create(person);
            }
        }
        addInsuranceContractsIndemnifiedPersonsTable(contract);

    }

    private void addInsuranceContractsIndemnifiedPersonsTable(IInsuranceContract contract) {
        try {
            Statement statement = null;

            statement = connection.createStatement();
            if (contract.getIndemnifiedPersonCollection().size() == 0) {
                return;
            }
            long contractId = contract.getId();
            Iterator<Long> it = contract.getIndemnifiedPersonCollection().keySet().iterator();
            StringBuilder idStr = new StringBuilder("(" + contractId + "," + it.next() + ")");

            while (it.hasNext()) {
                idStr.append(",(" + contractId + "," + it.next() + ")");
            }

            String rawQuery = "INSERT INTO "
                    + insuranceContractsIndemnifiedPersonsTableName
                    + "(contractId"
                    + ",personId)"
                    + " VALUES "
                    + idStr.toString()
                    + ";";

            statement.executeUpdate(rawQuery);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void deleteInsuranceContractsIndemnifiedPersonsTable(Long contractId) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            statement.executeUpdate(" DELETE FROM "
                    + insuranceContractsIndemnifiedPersonsTableName
                    + " WHERE contractId = "
                    + contractId
                    + ";"
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
