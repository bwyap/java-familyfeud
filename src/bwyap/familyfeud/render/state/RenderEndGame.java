package bwyap.familyfeud.render.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import bwyap.familyfeud.game.Family;
import bwyap.familyfeud.game.FamilyFeudGame;
import bwyap.familyfeud.render.AbstractRenderState;
import bwyap.familyfeud.render.RenderingPanel;
import bwyap.familyfeud.render.component.Fader;
import bwyap.familyfeud.render.component.RenderableImage;
import bwyap.familyfeud.render.component.RenderableString;
import bwyap.gridgame.res.ResourceLoader;

/**
 * Renders the new game state:
 * each family's name will be rendered on the screen as they are added to the game.
 * @author bwyap
 *
 */
public class RenderEndGame extends AbstractRenderState {
	
	private FamilyFeudGame game;
	private Family winner;
	
	private Fader bg;
	private RenderableString congrats;
	private RenderableString family;
	private RenderableString description;
	
	/**
	 * Create a new game render state
	 * @param game
	 */
	public RenderEndGame(FamilyFeudGame game) {
		this.game = game;
		
		this.bg = new Fader(1000, new RenderableImage(ResourceLoader.getImage("blur")), new RenderableImage(ResourceLoader.getImage("bg")));
		this.congrats = new RenderableString("Congratulations!", 0, 150, ResourceLoader.getFontName("Bebas Neue"), Font.PLAIN, 100, Color.ORANGE, true);
	}
	
	@Override
	public void reset() {
		bg.reset();
		family = null;
	}
	
	@Override
	public void update(float timeElapsed) {
		winner = game.getWinningFamily();
		if (family == null) {
			family = new RenderableString("", 0, 360, ResourceLoader.getFontName("Bebas Neue"), Font.BOLD, 180, Color.BLACK, true);
			description = new RenderableString("", 0, 430, ResourceLoader.getFontName("Bebas Neue"), Font.PLAIN, 60, Color.WHITE, true);			
		}
		family.setText(winner.getName());
		description.setText("won with " + winner.getPoints() + " points");
		bg.update(timeElapsed);
	}
	
	@Override
	public void render(RenderingPanel panel, Graphics g) {
		bg.render(panel, g);
		if (bg.finished()) {
			congrats.render(panel, g);
			family.render(panel, g);
			description.render(panel, g);			
		}
	}

}
