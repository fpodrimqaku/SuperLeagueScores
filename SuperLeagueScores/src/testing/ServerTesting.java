package testing;

import CommunicationServer.Server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import message.Message;
import message.MessageFactory;

public class ServerTesting {

    public static void main(String args[]) {
        Server s = new Server(9099);
        Thread sth = new Thread(s);
        sth.start();

        Thread th16 = new Thread(() -> {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("127.0.0.1", 9099));
                Message mes = MessageFactory.createType0Message("myroom", new InetSocketAddress("1.2.1.2", 9087));
                ObjectOutputStream oouts = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream oins = new ObjectInputStream(sock.getInputStream());

                oouts.writeObject(mes);
                System.out.println("object sent init");
                System.out.println(((Message) oins.readObject()).getMyID());
                while (true) {

                }
            } catch (Exception m) {
                m.printStackTrace();
            }
        });

        the th1 = new the(), th2 = new the(), th3 = new the(), th4 = new the(), th5 = new the(), th6 = new the(), th7 = new the();

        th1.start();
        th2.start();
        th3.start();
        th4.start();
        th5.start();
        th6.start();
        th7.start();

    }

    static class the extends Thread {
public static Lock lock=new ReentrantLock(); 
        static public boolean send = true;
public static int i=0;
        @Override
        public void run() {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("127.0.0.1", 9099));
                Message mes = MessageFactory.createType0Message("myroom", new InetSocketAddress("1.2.1.2", 9099));
                ObjectOutputStream oouts = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream oins = new ObjectInputStream(sock.getInputStream());

                oouts.writeObject(mes);
                Message fi = (Message) oins.readObject();

                if (fi.getType() == 8) {
                   
                    return;
                }
                Message mess3 = MessageFactory.createType1Message(fi.getMyID(), "i love coffee");
               
                oouts.writeObject(mess3);
                
               
                while (true) {
                        Message mfin = ((Message) oins.readObject());
                        if (mfin.getType() == 1) {
                            System.out.println("from:"+mfin.getMyID()+" to:"+fi.getMyID() + " --> " + mfin.getChatMessage());
                        } else {
                            System.out.println("***from:"+mfin.getMyID()+" to:"+fi.getMyID() + " --> " +mfin.getType());
                        }
                   
                }
            } catch (Exception m) {
                m.printStackTrace();
            }
        }

    }
}
