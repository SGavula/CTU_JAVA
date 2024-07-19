package cz.cvut.fel.pjv;

public class NodeImpl implements Node{
    private int value;
    Node leftChild;
    Node rightChild;

    public NodeImpl(int value) {
        this.value = value;
    }
    @Override
    public Node getLeft() {
        return leftChild;
    }

    @Override
    public Node getRight() {
        return rightChild;
    }

    @Override
    public int getValue() {
        return value;
    }
}
