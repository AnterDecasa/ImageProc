
import java.awt.Color;
import java.awt.Component;
import java.awt.image.*;
import java.io.*;
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
 * v1   July 8, 2016
 * v1.1 July 15, 2016
 * v2.0 August 6, 2016
 */

public class BasicProcessing  extends JPanel {
    
    //BufferedImage image;

    public BasicProcessing(){
        
    }
    
    public BasicProcessing(String filePath) {
        
        //this.image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filePath));
     
    }
    
    public void getAreaInPixels(String filename){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        
        float HSB[] = new float[3];
        int x, y, r, g, b;
        int cntPixel = 0;
        
        for(y = 0; y < image.getHeight(); y++){
            for (x = 0; x < image.getWidth(); x++){
                r = (image.getRGB(x, y) >> 16) & 0xFF;
                g = (image.getRGB(x, y) >> 8) & 0xFF;
                b = (image.getRGB(x, y)) & 0xFF;
                Color.RGBtoHSB(r, g, b, HSB);
                if (!(HSB[1] < 0.1 && HSB[2] > 0.9)){
                //if(image.getRGB(x, y) != Color.white.getRGB() && image.getRGB(x, y) != Color.black.getRGB()){
                    cntPixel++;
                }
            }
        }
        write("Number of colored pixels: " + cntPixel );
        
    }
    
