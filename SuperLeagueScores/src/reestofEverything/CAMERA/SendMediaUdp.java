package reestofEverything.CAMERA;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.DatagramPacket;

public class SendMediaUdp implements Runnable {

    final int PACKET_SIZE = 65000;
    boolean isAudio;//if false then it's video
    SocketAddress sAddress;
    Camera camera;
    //Microphone microphone;
    byte[] data;
    DatagramSocket dSocket;
    DatagramPacket dGPacket;

    public SendMediaUdp(Camera camera, SocketAddress sAddress) {

        this.camera = camera;
        this.sAddress = sAddress;

    }

    public static void main(String args) {

    }

    @Override
    public void run() {
        if (isAudio) {
           /* 
        
            microphone.startCapturing();
            dGPacket = new DatagramPacket(data, 0);
            while (true) {
                data = microphone.getCapuredShotArray();
                try {
                    dSocket.send(dGPacket);
                } catch (Exception mm) {}
            }
        */
        }
           //diference of audio and camera datagrams is in size
        //size is the delimiter which distinguishes auido from camera packets
         else {
            camera.startCapturing();
            dGPacket = new DatagramPacket(data, 1);
            while (true) {
                data = camera.getCapuredShotArray();
                try {
                    dSocket.send(dGPacket);
                } catch (Exception mm) {
                }
            }

        }

    }

}
