package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Options {
    public TextField password;
    public Button changePswdBtn;
    public Label changeStatus;

    public String pass;
    public String oldPass;
    public String login;
    public String token;

    public void setPass(){
        pass = password.getText();
    }
    public void setOldPass(String oldPass){
        this.oldPass = oldPass;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public void changePswd() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8081/changepassword")
                .header("Authorisation", token)
                .header("Content-Type", "application/json")
                .body("{\r\n\t\"login\":\""+login+"\",\r\n\t\"oldpassword\":\""+oldPass+"\",\r\n\t\"newpassword\":\""+pass+"\"\r\n}")
                .asString();

        if (response.getStatus() == 400){
            changeStatus.setText(response.getBody());
        }else {
            changeStatus.setText("change successful");
        }
    }

    public void setToken(String token) {
        this.token = token;
    }
}
