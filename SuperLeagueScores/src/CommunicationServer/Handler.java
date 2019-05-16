package CommunicationServer;

import message.Message;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.io.*;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import MyUtils.Converter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import message.MessageFactory;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.*;

public class Handler implements Runnable {

    
    
    
    private String userName;

    public Handler(Socket socket, List<Room> roomList, ExecutorService exeservice) {
        this.socket = socket;
        exeService = exeservice;
        this.roomList = roomList;
        this.myBuffer =new LinkedBlockingQueue<>();
               
        try {
            DGS = new DatagramSocket();

        } catch (Exception exe6) {
            exe6.printStackTrace();
        }//!! whatch out boi ->exe6 is pretty critical
    }
     List<Room> roomList;
    Socket socket;
    Room room;
    
    int ID;

    public DatagramSocket DGS;
    public InetSocketAddress DGSA;
boolean terminateFlag=false;
    BlockingQueue<Message> myBuffer;//--dont forget to initialize buffer when id is assigned

    ExecutorService exeService;
    ObjectInputStream oins;
    ObjectOutputStream oouts;
    InputStream ins;
    OutputStream outs;
//boolean busy=false;
static int count=0;  
    public static Lock lock=new ReentrantLock(); 
    @Override
    public void run() {
        Message message = null;
        try {
            ins = socket.getInputStream();
            outs = socket.getOutputStream();
            oins = new ObjectInputStream(ins);
            oouts = new ObjectOutputStream(outs);

          
        } catch (Exception exe1) {exe1.printStackTrace();
        }

        while (!terminateFlag) {
            try {
                if(ins.available()>0)
                {message = (Message) oins.readObject();
                handle(message);
                }
                handleBuffer();
                
            } catch (Exception exe4) {
                exe4.printStackTrace();
            }
            
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
            case 3:
                this.ConnectionTermination_Handler(message);
                break;
        }

    }

    public void assignARoom_Handler(Message message) {
        /*if(busy){
        while(busy){try{Thread.sleep(100);}catch(Exception exe9){}}
        
        }
        
        busy=true;*/
        
        lock.lock();
        
        count++;
        String roomName = message.getRoomName();

        List<Integer> otherIDS = new ArrayList();
        List<InetSocketAddress> otherUDps = new ArrayList();

        if (!((room = findRoom(roomName)) == null)) {
            if (room.isFull()) {
                writeMessageOut(MessageFactory.createType8Message());
                /*try{
                socket.close();}catch(Exception nm){nm.printStackTrace();}*/
                System.out.println("room full");
            } else {System.out.println("entered existing room");

                for (Handler x : room.getOccupants()) {
                    otherIDS.add(x.ID);
                    otherUDps.add(x.DGSA);

                }
                
                System.out.println(count+"                              "+room.getOccupants());
                room.enterRoom(this); //id is assigned by room

                writeMessageOut(MessageFactory.createType7Message(ID, otherIDS, otherUDps));
                room.broadcastMessage(MessageFactory.createType5Message(ID, DGSA));
                
              
                

            }
        } else { System.out.println("creating new room");
            room = new Room(roomName);
            roomList.add(room);
            Message message1=MessageFactory.createType7Message(ID, otherIDS, otherUDps);
            room.enterRoom(this);
            writeMessageOut(message1);
           
        }
        
         System.out.println("rooms number "+roomList.size());
         lock.unlock();
    }

    private void chatMessage_Handler(Message message) {
        broadacastMessage(message);
    }

    
    private void ConnectionTermination_Handler(Message message){
    broadacastMessage(message);
    try{
    socket.close();
    DGS.close();
    terminateFlag=true;
    }catch(Exception exe8){exe8.printStackTrace();}
    }
    
    
    
    public void handleBuffer() {
        if (!myBuffer.isEmpty()) {
            exeService.submit(this::handleBufferMETHOD);
        }

    }

    public void handleBufferMETHOD() {

        while (!myBuffer.isEmpty()) {
            try {Object o=myBuffer.poll();
           
                oouts.writeObject(o);

            } catch (Exception exe3) {
                exe3.printStackTrace();
         
        }

try{Thread.sleep(100);}catch(Exception exe10){exe10.printStackTrace();}
}
    }

    public Room findRoom(String roomName) {Room sob;
    if(roomList.size()==0)
    return null;
        for (int x=0;x<roomList.size();x++) {
            sob=roomList.get(x);
            if (sob.getRoomName().equals(roomName)) {
               
                return roomList.get(x);
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

    
    
    public static void main(String args[]){
    List <Room>rl=new ArrayList();
    Handler h1=new Handler(null,rl,null);
     Handler h2=new Handler(null,rl,null);
     Handler h3=new Handler(null,rl,null);
     Handler h4=new Handler(null,rl,null);
     Handler h5=new Handler(null,rl,null);
    Message mess=MessageFactory.createType0Message("ro2", null);
     Message mess2=MessageFactory.createType0Message("roo3", null);
    Room r1=new Room("r1");
    Room r2=new Room("ro2");
    Room r3=new Room("roo3");
    Room r4=new Room("room4");
    
    //h.roomList.add(r1);
    
    h1.assignARoom_Handler(mess);
    /*h2.assignARoom_Handler(mess);
    h3.assignARoom_Handler(mess);
    h4.assignARoom_Handler(mess);
    h5.assignARoom_Handler(mess);
    
    h1.findRoom("ro2").getOccupants().remove(h3);
     h1.findRoom("ro2").getOccupants().remove(h1);
    System.out.println(h1.findRoom("ro2").getOccupants().size());
    h5.assignARoom_Handler(mess);
    h3.assignARoom_Handler(mess);
     h1.assignARoom_Handler(mess);
    new Handler(null,rl,null).assignARoom_Handler(mess2);
    */
   //for (Handler x:h1.findRoom("ro2").getOccupants())
     //  System.out.println(x.ID);
    
     System.out.println(h1.findRoom("ro2"));
    }
    
    
    
}
