package bplustree;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.util.zip.Inflater;

//https://github.com/shandysulen/B-Plus-Tree/blob/40d3944257d466f1c7c0c8fcf5a8b7b5de41bc04/bplustree.java#L40
//https://iq.opengenus.org/b-tree-search-insert-delete-operations/
//https://en.wikipedia.org/wiki/B%2B_tree
public class Bplustree {

    public Node root;
    public int maxD;
    public HashMap<Integer, String> map = new HashMap<>();

    public static void main(String[] args) {
        Bplustree tree = new Bplustree();
        tree.maxD = 16000;
        //Node root = new Node(4, new ArrayList<>(), new ArrayList<>(), true);
        //tree.root=root;

        for (int i = 0; i < 50000; i++) {
            tree.insert(i, String.valueOf(i));
        }
//        tree.insert(1, "1");
//        tree.insert(3, "3");
//        tree.insert(16, "16");
//        tree.insert(9, "9");
//        tree.insert(4, "4");
//        tree.insert(25, "25");
//        tree.insert(13,"13");
//        tree.insert(36,"I want to sleep");
        Node root = tree.root;
        String key = (String) tree.searchValue(13);
        tree.root = root;
        ArrayList<String> rangeTest = new ArrayList<>();
        rangeTest= (ArrayList<String>) tree.rangeSearch(10,20);
        System.out.println(rangeTest);
    }
    //update value of key in map, and return updated value
    public Object updateValue(int key,String newValue){
        if (this.root == null) return null;
        Node root = this.root;
        if (root.isLeaf) {
            int idx = binarySearch(root.keys, key);
            if (idx == root.keys.size()) {
                System.out.println("key not found");
                return -1;
            } else {
                int id=root.keys.get(idx);
                map.replace(id,newValue);
                return map.get(id);
            }
        } else {
            ArrayList<Integer> curKeys = root.keys;
            int nxt = binarySearch(curKeys, key);
            this.root = root.child.get(nxt);
            return searchValue(key);
        }
    }

    //search for corresponding value of key
    public Object searchValue(int key) {//will change original root
        if (this.root == null) return null;
        Node root = this.root;
        if (root.isLeaf) {
            int idx = binarySearch(root.keys, key);
            if (idx == root.keys.size()) {
                System.out.println("key not found");
                return -1;
            } else {
                int id=root.keys.get(idx);
                return map.get(id);
            }
        } else {
            ArrayList<Integer> curKeys = root.keys;
            int nxt = binarySearch(curKeys, key);
            this.root = root.child.get(nxt);
            return searchValue(key);
        }
    }

    public Object rangeSearch(int min, int max) { //search for keys in a range (between..and..),min inclusive,max exclusive
        ArrayList<String> values = new ArrayList<>();
        int numValues = max - min;
        int cntValues = 0;
        //first search for node with min key
        Node minNode = searchLeaf(min);
        int minIdx = binarySearch(minNode.keys, min);
        for (int i = minIdx; i < minNode.keys.size(); i++) {
            values.add(map.get(minNode.keys.get(i)));
            cntValues++;
            if (cntValues >= numValues) {
                return values;
            }
        }
        Node cur = minNode.right;
        while (cur != null) {
            for (int i = 0; i < cur.keys.size(); i++) {
                values.add(map.get(cur.keys.get(i)));
                cntValues++;
                if (cntValues >= numValues) {
                    return values;
                }
            }
            cur = cur.right;
        }
        return values;
    }

    //search for leaf to insert
    Node searchLeaf(int key) {//will change original root
        if (this.root == null) return null;
        Node root = this.root;
        if (root.isLeaf) {
            return root;
        } else {
            ArrayList<Integer> curKeys = root.keys;
            int nxt = binarySearch(curKeys, key);
            this.root = root.child.get(nxt);
            return searchLeaf(key);
        }

    }


