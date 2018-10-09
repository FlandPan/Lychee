import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;

public class Profile extends Interface {

	// selections
	private int currentLayer;
	private int currentSelection;
	private int[] selectionsInLayer;
	private final int[] COPY_OF_SELECTIONS_IN_LAYER = { 0, 3, 2, 2, 2, 2, 3 };
	private final int NUM_OF_LAYERS = 6;
	// 1. 1-user profile, 2-store, 3-community
	// 2. 1-charge money, 2-edit info, 3-6-info
	// 3. 1-previous page, 2-next page, 3-10-friends
	// 4. owned creations
	// 5. made creations
	// 6. creation display

	// edit info
	private boolean editInfo;
	private String newUsername;
	private String newPassword;
	private String hiddenPassword;
	private String newYearOfBirth;
	private String newLocation;

	// friend list
	private User[] friendList;
	private int startFriendIndex, endFriendIndex;

	// creations
	private ArrayList<Content> ownedCreations;
	private ArrayList<Content> madeCreations;
	private int startOwnedIndex, endOwnedIndex, startMadeIndex, endMadeIndex;
	private Content currentContent;
	private Image currentImage;
	private final String DEFAULT_IMAGE = "NA.jpeg";
	private String rating;
	private String review;
	private String tag;

	// graphics
	private Font titleFont;
	private Font textFont;
	private Font smallTextFont;
	private Color topColor;
	private Color decorationColor1;

	private static final int MIN_INPUT_LENGTH = 1;
	private static final int MAX_INPUT_LENGTH = 30;

	public Profile(InterfaceControl ic, Database data) {
		super(ic, data);

		titleFont = new Font("Arial", Font.PLAIN, 30);
		textFont = new Font("Times New Roman", Font.PLAIN, 20);
		smallTextFont = new Font("Times New Roman", Font.PLAIN, 14);
		topColor = new Color(10, 60, 110, 200);
		decorationColor1 = new Color(102, 204, 255, 100);

		// no initialization on construction
	}

	public void initialize() {
		currentLayer = 1;
		currentSelection = 1;
		selectionsInLayer = COPY_OF_SELECTIONS_IN_LAYER.clone();

		editInfo = false;
		newUsername = "";
		newPassword = "";
		hiddenPassword = "";
		newYearOfBirth = "";
		newLocation = "";

		// friend
		friendList = Database.getCurrentUser().getFriendsList();
		if (Database.getCurrentUser().getNumOfFriends() == 0) {
			startFriendIndex = -1;
			endFriendIndex = -1;
		} else {
			startFriendIndex = 0;
			endFriendIndex = Math.min(7, Database.getCurrentUser().getNumOfFriends() - 1);
			selectionsInLayer[3] += (endFriendIndex - startFriendIndex + 1);
		}

		// creation
		ownedCreations = Database.getCurrentUser().getCreationsOwned();
		if (ownedCreations.isEmpty()) {
			startOwnedIndex = -1;
			endOwnedIndex = -1;
		} else {
			startOwnedIndex = 0;
			endOwnedIndex = Math.min(4, ownedCreations.size() - 1);
			selectionsInLayer[4] += (endOwnedIndex - startOwnedIndex + 1);
		}
		madeCreations = Database.getCurrentUser().getCreationsMade();
		if (madeCreations.isEmpty()) {
			startMadeIndex = -1;
			endMadeIndex = -1;
		} else {
			startMadeIndex = 0;
			endMadeIndex = Math.min(4, madeCreations.size() - 1);
			selectionsInLayer[5] += (endMadeIndex - startMadeIndex + 1);
		}
		currentContent = null;
		rating = "";
		review = "";
		tag = "";
	}

	public void update() {

	}

