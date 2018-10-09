import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class Register extends Interface {

	private static final int MIN_INPUT_LENGTH = 1;
	private static final int MAX_INPUT_LENGTH = 30;

	private String inputUsername;
	private String inputPassword;
	private String hiddenPassword;
	private String inputEmail;
	private int currentEnter; // 1-username, 2-password, 3-email, 4-register, 5-back to login
	private int registerStatus; // -1-unsuccessful, 0-normal, >0-registered user id

	private Font titleFont;
	private Font textFont;

	public Register(InterfaceControl ic, Database data) {
		super(ic, data);

		titleFont = new Font("Arial", Font.PLAIN, 60);
		textFont = new Font("Times New Roman", Font.BOLD, 30);

		initialize();
	}

	public void initialize() {
		inputUsername = "";
		inputPassword = "";
		hiddenPassword = "";
		inputEmail = "";
		currentEnter = 1;
		registerStatus = 0;
	}

	public void update() {
		if (registerStatus > 0)
			currentEnter = 5;
	}

	public void draw(java.awt.Graphics2D g) {
		g.setColor(Color.WHITE);
		// title
		g.setFont(titleFont);
		g.drawString("Create an account", 150, 100);

		// bars and buttons
		if (registerStatus <= 0) { // registering
			g.fillRect(350, 200, 600, 50);
			g.fillRect(350, 300, 600, 50);
			g.fillRect(350, 400, 600, 50);
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(500, 550, 200, 50, 10, 10);
			// thin borders
			g.setColor(Color.GRAY);
			g.drawRect(350, 200, 600, 50);
			g.drawRect(350, 300, 600, 50);
			g.drawRect(350, 400, 600, 50);
			g.drawRoundRect(500, 550, 200, 50, 10, 10);
		} else { // finished registering
		}
		// back to login
		g.setColor(Color.DARK_GRAY);
		g.fillRoundRect(500, 650, 200, 50, 10, 10);
		g.setColor(Color.GRAY);
		g.drawRoundRect(500, 650, 200, 50, 10, 10);

		// selection shade
		g.setColor(shade);
		switch (currentEnter) {
		case 1:
			g.fillRoundRect(340, 190, 620, 70, 25, 25);
			break;
		case 2:
			g.fillRoundRect(340, 290, 620, 70, 25, 25);
			break;
		case 3:
			g.fillRoundRect(340, 390, 620, 70, 25, 25);
			break;
		case 4:
			g.fillRoundRect(490, 540, 220, 70, 25, 25);
			break;
		case 5:
			g.fillRoundRect(490, 640, 220, 70, 25, 25);
			break;
		}

		// base text
		g.setFont(textFont);
		g.setColor(Color.WHITE);
		if (registerStatus <= 0) {
			g.drawString("Username :", 186, 235);
			g.drawString("Password :", 190, 335);
			g.drawString("E-mail :", 228, 435);
			g.drawString("Register", 547, 584);
		} else {
			g.drawString("Successful registration! Your user ID is ", 200, 400);
			g.setColor(Color.LIGHT_GRAY);
			g.drawString("" + registerStatus, 707, 400);
			g.setColor(Color.WHITE);
		}
		g.drawString("Back to login", 520, 684);

		// invalid input notification
		if (registerStatus == -1) {
			g.setColor(Color.RED);
			g.drawString("* Unsuccessful registration *", 420, 500);
		}

		// ID and password
		if (registerStatus <= 0) {
			g.setColor(Color.BLACK);
			g.drawString(inputUsername, 365, 235);
			g.drawString(hiddenPassword, 365, 335);
			g.drawString(inputEmail, 365, 435);
		}
	}

	public void keyTyped(char c) {
		// normal character input
		// restrict maximum input length
		if (currentEnter == 1 && inputUsername.length() < MAX_INPUT_LENGTH)
			inputUsername += c;
		else if (currentEnter == 2 && inputPassword.length() < MAX_INPUT_LENGTH) {
			inputPassword += c;
			hiddenPassword += '\u25cf';
		} else if (currentEnter == 3 && inputEmail.length() < MAX_INPUT_LENGTH) {
			inputEmail += c;
		}
	}

	public void keyPressed(int k) {
		// dicide on different commands based on the entered keys
		switch (k) {

		case KeyEvent.VK_ENTER:
			if (currentEnter == 1 || currentEnter == 2 || currentEnter == 3)
				currentEnter++;
			else if (currentEnter == 4)
				register();
			else if (currentEnter == 5)
				ic.setInterface(InterfaceControl.LOGIN);
			break;
		case KeyEvent.VK_UP:
			if (currentEnter > 1)
				currentEnter--;
			break;

		case KeyEvent.VK_DOWN:
			if (currentEnter < 5)
				currentEnter++;
			break;

		case KeyEvent.VK_BACK_SPACE:
			if (currentEnter == 1 && inputUsername.length() > 0)
				inputUsername = inputUsername.substring(0, inputUsername.length() - 1);
			else if (currentEnter == 2 && inputPassword.length() > 0) {
				inputPassword = inputPassword.substring(0, inputPassword.length() - 1);
				hiddenPassword = hiddenPassword.substring(0, hiddenPassword.length() - 1);
			} else if (currentEnter == 3 && inputEmail.length() > 0)
				inputEmail = inputEmail.substring(0, inputEmail.length() - 1);
			break;

		}
	}

	public void keyReleased(int k) {

	}

	private void register() {
		// check for minimum length requirement
		if (inputUsername.length() >= MIN_INPUT_LENGTH && inputPassword.length() >= MIN_INPUT_LENGTH
				&& inputEmail.length() >= MIN_INPUT_LENGTH) {
			registerStatus = data.getUserManagement().registerUser(inputUsername, inputPassword, inputEmail);
			if (registerStatus == -1) {
				initialize();
				registerStatus = -1;
			}
		} else {
			initialize();
			registerStatus = -1;
		}
	}
}
