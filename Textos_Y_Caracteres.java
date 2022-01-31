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