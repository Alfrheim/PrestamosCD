package transparencias;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author randolph
 */
public class BotonTransparente extends JButton {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private float trans;

	public BotonTransparente(String text) {
        this(text, null);
    }
    public BotonTransparente(String text, ImageIcon icon) {
        this(text, icon, 0.5f);
    }
    public BotonTransparente(String text, ImageIcon icon, float trans) {
    	super(text, icon);
    	this.trans = trans;
    	setOpaque(false);
    }
    

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        super.paint(g2);
        g2.dispose();
    }

}
