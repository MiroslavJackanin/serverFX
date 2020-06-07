package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class Controller {
    public TextField fieldUserLogin;
    public PasswordField fieldUserPass;
    public Button btnLogIn;
    public Label errMessage;
    public Button signUpBtn;

    public String userLogin;
    public String userPass;

    public void initialize() {}

    public void logIn() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8081/login")
                .header("Content-Type", "application/json")
                .body("{\n\t\"login\":\""+userLogin+"\",\n\t\"password\":\""+userPass+"\"\n}")
                .asString();

        JSONObject object;
        if (response.getStatus() == 201){
            object = new JSONObject(response.getBody());
            openMainWindow(object);
            closeLoginWindow();
        }else {
            object = new JSONObject(response.getBody());
            errMessage.setText(response.getStatus() +" "+ object.getString("error"));
        }
    }

    public void setLogin(){
        userLogin = fieldUserLogin.getText();
    }
    public void setPass(){
        userPass = fieldUserPass.getText();
    }

    public void openMainWindow(JSONObject object) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-window.fxml"));
            Parent root1 = loader.load();

            MainWindow mainWindow = loader.getController();
            mainWindow.setToken(object.getString("token"));
            mainWindow.setLogin(object.getString("login"));
            mainWindow.setUserName(object.getString("firstName"), object.getString("lastName"));
            mainWindow.getUsers(object.getString("token"));
            mainWindow.setPass(userPass);

            Stage stage = new Stage();
            Scene scene = new Scene(root1, 950, 680);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.setTitle("Chat App");
            stage.show();
        }
        catch (IOException | UnirestException e) {
            e.printStackTrace();
        }
    }

    public void closeLoginWindow() {
        Stage stage = (Stage) btnLogIn.getScene().getWindow();
        stage.close();
    }

    public void signUp(){
        closeLoginWindow();
        openSignUpWindow();
    }

    private void openSignUpWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-up.fxml"));
            Parent root1 = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root1, 390, 280);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.setTitle("Sign Up");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMsg(String message) {
        errMessage.setText(message);
    }
}
