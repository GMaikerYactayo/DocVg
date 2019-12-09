package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import model.Usuario;

public class UsuarioImpl extends Conexion implements ICRUD<Usuario> {

    @Override
    public void registrar(Usuario gen) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Usuario usuario) throws Exception {
        try {
            this.Conexion();
            String sql = "update USUARIO set PSWUSU=? where CODUSU=?";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setString(1, usuario.getPSWUSU());
                ps.setInt(5, usuario.getCODUSU());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error en modificarAlu " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminar(Usuario gen) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> listar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
