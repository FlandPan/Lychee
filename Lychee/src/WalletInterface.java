import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class WalletInterface extends Interface {

	private static final int MIN_INPUT_LENGTH = 1;
	private static final int MAX_INPUT_LENGTH = 16;
	
	private int currentSelection;
	private final int NUM_OF_LAYERS = 4; // 1-amount, 2-card number, 3-confirm, 4-back to profile
	private boolean invalidInput;

	private String amount;
	private String card;
	private boolean decimalUsed;

	private Font titleFont;
	private Font textFont;

	public WalletInterface(InterfaceControl ic, Database data) {
		super(ic, data);

		titleFont = new Font("Arial", Font.BOLD, 80);
		textFont = new Font("Times New Roman", Font.BOLD, 30);

		initialize();
	}

	public void initialize() {
		currentSelection = 1;
		invalidInput = false;

		amount = "";
		card = "";
		decimalUsed = false;
	}

	public void update() {

	}

	public void draw(java.awt.Graphics2D g) {
		// bars and buttons
		g.setColor(Color.WHITE);
		g.fillRect(350, 300, 600, 50);
		g.fillRect(350, 400, 600, 50);
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(500, 550, 200, 50, 10, 10);
		g.fillRoundRect(500, 650, 200, 50, 10, 10);
		// thin borders
		g.setColor(Color.GRAY);
		g.drawRect(350, 300, 600, 50);
		g.drawRect(350, 400, 600, 50);
		g.drawRoundRect(500, 550, 200, 50, 10, 10);
		g.drawRoundRect(500, 650, 200, 50, 10, 10);

		// selection shade
		g.setColor(shade);
		switch (currentSelection) {
		case 1:
			g.fillRoundRect(340, 290, 620, 70, 25, 25);
			break;
		case 2:
			g.fillRoundRect(340, 390, 620, 70, 25, 25);
			break;
		case 3:
			g.fillRoundRect(490, 540, 220, 70, 25, 25);
			break;
		case 4:
			g.fillRoundRect(490, 640, 220, 70, 25, 25);
			break;
		}

		// base text
		g.setFont(textFont);
		g.setColor(Color.WHITE);
		g.drawString("Enter amount : $", 130, 335);
		g.drawString("Card number :", 150, 435);
		g.drawString("Authorize", 540, 584);
		g.drawString("Back", 570, 684);

		// invalid input notification
		if (invalidInput) {
			g.setColor(Color.RED);
			g.drawString("* Unsuccessful recharge! *", 440, 500);
		}

		// input display
		g.setColor(Color.BLACK);
		g.drawString(amount, 365, 335);
		g.drawString(card, 365, 435);

	}

	public void keyTyped(char c) {
		// number input
		// restrict input length
		if (currentSelection == 1 && amount.length() < MAX_INPUT_LENGTH) {
			if(c>='0' && c<='9')
				amount += c;
			else if(c=='.' && !decimalUsed) {
				amount += c;
				decimalUsed = true;
			}
		}
		else if (currentSelection == 2 && card.length() < MAX_INPUT_LENGTH) {
			if(c>='0' && c<='9')
				card += c;
		}
	}

	public void keyPressed(int k) {
		// dicide on different commands based on the entered keys
				switch (k) {

				case KeyEvent.VK_ENTER:
					if (currentSelection == 1 || currentSelection == 2)
						currentSelection++;
					else if (currentSelection == 3)
						charge();
					else if (currentSelection == 4)
						ic.setInterface(InterfaceControl.PROFILE);
					break;

				case KeyEvent.VK_UP:
					if (currentSelection > 1)
						currentSelection--;
					break;

				case KeyEvent.VK_DOWN:
					if (currentSelection < NUM_OF_LAYERS)
						currentSelection++;
					break;

				case KeyEvent.VK_BACK_SPACE:
					if (currentSelection == 1 && amount.length() > 0) {
						if(amount.charAt(amount.length()-1)=='.') decimalUsed = false;
						amount = amount.substring(0, amount.length() - 1);
					} else if (currentSelection == 2 && card.length() > 0) {
						card = card.substring(0, card.length() - 1);
						
					}
					break;

				}
	}

	public void keyReleased(int k) {

	}
	
	private void charge() {
		if(amount.equals("."))
			amount = "0";
		
		if(card.length()==MAX_INPUT_LENGTH && amount.length()>=MIN_INPUT_LENGTH) {
			if(Database.getCurrentUser().getBag().fill(Double.parseDouble(amount), Long.parseLong(card)))
				ic.setInterface(InterfaceControl.PROFILE);
		}
		
		amount = "";
		card = "";
		invalidInput = true;
	}

}
