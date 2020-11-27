// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }

    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Initiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List curr = new A1List(address, size, key);
        curr.next = this.next;
        curr.prev = this;
        this.next.prev = curr;
        this.next = curr;
        return curr;
    }

    public boolean Delete(Dictionary d)
    {
        A1List curr = this.getFirst();
        while(curr != null){
            if(curr.key == d.key && curr.address == d.address && curr.size == d.size) break;
            curr = curr.next;
        }
        if(curr == null) return false;
        else{
            curr.next.prev = curr.prev;
            if(curr.prev != null) curr.prev.next = curr.next;
            curr = null;
            return true;
        }
    }

    public A1List Find(int k, boolean exact)
    {
        A1List curr = this;
        curr = curr.getFirst();
        if(exact){
            while(curr != null){
                if(curr.key == k) return curr;
                curr = curr.next;
            }
            
        }
        else{
            while(curr != null){
                if(curr.key >= k) return curr;
                curr = curr.next;
            }
        }
        return null;
    }

    public A1List getFirst()
    {
        A1List curr = this;
        while(curr.prev != null){
            curr = curr.prev;
        }
        if(curr.next.next == null) return null;
        else return curr.next;
    }
    
    public A1List getNext()
    {
        if(this.next.next == null) return null;
        else return this.next;
    }

    public boolean sanity()
    {
    	A1List p1 = this;
        A1List p2 = this;
        while(p1 != null && p2!= null && p2.next!= null){
            p1 = p1.next;
            p2 = p2.next.next;
            if(p1 == p2) return false;
        }
        p1= this;
        p2= this;
        while(p1 != null && p2 != null && p2.prev != null){
            p1 = p1.prev;
            p2 = p2.prev.prev;
            if(p1 == p2) return false;
        }
        p1 = this.getFirst();
        if(p1 != null){
            if(p1.prev.prev != null || p1.prev.address > 0) return false;
            while(p1.next != null){
                if(p1.next.prev != p1 || p1.prev.next != p1) return false;
                p1 = p1.next;
            }
            if(p1.next != null || p1.address > 0) return false;
        }
        return true;
    }

}


