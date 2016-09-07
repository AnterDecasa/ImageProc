
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
public class Test extends JFrame {
    
    static BasicProcessing img = new BasicProcessing("newAHydrangeas2.jpg");
    static Test frame = null;
    JButton segmentBtn = new JButton("Segmentation");
    JPanel workArea = new JPanel();
    
    Segmentation removeLeaves = new Segmentation();
    
    public static void main(String[] args){
        
        frame = new Test();
        //write("Number of object found: " + ObjectDetection.countObjects(readImage(new File("C:/Users/anter_000/Desktop/image processing/" + "cntObjects.png"))));
        
    }
    
    public Test(){
        /*
        segmentBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                write("segment clicked");
                workArea.add(removeLeaves);
                
            }
        });
        
        workArea.setLayout(new BoxLayout(workArea, BoxLayout.PAGE_AXIS));
        workArea.setVisible(true);
        workArea.add(segmentBtn);
        */
        write("Number of object found: " + ObjectDetection.countObjects(readImage(new File("C:/Users/anter_000/Desktop/image processing/" + "cntObjects.png"))));
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                }
        });
        this.setVisible(true);
        this.setSize(900, 900);
        
    }

    @Override
    public void paint (Graphics g){
        
        BufferedImage image = null;
        BufferedImage image1 = null, image2 = null;
        
        //image = img.grayScale("newAHydrangeas2.jpg");
        //img.getAreaInPixels("newAHydrangeas2.jpg");
        //img.createHistogram("newAHydrangeas2.jpg");
        //image = img.grayScaleBrighten("newAHydrangeas2.jpg", .5);
        //image = img.grayScaleDarken("newAHydrangeas2.jpg", .5);

        //image = img.imageDecrease("newAHydrangeas2.jpg");
        //image = img.imageIncrease("newAHydrangeas2.jpg");
        //image = img.imageIncreaseInter("newAHydrangeas2.jpg");
        //image = img.imageDecreaseInter("newAHydrangeas2.jpg");
        //image = img.warp();
        g.drawImage(image, 0 ,0 ,null );
        
        
        try{
            
            /*-----Segmentation-----*/
            /*
            Segmentation removeLeaves = new Segmentation();
            workArea.add(removeLeaves);
            */
            /*-----Morphing-----*/
            /*
            Transform morph = new Transform();
            workArea.add(morph);
            */
            //frame.add(workArea);
            /*
            
            /*-----------Connect the dots--------*/
            /*
            image1 = ImageIO.read(new File("C:/Users/anter_000/Desktop/image processing/" + "DottedSquareCircleFlag.png"));
            g.drawImage(image1, image1.getWidth(), image1.getHeight(), null);
            image2 = img.connectTheDots("DottedSquareCircleFlag.png");
            g.drawImage(image2, image1.getWidth()*2, image1.getHeight(), null);
            */
            
            /*-----------Get Edge---------------*/
            image1 = ImageIO.read(new File("C:/Users/anter_000/Desktop/image processing/" + "circle2.png"));
            g.drawImage(image1, 0, 0, null);
            image2 = img.edgeRetrieve("circle2.png");
            g.drawImage(image2, image1.getWidth(), 0, null);
            
        }
        catch(IOException e){
            
        }

        //f.setSize(image.getWidth(), image.getHeight());

    }
    
    private static BufferedImage readImage(File file){
        
        BufferedImage readImage = null;
        
        try{
            readImage = ImageIO.read(file);
            return readImage;
        }
        catch(IOException e){
            
        }
        write("Image read\n");
        
        return readImage;
        
    }

    private static void write(String string){
        System.out.print(string);
    }
    
}