	public void draw(java.awt.Graphics2D g) {

		// bottoming
		// top bar - layer 1
		g.setColor(topColor);
		g.fillRect(0, 0, 1200, 80);
		g.setColor(decorationColor1);
		g.drawLine(0, 80, 1200, 80);

		// layer 2
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, 298, 1200, 298);
		g.drawLine(400, 82, 400, 298);
		g.setColor(decorationColor1);
		g.fillRect(30, 258, 150, 30);
		g.fillRect(220, 258, 150, 30);
		g.setColor(Color.GRAY);
		g.drawRect(30, 258, 150, 30);
		g.drawRect(220, 258, 150, 30);
		if (editInfo) {
			g.setColor(Color.WHITE);
			g.fillRect(10, 100, 300, 23);
			g.fillRect(10, 140, 300, 23);
			g.fillRect(10, 180, 300, 23);
			g.fillRect(10, 220, 300, 23);
			g.setColor(Color.GRAY);
			g.drawRect(10, 100, 300, 23);
			g.drawRect(10, 140, 300, 23);
			g.drawRect(10, 180, 300, 23);
			g.drawRect(10, 220, 300, 23);
		}

		// layer 3
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(300, 300, 300, 800);
		g.setColor(decorationColor1);
		g.fillRoundRect(600, 90, 250, 40, 10, 10);
		g.fillRoundRect(900, 90, 250, 40, 10, 10);
		g.setColor(Color.GRAY);
		g.drawRoundRect(600, 90, 250, 40, 10, 10);
		g.drawRoundRect(900, 90, 250, 40, 10, 10);
		g.setColor(decorationColor1);
		g.drawLine(402, 140, 1200, 140);
		g.drawLine(402, 296, 1200, 296);
		g.drawLine(800, 140, 800, 296);
		g.drawLine(402, 179, 1200, 179);
		g.drawLine(402, 218, 1200, 218);
		g.drawLine(402, 257, 1200, 257);

		// layer 4 & 5
		g.setColor(decorationColor1);
		g.drawLine(0, 350, 298, 350);
		g.drawLine(0, 600, 298, 600);
		g.drawLine(0, 390, 298, 390);
		g.drawLine(0, 430, 298, 430);
		g.drawLine(0, 470, 298, 470);
		g.drawLine(0, 510, 298, 510);
		g.drawLine(0, 640, 298, 640);
		g.drawLine(0, 680, 298, 680);
		g.drawLine(0, 720, 298, 720);
		g.drawLine(0, 760, 298, 760);
		g.drawLine(0, 550, 298, 550);
		g.drawLine(0, 550, 298, 550);
		g.drawLine(0, 550, 298, 550);
		// buttons
		g.setColor(decorationColor1);
		g.fillRoundRect(160, 310, 50, 30, 10, 15);
		g.fillRoundRect(225, 310, 50, 30, 10, 15);
		g.fillRoundRect(160, 560, 50, 30, 10, 15);
		g.fillRoundRect(225, 560, 50, 30, 10, 15);
		g.setColor(Color.GRAY);
		g.drawRoundRect(160, 310, 50, 30, 10, 10);
		g.drawRoundRect(225, 310, 50, 30, 10, 10);
		g.drawRoundRect(160, 560, 50, 30, 10, 10);
		g.drawRoundRect(225, 560, 50, 30, 10, 10);

		// layer 6
		if (currentContent != null) {
			// main image
			g.drawImage(currentImage, 305, 400, null);
			// rating, review, tag
			g.setColor(Color.WHITE);
			g.fillRect(818, 633, 55, 40);
			g.fillRect(1050, 633, 130, 40);
			g.fillRect(720, 730, 460, 40);
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(818, 633, 55, 40);
			g.drawRect(1050, 633, 130, 40);
			g.drawRect(720, 730, 460, 40);
			if (madeCreations.contains(currentContent)) {
				g.setColor(Color.GRAY);
				g.fillRect(818, 633, 55, 40);
			}
		}

