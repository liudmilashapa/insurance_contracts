package api;


import dict.PersonStatus;

public interface ICustomer extends IEntity {

	public String getAddress();

	public PersonStatus getStatus();

}




