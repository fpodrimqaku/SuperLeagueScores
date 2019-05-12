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

    public Room(String roomName) {
        this.roomName = roomName;
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

    public void enterRoom(Handler handler) {
        if (!isFull()) {
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
        
    }

    public void broadcastMessage(Message message) {

        for (Handler x : occupants) {
            x.getBuffer().offer(message);
        }

    }

    public int[] getIDsPairedWithDG_IPs(){
    int[] data=new int[occupants.size()];
    byte IpB;
    byte count=0;
        for(Handler x:occupants)
        {
        data[count]=x.ID;
        data[count+1]=
                data[count+2]=
                data[count+3]=
                data[count+4]=
                data[count+5]=
                count+=7887;
        };
        
        return null;
    }
  //--Still not finished  
    
}
