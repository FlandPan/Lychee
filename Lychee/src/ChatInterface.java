import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class ChatInterface extends Interface {

	private static final int MIN_INPUT_LENGTH = 1;
	private static final int MAX_INPUT_LENGTH = 55;

	private int currentSelection;
	private final int MAX_SELECTIONS = 2;

	private String message;
	private Chat chat;

	private Font titleFont;
	private Font textFont;

	public ChatInterface(InterfaceControl ic, Database data) {
		super(ic, data);

		titleFont = new Font("Arial", Font.PLAIN, 30);
		textFont = new Font("Times New Roman", Font.PLAIN, 20);

		initialize();
	}

	public void initialize() {
		currentSelection = 1;
		message = "";
		chat = data.getCurrentChat();
	}

	public void update() {
		chat.removeExtra();
	}

	public void draw(java.awt.Graphics2D g) {

		// chat window
		g.setColor(Color.WHITE);
		g.fillRect(300, 50, 800, 701);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(300, 50, 800, 701);
		// bottom divider
		g.setColor(Color.GRAY);
		g.drawRect(300, 716, 800, 35);

		// return button
		g.setColor(Color.WHITE);
		g.fillRoundRect(30, 50, 200, 50, 20, 20);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRoundRect(30, 50, 200, 50, 20, 20);

		// shade
		g.setColor(shade);
		if (currentSelection == 1) {
			g.fillRoundRect(25, 45, 210, 60, 10, 10);
		} else if (currentSelection == 2) {
			g.fillRoundRect(300, 715, 800, 35, 10, 10);
		}

		// texts
		// button
		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Back to Profile", 36, 85);

		// chat message
		g.setFont(textFont);
		int count = 0;
		for (Chat.Message m : chat.getLog()) {
			g.setColor(Color.GRAY);
			g.drawString(m.getTextPerson() + " - " + m.getTime(), 320, 75 + count * 60);
			g.setColor(Color.DARK_GRAY);
			g.drawString(m.getLog(), 310, 100 + count * 60);
			count++;
		}

		// input text
		g.setColor(Color.BLACK);
		g.drawString(message, 310, 740);

	}

	public void keyTyped(char c) {
		// normal character input
		// restrict maximum input length
		if (currentSelection == 2 && message.length() < MAX_INPUT_LENGTH)
			message += c;
	}

	public void keyPressed(int k) {
		// dicide on different commands based on the entered keys
		switch (k) {

		case KeyEvent.VK_ENTER:
			if (currentSelection == 1)
				ic.setInterface(InterfaceControl.PROFILE);
			else if (currentSelection == 2 && message.length() >= MIN_INPUT_LENGTH) {
				Database.getCurrentUser().addTextToChat(chat, message);
				message = "";
			}
			break;

		case KeyEvent.VK_UP:
			if (currentSelection > 1)
				currentSelection--;
			break;

		case KeyEvent.VK_DOWN:
			if (currentSelection < MAX_SELECTIONS)
				currentSelection++;
			break;

		case KeyEvent.VK_BACK_SPACE:
			if (currentSelection == 2 && message.length() > 0)
				message = message.substring(0, message.length() - 1);
			break;

		}
	}

	public void keyReleased(int k) {

	}

}
