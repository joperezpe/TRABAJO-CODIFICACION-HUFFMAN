import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

/* CODIGO DE HUFFMAN  ,DECODIFICAR */

public class Main {
    static final boolean leerdesdearchivo = false;
    static final boolean nuevotextoarchivo = false;

    static PriorityQueue<Node> nodos = new PriorityQueue<>((x1, x2) -> (x1.valor < x2.valor) ? -1 : 1);
    static TreeMap<Character, String> codigos = new TreeMap<>();
    static String texto = "";
    static String CODIFICADO = "";
    static String DESCODIFICADO = "";
    static int C[] = new int[128];

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = (leerdesdearchivo) ? new Scanner(new File("input.txt")) : new Scanner(System.in);
        int decision = 1;
        while (decision != -1) {
            if (decisionManejo(scanner, decision)) continue;
            decision = menuConsola(scanner);
        }
    }

    private static int menuConsola(Scanner scanner) {
        int decision;
        System.out.println("\n---- Menu ----\n" +
                "-- [-1] Para salir del programa \n" +
                "-- [1] Para ingresar un nuevo texto\n" +
                "-- [2] Para codificar un texto\n" +
                "-- [3] para descodificar un texto");
        decision = Integer.parseInt(scanner.nextLine());
        if (leerdesdearchivo)
            System.out.println("Decision: " + decision + "\n---- Final del Menu ----\n");
        return decision;
    }

    private static boolean decisionManejo(Scanner scanner, int decision) {
        if (decision == 1) {
            if (manejoTextoNuevo(scanner)) return true;
        } else if (decision == 2) {
            if (manejoCodificadoTextoNuevo(scanner)) return true;
        } else if (decision == 3) {
            manejoDescoficadoTextoNuevo(scanner);
        }
        return false;
    }

    private static void manejoDescoficadoTextoNuevo(Scanner scanner) {
        System.out.println("Ingrese el texto a descodificar:");
        CODIFICADO = scanner.nextLine();
        System.out.println("Texto a descodificar: " + CODIFICADO);
        textoDescodificado();
    }

    private static boolean manejoCodificadoTextoNuevo(Scanner scanner) {
        System.out.println("Ingrese el texto a codificar:");
        texto = scanner.nextLine();
        System.out.println("Texto para codificar: " + texto);

        if (!mismoConjuntoCaracteres()) {
            System.out.println("Entrada no valida");
            texto = "";
            return true;
        }
        textoCodificado();
        return false;
    }

    private static boolean manejoTextoNuevo(Scanner scanner) {
        int viejaLongitudTexto = texto.length();
        System.out.println("Ingrese el texto:");
        texto = scanner.nextLine();
        if (nuevotextoarchivo && (viejaLongitudTexto != 0 && !mismoConjuntoCaracteres())) {
            System.out.println("Entrada no valida");
            texto = "";
            return true;
        }
            C = new int[128];
            nodos.clear();
            codigos.clear();
            CODIFICADO = "";
            DESCODIFICADO = "";
            System.out.println("Text: " + texto);
            calcularIntervalosCaracteres(nodos, true);
            construirArbol(nodos);
            generarCodigos(nodos.peek(), "");

            imprimirCodigos();
            System.out.println("-- Codificando/Descodificando --");
            textoCodificado();
            textoDescodificado();
            return false;



    }

    private static boolean mismoConjuntoCaracteres() {
        boolean F = true;
        for (int i = 0; i < texto.length(); i++)
            if (C[texto.charAt(i)] == 0) {
                F = false;
                break;
            }
        return F;
    }

    private static void textoDescodificado() {
        DESCODIFICADO = "";
        Node node = nodos.peek();
        for (int i = 0; i < CODIFICADO.length(); ) {
            Node tmpNode = node;
            while (tmpNode.derecho != null && tmpNode.izquierdo != null && i < CODIFICADO.length()) {
                if (CODIFICADO.charAt(i) == '1')
                    tmpNode = tmpNode.izquierdo;
                else tmpNode = tmpNode.derecho;
                i++;
            }
            if (tmpNode != null)
                if (tmpNode.caracter.length() == 1)
                    DESCODIFICADO += tmpNode.caracter;
                else
                    System.out.println("Entrada no valida");

        }
        System.out.println("Texto descodificado: " + DESCODIFICADO);
    }

    private static void textoCodificado() {
        CODIFICADO = "";
        for (int i = 0; i < texto.length(); i++)
            CODIFICADO += codigos.get(texto.charAt(i));
        System.out.println("Texto codificado: " + CODIFICADO);
    }

    private static void construirArbol(PriorityQueue<Node> vector) {
        while (vector.size() > 1)
            vector.add(new Node(vector.poll(), vector.poll()));
    }

    private static void imprimirCodigos() {
        System.out.println("--- Imprimiendo codigos ---");
        codigos.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }

    private static void calcularIntervalosCaracteres(PriorityQueue<Node> vector, boolean printIntervals) {
        if (printIntervals) System.out.println("-- Intervalos --");

        for (int i = 0; i < texto.length(); i++)
            C[texto.charAt(i)]++;

        for (int i = 0; i < C.length; i++)
            if (C[i] > 0) {
                vector.add(new Node(C[i] / (texto.length() * 1.0), ((char) i) + ""));
                if (printIntervals)
                    System.out.println("'" + ((char) i) + "' : " + C[i] / (texto.length() * 1.0));
            }
    }

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