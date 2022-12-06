import java.util.Arrays;

interface IStack {
  
  /*** Removes the element at the top of stack and returnsthat element.
  * @return top of stack element, or through exception if empty
  */
  
  public Object pop();
  
  /*** Get the element at the top of stack without removing it from stack.
  * @return top of stack element, or through exception if empty
  */
  
  public Object peek();
  
  /*** Pushes an item onto the top of this stack.
  * @param object to insert*
  */
  
  public void push(Object element);
  
  /*** Tests if this stack is empty
  * @return true if stack empty
  */
  public boolean isEmpty();
  
  public int size();
}

/**
 * Node
 */

/**
 * InnerStack
 */
class Node {
    Node next = null;
    
}
class NodeInt extends Node {


    int value;

    // constructors
    // Empty constructor
    public NodeInt(){}
    // Constructor to set value of node
    public NodeInt(int i) {
        this.value = i;
    }
    // Constructor to set value and next
    public NodeInt(int i, NodeInt n){
        this.value = i;
        this.next = n;
    }
    
}

class NodeChar extends Node {
    char value;

    // constructors
    // Empty constructor
    public NodeChar(){}
    // Constructor to set value of node
    public NodeChar(char c) {
        this.value = c;
    }
    // Constructor to set value and next
    public NodeChar(char c, Node n){
        this.value = c;
        this.next = n;
    }
    
}

public class Stack implements IStack {
    Node Head = null;

    // Custom functions
    public int[] stringToIntArray(String s){
        if (s.equals("[]")) {
            int[] output = new int[0];
            return output;
        } else {
            String[] arrayOfStrings = s.trim().replace("[", "").replace("]", "").split(",");
            int len = arrayOfStrings.length;
            int[] output = new int[len];

            for (int i = 0; i < len; i++) {
                output[i] = Integer.valueOf(arrayOfStrings[i].trim());
            }
            return output;

        }
        
    }

    public void stringToStack(String str) {
        NodeChar[] nodeArr = new NodeChar[str.length()];
        for (int i = 0; i < str.length(); i++) {
            nodeArr[i].value = str.charAt(i);
        }
        for (int i = 0; i < nodeArr.length; i++) {
            if (i == nodeArr.length - 1) {
                nodeArr[i].next = null;
            } else {
                nodeArr[i].next = nodeArr[i+1];
            }
        }
    }

    public void arrayToStack(int[] intArray) {

        int len = intArray.length;
        if (len == 0) {
            return;
        } else {
            NodeInt[] arrayOfNodes = new NodeInt[len];
            // LOOP TO SET VALUE OF EACH NODE
            for (int i = 0; i < len; i++) {
                arrayOfNodes[i] = new NodeInt(intArray[i]);
            }

            // LOOP TO SET POINTER OF EACH NODE
            for (int i = 0; i < len; i++) {
                if (i == len-1) {
                    arrayOfNodes[i].next = null;
                } else {
                    arrayOfNodes[i].next = arrayOfNodes[i+1];
                }
                
            }
            this.Head = arrayOfNodes[0];

        }
        
    }

    public void displayStack(){
        int[] arrayRepresentation = new int[this.size()];
        NodeInt currNode = (NodeInt) Head;
        for (int i = 0; i < arrayRepresentation.length; i++) {
            arrayRepresentation[i] = currNode.value;
            currNode = (NodeInt) currNode.next;
        }

        System.out.println(Arrays.toString(arrayRepresentation));
    }

    // Interface functions
    @Override
    public Object pop() throws RuntimeException {
        if (Head != null) {
            Node oldHead = this.Head;
            this.Head = oldHead.next;
            return oldHead;
        } else {
            throw new RuntimeException();
        }
        
    }

    @Override
    public Object peek() {
        return this.Head;
    }

    @Override
    public void push(Object element) {
        Node newHead = (Node) element;
        newHead.next = this.Head;
        this.Head = newHead;
    }

    @Override
    public boolean isEmpty() {
        return (Head == null);
    }

    @Override
    public int size() {
        int s = 0;
        Node currNode = this.Head;
        while (currNode != null) {
            s++;
            currNode = currNode.next;
        }
        return s;
    }
}