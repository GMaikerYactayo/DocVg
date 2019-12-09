package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection Cn;

    public void Conexion() {
        try {
            if (Cn == null) {
                Class.forName("oracle.jdbc.driver.OracleDriver");                
//                Cn = DriverManager.getConnection("jdbc:oracle:thin:@35.184.25.222:1521:XE", "dbDocVG", "DocVG-2019");
                Cn = DriverManager.getConnection("jdbc:oracle:thin:@35.184.25.222:1521:XE", "dbDocVG", "DocVG-2019");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e);
        }
    }
    public void Cerrar() throws SQLException {      //Cerrar la conexion
        if (Cn != null) {
            if (Cn.isClosed() == false) {
                Cn.close();
                Cn = null;
            }
        }
    }

    public Connection getCn() {
        return Cn;
    }

    public void setCn(Connection Cn) {
        this.Cn = Cn;
    }


    public static void main(String[] args) {
        Conexion dao = new Conexion();
        dao.Conexion();
        if (dao.getCn()!= null) {
            System.out.println("Conectado");
        } else {
            System.out.println("No hay Conexion");
        }
    }
}
