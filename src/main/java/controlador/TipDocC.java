package controlador;

import dao.TipDocImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.TipDoc;

@Named(value = "tipDocC")
@SessionScoped
public class TipDocC implements Serializable {
    
    private TipDoc tipdoc = new TipDoc();
    private TipDoc select;
    private List<TipDoc> listadoTip;
    private List<TipDoc> listadoTip2;

    @PostConstruct
    public void iniciar() {
        try {
            listar();
        } catch (Exception e) {
        }
    }

    public void registrar() throws Exception {
        TipDocImpl dao;
        try {
            dao = new TipDocImpl();
            dao.registrar(tipdoc);
            listar();
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Completado..."));
        } catch (Exception e) {
            throw e;
        }
    }


    public void modificar() throws Exception {
        TipDocImpl dao;
        try {
            dao = new TipDocImpl();
            dao.modificar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }
 
    public void eliminar() throws Exception {
        TipDocImpl dao;
        try {
            dao = new TipDocImpl();
            dao.eliminar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }
    public void activar() throws Exception {
        TipDocImpl dao;
        try {
            dao = new TipDocImpl();
            dao.activar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Activación", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }

    public void listar() throws Exception {
        TipDocImpl dao;
        try {
            dao = new TipDocImpl();
            listadoTip = dao.listar();
        } catch (Exception e) {
            throw e;
        }
    }


    public void limpiar() throws Exception {
        try {
            tipdoc = new TipDoc();
        } catch (Exception e) {
            throw e;
        }
    }

    public TipDoc getTipdoc() {
        return tipdoc;
    }

    public void setTipdoc(TipDoc tipdoc) {
        this.tipdoc = tipdoc;
    }

    public TipDoc getSelect() {
        return select;
    }

    public void setSelect(TipDoc select) {
        this.select = select;
    }

    public List<TipDoc> getListadoTip() {
        return listadoTip;
    }

    public void setListadoTip(List<TipDoc> listadoTip) {
        this.listadoTip = listadoTip;
    }

    public List<TipDoc> getListadoTip2() {
        return listadoTip2;
    }

    public void setListadoTip2(List<TipDoc> listadoTip2) {
        this.listadoTip2 = listadoTip2;
    }
    
}
