
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anter_000
 */
public class LetterRecognition extends JFrame{
    
    int []array = {0,0,0,0,0,0,0,0,0};
    boolean[] binaryVersion = {false,false,false,false,false,false,false,false,false};
    int random = 0;
    int ctrA = 0;
    int letterCtr = 0;
    
    JPanel panel;
    JButton yes;
    JButton no;
    JButton stop;
    JButton test;
    JButton next;
    JLabel imagePanel;
    JLabel imageSegment;
    JLabel ctrOfA;
    JLabel letterCnt;
    JLabel testLetter;
    JLabel guess;
    
    public static void main(String[] args){
        
        LetterRecognition frame = new LetterRecognition();
        
        
    }
    
    public LetterRecognition(){
        
        panel = new JPanel();
        ctrOfA = new JLabel();
        ctrOfA.setText("Number of A's found: " + ctrA);
        panel.add(ctrOfA);
        letterCnt = new JLabel();
        letterCnt.setText("Total number of letters ran: " + letterCtr);
        panel.add(letterCnt);
        
        random = generateRandomNum();
        imagePanel = new JLabel(new ImageIcon(readImage("letter" + random + ".png")));
        imageSegment = new JLabel(new ImageIcon(drawSegments(readImage("letter" + random + ".png"))));
        panel.add(imageSegment);
        panel.add(imagePanel);
        
        yes = new JButton("Yes");
        yes.setSize(10, 20);
        yes.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                letterCtr++;
                letterCnt.setText("Total number of letters ran: " + letterCtr);
                ctrA++;
                ctrOfA.setText("Number of A's found: " + ctrA);
                int []tempArray = {0,0,0,0,0,0,0,0,0};
                tempArray = checkSegments(readImage("letter" + random + ".png"));
                for(int i=0;i<array.length;i++){
                    array[i] = array[i] + tempArray[i];
                }
                //for(int i=0;i<array.length;i++){
                //    write(array[i] + ", ");
                //}
                random = generateRandomNum();
                imagePanel.setIcon(new ImageIcon(readImage("letter" + random + ".png")));
                imageSegment.setIcon(new ImageIcon(drawSegments(readImage("letter" + random + ".png"))));
            }
        });
        panel.add(yes);
        
        no = new JButton("No");
        no.setSize(10,20);
        no.addActionListener(new ActionListener(){
           
            @Override
            public void actionPerformed(ActionEvent e){
                letterCtr++;
                letterCnt.setText("Total number of letters ran: " + letterCtr);
                random = generateRandomNum();
                imagePanel.setIcon(new ImageIcon(readImage("letter" + random + ".png")));
                imageSegment.setIcon(new ImageIcon(drawSegments(readImage("letter" + random + ".png"))));
            }
        });
        panel.add(no);
        
        stop = new JButton("Stop");
        stop.setSize(10, 20);
        stop.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(ctrA > 0){
                    for(int i=0; i < array.length; i++){
                        array[i] = array[i]/ctrA;
                    }
                }
                for(int i=0;i < array.length;i++){
                    write(array[i] + ", ");
                }
                binaryVersion = binarySegment(array);
                for(int i=0;i < binaryVersion.length;i++){
                    write(binaryVersion[i] + ", ");
                }
                yes.setEnabled(false);
                no.setEnabled(false);
                stop.setEnabled(false);
                test.setEnabled(true);
                
            }
        });
        panel.add(stop);
        
        test = new JButton("Test");
        test.setSize(10,20);
        test.setEnabled(false);
        test.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                write("test");
                int []testArray = {0,0,0,0,0,0,0,0,0};
                boolean []testBinary = {false,false,false,false,false,false,false,false,false};;
                random = generateRandomNum();
                testLetter.setIcon(new ImageIcon(readImage("letter" + random + ".png")));
                testLetter.setVisible(true);
                testArray = checkSegments(readImage("letter" + random + ".png"));
                for(int i=0;i < testArray.length;i++){
                    write(testArray[i] + ", ");
                }
                testBinary = binarySegment(testArray);
                for(int i=0;i < testBinary.length;i++){
                    write(testBinary[i] + ", ");
                }
                if(equalLetter(binaryVersion,testBinary)){
                    guess.setText("Letter A");
                }
                else{
                    guess.setText("NOT A");
                }
                next.setVisible(true);
                test.setEnabled(false);
            }
        });
        panel.add(test);
        
        testLetter = new JLabel(new ImageIcon(readImage("letter" + random + ".png")));
        panel.add(testLetter);
        testLetter.setVisible(false);
        
        guess = new JLabel();
        panel.add(guess);
        
        next = new JButton("Next");
        next.setSize(10,20);
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int []testArray = {0,0,0,0,0,0,0,0,0};
                boolean []testBinary = {false,false,false,false,false,false,false,false,false};;
                random = generateRandomNum();
                testLetter.setIcon(new ImageIcon(readImage("letter" + random + ".png")));
                testLetter.setVisible(true);
                testArray = checkSegments(readImage("letter" + random + ".png"));
                for(int i=0;i < testArray.length;i++){
                    write(testArray[i] + ", ");
                }
                testBinary = binarySegment(testArray);
                for(int i=0;i < testBinary.length;i++){
                    write(testBinary[i] + ", ");
                }
                if(equalLetter(binaryVersion,testBinary)){
                    guess.setText("Letter A");
                }
                else{
                    guess.setText("NOT A");
                }
            }
        });
        panel.add(next);
        next.setVisible(false);
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                }
        });
        this.setSize(800, 800);
        this.setVisible(true);
        this.add(panel);
        
    }
    
    private boolean equalLetter(boolean[] array1, boolean[] array2){
        
        boolean retBool = true;
        
        for(int i = 0; i < array1.length; i++){
            if(array1[i] != array2[i]){
                retBool = false;
                break;
            }
        }
        
        return retBool;
                
    }
    
    private boolean[] binarySegment(int[] array){
        boolean []retArray = {false,false,false,false,false,false,false,false,false};
        
        for(int i = 0; i < array.length; i++){
            if(array[i] > 20){
                retArray[i] = true;
            }
        }
        
        return retArray;
    }
    
    private BufferedImage drawSegments(BufferedImage image){
        
        int width = image.getWidth();
        int height = image.getHeight();
        int segmentWidth = width/3;
        int segmentHeight = height/3;
        
        int x = segmentWidth;
        int y = segmentHeight;
        for(int i=0; i < height; i++){
            image.setRGB(x,i,Color.red.getRGB());
        }
        x += segmentWidth;
        for(int i=0; i < height; i++){
            image.setRGB(x,i,Color.red.getRGB());
        }
        
        for(int j=0; j < width; j++){
            image.setRGB(j,y,Color.red.getRGB());
        }
        y+= segmentHeight;
        for(int j=0; j < width; j++){
            image.setRGB(j,y,Color.red.getRGB());
        }
        
        return image;
    
    }
    
    private int[] checkSegments(BufferedImage image){
        
        int []array = {0,0,0,0,0,0,0,0,0};
        int width = image.getWidth();
        int height = image.getHeight();
        int segmentWidth = width/3;
        int segmentHeight = height/3;
        int segmentarea = segmentWidth * segmentHeight;
        int i=0, j=0, widthCtr=0, heightCtr=0, pixelCtr=0, currentX=0, currentY=0;
        int bgColor = image.getRGB(0,0);
        write("segment width: " + segmentWidth + "\n");
        write("segment height: " + segmentHeight + "\n");
        
        pixelCtr=0;
        for(i=0; i < segmentHeight; i++){
            for(j=0; j < segmentWidth; j++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[0] = 1;
        //}
        array[0] = pixelCtr;
        
        widthCtr=0;
        pixelCtr=0;
        currentX = segmentWidth;
        for(i=0; i < segmentHeight; i++){
            widthCtr=0;
            for(j=currentX; widthCtr < segmentWidth; j++, widthCtr++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[1] = 1;
        //}
        array[1] = pixelCtr;
        
        pixelCtr=0;
        currentX += segmentWidth;
        for(i=0; i < segmentHeight; i++){
            for(j=currentX; j < width; j++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[2] = 1;
        //}
        array[2] = pixelCtr;
        
        heightCtr=0;
        pixelCtr=0;
        currentY = segmentHeight;
        for(i=currentY; heightCtr < segmentHeight; i++,heightCtr++){
            for(j=0; j < segmentWidth; j++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[3] = 1;
        //}
        array[3] = pixelCtr;
        
        widthCtr=0;
        heightCtr=0;
        pixelCtr=0;
        currentX = segmentWidth;
        for(i=currentY; heightCtr < segmentHeight; i++,heightCtr++){
            widthCtr=0;
            for(j=currentX; widthCtr < segmentWidth; j++, widthCtr++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[4] = 1;
        //}
        array[4] = pixelCtr;
        
        heightCtr=0;
        pixelCtr=0;
        currentX += segmentWidth;
        for(i=currentY; heightCtr < segmentHeight; i++,heightCtr++){
            for(j=currentX; j < width; j++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[5] = 1;
        //}
        array[5] = pixelCtr;
        
        pixelCtr=0;
        currentY += segmentHeight;
        for(i=currentY; i < height; i++){
            for(j=0; j < segmentWidth; j++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[6] = 1;
        //}
        array[6] = pixelCtr;
        
        widthCtr=0;
        pixelCtr=0;
        currentX = segmentWidth;
        for(i=currentY; i < height; i++){
            widthCtr=0;
            for(j=currentX; widthCtr < segmentWidth; j++, widthCtr++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[7] = 1;
        //}
        array[7] = pixelCtr;
        widthCtr=0;
        pixelCtr=0;
        currentX += segmentWidth;
        for(i=currentY; i < height; i++){
            for(j=currentX; j < width; j++){
                if(image.getRGB(j, i) != bgColor){
                    pixelCtr++;
                }
            }
        }
        //if(pixelCtr > 0){
        //    array[8] = 1;
        //}
        array[8] = pixelCtr;
        
        return array;
    }
    
    private int generateRandomNum(){
        int random;
        
        random = ((int)(Math.random() * 100))%13;
        if(random == 0){
            random++;
        }
        
        return random;
    }
    
    private BufferedImage readImage(String filename){
        
        BufferedImage retImage = null;
        
        try{
            retImage = ImageIO.read(new File("C:\\Users\\anter_000\\Desktop\\image processing\\Letters\\" + filename));
        }
        catch(IOException e){
            write("No image read");
        }
        
        return retImage;
    }
    
    private void write(String string){
        System.out.println(string);
    }
    
}
