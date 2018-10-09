import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Login extends Interface {

	private static final int MIN_INPUT_LENGTH = 1;
	private static final int MAX_INPUT_LENGTH = 30;

	private String inputID;
	private String inputPassword;
	private String hiddenPassword;
	private int currentEnter; // 1-username, 2-password, 3-confirm, 4-register
	private boolean invalidInput;

	private Image logo;
	private final String LOGO_FILE = "Lychee.png";

	private Font titleFont;
	private Font textFont;

	public Login(InterfaceControl ic, Database data) {
		super(ic, data);

		titleFont = new Font("Arial", Font.BOLD, 80);
		textFont = new Font("Times New Roman", Font.BOLD, 30);
		readImage();

		initialize();
	}

	public void initialize() {
		inputID = "";
		inputPassword = "";
		hiddenPassword = "";
		currentEnter = 1;
		invalidInput = false;
	}

	public void update() {

	}

	public void draw(java.awt.Graphics2D g) {
		// logo
		if (logo != null)
			g.drawImage(logo, 220, 90, null);

		g.setColor(Color.WHITE);
		// title
		g.setFont(titleFont);
		g.drawString("LYCHEE", 430, 200);

		// bars and buttons
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
		switch (currentEnter) {
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
		g.drawString("ID / E-mail :", 170, 335);
		g.drawString("Password :", 190, 435);
		g.drawString("Login", 560, 584);
		g.drawString("Register", 545, 684);

		// invalid input notification
		if (invalidInput) {
			g.setColor(Color.RED);
			g.drawString("* Incorrect ID or password *", 420, 500);
		}

		// ID and password
		g.setColor(Color.BLACK);
		g.drawString(inputID, 365, 335);
		g.drawString(hiddenPassword, 365, 435);
	}

	public void keyTyped(char c) {
		// normal character input
		// restrict maximum input length
		if (currentEnter == 1 && inputID.length() < MAX_INPUT_LENGTH)
			inputID += c;
		else if (currentEnter == 2 && inputPassword.length() < MAX_INPUT_LENGTH) {
			inputPassword += c;
			hiddenPassword += '\u25cf';
		}
	}

	public void keyPressed(int k) {
		// dicide on different commands based on the entered keys
		switch (k) {

		case KeyEvent.VK_ENTER:
			if (currentEnter == 1 || currentEnter == 2)
				currentEnter++;
			else if (currentEnter == 3)
				login();
			else if (currentEnter == 4)
				ic.setInterface(InterfaceControl.REGISTER);
			break;

		case KeyEvent.VK_UP:
			if (currentEnter > 1)
				currentEnter--;
			break;

		case KeyEvent.VK_DOWN:
			if (currentEnter < 4)
				currentEnter++;
			break;

		case KeyEvent.VK_BACK_SPACE:
			if (currentEnter == 1 && inputID.length() > 0)
				inputID = inputID.substring(0, inputID.length() - 1);
			else if (currentEnter == 2 && inputPassword.length() > 0) {
				inputPassword = inputPassword.substring(0, inputPassword.length() - 1);
				hiddenPassword = hiddenPassword.substring(0, hiddenPassword.length() - 1);
			}
			break;

		}
	}

	public void keyReleased(int k) {

	}

	private void login() {
		// check minimum length and verify login information
		if (inputID.length() >= MIN_INPUT_LENGTH && inputPassword.length() >= MIN_INPUT_LENGTH
				&& data.login(inputID, inputPassword))
			ic.setInterface(InterfaceControl.PROFILE);
		else {
			initialize();
			invalidInput = true;
		}
	}

	private void readImage() {
		try {
			logo = ImageIO.read(getClass().getResourceAsStream(LOGO_FILE)).getScaledInstance(210, 140, Image.SCALE_DEFAULT);
		} catch (Exception e) {
			logo = null;
		}
	}

}
