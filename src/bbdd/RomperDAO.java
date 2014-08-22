package bbdd;

import java.util.Vector;

import datos.CDBean;


public interface RomperDAO {
	
	public Vector<CDBean> cargarCDs();
	public int actualizarCDs(Vector<CDBean> vector);
	public int guardarCD(CDBean cd);
	public int actualizarCD(CDBean cd);
	public int borrarCD(int id);
	public void limpiarTabla();

}
