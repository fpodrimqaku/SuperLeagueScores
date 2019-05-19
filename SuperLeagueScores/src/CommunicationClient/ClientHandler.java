/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import javafxapplication7.FXMLDocumentController;
import message.Message;
import message.MessageFactory;

public class ClientHandler {

    FXMLDocumentController fdc;
    int MyID;
    InetSocketAddress TCPisa = new InetSocketAddress("127.0.0.1", 9099);
MulticastSocket multicastSocket;
    Socket socket;

    InetSocketAddress UDPisa;

    Thread TCPConn;
    Thread UDPMicSend;
    Thread UDPCamSend;
    Thread UDPReceive;

    ObjectOutputStream oouts = null;

    public ClientHandler(FXMLDocumentController fdc, InetSocketAddress TCPisa) {
        this.fdc = fdc;
        try{
        multicastSocket=new MulticastSocket(9100);
        //this.TCPisa = TCPisa;
        }catch(Exception exe14){exe14.printStackTrace();}
        TCPConn = new Thread() {
            @Override
            public void run() {
                socket = new Socket();
                ObjectInputStream oins = null;
                Message message1;
                try {
                    socket.connect(new InetSocketAddress("127.0.0.1", 9099));

                    //testing
                    System.out.println("PRE---oouts initiated" + oins);

                    oouts = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("oouts initiated");

                    oins = new ObjectInputStream(socket.getInputStream());

                    oouts.writeObject(MessageFactory.createType0Message("myRoom", null));

                    while (true) {
                        message1 = ((Message) oins.readObject());
                        System.out.println(message1.getChatMessage());
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

    }

    public void newOccupant_handle(Message message) {
    }

    //Active
    public void sendChatMessage_handle(String text) {
        Message message=MessageFactory.createType1Message(MyID,text );
       sendMessage(message);
    }

    public void leaveRoom_handle() {
        
        
    }
    
    
    
    
    public void enteredRoom_handle(Message message){
    MyID=message.getMyID();
    //-- handle this part below
    if(message.getOtherUDPs().size()==0)
        return;
    //for(InetSocketAddress x:message.getOtherUDPs())
    //System.out.println("other occupants ------> "+x.getAddress()); 
    }
   
    

    public void startRunning() {
        TCPConn.start();
    }

    public void sendMessage(Message message){
     try {
            oouts.writeObject(message);
        } catch (Exception exe14) {
            exe14.printStackTrace();
        }
    }
    
    
}
