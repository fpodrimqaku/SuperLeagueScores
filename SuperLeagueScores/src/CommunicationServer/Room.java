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

    public void Room(String roomName){
    this.roomName=roomName;
    }
    
    
    private String roomName;
    private List<Handler> occupants;
    private Queue<Message> messageBuffer1;
    private Queue<Message> messageBuffer2;
    private Queue<Message> messageBuffer3;
    private Queue<Message> messageBuffer4;

    public boolean isBufferEmpty(int i) {
        i = i % 5;

        if (i == 1) {
            return messageBuffer1.isEmpty();
        } else if (i == 2) {
            return messageBuffer2.isEmpty();
        } else if (i == 3) {
            return messageBuffer3.isEmpty();
        }
        return messageBuffer4.isEmpty();

    }

    public Queue<Message> getBuffer(int i) {
        i = i % 5;
        if (i == 1) {
            return messageBuffer1;
        } else if (i == 2) {
            return messageBuffer2;
        } else if (i == 3) {
            return messageBuffer3;
        }
        return messageBuffer4;

    }

    public int getAvailId() {
        return 0;//--dont orget it
    }

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
    
}
