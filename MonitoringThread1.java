package monitoringoffice;
//Source:
//  Creating a simple Chat Client1/Server Solution 
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html


import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitoringThread1 extends Thread
{

    private Socket socket = null;
    private MonitoringOffice client1 = null;
    private ObjectInputStream streamIn = null;

    public MonitoringThread1(MonitoringOffice _client1, Socket _socket)
    {
        client1 = _client1;
        socket = _socket;
        open();
        start();
    }

    public void open()
    {
        try
        {
            streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch (IOException ioe)
        {
            System.out.println("Error getting input stream: " + ioe);
            //client1.stop();
            client1.close();
        }
    }

    public void close()
    {
        try
        {
            if (streamIn != null)
            {
                streamIn.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error closing input stream: " + ioe);
        }
    }

    public void run()
    {
        while (true)
        {
            try
            {
                TrafficReport ob = (TrafficReport)streamIn.readObject();
                client1.handle(ob);
            }
            catch (IOException ioe)
            {
                System.out.println("Listening error: " + ioe.getMessage());
                //client1.stop();
                client1.close();
            } 
            catch (ClassNotFoundException ex) 
            {
                Logger.getLogger(MonitoringThread1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