		// selection shade
		switch (currentLayer) {

		case 1:
			g.setColor(shade);
			switch (currentSelection) {
			case 1:
				g.fillRoundRect(120, 20, 200, 40, 10, 10);
				break;
			case 2:
				g.fillRoundRect(360, 20, 160, 40, 10, 10);
				break;
			case 3:
				g.fillRoundRect(560, 20, 160, 40, 10, 10);
				break;
			}
			break;

		case 2:
			g.setColor(selectionShade);
			g.fillRect(0, 82, 398, 214);
			g.setColor(shade);
			switch (currentSelection) {
			case 1:
				g.fillRoundRect(25, 253, 160, 40, 10, 10);
				break;
			case 2:
				g.fillRoundRect(215, 253, 160, 40, 10, 10);
				break;
			case 3:
				g.fillRoundRect(8, 98, 304, 27, 5, 5);
				break;
			case 4:
				g.fillRoundRect(8, 138, 304, 27, 5, 5);
				break;
			case 5:
				g.fillRoundRect(8, 178, 304, 27, 5, 5);
				break;
			case 6:
				g.fillRoundRect(8, 218, 304, 27, 5, 5);
				break;
			}
			break;

		case 3:
			g.setColor(selectionShade);
			g.fillRect(402, 82, 798, 214);
			g.setColor(shade);
			switch (currentSelection) {
			case 1:
				g.fillRoundRect(595, 85, 260, 50, 10, 10);
				break;
			case 2:
				g.fillRoundRect(895, 85, 260, 50, 10, 10);
				break;
			case 3:
				g.fillRect(402, 141, 397, 37);
				break;
			case 4:
				g.fillRect(402, 180, 397, 37);
				break;
			case 5:
				g.fillRect(402, 219, 397, 37);
				break;
			case 6:
				g.fillRect(402, 258, 397, 37);
				break;
			case 7:
				g.fillRect(802, 141, 397, 37);
				break;
			case 8:
				g.fillRect(802, 180, 397, 37);
				break;
			case 9:
				g.fillRect(802, 219, 397, 37);
				break;
			case 10:
				g.fillRect(802, 258, 397, 37);
				break;
			}
			break;

		case 4:
			g.setColor(selectionShade);
			g.fillRect(0, 300, 298, 248);
			g.setColor(shade);
			switch (currentSelection) {
			case 1:
				g.fillRoundRect(155, 305, 60, 40, 10, 10);
				break;
			case 2:
				g.fillRoundRect(220, 305, 60, 40, 10, 10);
				break;
			case 3:
				g.fillRect(0, 351, 298, 38);
				break;
			case 4:
				g.fillRect(0, 391, 298, 38);
				break;
			case 5:
				g.fillRect(0, 431, 298, 38);
				break;
			case 6:
				g.fillRect(0, 471, 298, 38);
				break;
			case 7:
				g.fillRect(0, 511, 298, 38);
				break;
			}
			break;

		case 5:
			g.setColor(selectionShade);
			g.fillRect(0, 552, 298, 248);
			g.setColor(shade);
			switch (currentSelection) {
			case 1:
				g.fillRoundRect(155, 555, 60, 40, 10, 10);
				break;
			case 2:
				g.fillRoundRect(220, 555, 60, 40, 10, 10);
				break;
			case 3:
				g.fillRect(0, 601, 298, 38);
				break;
			case 4:
				g.fillRect(0, 641, 298, 38);
				break;
			case 5:
				g.fillRect(0, 681, 298, 38);
				break;
			case 6:
				g.fillRect(0, 721, 298, 38);
				break;
			case 7:
				g.fillRect(0, 761, 298, 38);
				break;
			}
			break;

		case 6:
			g.setColor(selectionShade);
			g.fillRect(302, 300, 898, 500);
			if (currentContent != null) {
				g.setColor(shade);
				switch (currentSelection) {
				case 1:
					g.fillRect(813, 628, 65, 50);
					break;
				case 2:
					g.fillRect(1045, 628, 140, 50);
					break;
				case 3:
					g.fillRect(715, 725, 470, 50);
					break;
				}
			}
			break;

		}

		// texts
		// layer 1
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		// use FontMetrics to center username
		FontMetrics metrics = g.getFontMetrics(titleFont);
		g.drawString(Database.getCurrentUser().getUsername(),
				120 + (200 - metrics.stringWidth(Database.getCurrentUser().getUsername())) / 2, 50);
		g.drawString("Store", 404, 50);
		g.drawString("Community", 564, 50);

