// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        Dictionary curr = freeBlk.Find(blockSize, false);
        if(blockSize <= 0) return -1;
        else if(curr != null){
            allocBlk.Insert(curr.address, blockSize, curr.address);
            if(blockSize != curr.size) freeBlk.Insert(curr.address + blockSize, curr.size - blockSize, curr.size - blockSize);
            freeBlk.Delete(curr);
            return curr.address;
        }
        return -1;
    }
    
    public int Free(int startAddr) {
        Dictionary curr = allocBlk.Find(startAddr, true);
        if(curr!= null){
            allocBlk.Delete(curr);
            freeBlk.Insert(curr.address, curr.size, curr.size);
            return 0;
        }
        return -1;
    }
}