package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Carrera;

public class CarreraImpl extends Conexion implements ICRUD<Carrera> {

    @Override
    public void registrar(Carrera carrera) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into CARRERA"
                    + "(ABRCAR,NOMCAR,YEARCAR,ESTCAR)"
                    + "values (?,?,?,?)";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setString(1, "AS");
                ps.setString(2, "Analisis De Sistemas");
                ps.setString(3, carrera.getYEARCAR());
                ps.setString(4, "A");
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override 
    public void modificar(Carrera carrera) throws Exception {
        try {
            this.Conexion();
            String sql = "update CARRERA set ABRCAR=?,NOMCAR=?,YEARCAR=?,ESTCAR=? where CODCAR=?";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setString(1, carrera.getABRCAR());
                ps.setString(2, carrera.getNOMCAR());
                ps.setString(3, carrera.getYEARCAR());
                ps.setString(4, carrera.getESTCAR());
                ps.setInt(5, carrera.getCODCAR());
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
    public void eliminar(Carrera carrera) throws Exception {
        try {
            this.Conexion();
            String sql = "update CARRERA set ESTCAR='I' where CODCAR=?";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setInt(1, carrera.getCODCAR());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al elimoicarAlu " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }
    public void activar(Carrera carrera) throws Exception {
        try {
            this.Conexion();
            String sql = "update CARRERA set ESTCAR='A' where CODCAR=?";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setInt(1, carrera.getCODCAR());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al elimoicarAlu " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<Carrera> listar() throws Exception {
        List<Carrera> listado;
        Carrera car;
        try {
            this.Conexion();
            String sql = "select * from CARRERA where ESTCAR='A'";
            listado = new ArrayList();
            try (Statement st = this.getCn().createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    car = new Carrera();
                    car.setCODCAR(rs.getInt("CODCAR"));
                    car.setABRCAR(rs.getString("ABRCAR"));
                    car.setNOMCAR(rs.getString("NOMCAR"));
                    car.setYEARCAR(rs.getString("YEARCAR"));
                    car.setESTCAR(rs.getString("ESTCAR"));
                    listado.add(car);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            this.Cerrar();
        }
        return listado;
    }
    

    public String obtenerCodigoAlumno(String Alumno) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODALU FROM ALUMNO WHERE CONCAT(NOMALU,', ',APEALU) LIKE ?;";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, Alumno);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODALU");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteAlumno(String Consulta) throws SQLException {
        this.Conexion();
        ResultSet rs;
        List<String> Lista;
        try {
            String sql = "select top 10 CONCAT(NOMALU,', ',APEALU) AS ALUMNO from ALUMNO WHERE NOMALU LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Lista.add(rs.getString("ALUMNO"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }
    }

}
