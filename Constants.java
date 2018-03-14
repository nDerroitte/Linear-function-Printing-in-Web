
public interface Constants
{
    // server parameters
    int DEFAULT_THREAD_POOL = 10;
    int PORT = 8006;
    int TIMEOUT = 25000;  // 25 seconds of timeout

    // version
    String HTTP_VERSION = "HTTP/1.1";

    // headers
    String CONTENT_TYPE_HTML = "Content-Type: text/html\r\n";
    String CONTENT_LENGTH = "Content-Length: ";

    // methods
    String GET = "GET";
    String POST = "POST";
    String HEAD = "HEAD";
    String PUT = "PUT";
    String DELETE = "DELETE";
    String TRACE = "TRACE";
    String OPTIONS = "OPTIONS";
    String CONNECT = "CONNECT";
    String PATCH = "PATCH";

    // replies
    String OK_200 = "200 OK\r\n";
    String SEE_OTHER_303 = "303 See Other\r\n";
    String BAD_REQUEST_400 = "400 Bad Request\r\n";
    String NOT_FOUND_404 = "404 Not Found\r\n";
    String METHOD_NOT_ALLOWED_405 = "405 Method Not Allowed\r\n";
    String LENGTH_REQUIRED_411 = "411 Length Required\r\n";
    String NOT_IMPLEMENTED_501 = "501 Not Implemented\r\n";
    String HTTP_VERSION_NOT_SUPPORTED_505= "505 HTTP Version Not Supported\r\n";

    // pages
    String ROOT = "/";
    String FAVICON_ICO = "favicon.ico";
    String GRAPH_VIEW = "graphView.html";
    String VIEW_CACHE = "viewCache.html";

