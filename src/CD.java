import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import reports.InformeCDs;

import bbdd.RomperDAO;
import bbdd.RomperDAOImpl;

import datos.CDBean;


public class CD extends JFrame {
	private Vector<CDBean> CDs;
	private Vector<CDBean> CDsBorrados;
	private JTextField jtfTitulo;
	private JTextField jtfAutor;
	private JTextField jtfGenero;
	private JTextArea jtaPrestamo;
	BuscarReg dlgBuscar;
	private RomperDAO dao = null;
	DatosBorrados dlgBorrados;

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CD inst = new CD();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public CD () {
		super("A quién hay que romper las piernas?");
		initGUI();
		dao = new RomperDAOImpl();
		CDs = dao.cargarCDs();
		CDsBorrados = new Vector<CDBean>();
	}
	
	private void initGUI() {
		try {						
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					salirForm();
				}
			});
			//setResizable(false);
						
			JPanel contenedor = new JPanel();
			contenedor.setLayout(null);
			contenedor.setOpaque(false);			
						
			//MENU
			JMenuBar jmnuBar = new JMenuBar();
			jmnuBar.setOpaque(false);
			JMenu jmnuArchivo = new JMenu("Archivo");
			jmnuArchivo.setForeground(Color.white);
			jmnuArchivo.setMnemonic(KeyEvent.VK_A);
			
			JMenu jmnuDatos = new JMenu("Datos");
			jmnuDatos.setForeground(Color.white);
			jmnuDatos.setMnemonic(KeyEvent.VK_D);
			
			JMenu jmnuInformes = new JMenu("Informes");
			jmnuInformes.setForeground(Color.white);
			jmnuInformes.setMnemonic(KeyEvent.VK_I);
						
			//Menu ARCHIVO
			JMenuItem jmItemAnadirReg = new JMenuItem("Añadir/Modificar Registro");
			jmItemAnadirReg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jmItemAnadirRegActionPerformed(evt);
				}
			});
			
			JMenuItem jmItemBuscarReg = new JMenuItem("Buscar Registro");
			jmItemBuscarReg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jmItemBuscarRegActionPerformed(evt);
				}
			});
			
			JMenuItem jmItemSalir = new JMenuItem("Salir    Alt+Q",
											KeyEvent.VK_Q);
			jmItemSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					salirForm();
				}
				});
			
			//Menu DATOS
			JMenu jmnuRecuperar = new JMenu("Actualizar");
			JMenuItem jmItemRecuperar = new JMenuItem("de la BBDD");
			jmItemRecuperar.addActionListener(new ActionListener() {

				@Override
                public void actionPerformed(ActionEvent arg0) {
					actualizarDeBBDDForm();                
                }
				
			});
			JMenuItem jmItemRecuperar2 = new JMenuItem("cd borrado");
			jmItemRecuperar2.addActionListener(new ActionListener() {

				@Override
                public void actionPerformed(ActionEvent evt) {
	                jmItemCDsBorradosActionPerformed(evt);
	                
                }
				
			});
			jmnuRecuperar.add(jmItemRecuperar);
			jmnuRecuperar.add(jmItemRecuperar2);
			JMenuItem jmItemActualizarBD = new JMenuItem("Sobrescribir BD");
			jmItemActualizarBD.addActionListener(new ActionListener() {

				@Override
                public void actionPerformed(ActionEvent evt) {
					String texto = "Todos los datos de la BD se canviaran, ¿Está usted seguro?";
					int respuesta = confirmarForm(texto, "Sobrescribir BD");
					if(respuesta == JOptionPane.YES_OPTION) {
						dao.actualizarCDs(CDs);	
					}
					                
                }
				
			});
			
			//Menu INFORMES
			JMenuItem jmItemInformeCompleto = new JMenuItem("Informe Completo");
			jmItemInformeCompleto.addActionListener(new ActionListener() {

				@Override
                public void actionPerformed(ActionEvent evt) {
					String file = InformeCDs.informeCompleto();
	                if(file != "") {
	                	mostrarInformes(file);
	                } //TODO fer algo si ha fallat la creacio
	                
                }
				
			});
			
			JMenuItem jmItemSoloPrestado = new JMenuItem("Solo prestados");
			jmItemSoloPrestado.addActionListener(new ActionListener() {

				@Override
                public void actionPerformed(ActionEvent evt) {
					String file = InformeCDs.informeCDsPrestados();
	                if(file != "") {
	                	mostrarInformes(file);
	                } //TODO fer algo si ha fallat la creacio
	                
                }
				
			});
			
			JMenuItem jmItemSoloNoPrestados = new JMenuItem("Solo no prestados");
			jmItemSoloNoPrestados.addActionListener(new ActionListener() {

				@Override
                public void actionPerformed(ActionEvent arg0) {
					String file = InformeCDs.informeCDsDisponibles();
	                if(file != "") {
	                	mostrarInformes(file);
	                } //TODO fer algo si ha fallat la creacio                
                }
				
			});
			
			//añadimos los items al menu
			jmnuArchivo.add(jmItemAnadirReg);
			jmnuArchivo.add(jmItemBuscarReg);
			jmnuArchivo.addSeparator();
			jmnuArchivo.add(jmItemSalir);
			
			jmnuInformes.add(jmItemSoloPrestado);
			jmnuInformes.add(jmItemSoloNoPrestados);
			jmnuInformes.addSeparator();
			jmnuInformes.add(jmItemInformeCompleto);			
			
			jmnuDatos.add(jmnuRecuperar);
			jmnuDatos.addSeparator();
			jmnuDatos.add(jmItemActualizarBD);
			
			//añadimos el menu a la barra y la barra al JFrame
			jmnuBar.add(jmnuArchivo);
			jmnuBar.add(jmnuDatos);
			jmnuBar.add(jmnuInformes);
			setJMenuBar(jmnuBar);
			//Final del MENU
			
			JLabel jlbTitulo = new JLabel("Titulo");
			jlbTitulo.setBounds(20, 20, 50, 14);
			jlbTitulo.setForeground(Color.white);
			
			JLabel jlbAutor = new JLabel("Autor");
			jlbAutor.setBounds(20, 60, 50, 14);
			jlbAutor.setForeground(Color.white);
						
			JLabel jlbGenero = new JLabel("Genero");
			jlbGenero.setBounds(20, 100, 100, 14);
			jlbGenero.setForeground(Color.white);
			
			JLabel jlbPrestamo = new JLabel("Prestamo");
			jlbPrestamo.setBounds(20, 140, 100, 14);
			jlbPrestamo.setForeground(Color.white);
			
			JLabel jlbCreditos = new JLabel("copyright 2009 - SoftCIF");
			jlbCreditos.setBounds(3, 225, 392, 14);
			jlbCreditos.setForeground(Color.white);
			
			jtfTitulo = new JTextField();
			jtfTitulo.setBounds(100, 17, 275, 21);
			jtfTitulo.setBackground(new Color(255,255,255,80));
			jtfTitulo.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent evt) {
					jtfFocusGained(evt);
				}
				public void focusLost(FocusEvent evt) {
					jtfFocusLost(evt);
				}
			});
			
			jtfAutor = new JTextField();
			jtfAutor.setBounds(100, 57, 275, 21);
			jtfAutor.setBackground(new Color(255,255,255,80));
			jtfAutor.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent evt) {
					jtfFocusGained(evt);
				}
				public void focusLost(FocusEvent evt) {
					jtfFocusLost(evt);
				}
			});
			
			jtfGenero = new JTextField();
			jtfGenero.setBounds(100, 97, 275, 21);
			jtfGenero.setBackground(new Color(255,255,255,80));
			jtfGenero.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent evt) {
					jtfFocusGained(evt);
				}
				public void focusLost(FocusEvent evt) {
					jtfFocusLost(evt);
				}
			});
			
			jtaPrestamo = new JTextArea();
			jtaPrestamo.setBounds(100, 137, 275, 70);
			jtaPrestamo.setLineWrap(true);
			jtaPrestamo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
			jtaPrestamo.setBackground(new Color(255, 255, 255, 80));
			jtaPrestamo.addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent evt) {
					jtfFocusGained(evt);
				}
				public void focusLost(FocusEvent evt) {
					jtfFocusLost(evt);
				}
			});
			
			contenedor.add(jlbTitulo);
			contenedor.add(jtfTitulo);
			contenedor.add(jlbAutor);
			contenedor.add(jtfAutor);
			contenedor.add(jlbGenero);
			contenedor.add(jtfGenero);
			contenedor.add(jlbPrestamo);
			contenedor.add(jtaPrestamo);
			contenedor.add(jlbCreditos);
			
			
			getContentPane().add(contenedor);			
			((JPanel)getContentPane()).setOpaque(false);
			
			//Anadimos la imagen de fondo al panel
			final ImageIcon m_image = new ImageIcon(getClass().getResource("cata.jpg"));
	        JLabel backlabel = new JLabel() {
	        	
                private static final long serialVersionUID = 1L;

				public void paintComponent(Graphics g) {
	        		int w = 400;
	        		int h = 300;
	        		for(int i = 0; i < h; i+=h) {
	        			for (int j=0;j<w;j+=w)
                        {
                            m_image.paintIcon(this,g,j,i);
                        }
	        		}
	        	}
	        };
	         
	         
	        getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));
	        backlabel.setBounds(0,0,5000,5000);
	        //imagen anadida
	        
			pack();
			setSize(400, 300);			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void jmItemAnadirRegActionPerformed(ActionEvent evt) {	   
		String titulo = null;
		String autor = null;
		String genero = null;
		String descripcion = null;		
		
		titulo = jtfTitulo.getText().toString();
		
		if(titulo.compareTo("") == 0) {
			JOptionPane.showMessageDialog(null, "El campo titulo no puede estar vacio");
			return;
		}
				
		autor = jtfAutor.getText().toString();
		genero = jtfGenero.getText().toString();
		descripcion = jtaPrestamo.getText().toString();
		CDBean dummy = new CDBean(titulo, autor, genero, descripcion);
		//Comprovamos que el autor no sea uno modificado
		
		if(CDs.contains(dummy)) {
			modificarCD(dummy, false);
		} else {
			CDs.add(dummy);
		}
		Collections.sort(CDs);
		if (dlgBuscar != null && dlgBuscar.isVisible()) {
			dlgBuscar.actualizarLista();
		}
		jtfAutor.setText("");
		jtfTitulo.setText("");
		jtfGenero.setText("");
		jtaPrestamo.setText("");
	}

	public void modificarCD(CDBean dummy, Boolean anadirModificar) {		
		/*
		 * Se le envia el objeto CD para comprovar donde esta y modificarlo.
		 * Si se modifica el valor booleano toma true, si es una sobreescritura
		 * el valor booleano toma false.
		 */
		for(CDBean cd:CDs) {
			if(cd.equals(dummy)) {
				if(!anadirModificar) {
					String texto = "El titulo: "+cd.getTitulo()+ "ya existe, ¿Desea sobrescribirlo?";
					int respuesta = confirmarForm(texto,"Atención!");
					if (respuesta != JOptionPane.YES_OPTION) {
						break;
					}
				}
				CDs.remove(cd);
				CDs.add(dummy);
				break;
			}
		}
	}

	public Vector<CDBean> getCDs() {
		return CDs;
	}
	
	public void setRegDatos(int i) {
		CDBean cd = CDs.get(i);
		jtfTitulo.setText(cd.getTitulo());
		jtfAutor.setText(cd.getAutor());
		jtfGenero.setText(cd.getGenero());
		jtaPrestamo.setText(cd.getPrestamo());
	}
	
	/*
	 * Guardarem els CDs esborrats per si volem recuperar-los
	 */
	public Vector<CDBean> getCDsBorrados() {
		return CDsBorrados;
	}
	
	/*
	 * Quan guanyi focus quedara fons blanc i lletra negra, quan perdi focus quedara
	 * fons transparent i lletra blanca.
	 */
	protected void jtfFocusGained(FocusEvent evt) {
	    JTextComponent jtb = null;	    
	    jtb = (JTextComponent) evt.getSource();	    
	    jtb.setOpaque(true);
		jtb.setForeground(Color.black);
		jtb.setBackground(Color.white);
	    jtb.selectAll();	    
	}
	
	protected void jtfFocusLost(FocusEvent evt) {
		JTextComponent jtb = null;
		jtb = (JTextComponent) evt.getSource();
		jtb.setOpaque(false);
	    jtb.setForeground(Color.white);
				
	}
	
	private int confirmarForm(String texto, String wname) {
		int respuesta = JOptionPane.showConfirmDialog(null, texto, wname, JOptionPane.YES_NO_OPTION);
	    return respuesta;
	}
	
	private void actualizarDeBBDDForm() {
		int respuesta = JOptionPane.showConfirmDialog(null, 
				"Toda información modificada se borrara, ¿Cargar datos de la BBDD?", "Atención",
				JOptionPane.YES_NO_OPTION);
		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}
		CDs = dao.cargarCDs();
		// Actualizamos la lista si la ventana esta abierta
		if (dlgBuscar != null && dlgBuscar.isVisible()) {
			dlgBuscar.actualizarLista();
		}
	}
	
	private void mostrarInformes(String file) {
		try {
	        Desktop.getDesktop().open(new File(InformeCDs.getPath()+file));
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

	private void salirForm() {
		int respuesta = JOptionPane.showConfirmDialog(null,
				"Esta acción cerrará la aplicación, ¿desea continuar?",
				"Atención",
				JOptionPane.YES_NO_OPTION);
		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}
		System.exit(0);
	}
	private void jmItemBuscarRegActionPerformed(ActionEvent evt) {
		dlgBuscar = new BuscarReg(this);
		dlgBuscar.setVisible(true);
	}
	private void jmItemCDsBorradosActionPerformed(ActionEvent evt) {
		dlgBorrados = new DatosBorrados(this);
		dlgBorrados.setVisible(true);		
	}
}