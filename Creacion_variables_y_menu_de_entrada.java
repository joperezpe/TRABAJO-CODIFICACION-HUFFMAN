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