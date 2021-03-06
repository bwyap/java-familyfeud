package bwyap.familyfeud.game.play.state;

import bwyap.familyfeud.game.InvalidDataException;
import bwyap.familyfeud.game.Question;
import bwyap.utility.logging.Logger;

/**
 * This state represents the opportunity an opposing team has to 
 * steal the points from the board if they can guess an answer on 
 * the board.
 * @author bwyap
 *
 */
public class StateFamilySteal extends FFPlayState implements StrikeInterface {

	public static final int ACTION_OPENANSWER = 0x2;
	public static final int ACTION_STRIKE = 0x3;
	public static final int ACTION_SELECTWINFAMILY = 0x5;
	
	private Question question;
	private int selectedWinFamilyIndex;
	private int selectedStealFamilyIndex;
	private int strikes;

	protected StateFamilySteal() {
		super(FFPlayStateType.FAMILY_STEAL);
	}

	@Override
	public void initState(Object data) {		
		try {
			Object[] d = (Object[]) data;
			question = (Question)d[0];
			selectedStealFamilyIndex = (Integer)d[1];
		}
		catch (Exception e) {
			throw new InvalidDataException("StateFamilySteal: Question and Integer object expected from intialization data");
		}
		selectedWinFamilyIndex = -1;		
		strikes = 0;
	}

	@Override
	public void cleanupState() {
		// Pass the family selected to receive the points
		data = new Object[]{question, selectedWinFamilyIndex};
		strikes = 0;
	}

	@Override
	public boolean executeAction(int action, Object[] data) {
		switch(action) {
		case ACTION_OPENANSWER:
			// Reveal an answer
			if (data[1] instanceof Integer) {
				openAnswer((Integer) data[1]);
				return true;
			}
			else throw new InvalidDataException("Expecting a {*, Integer} when using action ACTION_OPENANSWER");
		case ACTION_STRIKE:
			strike();
			return true;
		case ACTION_SELECTWINFAMILY:
			if (data[1] instanceof Integer) {
				selectWinFamily((Integer) data[1]);
				return true;
			}
			else throw new InvalidDataException("Expecting a {*, Integer} when using action ACTION_SELECTWINFAMILY");
		default: 
			throw new RuntimeException("Invalid action: " + action);
		}
	}

	@Override
	public boolean canAdvance(String nextState) {
		return selectedWinFamilyIndex > -1;
	}
	
	/**
	 * Give the selected family a strike
	 */
	private void strike() {
		if (selectedStealFamilyIndex < 0) {
			Logger.err("No family selected as potential stealers");
		}
		else if (strikes > 0) {
			Logger.err("Only one strike permitted in a steal phase. Please selected a winning team.");
		}
		else {
			strikes++;
			Logger.log("Family [" + selectedStealFamilyIndex + "] now has " + strikes + " strike(s).");			
		}
	}
	
	@Override
	public int getStrikes() {
		return strikes;
	}
	
	/**
	 * Reveal and answer
	 * @param index
	 */
	private void openAnswer(int index) {
		if (selectedStealFamilyIndex > -1) {
			if (!question.getAnswers().get(index).isRevealed()) {
				question.getAnswers().get(index).setReveal(true);
				Logger.log("Revealed answer: " + question.getAnswers().get(index));
			}
			else Logger.log("Answer already revealed!");
		}
		else Logger.err("No family selected as potential stealers");
	}
	
	/**
	 * Set the family selected to win the points
	 * @param index
	 */
	private void selectWinFamily(int index) {
		selectedWinFamilyIndex = index;
		Logger.log("Family [" + selectedWinFamilyIndex + "] selected to receive points.");
	}
	
}
