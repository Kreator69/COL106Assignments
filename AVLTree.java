// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    private BSTree getSentinel(){
        BSTree curr = this;
        while(curr.parent != null){
            curr = curr.parent;
        }
        return curr;
    }

    private BSTree getRoot(){
        if(this.getSentinel() == null) return null;
        return this.getSentinel().right;
    }

    private int Compare(int address,int key){
        if(this.key > key) return 1;
        else if(this.key < key) return 2;
        else{
            if(this.address > address) return 1;
            else if(this.address < address) return 2;
            else return 3;
        }
    }

    private boolean ifExact(Dictionary e){
        return (this.key == e.key && this.address == e.address && this.size == e.size);
    }

    private void checkBalance(AVLTree node){
        if( (node.left.height - node.right.height) > 1 || (node.left.height - node.right.height) < -1 ){
            rebalance(node);
        }
        if(node.parent == null) return;
        checkBalance(node.parent);
    }

    private void rebalance(AVLTree node){
        if((node.left.height - node.right.height) > 1){
            if(node.left.left.height > node.left.right.height){
                node = rightRotate(node);
            }
            else node = leftRightRotate(node);
        }
        else{
            if(node.right.right.height > node.right.left.height){
                node = leftRotate(node);
            }
            else node = rightLeftRotate(node);
        }
        if(node.parent == null) root = node;
    }

    private AVLTree rightRotate(AVLTree node){
        
    }

    public AVLTree Insert(int address, int size, int key) 
    {
        return null;
    }

    public boolean Delete(Dictionary e)
    {
        return false;
    }

    public BSTree Find(int key, boolean exact)
    {
        BSTree curr = this.getRoot();
        BSTree ans = null;
        while(curr != null){
            if(curr.Compare(0, key) == 3){
                ans = curr;
                break;
            }
            else if(curr.Compare(0, key) == 1){
                ans = curr;
                curr = curr.left;
            }
            else curr = curr.right;
        }
        if(ans == null) return null;
        else{
            if(exact && ans.key != key) return null;
            return ans;
        }
    }

    public BSTree getFirst()
    {
        BSTree curr = this.getRoot();
        if(curr == null) return null;
        else{
            while(curr.left != null) curr = curr.left;
            return curr;
        }
    }

    public BSTree getNext()
    {
        if(this.right == null){
            BSTree curr = this;
            BSTree node = this.parent;
            if(this.parent == null) return null;
            else{
                while(node.left != curr && node.parent != null){
                    curr = node;
                    node = node.parent;
                }
                if(node.parent == null) return null;
                else return node;
            }
        }
        else{
            BSTree curr = this.right;
            while(curr.left != null) curr = curr.left;
            return curr;
        }
    }

    public boolean sanity()
    { 
        return false;
    }
}


