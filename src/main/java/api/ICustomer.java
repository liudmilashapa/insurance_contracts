package api;


import entity.PersonStatus;

public interface ICustomer extends IEntity {

	public String getAddress();

	public PersonStatus getStatus();

}




