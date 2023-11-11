package lk.ijse.freshBite.dto;

public class ForgotPassworddto {
    private  String userName;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String pwd;

    public ForgotPassworddto(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    public ForgotPassworddto() {
    }

    public ForgotPassworddto(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ForgotPassworddto{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
