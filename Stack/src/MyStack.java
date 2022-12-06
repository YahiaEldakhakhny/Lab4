import java.util.Arrays;
import java.util.Scanner;

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
class Node {
    int value;
    Node next = null;

    // constructors
    // Empty constructor
    public Node(){}
    // Constructor to set value of node
    public Node(int i) {
        this.value = i;
    }
    // Constructor to set value and next
    public Node(int i, Node n){
        this.value = i;
        this.next = n;
    }
    
}

public class MyStack implements IStack {
    Node Head = null;


    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        Scanner scan = new Scanner(System.in);
        // get string list of values
        String strList = scan.nextLine();

        //get command from user
        String command = scan.next();

        try {
            int[] arrList = myStack.stringToIntArray(strList);
            myStack.arrayToStack(arrList);
            switch (command) {
                case "push":
                    int newValue = scan.nextInt();
                    Node newNode = new Node(newValue);
                    myStack.push(newNode);
                    myStack.displayStack();
                    break;
                case "pop":
                    myStack.pop();
                    myStack.displayStack();
                    break;
                case "peek":
                    Node topNode = (Node) myStack.peek();
                    System.out.println(topNode.value);
                    break;
                case "isEmpty":
                    String output;
                    if (myStack.isEmpty()) {
                        output = "True";
                    } else {
                        output = "False";
                    }
                    System.out.println(output);
                    break;
                case "size":
                    System.out.println(myStack.size());
                    break;
                default:
                    throw new RuntimeException();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        


    }

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


    public void arrayToStack(int[] intArray) {

        int len = intArray.length;
        if (len == 0) {
            return;
        } else {
            Node[] arrayOfNodes = new Node[len];
            // LOOP TO SET VALUE OF EACH NODE
            for (int i = 0; i < len; i++) {
                arrayOfNodes[i] = new Node(intArray[i]);
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
        Node currNode = Head;
        for (int i = 0; i < arrayRepresentation.length; i++) {
            arrayRepresentation[i] = currNode.value;
            currNode = currNode.next;
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