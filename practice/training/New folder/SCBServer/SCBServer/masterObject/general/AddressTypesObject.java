package masterObject.general;
import java.io.Serializable;

public class AddressTypesObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String addressDesc;
	int addressCode;

	UserVerifier uv=new UserVerifier();

	public String getAddressDesc(){ return addressDesc;}
	public void setAddressDesc(String modd){this.addressDesc=modd;}

	public int getAddressCode(){ return addressCode;}
	public void setAddressCode(int modc){this.addressCode=modc;}

}
