package monitoringoffice;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
/**
 * @author Preston J. Trail
 */
public class MonitoringOffice extends JFrame implements ActionListener, KeyListener
{
    SpringLayout springLayout;
    private JButton btnPreDisplay, btnPreSave, btnInDisplay, btnInSave, btnPostDisplay, btnPostSave, btnDListDisplay, btnExit, btnSortLocation, btnSortVehicle, btnSortVelocity, btnConnect;
    private JTextArea txtBinaryTree, txtLinkedList, txtDataReceived, txtNetworkTest;
    private JLabel lblPre, lblBinaryTree, lblLinkedList, lblDataReceived, lblTableTitle, lblIn, lblPost, lblTitle, lblSortBy;
    JTable table;
    MyModel tableModel;
    ArrayList<Object[]> dataValues;   // = new ArrayList();
    DList myDList;
    BinaryTree myBTree;
    
    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private ObjectOutputStream streamOut = null;
    private MonitoringThread1 client = null;
    private String serverName = "localhost";
    private int serverPort = 4444;
    
    public static void main(String[] args)
    {
        MonitoringOffice monitoringOfficeApplication = new MonitoringOffice();
        monitoringOfficeApplication.run();
    }

    private void run()
    {
        setBounds(100, 10, 890, 525);
        setTitle("Monitoring Office");

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        displayGUI();
        setResizable(false);
        setVisible(true);
        getParameters();
        
        //code for later
        //  read in line
        //  add to array list
        //  myNode = new <>.node
        //  myDLL.append(myNode);
    }
    
