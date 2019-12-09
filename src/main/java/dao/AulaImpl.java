package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Aula;

public class AulaImpl extends Conexion implements ICRUD<Aula> {

    @Override
    public void registrar(Aula aula) throws Exception {

    }

    @Override
    public void modificar(Aula aula) throws Exception {
    }

    @Override
    public void eliminar(Aula aula) throws Exception {

    }

    @Override
    public List<Aula> listar() throws Exception {
        List<Aula> listado;
        Aula aul;
        try {
            this.Conexion();
            String sql = "select * from VW_DETALLE_AULA WHERE EST_DETAUL='A'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                aul = new Aula();
                aul.setCOD_DET_AUL(rs.getInt("COD_DET_AUL"));
                aul.setEST_DETAUL(rs.getString("EST_DETAUL"));
                aul.setSEMESTRE(rs.getString("SEMESTRE"));
                aul.setMODULO(rs.getString("MODULO"));
                aul.setPERSONA(rs.getString("PERSONA"));
                aul.setTIPO(rs.getString("TIPO"));
                listado.add(aul);
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

    public List<Aula> listarMonitor() throws Exception {
        List<Aula> listado;
        Aula aul;
        try {
            this.Conexion();
            String sql = "SELECT COD_DET_AUL, EST_DETAUL,NOMPER||', '||APEPER AS PERSONA, TIPPER AS TIPO, DNIPER AS DNI\n"
                    + "FROM DETALLE_AULA\n"
                    + "	left JOIN AULA\n"
                    + "		ON AULA.COD_AULA = DETALLE_AULA.AULA_COD_AULA\n"
                    + "	left JOIN CARRERA\n"
                    + "		ON CARRERA.COD_CAR = AULA.COD_CAR\n"
                    + "	left JOIN PERSONA\n"
                    + "		ON PERSONA.CODPER = DETALLE_AULA.CODPER\n"
                    + "        where PERSONA.TIPPER='MONITOR' AND DETALLE_AULA.EST_DETAUL='A'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                aul = new Aula();
                aul.setCOD_DET_AUL(rs.getInt("COD_DET_AUL"));
                aul.setEST_DETAUL(rs.getString("EST_DETAUL"));
                aul.setPERSONA(rs.getString("PERSONA"));
                aul.setTIPO(rs.getString("TIPO"));
                listado.add(aul);
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

    public void asignar(Aula aula) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into DETALLE_DOCUMENTO \n"
                    + "(IDDOC,COD_DET_AUL)\n"
                    + "VALUES ((SELECT MAX(IDDOC) from DOCUMENTO) ,?)";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setInt(1, aula.getCOD_DET_AUL());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al asignar " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }

    public List<Aula> listarAula() throws Exception {
        List<Aula> listado;
        Aula aul;
        try {
            this.Conexion();
            String sql = "select COD_AULA, EST_AULA, SEM_AULA, ABRMOD\n"
                    + "from AULA\n"
                    + "	INNER JOIN CARRERA\n"
                    + "		ON CARRERA.COD_CAR = AULA.COD_CAR";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                aul = new Aula();
                aul.setCOD_AULA(rs.getInt("COD_AULA"));
                aul.setEST_AULA(rs.getString("EST_AULA"));
                aul.setSEM_AULA(rs.getString("SEM_AULA"));
                aul.setABRMOD(rs.getString("ABRMOD"));
                listado.add(aul);
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

}
