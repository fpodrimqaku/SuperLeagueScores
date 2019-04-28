
package reestofEverything.CAMERA;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Camera  {

WritableImage WritableImage = null;
{
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
}
VideoCapture capture = new VideoCapture();
      Mat matrix = new Mat();
      BufferedImage image ;
       WritableRaster raster;
       DataBufferByte dataBuffer;
       byte[] data;
   
   
   
   
   
   
   
    public void startCapturing(){
   capture.open(0);
   
   }
   
   
   
   
    public byte[] getCapuredShotArray() {
      
      
      // Loading the OpenCV core library
     

      // Instantiating the VideoCapture class (camera:: 0)
     

      // Reading the next video frame from the camera
        
      capture.read(matrix);

      // If camera is opened
      if( capture.isOpened()) {
         // If there is next video frame
     if (capture.read(matrix)) {
            // Creating BuffredImage from the matrix
     
  
     
           image = new BufferedImage(matrix.width(), 
               matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
            
             raster = image.getRaster();
            dataBuffer = (DataBufferByte) raster.getDataBuffer();
          data = dataBuffer.getData();
            matrix.get(0, 0, data);
           //System.out.println(data.length);
            
            // Creating the Writable Image
           // WritableImage = SwingFXUtils.toFXImage(image, null);
        }
     }
      
      return data;
   }
   
   
   
   public static void main(String args[]) {
    
   }
}