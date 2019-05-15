/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationServer;

import java.util.ArrayList;
import message.Message;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Guesst
 */
 public  class Room {

    public Room(String roomName) {
        this.roomName = roomName;
        occupants=new ArrayList();
    }

    private String roomName;
    private List<Handler> occupants;

    public String getRoomName() {
        return roomName;
    }

    public boolean isFull() {
       
        if (occupants.size() >= 4) {//!!watch out boi size cant exceed 4
            System.out.println("room 1");
            return true;
        }
            System.out.println("room 0");
        return false;
        
    }

  synchronized  public void enterRoom(Handler handler) {
        
            IDLabel:
            for (int id = 0; id <= 3; id++) {
                for (Handler x : occupants) {
                    if (x.ID == id) {
                        continue IDLabel;
                    }

                }
                handler.ID = id;
                break IDLabel;//!! how do i know if this code is gonna work
            }
            occupants.add(handler);
             
        
        
    }

    public void broadcastMessage(Message message) {

        for (Handler x : occupants) {
            x.getBuffer().offer(message);
        }

    }

   

    public List<Handler> getOccupants() {
        return occupants;
    }

   
    
    
    
}
