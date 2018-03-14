public class JSONObject
{
    private double coeffs [];
    private double center [];
    private String image;
    private String Json;

    public JSONObject( double [] inputCoeffs, double [] inputCenter, String inputImg)
    {
        this.coeffs = inputCoeffs;
        this.center = inputCenter;
        this.image  = inputImg;

        this.Json = new String();
        Json = this.Json.concat("[{\"image\":\""+inputImg+"\"}]");
    }

    public String getJson()
    {
        return Json;
    }
}
