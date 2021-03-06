/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsystem;

import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class EditSensorController implements Initializable {

    @FXML
    private VBox addNewBox;
    @FXML
    private VBox addNewVbox;
    @FXML
    private TextField sName;
    @FXML
    private TextField sFloor;
    @FXML
    private TextField sRoom;
    @FXML
    private Button addSensorBtn;
    
    private int editId;
    
    private SensorService sensorService = null;

    @FXML
    private RadioButton Active;
    @FXML
    private RadioButton Inactive;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    ToggleGroup mainGroup;
    @FXML
    private ToggleGroup group1;
    
    
    
    
    public void transferData(int id, String ob1, String ob2, String ob3, String ob4) {
        /*
        this method use to get sensor datails from sensor details contoller 
        */
        this.editId = id;
        sName.setText(ob1);
        sFloor.setText(ob2);
        sRoom.setText(ob3);
        
        if(ob4.equals("Active"))
        {
            Active.setSelected(true);
        }
        else{
            Inactive.setSelected(true);
        }
            
    }
    
    
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
            
            
            mainGroup = new ToggleGroup();
            Active.setToggleGroup(mainGroup);
            Inactive.setToggleGroup(mainGroup);
 
        } catch (Exception e) {
            
            System.err.println("error "+e);
        } 
        
            
    }    

    @FXML
    private void editSensor(MouseEvent event) {
          // check mouse is clicked
        editSernsor();
    }
     @FXML
    private void editSensorKeyPressed(KeyEvent event) {
    // check enter key is presssed
     if (event.getCode() == KeyCode.ENTER){
              editSernsor();
        }
       
    }
    
    
    
    private void editSernsor(){
          // get new values and  update sensor details
        
        String name,floor,room, status;
        int id;
        id = editId;
        
        name=sName.getText().toString();
        floor=sFloor.getText().toString();
        room=sRoom.getText().toString();
       
        if(Active.isSelected())
            status = "Active";
        
        else
            status = "Inactive";
        
        //System.out.println(status);
        
        if (name.equals("") || floor.equals("") || room.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warining");
               alert.setHeaderText("All fields are mandatory!!!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                 });
                
                       
        }else{
              
             try {
                 // send new values to rmi server 
                String newMess= sensorService.editSensor(id, name, floor, room, status); 
                if (newMess.startsWith("Edited")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successfull");
                    alert.setHeaderText("Sensor: "+name+" Edited successfully");
                    alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                       // System.out.println("Pressed OK.");
                        }
                    }); 
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(newMess);
               
                    alert.showAndWait().ifPresent(rs -> {
                        if (rs == ButtonType.OK) {
                           // System.out.println("Pressed OK.");
                        }
                    });    
                }
                  
          

            Stage stage=(Stage)addSensorBtn.getScene().getWindow();
            stage.close();
        
         
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(e.toString());
               
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                 }); 
                
            }
           
             
             
        } 
    }

   
    
}
