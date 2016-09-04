
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anter_000
 * 
 * v1.0 August 5, 2016
 */
public class Segmentation extends JPanel{
    
    public Segmentation(){
        
        this.setVisible(true);
    
    }
    
    @Override
    public void paint(Graphics g){
        BufferedImage image = Segmentation.removeColor("newAHydrangeas2.jpg", 0.25, .5, 0.1);
        BufferedImage image2 = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + "newAHydrangeas2.jpg"));
        g.drawImage(image, 0 , 0, null);
        g.drawImage(image2, 0, image.getHeight(), null);
    }
    
    /*Remove a certain range of hue by providing the lower range and upper range in decimal value*/
    public static BufferedImage removeColor(String filename, Double colorRangeLower, Double colorRangeUpper, Double saturation){
        
        float HSB[] = new float[3];
        int x, y, r, g, b;
        
        BufferedImage hydrangeas = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));  
        BufferedImage noLeaves = new BufferedImage(hydrangeas.getWidth(), hydrangeas.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for(y = 0; y < hydrangeas.getHeight(); y++){
            for (x = 0; x < hydrangeas.getWidth(); x++){
                r = (hydrangeas.getRGB(x, y) >> 16) & 0xFF;
                g = (hydrangeas.getRGB(x, y) >> 8) & 0xFF;
                b = (hydrangeas.getRGB(x, y)) & 0xFF;
                Color.RGBtoHSB(r, g, b, HSB);
                
                if (!(HSB[0] > colorRangeLower && HSB[0] < colorRangeUpper)){
                        noLeaves.setRGB(x,y,hydrangeas.getRGB(x,y));
                }
                else {
                    if (HSB[1] < saturation){
                        noLeaves.setRGB(x,y,hydrangeas.getRGB(x,y));
                    }
                } 
            }
        }
        
        for(y = 0; y < hydrangeas.getHeight() - 8; y++){
            for (x = 0; x < hydrangeas.getWidth() - 8; x++){
               
                    if (noLeaves.getRGB(x, y) == Color.black.getRGB()){
                        if (noLeaves.getRGB(x + 8, y) == Color.black.getRGB() && noLeaves.getRGB(x, y + 8) == Color.black.getRGB()){
                            for(int i = y; i < y + 8; i++){
                                for(int j = x; j < x + 8; j++){
                                    noLeaves.setRGB(j, i,Color.black.getRGB());
                                }
                            }
                        }
                        
                    }
                
            }
        }
        
        write("Leaves removed");
        
        return noLeaves;
        
    }
    
    /*Get image file and return to caller the BufferedImage*/
    private static BufferedImage readImage(File file){
        
        BufferedImage img = null;
        
        try{
            img = ImageIO.read(file);
            return img;
        }
        catch(IOException e){
            
        }
        write("Image read\n");
        
        return img;
        
    }
    
    /*Print string on console*/
    private static void write(String string){
        
        System.out.print(string);
        
    }
    
}
