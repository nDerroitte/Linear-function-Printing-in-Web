import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

public class RequestHandler extends Thread implements Constants
{
    private Socket clientSocket;
    private BufferedWriter out;
    private BufferedReader in;
    private Cache cache;

    public RequestHandler(Socket clientSocket, Cache cache)
    {
        try
        {
            // initialize the socket
            this.clientSocket = clientSocket;
            clientSocket.setSoTimeout(TIMEOUT);

            // create the buffers
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.cache = cache;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            // handle the request
            handle(getRequest());

            // close the buffers and the connection
            close();
        }
        catch(SocketTimeoutException e){}
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void close()
    {
        try
        {
            out.close();
            in.close();
            clientSocket.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private Vector<String[]> getRequest() throws IOException
    {
        Vector<String[]> request = new Vector<>(1);
        String line;
        boolean post;
        int contentLength = -1;

        // check post request
        line = in.readLine();
        request.add(line.split(" "));
        //System.out.println(line);
        if(line.contains(POST))
            post = true;
        else
            post = false;

        // retrieve and parse each line of the HTTP request
        while (((line = in.readLine()) != null))
        {
            String[] field = line.split(" ");
            request.add(field);

            //System.out.println(line);

            // retrieve the Content-Length header value
            if(field[0].equals("Content-Length:") && field.length > 1)
                contentLength = Integer.valueOf(field[1]);

            // retrieve the body of the POST request
            if(post && line.length() == 0)
            {
                if(contentLength < 0)
                {
                    request.get(0)[0] = LENGTH_REQUIRED_411;
                    return request;
                }

                char[] characters = new char[contentLength];
                for(int i=0; i<contentLength; i++)
                {
                    characters[i] = (char) in.read();
                    //System.out.println(characters[i]);
                }
                String[] body = new String[1];
                body[0] = new String(characters);
                //System.out.println(body[0]);
                request.add(body);
            }

            if (line.isEmpty())
                break;
        }

        //System.out.println("LENGTH" + contentLength);

        return request;
    }

    private void handle(Vector<String[]> request)
    {
        if(request.get(0)[0].equals(LENGTH_REQUIRED_411))
            reply(LENGTH_REQUIRED_411,CONTENT_TYPE_HTML,errorBody(LENGTH_REQUIRED_411));

        if(request.size() < 1)
        {
            reply(BAD_REQUEST_400,CONTENT_TYPE_HTML,errorBody(BAD_REQUEST_400));
            return;
        }

        if(request.get(0).length < 3)
        {
            reply(BAD_REQUEST_400,CONTENT_TYPE_HTML,errorBody(BAD_REQUEST_400));
            return;
        }

        if(!request.get(0)[2].equals(HTTP_VERSION))
        {
            reply(HTTP_VERSION_NOT_SUPPORTED_505,CONTENT_TYPE_HTML,errorBody(HTTP_VERSION_NOT_SUPPORTED_505));
            return;
        }

        switch (request.get(0)[0])
        {
            case GET:
                getHandler(request);
                break;
            case POST:
                postHandler(request);
                break;
            case HEAD:
                reply(METHOD_NOT_ALLOWED_405,CONTENT_TYPE_HTML,errorBody(METHOD_NOT_ALLOWED_405));
                break;
            case PUT:
                reply(METHOD_NOT_ALLOWED_405,CONTENT_TYPE_HTML,errorBody(METHOD_NOT_ALLOWED_405));
                break;
            case DELETE:
                reply(METHOD_NOT_ALLOWED_405,CONTENT_TYPE_HTML,errorBody(METHOD_NOT_ALLOWED_405));
                break;
            case TRACE:
                reply(METHOD_NOT_ALLOWED_405,CONTENT_TYPE_HTML,errorBody(METHOD_NOT_ALLOWED_405));
                break;
            case OPTIONS:
                reply(METHOD_NOT_ALLOWED_405,CONTENT_TYPE_HTML,errorBody(METHOD_NOT_ALLOWED_405));
                break;
            case CONNECT:
                reply(METHOD_NOT_ALLOWED_405,CONTENT_TYPE_HTML,errorBody(METHOD_NOT_ALLOWED_405));
                break;
            case PATCH:
                reply(METHOD_NOT_ALLOWED_405,CONTENT_TYPE_HTML,errorBody(METHOD_NOT_ALLOWED_405));
                break;
            default:
                reply(NOT_IMPLEMENTED_501,CONTENT_TYPE_HTML,errorBody(NOT_IMPLEMENTED_501));
                break;
        }
    }

    private void getHandler(Vector<String[]> request)
    {
        // redirection
        if(request.get(0)[1].equals(ROOT))
        {
            reply(SEE_OTHER_303,"Location: http://localhost:8006/graphView.html\r\n","");
            return;
        }

        // split the paths
        String[] path = request.get(0)[1].split("/");
        if(path.length != 2)
        {
            reply(NOT_FOUND_404,CONTENT_TYPE_HTML,errorBody(NOT_FOUND_404));
            return;
        }

        // extract the page
        String[] page = path[1].split("\\?");

        // extract the arguments
        String[] arguments;
        if(page.length > 1)
            arguments = page[1].split("&");
        else
            arguments = new String[0];

        switch(page[0])
        {
            case GRAPH_VIEW:
                String gvb;
                boolean loadPage = checkLoadPage(request);

                if(loadPage)
                    gvb = graphViewBody(arguments,true);
                else
                    gvb = graphViewBody(arguments, false);

                switch(gvb)
                {
                    case "fError":
                        reply(BAD_REQUEST_400,"","fError");
                        break;
                    case "xError":
                        reply(BAD_REQUEST_400,"","xError");
                        break;
                    case "yError":
                        reply(BAD_REQUEST_400,"","yError");
                        break;
                    case "nbArgError":
                        reply(BAD_REQUEST_400,"","nbArgError");
                        break;
                    default:
                        if(loadPage)
                            reply(OK_200,CONTENT_TYPE_HTML,gvb);
                        else
                            reply(OK_200,"",gvb);
                        break;
                }
                break;
            case VIEW_CACHE:
                reply(OK_200,CONTENT_TYPE_HTML,viewCacheBody(arguments));
                break;
            case FAVICON_ICO:
                reply(OK_200,"","");
                break;
            default:
                reply(NOT_FOUND_404,CONTENT_TYPE_HTML,errorBody(NOT_FOUND_404));
                break;
        }
    }

    private boolean checkLoadPage(Vector<String[]> request)
    {
        for (int i=0; i<request.size(); i++)
        {
            for(int j=0; j<request.get(i).length; j++)
            {
                //System.out.print(request.get(i)[j]);
                if(request.get(i)[j].contains("LoadImage"))
                    return false;
            }
            //System.out.println();
        }
        return true;
    }

    private void postHandler(Vector<String[]> request)
    {
        // check the arguments string
        String[] arguments;
        if(request.get(request.size()-1)[0].isEmpty())
        {
            reply(BAD_REQUEST_400,CONTENT_TYPE_HTML,errorBody(BAD_REQUEST_400));
            return;
        }

        // convert the arguments string
        String convert = request.get(request.size()-1)[0];
        try
        {
            convert = URLDecoder.decode(convert,"UTF-8");
            convert = convert.replaceAll(" ", "");
        }
        catch(Exception e)
        {
            reply(BAD_REQUEST_400,CONTENT_TYPE_HTML,errorBody(BAD_REQUEST_400));
            return;
        }

        System.out.println(convert);

        arguments = convert.split("&");
        reply(OK_200,CONTENT_TYPE_HTML,graphViewBody(arguments, true));
    }

    private double[] getCoeffs(String polynomial)
    {
        int maxDegree = 0;
        int plusIndex = 0;
        int minusIndex = 0;
        int shortestIndex = 0;
        ArrayList<String> terms = new ArrayList<String>();
        SortedMap<Integer, Double> coeffsMap = new TreeMap<Integer, Double>();

        while (true) {
            if (polynomial.charAt(0) == '+') {
                plusIndex = polynomial.indexOf('+', polynomial.indexOf('+') + 1);
                minusIndex = polynomial.indexOf('-');
            } else if (polynomial.charAt(0) == '-') {
                plusIndex = polynomial.indexOf('+');
                minusIndex = polynomial.indexOf('-', polynomial.indexOf('-') + 1);
            } else {
                plusIndex = polynomial.indexOf('+');
                minusIndex = polynomial.indexOf('-');
            }
            if (plusIndex > minusIndex) {
                if (minusIndex != -1)
                    shortestIndex = minusIndex;
                else
                    shortestIndex = plusIndex;
            } else if (minusIndex >= plusIndex) {
                if (plusIndex != -1)
                    shortestIndex = plusIndex;
                else
                    shortestIndex = minusIndex;
            }
            if (shortestIndex == -1) {
                terms.add(polynomial);
                break;
            }
            terms.add(polynomial.substring(0, shortestIndex));
            polynomial = polynomial.substring(shortestIndex);
        }
        String beforeX = new String();
        String afterX = new String();
        int indexX = 0;
        double currentCoeff = 0.0;
        int currentDegree = 0;
        for (int i = 0; i < terms.size(); i++) {
            indexX = terms.get(i).indexOf('x');
            if (indexX == -1) {
                currentCoeff = Double.parseDouble(terms.get(i));
                coeffsMap.put(0, currentCoeff + coeffsMap.getOrDefault(0, 0.0));
                continue;
            } else if (indexX == 0)
                currentCoeff = 1.0;
            else {
                beforeX = terms.get(i).substring(0, indexX);
                if (beforeX.charAt(0) == '+' && beforeX.length() == 1)
                    currentCoeff = 1.0;
                else if (beforeX.charAt(0) == '-' && beforeX.length() == 1)
                    currentCoeff = -1.0;
                else
                    currentCoeff = Double.parseDouble(beforeX);
            }
            afterX = terms.get(i).substring((indexX + 1));
            if (afterX.length() == 0)
                currentDegree = 1;
            else
                currentDegree = Integer.parseInt(afterX);

            coeffsMap.put(currentDegree, currentCoeff + coeffsMap.getOrDefault(currentDegree, 0.0));
        }
        maxDegree = coeffsMap.lastKey();
        double[] coeffs = new double[maxDegree + 1];
        for (int i = 0; i < maxDegree + 1; i++)
            coeffs[i] = coeffsMap.getOrDefault(maxDegree - i, 0.0);

        return coeffs;
    }

    private boolean isADigit(char character)
    {
        switch (character)
        {
            case '0':
                return true;
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
        }
        return false;
    }

    private void reply(String response, String headers, String body)
    {
        try
        {
            //System.out.println(HTTP_VERSION + " " + response + headers + CONTENT_LENGTH + body.length() + "\r\n" + "\r\n" + body);

            out.write(HTTP_VERSION + " " + response);
            out.write(headers);
            out.write(CONTENT_LENGTH + body.length() + "\r\n");
            out.write("\r\n");
            out.write(body);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private String errorBody(String error)
    {
        return "<TITLE>Polynomial visualizer : error</TITLE>" + "<h1>" + error + "<h1>";
    }

    private boolean checkFunction(String function)
    {
        for(int i=0; i<function.length(); i++)
        {
            switch(function.charAt(i))
            {
                case '+':
                    break;
                case '-':
                    break;
                case '.':
                    break;
                case VAR:
                    break;
                default:
                    if(!isADigit(function.charAt(i)))
                        return false;
                    else
                        break;
            }
        }
        return true;
    }

    private String graphViewBody(String[] arguments, boolean loadPage)
    {
        Image graph = new Image();

        //System.out.println("LOAD PAGE " + loadPage);

        if(arguments.length > 0)
        {
            if(arguments.length != 3)
                return "nbArgError";

            // retrieve the function
            double[] coeffs;
            if(arguments[0].contains("function="))
            {
                String[] assets = arguments[0].split("=");

                if(assets.length < 2)
                    return "fError";

                if(!checkFunction(assets[1]))
                    return "fError";

                try
                {
                    coeffs = getCoeffs(assets[1]);
                }
                catch(Exception e)
                {
                    return "fError";
                }
            }
            else
                return "fError";

            // retrieve centerX
            double[] center = new double[2];
            if(arguments[1].contains("centerX="))
            {
                String[] assets = arguments[1].split("=");
                if(assets.length < 2)
                    return "xError";

                try
                {
                    center[0] = Double.valueOf(assets[1]);
                }
                catch(Exception e)
                {
                    return "xError";
                }
            }
            else
                return "xError";

            // retrieve centerY
            if(arguments[2].contains("centerY="))
            {
                String[] assets = arguments[2].split("=");
                if(assets.length < 2)
                    return "yError";

                try
                {
                    center[1] = Double.valueOf(assets[1]);
                }
                catch(Exception e)
                {
                    return "yError";
                }
            }
            else
                return "yError";

            graph = cache.getImage(center,coeffs);
            if(!loadPage)
            {
                JSONObject jsonGraph = graph.createJson();
                return jsonGraph.getJson();
            }

            return "<TITLE>Polynomial visualizer</TITLE>" + GRAPH_VIEW_HTML_BEGIN + graph.getImageBase64() + GRAPH_VIEW_HTML_END;
        }

        return "<TITLE>Polynomial visualizer</TITLE>" + GRAPH_VIEW_HTML;
    }

    private String viewCacheBody(String[] arguments)
    {
        return cache.createCachePage();
    }
}
