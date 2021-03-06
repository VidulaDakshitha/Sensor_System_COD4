/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;



public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private SensorService sensorService = null;
    @FXML
    private TextField password;
    @FXML
    private Button loginBTN;
         

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         /*
        this method use to connect rmi server 
        */
        System.setProperty("java.security.policy", "file:allowall.policy");
       try {
            Registry reg =LocateRegistry.getRegistry("127.0.0.1",2000);
            sensorService = (SensorService) reg.lookup("sensorServer");
        } catch (Exception e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.toString());
               
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                       // System.out.println("Pressed OK.");
                    }
                 }); 
        } 
    }
    

    private void login(){
          String pass;
        
        pass=password.getText().toString();// get password box value (password) 
        if (pass.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Enter password");
                alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                   // System.out.println("Pressed OK.");
                }
                }); 
        }else{
                try{
                 String logMsg = sensorService.login(pass);// send password to rmi server
                 //System.out.println(logMsg);
                 if(logMsg.startsWith("Logged")){ 
                     // if password is correct show main window
                     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                     Parent root = (Parent) fxmlLoader.load();
                     Stage stage = new Stage();
                     stage.setTitle("Sensor System");
                     stage.getIcons().add(new Image(this.getClass().getResourceAsStream("logoNew-removebg-preview.png")));
                     stage.setScene(new Scene(root));  
                     stage.show();
                     Stage stageClose=(Stage)loginBTN.getScene().getWindow();
                     stageClose.close();
                 }else if(logMsg.startsWith("Wrong")){
                     // if password is wrong show error alert
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("Unauthorized");
                     alert.setHeaderText("Wrong password");
                     alert.showAndWait().ifPresent(rs -> {
                     if (rs == ButtonType.OK) {
                        // System.out.println("Pressed OK.");
                     }
                     }); 
                 }else{
                      // if rmi server return exception Show error message
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("Error");
                     alert.setHeaderText("Oopzz!! Something went wrong!!");
                     alert.showAndWait().ifPresent(rs -> {
                     if (rs == ButtonType.OK) {
                       //  System.out.println("Pressed OK.");
                     }
                     }); 
                 }
             }catch(Exception e){
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("Error");
                     alert.setHeaderText(e.toString());
                     alert.showAndWait().ifPresent(rs -> {
                     if (rs == ButtonType.OK) {
                       //  System.out.println("Pressed OK.");
                     }
                     }); 
             } 
        }
        
    }
    
    
    @FXML
    private void loginMethod(MouseEvent event){
          login();
      
    }

    @FXML
    private void keyPressedLogin(KeyEvent event) {
       
         if (event.getCode() == KeyCode.ENTER){
             login();
        }
    }
        
    
}
