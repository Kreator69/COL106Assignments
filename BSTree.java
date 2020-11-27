// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){ 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key);
    }

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

    public BSTree Insert(int address, int size, int key) 
    {
        BSTree curr = this.getSentinel();
        if (curr.right == null){
            curr.right = new BSTree(address, size, key);
            curr.right.parent = curr;
            return curr.right;
        }
        else{
            BSTree node = this.getRoot();
            while(node != null){
                if(node.Compare(address, key) == 1){
                    curr = node;
                    if(node.left == null){
                        BSTree ans = new BSTree(address, size, key);
                        ans.parent = curr;
                        curr.left = ans;
                        return ans;
                    }
                    node = node.left;
                }
                else if(node.Compare(address, key) == 2){
                    curr = node;
                    if(node.right == null){
                        BSTree ans = new BSTree(address, size, key);
                        ans.parent = curr;
                        curr.right = ans;
                        return ans;
                    }
                    node = node.right;
                }
            }
            return null;
        }
    }

    public boolean Delete(Dictionary e)
    {
        BSTree curr = this.getRoot();
        BSTree node = this.getSentinel();
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
            if(node.Compare(curr.address, curr.key) == 1 ) node.left = null;
            else if(node.Compare(curr.address, curr.key) == 2 ) node.right = null;
            return true;
        }
        else if(curr.right != null && curr.left == null){
            if(node.right == curr){
                node.right = curr.right;
                curr.right.parent = node;
                return true;
            }
            else{
                node.left = curr.right;
                curr.right.parent = node;
                return true;
            }
        }
        else if(curr.right == null && curr.left != null){
            if(node.right == curr){
                node.right = curr.left;
                curr.left.parent = node;
                return true;
            }
            else{
                node.left = curr.left;
                curr.left.parent = node;
                return true;
            }
        }
        else{
            BSTree succ = curr.getNext();
            BSTree temp = new BSTree(curr.address, curr.size, curr.key);
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
                return true;
            }
            else{
                temp.address = succ.address;
                temp.key = succ.key;
                temp.size = succ.size;
                if(succ.parent.left == succ) succ.parent.left = null;
                else if(succ.parent.right == succ) succ.parent.right = null;
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

    private boolean ifParent(){
        if(this.left != null && this.right != null){
            if(this.left.parent != this || this.right.parent != this) return false;
            return (this.left.ifParent() && this.right.ifParent());
        }
        else if(this.left == null && this.right != null){
            if(this.right.parent != this) return false;
            return this.right.ifParent();
        }
        else if(this.left != null && this.right == null){
            if(this.left.parent != this) return false;
            return this.left.ifParent();
        }
        else return true;
    }

    private boolean checkCycleRoot(){
        BSTree curr1 = this;
        BSTree curr2 = this;
        while(curr2.parent != null && curr2.parent.parent != null){
            curr1 = curr1.parent;
            curr2 = curr2.parent.parent;
            if(curr1 == curr2) return false;
        }
        return curr2.ifParent();
    }

    public boolean sanity()
    {
        if(this.checkCycleRoot() == false) return false;
        BSTree curr = this.getFirst();
        while(curr != null){
            if(curr.parent == null) return false;
            if(curr.parent.parent == null){
                if(curr.parent.key != -1 || curr.parent.address != -1 || curr.parent.size != -1 || curr.parent.left != null) return false;
            }
            if( (curr.left != null && curr.left.parent != curr) || (curr.right != null &&curr.right.parent != curr)) return false;
            if(curr.key < 0 || curr.address < 0 || curr.size < 0) return false;
            if( curr.left != null && curr.Compare(curr.left.address, curr.left.key) != 1) return false; 
            if( curr.right != null && curr.Compare(curr.right.address, curr.right.key) != 2) return false;
            curr = curr.getNext();
        }
        return true;
    }
    
}


 


