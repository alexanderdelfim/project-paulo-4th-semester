package model.services;

import java.sql.SQLException;
import java.util.List;
import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {
    
    private ClienteDao dao = DaoFactory.createClienteDao();
    
    public List<Cliente> findAll() throws SQLException {        
        return dao.findAll();
    }
    
    public void saveOrUpdate(Cliente obj) {
        if (obj.getIdCliente() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

}