		// layer 2
		g.setColor(Color.WHITE);
		g.setFont(textFont);
		if (!editInfo) {
			g.drawString("User ID:", 10, 110);
			g.drawString("Age:", 10, 140);
			g.drawString("Location:", 10, 170);
			g.drawString("E-mail:", 10, 200);
			g.drawString("Balance:", 10, 240);
			g.drawString("" + Database.getCurrentUser().getUserId(), 83, 110);
			if (Database.getCurrentUser().getAge() < 0)
				g.drawString("N/A", 55, 140);
			else
				g.drawString("" + Database.getCurrentUser().getAge(), 55, 140);
			g.drawString(Database.getCurrentUser().getLocation(), 90, 170);
			g.drawString(Database.getCurrentUser().getEmail(), 75, 200);
			g.drawString("" + Database.getCurrentUser().getMoney(), 86, 240);
		}
		// button text
		g.drawString("Charge Money", 47, 280);
		if (!editInfo)
			g.drawString("Edit Information", 230, 280);
		else
			g.drawString("Save Information", 230, 280);
		// other edit info text
		if (editInfo) {
			// base text
			g.setColor(Color.WHITE);
			g.setFont(smallTextFont);
			g.drawString("New username:", 20, 97);
			g.drawString("New password:", 20, 137);
			g.drawString("Update year of birth:", 20, 177);
			g.drawString("Update location:", 20, 217);
			// input text
			g.setColor(Color.BLACK);
			g.drawString(newUsername, 15, 115);
			g.drawString(hiddenPassword, 15, 155);
			g.drawString(newYearOfBirth, 15, 195);
			g.drawString(newLocation, 15, 235);
		}

		// layer 3
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		g.drawString("Friend List", 420, 120);
		g.setFont(textFont);
		g.drawString("Previous Page", 666, 115);
		g.drawString("Next Page", 980, 115);
		if (startFriendIndex != -1 && endFriendIndex != -1) {
			if (startFriendIndex + 0 <= endFriendIndex && friendList[startFriendIndex + 0] != null)
				g.drawString(friendList[startFriendIndex + 0].getUsername(), 410, 167);
			if (startFriendIndex + 1 <= endFriendIndex && friendList[startFriendIndex + 1] != null)
				g.drawString(friendList[startFriendIndex + 1].getUsername(), 410, 206);
			if (startFriendIndex + 2 <= endFriendIndex && friendList[startFriendIndex + 2] != null)
				g.drawString(friendList[startFriendIndex + 2].getUsername(), 410, 245);
			if (startFriendIndex + 3 <= endFriendIndex && friendList[startFriendIndex + 3] != null)
				g.drawString(friendList[startFriendIndex + 3].getUsername(), 410, 284);
			if (startFriendIndex + 4 <= endFriendIndex && friendList[startFriendIndex + 4] != null)
				g.drawString(friendList[startFriendIndex + 4].getUsername(), 810, 167);
			if (startFriendIndex + 5 <= endFriendIndex && friendList[startFriendIndex + 5] != null)
				g.drawString(friendList[startFriendIndex + 5].getUsername(), 810, 206);
			if (startFriendIndex + 6 <= endFriendIndex && friendList[startFriendIndex + 6] != null)
				g.drawString(friendList[startFriendIndex + 6].getUsername(), 810, 245);
			if (startFriendIndex + 7 <= endFriendIndex && friendList[startFriendIndex + 7] != null)
				g.drawString(friendList[startFriendIndex + 7].getUsername(), 810, 284);
		}

