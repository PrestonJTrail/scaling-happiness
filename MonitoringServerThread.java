package monitoringoffice;
//Source:
//  Creating a simple Chat Client/Server Solution 
//  http://pirate.shu.edu/~wachsmut/Teaching/CSAS2214/Virtual/Lectures/chat-client-server.html


import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitoringServerThread extends Thread
{
    TrafficReport trafficData;
    private MonitoringServer server = null;
    private Socket socket = null;
    private int ID = -1;
    private ObjectInputStream streamIn = null;
    private ObjectOutputStream streamOut = null;

    public MonitoringServerThread(MonitoringServer _server, Socket _socket)
    {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
    }

    public void send(TrafficReport data)
    {
        try
        {
            streamOut.writeObject(data);
            streamOut.flush();
        }
        catch (IOException ioe)
        {
            System.out.println(ID + " ERROR sending: " + ioe.getMessage());
            server.remove(ID);
            stop();
        }
    }

    public int getID()
    {
        return ID;
    }

    public void run()
    {
        System.out.println("Server Thread " + ID + " running.");
        while (true)
        {
            try
            {
                TrafficReport ob = (TrafficReport)streamIn.readObject();                
                server.handle(ob);
            }
            catch (IOException ioe)
            {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MonitoringServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void open() throws IOException
    {
       
        streamOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        streamOut.flush();
         streamIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public void close() throws IOException
    {
        if (socket != null)
        {
            socket.close();
        }
        if (streamIn != null)
        {
            streamIn.close();
        }
        if (streamOut != null)
        {
            streamOut.close();
        }
    }
}
