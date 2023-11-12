package lk.ijse.freshBite.dto;

public class CustomerDetailDto {
 private  String CustId;
 private String name ;
 private String address;
 private String telephone;
 private String email;
 private String gender;
 private String membership;

 public CustomerDetailDto() {
 }

 public CustomerDetailDto(String custId, String name, String address, String telephone, String email, String gender, String membership) {
  CustId = custId;
  this.name = name;
  this.address = address;
  this.telephone = telephone;
  this.email = email;
  this.gender = gender;
  this.membership = membership;
 }

 public String getCustId() {
  return CustId;
 }

 public void setCustId(String custId) {
  CustId = custId;
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
  return "CustomerDetailDto{" +
          "CustId='" + CustId + '\'' +
          ", name='" + name + '\'' +
          ", address='" + address + '\'' +
          ", telephone='" + telephone + '\'' +
          ", email='" + email + '\'' +
          ", gender='" + gender + '\'' +
          ", membership='" + membership + '\'' +
          '}';
 }
}
