import java.awt.Color;
import java.awt.image.*;
import java.io.IOException;
import java.io.File;

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
public class Area_in_pixels {
    
    public static boolean CheckIfLine(BufferedImage image){
		
		boolean thickFlag = false;
		int i, j;
		int width = image.getWidth();
		int height = image.getHeight();
		int bgColor = image.getRGB(0, 0);
		
		for(i = 0; i < height; i++){
			for(j = 0; j < width; j++){
				if(image.getRGB(j, i) != bgColor){
					if(j < width-1 && image.getRGB(j + 1, i) != bgColor && 
							i > 0 && image.getRGB(j+1, i-1) != bgColor &&
							i < height-1 && image.getRGB(j+1, i+1) != bgColor){
						thickFlag = true;
					}
				}
			}
			if(j < width){
				break;
			}
		}
		
		return thickFlag;
		
	}
	
	public static int AreaWithFill(BufferedImage image){
		
		int cnt = 0;
		int bgColor = image.getRGB(0, 0);
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(image.getRGB(j, i) != bgColor){
					cnt++;
				}
			}
		}
		return cnt;
		
	}
	
	private static BufferedImage FillShapeThickLine(BufferedImage image){
		
		int bgColor = image.getRGB(0, 0);
		int fillColor;
		if(bgColor != Color.black.getRGB()){
			fillColor = Color.black.getRGB();
		}
		else{
			fillColor = Color.gray.getRGB();
		}
		int i, j;
		int width = image.getWidth();
		int height = image.getHeight();
		int cntline = 0;
		boolean fillFlag = false;
		
		for(i = 0; i < height; i++){
			for(j = 0; j < width;){
				int k;
				if(image.getRGB(j, i) != bgColor){
					if(j < width-1 && image.getRGB(j+1, i) == bgColor &&
							j > 0 && image.getRGB(j-1, i) != fillColor){
						
						for(k = j + 1; k < width; k++){
							if(image.getRGB(k, i) != bgColor){
								break;
							}
							cntline++;
						}
						if(k < width){
							fillFlag = true;
							j++;
						}
					}
					
				}
				else if (fillFlag){
					int cnt = 0;
					for(k = j; cnt <= cntline && k < width; cnt++, k++){
						image.setRGB(k, i, fillColor);
					}
					j = k-1;
					fillFlag = false;
					cntline = 0;
				}
				j++;
			}
		}
		
		return image;
	
	}
	 
        public static BufferedImage FillShape(BufferedImage image){
		
		int bgColor = image.getRGB(0, 0);
		int fillColor;
		if(bgColor != Color.black.getRGB()){
			fillColor = Color.black.getRGB();
		}
		else{
			fillColor = Color.gray.getRGB();
		}
		int i, j;
		int width = image.getWidth();
		int height = image.getHeight();
		int cntline = 0;
		boolean fillFlag = false;
		
		for(i = 0; i < height; i++){
			for(j = 0; j < width;){
				int k;
				if(image.getRGB(j, i) != bgColor){
					if(j < width-1 && image.getRGB(j+1, i) == bgColor &&
							j > 0 && image.getRGB(j-1, i) != fillColor){
						
						for(k = j + 1; k < width; k++){
							if(image.getRGB(k, i) != bgColor){
								break;
							}
							cntline++;
						}
						if(k < width){
							fillFlag = true;
							j++;
						}
					}
					
				}
				else if (fillFlag){
					int cnt = 0;
					for(k = j; cnt <= cntline && k < width; cnt++, k++){
						image.setRGB(k, i, fillColor);
					}
					j = k-1;
					fillFlag = false;
					cntline = 0;
				}
				j++;
			}
		}
		
		return image;
	
	}
	private static int AreaBg(BufferedImage image){
		int cnt = 0;
		int bgColor = image.getRGB(0, 0);
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(image.getRGB(j, i) == bgColor){
					cnt++;
				}
			}
		}
		return cnt;
	}
	
	private static int AreaWithNoFill(BufferedImage image){
		
		int totalCnt = 0;
		int totalLineCnt = 0;
		int cntBgColor = 0;
		int cntnonBgColor = 0;
		boolean fillFlag = false;
		boolean checkIfInside = false;
		int bgColor = image.getRGB(0,0);
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		for(int i = 0; i < height; i++){
			int cntline = 0;
			for(int j = 0; j < width;){
				//write(i + " j" + j + "\n");
				if(image.getRGB(j, i) == bgColor){
					cntBgColor++;
				}
				if(image.getRGB(j, i) != bgColor){
					totalCnt++;
					cntline++;
					cntnonBgColor++;
					if(j < width-1 && image.getRGB(j+1, i) == bgColor && !checkIfInside){
						fillFlag = true;
						checkIfInside = true;
					}
				}
				else if (fillFlag){
					int cnt = 0;
					int k;
					for(k = j; k < width; k++){
						//write(i + " k" + k + ":" + image.getRGB(k, i)  + "\n");
						cnt++;
						
						if(image.getRGB(k, i) == bgColor){
							cntBgColor++;
						}
						if(image.getRGB(k, i) != bgColor){
							cntline += cnt;
							cntnonBgColor++;
							totalCnt += cnt;
							//totalCnt++;
							break;
						}
					}
					j = k;
					fillFlag = false;
					checkIfInside = false;
				}
				j++;
			}
			//write(i + "lineCnt: " + cntline + "\n");
			totalLineCnt += cntline;
		}
		write("bgColor Count: " + cntBgColor + "\n");
		write("nonbgColor Count: " + cntnonBgColor + "\n");
		write("line Count: " + totalLineCnt + "\n");
		return totalCnt;
		
	}
	
	private static int ImageArea(BufferedImage image){
		return image.getHeight() * image.getWidth();
	}
	
	private static BufferedImage readImage(String filename){
		
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File("C:/Users/Patriz/pictures/Anter/" + filename));
		}
		catch (IOException e){
			write("Was not able to read the image");
		}
		if(image != null){
			write("Image read\n");
		}
		else{
			write("No image read\n");
		}
		return image;
		
	}
	
	private static void write(String string){
		System.out.print(string);
	}
	
}

