package bwyap.familyfeud.gui.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import bwyap.familyfeud.game.Family;
import bwyap.familyfeud.game.FamilyFeudGame;
import bwyap.familyfeud.game.state.StatePlay;
import bwyap.familyfeud.gui.GBC;
import bwyap.gridgame.res.ResourceLoader;

/**
 * A panel used to select a family for an action
 * @author bwyap
 * 
 */
public class FamilyPanel extends JPanel {
	
	private static final long serialVersionUID = -5811924958007378691L;

	public static final int WIDTH = WindowControlPanel.WIDTH;
	public static final int HEIGHT = StatePlayPanel.HEIGHT;
	
	private JLabel title;
	private JScrollPane listScroll;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton select;
	
	private int command = -1;
	
	private FamilyFeudGame game;
	
	public FamilyPanel(FamilyFeudGame game) {
		this.game = game;
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		initComponents();
	}
	
	public void initComponents() {
		title = new JLabel("FAMILIES");
		title.setFont(new Font(ResourceLoader.DEFAULT_FONT_NAME, Font.BOLD, 14));

		tableModel = new DefaultTableModel(new Object[]{"Family", "Points"}, 0);
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listScroll = new JScrollPane(table);
		listScroll.setMinimumSize(new Dimension(WIDTH - 20, (int)(HEIGHT*0.65)));
		
		select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() > -1 && command > -1) {
					game.getState().executeAction(StatePlay.ACTION_EXECUTEPLAYACTION, new Object[]{
							command, table.getSelectedRow()});
				}
			}
		});
		
		add(title, new GBC(0, 0));
		add(listScroll, new GBC(0, 1));
		add(select, new GBC(0, 2));
	}
	
	/**
	 * Load families into panel list
	 */
	public void loadFamilies() {
		int selected = table.getSelectedRow();
		reset();
		for(Family f : game.getFamilies()) {
			tableModel.addRow(new Object[]{f.getName(), f.getPoints()});
		}
		if (selected > -1) table.setRowSelectionInterval(selected, selected);
	}
	
	/**
	 * Set the command to execute when the select button is pressed
	 * @param command
	 */
	public void setCommand(int command) {
		this.command = command;
	}
	
	/**
	 * Reset the families for a new game
	 */
	public void reset() {
		for(int i = tableModel.getRowCount(); i > 0; i--) {
			tableModel.removeRow(i - 1);
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		select.setEnabled(enabled);
	}
	
}