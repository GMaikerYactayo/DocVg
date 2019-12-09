package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.CantPer;
import model.Persona;

public class PersonaImpl extends Conexion implements ICRUD<Persona> {

    @Override
    public void registrar(Persona persona) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into PERSONA"
                    + "(DNIPER,NOMPER,APEPER,TITPER,CELPER,CODEMP,CARPER,TELEMP,CORPER,TIPPER,DIRPER,CODUBI,ESTPER)"
                    + "values (?,?,?,'Estudiante',?,?,?,?,?,'ALUMNO',?,?,'A')";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, persona.getDNIPER());
            ps.setString(2, persona.getNOMPER());
            ps.setString(3, persona.getAPEPER());
            ps.setString(4, persona.getCELPER());
            ps.setString(5, persona.getCODEMP());
            ps.setString(6, persona.getCARPER());
            ps.setString(7, persona.getTELEMP());
            ps.setString(8, persona.getCORPER());
            ps.setString(9, persona.getDIRPER());
            ps.setString(10, persona.getCODUBI());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en registrarAlumno " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }
    public void registrarEx(Persona persona) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into PERSONA"
                    + "(DNIPER,NOMPER,APEPER,TITPER,CELPER,CODEMP,CARPER,TELEMP,CORPER,TIPPER,DIRPER,CODUBI,ESTPER)"
                    + "values (?,?,?,?,?,?,?,?,?,'EXTERNO',?,'A')";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, persona.getDNIPER());
            ps.setString(2, persona.getNOMPER());
            ps.setString(3, persona.getAPEPER());
            ps.setString(4, persona.getTITPER());
            ps.setString(5, persona.getCELPER());
            ps.setString(6, persona.getCODEMP());
            ps.setString(7, persona.getCARPER());
            ps.setString(8, persona.getTELEMP());
            ps.setString(9, persona.getCORPER());
            ps.setString(10, persona.getDIRPER());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error en registrarAlumno " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void modificar(Persona persona) throws Exception {
        try {
            this.Conexion();
            String sql = "update PERSONA set DNIPER=?,NOMPER=?,APEPER=?,TITPER=?,CELPER=?,CODEMP=?,CARPER=?,"
                    + "TELEMP=?,CORPER=?,DIRPER=?,CODUBI=?,ESTPER=? where CODPER=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setString(1, persona.getDNIPER());
            ps.setString(2, persona.getNOMPER());
            ps.setString(3, persona.getAPEPER());
            ps.setString(4, persona.getTITPER());
            ps.setString(5, persona.getCELPER());
            ps.setString(6, persona.getCODEMP());
            ps.setString(7, persona.getCARPER());
            ps.setString(8, persona.getTELEMP());
            ps.setString(9, persona.getCORPER());
            ps.setString(10, persona.getDIRPER());
            ps.setString(11, persona.getCODUBI());
            ps.setString(12, persona.getESTPER());
            ps.setInt(13, persona.getCODPER());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminar(Persona persona) throws Exception {
        try {
            this.Conexion();
            String sql = "update PERSONA set ESTPER = 'I' where CODPER=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setInt(1, persona.getCODPER());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public void activar(Persona persona) throws Exception {
        try {
            this.Conexion();
            String sql = "update PERSONA set ESTPER = 'A' where CODPER=?";
            PreparedStatement ps = this.getCn().prepareStatement(sql);
            ps.setInt(1, persona.getCODPER());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<Persona> listar() throws Exception {
        List<Persona> listado;
        Persona pers;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PERSONA WHERE ESTPER='A' AND TIPPER='ALUMNO'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pers = new Persona();
                pers.setCODPER(rs.getInt("CODPER"));
                pers.setDNIPER(rs.getString("DNIPER"));
                pers.setNOMPER(rs.getString("NOMPER"));
                pers.setAPEPER(rs.getString("APEPER"));
                pers.setTITPER(rs.getString("TITPER"));
                pers.setCELPER(rs.getString("CELPER"));
                pers.setCODEMP(rs.getString("CODEMP"));
                pers.setCARPER(rs.getString("CARPER"));
                pers.setTELEMP(rs.getString("TELEMP"));
                pers.setCORPER(rs.getString("CORPER"));
                pers.setTIPPER(rs.getString("TIPPER"));
                pers.setDIRPER(rs.getString("DIRPER"));
                pers.setCODUBI(rs.getString("CODUBI"));
                pers.setESTPER(rs.getString("ESTPER"));
                listado.add(pers);
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
    
    public List<Persona> listarALUMNOS(String filtro) throws Exception {
        List<Persona> listado;
        Persona pers;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PERSONA WHERE TIPPER='ALUMNO' AND ESTPER='" + filtro + "'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pers = new Persona();
                pers.setCODPER(rs.getInt("CODPER"));
                pers.setDNIPER(rs.getString("DNIPER"));
                pers.setNOMPER(rs.getString("NOMPER"));
                pers.setAPEPER(rs.getString("APEPER"));
                pers.setTITPER(rs.getString("TITPER"));
                pers.setCELPER(rs.getString("CELPER"));
                pers.setCODEMP(rs.getString("CODEMP"));
                pers.setCARPER(rs.getString("CARPER"));
                pers.setTELEMP(rs.getString("TELEMP"));
                pers.setCORPER(rs.getString("CORPER"));
                pers.setTIPPER(rs.getString("TIPPER"));
                pers.setDIRPER(rs.getString("DIRPER"));
                pers.setCODUBI(rs.getString("CODUBI"));
                pers.setESTPER(rs.getString("ESTPER"));
                listado.add(pers);
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
    public List<Persona> listarExternos(String filtro) throws Exception {
        List<Persona> listado;
        Persona pers;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_PERSONA WHERE TIPPER='EXTERNO' AND ESTPER='" + filtro + "'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pers = new Persona();
                pers.setCODPER(rs.getInt("CODPER"));
                pers.setDNIPER(rs.getString("DNIPER"));
                pers.setNOMPER(rs.getString("NOMPER"));
                pers.setAPEPER(rs.getString("APEPER"));
                pers.setTITPER(rs.getString("TITPER"));
                pers.setCELPER(rs.getString("CELPER"));
                pers.setCODEMP(rs.getString("CODEMP"));
                pers.setCARPER(rs.getString("CARPER"));
                pers.setTELEMP(rs.getString("TELEMP"));
                pers.setCORPER(rs.getString("CORPER"));
                pers.setTIPPER(rs.getString("TIPPER"));
                pers.setDIRPER(rs.getString("DIRPER"));
                pers.setCODUBI(rs.getString("CODUBI"));
                pers.setESTPER(rs.getString("ESTPER"));
                listado.add(pers);
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
            String sql = "select RAZSOCEMP AS EMPRESA from EMPRESA WHERE RAZSOCEMP LIKE UPPER(?) AND ROWNUM < 11";
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


    public Persona validarExistenciaPersona(String DNIPER) throws Exception {
        try {
            this.Conexion();
            String sql = "Select CODPER, DNIPER from VW_PERSONA where DNIPER = '" + DNIPER + "'";
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            Persona persona = new Persona();
            while (rs.next()) {
                persona.setCODPER(rs.getInt("CODPER"));
                persona.setDNIPER(rs.getString("DNIPER"));
                break;
            }
            return persona;
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    public String obtenerCodigoUbigeo(String Ubigeo) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODUBI FROM UBIGEO WHERE DPTUBI||', '||PROUBI||', '||DISUBI LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Ubigeo);
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

}
