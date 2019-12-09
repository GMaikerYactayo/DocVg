package controlador;

import dao.CanPerImpl;
import dao.DetEmpImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.CanAlu;
import model.DetEmp;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;

@Named(value = "detEmpC")
@SessionScoped
public class DetEmpC implements Serializable {

    private DetEmp detemp = new DetEmp();
    private DetEmp select;
    private List<DetEmp> selects;
    private List<DetEmp> listadoDetEmp;
    private List<DetEmp> listadoDetEmp2;
    
    private BarChartModel barra;
    private List<CanAlu> lstCantAlu;

    @PostConstruct
    public void iniciar() {
        try {
            lstCantAlu();
            listar();
        } catch (Exception e) {
        }
    }

    public DetEmpC() {
        selects = new ArrayList();
    }
    
    
    public void lstCantAlu() throws Exception {
        CanPerImpl dao;
        try {
            dao = new CanPerImpl();
            lstCantAlu = dao.lstCantAlu();
            createBarras();
            listar();
        } catch (SQLException e) {
            throw e;
        }
    }

    private void createBarras() {
        try {
            barra = new BarChartModel();
            for (int i = 0; i < lstCantAlu.size(); i++) {
                ChartSeries serie = new BarChartSeries();
                serie.setLabel(lstCantAlu.get(i).getAULA());
                serie.set(lstCantAlu.get(i).getAULA(), lstCantAlu.get(i).getCanAlu());
                barra.addSeries(serie);
            }
            Axis xAxis = barra.getAxis(AxisType.X);
            xAxis.setLabel("MÓDULOS");

            barra = getBarra();
            barra.setTitle("CANTIDAD DE ALUMNOS POR MÓDULO");
            barra.setAnimate(true);
            barra.setLegendPosition("ne");
            Axis yAxis = barra.getAxis(AxisType.Y);
            yAxis.setLabel("ALUMNOS");
            yAxis.setMin(0);
            yAxis.setMax(50);
        } catch (Exception e) {
            System.out.println("error" +e.getMessage());
        }
    }

    public List<String> completeTextPersona(String query) throws SQLException {
        DetEmpImpl Dao = new DetEmpImpl();
        return Dao.autocompletePersona(query);
    }

    public List<String> completeTextEmpresa(String query) throws SQLException {
        DetEmpImpl Dao = new DetEmpImpl();
        return Dao.autocompleteEmpresa(query);
    }

    public void registrar() throws Exception {
        DetEmpImpl dao;
        try {
            dao = new DetEmpImpl();
            detemp.setCODPER(dao.obtenerCodigoPersona(detemp.getCODPER()));
            detemp.setCODEMP(dao.obtenerCodigoEmpresa(detemp.getCODEMP()));
            dao.registrar(detemp);
            limpiar();
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Completado..."));
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modificar() throws Exception {
        DetEmpImpl dao;
        try {
            dao = new DetEmpImpl();
            select.setCODPER(dao.obtenerCodigoPersona(select.getCODPER()));
            select.setCODEMP(dao.obtenerCodigoEmpresa(select.getCODEMP()));
            dao.modificar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        DetEmpImpl dao;
        try {
            dao = new DetEmpImpl();
            dao.eliminar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void asignar() throws Exception {
        DetEmpImpl dao;
        try {
            dao = new DetEmpImpl();
            dao.eliminar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Asignación", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }

    public void listar() throws Exception {
        DetEmpImpl dao;
        try {
            dao = new DetEmpImpl();
            listadoDetEmp = dao.listar();
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiar() throws Exception {
        try {
            detemp = new DetEmp();
        } catch (Exception e) {
            throw e;
        }
    }

    public DetEmp getDetemp() {
        return detemp;
    }

    public void setDetemp(DetEmp detemp) {
        this.detemp = detemp;
    }

    public DetEmp getSelect() {
        return select;
    }

    public void setSelect(DetEmp select) {
        this.select = select;
    }

    public List<DetEmp> getSelects() {
        return selects;
    }

    public void setSelects(List<DetEmp> selects) {
        this.selects = selects;
    }

    public List<DetEmp> getListadoDetEmp() {
        return listadoDetEmp;
    }

    public void setListadoDetEmp(List<DetEmp> listadoDetEmp) {
        this.listadoDetEmp = listadoDetEmp;
    }

    public List<DetEmp> getListadoDetEmp2() {
        return listadoDetEmp2;
    }

    public void setListadoDetEmp2(List<DetEmp> listadoDetEmp2) {
        this.listadoDetEmp2 = listadoDetEmp2;
    }

    public BarChartModel getBarra() {
        return barra;
    }

    public void setBarra(BarChartModel barra) {
        this.barra = barra;
    }

    public List<CanAlu> getLstCantAlu() {
        return lstCantAlu;
    }

    public void setLstCantAlu(List<CanAlu> lstCantAlu) {
        this.lstCantAlu = lstCantAlu;
    }

    

}