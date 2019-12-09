package controlador;

import Reportes.report;
import dao.AulaImpl;
import dao.DocumentoImpl;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Aula;
import model.Documento;
import model.TipDoc;
import org.primefaces.PrimeFaces;

@Named(value = "documentoC")
@SessionScoped
public class DocumentoC implements Serializable {

    private TipDoc tipdoc = new TipDoc();

    private Documento documento = new Documento();
    private Documento select;
    private List<Aula> selectsM;
    private List<Aula> selects;
    private List<Documento> listadoGruDoc3;
    private List<Documento> listadoGruDoc;
    private List<Documento> listadoGruDoc2;
    private List<Documento> listadoDocu;
    private List<Documento> listadoDoc;
    private List<Documento> listadoDoc2;
    SimpleDateFormat formateador = new SimpleDateFormat("dd/MMM/yyyy");
    SimpleDateFormat sdf_d = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
    private Date fechaFormulario;
    private boolean activo;
    private boolean habi = false;

    @PostConstruct
    public void iniciar() {
        try {
            listar();
            listarDoc();
            listarGruDoc();
        } catch (Exception e) {
        }
    }

    public void rellenar() throws Exception {
        System.out.println(sdf_d.parse(select.getFECVIADOC()));
        fechaFormulario = sdf_d.parse(select.getFECVIADOC());
    }

    public void registrar() throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            if (activo == true) {
                documento.setFECVIADOC(formateador.format(fechaFormulario));
                documento.setCODTIPDOC(dao.obtenerCodigoTIPDOC(documento.getCODTIPDOC()));
                documento.setCODPER(dao.obtenerCodigoDetalleEmpresa(documento.getCODPER()));
                dao.registrar(documento);
            } else {
                documento.setCODTIPDOC(dao.obtenerCodigoTIPDOC(documento.getCODTIPDOC()));
                documento.setCODPER(dao.obtenerCodigoDetalleEmpresa(documento.getCODPER()));
                dao.registrar(documento);
            }
            limpiar();
            listar();
            habi = true;
            listarDoc();
            listarGruDoc();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Completado..."));
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> completeTextTIPDOC(String query) throws SQLException {
        DocumentoImpl Dao = new DocumentoImpl();
        return Dao.autocompleteTIPDOC(query);
    }

    public List<String> completeTextDetalleEmpresa(String query) throws SQLException {
        DocumentoImpl Dao = new DocumentoImpl();
        return Dao.autocompleteDetalleEmpresa(query);
    }

