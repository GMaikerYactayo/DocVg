package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CanAlu;
import model.CantPer;

public class CanPerImpl extends Conexion {

    public List<CantPer> lstCantPer() throws SQLException {
        List<CantPer> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "select TIPPER , COUNT(CODPER) AS CANTIDAD from PERSONA WHERE ESTPER='A' GROUP BY TIPPER";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList();
            CantPer emp;
            while (rs.next()) {
                emp = new CantPer();
                emp.setAluPer(rs.getString("TIPPER"));
                emp.setCanAlu(rs.getInt("CANTIDAD"));
                lista.add(emp);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }
    
    public List<CanAlu> lstCantAlu() throws SQLException {
        List<CanAlu> lista;
        ResultSet rs;
        try {
            this.Conexion();
            String sql = "select MODULO , COUNT(PERSONA) AS CANTIDAD from VW_DETALLE_AULA WHERE EST_DETAUL='A' AND TIPO='ALUMNO' GROUP BY MODULO";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            rs = ps.executeQuery();
            lista = new ArrayList();
            CanAlu emp;
            while (rs.next()) {
                emp = new CanAlu();
                emp.setAULA(rs.getString("MODULO"));
                emp.setCanAlu(rs.getInt("CANTIDAD"));
                lista.add(emp);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return lista;
    }

}