    // html pages
    String GRAPH_VIEW_HTML = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<!--Head info-->\n" +
            "    <head>\n" +
            "        <meta charset=\"utf-8\" />\n" +
            "        <title>Polynomial Visualizer</title>\n" +
            "        <style>\n" +
            "        body\n" +
            "        {\n" +
            "            text-align: center;\n" +
            "            font-family: \"Raleway\",sans-serif;\n" +
            "            background-color: #6C8EAC;\n" +
            "            color : white;\n" +
            "        }\n" +
            "        #NoJS\n" +
            "        {\n" +
            "          background-color: red;\n" +
            "          border: 2px solid black;\n" +
            "          position: fixed;\n" +
            "          top: 0;\n" +
            "          left: 0;\n" +
            "          z-index: 999;\n" +
            "          width: 100%;\n" +
            "          height: 23px;\n" +
            "        }\n" +
            "        #Mainimage\n" +
            "        {\n" +
            "          border: 1px solid black;\n" +
            "          text-align: center;\n" +
            "        }\n" +
            "        #title\n" +
            "        {\n" +
            "          text-align: center;\n" +
            "          font-family: \"Raleway\",sans-serif;\n" +
            "        }\n" +
            "        #Submission\n" +
            "        {\n" +
            "          padding: 5px;\n" +
            "          border: 1px solid black;\n" +
            "          display: none;\n" +
            "          background-color: grey;\n" +
            "        }\n" +
            "        #SubmissionNOSCRIPT\n" +
            "        {\n" +
            "          padding: 5px;\n" +
            "          border: 1px solid black;\n" +
            "          display: inline-block;\n" +
            "          background-color: grey;\n" +
            "        }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "      <header>\n" +
            "        <noscript>\n" +
            "          <div id =\"NoJS\">\n" +
            "            JavaScript is disabled.\n" +
            "          </div>\n" +
            "          <br/>\n" +
            "        </noscript>\n" +
            "        <h1 id=\"title\"> Polynomial Visualizer </h1>\n" +
            "      <header/>\n" +
            "        <p id = \"Informations\">\n" +
            "          Center : <span id = \"CenterValue\">(0,0)</span><br/>\n" +
            "          Function : <span id = \"fValue\">none</span><br/>\n" +
            "         </p>\n" +
            "        <div id = \"img\">\n" +
            "        <img id = \"Mainimage\" onmousemove=\"showCoords(event)\" onmouseout=\"clearCoor()\" onclick=\"reCenter(event)\" src=\"data:image/png;base64,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\" />\n" +
            "        <p id = \"Coord\"></p>  </div>\n" +
            "        <div id = \"Submission\">\n" +
            "        Function : <input id=\"f\" name=\"function\"> <br/>\n" +
            "        Center : (<input id=\"centerX\" name=\"centerX\" size = \"5\">,<input id=\"centerY\" name=\"centerY\" size = \"5\">) as (X,Y)       <br/><br/>\n" +
            "        <button type=\"button\" onclick=\"submission()\">Submit</button>\n" +
            "        <button type=\"button\" onclick=\"Example()\">Example</button>\n" +
            "        </div>\n" +
            "        <script>\n" +
            "        \t document.getElementById('Submission').style.display='inline-block';\n" +
            "        </script>\n" +
            "        <noscript >\n" +
            "          <form action=\"http://localhost:8006\" method=\"post\" id=\"SubmissionNOSCRIPT\">\n" +
            "          Function : <input  name=\"function\" id=\"f\"> <br/>\n" +
            "         Center : (<input id=\"centerX\" size = \"5\" name=\"centerX\">,<input id=\"centerY\" size = \"5\" name=\"centerY\">) as (X,Y)       <br/><br/>\n" +
            "          <button type=\"submit\">Submit</button>\n" +
            "        </form>\n" +
            "        <p style =\"text-align:left;\">\n" +
            "           Note that you are using the website without Javascript. <br/>\n" +
            "           Therfore, the request may be longer and you don't have access to all the dynamic options of this website! </p>\n" +
            "        </noscript>\n" +
            "      <script>\n" +
            "      var centerX = 0;\n" +
            "      var centerY = 0;\n" +
            "\n" +
            "      var f = null;\n" +
            "      var coeffs = [];\n" +
            "\n" +
            "      function getCoords(event){\n" +
            "        var coordsObject = {};\n" +
            "\n" +
            "        var x = event.pageX;\n" +
            "        var y = event.pageY;\n" +
            "        var image = document.getElementById(\"Mainimage\");\n" +
            "\n" +
            "        var realXPX = x - image.offsetLeft;\n" +
            "        var realYPX = y - image.offsetTop;\n" +
            "        coordsObject[\"x\"] = (centerX)-5+(realXPX/40);\n" +
            "        coordsObject[\"y\"] = Number(centerY)+5-(realYPX/40);\n" +
            "        return coordsObject;\n" +
            "      }\n" +
            "      function showCoords(event) {\n" +
            "\n" +
            "        var coords = getCoords(event);\n" +
            "        var coor = \"(\" + coords.x.toFixed(3) + \" , \" + coords.y.toFixed(3) + \")\";\n" +
            "        document.getElementById(\"Coord\").textContent = coor;\n" +
            "      }\n" +
            "\n" +
            "      function clearCoor() {\n" +
            "        document.getElementById(\"Coord\").textContent = \"\";\n" +
            "      }\n" +
            "\n" +
            "      function reCenter(event){\n" +
            "        var coords  = getCoords(event);\n" +
            "        centerX = coords.x;\n" +
            "        centerY = coords.y;\n" +
            "        if(f==null)\n" +
            "          f = \"0\";\n" +
            "        updateGraph();\n" +
            "        var coor = \"(\" + centerX.toFixed(3) + \" , \" + centerY.toFixed(3) + \")\";\n" +
            "        document.getElementById(\"CenterValue\").textContent = coor;\n" +
            "      }\n" +
            "      function submission() {\n" +
            "        document.getElementById(\"f\").style.backgroundColor =\"white\";\n" +
            "        document.getElementById(\"centerX\").style.backgroundColor =\"white\";\n" +
            "        document.getElementById(\"centerY\").style.backgroundColor =\"white\";\n" +
            "\n" +
            "        centerX = document.getElementById(\"centerX\").value;\n" +
            "        centerY = document.getElementById(\"centerY\").value;\n" +
            "\n" +
            "        f = document.getElementById(\"f\").value;\n" +
            "\n" +
            "        if(!checkFunction(f))\n" +
            "        {\n" +
            "          document.getElementById(\"f\").style.backgroundColor =\"red\";\n" +
            "          return false;\n" +
            "        }\n" +
            "        else\n" +
            "          f = f.replace(/\\s/g, '');\n" +
            "\n" +
            "        if(!checkCenter(centerX))\n" +
            "        {\n" +
            "          document.getElementById(\"centerX\").style.backgroundColor =\"red\";\n" +
            "          return false;\n" +
            "        }\n" +
            "        if(!checkCenter(centerY))\n" +
            "        {\n" +
            "          document.getElementById(\"centerY\").style.backgroundColor =\"red\";\n" +
            "          return false;\n" +
            "        }\n" +
            "        else\n" +
            "        {\n" +
            "          centerX = centerX.replace(/\\s/g, '');\n" +
            "          centerY = centerY.replace(/\\s/g, '');\n" +
            "        }\n" +
            "        updateGraph();\n" +
            "        var coor = \"(\" + centerX + \" , \" + centerY + \")\";\n" +
            "        document.getElementById(\"CenterValue\").textContent = coor;\n" +
            "        document.getElementById(\"fValue\").textContent = f;\n" +
            "      }\n" +
            "      function Example(){\n" +
            "        alert( \"Function : 2x2 + 7x + 9 \\n Center : (0,0)\\nThe function can only contain the carachter [0-9], ., +, - and x.\\nThe centers can only contain the carachter [0-9], ., - and x.\\nNote that the decimal are separated by a point and not by a coma.\\nIf you want to change the center of a function, you can also click directly on the image and it will set the center at your mouse position.\")\n" +
            "      }\n" +
            "      function checkFunction(f){\n" +
            "        if(f==\"\")\n" +
            "        {\n" +
            "          alert(\"Wrong argument! Please fill the function field.\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        var result = f.replace(/[\\d.\\-x\\+ ]/g, '');\n" +
            "        if(result != '')\n" +
            "        {\n" +
            "          alert(\"Wrong argument! See the example button on the main page.\\n\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "      }\n" +
            "      function checkCenter(center){\n" +
            "        if(center==\"\")\n" +
            "        {\n" +
            "          alert(\"Wrong argument! Please fill the centers field.\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        var result = center.replace(/[\\d.\\- ]/g, '');\n" +
            "        if(result != '')\n" +
            "        {\n" +
            "          alert(\"Wrong argument! See the example button on the main page.\\n\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "      }\n" +
            "\n" +
            "      function updateGraph(){\n" +
            "        var xmlHttpR = new XMLHttpRequest();\n" +
            "        var url = \"/graphView.html\";\n" +
            "        url = url.concat(\"?function=\");\n" +
            "      \turl = url.concat(f);\n" +
            "      \turl = url.concat(\"&centerX=\");\n" +
            "      \turl = url.concat(centerX);\n" +
            "        url = url.concat(\"&centerY=\");\n" +
            "      \turl = url.concat(centerY);\n" +
            "        xmlHttpR.open('GET', url, true);\n" +
            "        xmlHttpR.setRequestHeader(\"Content-Type\",\"LoadImage\");\n" +
            "        xmlHttpR.onreadystatechange = function() {\n" +
            "    \t\t\tif (xmlHttpR.readyState === XMLHttpRequest.DONE && xmlHttpR.status === 200) {\n" +
            "            var reply = JSON.parse(xmlHttpR.responseText);\n" +
            "            var imageSrc = \"data:image/png;base64,\"+ reply[0].image;\n" +
            "            document.getElementById(\"Mainimage\").src = imageSrc;\n" +
            "          }\n" +
            "          if (xmlHttpR.readyState === XMLHttpRequest.DONE && xmlHttpR.status === 400) {\n" +
            "            var reply = xmlHttpR.responseText;\n" +
            "            if(reply==\"fError\")\n" +
            "              document.getElementById(\"f\").style.backgroundColor =\"red\";\n" +
            "            else if(reply==\"xError\")\n" +
            "              document.getElementById(\"centerX\").style.backgroundColor =\"red\";\n" +
            "            else if(reply==\"yError\")\n" +
            "              document.getElementById(\"centerY\").style.backgroundColor =\"red\";\n" +
            "            alert(\"Wrong argument! See the example button on the main page.\\n\");\n" +
            "          }\n" +
            "        }\n" +
            "      xmlHttpR.send(null);\n" +
            "    }\n" +
            "\n" +
            "</script>\n" +
            "    </body>\n" +
            "\n" +
            "</html>\n";
    String BEFOREIMG = "<div class = element>\n" +
            "            <img class = \"img\" src=\"data:image/png;base64,";

    String AFTERIMG = "\" />\n" +
            "          </div>\n" +
            "          ";

    String BEGINCACHEHTML = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<!--Head info-->\n" +
            "    <head>\n" +
            "        <meta charset=\"utf-8\" />\n" +
            "        <title>Polynomial Visualizer cache</title>\n" +
            "        <style>\n" +
            "        body\n" +
            "        {\n" +
            "            text-align: center;\n" +
            "            font-family: \"Raleway\",sans-serif;\n" +
            "            background-color: #6C8EAC;\n" +
            "            color : white;\n" +
            "        }\n" +
            "        .img\n" +
            "        {\n" +
            "          border: 1px solid black;\n" +
            "          text-align: center;\n" +
            "        }\n" +
            "        #content\n" +
            "        {\n" +
            "          display: flex;\n" +
            "          flex-wrap: wrap;\n" +
            "          flex-direction: row;\n" +
            "          justify-content: space-around;\n" +
            "          align-items: center;\n" +
            "        }\n" +
            "        </style>\n" +
            "        <body>\n" +
            "          <h1> Polynomial Visualizer cache </h1>\n" +
            "          <div id=\"content\">\n" +
            "            ";

    String ENDCACHEHTML ="</div>\n" +
            "</body>\n" +
            "</html>";
    String GRAPH_VIEW_HTML_BEGIN= "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<!--Head info-->\n" +
            "    <head>\n" +
            "        <meta charset=\"utf-8\" />\n" +
            "        <title>Polynomial Visualizer</title>\n" +
            "        <style>\n" +
            "        body\n" +
            "        {\n" +
            "            text-align: center;\n" +
            "            font-family: \"Raleway\",sans-serif;\n" +
            "            background-color: #6C8EAC;\n" +
            "            color : white;\n" +
            "        }\n" +
            "        #NoJS\n" +
            "        {\n" +
            "          background-color: red;\n" +
            "          border: 2px solid black;\n" +
            "          position: fixed;\n" +
            "          top: 0;\n" +
            "          left: 0;\n" +
            "          z-index: 999;\n" +
            "          width: 100%;\n" +
            "          height: 23px;\n" +
            "        }\n" +
            "        #Mainimage\n" +
            "        {\n" +
            "          border: 1px solid black;\n" +
            "          text-align: center;\n" +
            "        }\n" +
            "        #title\n" +
            "        {\n" +
            "          text-align: center;\n" +
            "          font-family: \"Raleway\",sans-serif;\n" +
            "        }\n" +
            "        #Submission\n" +
            "        {\n" +
            "          padding: 5px;\n" +
            "          border: 1px solid black;\n" +
            "          display: none;\n" +
            "          background-color: grey;\n" +
            "        }\n" +
            "        #SubmissionNOSCRIPT\n" +
            "        {\n" +
            "          padding: 5px;\n" +
            "          border: 1px solid black;\n" +
            "          display: inline-block;\n" +
            "          background-color: grey;\n" +
            "        }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "      <header>\n" +
            "        <noscript>\n" +
            "          <div id =\"NoJS\">\n" +
            "            JavaScript is disabled.\n" +
            "          </div>\n" +
            "          <br/>\n" +
            "        </noscript>\n" +
            "        <h1 id=\"title\"> Polynomial Visualizer </h1>\n" +
            "      <header/>\n" +
            "        <p id = \"Informations\">\n" +
            "          Center : <span id = \"CenterValue\">(0,0)</span><br/>\n" +
            "          Function : <span id = \"fValue\">none</span><br/>\n" +
            "         </p>\n" +
            "        <div id = \"img\">\n" +
            "        <img id = \"Mainimage\" onmousemove=\"showCoords(event)\" onmouseout=\"clearCoor()\" onclick=\"reCenter(event)\" src=\"data:image/png;base64,";
    String GRAPH_VIEW_HTML_END ="\" />\n" +
            "        <p id = \"Coord\"></p>  </div>\n" +
            "        <div id = \"Submission\">\n" +
            "        Function : <input id=\"f\" name=\"function\"> <br/>\n" +
            "        Center : (<input id=\"centerX\" name=\"centerX\" size = \"5\">,<input id=\"centerY\" name=\"centerY\" size = \"5\">) as (X,Y)       <br/><br/>\n" +
            "        <button type=\"button\" onclick=\"submission()\">Submit</button>\n" +
            "        <button type=\"button\" onclick=\"Example()\">Example</button>\n" +
            "        </div>\n" +
            "        <script>\n" +
            "        \t document.getElementById('Submission').style.display='inline-block';\n" +
            "        </script>\n" +
            "        <noscript >\n" +
            "          <form action=\"http://localhost:8006\" method=\"post\" id=\"SubmissionNOSCRIPT\">\n" +
            "          Function : <input  name=\"function\" id=\"f\"> <br/>\n" +
            "         Center : (<input id=\"centerX\" size = \"5\" name=\"centerX\">,<input id=\"centerY\" size = \"5\" name=\"centerY\">) as (X,Y)       <br/><br/>\n" +
            "          <button type=\"submit\">Submit</button>\n" +
            "        </form>\n" +
            "        <p style =\"text-align:left;\">\n" +
            "           Note that you are using the website without Javascript. <br/>\n" +
            "           Therfore, the request may be longer and you don't have access to all the dynamic options of this website! </p>\n" +
            "        </noscript>\n" +
            "      <script>\n" +
            "      var centerX = 0;\n" +
            "      var centerY = 0;\n" +
            "\n" +
            "      var f = null;\n" +
            "      var coeffs = [];\n" +
            "\n" +
            "      function getCoords(event){\n" +
            "        var coordsObject = {};\n" +
            "\n" +
            "        var x = event.pageX;\n" +
            "        var y = event.pageY;\n" +
            "        var image = document.getElementById(\"Mainimage\");\n" +
            "\n" +
            "        var realXPX = x - image.offsetLeft;\n" +
            "        var realYPX = y - image.offsetTop;\n" +
            "        coordsObject[\"x\"] = (centerX)-5+(realXPX/40);\n" +
            "        coordsObject[\"y\"] = Number(centerY)+5-(realYPX/40);\n" +
            "        return coordsObject;\n" +
            "      }\n" +
            "      function showCoords(event) {\n" +
            "\n" +
            "        var coords = getCoords(event);\n" +
            "        var coor = \"(\" + coords.x.toFixed(3) + \" , \" + coords.y.toFixed(3) + \")\";\n" +
            "        document.getElementById(\"Coord\").textContent = coor;\n" +
            "      }\n" +
            "\n" +
            "      function clearCoor() {\n" +
            "        document.getElementById(\"Coord\").textContent = \"\";\n" +
            "      }\n" +
            "\n" +
            "      function reCenter(event){\n" +
            "        var coords  = getCoords(event);\n" +
            "        centerX = coords.x;\n" +
            "        centerY = coords.y;\n" +
            "        if(f==null)\n" +
            "          f = \"0\";\n" +
            "        updateGraph();\n" +
            "        var coor = \"(\" + centerX.toFixed(3) + \" , \" + centerY.toFixed(3) + \")\";\n" +
            "        document.getElementById(\"CenterValue\").textContent = coor;\n" +
            "      }\n" +
            "      function submission() {\n" +
            "        document.getElementById(\"f\").style.backgroundColor =\"white\";\n" +
            "        document.getElementById(\"centerX\").style.backgroundColor =\"white\";\n" +
            "        document.getElementById(\"centerY\").style.backgroundColor =\"white\";\n" +
            "\n" +
            "        centerX = document.getElementById(\"centerX\").value;\n" +
            "        centerY = document.getElementById(\"centerY\").value;\n" +
            "\n" +
            "        f = document.getElementById(\"f\").value;\n" +
            "\n" +
            "        if(!checkFunction(f))\n" +
            "        {\n" +
            "          document.getElementById(\"f\").style.backgroundColor =\"red\";\n" +
            "          return false;\n" +
            "        }\n" +
            "        else\n" +
            "          f = f.replace(/\\s/g, '');\n" +
            "\n" +
            "        if(!checkCenter(centerX))\n" +
            "        {\n" +
            "          document.getElementById(\"centerX\").style.backgroundColor =\"red\";\n" +
            "          return false;\n" +
            "        }\n" +
            "        if(!checkCenter(centerY))\n" +
            "        {\n" +
            "          document.getElementById(\"centerY\").style.backgroundColor =\"red\";\n" +
            "          return false;\n" +
            "        }\n" +
            "        else\n" +
            "        {\n" +
            "          centerX = centerX.replace(/\\s/g, '');\n" +
            "          centerY = centerY.replace(/\\s/g, '');\n" +
            "        }\n" +
            "        updateGraph();\n" +
            "        var coor = \"(\" + centerX + \" , \" + centerY + \")\";\n" +
            "        document.getElementById(\"CenterValue\").textContent = coor;\n" +
            "        document.getElementById(\"fValue\").textContent = f;\n" +
            "      }\n" +
            "      function Example(){\n" +
            "        alert( \"Function : 2x2 + 7x + 9 \\n Center : (0,0)\\nThe function can only contain the carachter [0-9], ., +, - and x.\\nThe centers can only contain the carachter [0-9], ., - and x.\\nNote that the decimal are separated by a point and not by a coma.\\nIf you want to change the center of a function, you can also click directly on the image and it will set the center at your mouse position.\")\n" +
            "      }\n" +
            "      function checkFunction(f){\n" +
            "        if(f==\"\")\n" +
            "        {\n" +
            "          alert(\"Wrong argument! Please fill the function field.\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        var result = f.replace(/[\\d.\\-x\\+ ]/g, '');\n" +
            "        if(result != '')\n" +
            "        {\n" +
            "          alert(\"Wrong argument! See the example button on the main page.\\n\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "      }\n" +
            "      function checkCenter(center){\n" +
            "        if(center==\"\")\n" +
            "        {\n" +
            "          alert(\"Wrong argument! Please fill the centers field.\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        var result = center.replace(/[\\d.\\- ]/g, '');\n" +
            "        if(result != '')\n" +
            "        {\n" +
            "          alert(\"Wrong argument! See the example button on the main page.\\n\");\n" +
            "          return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "      }\n" +
            "\n" +
            "      function updateGraph(){\n" +
            "        var xmlHttpR = new XMLHttpRequest();\n" +
            "        var url = \"/graphView.html\";\n" +
            "        url = url.concat(\"?function=\");\n" +
            "      \turl = url.concat(f);\n" +
            "      \turl = url.concat(\"&centerX=\");\n" +
            "      \turl = url.concat(centerX);\n" +
            "        url = url.concat(\"&centerY=\");\n" +
            "      \turl = url.concat(centerY);\n" +
            "        xmlHttpR.open('GET', url, true);\n" +
            "        xmlHttpR.setRequestHeader(\"Content-Type\",\"LoadImage\");\n" +
            "        xmlHttpR.onreadystatechange = function() {\n" +
            "    \t\t\tif (xmlHttpR.readyState === XMLHttpRequest.DONE && xmlHttpR.status === 200) {\n" +
            "            var reply = JSON.parse(xmlHttpR.responseText);\n" +
            "            var imageSrc = \"data:image/png;base64,\"+ reply[0].image;\n" +
            "            document.getElementById(\"Mainimage\").src = imageSrc;\n" +
            "          }\n" +
            "          if (xmlHttpR.readyState === XMLHttpRequest.DONE && xmlHttpR.status === 400) {\n" +
            "            var reply = xmlHttpR.responseText;\n" +
            "            if(reply==\"fError\")\n" +
            "              document.getElementById(\"f\").style.backgroundColor =\"red\";\n" +
            "            else if(reply==\"xError\")\n" +
            "              document.getElementById(\"centerX\").style.backgroundColor =\"red\";\n" +
            "            else if(reply==\"yError\")\n" +
            "              document.getElementById(\"centerY\").style.backgroundColor =\"red\";\n" +
            "            alert(\"Wrong argument! See the example button on the main page.\\n\");\n" +
            "          }\n" +
            "        }\n" +
            "      xmlHttpR.send(null);\n" +
            "    }\n" +
            "\n" +
            "</script>\n" +
            "    </body>\n" +
            "\n" +
            "</html>\n";


    // other
    char VAR = 'x';
    int WINDOW_HEIGHT = 400;
    int WINDOW_WIDTH = 400;
    int MAX_UNIT_X = 10;
    int MAX_UNIT_Y = 10;
    int PX_PER_UNIT = 40;
}