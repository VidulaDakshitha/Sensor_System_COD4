package sensorsystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface SensorService extends Remote{
    public String addSensor(String name, String floor, String room, double level) throws  RemoteException;
    public String getSernsors() throws Exception;
    public String editSensor(int id, String name, String floor, String room, String status) throws Exception;
    public String login(String password) throws Exception;
    
//    public String updateSernsorLevel(int id,double lavel) throws Exception;
}
