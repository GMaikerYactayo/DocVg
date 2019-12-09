package controlador;

import Reportes.report;
import dao.AulaImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Aula;

@Named(value = "aulaC")
@SessionScoped
public class AulaC implements Serializable {

    private Aula aula = new Aula();
    private Aula select;
    private List<Aula> selects;
    private List<Aula> ListadoMonitor;
    private List<Aula> ListadoAul;
    private List<Aula> ListadoDetAul;
    private List<Aula> ListadoDetAul2;

    @PostConstruct
    public void iniciar() {
        try {
            listar();
            listarAula();
            listarMonitor();
        } catch (Exception e) {
        }
    }
    
    
    public void limpiar() {
        aula = new Aula();
    }

    public void listar() throws Exception {
        AulaImpl dao;
        try {
            dao = new AulaImpl();
            ListadoDetAul = dao.listar();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void listarMonitor() throws Exception {
        AulaImpl dao;
        try {
            dao = new AulaImpl();
            ListadoMonitor = dao.listarMonitor();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void listarAula() throws Exception {
        AulaImpl dao;
        try {
            dao = new AulaImpl();
            ListadoAul = dao.listarAula();
        } catch (Exception e) {
            throw e;
        }
    }
    

    public Aula getSelect() {
        return select;
    }

    public void setSelect(Aula select) {
        this.select = select;
    }

    public List<Aula> getSelects() {
        return selects;
    }

    public void setSelects(List<Aula> selects) {
        this.selects = selects;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public List<Aula> getListadoDetAul() {
        return ListadoDetAul;
    }

    public void setListadoDetAul(List<Aula> ListadoDetAul) {
        this.ListadoDetAul = ListadoDetAul;
    }

    public List<Aula> getListadoDetAul2() {
        return ListadoDetAul2;
    }

    public void setListadoDetAul2(List<Aula> ListadoDetAul2) {
        this.ListadoDetAul2 = ListadoDetAul2;
    }

    public List<Aula> getListadoAul() {
        return ListadoAul;
    }

    public void setListadoAul(List<Aula> ListadoAul) {
        this.ListadoAul = ListadoAul;
    }

    public List<Aula> getListadoMonitor() {
        return ListadoMonitor;
    }

    public void setListadoMonitor(List<Aula> ListadoMonitor) {
        this.ListadoMonitor = ListadoMonitor;
    }
    
}
