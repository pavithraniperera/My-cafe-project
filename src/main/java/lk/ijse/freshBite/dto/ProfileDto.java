package lk.ijse.freshBite.dto;

public class ProfileDto {
    private String userId ;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;

    public ProfileDto() {
    }

    public ProfileDto(String userId, String userName, String password, String email, String phone,String address) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public ProfileDto(String userName, String pw, String mail, String phoneNo, String address) {
        this.userName = userName;
        this.password = pw;
        this.email = mail;
        this.phone = phoneNo;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "ProfileDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
