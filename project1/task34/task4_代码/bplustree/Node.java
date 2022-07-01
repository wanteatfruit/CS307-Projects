package bplustree;

import java.util.ArrayList;
import java.util.TreeSet;

public class Node {
    int maxD;
    int minD;
    int curD;
    Node left;
    Node right;
    Node parent;
    ArrayList<Node> child;
    ArrayList<Integer> keys;
    boolean isLeaf=false;

    public Node(int max,ArrayList<Integer> keys,ArrayList<Node> child,boolean isLeaf){
        maxD=max;
        minD=(maxD+1)/2;
        curD=0;
        this.keys=keys;
        this.child=child;
        this.isLeaf=isLeaf;
    }
}
