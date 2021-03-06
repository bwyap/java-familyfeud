package bwyap.familyfeud;

import javax.swing.JOptionPane;

import bwyap.familyfeud.game.FamilyFeudGame;
import bwyap.familyfeud.gui.UIManager;
import bwyap.familyfeud.render.FFRenderingEngine;
import bwyap.familyfeud.render.RenderingPanel;
import bwyap.familyfeud.res.FamilyFeudResources;
import bwyap.familyfeud.testdriver.FamilyFeudTestDriver;
import bwyap.gridgame.res.ResourceLoader;
import bwyap.utility.logging.Logger;

/**
 * Controller class that manages the game and GUI components of Family Feud
 * @author bwyap
 *
 */
public class FamilyFeudController {
	
	private static final int FPS_RATE = 60;
	
	private ResourceLoader resourceLoader;
	private FamilyFeudGame game;
	private FamilyFeudGUI gui;
	private FFRenderingEngine engine;
	private Thread engineThread;
	
	/**
	 * Initialize the application
	 */
	public void init() {
		// Select resolution
		int widescreen = JOptionPane.showConfirmDialog(null, "Use widescreen resolution?", "Widescreen", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
		UIManager.setWidescreen(widescreen == JOptionPane.YES_OPTION);
		
		resourceLoader = new FamilyFeudResources();
		resourceLoader.init();
		resourceLoader.load();
		
		game = new FamilyFeudGame();		
		game.init();
		
		RenderingPanel renderPanel = new RenderingPanel();
		
		gui = new FamilyFeudGUI(renderPanel, game);
		gui.init();
		
		engine = new FFRenderingEngine(FPS_RATE, renderPanel, game);
		engineThread = new Thread(engine);

		if (FamilyFeudTestDriver.DEBUG_LOG_CONSOLE) Logger.info("Controller initialized.");
	}
	
	/**
	 * Start the application
	 */
	public void start() {
		if (FamilyFeudTestDriver.DEBUG_LOG_CONSOLE) Logger.log("Welcome to Family Feud!");
		gui.start();
		engineThread.start();
	}
	
	/**
	 * Get the current Family Feud game
	 * @return
	 */
	public FamilyFeudGame getGame() {
		return game;
	}
	
}
