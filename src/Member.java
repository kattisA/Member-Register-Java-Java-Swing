import java.io.Serializable;

/**
 * Created by kattis on 10/03/17.
 * Exercise Java D0018D
 */
public class Member implements Serializable
{
    private String name;
    private int idNumber;
    private String address;
    private String district;
    private int postalCode;
    private int phonenumber;

    public Member( int idNr)
    {
        name = "";
        address = "";
        district = "";
        postalCode = 0;
        phonenumber = 0;
    }

    public Member(int idNr, String name, String address, String district, int postal, int phonenumber)
    {
        idNumber = idNr;
        this.name = name;
        this.address = address;
        this.district = district;
        postalCode = postal;
        this.phonenumber = phonenumber;
    }

    //Getters and setters for oinstance variables
    public String getName() { return name; }

    public void setName(String newName)
    {
        if( newName.length() > 0)
        {
            this.name = newName;
        }
    }

    public int getIdNumber() { return idNumber; }


    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public int getPostalCode() { return postalCode; }

    public void setPostalCode(int postalCode) { this.postalCode = postalCode;}

    public int getPhonenumber() { return phonenumber; }

    public void setPhonenumber(int phonenumber) { this.phonenumber = phonenumber; }

    public String getInfo() { return name + ", " + idNumber; }
    public String toString()
    {
        return name;
    }

    public String printInfoMember()
    {
        String info = name + ", " + idNumber + ", " + address + ", " + postalCode + " " + district + ", " + phonenumber;
        return info;
    }
}
