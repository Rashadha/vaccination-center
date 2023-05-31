public class Patient
{
    private String firstName;
    private String surName;
    private int age;
    private String city;
    private String NICNumber;
    private String passportNumber;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNICNumber(String NICNumber) {
        this.NICNumber = NICNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getNICNumber() {
        return NICNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

}
