package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;

public class Login extends Conexion {

    public Usuario startSession(String USEUSU, String PSWUSU) throws Exception {
        this.Conexion();
        ResultSet rs;
        Usuario usuario = null;
        try {
            String sql = "select CODUSU,TIPUSU from USUARIO where USEUSU=? and PSWUSU=?";
            PreparedStatement ps = getCn().prepareStatement(sql);
            ps.setString(1, USEUSU);
            ps.setString(2, PSWUSU);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setCODUSU(rs.getInt("CODUSU"));
                usuario.setTIPUSU(rs.getString("TIPUSU"));
                usuario.getUSEUSU();
                usuario.getPSWUSU();
            }
            return usuario;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }

    }

}
