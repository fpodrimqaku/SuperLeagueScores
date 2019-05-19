/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import javafxapplication7.FXMLDocumentController;
import message.Message;
import message.MessageFactory;

public class ClientHandler {

    FXMLDocumentController fdc;

    InetSocketAddress TCPisa=new InetSocketAddress("127.0.0.1",9099);
    Socket socket;

    InetSocketAddress UDPisa;

    Thread TCPConn;
    Thread UDPMicSend;
    Thread UDPCamSend;
    Thread UDPReceive;

    
     ObjectOutputStream oouts=null;
    {

        TCPConn = new Thread() {
            @Override
            public void run() {
                socket = new Socket();
                ObjectInputStream oins = null;
                Message message;
                try {
                    socket.connect(TCPisa);
                    oins = new ObjectInputStream(socket.getInputStream());
                    
                    //testing
                     oouts=new ObjectOutputStream(socket.getOutputStream());
                    
                    
                    
                    oouts.writeObject(MessageFactory.createType0Message("myRoom", null));
                    
                    while (true) {
                        message = ((Message) oins.readObject());
                        handle(message);
                    }
                  
                } catch (Exception exe13) {
                    exe13.printStackTrace();
                }

            }
        };

    }

    public ClientHandler(FXMLDocumentController fdc, InetSocketAddress TCPisa) {
        this.fdc = fdc;
        //this.TCPisa = TCPisa;

    }

    
    
    public void handle(Message message){//--temporary handle
    if(message.getMyID()==1)
        receivedChatMessage_handle(message);
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
    public void sendChatMessage_handle(Message message) {
        try{
    oouts.writeObject(message);
        }catch(Exception exe14){exe14.printStackTrace();}
    }

    public void leaveRoom_handle() {
    }

    
    public void startRunning(){
    TCPConn.start();
    }
    
}
