import java.util.ArrayList;
import java.util.Arrays;

public class Cache implements Constants
{
    private ArrayList<Image> images;
    public Cache ()
    {
        images = new ArrayList<Image>();
    }

    public synchronized void addImage(Image inputImg)
    {
        if(images.size()==20)
            images.remove(0);
        images.add(inputImg);
    }
    public synchronized Image getImage(double[] inputCenters, double [] inputCoords)
    {
        for(int i =0; i<images.size();i++)
        {
            if((Arrays.equals(inputCenters,images.get(i).getCenter()))&&(Arrays.equals(inputCoords,images.get(i).getCoeffs())))
                return images.get(i);
        }
        Image newImg = new Image(inputCenters,inputCoords);
        this.addImage(newImg);
        return newImg;
    }
    public String createImagesSrc()
    {
        String imagesSrc = new String();
        for(int i =0; i<images.size();i++)
        {
            imagesSrc = imagesSrc.concat(BEFOREIMG);
            imagesSrc = imagesSrc.concat(images.get(i).getImageBase64());
            imagesSrc = imagesSrc.concat(AFTERIMG);
        }
        return imagesSrc;
    }
    public String createCachePage()
    {
        String cacheHtml = new String();
        cacheHtml = cacheHtml.concat(BEGINCACHEHTML);
        cacheHtml = cacheHtml.concat(createImagesSrc());
        cacheHtml = cacheHtml.concat(ENDCACHEHTML);
        return cacheHtml;
    }
}
