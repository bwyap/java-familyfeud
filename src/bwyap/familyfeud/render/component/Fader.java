package bwyap.familyfeud.render.component;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import bwyap.familyfeud.render.RenderableInterface;
import bwyap.familyfeud.render.RenderingPanel;

/**
 * A renderable container that fades a renderable component 
 * over another during a specified time period
 * @author bwyap
 *
 */
public class Fader implements RenderableInterface {

	private final float TRANSITION_TIME;
	private float counter = 0.0f;
	private RenderableInterface over;
	private RenderableInterface base;
	private boolean fading;
	private boolean resetAlpha = true;

	/**
	 * Create a new fader renderable
	 * @param transition period of transition
	 * @param over the overlay image
	 * @param base the base image
	 * @param resetAlpha whether the fader should reset the alpha value before rendering other components
	 */
	public Fader(float transition, RenderableInterface over, RenderableInterface base, boolean resetAlpha) {
		TRANSITION_TIME = transition;
		this.over = over;
		this.base = base;
		this.resetAlpha = resetAlpha;
		fading = true;
	}
	
	/**
	 * Create a new fader renderable. By default this resets the alpha before rendering other components.
	 * @param transition period of transition
	 * @param over the overlay image
	 * @param base the base image
	 */
	public Fader(float transition, RenderableInterface over, RenderableInterface base) {
		this(transition, over, base, true);
	}

	/**
	 * Check if this fader is fading
	 * @return
	 */
	public boolean isFading() {
		return fading;
	}
	
	/**
	 * Set whether this fader is fading
	 * @param fading
	 */
	public void setFading(boolean fading) {
		this.fading = fading;
	}
	
	/**
	 * Reset the counter to 0
	 */
	public void reset() {
		counter = 0;
	}
	
	/**
	 * Check whether the fader resets the alpha 
	 * before rendering other components.
	 * Default is true.
	 * @param resetAlpha
	 */
	public boolean isResetAlpha() {
		return resetAlpha;
	}
	
	/**
	 * Set whether the fader should reset the alpha 
	 * before rendering other components.
	 * Default is true.
	 * @param resetAlpha
	 */
	public void setResetAlpha(boolean resetAlpha) {
		this.resetAlpha = resetAlpha;
	}

	@Override
	public void update(float timeElapsed) {
		if (fading && counter < TRANSITION_TIME) counter += timeElapsed;
		else counter = TRANSITION_TIME;
	}
	
	@Override
	public void render(RenderingPanel panel, Graphics g) {
		if (counter < TRANSITION_TIME) {
			base.render(panel, g);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, counter / TRANSITION_TIME));
			over.render(panel, g2d);
			if (resetAlpha) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		}
		else over.render(panel, g);
	}

}
