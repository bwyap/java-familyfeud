package bwyap.familyfeud.gui.window.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * A panel used to render game graphics using Java's Graphics API.
 * @author bwyap
 *
 */
public class RenderingPanel extends JPanel {

	private static final long serialVersionUID = 8000180076902777265L;
	
	private boolean renderFPS = true;
	private float lastFPS;
	
	/**
	 * Render graphics
	 */
	public void render() {
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		setAntiAlias((Graphics2D)g);
		
		// TESTING
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		
		// Draw fps to the screen
		if (renderFPS) {
			g.setColor(Color.YELLOW);
			g.setFont(new Font("SansSerif", Font.PLAIN, 12));
			g.drawString((int) lastFPS + " fps", 2, 12);			
		}
	}

	/**
	 * Set anti-aliasing for rendering
	 * @param g
	 */
	private void setAntiAlias(Graphics2D g) {
		g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	/**
	 * Set whether the FPS is rendered to the screen
	 * @param show
	 */
	public void showFPS(boolean show) {
		renderFPS = show;
	}
	
	/**
	 * Set the value of the last measured fps to render to the screen
	 * @param fps
	 */
	public void updateFPS(float fps) {
		lastFPS = fps;
	}
	
}
