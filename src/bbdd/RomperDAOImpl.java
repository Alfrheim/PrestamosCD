package bbdd;


import java.sql.*;
import java.util.Vector;



import datos.CDBean;

public class RomperDAOImpl implements RomperDAO {
	
	private String URLCon = "jdbc:mysql://localhost:3306/rompepiernas?user=randolph&password=randolph";
	
	
	public RomperDAOImpl() {
		Conexion.setURL(URLCon);		
	}

	@Override
	public int actualizarCD(CDBean cd) {
		// TODO Posar un boto per actualitzar individualment un encomptes de tot el vector?
		int update = 0;
		try {
			Connection con = Conexion.getConexion();
			PreparedStatement ps =
				con.prepareStatement("UPDATE cd SET" +
						"autor = ?, " +
						"genero = ?, " +
						"prestamo = ? " +
						"WHERE titulo = ?");
			ps.setString(1, cd.getAutor());
			ps.setString(2, cd.getGenero());
			ps.setString(3, cd.getPrestamo());
			ps.setString(4, cd.getTitulo());
			update = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.desconecta();
		}
		return update;
	}

	@Override
	public int actualizarCDs(Vector<CDBean> vector) {
		limpiarTabla();
		for(CDBean cd:vector) {			
			System.out.print("Actualizando: ");
			System.out.print(cd.toString());
			if(guardarCD(cd)==0) {
				System.out.println("Error al guardar");				
			} else {
				System.out.println("\tOK!");
			}
			
		}		
		return 0;
	}

	@Override
	public int borrarCD(int id) {
		int update = 0;
		try {
			Connection con = Conexion.getConexion();
			PreparedStatement ps = con.prepareStatement("DELETE FROM cd WHERE id = ?");
			ps.setInt(1, id);
			update = ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.desconecta();
		}		
		return update;
	}

	@Override
	public Vector<CDBean> cargarCDs() {
		
		Vector<CDBean> CDs = new Vector<CDBean>();		
		try {
			Connection con = Conexion.getConexion();
			PreparedStatement ps = con.prepareStatement("SELECT titulo, autor, genero, prestamo FROM cd");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {				
				String titulo = rs.getString(1);
				String autor = rs.getString(2);
				String genero = rs.getString(3);
				String prestamo = rs.getString(4);
				CDs.add(new CDBean(titulo, autor, genero, prestamo));
			}
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.desconecta();
		}
		return CDs;
	}
	@Override
	public int guardarCD(CDBean cd) {
		int update = 0;
		try {
			Connection con = Conexion.getConexion();
			PreparedStatement ps =
				con.prepareStatement("INSERT INTO cd VALUES(" +
						"?, ?, ?, ?)");
			ps.setString(1, cd.getTitulo());
			ps.setString(2, cd.getAutor());
			ps.setString(3, cd.getGenero());
			ps.setString(4, cd.getPrestamo());
			update = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.desconecta();
		}
		return update;
	}

	@Override
    public void limpiarTabla() {
	    try {
	    	Connection con = Conexion.getConexion();
	    	PreparedStatement ps = con.prepareStatement("DELETE FROM cd");
	    	ps.execute();
	    	ps.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexion.desconecta();
		}	    
    }
}
