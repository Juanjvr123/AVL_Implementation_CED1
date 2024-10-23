package structures;

public class AVL <T extends Comparable<T>> implements IAVL<T>  {
    private Node<T> root;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (root == null) {
            root = node;
        } else {
            root = add(root, value);
        }
    }

    private Node<T> add(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeft(add(node.getLeft(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRight(add(node.getRight(), value));
        } else {
            return node;
        }

        updateHeight(node);
        return rebalance(node);
    }

    private Node<T> rebalance(Node<T> node) {
        int balanceFactor = balanceFactor(node);

        if (balanceFactor < -1) {
            if (balanceFactor(node.getLeft()) <= 0) {
                node = rotateRight(node);
            } else {
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
            }
        }

        if (balanceFactor > 1) {
            if (balanceFactor(node.getRight()) >= 0) {
                node = rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
            }
        }

        return node;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);

        updateHeight(node);
        updateHeight(rightChild);

        return rightChild;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);

        updateHeight(node);
        updateHeight(leftChild);

        return leftChild;
    }

    private void updateHeight(Node<T> node) {
        node.setHeight(1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight())));
    }

    private int getHeight(Node<T> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    private int balanceFactor(Node<T> node) {
        return (node == null) ? 0 : getHeight(node.getRight()) - getHeight(node.getLeft());
    }

    public Node<T> getMax(){
        return getMax(root);
    }

    private Node<T> getMax(Node<T> current){
        if(current.getRight() == null){
            return current;
        }
        else {
            return getMax(current.getRight());
        }
    }

    public Node<T> search(T value){
        Node<T> found = null;

        // CASO BASE 1. El nodo buscado es la raiz
        if(root.getValue().compareTo(value) == 0){
            found = root;
        }
        // LLAMADO AL METODO RECURSIVO
        else {
            found = search(root, value);
        }

        return found;
    }

    private Node<T> search(Node<T> current, T value){
        Node<T> found = null;
        //
        if(current != null){
            // CASO BASE: Encuentro el Nodo
            if(current.getValue().compareTo(value) == 0){
                found = current;
            }
            // CASO RECURSIVO: BUSQUEDA POR Izquierda
            else if (current.getValue().compareTo(value) > 0) {
                found = search(current.getLeft(), value);
            }
            // CASO RECURSIVO: Busqueda por derecha
            else if (current.getValue().compareTo(value) < 0) {
                found = search(current.getRight(), value);
            }
        }
        //
        else {
            // No esta dentro del arbol
        }
        return found;
    }


    public String inOrder(){
        String msg = "";
        if(root == null){
            msg = "Empty Tree";
        }
        else {
            msg = inOrder(root);
        }
        return msg;
    }

    private String inOrder(Node<T> current){
        if(current == null){
            return " ";
        }
        else {
            return inOrder(current.getLeft()) + current.getValue() + inOrder(current.getRight());
        }
    }

    @Override
    public void delete(T value) {
        if (root != null) {
            root = delete(root, value);
        }
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeft(delete(node.getLeft(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRight(delete(node.getRight(), value));
        } else {
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                Node<T> maxLeft = getMax(node.getLeft());
                node.setValue(maxLeft.getValue());
                node.setLeft(delete(node.getLeft(), maxLeft.getValue()));
            }
        }

        updateHeight(node);
        return rebalance(node);
    }
}

