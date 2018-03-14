import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer implements Constants
{
    public static void main(String[] args) throws Exception
    {
        // initialize the thread pool
        ExecutorService executor;
        if(args.length > 0)
        {
            executor = Executors.newFixedThreadPool(Integer.parseInt(args[0]));
        }
        else
        {
            executor = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL);
        }

        // start the server
        Cache cache = new Cache();
        ServerSocket serverSocket = new ServerSocket(PORT);
        while(true)
        {
            // create a new worker thread for each new client
            Socket clientSocket = serverSocket.accept();
            executor.execute(new RequestHandler(clientSocket,cache));
        }
    }
}
