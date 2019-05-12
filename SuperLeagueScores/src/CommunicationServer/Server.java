package CommunicationServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    ServerSocket serverSocket;
    List<Room> rooms;
    ExecutorService daPool;
    Socket socket;

    public Server(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
        } catch (IOException exe7) {
            exe7.printStackTrace();
        }
        rooms = new ArrayList();
        daPool = Executors.newCachedThreadPool();
    }

    public void run() {
        Handler handler;
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException exe9) {
                exe9.printStackTrace();
            }
            handler = new Handler(socket, rooms, daPool);
            daPool.execute(handler);
        }

    }

    public static void main(String args[]) {
Server server=new Server(9090);
server.run();
    }

}
