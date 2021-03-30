package models;

public class LoginData {
    private String email;
    private String password;
    private String texthome;

    public String getTexthome() {return texthome; }
    public void setTexthome(String texthome) {this.texthome = texthome; }
    public String getEmail() {  return email; }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


}
