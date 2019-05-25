/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.recSenders;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Guesst
 */
public class MicrophoneRecSender implements Runnable {

   // MulticastSocket multicastSocket;

ArrayList <InetAddress> adds=new ArrayList();
    
    public MicrophoneRecSender(MulticastSocket multicastSocket) {
        try {
           // multicastSocket = new MulticastSocket(9099);
            //--
            //multicastSocket.joinGroup(InetAddress.getByName("127.0.0.1"));
            this.add(new InetSocketAddress("127.0.0.1",9099));
        } catch (Exception m) {
            m.printStackTrace();
        }

    }

    public void add(InetSocketAddress iadd){
       adds.add(iadd.getAddress());
    }
    
    
    public void remove(InetSocketAddress iadd){
    
           adds.remove(iadd.getAddress());
    }
    
    
    
    
    @Override
    public void run() {
        try {

            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
            TargetDataLine microphone;
            SourceDataLine speakers;
            try {
                microphone = AudioSystem.getTargetDataLine(format);

                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int numBytesRead;
                int CHUNK_SIZE = 1024;
                byte[] data = new byte[microphone.getBufferSize() / 5];
                microphone.start();

                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
                speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                speakers.open(format);
                //speakers.start();

                // Configure the ip and port
                String hostname = "localhost";
                //int port = 5555;

                InetAddress address = InetAddress.getByName(hostname);
               
               DatagramSocket socket = new DatagramSocket(9099);
                byte[] buffer = new byte[1024];
                for (;;) {
                    numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                    //  bytesRead += numBytesRead;
                    // write the mic data to a stream for use later
                    out.write(data, 0, numBytesRead);
                    // write mic data to stream for immediate playback
                    speakers.write(data, 0, numBytesRead);

                    //DatagramPacket request = new DatagramPacket(data, numBytesRead, address, port);
                    DatagramPacket request = new DatagramPacket(data, numBytesRead,9099);
                    //socket.send(request);
//multicastSocket.send(request);
for(InetAddress x:adds){
    request.setAddress(x);
socket.send(request);}

                }

            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (Exception exe12) {
            exe12.printStackTrace();
        }

    }
    
    
    
    
    
    public static void main(String  args[]){
    
    new Thread(new MicrophoneRecSender(null)).start();
    
    
    
    }
}

