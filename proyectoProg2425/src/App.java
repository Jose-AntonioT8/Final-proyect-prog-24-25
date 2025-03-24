import concesionarios.Concesionario;
import concesionarios.ConcesionariosService;
import connection.ConnectionPool;
import coches.Coches;
import coches.CochesService;
import marcas.Marca;
import marcas.MarcaService;
import modelos.Modelo;
import modelos.ModeloService;
import nuevo.Nuevo;
import nuevo.NuevoService;
import segundamano.SegundaMano;
import segundamano.SegundaManoService;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App {


    private static void menuConcesionarios() throws SQLException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Concesionarios ---");
            System.out.println("1. Listar Concesionarios");
            System.out.println("2. Añadir Concesionario");
            System.out.println("3. Editar Concesionario");
            System.out.println("4. Eliminar Concesionario");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    listarConcesionarios();
                    break;
                case 2:
                    añadirConcesionario();
                    break;
                case 3:
                    editarConcesionario();
                    break;
                case 4:
                    eliminarConcesionario();
                    break;
                case 5:
                    salir = true;
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarConcesionarios() throws SQLException {
        List<Concesionario> concesionarios = concesionariosService.requestAll();
        for (Concesionario c : concesionarios) {
            System.out.println(c);
        }
    }

    private static void añadirConcesionario() throws SQLException {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Número de Empleados: ");
        int numemp = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Concesionario c = new Concesionario(id, numemp, nombre, direccion, telefono);
        concesionariosService.create(c);
        System.out.println("Concesionario añadido correctamente.");
    }

    private static void editarConcesionario() throws SQLException {
        System.out.print("ID del Concesionario a editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Concesionario c = concesionariosService.requestById(id);
        if (c == null) {
            System.out.println("Concesionario no encontrado.");
            return;
        }

        System.out.print("Nuevo Número de Empleados: ");
        int numemp = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Nuevo Teléfono: ");
        String telefono = scanner.nextLine();
        c.setNumemo(numemp);
        c.setNomCon(nombre);
        c.setDirCon(direccion);
        c.settlfnCon(telefono);
        concesionariosService.update(c);
        System.out.println("Concesionario actualizado correctamente.");
    }

    private static void eliminarConcesionario() throws SQLException {
        System.out.print("ID del Concesionario a eliminar: ");
        int id = scanner.nextInt();
        boolean eliminado = concesionariosService.delete(id);
        if (eliminado) {
            System.out.println("Concesionario eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el concesionario.");
        }
    }

    private static void menuCoches() throws SQLException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Coches ---");
            System.out.println("1. Listar Coches");
            System.out.println("2. Añadir Coche");
            System.out.println("3. Editar Coche");
            System.out.println("4. Eliminar Coche");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    listarCoches();
                    break;
                case 2:
                    añadirCoche();
                    break;
                case 3:
                    editarCoche();
                    break;
                case 4:
                    eliminarCoche();
                    break;
                case 5:
                    salir = true;
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarCoches() throws SQLException {
        List<Coches> coches = cochesService.requestAll();
        for (Coches c : coches) {
            System.out.println(c);
        }
    }

    private static void añadirCoche() throws SQLException {
        System.out.print("Número de Bastidor: ");
        int numBast = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Fecha de Fabricación (yyyy-MM-dd): ");
        String fechaFabStr = scanner.nextLine();
        Date fechaFab = java.sql.Date.valueOf(fechaFabStr);
        System.out.print("Precio: ");
        int precio = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Tipo: ");
        String tipo = scanner.nextLine();
        System.out.print("Código de Concesionario: ");
        int codCon = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Código de Modelo: ");
        int codMod = scanner.nextInt();
        scanner.nextLine();
        Coches c = new Coches(numBast, fechaFab, precio, tipo, codCon, codMod);
        cochesService.create(c);
        System.out.println("Coche añadido correctamente.");
    }

    private static void editarCoche() throws SQLException {
        System.out.print("Número de Bastidor del Coche a editar: ");
        int numBast = scanner.nextInt();
        scanner.nextLine();
        Coches c = cochesService.requestById(numBast);
        if (c == null) {
            System.out.println("Coche no encontrado.");
            return;
        }

        System.out.print("Nueva Fecha de Fabricación (yyyy-MM-dd): ");
        String fechaFabStr = scanner.nextLine();
        Date fechaFab = java.sql.Date.valueOf(fechaFabStr);
        System.out.print("Nuevo Precio: ");
        int precio = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo Tipo: ");
        String tipo = scanner.nextLine();
        System.out.print("Nuevo Código de Concesionario: ");
        int codCon = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo Código de Modelo: ");
        int codMod = scanner.nextInt();
        scanner.nextLine();
        c.setFechaFab(fechaFab);
        c.setPrecio(precio);
        c.setTipo(tipo);
        c.setCodCon(codCon);
        c.setCodMod(codMod);
        cochesService.update(c);
        System.out.println("Coche actualizado correctamente.");
    }

    private static void eliminarCoche() throws SQLException {
        System.out.print("Número de Bastidor del Coche a eliminar: ");
        int numBast = scanner.nextInt();
        boolean eliminado = cochesService.delete(numBast);
        if (eliminado) {
            System.out.println("Coche eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el coche.");
        }
    }

    private static void menuMarcas() throws SQLException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Marcas ---");
            System.out.println("1. Listar Marcas");
            System.out.println("2. Añadir Marca");
            System.out.println("3. Editar Marca");
            System.out.println("4. Eliminar Marca");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    listarMarcas();
                    break;
                case 2:
                    añadirMarca();
                    break;
                case 3:
                    editarMarca();
                    break;
                case 4:
                    eliminarMarca();
                    break;
                case 5:
                    salir = true;
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarMarcas() throws SQLException {
        List<Marca> marcas = marcaService.requestAll();
        for (Marca m : marcas) {
            System.out.println(m);
        }
    }

    private static void añadirMarca() throws SQLException {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        Marca m = new Marca(id, nombre);
        marcaService.create(m);
        System.out.println("Marca añadida correctamente.");
    }

    private static void editarMarca() throws SQLException {
        System.out.print("ID de la Marca a editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Marca m = marcaService.requestById(id);
        if (m == null) {
            System.out.println("Marca no encontrada.");
            return;
        }
        System.out.print("Nuevo Nombre: ");
        String nombre = scanner.nextLine();
        m.setNombre(nombre);
        marcaService.update(m);
        System.out.println("Marca actualizada correctamente.");
    }

    private static void eliminarMarca() throws SQLException {
        System.out.print("ID de la Marca a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean eliminado = marcaService.delete(id);
        if (eliminado) {
            System.out.println("Marca eliminada correctamente.");
        } else {
            System.out.println("No se pudo eliminar la marca.");
        }
    }

    private static void menuModelos() throws SQLException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Modelos ---");
            System.out.println("1. Listar Modelos");
            System.out.println("2. Añadir Modelo");
            System.out.println("3. Editar Modelo");
            System.out.println("4. Eliminar Modelo");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    listarModelos();
                    break;
                case 2:
                    añadirModelo();
                    break;
                case 3:
                    editarModelo();
                    break;
                case 4:
                    eliminarModelo();
                    break;
                case 5:
                    salir = true;
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarModelos() throws SQLException {
        List<Modelo> modelos = modeloService.requestAll();
        for (Modelo m : modelos) {
            System.out.println(m);
        }
    }

    private static void añadirModelo() throws SQLException {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Código de Marca: ");
        int codMar = scanner.nextInt();
        scanner.nextLine();
        Modelo m = new Modelo(id, nombre, codMar);
        modeloService.create(m);
        System.out.println("Modelo añadido correctamente.");
    }

    private static void editarModelo() throws SQLException {
        System.out.print("ID del Modelo a editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Modelo m = modeloService.requestById(id);
        if (m == null) {
            System.out.println("Modelo no encontrado.");
            return;
        }
        System.out.print("Nuevo Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo Código de Marca: ");
        int codMar = scanner.nextInt();
        scanner.nextLine();
        m.setNombre(nombre);
        m.setCodMar(codMar);
        modeloService.update(m);
        System.out.println("Modelo actualizado correctamente.");
    }

    private static void eliminarModelo() throws SQLException {
        System.out.print("ID del Modelo a eliminar: ");
        int id = scanner.nextInt();
        boolean eliminado = modeloService.delete(id);
        if (eliminado) {
            System.out.println("Modelo eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el modelo.");
        }
    }

    private static void menuNuevos() throws SQLException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Coches Nuevos ---");
            System.out.println("1. Listar Coches Nuevos");
            System.out.println("2. Añadir Coche Nuevo");
            System.out.println("3. Editar Coche Nuevo");
            System.out.println("4. Eliminar Coche Nuevo");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    listarNuevos();
                    break;
                case 2:
                    añadirNuevo();
                    break;
                case 3:
                    editarNuevo();
                    break;
                case 4:
                    eliminarNuevo();
                    break;
                case 5:
                    salir = true;
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarNuevos() throws SQLException {
        List<Nuevo> nuevos = nuevoService.requestAll();
        for (Nuevo n : nuevos) {
            System.out.println(n);
        }
    }

    private static void añadirNuevo() throws SQLException {
        System.out.print("Garantía (en meses): ");
        int garantia = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Número de Bastidor: ");
        int numBast = scanner.nextInt();
        scanner.nextLine();
        Nuevo n = new Nuevo(garantia, numBast);
        nuevoService.create(n);
        System.out.println("Coche nuevo añadido correctamente.");
    }

    private static void editarNuevo() throws SQLException {
        System.out.print("Número de Bastidor del Coche Nuevo a editar: ");
        int numBast = scanner.nextInt();
        scanner.nextLine();
        Nuevo n = nuevoService.requestById(numBast);
        if (n == null) {
            System.out.println("Coche nuevo no encontrado.");
            return;
        }
        System.out.print("Nueva Garantía (en meses): ");
        int garantia = scanner.nextInt();
        scanner.nextLine();
        n.setGarantia(garantia);
        nuevoService.update(n);
        System.out.println("Coche nuevo actualizado correctamente.");
    }

    private static void eliminarNuevo() throws SQLException {
        System.out.print("Número de Bastidor del Coche Nuevo a eliminar: ");
        int numBast = scanner.nextInt();
        boolean eliminado = nuevoService.delete(numBast);
        if (eliminado) {
            System.out.println("Coche nuevo eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el coche nuevo.");
        }
    }

    private static void menuSegundaMano() throws SQLException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Coches de Segunda Mano ---");
            System.out.println("1. Listar Coches de Segunda Mano");
            System.out.println("2. Añadir Coche de Segunda Mano");
            System.out.println("3. Editar Coche de Segunda Mano");
            System.out.println("4. Eliminar Coche de Segunda Mano");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    listarSegundaMano();
                    break;
                case 2:
                    añadirSegundaMano();
                    break;
                case 3:
                    editarSegundaMano();
                    break;
                case 4:
                    eliminarSegundaMano();
                    break;
                case 5:
                    salir = true;
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarSegundaMano() throws SQLException {
        List<SegundaMano> segundaManos = segundaManoService.requestAll();
        for (SegundaMano s : segundaManos) {
            System.out.println(s);
        }
    }

    private static void añadirSegundaMano() throws SQLException {
        System.out.print("Kilometraje: ");
        int kilometraje = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Número de Propietarios: ");
        int numPropietarios = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Número de Bastidor: ");
        int numBast = scanner.nextInt();
        scanner.nextLine();
        SegundaMano s = new SegundaMano(kilometraje, numPropietarios, numBast);
        segundaManoService.create(s);
        System.out.println("Coche de segunda mano añadido correctamente.");
    }

    private static void editarSegundaMano() throws SQLException {
        System.out.print("Número de Bastidor del Coche de Segunda Mano a editar: ");
        int numBast = scanner.nextInt();
        scanner.nextLine();
        SegundaMano s = segundaManoService.requestById(numBast);
        if (s == null) {
            System.out.println("Coche de segunda mano no encontrado.");
            return;
        }

        System.out.print("Nuevo Kilometraje: ");
        int kilometraje = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo Número de Propietarios: ");
        int numPropietarios = scanner.nextInt();
        scanner.nextLine();
        s.setKilometraje(kilometraje);
        s.setNumPropietarios(numPropietarios);
        segundaManoService.update(s);
        System.out.println("Coche de segunda mano actualizado correctamente.");
    }

    private static void eliminarSegundaMano() throws SQLException {
        System.out.print("Número de Bastidor del Coche de Segunda Mano a eliminar: ");
        int numBast = scanner.nextInt();
        boolean eliminado = segundaManoService.delete(numBast);
        if (eliminado) {
            System.out.println("Coche de segunda mano eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el coche de segunda mano.");
        }
    }


    private static ConnectionPool conn;
    private static ConcesionariosService concesionariosService;
    private static CochesService cochesService;
    private static MarcaService marcaService;
    private static ModeloService modeloService;
    private static NuevoService nuevoService;
    private static SegundaManoService segundaManoService;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            // Establecer conexión con la base de datos
            String url = "jdbc:mysql://localhost:3306/concesionario";
            String usuario = "usuario";
            String clave = "contraseña";
            conn = new ConnectionPool(url, usuario, clave);
            concesionariosService = new ConcesionariosService(conn.getConnection());
            cochesService = new CochesService(conn.getConnection());
            marcaService = new MarcaService(conn.getConnection());
            modeloService = new ModeloService(conn.getConnection());
            nuevoService = new NuevoService(conn.getConnection());
            segundaManoService = new SegundaManoService(conn.getConnection());
            boolean salir = false;
            while (!salir) {
                System.out.println("\n--- Menú Principal ---");
                System.out.println("1. Concesionarios");
                System.out.println("2. Coches");
                System.out.println("3. Marcas");
                System.out.println("4. Modelos");
                System.out.println("5. Coches Nuevos");
                System.out.println("6. Coches de Segunda Mano");
                System.out.println("7. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        menuConcesionarios();
                        break;
                    case 2:
                        menuCoches();
                        break;
                    case 3:
                        menuMarcas();
                        break;
                    case 4:
                        menuModelos();
                        break;
                    case 5:
                        menuNuevos();
                        break;
                    case 6:
                        menuSegundaMano();
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                         salir=true;
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.closeAll();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

}