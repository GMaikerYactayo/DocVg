package controlador;

import dao.CarreraImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Carrera;

@Named(value = "carreraC")
@SessionScoped
public class CarreraC implements Serializable {

    private Carrera carrera = new Carrera();
    private Carrera select;
    private List<Carrera> listadoCar;
    private List<Carrera> listadoCar2;


    @PostConstruct
    public void iniciar() {
        try {
            listar();
        } catch (Exception e) {
        }
    }

    public void registrar() throws Exception {
        CarreraImpl dao;
        try {
            dao = new CarreraImpl();
            dao.registrar(carrera);
            limpiar();
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Completado..."));
        } catch (Exception e) {
            throw e;
        }
    }

    public void modificar() throws Exception {
        CarreraImpl dao;
        try {
            dao = new CarreraImpl();
            dao.modificar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización", "Completado.."));
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar() throws Exception {
        CarreraImpl dao;
        try {
            dao = new CarreraImpl();
            dao.eliminar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Eliminación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }
    public void activar() throws Exception {
        CarreraImpl dao;
        try {
            dao = new CarreraImpl();
            dao.activar(select);
            listar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Activación", "Completado.."));
        } catch (SQLException e) {
            throw e;
        }
    }

    public void listar() throws Exception {
       CarreraImpl dao;
        try {
            dao = new CarreraImpl();
            listadoCar = dao.listar();
        } catch (Exception e) {
            throw e;
        }
    }


    public void limpiar() throws Exception {
        try {
            carrera = new Carrera();
        } catch (Exception e) {
            throw e;
        }
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public List<Carrera> getListadorCar() {
        return listadoCar;
    }

    public void setListadoCar(List<Carrera> listadoCar) {
        this.listadoCar = listadoCar;
    }

    public Carrera getSelect() {
        return select;
    }

    public void setSelect(Carrera select) {
        this.select = select;
    }

    public List<Carrera> getListadoCar2() {
        return listadoCar2;
    }

    public void setListadoCar2(List<Carrera> listadoCar2) {
        this.listadoCar2 = listadoCar2;
    }
    
}
