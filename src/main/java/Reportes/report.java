package Reportes;

import dao.Conexion;
import dao.TipDocImpl;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import model.TipDoc;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import servicios.SessionUtils;

public class report extends Conexion {
    
    public void exportarPDF(Map parameters, String ruta, String PDF) throws JRException, IOException, Exception {
        this.Conexion();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/"+ ruta +""));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.getCn());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename="+ PDF +"");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.close();
        }
        
    }

    
}

