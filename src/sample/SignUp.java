package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUp {
    public TextField fname;
    public TextField lname;
    public TextField login;
    public TextField pass;
    public Button signup;
    public Label errorMessage;
    public Button goBack;

    public String fstname;
    public String lstname;
    public String usrlogin;
    public String usrpass;

    public void goBack(){
        closeSignUpWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root1 = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root1, 450, 350);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.setTitle("Log In");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack(String message){
        closeSignUpWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root1 = loader.load();

            Controller controller = loader.getController();
            controller.setMsg(message);

            Stage stage = new Stage();
            Scene scene = new Scene(root1, 450, 350);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.setTitle("Log In");
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeSignUpWindow() {
        Stage stage = (Stage) goBack.getScene().getWindow();
        stage.close();
    }

    public void setFstname(){
        fstname = fname.getText();
    }

    public void setLstname(){
        lstname = lname.getText();
    }

    public void setUsrlogin(){
        usrlogin = login.getText();
    }

    public void setUsrpass(){
        usrpass = pass.getText();
    }

    public void signUp() throws UnirestException {
        if (usrpass == null || usrlogin == null || fstname == null || lstname == null){
            errorMessage.setText("All fields are mandatory");
            return;
        }
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8081/signup")
                .header("Content-Type", "application/json")
                .body("{\n\t\"firstName\":\""+fstname+"\",\n\t\"lastName\":\""+lstname+"\",\n\t\"login\":\""+usrlogin+"\",\n\t\"password\":\""+usrpass+"\"\n}")
                .asString();

        if (response.getStatus() == 400){
            errorMessage.setText(response.getBody());
        }else {
            goBack("Successfully registered");
        }
    }
}
