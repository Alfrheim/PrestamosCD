package transparencias;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JScrollPane;

public class ScrollPaneTransparente extends JScrollPane {
	private static final long serialVersionUID = 1L;
    
    private float trans;

	public ScrollPaneTransparente() {
        this(0.5f);
    }
    public ScrollPaneTransparente(float trans) {
        super();
        this.trans = trans;
    }  
    

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
        super.paint(g2);
        g2.dispose();
    }
}
