package thebookshop;

import org.apache.commons.codec.digest.DigestUtils;

public class Node{
    protected Book book;
    protected Node left;
    protected Node right;
    protected Color color;
    protected Node parent;
    protected String key;

    /**
     * Creates a node with default values
     */
    Node(){
        parent = null;
        book = null;
        left = null;
        right = null;
        color = Color.RED;
    }

    /**
     * Creates a Node from a book and sets the node's hash
     * @param book the book of the node
     * @see Book Book
     * @see DigestUtils DigestUtils.sha256Hex
     */
    public Node(Book book) {
        this.book = book;
        key = book.indexer;
        color = Color.RED;
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
        this.key = node.key;
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
        return this == this.parent.left;
    };

    /**
     * Checks if this node is the right child of its parent
     * @return true if theis node is the right child false otherwise
     */
    boolean isRightChild(){
        return this == this.parent.right;
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
        return this.parent.getSibling();
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
        if (this == this.parent.left) {
            return this.parent.right;
        } else {
            return this.parent.left;
        }
    }

    /**
     * Rotates node color
     */
    void switchColor() {
        if (color == Color.RED) {
            color = Color.BLACK;
        } else {
            color = Color.RED;
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

    public Node getGrandparent() {
        if (this.isParent()) {
            return this.getParent().getParent();
        } else {
            return null;
        }
    }


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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public boolean isRed() {
        return this.color == Color.RED;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isParent() {
        return this.parent != null;
    }

    public boolean isUncle() {
        if (this.isParent()) {
            if (this.isLeftChild()) {
                return this.getParent().getRight() != null;
            } else {
                return this.getParent().getLeft() != null;
            }
        } else {
            return false;
        }
    }


    public boolean isGrandparent() {
        if (this.isParent()) {
            return this.getParent().getParent() != null;
        } else {
            return false;
        }
    }

    public boolean isSibling() {
        if (this.isParent()) {
            if (this.isLeftChild()) {
                return this.getParent().getRight() != null;
            } else {
                return this.getParent().getLeft() != null;
            }
        } else {
            return false;
        }
    }

    public boolean isChildOf(Node node) {
        if (this.isParent()) {
            if (this.isLeftChild()) {
                return this.getParent().getLeft() == node;
            } else {
                return this.getParent().getRight() == node;
            }
        } else {
            return false;
        }
    }


    public Node getParent(){
        return parent;
    }

    public boolean isSiblingOf(Node node) {
        if (this.isParent()) {
            if (this.isLeftChild()) {
                return this.getParent().getRight() == node;
            } else {
                return this.getParent().getLeft() == node;
            }
        } else {
            return false;
        }
    }

    public boolean isParentOf(Node node) {
        return this.getLeft() == node || this.getRight() == node;
    }

    public boolean isGrandparentOf(Node node) {
        if (this.isParent()) {
            return this.getParent().getParent() == node;
        } else {
            return false;
        }
    }

    public boolean isUncleOf(Node node) {
        if (this.isParent()) {
            if (this.isLeftChild()) {
                return this.getParent().getRight() == node;
            } else {
                return this.getParent().getLeft() == node;
            }
        } else {
            return false;
        }
    }

    public enum Color{
        BLACK,
        RED
    }
}

