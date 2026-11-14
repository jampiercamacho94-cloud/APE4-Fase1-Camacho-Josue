/**
 * APE 4 - Fase 1: Grafos y vértices.
 * Estructura de Datos - Entrega individual.
 * Autor: Camacho Monta Josue Jampier
 *
 * Carga el ejemplo, ejecuta las pruebas e imprime los resultados.
 * Toda la impresión está aquí; GrafoSocial solo guarda datos y valida reglas.
 */
public class Main {

    public static void main(String[] args) {
        GrafoSocial red = new GrafoSocial();

        // Vértices
        red.agregarUsuario("U1", "Irene");
        red.agregarUsuario("U2", "Nicolás");
        red.agregarUsuario("U3", "Paula");
        red.agregarUsuario("U4", "Sergio");
        red.agregarUsuario("U5", "Valentina");
        red.agregarUsuario("U6", "Emilia");

        // Aristas
        red.agregarAmistad("U1", "U2");
        red.agregarAmistad("U2", "U3");
        red.agregarAmistad("U3", "U4");
        red.agregarAmistad("U4", "U1");
        red.agregarAmistad("U1", "U5");

        System.out.println("=== APE 4 - Fase 1: Grafos y vértices ===");
        System.out.println("Autor: Camacho Monta Josue Jampier");

        System.out.println();
        System.out.println("1) Personas (" + red.cantidadUsuarios() + " vértices)");
        imprimirPersonas(red);

        System.out.println();
        System.out.println("2) Amistades (" + red.cantidadAmistades() + " aristas)");
        imprimirAmistades(red);

        System.out.println();
        System.out.println("3) Grados");
        imprimirGrados(red);
        System.out.println("  Suma de grados = " + red.sumaGrados()
            + " | 2 x aristas = " + (2 * red.cantidadAmistades())
            + " | coincide: " + (red.sumaGrados() == 2 * red.cantidadAmistades()));

        // Usuario de mayor grado (en empate se queda con el primero)
        Usuario mayor = null;
        for (Usuario u : red.getUsuarios()) {
            if (mayor == null || red.grado(u.getId()) > red.grado(mayor.getId())) {
                mayor = u;
            }
        }
        System.out.println("  Mayor grado: " + mayor + " con " + red.grado(mayor.getId()));

        System.out.println();
        System.out.println("4) Consultas de amistad");
        System.out.println("  existeRelacion(U1, U2) = " + red.existeRelacion("U1", "U2") + "   (verdadera)");
        System.out.println("  existeRelacion(U2, U4) = " + red.existeRelacion("U2", "U4") + "  (falsa)");
        System.out.println("  existeRelacion(U2, U1) = " + red.existeRelacion("U2", "U1")
            + "   (recíproca de U1-U2)");

        System.out.println();
        System.out.println("5) Vecinos y grado");
        imprimirVecinos(red, "U1");
        System.out.println("  grado(U6) = " + red.grado("U6") + "  (Emilia, aislada)");
        imprimirVecinos(red, "U6");

        System.out.println();
        System.out.println("6) Verificación del ciclo Irene -> Nicolás -> Paula -> Sergio -> Irene");
        System.out.println("  U1-U2 = " + red.existeRelacion("U1", "U2")
            + " | U2-U3 = " + red.existeRelacion("U2", "U3")
            + " | U3-U4 = " + red.existeRelacion("U3", "U4")
            + " | U4-U1 = " + red.existeRelacion("U4", "U1"));

        System.out.println();
        System.out.println("7) Rechazos (el grafo no debe cambiar)");
        intentarAmistad(red, "U3", "U3"); // lazo
        intentarAmistad(red, "U2", "U1"); // duplicada con el orden invertido
        intentarAmistad(red, "U1", "U9"); // usuario inexistente
    }

    // ---------------------------------------------------------------
    // Impresión
    // ---------------------------------------------------------------

    private static void imprimirPersonas(GrafoSocial red) {
        for (Usuario u : red.getUsuarios()) {
            System.out.println("  " + u.getId() + " = " + u.getNombre());
        }
    }

    private static void imprimirAmistades(GrafoSocial red) {
        int n = 1;
        for (Amistad a : red.getAmistades()) {
            Usuario x = a.getUsuarioA();
            Usuario y = a.getUsuarioB();
            System.out.println("  " + n + ") " + x.getId() + "-" + y.getId()
                + "  (" + x.getNombre() + " - " + y.getNombre() + ")");
            n++;
        }
    }

    private static void imprimirGrados(GrafoSocial red) {
        for (Usuario u : red.getUsuarios()) {
            System.out.println("  grado(" + u.getId() + ") = " + red.grado(u.getId())
                + "  " + u.getNombre());
        }
    }

    private static void imprimirVecinos(GrafoSocial red, String id) {
        StringBuilder sb = new StringBuilder();
        for (String v : red.vecinos(id)) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(red.obtenerUsuario(v));
        }
        System.out.println("  vecinos(" + id + ") = [" + sb + "]");
    }

    /** Intenta registrar una amistad e imprime si fue aceptada o rechazada. */
    private static void intentarAmistad(GrafoSocial red, String a, String b) {
        String antes = red.instantanea();
        System.out.println("  agregarAmistad(" + a + ", " + b + ")");
        try {
            red.agregarAmistad(a, b);
            System.out.println("    ACEPTADA");
        } catch (IllegalArgumentException e) {
            System.out.println("    RECHAZADA: " + e.getMessage());
        }
        boolean intacto = antes.equals(red.instantanea());
        System.out.println("    grafo intacto: " + intacto
            + " | amistades = " + red.cantidadAmistades()
            + " | suma de grados = " + red.sumaGrados());
    }
}