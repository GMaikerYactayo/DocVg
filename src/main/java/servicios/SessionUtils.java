package servicios;

import javax.faces.context.FacesContext;
import model.Usuario;

public class SessionUtils {

    public static Usuario obtenerObjetoSesion() {
        return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
    }

    public static String ObtenerNombreSesion() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (us != null) {
            return us.getUSEUSU();
        } else {
            return null;
        }
    }

    public static int ObtenerCodigoSesion() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        if (us != null) {
            return us.getCODUSU();
        } else {
            return 0;
        }
    }

}
