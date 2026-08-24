package com.ebac.modulo34.model;

import com.ebac.modulo34.dto.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioModel implements OperacionesCRUD<Usuario> {

    private final Connection connection;

    public UsuarioModel(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Usuario guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios(nombre, edad) values(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNombre());
        statement.setInt(2, usuario.getEdad());

        int elementosInsertados = statement.executeUpdate();
        if (elementosInsertados == 1) {
            return usuario;
        }
        throw new SQLException("Algo salio mal al ejecutar el insert");
    }

    @Override
    public Usuario actualizarPorId(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, edad = ? WHERE idUsuario = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNombre());
        statement.setInt(2, usuario.getEdad());
        statement.setInt(3, usuario.getIdUsuario());

        int elementosActualizados = statement.executeUpdate();
        if (elementosActualizados == 1) {
            return usuario;
        }
        throw new SQLException("Algo salio mal al ejecutar el update");
    }

    @Override
    public int eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        return statement.executeUpdate();
    }

    @Override
    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE idUsuario = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        Usuario usuario = new Usuario();
        while (resultSet.next()) {
            usuario.setIdUsuario(resultSet.getInt("idUsuario"));
            usuario.setNombre(resultSet.getString("nombre"));
            usuario.setEdad(resultSet.getInt("edad"));
        }

        return usuario;
    }
}
