package bwyap.familyfeud.gui.window;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bwyap.familyfeud.gui.GBC;
import bwyap.familyfeud.gui.control.ConsolePanel;
import bwyap.familyfeud.gui.control.WindowControlPanel;
import bwyap.utility.logging.Logger;

/**
 * This is a control window with controls to 
 * run the Family Feud game from a separate window
 * @author bwyap
 *
 */
public class ControlWindow extends FamilyFeudWindow {
	
	private static final long serialVersionUID = -4445104890877967661L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private GameWindow gameWindow;
	private JPanel contentPane;
	private ConsolePanel consolePanel;
	private WindowControlPanel windowControlPanel;
	
	/**
	 * Create a new control window
	 * @param title
	 */
	public ControlWindow(String title, GameWindow gameWindow) {
		super(title, WIDTH, HEIGHT);
		this.gameWindow = gameWindow;
	}

	/**
	 * Initialize window components
	 */
	public void initWindow() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		setLayout(new BorderLayout());
		
		// init components
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		
		consolePanel = new ConsolePanel();
		contentPane.add(consolePanel, new GBC(0, 1));
		
		windowControlPanel = new WindowControlPanel(gameWindow);
		contentPane.add(windowControlPanel, new GBC(0, 0));
		
		add(contentPane, BorderLayout.CENTER);
		Logger.info("Control window initialized.");
	}

}
