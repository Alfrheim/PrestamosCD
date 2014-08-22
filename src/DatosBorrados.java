import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import datos.CDBean;

import transparencias.*;


public class DatosBorrados extends JDialog {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
	//TODO Acer que solo haya una ventana activa
    private static int activa = 0;
    CD ventanaPadre;
    
	private JButton jbtAceptar, jbtCancelar;
	private int iconaX = 25 , iconaY = 25;
	private JScrollPane jScrollPane1;
	private JList jlsListaCDs;

	
	public DatosBorrados(JFrame frame) {
		super(frame, "CDs Borrados");
		initGUI();
		activa++;
		ventanaPadre = (CD)frame;
		jlsListaCDs.setListData(ventanaPadre.getCDsBorrados());
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
			jbtAceptar.setBounds(48, 225, 125, 40);
			jbtAceptar.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent evt) {
					jbtAceptarActionPerformed(evt);					
				}
			});
			
			jbtCancelar = new BotonTransparente("Cancelar", escalarIcona("cancelar.png", iconaX, iconaY), 0.8f);
			jbtCancelar.setMnemonic(KeyEvent.VK_C);
			//jbtCancelar.setBounds(285, 58, 100, 26);
			jbtCancelar.setBounds(221, 225, 125, 40);
			jbtCancelar.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent evt) {
					jbtCancelarActionPreformed(evt);			
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
		//TODO afegir CDsBorrado id a CDs
		int i = jlsListaCDs.getSelectedIndex();
		if(i>=0) {
			CDBean dummy = ventanaPadre.getCDsBorrados().get(i);
			if(ventanaPadre.getCDs().contains(dummy)) {
				ventanaPadre.modificarCD(ventanaPadre.getCDsBorrados().get(i), false);

			}else {
				ventanaPadre.getCDs().add(dummy);
			}
			//ventanaPadre.getCDs().add(ventanaPadre.getCDsBorrados().get(i));
			ventanaPadre.getCDsBorrados().remove(i);
			this.actualizarLista();
			Collections.sort(ventanaPadre.getCDs());
			if (ventanaPadre.dlgBuscar != null && ventanaPadre.dlgBuscar.isVisible()) {
				ventanaPadre.dlgBuscar.actualizarLista();
			}
		}
	}
	
	private void jbtCancelarActionPreformed(ActionEvent evt) {
		this.setVisible(false);
		this.dispose();
	}
	
    
	public void actualizarLista() {
		jlsListaCDs.setListData(ventanaPadre.getCDsBorrados());
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
