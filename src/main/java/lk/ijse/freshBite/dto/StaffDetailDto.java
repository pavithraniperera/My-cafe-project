package lk.ijse.freshBite.dto;

public class StaffDetailDto {
    private String EmpId  ;
    private String name;
    private String address;
    private String Nic;
    private String telephone;
    private String email;
    private String jobRole;
    private double chargePerHour;
    private String qualification;
    private String barCode;

    public StaffDetailDto(String emp_id, String name, String address, String nic, String telephone, String email, String jobRll, double chargePerHour, String qualification) {

        EmpId = emp_id;
        this.name = name;
        this.address = address;
        this. Nic = nic;
        this.telephone = telephone;
        this.email = email;
        this.jobRole = jobRll;
        this.chargePerHour = chargePerHour;
        this.qualification = qualification;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StaffDetailDto(String empId, String name, String address, String nic, String telephone, String email, String jobRole, double chargePerHour, String qualification,String barCode) {
        EmpId = empId;
        this.name = name;
        this.address = address;
       this. Nic = nic;
        this.telephone = telephone;
        this.email = email;
        this.jobRole = jobRole;
        this.chargePerHour = chargePerHour;
        this.qualification = qualification;
        this.barCode = barCode;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
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

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public double getChargePerHour() {
        return chargePerHour;
    }

    public void setChargePerHour(double chargePerHour) {
        this.chargePerHour = chargePerHour;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    @Override
    public String toString() {
        return "StaffDetailDto{" +
                "EmpId='" + EmpId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", Nic='" + Nic + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", jobRole='" + jobRole + '\'' +
                ", chargePerHour=" + chargePerHour +
                ", qualification='" + qualification + '\'' +
                '}';
    }


    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
