package lk.ijse.freshBite.dto.tm;

public class StaffTm {
 private String emp_id;
 private String name;
 private String address;
 private String telephone;
 private  String email;
 private double charge;
 private String qualification;
 private String jobRole;
 private String nic;

    public StaffTm() {
    }

    public StaffTm(String emp_id, String name, String address, String telephone, String email, double charge, String qualification, String jobRole, String nic) {
        this.emp_id = emp_id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.charge = charge;
        this.qualification = qualification;
        this.jobRole = jobRole;
        this.nic = nic;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return "StaffTm{" +
                "emp_id='" + emp_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", charge='" + charge + '\'' +
                ", qualification='" + qualification + '\'' +
                ", jobRole='" + jobRole + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
