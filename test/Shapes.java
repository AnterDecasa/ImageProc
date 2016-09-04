
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anter_000
 */
public class Shapes extends JPanel{
    
    private static BufferedImage image = new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB);
    int x, y;
    
    public static void main(String[] args){
        JFrame f = new JFrame("Shapes");
        f.add(new Shapes());
        f.setSize(400,400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
    @Override
    public void paint(Graphics g){
        setBackgroundColor(Color.white);
        //square();
        //triangle();
        circle();
        g.drawImage(image, 0, 0, this);
    }
    
    public void setBackgroundColor(Color color){
        for(y = 0; y < 400; y++){
            for(x = 0; x < 400; x++){
                image.setRGB(x, y, color.getRGB());
            }
        }
    }
    
    public void circle(){
        int radius = 50;
        int centerX = 50, centerY = 50;
        double angle, x, y, r;
        double PI = 3.1415926535;
        
        for (r = 0; r < radius; r++){
            for (angle = 0; angle < 360; angle += 0.1){
                x = r * Math.cos(angle * PI/180);
                y = r * Math.sin(angle * PI/180);
                image.setRGB(centerY + (int)y, centerX + (int)x, Color.yellow.getRGB());
            }
        }
        getAreaInPixels(image);
    }
    
    public void square(){
        for(y = 0; y < 100; y++){
            for(x = 0; x < 100; x++){
                image.setRGB(x, y, Color.blue.getRGB());
            }
        }
        getAreaInPixels(image);
    }
    
    public void triangle(){
        for(y = 0; y < 100; y++){
            for(x = 0; x < y; x++){
                image.setRGB(x, y, Color.red.getRGB());
            }
        }
        getAreaInPixels(image);
    }
    
    public void getAreaInPixels(BufferedImage image){
        float HSB[] = new float[3];
        int r, g, b;
        
        int cntPixel = 0;
        
        for(y = 0; y < 400; y++){
            for (x = 0; x < 400; x++){
                r = (image.getRGB(x, y) >> 16) & 0xFF;
                g = (image.getRGB(x, y) >> 8) & 0xFF;
                b = (image.getRGB(x, y)) & 0xFF;
                Color.RGBtoHSB(r, g, b, HSB);
                if (!(HSB[1] < 0.1 && HSB[2] > 0.9)){
                //if(image.getRGB(x, y) != Color.white.getRGB()){
                    cntPixel++;
                }
            }
        }
        System.out.print("Number of colored pixels: " + cntPixel );
    }
}
