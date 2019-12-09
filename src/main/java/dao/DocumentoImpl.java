package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Documento;

public class DocumentoImpl extends Conexion implements ICRUD<Documento> {

    @Override
    public void registrar(Documento documento) throws Exception {
        try {
            this.Conexion();
            String sql = "insert into DOCUMENTO"
                    + "(ESTDOC,FECVIADOC,HORVIADOC,DESDOC,CODTIPDOC,CODPER)"
                    + "values ('A',TO_DATE(?,'DD/MM/YYYY'),?,?,?,?)";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setString(1, documento.getFECVIADOC());
                ps.setString(2, documento.getHORVIADOC());
                ps.setString(3, documento.getDESDOC());
                ps.setString(4, documento.getCODTIPDOC());
                ps.setString(5, documento.getCODPER());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error en registrar" + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void modificar(Documento documento) throws Exception {
        try {
            this.Conexion();
            String sql = "update DOCUMENTO set ESTDOC=?,FECVIADOC=?,HORVIADOC=?,"
                    + "DESDOC=?,CODTIPDOC=?,CODPER=? where IDDOC=?";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setString(1, documento.getESTDOC());
                ps.setString(2, documento.getFECVIADOC());
                ps.setString(3, documento.getHORVIADOC());
                ps.setString(4, documento.getDESDOC());
                ps.setString(5, documento.getCODTIPDOC());
                ps.setString(6, documento.getCODPER());
                ps.setInt(7, documento.getIDDOC());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error en modificar " + e.getMessage());
            throw e;
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public void eliminar(Documento documento) throws Exception {
        try {
            this.Conexion();
            String sql = "delete from DOCUMENTO where IDDOC=?";
            try (PreparedStatement ps = this.getCn().prepareCall(sql)) {
                ps.setInt(1, documento.getIDDOC());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al elimoicar " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }

    @Override
    public List<Documento> listar() throws Exception {
        List<Documento> listado;
        Documento doc;
        try {
            this.Conexion();
            String sql = "SELECT * FROM VW_DOCUMENTO WHERE ESTDOC='A'";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                doc = new Documento();
                doc.setIDDOC(rs.getInt("IDDOC"));
                doc.setESTDOC(rs.getString("ESTDOC"));
                doc.setFECDOC(rs.getString("FECDOC"));
                doc.setFECVIADOC(rs.getString("FECVIADOC"));
                doc.setHORVIADOC(rs.getString("HORVIADOC"));
                doc.setDESDOC(rs.getString("DESDOC"));
                doc.setCODTIPDOC(rs.getString("CODTIPDOC"));
                doc.setCODPER(rs.getString("CODPER"));
                listado.add(doc);
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

    public void eliminarA(Documento documento) throws Exception {
        try {
            this.Conexion();
            String sql = "DELETE FROM DETALLE_DOCUMENTO where CODDETDOC=?";
            try (PreparedStatement ps = this.getCn().prepareStatement(sql)) {
                ps.setInt(1, documento.getCODDETDOC());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error al elimoicarAlu " + e.getMessage());
        } finally {
            this.Cerrar();
        }
    }

    public String obtenerCodigoTIPDOC(String TipDoc) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODTIPDOC FROM TIPO_DOCUMENTO WHERE ASUTIPDOC LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, TipDoc);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODTIPDOC");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteTIPDOC(String Consulta) throws SQLException {
        this.Conexion();
        ResultSet rs;
        List<String> Lista;
        try {
            String sql = "select ASUTIPDOC AS TIPO_DOCUMENTO from TIPO_DOCUMENTO WHERE ASUTIPDOC LIKE ? AND ROWNUM < 10";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Lista.add(rs.getString("TIPO_DOCUMENTO"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    public String obtenerCodigoDetalleEmpresa(String CodPer) throws SQLException, Exception {
        this.Conexion();
        ResultSet rs;
        try {
            String sql = "SELECT CODPER FROM VW_PERSONA WHERE CODEMP||' || '||NOMPER||', '||APEPER LIKE ?";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, CodPer);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("CODPER");
            }
            return null;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<String> autocompleteDetalleEmpresa(String Consulta) throws SQLException {
        this.Conexion();
        ResultSet rs;
        List<String> Lista;
        try {
            String sql = "select CODEMP||' || '||NOMPER||', '||APEPER AS VW_PERSONA from VW_PERSONA WHERE ESTPER='A' AND TIPPER='EXTERNO' AND CODEMP LIKE UPPER(?) AND ROWNUM < 11";
            PreparedStatement ps = this.getCn().prepareCall(sql);
            ps.setString(1, "%" + Consulta + "%");
            Lista = new ArrayList<>();
            rs = ps.executeQuery();
            while (rs.next()) {
                Lista.add(rs.getString("VW_PERSONA"));
            }
            return Lista;
        } catch (SQLException e) {
            throw e;
        }
    }

    public List<Documento> listarDoc() throws Exception {
        List<Documento> listado;
        Documento doc;
        try {
            this.Conexion();
            String sql = "select IDDOC, CODTIPDOC, CODPER, FECDOC from VW_DOCUMENTO where ROWNUM < 2 AND ESTDOC='A' order by IDDOC DESC ";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                doc = new Documento();
                doc.setIDDOC(rs.getInt("IDDOC"));
                doc.setCODTIPDOC(rs.getString("CODTIPDOC"));
                doc.setCODPER(rs.getString("CODPER"));
                doc.setFECDOC(rs.getString("FECDOC"));
                listado.add(doc);
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

    public List<Documento> listarGruDoc() throws Exception {
        List<Documento> listado;
        Documento doc;
        try {
            this.Conexion();
            String sql = "select ROWNUM AS N, VW_DOCUMENTO.IDDOC, CODDETDOC, PERSONA, TIPO, DNI\n" +
"                                   from DETALLE_DOCUMENTO\n" +
"                                         inner join VW_DOCUMENTO\n" +
"                                        ON VW_DOCUMENTO.IDDOC = DETALLE_DOCUMENTO.IDDOC\n" +
"                                inner join VW_DETALLE_AULA\n" +
"                                       ON VW_DETALLE_AULA.COD_DET_AUL = DETALLE_DOCUMENTO.COD_DET_AUL\n" +
"                                      where VW_DOCUMENTO.IDDOC= (SELECT MAX(IDDOC) from DOCUMENTO)";
            listado = new ArrayList();
            Statement st = this.getCn().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                doc = new Documento();
                doc.setNRO(rs.getString("N"));
                doc.setIDDOC(rs.getInt("IDDOC"));
                doc.setCODDETDOC(rs.getInt("CODDETDOC"));
                doc.setPERSONA(rs.getString("PERSONA"));
                doc.setTIPO(rs.getString("TIPO"));
                doc.setDNI(rs.getString("DNI"));
                listado.add(doc);
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

    public List<Documento> listarGruDoc2(int IDDOC) throws Exception {
        List<Documento> listado;
        Documento doc;
        try {
            this.Conexion();
            String sql = "select IDDOC, PERSONA, TIPO, DNI \n"
                    + "from DETALLE_DOCUMENTO\n"
                    + "    inner join VW_DETALLE_AULA \n"
                    + "        ON VW_DETALLE_AULA.COD_DET_AUL = DETALLE_DOCUMENTO.COD_DET_AUL \n"
                    + "    where IDDOC=?";
            listado = new ArrayList();
            PreparedStatement st = this.getCn().prepareStatement(sql);
            st.setInt(1, IDDOC);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                doc = new Documento();
                doc.setIDDOC(rs.getInt("IDDOC"));
                doc.setPERSONA(rs.getString("PERSONA"));
                doc.setTIPO(rs.getString("TIPO"));
                doc.setDNI(rs.getString("DNI"));
                listado.add(doc);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw e;
        }
        return listado;
    }

}
