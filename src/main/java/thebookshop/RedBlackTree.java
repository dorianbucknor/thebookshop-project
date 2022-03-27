package thebookshop;

import org.apache.commons.codec.digest.DigestUtils;


class RedBlackTree {
    private Node root;
    private int size;

    /**
     * Creates a new Red-Black Tree and initialize root node and size.
     * @see Node Node
     */
    public RedBlackTree() {
        root = null;
        size = 0;
    }

    /**
     * Searches tree for a node with the given key. Uses SHA256HEX hashing for keys.
     * @param key The search key for the node.
     * @return The node or null if none found.
     * @see DigestUtils DigestUtils.sha256Hex();
     * @see Node Node
     */
    protected Node searchForNode(String key) {
        Node node = root;
        String keyHash = DigestUtils.sha256Hex(key);

        while (node != null) {
            int compareResult = keyHash.compareTo(node.hash);
            if (compareResult == 0) {
                return node;
            } else if (compareResult < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * Inserts a new node into tree.
     * @param book The book for the node to add to tree
     * @throws IllegalArgumentException if node already exists
     * @see Book Book
     */
    protected void insertNode(Book book) {
        Node node = root;
        Node parent = null;
        String keyHash = DigestUtils.sha256Hex(book.indexer);

        while (node != null) {
            parent = node;
            int compareResult = keyHash.compareTo(node.hash);
            if (compareResult == 0) {
                throw new IllegalArgumentException("Tree already contains a node with key " + book.indexer);
            } else if (compareResult > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        // Insert new node
        Node newNode = new Node(book);
        newNode.color = "RED";

        if (parent == null) {
            root = newNode;
        } else {
            int compareResult = keyHash.compareTo(parent.hash);
            if (compareResult < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
        newNode.parent = parent;

        recolor(newNode);
        size++;
    }



    /**
     * Fixes red-black colors by recolouring.
     * @param node the node to start recoloring from
     * @see Node Node
     */
    private void recolor(Node node) {
        Node nodeParent = node.parent;

        // Case 1: Parent is null, we've reached the root, the end of the recursion
        if (nodeParent == null) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // node.color = BLACK;
            return;
        }

        // Parent is black --> nothing to do
        if (nodeParent.isBlack()) {
            return;
        }

        // From here on, nodeParent is red
        Node grandparent = node.getGrandParent();

        // Case 2:
        // Not having a grandparent means that nodeParent is the root. If we enforce black roots
        // (rule 2), grandparent will never be null, and the following if-then block can be
        // removed.
        if (grandparent == null) {
            // As this method is only called on red nodes (either on newly inserted ones - or -
            // recursively on red grandparents), all we have to do is to recolor the root black.
            nodeParent.color = "BLACK";
            return;
        }

        // Get the uncleNode (maybe null/nil), in which case its color is BLACK)
        Node uncleNode = node.getUncle();

        // Case 3: Uncle is red -> recolor nodeParent, grandparent and uncleNode
        if (uncleNode != null && uncleNode.color.equals("RED")) {
            nodeParent.color = "BLACK";
            grandparent.color = "RED";
            uncleNode.color = "BLACK";

            recolor(grandparent);
        }

        else if (nodeParent.isLeftChild()) {
            // Case 4a: Uncle is black and node is left->right "inner child" of its grandparent
            if (node.isRightChild()) {
                rotateLeft(nodeParent);


                // Let "nodeParent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                nodeParent = node;
            }

            // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
            rotateRight(grandparent);

            // Recolor original nodeParent and grandparent
            nodeParent.color = "BLACK";
            grandparent.color = "RED";
        }
        else {
            // Case 4b: Uncle is black and node is right->left "inner child" of its grandparent
            if (node.isLeftChild()) {
                rotateRight(nodeParent);

                // Let "nodeParent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                nodeParent = node;
            }

            // Case 5b: Uncle is black and node is right->right "outer child" of its grandparent
            rotateLeft(grandparent);

            // Recolor original nodeParent and grandparent
            nodeParent.color = "BLACK";
            grandparent.color = "RED";
        }
    }

    /**
     * Removes a node from the tree.
     * @param key The node key.
     * @see Node Node
     */
    protected Node deleteNode(String key) {
        // Find the node to be deleted
        Node node = searchForNode(key);

        // Node not found
        if (node == null) {
            throw new IllegalArgumentException("Node not found!");
        }

        // At this point, "node" is the node to be deleted
        size--;
        // In this variable, we'll store the node at which we're going to start to fix the R-B
        // properties after deleting a node.
        Node movedUpNode;
        String deletedNodeColor;

        // Node has zero or one child
        if (node.hasOneOrZeroChildren()) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        }
        // Node has two children
        else {
            // Find minimum node of right subtree ("inorder successor" of current node)
            Node inOrderSuccessor = getLeftMostNode(node.right);

            // Copy inorder successor's data to current node (keep its color!)
            node.hash = inOrderSuccessor.hash;
            node.book = inOrderSuccessor.book;

            // Delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        if (deletedNodeColor.equals("BLACK")) {
            fixTreeAfterDelete(movedUpNode);

            // Remove the temporary NIL node
            if (movedUpNode.getClass() == NilNode.class) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null);
            }
        }
        return node;
    }

    /**
     * Delete a node with one or zero child
     * @param node The node to delete
     * @return The node replace deleted node
     * @see Node Node
     */
    private Node deleteNodeWithZeroOrOneChild(Node node) {
        // Node has ONLY a left child --> replace by its left child
        if (node.hasLeftChild()) {
            replaceParentsChild(node.parent, node, node.left);
            return node.left; // moved-up node
        }

        // Node has ONLY a right child --> replace by its right child
        else if (node.hasRightChild()) {
            replaceParentsChild(node.parent, node, node.right);
            return node.right; // moved-up node
        }

        // Node has no children -->
        // * node is red --> just remove it
        // * node is black --> replace it by a temporary NIL node (needed to fix the R-B rules)
        else {
            Node newChild = node.color.equals("BLACK") ? new NilNode() : null;
            replaceParentsChild(node.parent, node, newChild);
            return newChild;
        }
    }

    /**
     * Gets left-most node
     * @param node The node to start at
     * @return Left-most node
     * @see  Node Node
     */
    private Node getLeftMostNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Fixes tree properties after deletion of a node.
     * @param node The node to start from.
     * @see Node Node
     */
    private void fixTreeAfterDelete(Node node) {
        // Case 1: Examined node is root, end of recursion
        if (node == root) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // node.color = BLACK;
            return;
        }

        Node sibling = node.getSibling();

        // Case 2: Red sibling
        if (sibling.color.equals("RED")) {
            handleRedSibling(node, sibling);
            sibling = node.getSibling(); // Get new sibling for fall-through to cases 3-6
        }

        // Cases 3+4: Black sibling with two black children
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = "RED";

            // Case 3: Black sibling with two black children + red parent
            if (node.parent.color.equals("RED")) {
                node.parent.color = "BLACK";
            }

            // Case 4: Black sibling with two black children + black parent
            else {
                fixTreeAfterDelete(node.parent);
            }
        }

        // Case 5+6: Black sibling with at least one red child
        else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }

    /**
     *
     * @param node
     * @param sibling
     */
    private void handleRedSibling(Node node, Node sibling) {
        // Recolor...
        sibling.color = "BLACK";
        node.parent.color = "RED";

        // ... and rotate
        if (node == node.parent.left) {
            rotateLeft(node.parent);
        } else {
            rotateRight(node.parent);
        }
    }

    /**
     *
     * @param node
     * @param sibling
     */
    private void handleBlackSiblingWithAtLeastOneRedChild(Node node, Node sibling) {
        // Case 5: Black sibling with at least one red child + "outer nephew" is black
        // --> Recolor sibling and its child, and rotate around sibling
        if (node.isLeftChild() && isBlack(sibling.right)) {
            sibling.left.color = "BLACK";
            sibling.color = "RED";
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (node.isRightChild() && isBlack(sibling.left)) {
            sibling.right.color = "BLACK";
            sibling.color = "RED";
            rotateLeft(sibling);
            sibling = node.parent.left;
        }

        // Fall-through to case 6...

        // Case 6: Black sibling with at least one red child + "outer nephew" is red
        // --> Recolor sibling + parent + sibling's child, and rotate around parent
        sibling.color = node.parent.color;
        node.parent.color = "BLACK";
        if (node.isLeftChild()) {
            sibling.right.color = "BLACK";
            rotateLeft(node.parent);
        } else {
            sibling.left.color = "BLACK";
            rotateRight(node.parent);
        }
    }

    /**
     * Check if node color is black
     * @param node The node to check
     * @return true if node color is black, false otherwise
     */
    private boolean isBlack(Node node) {
        return node == null || node.isBlack();
    }

    private static class NilNode extends Node {
        private NilNode() {
            super();
            this.color = "BLACK";
        }
    }

    /**
     * Rotates tree to the right
     * @param node Pivot node
     * @see Node Node
     */
    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }

    /**
     * Rotates tree to the left
     * @param node Pivot node
     * @see Node Node
     */
    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }

    /**
     * Replace parent child node with new node
     * @param parent The parent node
     * @param oldChild The old child node
     * @param newChild The new child node
     */
    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else {
            parent.replaceChild(oldChild, newChild);
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    /**
     * Gets tree root
     * @return root node of tree
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Gets size of tree
     * @return number of nodes in tree
     */
    public int getSize() {
        return size;
    }
}

