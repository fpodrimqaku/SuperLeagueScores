/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafxapplication7.FXMLDocumentController;
import media.recSenders.MicrophoneRecSender;
import media.recSenders.SoundReceiver;
import message.Message;
import message.MessageFactory;

public class ClientHandler {
InetAddress mainAddress;
int micUDPport;
Lock lock=new ReentrantLock(true);
       
    FXMLDocumentController fdc;
    int MyID;
    InetSocketAddress TCPisa ;
    Socket socket;

  
    InetSocketAddress UDPisa;

    Thread TCPConn;
    
    
    
    
    Thread UDPMicSend;
    Thread UDPMicReceive;
    
    Thread UDPCamSend;
    Thread UDPReceive;

    ObjectOutputStream oouts = null;


    
    
    
    
    MicrophoneRecSender mrc;
    SoundReceiver sr;
    ArrayList<InetSocketAddress>adds=new ArrayList(3);

    public ClientHandler(FXMLDocumentController fdc, InetSocketAddress TCPisa) {
        this.fdc = fdc;
        micUDPport=getFreePort();
        try {
           
           this.TCPisa = TCPisa;

        } catch (Exception exe14) {
            exe14.printStackTrace();
        }
        initializeConnectionThreads();


    }

    public void initializeConnectionThreads() {
        TCPConn = new Thread() {
            @Override
            public void run() {
                socket = new Socket();
                ObjectInputStream oins = null;
                Message message1;
                try {
                    socket.connect(TCPisa);
mainAddress=socket.getLocalAddress();

                    //testing
                    System.out.println("PRE---oouts initiated" + oins);

                    oouts = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("oouts initiated");

                    oins = new ObjectInputStream(socket.getInputStream());
//-- port is hard coded


                    oouts.writeObject(MessageFactory.createType0Message("myRoom", new InetSocketAddress(mainAddress,micUDPport)));

                    while (true) {
                        message1 = ((Message) oins.readObject());
                        System.out.println(message1);
                        handle(message1);
                    }

                } catch (Exception exe13) {
                    exe13.printStackTrace();
                }

            }
        };

        
        
        
        
       
    }

    public void handle(Message message) {//--temporary handle
        switch (message.getType()) {
            case 1:
                receivedChatMessage_handle(message);
                break;
            case 3:
                occupantDisconnected_handle(message);
                break;
            case 5:
                newOccupant_handle(message);
                break;
            case 7:
                enteredRoom_handle(message);
                break;
        }
    }

    //Passive
    public void receivedChatMessage_handle(Message message) {
        fdc.addText(message.getMyID(), message.getChatMessage());
    }

    public void occupantDisconnected_handle(Message message) {
 adds.remove(message.getMyUDPadd().getAddress());
    }

    public void newOccupant_handle(Message message) {
        
        adds.add(message.getMyUDPadd());
       
    }

    //Active
    public void sendChatMessage_handle(String text) {
        Message message = MessageFactory.createType1Message(MyID, text);
        sendMessage(message);
    }

    public void leaveRoom_handle() {

    }

    public void enteredRoom_handle(Message message) {
        MyID = message.getMyID();
        //-- handle this part below
        if (message.getOtherUDPs().size() == 0) {
            return;
        }
        try{
        System.out.println("reached the plaece where----------    "+adds);
        for(InetSocketAddress x:message.getOtherUDPs())
        adds.add(x);
        
        mrc=new MicrophoneRecSender(adds);
        sr=new SoundReceiver(micUDPport);
        new Thread(mrc).start();
        new Thread(sr).start();}catch(Exception ee3){ee3.printStackTrace();System.exit(0);}
    }

    public void startRunning() {
        TCPConn.start();
    }

    public void sendMessage(Message message) {
        lock.lock();
        try {
            oouts.writeObject(message);
        } catch (Exception exe14) {
            exe14.printStackTrace();
        }finally{
        lock.unlock();
        }
    }

    public int getMyID() {
        return MyID;
    }

    
static private int getFreePort(){
int uu=9100;

try{
    ServerSocket trySocket=new ServerSocket(uu+=(int)(Math.random()*100));
    trySocket.close();
    return uu;
}catch(Exception mm){
uu=getFreePort();
return uu;
}


}   

public void leaveRoom(){


    
    

}



public static void main(String args[]){
System.out.println(getFreePort());



}



}