		// layer 4 & 5
		g.setColor(Color.WHITE);
		g.setFont(textFont);
		g.drawString("Owned Contents", 10, 330);
		g.drawString("Created Contents", 10, 580);
		g.setFont(titleFont);
		g.drawString("<", 175, 336);
		g.drawString(">", 242, 336);
		g.drawString("<", 175, 586);
		g.drawString(">", 242, 586);
		g.setFont(textFont);
		if (!ownedCreations.isEmpty()) {
			if (startOwnedIndex + 0 <= endOwnedIndex && ownedCreations.get(startOwnedIndex + 0) != null)
				g.drawString(ownedCreations.get(startOwnedIndex + 0).getName(), 10, 377);
			if (startOwnedIndex + 1 <= endOwnedIndex && ownedCreations.get(startOwnedIndex + 1) != null)
				g.drawString(ownedCreations.get(startOwnedIndex + 1).getName(), 10, 417);
			if (startOwnedIndex + 2 <= endOwnedIndex && ownedCreations.get(startOwnedIndex + 2) != null)
				g.drawString(ownedCreations.get(startOwnedIndex + 2).getName(), 10, 457);
			if (startOwnedIndex + 3 <= endOwnedIndex && ownedCreations.get(startOwnedIndex + 3) != null)
				g.drawString(ownedCreations.get(startOwnedIndex + 3).getName(), 10, 497);
			if (startOwnedIndex + 4 <= endOwnedIndex && ownedCreations.get(startOwnedIndex + 4) != null)
				g.drawString(ownedCreations.get(startOwnedIndex + 4).getName(), 10, 537);
		}
		if (!madeCreations.isEmpty()) {
			if (startMadeIndex + 0 <= endMadeIndex && madeCreations.get(startMadeIndex + 0) != null)
				g.drawString(madeCreations.get(startMadeIndex + 0).getName(), 10, 627);
			if (startMadeIndex + 1 <= endMadeIndex && madeCreations.get(startMadeIndex + 1) != null)
				g.drawString(madeCreations.get(startMadeIndex + 1).getName(), 10, 667);
			if (startMadeIndex + 2 <= endMadeIndex && madeCreations.get(startMadeIndex + 2) != null)
				g.drawString(madeCreations.get(startMadeIndex + 2).getName(), 10, 707);
			if (startMadeIndex + 3 <= endMadeIndex && madeCreations.get(startMadeIndex + 3) != null)
				g.drawString(madeCreations.get(startMadeIndex + 3).getName(), 10, 747);
			if (startMadeIndex + 4 <= endMadeIndex && madeCreations.get(startMadeIndex + 4) != null)
				g.drawString(madeCreations.get(startMadeIndex + 4).getName(), 10, 787);
		}

