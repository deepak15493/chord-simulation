import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Chord {
    public static final long RING_MAX_SIZE = 4294967296L;
    public static final int RING = 32;
    public static int m=0;
    public static ArrayList<ChordNode> chordNodes = new ArrayList<>();
    public static ArrayList<Integer> nodeList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner src = new Scanner(System.in);
        //******************************* Batch ***************************
        if(args.length==3)
        {
            String i = args[0];
            if(i.charAt(0)!='-' || i.charAt(1)!='i')
            {
                System.out.println("Error : batch mode requires -i as argument, passed different argument... Exiting program");
                System.exit(1);
            }
            File fileName = new File(args[1]);
            Chord chord = new Chord();
            Scanner sfile = null;
            try {
                sfile = new Scanner(fileName);
            }catch (FileNotFoundException fne)
            {
                System.out.println("File Not Found Exception Occurred");
                fne.printStackTrace();
            }

            try{
                m = Integer.parseInt(args[2]);
                if(m<=0 || m>31)
                {
                    System.out.println("Error: invalid integer m= " + m);
                    System.exit(1);
                }
            }
            catch (Exception e)
            {
                System.out.println("Error: invalid integer" + args[2]);
            }



            while (sfile.hasNextLine())
            {
                String line = sfile.nextLine();
                String[] str = line.split(" ");
                try{
                    if(line.equalsIgnoreCase("end")){
                        if(str.length>1)
                        {
                            System.out.println("SYNTAX ERROR: end expects no parameters");
                            continue;
                        }
                        sfile.close();
                        //System.exit(0);
                        chord.end();
                    } // end command

                    if (str.length>1){
                        if (Integer.parseInt(str[1]) >= Math.pow(2, m) || Integer.parseInt(str[1])<0) {
                            System.out.println("ERROR: node id must be in [0," + (int) (Math.pow(2, m)) + ")");
                            continue;
                        }
                    } // for add, show check
                    if (str.length > 2) {
                        if (Integer.parseInt(str[2]) >= Math.pow(2, m) || Integer.parseInt(str[2])<0) {
                            System.out.println("ERROR: node id must be in [0," + (Math.pow(2, m)) + ")");
                            continue;
                        }
                    } // for join check

                    if(str[0].equalsIgnoreCase("drop")){
                        if(str.length>2)
                        {
                            System.out.println("SYNTAX ERROR: drop expects 1 parameter not " + str.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(str[1]);
                        chord.drop(nNode);
                        //chordNodes.remove(chord.getNodeFromList(nNode));
                    } // end drop
                    else if(str[0].equalsIgnoreCase("add")){
                        if(str.length > 2)
                        {
                            System.out.println("SYNTAX ERROR: add expects 1 parameter not " + str.length);
                            continue;
                        }

                        int nNode = Integer.parseInt(str[1]);
                        chord.add(nNode);
                    }//end add
                    else if(str[0].equalsIgnoreCase("join")){
                        if (str.length > 3) {
                            System.out.println("SYNTAX ERROR: join expects 2 parameters not " + str.length);
                            continue;
                        }
                        int nNode1 = Integer.parseInt(str[1]);
                        int nNode2 = Integer.parseInt(str[2]);

                        chord.join(nNode1,nNode2);
                    }// end join
                    else if(str[0].equalsIgnoreCase("stab")){
                        if (str.length > 2) {
                            System.out.println("SYNTAX ERROR: stab expects 1 parameter not " + str.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(str[1]);
                        chord.stabilize(nNode);

                    } // end stabilize
                    else if(str[0].equalsIgnoreCase("fix")){
                        if (str.length > 2) {
                            System.out.println("SYNTAX ERROR: fix expects 1 parameter not " + str.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(str[1]);
                        chord.fix_finger(nNode);
                    } // end fix
                    else if(str[0].equalsIgnoreCase("show")){
                        if (str.length > 2) {
                            System.out.println("SYNTAX ERROR: show expects 1 parameter not " + str.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(str[1]);
                        chord.show(nNode);
                    } // end show
                    else if(str[0].equalsIgnoreCase("list")){
                        if (str.length > 1) {
                            System.out.println("SYNTAX ERROR: list expects no parameter ");
                            continue;
                        }
                        chord.list();
                    }//end list
                    else{
                        System.out.println("Invalid Input");
                    }
                }catch (NumberFormatException nfe)
                {
                    System.out.println("ERROR: invalid integer");//nfe.printStackTrace();
                }
                catch (Exception e)
                {
                    System.out.println("Something went wrong");
                    e.printStackTrace();
                }
            }//end of while
        } // batch mode ends
        //*********************************** Interactive ******************************
        else if(args.length ==1)
        {
            try{
                m = Integer.parseInt(args[0]);
                if(m<=0 || m>31)
                {
                    System.out.println("Error: invalid integer " + m);
                    System.exit(1);
                }
            }
            catch (Exception e)
            {
                System.out.println("Error: invalid integer m= " + args[1]);
            }
            Chord chord = new Chord();

            while (true)
            {
                String inputLine = src.nextLine();
                String[] strArray = inputLine.split(" ");
                try{
                    if(strArray[0].equalsIgnoreCase("end")){
                        if(strArray.length>1)
                        {
                            System.out.println("SYNTAX ERROR: end expects no parameters");
                            continue;
                        }
                        //sfile.close();
                        //System.exit(0);
                        chord.end();
                    } // end command
                    if (strArray.length>1){
                        if (Integer.parseInt(strArray[1]) >= Math.pow(2, m) || Integer.parseInt(strArray[1])<0) {
                            System.out.println("ERROR: node id must be in [0," + (int) Math.pow(2, m) + ")");
                            continue;
                        }
                    } // for value check
                    if (strArray.length > 2) {
                        if (Integer.parseInt(strArray[2]) >= Math.pow(2, m) || Integer.parseInt(strArray[2])<0) {
                            System.out.println("ERROR: node id must be in [0," + (int) (Math.pow(2, m)) + ")");
                            continue;
                        }
                    } // for value check

                    if(strArray[0].equalsIgnoreCase("drop")){
                        if(strArray.length>2)
                        {
                            System.out.println("SYNTAX ERROR: drop expects 1 parameter not " + strArray.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(strArray[1]);
                        chord.drop(nNode);
                    } // end drop
                    else if(strArray[0].equalsIgnoreCase("add")){
                        if(strArray.length > 2)
                        {
                            System.out.println("SYNTAX ERROR: add expects 1 parameter not " + strArray.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(strArray[1]);
                        chord.add(nNode);
                    }//end add
                    else if(strArray[0].equalsIgnoreCase("join")){
                        if (strArray.length > 3) {
                            System.out.println("SYNTAX ERROR: join expects 2 parameters not " + strArray.length);
                            continue;
                        }
                        int nNode1 = Integer.parseInt(strArray[1]);
                        int nNode2 = Integer.parseInt(strArray[2]);

                        chord.join(nNode1,nNode2);
                    }// end join
                    else if(strArray[0].equalsIgnoreCase("stab")){
                        if (strArray.length > 2) {
                            System.out.println("SYNTAX ERROR: stab expects 1 parameter not " + strArray.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(strArray[1]);
                        chord.stabilize(nNode);

                    } // end stabilize
                    else if(strArray[0].equalsIgnoreCase("fix")){
                        if (strArray.length > 2) {
                            System.out.println("SYNTAX ERROR: fix expects 1 parameter not " + strArray.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(strArray[1]);
                        chord.fix_finger(nNode);
                    } // end fix
                    else if(strArray[0].equalsIgnoreCase("show")){
                        if (strArray.length > 2) {
                            System.out.println("SYNTAX ERROR: show expects 1 parameter not " + strArray.length);
                            continue;
                        }
                        int nNode = Integer.parseInt(strArray[1]);
                        chord.show(nNode);
                    } // end show
                    else if(strArray[0].equalsIgnoreCase("list")){
                        if (strArray.length > 1) {
                            System.out.println("SYNTAX ERROR: list expects no parameter ");
                            continue;
                        }
                        chord.list();
                    }//end list
                    else{
                        System.out.println("Invalid Input");
                    }

                }catch (NumberFormatException nfe)
                {
                    System.out.println("ERROR: invalid integer");//nfe.printStackTrace();
                }
                catch (Exception e)
                {
                    System.out.println("Something went wrong");
                    //e.printStackTrace();
                }
            }//while
        }
        else{
            System.out.println("Error : Invalid Number of arguments passed");
            System.out.println("For batch mode pass : chord -i \"input_file\" m");
            System.out.println("For interactive mode pass : chord m");
        }

    } // end of main *****************


    public int find_successor(double id,int n)
    {
        ChordNode node = getNodeFromList(n);
        if(node==null)
        {
            return (int) id;
        }
        int successor = node.getSuccessor();
        if(successor<=n)
        {
            if((n<id && id<=Math.pow(2,m)-1)||(id<= 0 && id<=successor))
            {
                return successor;
            }
            else
            {
                int n_dash = closest_preceding_node(id,n);
                /*if(chordNodes.contains(getNodeFromList(n_dash)))
                    return successor;*/
                return find_successor(id,n_dash);
            }
        }
        else{
            if(n<id && id<=successor)
                return successor;
            else
            {
                int n_dash = closest_preceding_node(id,n);
                /*if(chordNodes.contains(getNodeFromList(n_dash)))
                    return successor;*/
                return find_successor(id,n_dash);
            }
        }
    }


    public int closest_preceding_node(double id, int n)
    {

        ChordNode node = getNodeFromList(n);
        //int successor = node.getSuccessor();
        for (int i = m-1; i >=0 ; i--) {
            if(n<id){
                if(node.finger[i]> n && node.finger[i]< id)
                    return node.finger[i];
            }
            else{
                if((node.finger[i]> n && node.finger[i]<=Math.pow(2,m)-1) || (node.finger[i]>=0 && node.finger[i]<id)) //n
                {
                    return node.finger[i];
                }
            }
        }
        return n;
    }


    public void fix_finger(int n)
    {
        try{
            ChordNode node = getNodeFromList(n);

            if(chordNodes.contains(node))
            {
                //Collections.sort(nodeList);
                for (int j = 0; j <m ; j++) {
                    double nNode =  n + Math.pow(2,j);
                    double value =  Math.pow(2,m);
                    int x = (int) (nNode%value);
                    node.finger[j]=find_successor(x,n);
                }
            }
            else
                System.out.println("ERROR: Node " + n + " does not exist");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        //return node.getNodeId();
    }

    public void end(){
        System.exit(0);
    }

    public void stabilize(int n)
    {

        try{
            ChordNode current = getNodeFromList(n);

            ChordNode pred = getNodeFromList(current.getPredecessor());
            ChordNode succ = getNodeFromList(current.getSuccessor());
            if(pred!=null && !chordNodes.contains(pred)){
                current.setPredecessor(-1);
            }
            if(succ!=null && !chordNodes.contains(succ)){
                current.setSuccessor(n);
            }
            if(chordNodes.contains(current) && n!=-1)
            {
                //ChordNode current = getNodeFromList(n);
                int successorVal = current.getSuccessor();
                ChordNode successor = getNodeFromList(successorVal);
                if(successor!=null)
                {
                    int x = successor.getPredecessor();
                    if(x!=-1)
                    {
                        if(current.getSuccessor()<=n){
                            if((n<x && x<=Math.pow(2,m)-1) || (0<=x && x<current.getSuccessor()))
                            {
                                current.setSuccessor(x);
                            }
                        }
                        else
                        {
                            if(n<x && x<current.getSuccessor())
                            {
                                current.setSuccessor(x);
                            }
                        }
                    }
                    else
                    {
                        System.out.println("ERROR: Node " + n + " does not exist");
                    }
                    ChordNode newSuccessor = getNodeFromList(current.getSuccessor()); //getNodeFromList(n).getSuccessor();
                    notify(n,newSuccessor.getNodeId());
                }
                else
                {
                    return;
                }
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void notify(int n,int succ)
    {
        //this ==succ
        //n = id
        try{
            ChordNode node = getNodeFromList(succ);
            if(node.getPredecessor()==-1)
            {
                node.setPredecessor(n);
            }else if(succ<=node.getPredecessor()){
                if((node.getPredecessor()<n && n<=Math.pow(2,m)-1)|| (0<=n && n<succ))
                {
                    node.setPredecessor(n);
                }
            }
            else{
                if(node.getPredecessor()<n && n< succ)
                {
                    node.setPredecessor(n);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void drop(int n)
    {
        try{
            ChordNode node = getNodeFromList(n);

            if(chordNodes.contains(node))
            {
                ChordNode successor = getNodeFromList(node.getSuccessor());
                //nodeList.remove(Integer.valueOf(n));
                if(node.getPredecessor()!=-1)
                {
                    ChordNode predecessor = getNodeFromList(node.getPredecessor());
                    predecessor.setSuccessor(node.getSuccessor());
                }
                successor.setPredecessor(node.getPredecessor());
                chordNodes.remove(node);
                System.out.println("Dropped Node "+n);
                //check predessor for other nodes
            }
            else
                System.out.println("ERROR: Node " + n + " does not exist");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void join(int n1,int n2)
    {
        try {

            ChordNode node1 = getNodeFromList(n1);
            ChordNode node2 = getNodeFromList(n2);
            if(chordNodes.contains(node1) && chordNodes.contains(node2))
            {
                //chordNodes.remove(node);
                int x = find_successor(n1,n2);
                node1.setSuccessor(x);
                node1.setPredecessor(-1);

                //chordNodes.add(node);
            }
            else {
                if (!chordNodes.contains(node1))
                    System.out.println("ERROR: Node " + n1 + " does not exist");
                else
                    System.out.println("ERROR: Node " + n2 + " does not exist");
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void add(int n)
    {
        try{
            ChordNode nodeNew = new ChordNode(n,m);

            if(chordNodes.contains(nodeNew))
            {
                System.out.println("ERROR: Node " + n + " exists");
            }
            else {
                chordNodes.add(nodeNew);
                //nodeList.add(n);
                System.out.println("Added Node "+n);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage()
            );
        }
    }

    public void list()
    {
        try{
            Collections.sort(chordNodes,(o1, o2) -> o1.nodeId -o2.nodeId);
            System.out.print("Nodes: ");
            for (int i = 0; i < chordNodes.size()-1; i++) {
                System.out.print(chordNodes.get(i).nodeId+", ");
            }
            System.out.print(chordNodes.get(chordNodes.size()-1).nodeId);
            System.out.println();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void check_predecessor(int n)
    {
        ChordNode node = getNodeFromList(n);
        if(!chordNodes.contains(node));
            node.setPredecessor(-1);
    }

    public void show(int n)
    {
        try{
            ChordNode node = getNodeFromList(n);
            if(chordNodes.contains(node))
            {
                System.out.print("Node "+ node.getNodeId()+":");
                System.out.print(" suc "+node.getSuccessor()+",");
                if(node.getPredecessor()==-1)
                    System.out.print(" pre None:");
                else
                    System.out.print(" pre "+node.getPredecessor()+":");
                System.out.print(" finger ");
                for (int i = 0; i < node.finger.length-1; i++) {
                    System.out.print(node.finger[i]+",");
                }
                System.out.print(node.finger[(m-1)]);
                System.out.println();
            }
            else
                System.out.println("ERROR: Node " + n + " does not exist");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public ChordNode getNodeFromList(int n)
    {
        ChordNode node = null;
        for (int i = 0; i < chordNodes.size(); i++) {
            if(chordNodes.get(i).getNodeId()==n)
            {
                node = chordNodes.get(i);
                break;
            }

        }
        return node;
    }

}

class ChordNode{
    public int nodeId;
    public int successor;
    public int predecessor;
    //public Map<Integer,Integer> finger = new HashMap<>();
    public int[] finger;

    public ChordNode(int n,int m)
    {
        nodeId = n;
        successor = n;
        predecessor=-1;
        finger = new int[m];
        for (int i = 0; i < m; i++) {
            {
                //finger.put(i,n);
                finger[i]=n;
            }
        }
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getSuccessor() {
        return successor;
    }

    public void setSuccessor(int successor) {
        this.successor = successor;
        this.finger[0] = successor;
    }

    public int getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(int predecessor) {
        this.predecessor = predecessor;
    }

    public int[] getFinger() {
        return finger;
    }

    public void setFinger(int[] finger) {
        this.finger = finger;
    }


}
