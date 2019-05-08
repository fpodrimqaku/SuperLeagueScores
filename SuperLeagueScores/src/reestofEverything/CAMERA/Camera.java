
package reestofEverything.CAMERA;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.opencv.imgcodecs.Imgcodecs;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Camera  {

WritableImage WritableImage = null;
{
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
}
public VideoCapture capture = new VideoCapture();
      Mat matrix = new Mat();
      BufferedImage image ;
       WritableRaster raster;
       DataBufferByte dataBuffer;
       byte[] data;
   
       
       FileOutputStream fous;
         //compression variables
       
ByteArrayOutputStream compressed = null;
ImageOutputStream outputStream =null;
       ImageWriter jpgWriter;
ImageWriteParam jpgWriteParam; 
     byte[] jpegData;
   
   
   
   
   
    public void startCapturing(){
   capture.open(0);
   
   }
   public void stopCapturing(){
   capture.release();
   }
   
   
   
    public BufferedImage getCapuredShotArray() {
      
        try{fous=new FileOutputStream("C:\\users\\guesst\\desktop\\pinu.jpg");
        }catch(Exception m){m.printStackTrace();}
             
      capture.read(matrix);

      // If camera is opened
      if( capture.isOpened()) {
         // If there is next video frame
     if (capture.read(matrix)) {
            // Creating BuffredImage from the matrix
            
    // matrix=matrix.submat(matrix.height()/4, matrix.height()*3/4, matrix.width()/4, matrix.width()*3/4);
           image = new BufferedImage(matrix.width(), 
               matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
            
           //System.out.println("1::"+image.hashCode());
           //testing
           
           
           
           
           
           
           // The important part: Create in-memory stream
 compressed = new ByteArrayOutputStream();
 outputStream =null;
try{
outputStream = ImageIO.createImageOutputStream(compressed);
}catch(Exception m){m.printStackTrace();}
// NOTE: The rest of the code is just a cleaned up version of your code

// Obtain writer for JPEG format
 jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

// Configure JPEG compression: 70% quality
jpgWriteParam = jpgWriter.getDefaultWriteParam();
jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
jpgWriteParam.setCompressionQuality(0.1f);

// Set your in-memory stream as the output
jpgWriter.setOutput(outputStream);

// Write image as JPEG w/configured settings to the in-memory stream
// (the IIOImage is just an aggregator object, allowing you to associate
// thumbnails and metadata to the image, it "does" nothing)
try{
jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);
}catch(Exception m){m.printStackTrace();}
// Dispose the writer to free resources
jpgWriter.dispose();

// Get data for further processing...
 jpegData = compressed.toByteArray();
      
   

 
 
          
           try{
           image= ImageIO.read(new ByteArrayInputStream(jpegData));
                   
            //       ImageIO.read(ImageIO.createImageInputStream(compressed));
          
           }catch(Exception m){m.printStackTrace();}

          
//testing
 try{
     
      //BufferedImage hehe = ImageIO.read(new ByteArrayInputStream(data));
        ImageIO.write(image, "jpg", fous);
     

 if(1==1){

 System.exit(0);
 }
 }catch(Exception m){m.printStackTrace();System.exit(0);}


           
            System.out.println("nefore "+jpegData.length);
             raster = image.getRaster();
            dataBuffer = (DataBufferByte) raster.getDataBuffer();
          jpegData = dataBuffer.getData();
            matrix.get(0, 0, jpegData);
           
              
             

            WritableImage = SwingFXUtils.toFXImage(image, null);
      
            
            
            
                        
            
}
     }
     // System.out.println("arrhash: "+jpegData.hashCode());
     // return jpegData;
   
    return null;
    }
   
   
   
   public static void main(String args[]) {
    
   }
}