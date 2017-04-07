//Source:  http://www.newthinktank.com/2013/03/binary-tree-in-java/
// New Think Tank

package monitoringoffice;

import javax.swing.JTextArea;

public class BinaryTree 
{
    BTNode root;

    public void addNode(int key, String Time, String Location, String AvgVehicles, String AvgVelocity) 
    {
        // Create a new BTNode and initialize it
        BTNode newNode = new BTNode(key, Time + Location + AvgVehicles + AvgVelocity);
        
        // If there is no root this becomes root
        if (root == null) 
        {
            root = newNode;
        }
        
        else 
        {
            // Set root as the BTNode we will start
            // with as we traverse the tree
            BTNode focusNode = root;
            // Future parent for our new BTNode
            BTNode parent;

            while (true) 
            {
                // root is the top parent so we start there
                parent = focusNode;

                // Check if the new node should go on
                // the left side of the parent node

                if (key < focusNode.key) 
                {
                    // Switch focus to the left child
                    focusNode = focusNode.leftChild;
                    // If the left child has no children

                    if (focusNode == null) 
                    {
                        // then place the new node on the left of it
                        parent.leftChild = newNode;
                        return; // All Done
                    }
                } 
                
                else 
                { // If we get here put the node on the right
                    focusNode = focusNode.rightChild;
                    // If the right child has no children
                    if (focusNode == null) 
                    {
                        // then place the new node on the right of it
                        parent.rightChild = newNode;
                        return; // All Done
                    }
                }
            }
        }
    }

    // All nodes are visited in ascending order
    // Recursion is used to go to one node and
    // then go to its child nodes and so forth

    public void inOrderTraverseTree(BTNode focusNode, JTextArea ta) 
    {
        if (focusNode != null) 
        {
            // Traverse the left node
            inOrderTraverseTree(focusNode.leftChild, ta);
            // Visit the currently focused on node
            ta.append(focusNode.toString());
            System.out.println(focusNode);
            // Traverse the right node
            inOrderTraverseTree(focusNode.rightChild, ta);
        }
    }

    public void preorderTraverseTree(BTNode focusNode, JTextArea ta) 
    {
        if (focusNode != null) 
        {
            System.out.println(focusNode);
            ta.append(focusNode.toString());
            preorderTraverseTree(focusNode.leftChild, ta);
            preorderTraverseTree(focusNode.rightChild, ta);
        }
    }

    public void postOrderTraverseTree(BTNode focusNode, JTextArea ta) 
    {
        if (focusNode != null) 
        {
            postOrderTraverseTree(focusNode.leftChild, ta);
            postOrderTraverseTree(focusNode.rightChild, ta);
            System.out.println(focusNode);
            ta.append(focusNode.toString());
        }
    }

    public BTNode findNode(int key) 
    {
        // Start at the top of the tree
        BTNode focusNode = root;
        
        // While we haven't found the BTNode
        // keep looking

        while (focusNode.key != key) 
        {
            // If we should search to the left
            if (key < focusNode.key) 
            {

                // Shift the focus BTNode to the left child

                focusNode = focusNode.leftChild;
            } 
            
            else 
            {
                // Shift the focus BTNode to the right child
                focusNode = focusNode.rightChild;
            }

            // The node wasn't found
            if (focusNode == null)
                    return null;
        }
        
        return focusNode;
    }

//public static void main(String[] args) {
//
//		BinaryTree theTree = new BinaryTree();
//
//		theTree.addNode(50, "Boss");
//
//		theTree.addNode(25, "Vice President");
//
//		theTree.addNode(15, "Office Manager");
//
//		theTree.addNode(30, "Secretary");
//
//		theTree.addNode(75, "Sales Manager");
//
//		theTree.addNode(85, "Salesman 1");
//
//		// Different ways to traverse binary trees
//
//		// theTree.inOrderTraverseTree(theTree.root);
//
//		// theTree.preorderTraverseTree(theTree.root);
//
//		// theTree.postOrderTraverseTree(theTree.root);
//
//		// Find the node with key 75
//
//		System.out.println("\nNode with the key 75");
//
//		System.out.println(theTree.findNode(75));
//
//}
}

class BTNode
{
    int key;
    String name;
    BTNode leftChild;
    BTNode rightChild;

    BTNode(int key, String name) 
    {
        this.key = key;
        this.name = name;
    }

    public String toString() 
    {
        return name + " has the key " + key;
        /*
         * return name + " has the key " + key + "\nLeft Child: " + leftChild +
         * "\nRight Child: " + rightChild + "\n";
         */
    }
}