    public void insert(int key, String val) {
        if (root == null) {//empty tree
            Node newRoot = new Node(this.maxD, new ArrayList<>(), new ArrayList<>(), true);
            newRoot.keys.add(key);
            this.map.put(key, val);
            this.root = newRoot;
        } else {
            //record current root
            Node originalRoot = this.root;
            //find leaf node
            Node leaf = searchLeaf(key);
            this.root = originalRoot;

            //if leaf is already full
            if (leaf.keys.size() >= maxD - 1) {

                //first insert it into sorted position
                ArrayList<Integer> keys = addToKeys(key, leaf);
                this.map.put(key, val);

                //split half
                int halfPoint = (int) Math.ceil((leaf.keys.size()) / 2.0);
                int halfPointVal = keys.get(halfPoint);
                ArrayList<Integer> secondHalf = new ArrayList<>();
                ArrayList<Integer> firstHalf = new ArrayList<>();
                for (int i = 0; i < halfPoint; i++) {
                    firstHalf.add(keys.get(i));
                }
                for (int i = halfPoint; i < keys.size(); i++) {
                    secondHalf.add(keys.get(i));
                }
                leaf.keys = firstHalf;
                //the half point goes into parent
                if (leaf.parent == null) {
                    //if grow at root
                    ArrayList<Integer> parentKeys = new ArrayList<>();
                    parentKeys.add(halfPointVal);
                    Node parent = new Node(this.maxD, parentKeys, new ArrayList<>(), false);
                    leaf.parent = parent;
                    parent.child.add(leaf);
                } else {
                    addToKeys(halfPointVal, leaf.parent);
                }

                //create other leaf
                Node newLeaf = new Node(this.maxD, secondHalf, new ArrayList<>(), true);
                newLeaf.parent = leaf.parent;

                //update parent's child
                updateChildren(leaf.parent, newLeaf);


                //update linked leaf
                insertLinkedList(newLeaf, leaf);
                if (this.root == null) {
                    this.root = leaf.parent;
                } else {//if parent full
                    Node pa = leaf.parent;
                    while (pa != null) { //keep going up
                        if (pa.keys.size() >= maxD - 1) {
                            splitInternal(pa);
                            pa = pa.parent;
                        } else {
                            break;
                        }
                    }
                    this.root = pa;
                }


            } else {//not full
                //first insert it into sorted position
                ArrayList<Integer> keys = addToKeys(key, leaf);
                this.map.put(key, val);

            }
            //this.root=originalRoot;
        }
    }

    void delete(int key) {

    }

    void splitInternal(Node node) {
        ArrayList<Integer> keys = node.keys;
        int halfPoint = (int) Math.ceil((maxD + 1) / 2.0);
        int halfPointVal = keys.get(halfPoint);
        ArrayList<Integer> firstHalf = (ArrayList<Integer>) keys.subList(0, halfPoint + 1);//included half point number
        node.keys = firstHalf;
        ArrayList<Integer> secondHalf = (ArrayList<Integer>) keys.subList(halfPoint + 1, keys.size());
        if (node.parent == null) {
            //if grow at root
            ArrayList<Integer> parentKeys = new ArrayList<>();
            parentKeys.add(halfPointVal);
            Node parent = new Node(this.maxD, parentKeys, new ArrayList<>(), false);
            node.parent = parent;
            parent.child.add(node);
        } else {
            addToKeys(halfPointVal, node.parent);
        }

        //create other internal node
        Node newNode = new Node(this.maxD, secondHalf, new ArrayList<>(), false);
        newNode.parent = node.parent;

        //update parent's child
        updateChildren(node.parent, newNode);

    }

    void insertLinkedList(Node toInsert, Node leftNode) {
        leftNode.right = toInsert.right;
        if (toInsert.right != null) {
            toInsert.right.left = toInsert;
        }
        leftNode.right = toInsert;
        toInsert.left = leftNode;
    }

    void updateChildren(Node parent, Node child) {
        int childMax = child.keys.get(child.keys.size() - 1);
        int childMin = child.keys.get(0);
//        Node left=child.left;
//        Node right=child.right;
//        int leftMax=left.keys.get(left.keys.size()-1);
//        int rightMin=right.keys.get(0);
        int minIdx = binarySearch(parent.keys, childMin);
        int maxIdx = binarySearch(parent.keys, childMax);
        int parentMin = parent.keys.get(minIdx);
        //int parentMax=parent.keys.get(maxIdx);
        if (parentMin == childMin) {
            parent.child.add(minIdx + 1, child);
        } else {
            parent.child.add(minIdx, child);
        }

        //parent.curD++;
    }


    private ArrayList<Integer> addToKeys(int key, Node node) {
        ArrayList<Integer> keys = node.keys;
        int idx = binarySearch(keys, key);
        keys.add(idx, key);
//        for (int i = 0; i < keys.size(); i++) {
//            if (key > keys.get(i)) {
//                keys.add(i+1, key);
//                break;
//            }
//        }
        return keys;
    }


    static int binarySearch(ArrayList<Integer> list, int key) { //insert position, if not found, return the idx bigger than current key
        int left = 0, right = list.size() - 1;
        int res = list.size();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (key <= list.get(mid)) {
                res = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return res;
    }
}

