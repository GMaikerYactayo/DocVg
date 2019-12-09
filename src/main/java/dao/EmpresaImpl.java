package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Empresa;

public class EmpresaImpl extends Conexion implements ICRUD<Empresa> {

    @Override
    public void registrar(Empresa empresa) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into EMPRESA"
                    + "(ESTEMP,RAZSOCEMP,RUCEMP,CODUBI)"
                    + "values ('A',?,?,?)";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setString(1, empresa.getRAZSOCEMP());
                ps.setString(2, empresa.getRUCEMP());
                ps.setString(3, empresa.getCODUBI());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error en registrarEmpresa " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void modificar(Empresa empresa) throws Exception {
        try {
            this.Conexion();
            String sql = "update EMPRESA set ESTEMP=?, RAZSOCEMP=?, RUCEMP=?, CODUBI=? where CODEMP=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, empresa.getESTEMP());
            ps.setString(2, empresa.getRAZSOCEMP());
            ps.setString(3, empresa.getRUCEMP());
            ps.setString(4, empresa.getCODUBI());
            ps.setInt(5, empresa.getCODEMP());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en modificarEmp " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminar(Empresa empresa) throws Exception {
        try {
            this.Conexion();
            String sql = "update EMPRESA set ESTEMP='I' where CODEMP=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setInt(1, empresa.getCODEMP());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar empresa " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }
    public void activar(Empresa empresa) throws Exception {
        try {
            this.Conexion();
            String sql = "update EMPRESA set ESTEMP='A' where CODEMP=?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setInt(1, empresa.getCODEMP());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar mpresa " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<Empresa> listar() throws Exception {
        List<Empresa> listado;
        Empresa empr;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_EMPRESA where ESTEMP='A'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                empr = new Empresa();
                empr.setCODEMP(rs.getInt("CODEMP"));
                empr.setESTEMP(rs.getString("ESTEMP"));
                empr.setRAZSOCEMP(rs.getString("RAZSOCEMP"));
                empr.setRUCEMP(rs.getString("RUCEMP"));
                empr.setCODUBI(rs.getString("CODUBI"));
                listado.add(empr);
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
    
    public List<Empresa> listarEstado(String filtro) throws Exception {
        List<Empresa> listado;
        Empresa empr;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_EMPRESA where ESTEMP='" + filtro + "'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                empr = new Empresa();
                empr.setCODEMP(rs.getInt("CODEMP"));
                empr.setESTEMP(rs.getString("ESTEMP"));
                empr.setRAZSOCEMP(rs.getString("RAZSOCEMP"));
                empr.setRUCEMP(rs.getString("RUCEMP"));
                empr.setCODUBI(rs.getString("CODUBI"));
                listado.add(empr);
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

    public String obtenerCodigoUbigeo(String Empresa) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODUBI FROM UBIGEO WHERE DPTUBI||', '||PROUBI||', '||DISUBI LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Empresa);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODUBI");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteUbigeo(String Consulta) throws SQLException {
        this.Conexion();
        ResultSet rs;
        List<String> Lista;
        try {
            String sql = "select DPTUBI||', '||PROUBI||', '||DISUBI AS UBIGEO from UBIGEO WHERE DISUBI LIKE UPPER(?) AND ROWNUM < 11";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Lista.add(rs.getString("UBIGEO"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }

    }

    public String obtenerCodigoResponsable(String Responsable) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODRES FROM RESPONSABLE WHERE CONCAT(NOMRES,', ',APERES) LIKE ?;";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Responsable);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODRES");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteResponsable(String Consulta) throws SQLException {
        this.Conexion();
        ResultSet rs;
        List<String> Lista;
        try {
            String sql = "select top 10 CONCAT(NOMRES,', ',APERES) AS RESPONSABLE from RESPONSABLE WHERE NOMRES LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Lista.add(rs.getString("RESPONSABLE"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }

    }
}
