package com.ebac.modulo34;

import com.ebac.modulo34.dto.Direccion;
import com.ebac.modulo34.dto.Telefono;
import com.ebac.modulo34.dto.Usuario;
import com.ebac.modulo34.model.DireccionModel;
import com.ebac.modulo34.model.TelefonoModel;
import com.ebac.modulo34.model.UsuarioModel;

import java.sql.Connection;
import java.sql.SQLException;

public class Contexto {

    static Connection connection;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/moduloEjemplo";
        String user = "root";
        String password = "root123";

        connection = java.sql.DriverManager.getConnection(url, user, password);

        operacionConUsuarios();
        operacionConTelefonos();
        operacionConDirecciones();

        connection.close();
    }

    public static void operacionConUsuarios() throws SQLException {
        System.out.println("------- OPERACION CON USUARIOS -------");
        Usuario usuarioMaria = crearUsuario("Maria", 25);
        Usuario usuarioJulian = crearUsuario("Julian", 23);

        UsuarioModel usuarioModel = new UsuarioModel(connection);
        Usuario maria = usuarioModel.guardar(usuarioMaria);
        Usuario julian = usuarioModel.guardar(usuarioJulian);

        System.out.println(maria);
        System.out.println(julian);
        System.out.println("-----------------------------------");

        Usuario usuario1EnDB = usuarioModel.obtenerPorId(1);
        System.out.println(usuario1EnDB);
        Usuario usuario2EnDB = usuarioModel.obtenerPorId(2);
        System.out.println(usuario2EnDB);
        System.out.println("-----------------------------------");

        Usuario usuarioInexistente = usuarioModel.obtenerPorId(3);
        System.out.println(usuarioInexistente);
        System.out.println("-----------------------------------");

        usuarioModel.eliminarPorId(2);
        Usuario usuario2Eliminado = usuarioModel.obtenerPorId(2);
        System.out.println(usuario2Eliminado);
    }


    public static void operacionConTelefonos() throws SQLException {
        System.out.println("------- OPERACION CON TELEFONOS -------");
        Telefono telefono = crearTelefono(1, "55-11111-22222", "Casa");

        TelefonoModel telefonoModel = new TelefonoModel(connection);
        telefonoModel.guardar(telefono);
        Telefono telefonoEnDB = telefonoModel.obtenerPorId(1);

        System.out.println(telefonoEnDB);
    }

    public static void operacionConDirecciones() throws SQLException {
        System.out.println("------- OPERACION CON DIRECCIONES -------");

        Direccion direccionProbable = crearDireccion(1, "Av. Reforma", 123, "Puebla", 75700);

        DireccionModel direccionModel = new DireccionModel(connection);
        Direccion direccionGuardada = direccionModel.guardar(direccionProbable);
        System.out.println("Guardado: " + direccionGuardada);
        System.out.println("-----------------------------------");

        Direccion direccionEnDB = direccionModel.obtenerPorId(1);
        System.out.println("Obtenido por ID 1: " + direccionEnDB);
        System.out.println("-----------------------------------");

        direccionEnDB.setCalle("Av. Tecnológico");
        direccionEnDB.setNumero(456);
        Direccion direccionActualizada = direccionModel.actualizarPorId(direccionEnDB);
        System.out.println("Actualizado: " + direccionActualizada);
        System.out.println("-----------------------------------");

        direccionModel.eliminarPorId(1);
        Direccion direccionEliminada = direccionModel.obtenerPorId(1);
        System.out.println("Tras eliminar: " + direccionEliminada);
    }

    private static Direccion crearDireccion(int idUsuario, String calle, int numero, String estado, int cp) {
        Direccion direccion = new Direccion();
        direccion.setIdUsuario(idUsuario);
        direccion.setCalle(calle);
        direccion.setNumero(numero);
        direccion.setEstado(estado);
        direccion.setCp(cp);

        return direccion;
    }

    private static Usuario crearUsuario(String nombre, int edad) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEdad(edad);

        return usuario;
    }

    private static Telefono crearTelefono(int idUsuario, String numero, String tipo) {
        Telefono telefono = new Telefono();
        telefono.setIdUsuario(idUsuario);
        telefono.setNumero(numero);
        telefono.setTipo(tipo);

        return telefono;
    }
}
