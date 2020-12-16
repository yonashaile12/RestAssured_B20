package Pojo;
//A POJO (Plain Old java Object) class
// is used to create object that represent data
// for example :
// This is an Java object that contains 3 values for 3 fields
//Spartan sp1 = new Spartan("B20 user","Male",1234567890L) ;
//
// A POJO Class must have
// encapsulated fields
// public no arg constructor
// everything else is optional
public class Spartan {

    private String name;
    private String gender;
    private long phone;

    public Spartan(){

    }

    public Spartan(String name, String gender, long phone) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public long getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }


}
