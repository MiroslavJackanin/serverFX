package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.json.JSONArray;

public class LogWindow {
    public ListView<String> logList;
    
    public String token;
    public Button logins;
    public Button logouts;

    public void setToken(String token){
        this.token = token;
    }

    public void getLogs() throws UnirestException {
        logList.getItems().clear();
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8081/log")
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body("{\n\t\"type\": \"login\"\n}")
                .asString();

        JSONArray array = new JSONArray(response.getBody());
        for (int i=0; i<array.length(); i++){
            logList.getItems().addAll("datetime: "+array.getJSONObject(i).getString("datetime")+", "+"type: "+array.getJSONObject(i).getString("type"));
        }
    }
    
    public void getLogInLogs() throws UnirestException {
        getLogs();
    }
    
    public void getLogOutLogs() throws UnirestException {
        logList.getItems().clear();
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8081/log")
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .body("{\n\t\"type\": \"logout\"\n}")
                .asString();

        JSONArray array = new JSONArray(response.getBody());
        for (int i=0; i<array.length(); i++){
            logList.getItems().addAll("datetime: "+array.getJSONObject(i).getString("datetime")+", "+"type: "+array.getJSONObject(i).getString("type"));
        }
    }
}
