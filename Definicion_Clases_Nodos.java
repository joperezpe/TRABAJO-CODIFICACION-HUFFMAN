private static void generarCodigos(Node node, String s) {
        if (node != null) {
            if (node.izquierdo != null)
                generarCodigos(node.izquierdo, s + "1");

            if (node.derecho != null)
                generarCodigos(node.derecho, s + "0");

            if (node.derecho == null && node.izquierdo == null)
                codigos.put(node.caracter.charAt(0), s);
        }
    }
}

class Node {
    Node derecho, izquierdo;
    double valor;
    String caracter;

    public Node(double valor, String caracter) {
        this.valor = valor;
        this.caracter = caracter;
        derecho = null;
        izquierdo = null;
    }

    public Node(Node derecho, Node izquierdo) {
        this.valor = derecho.valor + izquierdo.valor;
        caracter = derecho.caracter + izquierdo.caracter;
        if (derecho.valor < izquierdo.valor) {
            this.izquierdo = izquierdo;
            this.derecho = derecho;
        } else {
            this.izquierdo = derecho;
            this.derecho = izquierdo;
        }
    }
}