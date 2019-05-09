/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationServer;

import java.util.List;
import java.util.Queue;

/**
 *
 * @author Guesst
 */
public class Room {

    public Room(String roomName){
    this.roomName=roomName;
    }
    
    
    private String roomName;
    private List<Handler> occupants;
   
   

    public String getRoomName() {
        return roomName;
    }

   
    public boolean isFull() {
        if (occupants.size() >= 4) {//!!watch out boi size cant exceed 4
            return true;
        }
        return false;
    }
    
    
    public void enterRoom(Handler handler){
        if(!isFull())
    occupants.add(handler);
    }
    
    public void broadcastMessage(Message message){
    
        for (Handler x:occupants )
            x.getBuffer().offer(message);
        
    
    }
    
    
    
}
