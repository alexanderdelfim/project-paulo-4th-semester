package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import model.entities.enums.Sexo;

public class Cliente extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idCliente;
    private Date dataCadastro;
    
    public Cliente() {
        super();
    }

    public Cliente(Integer idCliente, Date dataCadastro, String nome, String cpf, String rg, String email, String telefone, String celular, Sexo sexo, String endereco, String cep, String bairro, String cidade, Date dataNascimento) {
        super(nome, cpf, rg, email, telefone, celular, sexo, endereco, cep, bairro, cidade, dataNascimento);
        this.idCliente = idCliente;
        this.dataCadastro = dataCadastro;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idCliente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.idCliente, other.idCliente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", dataCadastro=" + dataCadastro + '}';
    }
    
    

}
