package controlador;

import Reportes.report;
import dao.CanPerImpl;
import dao.PersonaImpl;
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
import model.CantPer;
import model.Persona;
import org.primefaces.model.chart.PieChartModel;

@Named(value = "personaC")
@SessionScoped
public class PersonaC implements Serializable {

    private Persona persona = new Persona();
    private Persona select;
    private List<Persona> listadoPerAlu;
    private List<Persona> listadoPerAlu2;
    private List<Persona> listadoPerExt;
    private List<Persona> listadoPerExt2;

    private boolean bt;

    private PieChartModel Circulo;
    private List<CantPer> lstCantPer;

    @PostConstruct
    public void iniciar() {
        try {
            lstCantPers();
            listarAlumnos("A");
            listarExternos("A");
        } catch (Exception e) {
        }
    }

    public void lstCantPers() throws Exception {
        CanPerImpl dao;
        try {
            dao = new CanPerImpl();
            lstCantPer = dao.lstCantPer();
            createPie(lstCantPer);
        } catch (SQLException e) {
            throw e;
        }
    }

    private void createPie(List<CantPer> lista) {
        Circulo = new PieChartModel();
        for (CantPer per : lstCantPer) {
            Circulo.set(per.getAluPer(), per.getCanAlu());
        }
        Circulo.setTitle("CANTIDAD DE PERSONAS");
        Circulo.setLegendPosition("ne");
        Circulo.setShowDataLabels(true);
        Circulo.setDiameter(200);
    }

    public void registrar() throws Exception {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            persona.setCODUBI(dao.obtenerCodigoUbigeo(persona.getCODUBI()));
            persona.setCODEMP(dao.obtenerCodigoEmpresa(persona.getCODEMP()));
            dao.registrar(persona);
            limpiar();
            listarAlumnos("A");
            listarExternos("A");
            lstCantPers();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Completado..."));
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void registrarP() throws Exception {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            persona.setCODEMP(dao.obtenerCodigoEmpresa(persona.getCODEMP()));
            dao.registrarEx(persona);
            limpiar();
            lstCantPers();
            listarExternos("A");
            listarAlumnos("A");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Completado..."));
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> completeTextEmpresa(String query) throws SQLException {
        PersonaImpl Dao = new PersonaImpl();
        return Dao.autocompleteEmpresa(query);
    }

    public boolean validarExistenciaPersona(String dniPersona) {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            Persona personaValidacion = new Persona();
            personaValidacion = dao.validarExistenciaPersona(dniPersona);
            String DNIPER = personaValidacion.getDNIPER().toLowerCase().trim();
            if (DNIPER.equals(dniPersona)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public List<String> completeTextUbigeo(String query) throws SQLException {
        PersonaImpl Dao = new PersonaImpl();
        return Dao.autocompleteUbigeo(query);
    }

    public void modificar() throws Exception {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            select.setCODUBI(dao.obtenerCodigoUbigeo(select.getCODUBI()));
            select.setCODEMP(dao.obtenerCodigoEmpresa(select.getCODEMP()));
            System.out.println(select.getCODUBI());
            dao.modificar(select);
            lstCantPers();
            listarAlumnos("A");
            listarExternos("A");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            dao.eliminar(select);
            listarAlumnos("A");
            listarExternos("A");
            lstCantPers();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void activar() throws Exception {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            dao.activar(select);
            listarAlumnos("I");
            listarExternos("I");
            lstCantPers();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Activación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void listarAlumnos(String filtro) throws Exception {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            if (filtro.equals("A")) {
                bt = true;
            } else {
                bt = false;
            }
            listadoPerAlu = dao.listarALUMNOS(filtro);
        } catch (Exception e) {
            throw e;
        }
    }

    public void listarExternos(String filtro) throws Exception {
        PersonaImpl dao;
        try {
            dao = new PersonaImpl();
            if (filtro.equals("A")) {
                bt = true;
            } else {
                bt = false;
            }
            listadoPerExt = dao.listarExternos(filtro);
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiar() throws Exception {
        try {
            persona = new Persona();
        } catch (Exception e) {
            throw e;
        }
    }

    public void REPORTE_PDF_ALUMNO(String CodigoAlumno) throws Exception {
        report reportAlu = new report();
        try {
            Map<String, Object> parameters = new HashMap(); // Libro de parametros
            parameters.put(null, CodigoAlumno); //Insertamos un parametro
            reportAlu.exportarPDF(parameters, "LstAlumnos.jasper", "Reporte_alumnos.pdf"); //Pido exportar Reporte con los parametros
//            report.exportarPDF2(parameters);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void REPORTE_PDF_EXTERNO(String CodigoAlumno) throws Exception {
        report reportExt = new report();
        try {
            Map<String, Object> parameters = new HashMap(); // Libro de parametros
            parameters.put(null, CodigoAlumno); //Insertamos un parametro
            reportExt.exportarPDF(parameters, "LstExternos.jasper", "Reporte_externos.pdf"); //Pido exportar Reporte con los parametros
//            report.exportarPDF2(parameters);
        } catch (Exception e) {
            throw e;
        }
    }


    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Persona getSelect() {
        return select;
    }

    public void setSelect(Persona select) {
        this.select = select;
    }

    public List<Persona> getListadoPerExt() {
        return listadoPerExt;
    }

    public void setListadoPerExt(List<Persona> listadoPerExt) {
        this.listadoPerExt = listadoPerExt;
    }

    public List<Persona> getListadoPerExt2() {
        return listadoPerExt2;
    }

    public void setListadoPerExt2(List<Persona> listadoPerExt2) {
        this.listadoPerExt2 = listadoPerExt2;
    }

    public PieChartModel getCirculo() {
        return Circulo;
    }

    public void setCirculo(PieChartModel Circulo) {
        this.Circulo = Circulo;
    }

    public List<CantPer> getLstCantPer() {
        return lstCantPer;
    }

    public void setLstCantPer(List<CantPer> lstCantPer) {
        this.lstCantPer = lstCantPer;
    }

    public List<Persona> getListadoPerAlu() {
        return listadoPerAlu;
    }

    public void setListadoPerAlu(List<Persona> listadoPerAlu) {
        this.listadoPerAlu = listadoPerAlu;
    }

    public List<Persona> getListadoPerAlu2() {
        return listadoPerAlu2;
    }

    public void setListadoPerAlu2(List<Persona> listadoPerAlu2) {
        this.listadoPerAlu2 = listadoPerAlu2;
    }

    public boolean isBt() {
        return bt;
    }

    public void setBt(boolean bt) {
        this.bt = bt;
    }

}
