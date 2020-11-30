// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment() {
        if(this.type == 1) return;
        else if(this.type == 2){
            Dictionary temp = freeBlk.getFirst();
            Dictionary newTree = new BSTree();
            while(temp != null){
                newTree.Insert(temp.address, temp.size, temp.address);
                temp = temp.getNext();
            }
            Dictionary curr1 = newTree.getFirst();
            if(curr1 == null) return;
            Dictionary curr2 = curr1.getNext();
            while(curr2 != null){
                if(curr1.address + curr1.size == curr2.address){
                    BSTree cur1 = new BSTree(curr1.address, curr1.size, curr1.size);
                    BSTree cur2 = new BSTree(curr2.address, curr2.size, curr2.size);
                    freeBlk.Delete(cur1);
                    freeBlk.Delete(cur2);
                    freeBlk.Insert(curr1.address, curr1.size + curr2.size, curr1.size + curr2.size);
                    curr2.address = curr1.address;
                    curr2.size = curr1.size + curr2.size;
                }
                curr1 = curr1.getNext();
                curr2 = curr2.getNext();
            }
            return;
        }
        else if(this.type == 3){
            Dictionary temp = freeBlk.getFirst();
            Dictionary newTree = new AVLTree();
            while(temp != null){
                newTree.Insert(temp.address, temp.size, temp.address);
                temp = temp.getNext();
            }
            Dictionary curr1 = newTree.getFirst();
            if(curr1 == null) return;
            Dictionary curr2 = curr1.getNext();
            while(curr2 != null){
                if(curr1.address + curr1.size == curr2.address){
                    AVLTree cur1 = new AVLTree(curr1.address, curr1.size, curr1.size);
                    AVLTree cur2 = new AVLTree(curr2.address, curr2.size, curr2.size);
                    freeBlk.Delete(cur1);
                    freeBlk.Delete(cur2);
                    freeBlk.Insert(curr1.address, curr1.size + curr2.size, curr1.size + curr2.size);
                    curr2.address = curr1.address;
                    curr2.size = curr1.size + curr2.size;
                }
                curr1 = curr1.getNext();
                curr2 = curr2.getNext();
            }
            return;
        }
    }
}