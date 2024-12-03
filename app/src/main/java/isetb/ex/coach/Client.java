package isetb.ex.coach;

public class Client {
    private int id;
    private String name, lastName, cin, country;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public Client(String name, String lastName, int age, String cin, String country) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.cin = cin;
        this.country = country;
    }

    public Client() {

    }

    public Client(String name, String lastName, int id, String cin, String country, int age) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.cin = cin;
        this.country = country;
        this.age = age;
    }
}