    public void modificar() throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            if (activo) {
                select.setFECVIADOC(formateador.format(fechaFormulario));
                select.setCODTIPDOC(dao.obtenerCodigoTIPDOC(select.getCODTIPDOC()));
                select.setCODPER(dao.obtenerCodigoDetalleEmpresa(select.getCODPER()));
                dao.modificar(select);
            } else {
                select.setCODTIPDOC(dao.obtenerCodigoTIPDOC(select.getCODTIPDOC()));
                select.setCODPER(dao.obtenerCodigoDetalleEmpresa(select.getCODPER()));
                dao.modificar(select);
            }
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }
    
    public void eliminarA() throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            dao.eliminarA(select);
            listarGruDoc();
            listar();
            listarDoc();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void asignar() throws Exception {
        AulaImpl dao;
        try {
            dao = new AulaImpl();
            for (Aula aula1 : selects) {
                dao.asignar(aula1);
            }
            listar();
            habi = false;
            listarGruDoc();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignación", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }
    public void asignarM() throws Exception {
        AulaImpl dao;
        try {
            dao = new AulaImpl();
            for (Aula aula1 : selectsM) {
                dao.asignar(aula1);
            }
            listar();
            habi = false;
            listarGruDoc();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignación", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            dao.eliminar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }


    public void listar() throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            listadoDoc = dao.listar();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarDoc() throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            listadoDocu = dao.listarDoc();
            listarGruDoc();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarGruDoc() throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            listadoGruDoc = dao.listarGruDoc();
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarGruDoc2a(int IDDOC) throws Exception {
        DocumentoImpl dao;
        try {
            dao = new DocumentoImpl();
            listadoGruDoc2 = dao.listarGruDoc2(IDDOC);
            listar();
            listarDoc();
            listarGruDoc();
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiar() {
        documento = new Documento();
    }

    public void reporte1(String tipo, int IDDOC) {
        report reportPrac = new report();
        try {
            Map<String, Object> parameters;
            // Libro de parametros
            //Insertamos un parametro
            switch (tipo) {
                case "1":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "VisitaE.jasper", "Visita1.pdf");
//                    REPORTE("IDDOC", IDDOC);
//                    reporteDos(IDDOC);
                    FacesContext.getCurrentInstance().responseComplete();

                    break;
                case "2":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "AlumnosVisita.jasper", "Visita2.pdf");
//                    REPORTE("IDDOC", IDDOC);
//                    reporteDos(IDDOC);
                    FacesContext.getCurrentInstance().responseComplete();

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //REPORTE DOCUMENTO  
    public void REPORTE_PDF_PRACTICA(String tipo, int IDDOC) throws Exception {
        report reportPrac = new report();
        try {
            Map<String, Object> parameters;
            // Libro de parametros
            //Insertamos un parametro
            switch (tipo) {
                case "SOLICITUD DE PRACTICA PRE-PROFESIONALES":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "Practica.jasper", "Reporte_practica_pre-profesional.pdf");
                    break;
                case "DELEGACIÓN PARA LA VISITA DE ESTUDIO":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "VisitaViaje.jasper", "Reporte_visita_estudio.pdf");
                    REPORTE(IDDOC);
                    FacesContext.getCurrentInstance().responseComplete();

                    break;
                case "DELEGACIÓN PARA EL VIAJE DE ESTUDIO":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "VisitaViaje.jasper", "Reporte_viaje_estudio.pdf");
                    break;
                case "RENOVACIÓN DE PRACTICAS PRE-PROFESIONALES":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "PraticaP.jasper", "Renovación.pdf");
                    break;
                case "SOLICITUD DE RENOVAVIÓN DE CONVENIO":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "Reporte_renovación_practicas.jasper", "Reporte_renovacion_practicas.pdf");
                    break;
                default:
                    break;  
            }
        } catch (Exception e) {
            throw e;
        }
    }
    public void REPORTE_PDF_GRUPO(String tipo, int IDDOC) throws Exception {
        report reportPrac = new report();
        try {
            Map<String, Object> parameters;
            // Libro de parametros
            //Insertamos un parametro
            switch (tipo) {
                case "DELEGACIÓN PARA LA VISITA DE ESTUDIO":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "LstGrupo.jasper", "Reporte_grupo_visita_estudio.pdf");
                    REPORTE(IDDOC);
                    FacesContext.getCurrentInstance().responseComplete();

                    break;
                case "DELEGACIÓN PARA EL VIAJE DE ESTUDIO":
                    parameters = new HashMap();
                    parameters.put("IDDOC", IDDOC);
                    reportPrac.exportarPDF(parameters, "LstGrupo.jasper", "Reporte_grupo_viaje_estudio.pdf");
                    break;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void reporteDos(int IDDOC2) throws IOException, Exception {
        System.out.println("PARAMETRO 2 " + IDDOC2);
        report reportPrac = new report();

        Map<String, Object> parameters;
        parameters = new HashMap();
        parameters.put("IDDOC", IDDOC2);
        reportPrac.exportarPDF(parameters, "AlumnosVisita.jasper", "Visita.pdf");
    }

    public void REPORTE(int IDDOC) throws Exception {
        report reportPrac = new report();
        Map<String, Object> parameters;
        parameters = new HashMap();
        parameters.put("IDDOC", IDDOC);
        reportPrac.exportarPDF(parameters, "LstGrupo.jasper", "Grupo.pdf");

    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Documento getSelect() {
        return select;
    }

    public void setSelect(Documento select) {
        this.select = select;
    }

    public List<Documento> getListadoDoc() {
        return listadoDoc;
    }

    public void setListadoDoc(List<Documento> listadoDoc) {
        this.listadoDoc = listadoDoc;
    }

    public List<Documento> getListadoDoc2() {
        return listadoDoc2;
    }

    public void setListadoDoc2(List<Documento> listadoDoc2) {
        this.listadoDoc2 = listadoDoc2;
    }

    public SimpleDateFormat getFormateador() {
        return formateador;
    }

    public void setFormateador(SimpleDateFormat formateador) {
        this.formateador = formateador;
    }

    public SimpleDateFormat getSdf_d() {
        return sdf_d;
    }

    public void setSdf_d(SimpleDateFormat sdf_d) {
        this.sdf_d = sdf_d;
    }

    public Date getFechaFormulario() {
        return fechaFormulario;
    }

    public void setFechaFormulario(Date fechaFormulario) {
        this.fechaFormulario = fechaFormulario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Documento> getListadoDocu() {
        return listadoDocu;
    }

    public void setListadoDocu(List<Documento> listadoDocu) {
        this.listadoDocu = listadoDocu;
    }

    public List<Documento> getListadoGruDoc() {
        return listadoGruDoc;
    }

    public void setListadoGruDoc(List<Documento> listadoGruDoc) {
        this.listadoGruDoc = listadoGruDoc;
    }

    public List<Aula> getSelects() {
        return selects;
    }

    public void setSelects(List<Aula> selects) {
        this.selects = selects;
    }

    public List<Documento> getListadoGruDoc2() {
        return listadoGruDoc2;
    }

    public void setListadoGruDoc2(List<Documento> listadoGruDoc2) {
        this.listadoGruDoc2 = listadoGruDoc2;
    }

    public boolean isHabi() {
        return habi;
    }

    public void setHabi(boolean habi) {
        this.habi = habi;
    }

    public TipDoc getTipdoc() {
        return tipdoc;
    }

    public void setTipdoc(TipDoc tipdoc) {
        this.tipdoc = tipdoc;
    }

    public List<Aula> getSelectsM() {
        return selectsM;
    }

    public void setSelectsM(List<Aula> selectsM) {
        this.selectsM = selectsM;
    }

    public List<Documento> getListadoGruDoc3() {
        return listadoGruDoc3;
    }

    public void setListadoGruDoc3(List<Documento> listadoGruDoc3) {
        this.listadoGruDoc3 = listadoGruDoc3;
    }
    
}
