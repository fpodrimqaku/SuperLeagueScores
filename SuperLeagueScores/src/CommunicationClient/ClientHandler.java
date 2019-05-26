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
import java.net.Socket;
import java.util.ArrayList;
import javafxapplication7.FXMLDocumentController;
import media.recSenders.MicrophoneRecSender;
import media.recSenders.SoundReceiver;
import message.Message;
import message.MessageFactory;

public class ClientHandler {

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
    ArrayList<InetAddress>adds=new ArrayList(3);

    public ClientHandler(FXMLDocumentController fdc, InetSocketAddress TCPisa) {
        this.fdc = fdc;
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

                    //testing
                    System.out.println("PRE---oouts initiated" + oins);

                    oouts = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("oouts initiated");

                    oins = new ObjectInputStream(socket.getInputStream());
//-- port is hard coded
                    oouts.writeObject(MessageFactory.createType0Message("myRoom", new InetSocketAddress(InetAddress.getLocalHost(),9100)));

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
        
        adds.add(message.getMyUDPadd().getAddress());
       
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
        adds.add(x.getAddress());
        
        mrc=new MicrophoneRecSender(adds,9100);
        sr=new SoundReceiver(9100);
        new Thread(mrc).start();
        new Thread(sr).start();}catch(Exception ee3){ee3.printStackTrace();System.exit(0);}
    }

    public void startRunning() {
        TCPConn.start();
    }

    public void sendMessage(Message message) {
        try {
            oouts.writeObject(message);
        } catch (Exception exe14) {
            exe14.printStackTrace();
        }
    }

    public int getMyID() {
        return MyID;
    }

    
   


}
