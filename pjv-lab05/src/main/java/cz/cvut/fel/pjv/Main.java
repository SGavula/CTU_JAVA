package cz.cvut.fel.pjv;

public class Main {

    public static void main(String[] args) {
        TreeImpl tree = new TreeImpl();
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        tree.setTree(values);
        tree.toString();
    }
}
