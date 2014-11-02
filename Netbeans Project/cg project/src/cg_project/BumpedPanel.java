

package cg_project;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class BumpedPanel extends JPanel
{
    CGProjectView bpv;
    
    float normalvectors[][];
    int buffer[];
    int img_width, img_height;
    double light[];
    Image blitthis;
    
    int colorArr[][];
    
    BumpedPanel(CGProjectView _bpv)
    {
            bpv = _bpv;
            
            img_width = bpv.temp_data.getWidth();

            img_height = bpv.temp_data.getHeight();
            normalvectors = new float[img_width * img_height][3];
            light = new double[3];
            buffer = new int[img_height * img_width];
            if(bpv.onlyBumped){}
            else{
            light[0] = -(img_width*2 + bpv.longitudeUL.get(bpv.img_id)); //x
            light[1] = -(img_height*1 + bpv.latitudeUL.get(bpv.img_id)); //y
            light[2] = -30;
            
            }
    }

    

    public void execute()
    {
        findNormalVectors();
        makeImage();
    }

   

    public void makeBWImage(){
        int x;
            int y;
            float intensity;
            double intensity2,radius;
            int newColor, newR, newG, newB;
            int oldColor, oldR = 255, oldG = 255, oldB = 255;
            int[] buff = new int[1];
            int ht, index;
            int prevIndex, curIndex;
            float Nx, Ny, Nz, lightvX, lightvY, lightvZ, length;
            for (x = 0; x < img_width; x++)
            {
                for (y = 0; y < img_height; y++)
                {

		Nx = normalvectors[y * img_width + x][0];
		Ny = normalvectors[y * img_width + x][1];
		Nz = normalvectors[y * img_width + x][2];

		// make vector from pixel to light
		lightvX = (float)(light[0] - x);
		lightvY = (float)(light[1] - y);
		lightvZ = (float)(light[2]  - 0.0f);

		// normalize
		length = (float).1*((float)Math.sqrt (lightvX*lightvX + lightvY*lightvY + lightvZ*lightvZ));

		lightvX /= length;
		lightvY /= length;
		lightvZ /= length;

		// take dot product
		intensity = (Nx * lightvX + Ny * lightvY + Nz * lightvZ);

		if (intensity < 0.0f){intensity = 0.0f;}

                    // brighten slightly (ambient)
                intensity+=0.35;
                if (intensity > 1.0) {
                    intensity = (float) 1.0;}

                    bpv.dataimg.getPixel(x, y, buff);
                    ht = buff[0];
                    newR = (int) ((intensity * 255 * (ht - bpv.minValue)) / (bpv.maxValue - bpv.minValue));
                    newG = newB = newR;
    //                System.out.println("error pt = "+(x*img_width + y));

                    buffer[x*img_width + y] = (255 << 24) | (newR << 16) | (newG << 8) | newB;
                    }
                }
 }

    public void makeImage()
    {
            if(bpv.onlyBumped){
                makeBWImage();
            }
            

            blitthis = createImage(new MemoryImageSource(img_width, img_height, buffer, 0, img_width));
               BufferedImage bi = new BufferedImage
            (blitthis.getWidth(null),blitthis.getHeight(null),BufferedImage.TYPE_INT_RGB);
            Graphics bg = bi.getGraphics();
            bg.drawImage(blitthis, 0, 0, null);
            bg.dispose();
            File outputfile = new File(bpv.outputPath+"\\"+bpv.fName+".jpg");
            bpv.counter++;
            try {
            ImageIO.write(bi,"jpg", outputfile);
            normalvectors = null;
            } catch (IOException e) {
            e.printStackTrace();
            }

         
    }

    public double distSquare(double x0,double y0,double x1,double y1){
        return ( ((x1-x0)*(x1-x0))+((y1-y0)*(y1-y0)) );
    }

    private void findNormalVectors()
    {
        System.out.println("Min value = "+bpv.minValue +" Max value = "+bpv.maxValue);
        int[] arr=new int[1];
		for (int x = 1; x < img_width - 1; x++)
		{
			for (int y = 1; y < img_height - 1; y++)
			{
				bpv.dataimg.getPixel( x + 1, y,arr);
                                float X0= arr[0];//*bpv.verticalExaggeration;
                                bpv.dataimg.getPixel(x - 1, y,arr);
                                float X1 = arr[0];//*bpv.verticalExaggeration;
                                bpv.dataimg.getPixel(x, y+1,arr);
                                float Y0 = arr[0];//*bpv.verticalExaggeration;
                                bpv.dataimg.getPixel(x , y-1,arr);
                                float Y1 = arr[0];//*bpv.verticalExaggeration;
                                float Zx = (X0-X1)*bpv.verticalExaggeration;
                                float Zy = (Y0-Y1)*bpv.verticalExaggeration;

                                float mod = (float)Math.sqrt(Zx*Zx+Zy*Zy+60*60);
                                // maximum for Xd, Yd is: (MAX - 0) + (MAX - 0) + (MAX - 0) = 3 * MAX
                                float Xd=-Zx/mod;
                                float Yd=-Zy/mod;
                                //System.out.println("Xd= "+Xd+" Yd= "+Yd);
                                float Nx = Xd;
                                //System.out.println(Xd);
                                float Ny = Yd;
                                float Nz = 60/mod;
                                if (Nz < 0.0f)
                                Nz = 0.0f;
                                normalvectors[y * img_width + x][0] = Nx;
                                normalvectors[y * img_width + x][1] = Ny;
                                normalvectors[y * img_width + x][2] = Nz;
                                
			}
		}
	}

}
