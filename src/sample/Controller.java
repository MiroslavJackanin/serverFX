package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {

    public TextField fieldUserLogin;
    public PasswordField fieldUserPass;
    public Button btnLogIn;
    public Label errMessage;

    public String userLogin;
    public String userPass;

    public void logIn() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8081/login")
                .header("Content-Type", "application/json")
                .body("{\n\t\"login\":\""+userLogin+"\",\n\t\"password\":\""+userPass+"\"\n}")
                .asString();
        System.out.println(response.getBody());
    }

    public void setLogin(){
        userLogin = fieldUserLogin.getText();
    }
    public void setPass(){
        userPass = fieldUserPass.getText();
    }
}
