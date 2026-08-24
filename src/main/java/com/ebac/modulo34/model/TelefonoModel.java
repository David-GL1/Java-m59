package com.ebac.modulo34.model;

import com.ebac.modulo34.dto.Telefono;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelefonoModel implements OperacionesCRUD<Telefono> {

    private final Connection connection;

    public TelefonoModel(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Telefono guardar(Telefono telefono) throws SQLException {
        String sql = "INSERT INTO telefonos(idUsuario, numero, tipo) values(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, telefono.getIdUsuario());
        statement.setString(2, telefono.getNumero());
        statement.setString(3, telefono.getTipo());

        int elementosInsertados = statement.executeUpdate();
        if (elementosInsertados == 1) {
            return telefono;
        }
        throw new SQLException("Algo salio mal al ejecutar el insert");
    }

    @Override
    public Telefono actualizarPorId(Telefono telefono) throws SQLException {
        String sql = "UPDATE telefonos SET idUsuario = ?, numero = ?, tipo = ? WHERE idTelefono = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, telefono.getIdUsuario());
        statement.setString(2, telefono.getNumero());
        statement.setString(3, telefono.getTipo());
        statement.setInt(4, telefono.getIdTelefono());

        int elementosActualizados = statement.executeUpdate();
        if (elementosActualizados == 1) {
            return telefono;
        }
        throw new SQLException("Algo salio mal al ejecutar el update");
    }

    @Override
    public int eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM telefonos WHERE idTelefono = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        return statement.executeUpdate();
    }

    @Override
    public Telefono obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM telefonos WHERE idTelefono = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        Telefono telefono = new Telefono();
        while (resultSet.next()) {
            telefono.setIdTelefono(resultSet.getInt("idTelefono"));
            telefono.setIdUsuario(resultSet.getInt("idUsuario"));
            telefono.setNumero(resultSet.getString("numero"));
            telefono.setTipo(resultSet.getString("tipo"));
        }

        return telefono;
    }
}
