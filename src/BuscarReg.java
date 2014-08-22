
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import transparencias.*;


public class BuscarReg extends JDialog {
	
    private static final long serialVersionUID = 1L;
    //TODO Acer que solo haya una ventana activa
    private static int activa = 0;
    
    

	CD ventanaPadre;
    
	private JButton jbtAceptar, jbtCancelar, jbtBorrar;
	private int iconaX = 20 , iconaY = 25;
	private JScrollPane jScrollPane1;
	private JList jlsListaCDs;

	
	public BuscarReg(JFrame frame) {
		super(frame, "Buscar Registro");
		initGUI();
		activa++;
		ventanaPadre = (CD)frame;
		jlsListaCDs.setListData(ventanaPadre.getCDs());
		
	}

	private void initGUI() {
		try {		
			getContentPane().setLayout(null);
			this.setResizable(false);
			JPanel contenedor = new JPanel();
			contenedor.setLayout(null);
			contenedor.setOpaque(false);
			
			jbtAceptar = new BotonTransparente( "Aceptar", escalarIcona("aceptar.png", iconaX, iconaY), 0.8f);
			jbtAceptar.setMnemonic(KeyEvent.VK_A);
			//jbtAceptar.setBounds(285, 21, 120, 40);
			jbtAceptar.setBounds(10, 225, 120, 40);
			jbtAceptar.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent evt) {
					jbtAceptarActionPerformed(evt);					
				}
			});
			
			jbtCancelar = new BotonTransparente("Cancelar", escalarIcona("cancelar.png", iconaX, iconaY), 0.8f);
			jbtCancelar.setMnemonic(KeyEvent.VK_C);
			//jbtCancelar.setBounds(285, 58, 100, 26);
			jbtCancelar.setBounds(135, 225, 120, 40);
			jbtCancelar.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent evt) {
					jbtCancelarActionPreformed(evt);			
				}
			});
			
			jbtBorrar = new BotonTransparente("Borrar",escalarIcona("borrar.png", iconaX, iconaY), 0.8f);
			jbtBorrar.setMnemonic(KeyEvent.VK_B);
			//jbtBorrar.setBounds(285, 95, 100, 26);			
			jbtBorrar.setBounds(260, 225, 120, 40);
			jbtBorrar.addActionListener(new ActionListener() {
				@Override
                public void actionPerformed(ActionEvent evt) {
	                jbtBorrarActionPerformed(evt);	                
                }				
			});
			
			
			getRootPane().setDefaultButton(jbtAceptar);
			
			jScrollPane1 = new JScrollPane();
			//jScrollPane1.setBounds(17, 21, 247, 220);
			jScrollPane1.setBounds(17, 21, 363, 200);
			
			jlsListaCDs = new JList();			
			jScrollPane1.setViewportView(jlsListaCDs);
			
			add(jScrollPane1);			
			add(jbtAceptar);
			add(jbtCancelar);
			add(jbtBorrar);
						
			getContentPane().add(contenedor);			
			((JPanel)getContentPane()).setOpaque(false);
			
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
	        
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ImageIcon escalarIcona(String ruta, int x, int y) {
		ImageIcon icona;
		ImageIcon iconaTemp = new ImageIcon(getClass().getResource(ruta));
		Image iconaImg = iconaTemp.getImage();
		iconaImg = iconaImg.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		icona = new ImageIcon(iconaImg);
		return icona;
	}
	
	private void jbtAceptarActionPerformed(ActionEvent evt) {
		int i = jlsListaCDs.getSelectedIndex();
		if(i>=0) {
			ventanaPadre.setRegDatos(i);
		}
	}
	
	private void jbtCancelarActionPreformed(ActionEvent evt) {
		this.setVisible(false);
		this.dispose();
	}
	
	private void jbtBorrarActionPerformed(ActionEvent evt) {
		int i = jlsListaCDs.getSelectedIndex();
		if (i>=0) {
			ventanaPadre.getCDsBorrados().add(ventanaPadre.getCDs().get(i));
			ventanaPadre.getCDs().remove(i);
			this.actualizarLista();
			if (ventanaPadre.dlgBorrados != null && ventanaPadre.dlgBorrados.isVisible()) {
				ventanaPadre.dlgBorrados.actualizarLista();
			}
		}
	}
    
	public void actualizarLista() {
		jlsListaCDs.setListData(ventanaPadre.getCDs());
	}
	public int getIconaX() {
    	return iconaX;
    }

	public int getIconaY() {
    	return iconaY;
    }

	public void setIconaX(int iconaX) {
    	this.iconaX = iconaX;
    }

	public void setIconaY(int iconaY) {
    	this.iconaY = iconaY;
    }
}