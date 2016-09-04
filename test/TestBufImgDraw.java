/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anter_000
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestBufImgDraw extends Component{
    BufferedImage image;
    static int height, width;
    
    @Override
    public void paint (Graphics g){
        try{
            image = ImageIO.read(new File("C:/Users/anter_000/Desktop/image processing/shapes/japan.jpg"));
            height = image.getHeight();
            width = image.getWidth();
            System.out.print("height: " + height + " width: " + width );
        }catch(IOException e){
            
        }
        
        Image img2 = createImage(image);
        g.drawImage(img2, 0, 0, this);
    }
    
    //public TestBufImgDraw(){
        
    //}
    
    public Image createImage(BufferedImage image){
        //try{
            BufferedImage img;
            img = new BufferedImage(1500,1500,BufferedImage.TYPE_INT_RGB);
            Graphics g = img.createGraphics();
            //g.setColor(new Color ( 255, 255, 255 ) );
            //g.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
            g.drawImage(image, 0, 0, null);
            getAreaInPixels(img);
            //g.setColor(Color.black);
            //g.drawString("anter", 20, 20);
            //g.setColor(Color.red);  
            //g.drawLine(0, 50, 50, 50);
        //}catch(IOException e){
            
        //}
        
        return img;
    }
    
    @Override
    public Dimension getPreferredSize() {
        if (image == null) {
             return new Dimension(1500,1500);
        } else {
           return new Dimension(image.getWidth(null), image.getHeight(null));
       }
    }
    
    public void getAreaInPixels(BufferedImage image){
        float HSB[] = new float[3];
        int x, y, r, g, b;
        int cntPixel = 0;
        
        for(y = 0; y < image.getWidth(); y++){
            for (x = 0; x < image.getHeight(); x++){
                r = (image.getRGB(x, y) >> 16) & 0xFF;
                g = (image.getRGB(x, y) >> 8) & 0xFF;
                b = (image.getRGB(x, y)) & 0xFF;
                Color.RGBtoHSB(r, g, b, HSB);
                //if (!(HSB[1] < 0.1 && HSB[2] > 0.9)){
                if(image.getRGB(x, y) != Color.white.getRGB() && image.getRGB(x, y) != Color.black.getRGB()){
                    cntPixel++;
                }
            }
        }
        System.out.print("Number of colored pixels: " + cntPixel );
        grayScale(image);
    }
    
    public void grayScale(BufferedImage image){
        for (int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                Color c = new Color(image.getRGB(j,i));
                int red = (int)(c.getRed());
                int green = (int)(c.getGreen());
                int blue = (int)(c.getBlue());
                Color newColor = new Color((red+green+blue)/3, (red+green+blue)/3, (red+green+blue)/3);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
    }
    
    public static void main(String[] args){
        
        JFrame f = new JFrame("Load Image");
        f.setSize(width,height);
        f.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        f.add(new TestBufImgDraw());
        f.pack();
        f.setVisible(true);
    }
}
