package CommunicationServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    ServerSocket serverSocket;
    List<Room> rooms;
    ExecutorService daPool;
    Socket socket;

    public Server(int port) {
        try {
          serverSocket = new ServerSocket(port);
            
            
        } catch (IOException exe7) {
            exe7.printStackTrace();
        }
        rooms = new ArrayList();
        daPool = Executors.newCachedThreadPool();
    }
@Override
    public void run() {
       
        Handler handler;
        while (true) {
            try { 
                socket = serverSocket.accept();
               handler = new Handler(socket, rooms, daPool);
            daPool.execute(handler);
                
                
            } catch (IOException exe9) {
                exe9.printStackTrace();
            }
            
        }

    }

    public static void main(String args[]) {
Server server=new Server(9099);
server.run();
    }

}
