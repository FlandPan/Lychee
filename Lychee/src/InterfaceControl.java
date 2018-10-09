
import java.util.*;

public class InterfaceControl {

	public Map<String, Interface> interfaces;
	private Interface currentInterface;

	public static final String LOGIN = "Login";
	public static final String REGISTER = "Register";
	public static final String PROFILE = "Profile";
	public static final String STORE = "Store";
	public static final String COMMUNITY = "Community";
	public static final String SEARCH = "Search";
	public static final String CHAT = "Chat";
	public static final String WALLET = "Wallet";

	public InterfaceControl(Database data) {

		interfaces = new HashMap<String, Interface>();

		interfaces.put(LOGIN, new Login(this, data));
		interfaces.put(REGISTER, new Register(this, data));
		interfaces.put(PROFILE, new Profile(this, data));
		interfaces.put(STORE, new StoreInterface(this, data));
		interfaces.put(COMMUNITY, new CommunityInterface(this, data));
		interfaces.put(SEARCH, new Search(this, data));
		interfaces.put(CHAT, new ChatInterface(this, data));
		interfaces.put(WALLET, new WalletInterface(this, data));

		currentInterface = interfaces.get(LOGIN);
	}

	public void setInterface(String face) {
		currentInterface = interfaces.get(face);
		currentInterface.initialize();
	}

	public void update() {
		currentInterface.update();
	}

	public void draw(java.awt.Graphics2D g) {
		currentInterface.draw(g);
	}

	public void keyTyped(char c) {
		currentInterface.keyTyped(c);
	}

	public void keyPressed(int k) {
		currentInterface.keyPressed(k);
	}

	public void keyReleased(int k) {
		currentInterface.keyReleased(k);
	}

}
