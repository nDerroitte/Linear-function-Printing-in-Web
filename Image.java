/**
 *
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;



public class Image implements Constants
{
    private int height;
    private int width ;
    private double [] coeffs ;
    private double [] center ;
    private double [] borderX;
    private double [] borderY;
    private BufferedImage bImage;
    private String imageBase64;

    public Image(){}

    public Image ( double[] inputCenter, double [] inputCoeffs)
    {
        this.center  = new double[2];
        this.borderX = new double[2];
        this.borderY = new double[2];
        this.coeffs  = new double[inputCoeffs.length];

        this.height = WINDOW_HEIGHT;
        this.width  = WINDOW_WIDTH;

        this.coeffs = inputCoeffs;
        this.center = inputCenter;

        this.borderX[0] = inputCenter[0]-(MAX_UNIT_X/2);
        this.borderX[1] = inputCenter[0]+(MAX_UNIT_X/2);
        this.borderY[0] = inputCenter[1]-(MAX_UNIT_Y/2);
        this.borderY[1] = inputCenter[1]+(MAX_UNIT_Y/2);

        bImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Color White = new Color(255, 255, 255); // Color white
        int rgbW = White.getRGB();
        Color Red = new Color(255, 0, 0); // Color red
        int rgbR = Red.getRGB();

        int yPxFunction;
        int lastPx=-1;
        int tmp;

        for (int x = 0; x < WINDOW_WIDTH; x++)
        {
            for (int y = 0; y < WINDOW_HEIGHT; y++)
                bImage.setRGB(x, y, rgbW);

            yPxFunction=polynomial(coeffs,x);
            tmp = yPxFunction;
            if(lastPx !=-1 &&(lastPx<1000&&lastPx>-500) &&(yPxFunction<1000&&yPxFunction>-500) && lastPx!=yPxFunction )
            {
                if (lastPx > yPxFunction)
                    while (lastPx != yPxFunction && yPxFunction<400) {
                        if (yPxFunction < WINDOW_HEIGHT && yPxFunction > 0)
                            bImage.setRGB(x, yPxFunction, rgbR);
                        yPxFunction++;
                    }
                else if (lastPx <= yPxFunction) {
                    while (lastPx != yPxFunction && yPxFunction>0) {
                        if (yPxFunction < WINDOW_HEIGHT && yPxFunction > 0)
                            bImage.setRGB(x, yPxFunction, rgbR);
                        yPxFunction--;
                    }
                }
            }
            else
            {
                if( yPxFunction<WINDOW_HEIGHT && yPxFunction>0 )
                    bImage.setRGB(x, yPxFunction, rgbR);
            }
            lastPx=tmp;
        }

        drawAxis();

        //Transformation to Base64
        try
        {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", os);
            this.imageBase64 = Base64.getEncoder().encodeToString(os.toByteArray());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private void drawAxis ()
    {
        Color Black = new Color(0, 0, 0); // Color black
        int rgbB = Black.getRGB();
        xAxis(rgbB);
        YAxis(rgbB);
    }
    private void xAxis (int rgb)
    {
        if(!(((borderY[0] + MAX_UNIT_Y) < 0)||(borderY[1] - MAX_UNIT_Y)>0 )) //There is a axis to print
        {
            int AxisPx = (int) ((0+borderY[1]) * PX_PER_UNIT);
            int firstUnitPx = (int) (-(Math.abs(borderX[0]) + (int)Math.abs(borderX[0])) * PX_PER_UNIT);

            for (int i=0; i < WINDOW_WIDTH ; i++)
            {
                if( AxisPx<WINDOW_WIDTH && AxisPx-2>0 ) {

                    if ((i + firstUnitPx) % PX_PER_UNIT == 0) {
                        bImage.setRGB(i, AxisPx - 1, rgb);
                        bImage.setRGB(i, AxisPx - 2, rgb);
                    }
                    bImage.setRGB(i, AxisPx, rgb);
                }
             }
        }
    }
    private void YAxis (int rgb)
    {
        if(!(((borderX[0] + MAX_UNIT_X) < 0)||(borderX[1]-MAX_UNIT_X)>0)) //There is a axis to print
        {
            int AxisPx = (int) ((0-borderX[0]) * PX_PER_UNIT);
            int firstUnitPx = (int) ((Math.abs(borderY[0]) - (int)Math.abs(borderY[0])) * PX_PER_UNIT);
            for (int i=0; i<WINDOW_HEIGHT; i++)
            {
                if( AxisPx+2<WINDOW_HEIGHT && AxisPx>0 )
                {
                    if ((i+firstUnitPx) % PX_PER_UNIT==0)
                    {
                        bImage.setRGB(AxisPx+1, i, rgb);
                        bImage.setRGB(AxisPx+2, i, rgb);
                    }
                bImage.setRGB(AxisPx, i, rgb);
                }

            }
        }
    }
    private int polynomial(double [] coeffs,int xPx)
    {
        double result= 0;
        double realX = borderX[0] + ((double)xPx/ PX_PER_UNIT );
        for (int i =0; i<coeffs.length; i++)
            result += coeffs[i] *  Math.pow(realX,(double)coeffs.length-1-i);

        return (int)((borderY[1]-result) * PX_PER_UNIT);
    }
    public String getImageBase64()
    {
        return imageBase64;
    }

    public JSONObject createJson()
    {
        JSONObject result = new JSONObject(coeffs,center,imageBase64);
        return result;
    }
    public double[] getCoeffs(){
        return this.coeffs;
    }
    public double[] getCenter(){
        return this.center;
    }
}
