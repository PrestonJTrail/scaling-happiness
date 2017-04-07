/**
 * **************************************************************************
 */
/*                                                                           */
/*                    Doubly-Linked List Manipulation                        */
/*                                                                           */
/*                     January 1998, Toshimi Minoura                         */
/*                                                                           */
/**
 * **************************************************************************
 */
// Filename: Doubly-LinkedList_ToshimiMinoura
// Source:   TBA
package monitoringoffice;

// A Node is a node in a doubly-linked list.

import javax.swing.JTextArea;

class Node
{              // class for nodes in a doubly-linked list

    Node prev;              // previous Node in a doubly-linked list
    Node next;              // next Node in a doubly-linked list
    TrafficReport myTrafficReport;
    //public char data;       // data stored in this Node

    Node()
    {                // constructor for head Node 
        prev = this;           // of an empty doubly-linked list
        next = this;
        myTrafficReport = new TrafficReport();
        // for when the program first starts in the morning and it has no
        // data, when you instantiate it you give it some temporary data for
        // some reason.
    }

    // When you send it data
    Node(String str)
    {       // constructor for a Node with data
        prev = null;
        next = null;
        myTrafficReport = new TrafficReport(str); // putting the data in the temp array with the constructor from TrafficReport.java
        //this.data = data;     // set argument data to instance variable data
    }
    
    // When you send it data
    Node(String str1, String str2, String str3, String str4)
    {       // constructor for a Node with data
        prev = null;
        next = null;
        myTrafficReport = new TrafficReport(str1, str2, str3, str4); // putting the data in the temp array with the constructor from TrafficReport.java
        //this.data = data;     // set argument data to instance variable data
    }

    // Adds a new node after the current node.
    public void append(Node newNode)
    {  // attach newNode after this Node
        newNode.prev = this;
        // all this next and prev stuff is changing the links between the nodes.
        newNode.next = next;
        if (next != null)
        {
            next.prev = newNode;
            // the next node's link that points to its previous node is now pointing
            // to the new node we just created, instead of the one before the one
            // we just created.
        }
        next = newNode;
        System.out.println("Node with data " + newNode.myTrafficReport.getTrafficReport()
                + " appended after Node with data " + myTrafficReport.getTrafficReport());
    }

    // Adds new node after the head.
    public void insert(Node newNode)
    {  // attach newNode before this Node
        newNode.prev = prev;
        newNode.next = this;
        prev.next = newNode;;
        prev = newNode;
        System.out.println("Node with data " + newNode.myTrafficReport.getTrafficReport()
                + " inserted before Node with data " + myTrafficReport.getTrafficReport());
    }

    // Remove the current node.
    public void remove()
    {              // remove this Node
        next.prev = prev;                 // bypass this Node
        prev.next = next;
        System.out.println("Node with data " + myTrafficReport.getTrafficReport() + " removed");
    }
    
    public String toString(){
        return this.myTrafficReport.getTrafficReport();
    }
}

class DList
{
    // creates the head node.
    Node head;

    public DList()
    {
        head = new Node();
    }
// both of these are constructors, not nodes.
    // they run as soon as this is instantiated.
        // constructors can be identified as they do not have a return value,
        // and they have the same name as the class they are in.
    public DList(String str)
    {
        head = new Node(str);
    }

    public Node find(String str)
    {          // find Node containing x
        for (Node current = head.next; current != head; current = current.next)
            // starts at the current set of data, and goes through to the last one
        {
            if (current.myTrafficReport.getTimeLocation().compareToIgnoreCase(str) == 0) //searched by the time and location
            {        // is x contained in current Node?
                System.out.println("Data " + str + " found");
                return current;               // return Node containing x
            }
        }
        System.out.println("Data " + str + " not found");
        return null;
    }

    //This Get method Added by Matt C
    public Node get(int i)
    {
        Node current = this.head;
        if (i < 0 || current == null)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        while (i > 0)
        {
            i--;
            current = current.next;
            if (current == null)
            {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        return current;
    }

    public String toString()
    {
        String str = "";
        if (head.next == head)
        {             // list is empty, only header Node
            return "List Empty";
        }
        str = "list content = ";
        for (Node current = head.next; current != head && current != null; current = current.next)
        {
            str = str + current.myTrafficReport.getTrafficReport();
        }
        return str;
    }

    public void print()
    {                  // print content of list
        if (head.next == head)
        {             // list is empty, only header Node
            System.out.println("list empty");
            return;
        }
        System.out.print("list content = ");
        for (Node current = head.next; current != head; current = current.next)
        {
            System.out.print(" " + current.myTrafficReport.getTrafficReport());
        }
        System.out.println("");
    }

        public void displayInTextArea(JTextArea myTextArea)
    {                  // print content of list
        if (head.next == head)
        {             // list is empty, only header Node
            myTextArea.setText("List empty.");
            return;
        }
        myTextArea.setText("HEAD");
        for (Node current = head.next; current != head; current = current.next)
        {
            myTextArea.append(" <--> " + current.myTrafficReport.getTrafficReport());
        }
        myTextArea.append(" <-> TAIL");
    }
    
  public static void main(String[] args) 
  {
    DList dList = new DList();              // create an empty dList
    dList.print();

    dList.head.append(new Node("6:00 AM,1,2,10,5,72"));       // add Node with data '1'
    dList.print();
    dList.head.append(new Node("6:00 AM,2,2,10,5,65"));       // add Node with data '2'
    dList.print();
    dList.head.append(new Node("7:00 AM,1,2,10,5,100"));       // add Node with data '3'
    dList.print();
    dList.head.insert(new Node("7:00 AM,2,2,10,5,75"));       // add Node with data 'A'
    dList.print();
    dList.head.insert(new Node("8:00 AM,1,2,10,5,82"));       // add Node with data 'B'
    dList.print();
    dList.head.insert(new Node("8:00 AM,2,2,10,5,60"));       // add Node with data 'C'
    dList.print();

    Node nodeA = dList.find("7:00 AM_1");           // find Node containing 'A'
    nodeA.remove();                         // remove that Node
    dList.print();

    Node node2 = dList.find("6:00 AM_2");           // find Node containing '2'
    node2.remove();                           // remove that Node
    dList.print();

    Node nodeB = dList.find("5");            // find Node containing 'B'
    nodeB.append(new Node("7:00 AM_1"));   // add Node with data X
    dList.print();
  }
}