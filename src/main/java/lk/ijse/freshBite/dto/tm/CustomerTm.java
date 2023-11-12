package lk.ijse.freshBite.dto.tm;

public class CustomerTm {
    private String cust_id;
    private String name;
    private String address;
    private String email;
    private String telephone;
    private  String gender;
    private String membership;

    public CustomerTm() {
    }

    public CustomerTm(String cust_id, String name, String address, String email, String telephone, String gender, String membership) {
        this.cust_id = cust_id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.gender = gender;
        this.membership = membership;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        return "customerTm{" +
                "cust_id='" + cust_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", gender='" + gender + '\'' +
                ", membership='" + membership + '\'' +
                '}';
    }
}
