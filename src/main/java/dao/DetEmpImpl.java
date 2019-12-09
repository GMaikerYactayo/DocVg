package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.DetEmp;

public class DetEmpImpl extends Conexion implements ICRUD<DetEmp> {

    @Override
    public void registrar(DetEmp grupo) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into DETALLE_EMPRESA "
                    + "(ESTDETEMP,CARDETEMP,CODPER,CODEMP)"
                    + "values ('A',?,?,?)";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, grupo.getCARDETEMP());
            ps.setString(2, grupo.getCODPER());
            ps.setString(3, grupo.getCODEMP());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en registrar " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void modificar(DetEmp grupo) throws Exception {
        try {
            this.Conexion();
            String sql = "update DETALLE_EMPRESA set ESTDETEMP=?,CARDETEMP=?,CODPER=?,CODEMP=? where CODDETEMP=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, grupo.getESTDETEMP());
            ps.setString(2, grupo.getCARDETEMP());
            ps.setString(3, grupo.getCODPER());
            ps.setString(4, grupo.getCODEMP());
            ps.setInt(5, grupo.getCODDETEMP());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en modificar " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminar(DetEmp grupo) throws Exception {
        try {
            this.Conexion();
            String sql = "update DETALLE_EMPRESA set ESTDETEMP='I' where CODDETEMP=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setInt(1, grupo.getCODDETEMP());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<DetEmp> listar() throws Exception {
        List<DetEmp> listado;
        DetEmp gru;
        try {
            this.Conexion();
            String sql = "select * from VW_DETALLE_EMPRESA where ESTDETEMP='A'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                gru = new DetEmp();
                gru.setCODDETEMP(rs.getInt("CODDETEMP"));
                gru.setESTDETEMP(rs.getString("ESTDETEMP"));
                gru.setCARDETEMP(rs.getString("CARDETEMP"));
                gru.setCODPER(rs.getString("CODPER"));
                gru.setCODEMP(rs.getString("CODEMP"));
                listado.add(gru);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return listado;
    }

    public String obtenerCodigoPersona(String Persona) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODPER FROM PERSONA WHERE NOMPER||', '||APEPER LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Persona);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODPER");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompletePersona(String Consulta) throws SQLException {
        this.Conexion();
        ResultSet rs;
        List<String> Lista;
        try {
            String sql = "select NOMPER||', '||APEPER AS PERSONA from PERSONA WHERE TIPPER='EXTERNO' AND NOMPER LIKE ? AND ROWNUM < 11";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Lista.add(rs.getString("PERSONA"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    public String obtenerCodigoEmpresa(String Empresa) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODEMP FROM EMPRESA WHERE RAZSOCEMP LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Empresa);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODEMP");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteEmpresa(String Consulta) throws SQLException {
        this.Conexion();
        ResultSet rs;
        List<String> Lista;
        try {
            String sql = "select RAZSOCEMP AS EMPRESA from EMPRESA WHERE RAZSOCEMP LIKE ? AND ROWNUM < 11";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Lista.add(rs.getString("EMPRESA"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }
    }

}