    public BufferedImage grayScale(String filename){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        BufferedImage grayScaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                Color c = new Color(image.getRGB(j,i));
                int red = (int)(c.getRed());
                int green = (int)(c.getGreen());
                int blue = (int)(c.getBlue());
                Color newColor = new Color((red+green+blue)/3, (red+green+blue)/3, (red+green+blue)/3);
                grayScaleImage.setRGB(j, i, newColor.getRGB());
            }
        }
        
        write("GrayScale image\n");
        
        return grayScaleImage;
               
    }
    
    public BufferedImage grayScaleBrighten(String filename, Double intensity){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        BufferedImage grayScaleImageLight = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                Color c = new Color(image.getRGB(j,i));
                int red = (int)(c.getRed());
                int green = (int)(c.getGreen());
                int blue = (int)(c.getBlue());
                int grayValue = (int)(((red+green+blue)/3));
                int lighterColor = grayValue + (int)(grayValue * intensity);
                if (lighterColor > 255)
                    lighterColor = 255;
                Color newColor = new Color(lighterColor, lighterColor, lighterColor);
                grayScaleImageLight.setRGB(j, i, newColor.getRGB());
            }
        }
        write("Grayscale Brighten\n");
        
        return grayScaleImageLight;
        
    }
    
    public BufferedImage grayScaleDarken(String filename, Double intensity){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        BufferedImage grayScaleImageDark = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for (int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                Color c = new Color(image.getRGB(j,i));
                int red = (int)(c.getRed());
                int green = (int)(c.getGreen());
                int blue = (int)(c.getBlue());
                int grayValue = (int)(((red+green+blue)/3));
                int darkerColor = grayValue - (int)(grayValue * intensity);
                if (darkerColor < 0)
                    darkerColor = 0;
                Color newColor = new Color(darkerColor, darkerColor, darkerColor);
                grayScaleImageDark.setRGB(j, i, newColor.getRGB());
            }
        }
        write("Grayscale Darken\n");
        
        return grayScaleImageDark;
        
    }
    
    private void createHistogram(BufferedImage image){
        
        int[] histogram = new int[256];
        int red = 0, green = 0, blue = 0;

        Color color;
        
        for (int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                color = new Color(image.getRGB(j, i));
                red = (int) color.getRed();
                green = (int) color.getGreen();
                blue = (int) color.getBlue();
                histogram[(red + green + blue)/3] += 1;
            }
        }
        
        createHistogramCSV(histogram, "HistogramLight");
        write("Histogram created\n");
        
    }
    
    private void createHistogramCSV(int[] array, String filename){
        
        File courseCSV = new File("HistogramLight.csv");

        try{
            PrintWriter outfile = new PrintWriter(courseCSV);
            
            for (int i=0; i < array.length ; i++) {
                outfile.write(array[i] + ",");
            }

            outfile.close();
        }
        catch(IOException e){
            
        }
        write("Histogram CSV created\n");
    
    }
    
    public BufferedImage imageDecrease(String filename){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        write("height: " + image.getHeight() + " width: " + image.getWidth() + "\n" );
        
        BufferedImage smallerImage = new BufferedImage(image.getWidth()/2, image.getHeight()/2, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i <= image.getHeight()-2; i = i+2){
            for(int j = 0; j <= image.getWidth()-2; j = j+2){
                int smallerX = j/2;
                int smallerY = i/2;
                smallerImage.setRGB(smallerX, smallerY, image.getRGB(j, i));
            }
        }
        
        write("Image decreased\n");
        
        write("height: " + smallerImage.getHeight() + " width: " + smallerImage.getWidth() + "\n" );
       
        return smallerImage;
        
    }
    
    public BufferedImage imageIncrease(String filename){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        write("height: " + image.getHeight() + " width: " + image.getWidth() + "\n" );
        
        BufferedImage biggerImage = new BufferedImage(image.getWidth()*2, image.getHeight()*2, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                biggerImage.setRGB(j*2, i*2, image.getRGB(j, i));
                biggerImage.setRGB(j*2+1, i*2, image.getRGB(j, i));
                biggerImage.setRGB(j*2, i*2+1, image.getRGB(j, i));
                biggerImage.setRGB(j*2+1, i*2+1, image.getRGB(j, i));
            }
        }
        write("Image increased\n");
        
        write("height: " + biggerImage.getHeight() + " width: " + biggerImage.getWidth() + "\n" );
        
        return biggerImage;
        
    }
    
    public BufferedImage imageDecreaseInter(String filename){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        
        write("height: " + image.getHeight() + " width: " + image.getWidth() + "\n" );
        
        BufferedImage smallerImage = new BufferedImage(image.getWidth()/2, image.getHeight()/2, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < image.getHeight()/2; i++){
            for(int j = 0; j < image.getWidth()/2; j++){
                
                Color curr = new Color(image.getRGB(j*2,i*2));
                Color left = new Color(image.getRGB(j*2+1,i*2));
                Color down = new Color(image.getRGB(j*2, i*2+1));
                Color leftDown = new Color(image.getRGB(j*2+1,i*2+1));
                
                int red = (curr.getRed() + left.getRed() + down.getRed() + leftDown.getRed()) / 4;
                int blue = (curr.getBlue() + left.getBlue() + down.getBlue() + leftDown.getBlue()) / 4;
                int green = (curr.getGreen() + left.getGreen() + down.getGreen() + leftDown.getGreen()) / 4;
                
                smallerImage.setRGB(j, i,new Color(red , green, blue).getRGB());
                
            }
        }
        
        write("Image decreased\n");
        
        write("height: " + smallerImage.getHeight() + " width: " + smallerImage.getWidth() + "\n" );
        
        return smallerImage;
        
    }
    
    public BufferedImage imageIncreaseInter(String filename){
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        
        write("height: " + image.getHeight() + " width: " + image.getWidth() + "\n" );
        
        BufferedImage biggerImage = new BufferedImage(image.getWidth()*2, image.getHeight()*2, BufferedImage.TYPE_INT_RGB);
        
        for(int i = 0; i < image.getHeight()-1; i++){
            for(int j = 0; j < image.getWidth()-1; j++){
                biggerImage.setRGB(j*2, i*2, image.getRGB(j, i));
                
                Color xcurr = new Color(image.getRGB(j,i));
                Color xnext = new Color(image.getRGB(j+1,i));
                
                int xr = (xcurr.getRed() + xnext.getRed()) / 2;
                int xg = (xcurr.getGreen() + xnext.getGreen()) / 2;
                int xb = (xcurr.getBlue() + xnext.getBlue()) / 2;
                
                biggerImage.setRGB(j*2+1, i*2,new Color(xr , xg, xb).getRGB());
                
                
                Color ycurr = new Color(image.getRGB(j,i));
                Color ynext = new Color(image.getRGB(j,i+1));
                
                int yr = (ycurr.getRed() + ynext.getRed()) / 2;
                int yg = (ycurr.getGreen() + ynext.getGreen()) / 2;
                int yb = (ycurr.getBlue() + ynext.getBlue()) / 2;
                
                biggerImage.setRGB(j*2, i*2+1, new Color(yr, yg, yb).getRGB());
                
                

                
                biggerImage.setRGB(j*2+1, i*2+1, new Color((xr+yr)/2, (xg+yg)/2, (xb+yb)/2).getRGB());
            }
        }
        
        write("Image increased\n");
        
        write("height: " + biggerImage.getHeight() + " width: " + biggerImage.getWidth() + "\n" );
        
        return biggerImage;
        
    }
    
    public BufferedImage warp(){
        
        int i, j, interval = 1;
        
        BufferedImage image = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + "newAHydrangeas2.jpg"));
        BufferedImage warpImage = new BufferedImage(image.getWidth()*2, image.getHeight()*2, BufferedImage.TYPE_INT_RGB);
        
        for(i = 0; i <= image.getHeight()/2-2; i = i + 2){
            for(j = 0; j <= image.getWidth()/2-2; j++){
                int smallerX = j/interval;
                int smallerY = i/2;
                int storeCurrX = smallerX;
                int storeCurrY = smallerY+1;
                warpImage.setRGB(j, i, image.getRGB(j, i));
                warpImage.setRGB(j, i, image.getRGB(storeCurrX, storeCurrY));
                warpImage.setRGB(j, i+1, image.getRGB(storeCurrX, storeCurrY+1));
                warpImage.setRGB(j+1, i, image.getRGB(storeCurrX+1, storeCurrY));
                warpImage.setRGB(j+1, i+1, image.getRGB(storeCurrX+1, storeCurrY+1));
            }
            for(; j <= image.getWidth()-2; j = j + 2){
                int smallerX = j/interval;
                int smallerY = i/2;
                int storeCurrX = smallerX;
                int storeCurrY = smallerY+1;
                warpImage.setRGB(smallerX, smallerY, image.getRGB(j, i));
                warpImage.setRGB(j, i, image.getRGB(storeCurrX, storeCurrY));
                warpImage.setRGB(j, i+1, image.getRGB(storeCurrX, storeCurrY+1));
                warpImage.setRGB(j+1, i, image.getRGB(storeCurrX+1, storeCurrY));
                warpImage.setRGB(j+1, i+1, image.getRGB(storeCurrX+1, storeCurrY+1));
            }
            if( i%4 == 0){
                interval++;
            }
        }
        
        for(; i <= image.getHeight()-2; i = i + 2){
            for(j = 0; j <= image.getWidth()-2; j = j + 2){
                int smallerX = j/interval;
                int smallerY = i/2;
                warpImage.setRGB(smallerX, smallerY, image.getRGB(j, i));
            }
            if( i%4 == 0){
                interval--;
            }
        }
        
        write("Fisheye\n");
        
        return warpImage;
        
    }
    
    public BufferedImage connectTheDots(String filename){
        
        BufferedImage connectedDots = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
        double PI = 3.1415926535;
        int radius = 5;
        double angle, x, y, r;
        int centerY = 0, centerX = 0;
        
        write("width: " + connectedDots.getWidth() + " height: " + connectedDots.getHeight() + "\n");
        
        for(int i = 10; i < connectedDots.getHeight() - radius; i++ ){
            for(int j = 10; j < connectedDots.getWidth() - radius; j++){
                
                write("X: " + j + " Y: " + i + "\n");
                
                if (connectedDots.getRGB(j, i) != Color.white.getRGB()){
                    
                    centerY = i;
                    centerX = j;
                    write("Center: X: " + centerX + " Y: " + centerY + "\n");
                    
                    double tempRadius = 0;
                    for(r = 0; r < radius; r++){
                        for (angle = 0; angle < 360; angle += 0.1){
                            x = r * Math.cos(angle * PI/180);
                            y = r * Math.sin(angle * PI/180);
                            
                            if(connectedDots.getRGB(centerX + (int)x, centerY + (int)y) != Color.white.getRGB()){
                                
                                tempRadius = r; 
                                for (r = 0; r < tempRadius; r++){
                                    x = r * Math.cos(angle * PI/180);
                                    y = r * Math.sin(angle * PI/180);
                                    connectedDots.setRGB(centerX + (int)x, centerY + (int)y, Color.black.getRGB());
                                }
                            
                            }
                        }
                    }
                }
            }
        }
        
        write("Dots connected");
        return connectedDots; 
    
    }
    
    public BufferedImage edgeRetrieve(String filename){
        
        BufferedImage imageEdge = readImage(new File("C:/Users/anter_000/Desktop/image processing/" + filename));
       
        boolean colorWhiteFlag = false;
        boolean colorBlackFlag = false;
        int width = imageEdge.getWidth();
        int height = imageEdge.getHeight();
        
        int bgColor = imageEdge.getRGB(0,0);
        for(int i = 0; i < height - 1; i++){
            for(int j = 0; j < width - 1; j++){
                if(imageEdge.getRGB(j, i) != bgColor){
                    int currColor = imageEdge.getRGB(j, i);
                    if(j > 0 && i > 0 && imageEdge.getRGB(j-1, i-1) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                    if(i > 0 && imageEdge.getRGB(j, i-1) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                    if(i > 0 && j < width && imageEdge.getRGB(j+1, i-1) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                    if(j > 0 && imageEdge.getRGB(j-1, i) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                    if(j < width&& imageEdge.getRGB(j+1, i) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                    if(j > 0 && i < height && imageEdge.getRGB(j-1, i+1) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                    if(i < height && imageEdge.getRGB(j, i+1) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                    if (i < height && j < width && imageEdge.getRGB(j+1, i+1) != currColor ){
                        //imageEdge.setRGB(j, i, Color.black.getRGB());
                        colorBlackFlag = true;
                    }
                }
                
                if(colorBlackFlag){
                    imageEdge.setRGB(j, i, Color.black.getRGB());
                }
                else{
                    imageEdge.setRGB(j, i, Color.white.getRGB());
                }
             colorBlackFlag = false;   
            }
            
            
           //colorWhiteFlag = false;
           
        }
        
        /*
        for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    if (imageEdge.getRGB(j, i) == bgColor) {
                        imageEdge.setRGB(j, i, Color.white.getRGB());
                    }
                }
            }
        */
        write("Edge retrieved\n");
        
        return imageEdge;
        
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
    
    private void write(String string){
        
        System.out.print(string);
        
    }
    
}
