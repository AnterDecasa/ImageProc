
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
public class Transform extends JPanel{
    
    public Transform(){
        
        this.setVisible(true);
    
    }
    
    @Override
    public void paint(Graphics g){
        
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        
        image1 = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + "womanMorphSmall5.jpg"));
            
        g.drawImage(image1, 0, 0, null);
            
        g.drawImage(image1, image1.getWidth(), 0, null);
        image2 = morphAll("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg", 0x22ffffff);
        g.drawImage(image2, image1.getWidth(), 0, null);
            
        g.drawImage(image1, (image1.getWidth())*2, 0, null);
        image2 = morphAll("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg", 0x44ffffff);
        g.drawImage(image2, (image1.getWidth())*2, 0, null);
            
        g.drawImage(image1, 0, image1.getHeight(), null); 
        image2 = morphAll("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg", 0x66ffffff);
        g.drawImage(image2, 0, image1.getHeight(), null);
            
        g.drawImage(image1, image1.getWidth(), image1.getHeight(), null);
        image2 = morphAll("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg", 0x88ffffff);
        g.drawImage(image2, image1.getWidth(), image1.getHeight(), null);
            
        g.drawImage(image1, (image1.getWidth())*2, image1.getHeight(), null);
        image2 = morphAll("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg", 0xaaffffff);
        g.drawImage(image2, (image1.getWidth())*2, image1.getHeight(), null);
            
        g.drawImage(image1, 0, (image1.getHeight())*2, null);
        image2 = morphAll("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg", 0xccffffff);
        g.drawImage(image2, 0, (image1.getHeight())*2, null);
            
        g.drawImage(image1, image1.getWidth(), (image1.getHeight())*2, null);
        image2 = morphAll("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg", 0xeeffffff);
        g.drawImage(image2, image1.getWidth(), (image1.getHeight())*2, null);
            
        g.drawImage(image1, (image1.getWidth())*2, (image1.getHeight())*2, null);
        image2 = morphHalf("womanMorphSmall5.jpg", "tigerMorphSmall5.jpg");
        g.drawImage(image2, (image1.getWidth())*2, (image1.getHeight())*2, null);
          
    }
    
    public BufferedImage morphAll(String image1Filename, String image2Filename, int transparency){
        
        BufferedImage image1 = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + image1Filename));
        BufferedImage image2 = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + image2Filename));
        BufferedImage morphedImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int y, x;
        
        for(y = 0; y < image1.getHeight(); y++){
            for (x = 0 ; x < image1.getWidth(); x++){
                    morphedImage.setRGB(x, y, image2.getRGB(x, y) &  transparency);
            }
        }
        
        write("Morping entire image done\n");
        
        return morphedImage;
    }
    
    public BufferedImage morphHalf(String image1Filename, String image2Filename){
        
        BufferedImage image1 = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + image1Filename));
        BufferedImage image2 = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + image2Filename));
        BufferedImage morphedImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int y, x;
        
        for(y = 0; y < image1.getHeight(); y++){
            int transparencyTemp = 0xffffffff;
            int cnt = 0;
            for (x = 0 ; x < (image1.getWidth()/8)*3; x++){
                    morphedImage.setRGB(x, y, image2.getRGB(x, y));
            }
            for (; x < (image1.getWidth()/8)*6 && cnt <= 123; x++){
                morphedImage.setRGB(x, y, image2.getRGB(x, y) &  transparencyTemp);
                transparencyTemp = transparencyTemp - 0x02000000;
                cnt++;
            }
            for(;x < image1.getWidth(); x++){
                morphedImage.setRGB(x, y, image1.getRGB(x, y));
            }
        }        
        
        write("Morping half of image done\n");
        
        return morphedImage;
        
    }
    
    private BufferedImage readImage(File file){
        
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

    public void write(String string){
        System.out.print(string);
    }
    
}
