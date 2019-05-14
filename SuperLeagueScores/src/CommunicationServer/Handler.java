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
import message.MessageFactory;

public class Handler implements Runnable {

    private String userName;

    public Handler(Socket socket, List<Room> roomList, ExecutorService exeservice) {
        this.socket = socket;
        exeService = exeservice;
        this.roomList = roomList;
        this.myBuffer = new LinkedList();
        try {
            DGS = new DatagramSocket();

        } catch (Exception exe6) {
            exe6.printStackTrace();
        }//!! whatch out boi ->exe6 is pretty critical
    }
    List<Room> roomList;
    Socket socket;
    Room room;
    List<Message> m;
    int ID;

    public DatagramSocket DGS;
    public InetSocketAddress DGSA;
boolean terminateFlag=false;
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

        while (!terminateFlag) {
            try {
                message = (Message) oins.readObject();
                handle(message);
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

    public void assignARoom_Handler(Message message) {//--
        String roomName = message.getRoomName();//--watch out

        List<Integer> otherIDS = new ArrayList();
        List<InetSocketAddress> otherUDps = new ArrayList();

        if (!((room = findRoom(roomName)) == null)) {
            if (room.isFull()) {
                writeMessageOut(MessageFactory.createType8Message());
            } else {
                for (Handler x : room.getOccupants()) {
                    otherIDS.add(x.ID);
                    otherUDps.add(x.DGSA);

                }
                room.enterRoom(this); //id is assigned by room

                writeMessageOut(MessageFactory.createType7Message(ID, otherIDS, otherUDps));
                room.broadcastMessage(MessageFactory.createType5Message(ID, DGSA));

            }
        } else { System.out.println("reached here");
            room = new Room(roomName);
            Message message1=MessageFactory.createType7Message(ID, otherIDS, otherUDps);
            room.enterRoom(this);
            writeMessageOut(message1);
        }
          
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
