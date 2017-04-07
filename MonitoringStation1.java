package monitoringoffice;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.Object;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.util.Arrays;

/**
 * 
 *
 * @author Preston J. Trail
 */
public class MonitoringStation1 extends JFrame implements ActionListener, KeyListener
{
    SpringLayout springLayout;
    private JLabel lblTitle, lblSubTitle, lblTime, lblLocation, lblNoLanes, lblTotalVehicles, lblAvgVehicles, lblAvgVelocity, lblStatus;
    private JTextField txtTime, txtLocation, txtNoLanes, txtTotalVehicles, txtAvgVehicles, txtAvgVelocity;
    private JButton btnSend, btnExit, btnConnect;
    private TrafficReport trafficData;
    
    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private ObjectOutputStream streamOut = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    //----------------------------------------
    
    public static void main(String[] args)
    {
        MonitoringStation1 monitoringStation1 = new MonitoringStation1();
        monitoringStation1.run();
    }
    
    private void run()
    {
        setBounds(100, 10, 258, 346);
        setTitle("Monitoring Station 1");
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        DisplayGUI();
        setResizable(false);
        setVisible(true);
        
        //CHAT RELATED ---------------------------
        getParameters(); 
        //----------------------------------------
    }
    
    //<editor-fold defaultstate="collapsed" desc="DisplayGUI">
    
        public void DisplayGUI()
    {
        springLayout = new SpringLayout();
        setLayout(springLayout);
        
        SetupLabels(springLayout);
        SetupTextFields(springLayout);
        SetupButtons(springLayout);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SetupLabels">
        
    public void SetupLabels(SpringLayout layout)
    {
        lblTime = LibraryComponents.LocateAJLabel(this, layout, "Time:", 20, 70);
        lblLocation = LibraryComponents.LocateAJLabel(this, layout, "Location:", 20, 100);
        lblNoLanes = LibraryComponents.LocateAJLabel(this, layout, "# Lanes:", 20, 130);
        lblTotalVehicles = LibraryComponents.LocateAJLabel(this, layout, "TotalVehicles:", 20, 160);
        lblAvgVehicles = LibraryComponents.LocateAJLabel(this, layout, "Avg # Vehicles:", 20, 190);
        lblAvgVelocity = LibraryComponents.LocateAJLabel(this, layout, "Avg Velocity:", 20, 220);   
          
        lblTitle = LibraryComponents.LocateAJLabel(this, layout, "Monitoring Stn 1", 0, 0);
        lblTitle.setPreferredSize(new Dimension(258, 35));
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f));
        lblTitle.setHorizontalAlignment(JTextField.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color (0, 102, 0));
        lblTitle.setForeground(new Color(255, 255, 255));
        
        lblSubTitle = LibraryComponents.LocateAJLabel(this, layout, "Enter your readings and click Submit", 10, 40);
        lblSubTitle.setPreferredSize(new Dimension(229, 18));
        lblSubTitle.setFont(lblTitle.getFont().deriveFont(11f));
        lblSubTitle.setHorizontalAlignment(JTextField.CENTER);
        lblSubTitle.setOpaque(true);
        lblSubTitle.setBackground(new Color (0, 102, 0));
        lblSubTitle.setForeground(new Color(255, 255, 255));
        
        lblStatus = LibraryComponents.LocateAJLabel(this, layout, "Please fill all fields.", 1, 237);
        lblStatus.setVisible(false);
        lblStatus.setFont(lblStatus.getFont().deriveFont(11f));
    }
        
//</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="SetupTextAreas">
    
    public void SetupTextFields(SpringLayout layout)
    {
        txtTime = LibraryComponents.LocateAJTextField(this, layout, 11, 120, 70);
        txtLocation = LibraryComponents.LocateAJTextField(this, layout, 11, 120, 100);
        txtNoLanes = LibraryComponents.LocateAJTextField(this, layout, 11, 120, 130);
        txtTotalVehicles = LibraryComponents.LocateAJTextField(this, layout, 11, 120, 160);
        txtAvgVehicles = LibraryComponents.LocateAJTextField(this, layout, 11, 120, 190);
        txtAvgVelocity = LibraryComponents.LocateAJTextField(this, layout, 11, 120, 220);
    }
        
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SetupButtons">
    
    public void SetupButtons(SpringLayout layout)
    {
        btnSend = LibraryComponents.LocateAJButton(this, this, springLayout, "Send", 1, 252, 100, 25);
        btnExit = LibraryComponents.LocateAJButton(this, this, springLayout, "Exit", 151, 252, 100, 25);
        btnConnect = LibraryComponents.LocateAJButton(this, this, springLayout, "Connect", 151, 276, 100, 25);
    }
    
//</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Server">
    
    public void connect(String serverName, int serverPort)
    {
        println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    private void send()
    {
        try
        {
            // remove: make it take text from all text boxes, and wrap it in an array
            trafficData = new TrafficReport();
            trafficData.Time = txtTime.getText();
            trafficData.Location = txtLocation.getText();
            trafficData.NoLanes = txtNoLanes.getText();
            trafficData.TotalVehicles = txtTotalVehicles.getText();
            trafficData.AvgVehicles = txtAvgVehicles.getText();
            trafficData.AvgVelocity = txtAvgVelocity.getText();
            //System.out.println(trafficData);
            streamOut.writeObject(trafficData);
            streamOut.flush();
        }
        catch (IOException ioe)
        {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    public void handle(String msg)
    {
        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            System.out.println("Handle: " + msg);
            println(msg);
        }
    }

    public void open()
    {
        try
        {
            streamOut = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            println("Error closing ...");
        }
    }

    void println(String msg)
    {
        //display.appendText(msg + "\n");
        // remove: make this back into lblMessage
        lblStatus.setText(msg);
    }
    
    public void getParameters()
    {
        // serverName = getParameter("host");
        // serverPort = Integer.parseInt(getParameter("port"));
        
        serverName = "localhost";
        serverPort = 4444;    
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ActionListener">
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnSend)
        {
            send();
            txtTime.requestFocus();
        }
        
        if (e.getSource() == btnExit)
        {
            System.exit(0);
        }
        
        if (e.getSource() == btnConnect)
        {
            connect(serverName, serverPort);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//</editor-fold>
}
