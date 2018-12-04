package api;


import dict.PersonStatus;

public interface ICustomer extends IEntity {

	public long getId();

	public String getAddress();

	public PersonStatus getStatus();


	public void setId(long id);

	public void setAddress(String address);



}




