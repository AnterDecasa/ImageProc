
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anter_000
 */
public class ObjectDetection {
    
    public static int countObjects(BufferedImage image){
        
        int cnt = 0;
        int width = image.getWidth();
        int height = image.getHeight();
        int i,j;
        int bgColor = image.getRGB(0,0);
        int currX, currY, startX, startY;
        
        for(i = 0; i < height; i++){
            for(j = 0; j < width; j++){
                if(image.getRGB(j, i) != bgColor){
                    write("Enter if of (" + j+ "," + i +")\n");
                    currX = j;
                    currY = i;
                    startX = currX;
                    startY = currY;
                    do{
                        if(currY > 0 && image.getRGB(currX, currY-1) != bgColor && currX != startX && currY != startY){
                                currY = currY-1;
                        }
                        else if(currY > 0 && currX < width-1 && image.getRGB(currX+1,currY-1) != bgColor){
                            currX = currX+1;
                            currY = currY-1;
                        }
                        else if(currX < width-1 && image.getRGB(currX+1,currY) != bgColor){
                            currX = currX+1;
                        }
                        else if(currY < height-1 && currX < width-1 && image.getRGB(currX+1, currY+1) != bgColor){
                            currX = currX+1;
                            currY = currY+1;
                        }
                        else if(currY < height-1 && image.getRGB(currX, currY+1) != bgColor){
                            currY = currY+1;
                        }
                        else if(currY < height-1 && currX > 0 && image.getRGB(currX-1, currY+1) != bgColor){
                            currX = currX-1;
                            currY = currY+1;
                        }
                        else if(currX > 0 && image.getRGB(currX-1, currY) != bgColor){
                            currX = currX-1;
                        }
                        else if(currY > 0 && currX > 0 && image.getRGB(currX-1,currY-1) != bgColor){
                            currX = currX-1;
                            currY = currY-1;
                        }
                        else{
                            break;
                        }
                        if(currX != startX || currY != startY){
                            image.setRGB(currX, currY, bgColor);
                        }
                        write(currX + "," + currY + "\n");
                    }while(currX != startX || currY != startY);
                    image.setRGB(startX, startY, bgColor);
                    
                    if(currX == startX && currY == startY){
                        cnt++;
                        write("Object(s) found: " + cnt + "\n");
                    }
               }
           } 
        }
        
        return cnt;
        
    }
    
    private static BufferedImage readImage(String filename){
        BufferedImage image = null;
        try{
            image = ImageIO.read(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        }
        catch(IOException e){
            write("No image read\n");
        }
        if(image != null){
            write("Image read\n");
        }
        return image;
    }
    
    private static void write(String string){
        
        System.out.print(string);
     
    }
    
}
