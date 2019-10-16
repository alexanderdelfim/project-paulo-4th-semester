package model.dao.impl;

import com.mysql.jdbc.Connection;
import db.ConnectionFactory;
import java.sql.Date;

import model.entities.Cliente;
import model.entities.enums.Sexo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.ClienteDao;

public class ClienteDaoJDBC implements ClienteDao {

    private Connection conn;

    public ClienteDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Cliente obj) {

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("INSERT INTO cliente (nome, email, cpf, rg, dataNascimento, dataCadastro, sexo, telefone, celular, endereco, cep, bairro, cidade)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getCpf());
            stmt.setString(4, obj.getRg());
            stmt.setDate(5, new Date(obj.getDataNascimento().getTime()));
            stmt.setDate(6, new Date(java.util.Calendar.getInstance().getTimeInMillis()));
            stmt.setString(7, obj.getSexo().name());
            stmt.setString(8, obj.getTelefone());
            stmt.setString(9, obj.getCelular());
            stmt.setString(10, obj.getEndereco());
            stmt.setString(11, obj.getCep());
            stmt.setString(12, obj.getBairro());
            stmt.setString(13, obj.getCidade());

            stmt.executeUpdate();
            System.out.println("Cadastrado com sucesso!");

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
         //   ConnectionFactory.closeConnection(conn, stmt);

        }

    }

    @Override
    public void update(Cliente obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cliente findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> findAll() {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Cliente> clientes = new ArrayList<>();

        try {
            stmt = conn.prepareStatement("SELECT * FROM cliente");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Cliente obj = new Cliente();

                obj = new Cliente();
                obj.setIdCliente(rs.getInt("idCliente"));
                obj.setNome(rs.getString("nome"));
                obj.setEmail(rs.getString("email"));
                obj.setCpf(rs.getString("cpf"));
                obj.setRg(rs.getString("rg"));
                obj.setDataNascimento(rs.getDate("dataNascimento"));
                obj.setDataCadastro(rs.getDate("dataCadastro"));
                obj.setSexo(Enum.valueOf(Sexo.class, rs.getString("Sexo")));
                obj.setTelefone(rs.getString("telefone"));
                obj.setCelular(rs.getString("celular"));
                obj.setEndereco(rs.getString("endereco"));
                obj.setCep(rs.getString("cep"));
                obj.setBairro(rs.getString("bairro"));
                obj.setCidade(rs.getString("cidade"));
                clientes.add(obj);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          //  ConnectionFactory.closeConnection(conn, stmt, rs);
        }

        return clientes;
    }
}
