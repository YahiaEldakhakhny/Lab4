import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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


class Stack implements IStack {
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


public class Evaluator {
    final String variables = "abc";
    final String operators = "+-*/^";
    int[] values = new int[3];
    final String[] invalidSequences = {"^(--)", "([a-z]|\\d{0,})(--)([a-z]|\\d{0,})", "(--)$"};
    final String[] replacements = {"", "$1"+"\\+"+"$3", ""};
    String err = "Error";
    
    public static void main(String[] args) throws Exception {
        
        Evaluator ev = new Evaluator();
        Scanner scan = new Scanner(System.in);

        try {
            //get infix expression
            String infix = scan.next();
            // get values of a, b, c
            for (int i = 0; i < ev.values.length; i++) {
                String line = scan.next();
                String[] lineElements = line.split("=");
                String currVariable = Character.toString(ev.variables.charAt(i));
                if (currVariable.equals(lineElements[0])) {
                    ev.values[i] = Integer.valueOf(lineElements[lineElements.length-1]);                    
                } else {
                    scan.close();
                    throw new RuntimeException();
                }
            }
            // get equivalent postfix expression
            String postfix = ev.inToPost(infix);

            //evaluate postfix expression
            int result = ev.evaluatePostfix(postfix);

            //output
            System.out.println(postfix);
            System.out.println(result);

            scan.close();
            
        } catch (Exception e) {
            System.out.println(ev.err);
        }
        
    }


    char nodeToChar(Object Nch){
        return ((NodeChar) Nch).value;
    }
    

    int nodeToInt(Object Nch){
        return ((NodeInt) Nch).value;
    }
    

    NodeChar charToNode(char ch){
        return new NodeChar(ch);
    }


    NodeInt intToNode(int i){
        return new NodeInt(i);
    }


    public String formatEquation(String equationInput) {
        boolean foundInvalidExpression = false;
        String equation = equationInput;
        do {
            foundInvalidExpression = false;
            for (int i = 0; i < replacements.length; i++) {
                String invalidSequence = invalidSequences[i];
                String replacement = replacements[i];
    
                Pattern pattern = Pattern.compile(invalidSequence);
                Matcher matcher = pattern.matcher(equation);
                boolean currentFinder = matcher.find();
                foundInvalidExpression = foundInvalidExpression || currentFinder;
    
                if (currentFinder) {
                    equation = matcher.replaceAll(replacement);
                }
            }
        } while (foundInvalidExpression);
            
        

        return equation;
    }


    int precedence(char ch) {
        if(ch == '+' || ch == '-') {
           return 1;              //Precedence of + or - is 1
        }else if(ch == '*' || ch == '/') {
           return 2;            //Precedence of * or / is 2
        }else if(ch == '^') {
           return 3;            //Precedence of ^ is 3
        }else {
           return 0;
        }
    }


    String inToPost(String inputInfix){
        String infix = formatEquation(inputInfix);
        Stack myStack = new Stack();
        char dummyChar = '#';
        myStack.push(charToNode(dummyChar));
        String postfix = "";

        for (int i = 0; i < infix.length(); i++) {
            char currChar = infix.charAt(i);

            if (isOperand(Character.toString(currChar))) {
                postfix += currChar;
            } 
            else if (currChar == '(' || currChar == '^') {
                myStack.push(charToNode(currChar));
            }
            else if(currChar == ')') {
                while(nodeToChar(myStack.peek()) != dummyChar && nodeToChar(myStack.peek()) != '(') {
                   postfix += nodeToChar(myStack.peek()); //store and pop until ( has found
                   myStack.pop();
                }
                myStack.pop(); //remove the '(' from stack
            }
            else {
                if(precedence(currChar) > precedence(nodeToChar(myStack.peek())))
                   myStack.push(charToNode(currChar)); //push if precedence is high
                else {
                   while(nodeToChar(myStack.peek()) != dummyChar && precedence(currChar) <= precedence(nodeToChar(myStack.peek()))) {
                      postfix += nodeToChar(myStack.pop());        //store and pop until higher precedence is found
                    }
                    myStack.push(charToNode(currChar));
                }
            }
        }

        while(nodeToChar(myStack.peek()) != dummyChar) {
            postfix += nodeToChar(myStack.pop());        //store and pop until stack is not empty.
        }
      
        return postfix;

    }


    
    int valueOf(String ch) throws RuntimeException{
        if (Character.isDigit(ch.charAt(0))) {
            return Integer.valueOf(ch);
        } else {
            switch (ch) {
                case "a":
                    return this.values[0];
                case "b":
                    return this.values[1];
                case "c":
                    return this.values[2];
                default:
                    throw new RuntimeException();
            }
        }
        
    }

    boolean isOperator(String ch){
        if (this.operators.contains(ch)) {
            return true;
        } else {
            return false;
        }
    }

    boolean isOperand(String n){
        if (this.variables.contains(n)) {
            return true;
        } else if (Character.isDigit(n.charAt(0))) {
            return true;
        } else {
            return false;
        }
    }


    int evaluatePostfix(String postExp) throws RuntimeException{
        String[] expElements = postExp.split("");
        Stack evalStack = new Stack();
        // make sure the first element is a zero for negation operations
        for (int i = 0; i < expElements.length; i++) {
            evalStack.push(intToNode(0));
        }

        for (String elem : expElements) {
            // If the scanned character is an operand (a, b, or c),
            // push its value to the stack
            if (isOperand(elem)) {
                evalStack.push(intToNode(valueOf(elem)));
            } 
            else {//  If the scanned character is an operator, pop two elements from stack apply the operator
                int val1 = nodeToInt(evalStack.pop());
                int val2 = nodeToInt(evalStack.pop());
                int result;
                 
                switch(elem)
                {
                    case "+":
                        result = val2 + val1;
                        break;
                     
                    case "-":
                        result = val2 - val1;
                        break;
                     
                    case "/":
                        result = val2 / val1;
                        break;
                     
                    case "*":
                        result = val2 * val1;
                        break;

                    case "^":
                        result = (int) Math.pow(val2, val1);
                        break;
                    default:
                        throw new RuntimeException();
                }
                evalStack.push(intToNode(result));

            }

        }

        return nodeToInt(evalStack.pop());
    }

}