		// layer 6
		if (currentContent != null) {
			// infos
			g.setColor(Color.WHITE);
			g.setFont(titleFont);
			g.drawString(currentContent.getName(), 310, 380);
			g.drawString("Creator: " + currentContent.getCreator().getUsername(), 720, 450);
			g.drawString("Date of release: " + currentContent.getDateOfRelease(), 720, 500);
			g.drawString("Rating: " + numberFormat.format(currentContent.rating) + "/10", 720, 550);
			// inputs
			g.drawString("Rating:        /10", 720, 660);
			g.drawString("Add tag:", 933, 660);
			g.drawString("Review:", 720, 710);
			// more inputs
			g.setColor(Color.BLACK);
			g.setFont(textFont);
			g.drawString(rating, 830, 660);
			g.drawString(tag, 1060, 660);
			g.drawString(review, 730, 755);
		}

	}

	public void keyTyped(char c) {
		// normal character input
		// restrict maximum input length
		if (currentLayer == 2) {
			if (currentSelection == 3 && newUsername.length() < MAX_INPUT_LENGTH) {
				newUsername += c;
			} else if (currentSelection == 4 && newPassword.length() < MAX_INPUT_LENGTH) {
				newPassword += c;
				hiddenPassword += '\u25cf';
			} else if (currentSelection == 5 && newYearOfBirth.length() < MAX_INPUT_LENGTH) {
				newYearOfBirth += c;
			} else if (currentSelection == 6 && newLocation.length() < MAX_INPUT_LENGTH) {
				newLocation += c;
			}
		} else if (currentLayer == 6) {
			if (currentSelection == 1 && !madeCreations.contains(currentContent) && rating.length() < 2
					&& (c >= '0' && c <= '9')) {
				rating += c;
			} else if (currentSelection == 2 && tag.length() < 10) {
				tag += c;
			} else if (currentSelection == 3 && review.length() < 50) {
				review += c;
			}
		}
	}

	public void keyPressed(int k) {
		// dicide on different commands based on the entered keys
		switch (k) {

		case KeyEvent.VK_UP:
			if (currentLayer > 1) {
				currentLayer--;
				currentSelection = 1;
			}
			break;

		case KeyEvent.VK_DOWN:
			if (currentLayer < NUM_OF_LAYERS) {
				currentLayer++;
				currentSelection = 1;
			}
			break;

		case KeyEvent.VK_LEFT:
			if (currentSelection > 1)
				currentSelection--;
			break;

		case KeyEvent.VK_RIGHT:
			if (currentSelection < selectionsInLayer[currentLayer])
				currentSelection++;
			break;

		case KeyEvent.VK_ENTER:
			switch (currentLayer) {

			case 1:
				switch (currentSelection) {
				case 1:
					ic.setInterface(InterfaceControl.PROFILE);
					break;
				case 2:
					ic.setInterface(InterfaceControl.STORE);
					break;
				case 3:
					ic.setInterface(InterfaceControl.COMMUNITY);
					break;
				}
				break;

			case 2:
				switch (currentSelection) {
				case 1:
					ic.setInterface(InterfaceControl.WALLET);
					break;
				case 2:
					if (!editInfo) { // turn on edit info
						editInfo = true;
						selectionsInLayer[2] = 6;
					} else { // finish edit info
						setNewInfo();
						editInfo = false;
						selectionsInLayer[2] = 2;
					}
					break;
				case 3:
				case 4:
				case 5:
					currentSelection++;
					break;
				case 6:
					currentSelection = 2;
					break;
				}
				break;

			case 3:
				if (currentSelection == 1) {
					if (startFriendIndex > 0) {
						startFriendIndex -= 8;
						endFriendIndex -= 8;
						selectionsInLayer[3] = 10;
					}
				} else if (currentSelection == 2) {
					if (endFriendIndex < Database.getCurrentUser().getNumOfFriends() - 1) {
						startFriendIndex += 8;
						endFriendIndex += Math.min(8, Database.getCurrentUser().getNumOfFriends() - startFriendIndex);
						selectionsInLayer[3] = 2 + (endFriendIndex - startFriendIndex + 1);
					}
				} else {
					if (friendList[startFriendIndex + currentSelection - 3] != null) {
						if (Database.getCurrentUser()
								.getChat(friendList[startFriendIndex + currentSelection - 3]) == null) {
							// start new chat if no old chat is found
							Database.getCurrentUser().newChat(friendList[startFriendIndex + currentSelection - 3]);
						}
						// set current chat
						data.setCurrentChat(
								Database.getCurrentUser().getChats()[startFriendIndex + currentSelection - 3]);
						ic.setInterface(InterfaceControl.CHAT);
					}
				}
				break;

			case 4:
				if (currentSelection == 1) {
					if (startOwnedIndex > 0) {
						startOwnedIndex -= 5;
						endOwnedIndex -= 5;
						selectionsInLayer[4] = 7;
					}
				} else if (currentSelection == 2) {
					if (endOwnedIndex < ownedCreations.size() - 1) {
						startOwnedIndex += 5;
						endOwnedIndex += Math.min(5, ownedCreations.size() - startOwnedIndex);
						selectionsInLayer[4] = 2 + (endOwnedIndex - startOwnedIndex + 1);
					}
				} else {
					if (ownedCreations.get(startOwnedIndex + currentSelection - 3) != null) {
						// display content
						currentContent = ownedCreations.get(startOwnedIndex + currentSelection - 3);
						readImage(currentContent.getImageName());
					}
				}
				break;

			case 5:
				if (currentSelection == 1) {
					if (startMadeIndex > 0) {
						startMadeIndex -= 5;
						endMadeIndex -= 5;
						selectionsInLayer[5] = 7;
					}
				} else if (currentSelection == 2) {
					if (endMadeIndex < madeCreations.size() - 1) {
						startMadeIndex += 5;
						endMadeIndex += Math.min(5, madeCreations.size() - startMadeIndex);
						selectionsInLayer[5] = 2 + (endMadeIndex - startMadeIndex + 1);
					}
				} else {
					if (madeCreations.get(startMadeIndex + currentSelection - 3) != null) {
						// display content
						currentContent = madeCreations.get(startMadeIndex + currentSelection - 3);
						readImage(currentContent.getImageName());
					}
				}
				break;

			case 6:
				if (currentSelection == 1 && rating.length() >= MIN_INPUT_LENGTH) {
					// add or change rating
					try {
						Database.getCurrentUser().rate(currentContent, Double.parseDouble(rating));
						rating = "";
					} catch (NumberFormatException e) {

					}
				} else if (currentSelection == 2 && tag.length() >= MIN_INPUT_LENGTH) {
					// add tag
					Database.getCurrentUser().addTag(currentContent, tag);
					tag = "";
				} else if (currentSelection == 3 && review.length() >= MIN_INPUT_LENGTH) {
					// add review
					Database.getCurrentUser().review(currentContent, review);
					review = "";
				}
				break;

			}
			break;

		case KeyEvent.VK_BACK_SPACE:
			if (currentLayer == 2) {
				if (currentSelection == 3 && newUsername.length() > 0) {
					newUsername = newUsername.substring(0, newUsername.length() - 1);
				} else if (currentSelection == 4 && newPassword.length() > 0) {
					newPassword = newPassword.substring(0, newPassword.length() - 1);
					hiddenPassword = hiddenPassword.substring(0, hiddenPassword.length() - 1);
				} else if (currentSelection == 5 && newYearOfBirth.length() > 0) {
					newYearOfBirth = newYearOfBirth.substring(0, newYearOfBirth.length() - 1);
				} else if (currentSelection == 6 && newLocation.length() > 0) {
					newLocation = newLocation.substring(0, newLocation.length() - 1);
				}
			} else if (currentLayer == 6) {
				if (currentSelection == 1 && rating.length() > 0) {
					rating = rating.substring(0, rating.length() - 1);
				} else if (currentSelection == 2 && tag.length() > 0) {
					tag = tag.substring(0, tag.length() - 1);
				} else if (currentSelection == 3 && review.length() > 0) {
					review = review.substring(0, review.length() - 1);
				}
			}
			break;

		}
	}

	public void keyReleased(int k) {

	}

	private void setNewInfo() {
		if (newUsername.length() >= MIN_INPUT_LENGTH)
			Database.getCurrentUser().setUsername(newUsername);
		if (newPassword.length() >= MIN_INPUT_LENGTH)
			Database.getCurrentUser().setPassword(newPassword);
		if (newYearOfBirth.length() >= MIN_INPUT_LENGTH)
			try {
				Database.getCurrentUser().setYearOfBirth(Integer.parseInt(newYearOfBirth));
			} catch (NumberFormatException nfx) {
			}
		if (newLocation.length() >= MIN_INPUT_LENGTH) {
			if (newLocation.equals("N/A"))
				Database.getCurrentUser().setLocation("");
			else
				Database.getCurrentUser().setLocation(newLocation);
		}
		newUsername = "";
		newPassword = "";
		hiddenPassword = "";
		newYearOfBirth = "";
		newLocation = "";
	}

	private void readImage(String imageName) {
		try {
			currentImage = ImageIO.read(getClass().getResourceAsStream(imageName)).getScaledInstance(400, 400,
					Image.SCALE_DEFAULT);
		} catch (Exception e) {
			System.out.println("Error");
			try {
				currentImage = ImageIO.read(getClass().getResourceAsStream(DEFAULT_IMAGE)).getScaledInstance(400, 400,
						Image.SCALE_DEFAULT);
			} catch (Exception x) {
				currentImage = null;
			}
		}
	}

}
