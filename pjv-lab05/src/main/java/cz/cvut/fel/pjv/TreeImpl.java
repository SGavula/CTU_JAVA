package cz.cvut.fel.pjv;

public class TreeImpl implements Tree{
    NodeImpl root;
    int level = 0;
    String space = "";
    public TreeImpl() {

    }

    @Override
    public void setTree(int[] values) {
        if (values == null || values.length == 0) {
            root = null;
            return;
        }
        root = createTree(values, 0, values.length - 1);
    }

    private NodeImpl createTree(int[] values, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end + 1) / 2;
        NodeImpl node = new NodeImpl(values[mid]);
        node.leftChild = createTree(values, start, mid - 1);
        node.rightChild = createTree(values, mid + 1, end);
        return node;
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        String binaryTreeString = printValue(root);
        return binaryTreeString;
    }

    private String printValue(NodeImpl node) {
        if (node == null) {
            return "";
        }
        level++;
        String leftStringChild = printValue((NodeImpl) node.leftChild);
        String rightStringChild =  printValue((NodeImpl) node.rightChild);
        level--;
        space = "";
        for(int i = 0; i < level; i++) {
            space = space + " ";
        }
        String valueInStringFormat = space + "- " + node.getValue() + "\n";
        String returnString =  valueInStringFormat + leftStringChild + rightStringChild;
        return returnString;
    }
}
