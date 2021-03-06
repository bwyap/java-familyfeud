package bwyap.familyfeud.game.play.state;

import bwyap.familyfeud.game.InvalidDataException;
import bwyap.familyfeud.game.QuestionSet;
import bwyap.utility.logging.Logger;

/**
 * This state is used to select a question before the two families face off
 * @author bwyap
 *
 */
public class StateSelectQuestion extends FFPlayState {
	
	public static final int ACTION_SELECTQUESTION = 0x0;
	
	private QuestionSet questions;
	private int selectedIndex;
	
	protected StateSelectQuestion(QuestionSet questions) {
		super(FFPlayStateType.SELECT_QUESTION);
		this.questions = questions;
	}

	@Override
	public void initState(Object data) {
		selectedIndex = -1;
	}

	@Override
	public void cleanupState() {
		data = selectedIndex;
	}
	
	@Override
	public boolean executeAction(int action, Object[] data) {
		switch(action) {
		case ACTION_SELECTQUESTION:
			if (data[1] instanceof Integer && data[2] instanceof Integer) {
				return selectQuestion((Integer) data[1], (Integer) data[2]);
			}
			else throw new InvalidDataException("Expecting a {*, Integer, Integer} when using action ACTION_SELECTQUESTION");
		default: 
			throw new RuntimeException("Invalid action: " + action);
		}
	}
	
	/**
	 * Set the index for the selected question if it is within bounds.
	 * @param index
	 * @return
	 */
	private boolean selectQuestion(int index, int multiplier) {
		if (index < questions.size()) {
			if (questions.getQuestion(index).isAnswered()) {
				Logger.err("Question already answered! ");
			}
			else {
				selectedIndex = index;
				questions.setSelectedQuestion(selectedIndex);
				questions.getSelectedQuestion().setMultiplier(multiplier);
				Logger.log("Question [" + selectedIndex +  "] selected with multiplier of " + multiplier + "x.");
				return true;				
			}
		}
		return false;
	}
	
	@Override
	public boolean canAdvance(String nextState) {
		return selectedIndex > -1;
	}

}
