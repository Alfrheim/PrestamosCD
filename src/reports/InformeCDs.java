package reports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class InformeCDs {
	private static String URLCon = 
		"jdbc:mysql://localhost:3306/rompepiernas?user=usuario1&password=usuario1";
	private static String PATH = "InformesPDF/";

	private static final String INFORME_COMPLETO = 
		"./informes/InformeCompleto.jrxml";
	
	private static final String INFORME_CDS_PRESTADOS = 
		"./informes/InformeCDsPrestados.jrxml";
	
	private static final String INFORME_CDS_DISPONIBLES = 
		"./informes/InformeCDsDisponibles.jrxml";
	
	public static String informeCompleto() {
		String file = "InformeCompleto.pdf";
		if(report(INFORME_COMPLETO, file)==0) {
			file = "";
		}
		return file;
	}
	
	public static String informeCDsPrestados() {
		String file = "InformeCDsPrestados.pdf";
		if(report(INFORME_CDS_PRESTADOS, file)==0) {
			file = "";
		}			
		return file;
	}
	public static String informeCDsDisponibles() {
		String file = "InformeCDsDisponibles.pdf";
		if(report(INFORME_CDS_DISPONIBLES, file)==0) {
			file = "";
		}
		return file;
	}
	
	private static int report(String informe, String tipo) {
		JasperReport jasperReport;
        JasperPrint jasperPrint;
        int completado = 0;
		try {
			// 1-Se hace la conexion a la Base de Datos
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager
					.getConnection(URLCon);

			// 2-Compilamos el archivo XML y lo cargamos en memoria
			jasperReport = JasperCompileManager.compileReport(informe);

			// 3-Llenamos el informe con la información de la BD y parámetros
			// necesarios para la consulta
			jasperPrint = JasperFillManager
					.fillReport(jasperReport, new HashMap(), conn);

			// 4-Exportamos el report a pdf y lo guardamos en disco
			JasperExportManager.exportReportToPdfFile(jasperPrint, PATH+tipo);

			// 5-Cerrar la conexion
			conn.close();

			System.out.println("Informe "+tipo+" generado.");
			completado = 1;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return completado;
	}
	public static String getPath() {
		return PATH;
	}
    
}



