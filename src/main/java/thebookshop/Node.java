package thebookshop;

import org.apache.commons.codec.digest.DigestUtils;

public class Node{
    protected Book book;
    protected Node left;
    protected Node right;
    protected String color;
    protected String hash;
    protected Node parent;

    /**
     * Creates a node with default values
     */
    Node(){
        parent = null;
        book = null;
        left = null;
        right = null;
        hash = "";
    }

    /**
     * Creates a Node from a book and sets the node's hash
     * @param book the book of the node
     * @see Book Book
     * @see DigestUtils DigestUtils.sha256Hex
     */
    public Node(Book book) {
        this.book = book;
        this.hash = DigestUtils.sha256Hex(book.indexer);
    }

    /**
     * Crestes a node that mirrors another node
     * @param node
     */
    public Node(Node node) {
        this.book = node.book;
        this.color = node.color;
        this.left = node.left;
        this.right = node.right;
        this.hash = node.hash;
    }

    /**
     * Checks if this node has 1 or 0 children
     * @return true if 1 or 0 children false ootherwise
     */
    boolean hasOneOrZeroChildren(){
        return right == null || left == null;
    }

    /**
     * Checks if this node is the left child of its parent
     * @return true if theis node is the left child false otherwise
     */
    boolean isLeftChild(){
        return this == parent.left;
    };

    /**
     * Checks if this node is the right child of its parent
     * @return true if theis node is the right child false otherwise
     */
    boolean isRightChild(){
        return this == parent.right;
    };

    /**
     * Checks if this node is the left child of a given parent
     * @return true if theis node is the left child false otherwise
     */
    boolean isLeftChildOf(Node parent){
        return this == parent.left;
    };

    /**
     * Checks if this node is the right child of a given parent
     * @return true if theis node is the right child false otherwise
     */
    boolean isRightChildOf(Node parent){
        return this == parent.right;
    };

    /**
     * Gets grandparent of this node
     * @return the grandparent node (node.parent.parent)
     */
    Node getGrandParent(){
        return parent.getParent();
    }

    /**
     * Gets the uncle of this node
     * @return the uncle node
     */
    Node getUncle(){
        return parent.getSibling();
    }

    /**
     * Replace child node with new node
     * @param oldChild The old child node
     * @param newChild The new child node
     */
   void replaceChild(Node oldChild, Node newChild){
       if (oldChild.isLeftChildOf(this)) {
          left = newChild;
       } else if (oldChild.isRightChildOf(this)) {
          right = newChild;
       } else {
           throw new IllegalStateException("Node is not a child of its parent");
       }
    }

    /**
     * Checks if node color is black
     * @return true if node color is black, false otherwise
     */
    boolean isBlack(){
        return color.equals("BLACK");
    }

    /**
     * Gets sibling node of this node
     * @return the sibling node or null
     * @throws IllegalStateException if node
     */
    Node getSibling(){
        if (this == parent.left) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    /**
     * Rotates node color
     */
    void switchColor(){
        if(color.equals("RED")){
            color = "BLACK";
        }else{
            color = "RED";
        }
    }

    /**
     * Checks of node has a left child
     * @return true if node has a left child, false otherwise
     */
    boolean hasLeftChild(){
       return left != null;
    }
    /**
     * Checks of node has a right child
     * @return true if node has a right child, false otherwise
     */
    boolean hasRightChild() {
        return right != null;
    }

    //Getters and setters

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
