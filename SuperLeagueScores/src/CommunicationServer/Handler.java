package CommunicationServer;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.io.*;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import MyUtils.Converter;
import java.util.ArrayList;
import java.util.LinkedList;

public class Handler implements Runnable {
      private String userName;

    public Handler(Socket socket, List<Room> roomList, ExecutorService exeservice) {
        this.socket = socket;
        exeService = exeservice;
        this.roomList = roomList;
        this.myBuffer = new LinkedList();
        
    }
    List<Room> roomList;
    Socket socket;
    Room room;
    List<Message> m;
    int ID=(int)(Math.random());
    Queue<Message> myBuffer;//--dont forget to initialize buffer when id is assigned
    
    ExecutorService exeService;
    ObjectInputStream oins;
    ObjectOutputStream oouts;
    InputStream ins;
    OutputStream outs;
    
    @Override
    public void run() {
        Message message = null;
        try {
            ins = socket.getInputStream();
            outs = socket.getOutputStream();
            oins = new ObjectInputStream(ins);
            oouts = new ObjectOutputStream(outs);
            
        } catch (Exception exe1) {
        }
        
        while (true) {
            try {
                message = (Message) oins.readObject();
            } catch (Exception exe4) {
                exe4.printStackTrace();
            }
            handle(message);
        }
        
    }
    
    public void handle(Message message) {
        int type;
        type = message.getType();
        
        switch (type) {
            case 0:
                this.assignARoom_Handler(message);
                break;
            case 1:
                this.chatMessage_Handler(message);
                break;
        }
        
    }
    
    public void assignARoom_Handler(Message message) {
        String roomName = new String(Converter.intArrtoByteArr(message.getData()));
        if (!((room = findRoom(roomName)) == null)) {
            if (room.isFull()) {
                writeMessageOut(new Message(102, null));                
            } else {                
                room.enterRoom(this);
                room.broadcastMessage(new Message(5,new int[]{ID}));
            }
        } 
        else {
            room = new Room(roomName);
            room.enterRoom(this);
        }
        
    }
    
    
    
    private void chatMessage_Handler(Message message){
    broadacastMessage(message);
    }
    
    
    
    public void handleBuffer() {
        if (!myBuffer.isEmpty()) {
            exeService.submit(this::handleBufferMETHOD);
        }
        
    }
    
    public void handleBufferMETHOD() {
        
        while (!myBuffer.isEmpty()) {
            try {
                oouts.writeObject(myBuffer.poll());
                
            } catch (Exception exe3) {
                exe3.printStackTrace();
            }
        }
        
    }
    
    public Room findRoom(String roomName) {
        for (Room x : roomList) {
            if (x.getRoomName().equals(roomName)) {
                return x;
            }
        }
        return null;
    }
    
    public void writeMessageOut(Message message) {
        
        try {
            oouts.writeObject(message);
        } catch (IOException exe5) {
            exe5.printStackTrace();
        }
        
    }
    
    public Queue<Message> getBuffer() {
        return this.myBuffer;
    }
    
    private void broadacastMessage(Message message) {
        room.broadcastMessage(message);
        
    }
    
}
