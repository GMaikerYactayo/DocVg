package controlador;

import java.util.Map;
import java.util.HashMap;
import Reportes.report;
import dao.EmpresaImpl;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Empresa;

@Named(value = "empresaC")
@SessionScoped
public class EmpresaC implements Serializable {

    private Empresa empresa = new Empresa();
    private Empresa select;
    private List<Empresa> listadoEstado;
    private List<Empresa> listadoEmp2;
    private boolean bt;

    @PostConstruct
    public void iniciar() {
        try {
            listarEstado("A");
        } catch (Exception e) {
        }
    }

    public void registrar() throws Exception {
        EmpresaImpl dao;
        try {
            dao = new EmpresaImpl();
            empresa.setCODUBI(dao.obtenerCodigoUbigeo(empresa.getCODUBI()));
            dao.registrar(empresa);
            limpiar();
            listarEstado("A");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Completado..."));
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> completeTextUbigeo(String query) throws SQLException {
        EmpresaImpl Dao = new EmpresaImpl();
        return Dao.autocompleteUbigeo(query);
    }

    public List<String> completeTextResponsable(String query) throws SQLException {
        EmpresaImpl Dao = new EmpresaImpl();
        return Dao.autocompleteResponsable(query);
    }

    public void modificar() throws Exception {
        EmpresaImpl dao;
        try {
            dao = new EmpresaImpl();
            select.setCODUBI(dao.obtenerCodigoUbigeo(select.getCODUBI()));
            System.out.println(select.getCODUBI());
            dao.modificar(select);
            listarEstado("A");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        EmpresaImpl dao;
        try {
            dao = new EmpresaImpl();
            dao.eliminar(select);
            listarEstado("A");
            listarEstado("I");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void activar() throws Exception {
        EmpresaImpl dao;
        try {
            dao = new EmpresaImpl();
            dao.activar(select);
            listarEstado("I");
            listarEstado("A");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Activación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }


    public void listarEstado(String filtro) throws Exception {
        EmpresaImpl dao;
        dao = new EmpresaImpl();
        try {
            if (filtro.equals("A")) {
                bt = true;
            } else {
                bt = false;
            }
            listadoEstado = dao.listarEstado(filtro);
        } catch (Exception e) {
            throw e;
        }

    }

    public void limpiar() throws Exception {
        try {
            empresa = new Empresa();
        } catch (Exception e) {
            throw e;
        }
    }

    public void REPORTE_PDF_EMPRESA(String CodigoEMPRESA) throws Exception {
        report reportEmp = new report();
        try {
            Map<String, Object> parameters = new HashMap(); // Libro de parametros
            parameters.put(null, CodigoEMPRESA); //Insertamos un parametro
            reportEmp.exportarPDF(parameters, "LstEmpresa.jasper", "Reporte_empresas.pdf");//Pido exportar Reporte con los parametros
//            report.exportarPDF2(parameters);
        } catch (Exception e) {
            throw e;
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getListadoEstado() {
        return listadoEstado;
    }

    public void setListadoEstado(List<Empresa> listadoEstado) {
        this.listadoEstado = listadoEstado;
    }

    public Empresa getSelect() {
        return select;
    }

    public void setSelect(Empresa select) {
        this.select = select;
    }

    public List<Empresa> getListadoEmp2() {
        return listadoEmp2;
    }

    public void setListadoEmp2(List<Empresa> listadoEmp2) {
        this.listadoEmp2 = listadoEmp2;
    }

    public boolean isBt() {
        return bt;
    }

    public void setBt(boolean bt) {
        this.bt = bt;
    }

}