    //<editor-fold defaultstate="collapsed" desc="displayGUI">
        private void displayGUI()
    {
        springLayout = new SpringLayout();
        setLayout(springLayout);

        setupButtons(springLayout);
        setupTextAreas(springLayout);
        setupLabels(springLayout);
        setupTables(springLayout);
        CreateDList(txtLinkedList);
        CreateBTree(txtBinaryTree);
        
//        String test = (String)table.getModel().getValueAt(1, 2);
//        System.out.println(test);
        //setupTable(springLayout);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setupTextAreas">
    private void setupTextAreas (SpringLayout layout)
    {
        txtBinaryTree = LibraryComponents.LocateAJTextArea(this, layout, txtBinaryTree, 13, 372, 4, 78);
        txtBinaryTree.setPreferredSize(new Dimension(1, 1));
        txtBinaryTree.setLineWrap(true);
        
        txtLinkedList = LibraryComponents.LocateAJTextArea(this, layout, txtLinkedList, 13, 275, 4, 78);
        txtLinkedList.setPreferredSize(new Dimension(1, 1));
        
        txtDataReceived = LibraryComponents.LocateAJTextArea(this, layout, txtDataReceived, 610, 70, 9, 18);
        txtDataReceived.setText("Location: 1\nLocation: 2");
        txtDataReceived.setPreferredSize(new Dimension(9, 18));
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setupLabels">
    private void setupLabels (SpringLayout layout)
    {
        lblBinaryTree = LibraryComponents.LocateAJLabel(this, layout, "Binary Tree:", 13, 352);
        lblLinkedList = LibraryComponents.LocateAJLabel(this, layout, "Linked List:", 13, 257);
        lblDataReceived = LibraryComponents.LocateAJLabel(this, layout, "New data received from:", 610, 50);
        
        lblTableTitle = LibraryComponents.LocateAJLabel(this, layout, "Traffic Monitoring Data", 15, 50);
        lblTableTitle.setHorizontalAlignment(JTextField.CENTER);
        lblTableTitle.setOpaque(true);
        lblTableTitle.setBackground(new Color(0, 102, 0));
        lblTableTitle.setForeground(Color.WHITE);
        lblTableTitle.setPreferredSize(new Dimension(499, 15));
        
        lblPre = LibraryComponents.LocateAJLabel(this, layout, "Pre-Order", 10, 443);
        lblPre.setHorizontalAlignment(JTextField.CENTER);
        lblPre.setOpaque(true);
        lblPre.setPreferredSize(new Dimension(168, 19));
        lblPre.setBackground(new Color(0, 102, 0));
        lblPre.setForeground(new Color(255, 255, 255));

        lblIn = LibraryComponents.LocateAJLabel(this, layout, "In-Order", 360, 443);
        lblIn.setHorizontalAlignment(JTextField.CENTER);
        lblIn.setOpaque(true);
        lblIn.setPreferredSize(new Dimension(168, 19));
        lblIn.setBackground(new Color(0, 102, 0));
        lblIn.setForeground(new Color(255, 255, 255));

        lblPost = LibraryComponents.LocateAJLabel(this, layout, "Post-Order", 705, 443);
        lblPost.setHorizontalAlignment(JTextField.CENTER);
        lblPost.setOpaque(true);
        lblPost.setPreferredSize(new Dimension(168, 19));
        lblPost.setBackground(new Color(0, 102, 0));
        lblPost.setForeground(new Color(255, 255, 255));

        lblTitle = LibraryComponents.LocateAJLabel(this, layout, "Monitoring Office", 0, 0);
        lblTitle.setPreferredSize(new Dimension(884, 35));
        lblTitle.setFont(lblTitle.getFont().deriveFont(20f));
        lblTitle.setHorizontalAlignment(JTextField.CENTER);
        lblTitle.setOpaque(true);
        lblTitle.setBackground(new Color (0, 102, 0));
        lblTitle.setForeground(new Color(255, 255, 255));
        
        lblSortBy = LibraryComponents.LocateAJLabel(this, layout, "Sort by:", 90, 220);
        lblSortBy.setPreferredSize(new Dimension (80, 25));
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setupButtons">
    private void setupButtons(SpringLayout layout)
    {
        btnPreDisplay = LibraryComponents.LocateAJButton(this, this, layout, "Display", 10, 465, 80, 25);
        btnPreSave = LibraryComponents.LocateAJButton(this, this, layout, "Save", 98, 465, 80, 25);
        
        btnInDisplay = LibraryComponents.LocateAJButton(this, this, layout, "Display", 360, 465, 80, 25);
        btnInSave = LibraryComponents.LocateAJButton(this, this, layout, "Save", 448, 465, 80, 25);
        
        btnPostDisplay = LibraryComponents.LocateAJButton(this, this, layout, "Display", 705, 465, 80, 25);
        btnPostSave = LibraryComponents.LocateAJButton(this, this, layout, "Save", 793, 465, 80, 25);
        
        btnDListDisplay = LibraryComponents.LocateAJButton(this, this, layout, "Display", 793, 345, 80, 25);
        
        btnExit = LibraryComponents.LocateAJButton(this, this, layout, "Exit", 610, 215, 200, 25);
        
        btnSortLocation = LibraryComponents.LocateAJButton(this, this, layout, "Location", 136, 219, 121, 25);
        btnSortVehicle = LibraryComponents.LocateAJButton(this, this, layout, "Vehicle #", 256, 219, 122, 25);
        btnSortVelocity = LibraryComponents.LocateAJButton(this, this, layout, "Velocity", 377, 219, 121, 25);
        
        btnConnect = LibraryComponents.LocateAJButton(this, this, layout, "Connect", 610, 239, 200, 25);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setupTables">
    
    private void setupTables(SpringLayout layout)
    {
        // Create a panel to hold all other components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        add(topPanel);

        // Create column names
        String columnNames[] = {"Time", "Location", "Av.Vehicle#", "Av.Velocity"};

        // Create some data
        // TODO
        //TrafficReport test = new TrafficReport("6:00:00", "1", "09", "70");
        dataValues = new ArrayList();
        dataValues.add(new Object[] {"6:00:00 AM", "1", "09", "70"});
        dataValues.add(new Object[] {"6:00:00 AM", "2", "08", "80"});
        dataValues.add(new Object[] {"7:00:00 AM", "1", "10", "60"});
        dataValues.add(new Object[] {"7:00:00 AM", "2", "10", "60"});
        dataValues.add(new Object[] {"8:00:00 AM", "1", "12", "40"});
        dataValues.add(new Object[] {"8:00:00 AM", "2", "11", "50"});
        dataValues.add(new Object[] {"9:00:00 AM", "1", "11", "50"});
        dataValues.add(new Object[] {"9:00:00 AM", "2", "09", "65"});
        dataValues.add(new Object[] {"10:00:00 AM", "1", "08", "80"});
        dataValues.add(new Object[] {"10:00:00 AM", "2", "07", "80"});

        // constructor of JTable model
	tableModel = new MyModel(dataValues, columnNames);
        
        // Create a new table instance
        table = new JTable(tableModel);

        // Configure some of JTable's paramters
        table.isForegroundSet();
        table.setShowHorizontalLines(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        add(table);

        // Change the text and background colours
        table.setSelectionForeground(Color.white);
        table.setSelectionBackground(new Color(0, 102, 0));

        // Add the table to a scrolling pane, size and locate
        JScrollPane scrollPane = table.createScrollPaneForTable(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(500, 156));
        layout.putConstraint(SpringLayout.WEST, topPanel, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, topPanel, 65, SpringLayout.NORTH, this);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="dataSorts">
    
    public static void bubbleSortLocation(ArrayList<Object[]> arr, JTable myTable) 
    {
        for(int j=0; j<arr.size(); j++) 
        {  
            for(int i=j+1; i<arr.size(); i++)
            {  
                if((arr.get(i)[1]).toString().compareToIgnoreCase(arr.get(j)[1].toString())<0)
                {  
                   Object[] words = arr.get(j); 
                   arr.set(j, arr.get(i));
                   arr.set(i, words);
                }  
            }  
            System.out.println(arr.get(j)[0] + " - " + arr.get(j)[1] + " - " + arr.get(j)[2] + " - " + arr.get(j)[3]);  
            myTable.repaint();
        }
        System.out.println("------------------------");
    }
    
        // Selection Sort Method for Descending Order
        public static void selectionSortVehicle (ArrayList<Object[]> arr, JTable myTable)
        {
            int i, j, first;
            Object[] temp;
            for (i=arr.size()-1; i > 0; i--)
            {
                first = 0; //initialize to subscript of first element
                
                for(j=1; j<=i; j++) //locate smallest element between positions 1 and i.
                {
                    if((arr.get(j)[2]).toString().compareToIgnoreCase(arr.get(first)[2].toString())>0)
                    first = j;
                }
                temp = arr.get(first); //swap smallest found with element in position i.
                  arr.set(first, arr.get(i));
                  arr.set(i, temp);
                  System.out.println(arr.get(i)[0] + " - " + arr.get(i)[1] + " - " + arr.get(i)[2] + " - " + arr.get(i)[3]);  
            }
            System.out.println(arr.get(i)[0] + " - " + arr.get(i)[1] + " - " + arr.get(i)[2] + " - " + arr.get(i)[3]);
            System.out.println("------------------------");
            myTable.repaint();
        }
    
    public static void InsertionSortVelocity(ArrayList<Object[]> arr, JTable myTable)
    {
        //TODO
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="DList">
    public void CreateDList(JTextArea myTextArea)
    {
        myDList = new DList();
        myDList.head.append(new Node("6:00 AM, 1,2,10,5,72"));
        myDList.head.append(new Node("6:00 AM,2,2,10,5,65"));
        myDList.head.append(new Node("7:00 AM,1,2,10,5,100"));
        myDList.head.insert(new Node("7:00 AM,2,2,10,5,75"));
        myDList.displayInTextArea(myTextArea);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Binary Tree">
    
    public void CreateBTree(JTextArea myTextArea)
    {
        myBTree = new BinaryTree();
        myBTree.addNode(3, "6:00 AM", "1", "5", "72");
        myBTree.addNode(3, "6:00 AM", "2", "5", "65");
        myBTree.addNode(3, "7:00 AM", "1", "5" ,"100");
        myBTree.addNode(3, "7:00 AM", "2", "5","75");
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Server">
    
    public void getParameters()
    {
//      serverName = getParameter("host");
//      serverPort = Integer.parseInt(getParameter("port"));
        serverName = "localhost";
        serverPort = 4444;        
    }
    
    public void connect(String serverName, int serverPort)
    {
        System.out.println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            System.out.println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
    }

    public void handle(TrafficReport data)
    {
        System.out.println(data.Time);
        System.out.println(data.Location);
        System.out.println(data.NoLanes);
        System.out.println(data.TotalVehicles);
        System.out.println(data.AvgVehicles);
        System.out.println(data.AvgVelocity);
        
        dataValues.add(new Object[] {data.Time, data.Location, data.AvgVehicles, data.AvgVelocity});
        tableModel.fireTableDataChanged();
        
        myBTree.addNode(3, data.Time, data.Location, data.AvgVehicles, data.AvgVelocity);
        myDList.head.append(new Node(data.Time, data.Location, data.AvgVehicles, data.AvgVelocity));
        myDList.displayInTextArea(txtLinkedList);
    }

    public void open()
    {
        try
        {
            streamOut = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            streamOut.flush();
            client = new MonitoringThread1(this, socket);
        }
        catch(Exception ex)
        {
            System.out.println("Error opening output stream: " + ex);
        }
    }

    public void close()
    {
        try
        {
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error closing ..." + ioe);
        }
        client.close();
        client.stop();
    }
    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="actionPerformed">
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnSortLocation)
        {
            bubbleSortLocation(dataValues, table);
        }
        
        if (e.getSource() == btnSortVehicle)
        {
            selectionSortVehicle(dataValues, table);
        }
        
        if (e.getSource() == btnSortVelocity)
        {   
            InsertionSortVelocity(dataValues, table);
        }
        
        if (e.getSource() == btnConnect)
        {
            connect(serverName, serverPort);
        }
        
        if (e.getSource() == btnPreDisplay)
        {
            txtBinaryTree.setText(null);
            myBTree.preorderTraverseTree(myBTree.root, txtBinaryTree);
        }
        
        if (e.getSource() == btnInDisplay)
        {
            txtBinaryTree.setText(null);
            myBTree.inOrderTraverseTree(myBTree.root, txtBinaryTree);
        }
                
        if (e.getSource() == btnPostDisplay)
        {
            txtBinaryTree.setText(null);
            myBTree.postOrderTraverseTree(myBTree.root, txtBinaryTree);
        }
        
        if (e.getSource() == btnDListDisplay)
        {
            CreateDList(txtLinkedList);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//</editor-fold>
    
}

//<editor-fold defaultstate="collapsed" desc="MyModel">

class MyModel extends AbstractTableModel
    {
        ArrayList<Object[]> al;

        // the headers
        String[] header;

        // constructor 
        MyModel(ArrayList<Object[]> obj, String[] header)
        {
            // save the header
            this.header = header;
            // and the data
            al = obj;
        }

        // method that needs to be overload. The row count is the size of the ArrayList

        public int getRowCount()
        {
            return al.size();
        }

        // method that needs to be overload. The column count is the size of our header
        public int getColumnCount()
        {
            return header.length;
        }

        // method that needs to be overload. The object is in the arrayList at rowIndex
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return al.get(rowIndex)[columnIndex];
        }

        // a method to return the column name 
        public String getColumnName(int index)
        {
            return header[index];
        }

        // a method to add a new line to the table
        void add(String word1, String word2, String word3, String word4)
        {
            // make it an array[2] as this is the way it is stored in the ArrayList
            // (not best design but we want simplicity)
            String[] str = new String[2];
            str[0] = word1;
            str[1] = word2;
            str[2] = word3;
            str[3] = word4;
            al.add(str);
            // inform the GUI that I have change
            fireTableDataChanged();
        }
    }

//</editor-fold>