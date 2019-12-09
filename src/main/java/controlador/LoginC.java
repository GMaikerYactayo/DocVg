package controlador;

import dao.Login;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import model.Persona;
import model.Usuario;
import org.primefaces.context.RequestContext;
import servicios.SessionUtils;

@Named(value = "loginC")
@SessionScoped
public class LoginC implements Serializable {

    Usuario usuario = new Usuario();
    //Logeo
    private String TIPUSU;
    private String USEUSU;
    private String PSWUSU;
    Login dao;
    private int intentos, number;

    public void increment() {
        number++;
        if (number > 5) {
            number = 0;
            intentos = 0;
            RequestContext.getCurrentInstance().execute(" location.reload (); ");
        }
    }

    public void startSession() throws Exception {
        try {
            dao = new Login();
            usuario = dao.startSession(USEUSU, PSWUSU);
            if (usuario != null) {
                intentos = 0;
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", usuario);
                switch (usuario.getTIPUSU()) {
                    case "MONITOR":
                        FacesContext.getCurrentInstance().getExternalContext().redirect("/DocVG/faces/vistas/Grafica/Grafica.xhtml");
                        break;
                    case "2":
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No eres un profesor >:v", "no lo intentes"));
                        System.out.println("Error porque no es profesor");
                        break;
                }
            } else {
                setPSWUSU(null);
                usuario = new Usuario();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Ingreso mal el usuario o contraseña", "o el usuario debe estar inactivo"));
                intentos++;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void securityLogin() throws IOException {
        Usuario us = SessionUtils.obtenerObjetoSesion();
        if (us != null) {
            switch (us.getTIPUSU()) {
                case "1":
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/DocVG/faces/vistas/LoginN/login.xhtml");
                    break;
                case "2":
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/DocVG/faces/vistas/LoginN/login.xhtml");
                    break;
            }
        }
    }

    public void securitySession() throws IOException {
        if (SessionUtils.obtenerObjetoSesion() == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/DocVG/faces/vistas/LoginN/login.xhtml");
        }
    }

    public void obtenerDatos() {
        System.out.println(SessionUtils.ObtenerCodigoSesion());
        System.out.println(SessionUtils.ObtenerNombreSesion());
    }

    public void cerrar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/DocVG/faces/vistas/LoginN/login.xhtml");
    }

    public void logout() {
        ExternalContext ctx
                = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath
                = ((ServletContext) ctx.getContext()).getContextPath();
        try {
            ((HttpSession) ctx.getSession(false)).invalidate();
            ctx.redirect(ctxPath + "/DocVG/faces/vistas/LoginN/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTIPUSU() {
        return TIPUSU;
    }

    public void setTIPUSU(String TIPUSU) {
        this.TIPUSU = TIPUSU;
    }

    public String getUSEUSU() {
        return USEUSU;
    }

    public void setUSEUSU(String USEUSU) {
        this.USEUSU = USEUSU;
    }

    public String getPSWUSU() {
        return PSWUSU;
    }

    public void setPSWUSU(String PSWUSU) {
        this.PSWUSU = PSWUSU;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
