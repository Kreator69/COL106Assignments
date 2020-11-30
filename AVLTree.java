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

    private int height(AVLTree node){
        if(node == null) return 0;
        else return node.height;
    }

    private boolean ifExact(Dictionary e){
        return (this.key == e.key && this.address == e.address && this.size == e.size);
    }

    private int updateHeight(){
        return (Math.max(height(this.right), height(this.left)) + 1);
    }

    private void checkBalance(AVLTree node){
        if(node.parent == null) return;
        if( ( height(node.left) - height(node.right)) > 1 || ( height(node.left) - height(node.right) ) < -1 ){
            rebalance(node);
        }
        node.updateHeight();
        checkBalance(node.parent);
    }

    private void rebalance(AVLTree node){
        if(( height(node.left) - height(node.right)) > 1){
            if( height(node.left.left) >= height(node.left.right)){
                node = rightRotate(node);
            }
            else node = leftRightRotate(node);
        }
        else{
            if( height(node.right.right) >= height(node.right.left)){
                node = leftRotate(node);
            }
            else node = rightLeftRotate(node);
        }
    }

    private AVLTree rightRotate(AVLTree node){
        AVLTree temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.parent = node.parent;
        node.parent = temp;
        node.updateHeight();
        temp.updateHeight();
        return temp;
    }

    private AVLTree leftRotate(AVLTree node){
        AVLTree temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.parent = node.parent;
        node.parent = temp;
        node.updateHeight();
        temp.updateHeight();
        return temp;
    }

    private AVLTree leftRightRotate(AVLTree node){
        node.left = leftRotate(node.left);
        AVLTree temp = rightRotate(node);
        return temp;
    }

    private AVLTree rightLeftRotate(AVLTree node){
        node.right = righttRotate(node.right);
        AVLTree temp = leftRotate(node);
        return temp;
    }

    public AVLTree Insert(int address, int size, int key) 
    {
        AVLTree curr = this.getSentinel();
        if(curr.right == null){
            AVLTree newNode = new AVLTree(address, size, key);
            curr.right = newNode;
            newNode.parent = curr;
            curr.right.height++;
            return newNode;
        }
        else{
            AVLTree temp = curr.right.add(address, size, key);
            checkBalance(temp);
        }
        return null;
    }

    private AVLTree add(int address, int size, int key){
        if(this.Compare(address, key) == 1){
            if(this.right == null){
                AVLTree newNode = new AVLTree(address, size, key);
                this.right = newNode;
                newNode.parent = this;
                return newNode;
            }
            else return this.right.add(address, size, key);
        }
        else{
            if(this.left == null){
                AVLTree newNode = new AVLTree(address, size, key);
                this.left = newNode;
                newNode.parent = this;
                return newNode;
            }
            else return this.left.add(address, size, key);
        }
    }

    public boolean Delete(Dictionary e)
    {
        AVLTree curr = this.getRoot();
        AVLTree node = this.getSentinel();
        if (curr == null) return false;
        else{
            while(curr.ifExact(e) == false && curr != null){
                if(curr.Compare(e.address, e.key) == 1){
                    node = curr;
                    curr = curr.left;
                }
                else if(curr.Compare(e.address, e.key) == 2){
                    node = curr;
                    curr = curr.right;
                    }
                }
            }
        if(curr == null) return false;
        else if(curr.right == null && curr.left == null){
            if(node.Compare(curr.address, curr.key) == 1 ){
                node.left = null;
                checkBalance(node);
            }
            else if(node.Compare(curr.address, curr.key) == 2 ){
                node.right = null;
                checkBalance(node);
            }
            return true;
        }
        else if(curr.right != null && curr.left == null){
            if(node.right == curr){
                node.right = curr.right;
                curr.right.parent = node;
                checkBalance(node);
                return true;
            }
            else{
                node.left = curr.right;
                curr.right.parent = node;
                checkBalance(node);
                return true;
            }
        }
        else if(curr.right == null && curr.left != null){
            if(node.right == curr){
                node.right = curr.left;
                curr.left.parent = node;
                checkBalance(node);
                return true;
            }
            else{
                node.left = curr.left;
                curr.left.parent = node;
                checkBalance(node);
                return true;
            }
        }
        else{
            AVLTree succ = curr.getNext();
            AVLTree temp = new AVLTree(curr.address, curr.size, curr.key);
            if(curr == curr.parent.left){
                curr.parent.left = temp;
            }
            else{
                curr.parent.right = temp;
            }
            curr.left.parent = temp;
            curr.right.parent = temp;
            temp.left = curr.left;
            temp.right = curr.right;
            temp.parent = curr.parent;
            if(succ.right != null){
                if(succ.parent.left == succ) succ.parent.left = succ.right;
                else if(succ.parent.right == succ) succ.parent.right = succ.right;
                succ.right.parent = succ.parent;
                temp.address = succ.address;
                temp.key = succ.key;
                temp.size = succ.size;
                checkBalance(succ.parent);
                return true;
            }
            else{
                temp.address = succ.address;
                temp.key = succ.key;
                temp.size = succ.size;
                if(succ.parent.left == succ) succ.parent.left = null;
                else if(succ.parent.right == succ) succ.parent.right = null;
                checkBalance(succ.parent);
                return true;
            }
        }
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
        return true;
    }
}


