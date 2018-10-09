import java.io.*;

public class Database implements Serializable{

	private static final long serialVersionUID = 6287051071887483228L;
	public static final String SERIALIZE_FILE = "DatabaseSerialized.ser";		// Serialized Data
	private static final String NEW_DATA = "NewData.txt";						// Initial Data
	private static final String ADD_DATA = "AddData.txt";						// Additional Data
	private static final String HUMAN_DATA = "HumanRead.txt";
	
	private static User currentUser;
	private Chat currentChat;
	
	private UserManagement userManagement;
	private Community community;
	private Store store;
	
	public Database() {
		currentUser = null;
		currentChat = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(NEW_DATA));
			int numOfUsers = Integer.parseInt(in.readLine());
			User[] userList = new User[numOfUsers]; 								// creates a user array based on txt file
			
			for(int i = 0; i < numOfUsers; i++) {									// adds each element in the file
				userList[i] = new User(Integer.parseInt(in.readLine()), in.readLine(), in.readLine(), in.readLine());
			}
			
			userManagement = new UserManagement(userList);							// creates usermanagement based on exisiting user array
			
			int numOfPosts = Integer.parseInt(in.readLine());
			Post[] postList = new Post[numOfPosts];									// creates a post array based on txt file
			for(int i = 0; i < numOfPosts; i++) {									// adds each element in the txt file
				postList[i] = new Post(userManagement.findUserByID(Integer.parseInt(in.readLine())), in.readLine(), in.readLine());
			}

			community = new Community(postList);									// creates community based on the posts
			
			
			int numOfContents = Integer.parseInt(in.readLine());

			Content[] contentList = new Content[numOfContents];						// creates content array based on txt file

			String genre;
			String name;
			User creator;
			double cost;
			int age;
			for(int i = 0; i < numOfContents; i++) {
				genre = in.readLine();
				name = in.readLine();
				creator = userManagement.findUserByID(Integer.parseInt(in.readLine()));
				cost = Double.parseDouble(in.readLine());
				age = Integer.parseInt(in.readLine());
				if(genre.equals("Anime")) {
					contentList[i] = new Anime(name, creator, cost, age);
				} else if(genre.equals("Comic")) {
					contentList[i] = new Comic(name, creator, cost, age);
				} else if (genre.equals("Game")) {
					contentList[i] = new Game(name, creator, cost, age);
				}
				
				creator.createContent(contentList[i]);
			}	
			store = new Store(contentList);											// creates the store based on the content lists
			in.close();
		} catch(IOException iox) {
			userManagement = new UserManagement(new User[0]);
			store = new Store(new Content[0]);
			community = new Community(new Post[0]);
			System.out.println("error readin NEWDATA");
		} catch(Exception x) {
			x.printStackTrace();
		}
		
		addNewData();															// adds in additional data
		
	}
	// Adds data on top of the constructor
	public void addNewData() {
		currentUser = null;
		currentChat = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(ADD_DATA));
			
			int numOfUsers = Integer.parseInt(in.readLine());
			User[] userList = new User[numOfUsers];
			
			for(int i = 0; i < numOfUsers; i++) {
				userList[i] = new User(Integer.parseInt(in.readLine()), in.readLine(), in.readLine(), in.readLine());
			}
			userManagement.addData(userList);
			
			int numOfPosts = Integer.parseInt(in.readLine());
			Post[] postList = new Post[numOfPosts];
			for(int i = 0; i < numOfPosts; i++) {
				postList[i] = new Post(userManagement.findUserByID(Integer.parseInt(in.readLine())), in.readLine(), in.readLine());
			}

			community.addData(postList);
			
			
			int numOfContents = Integer.parseInt(in.readLine());
			
			Content[] contentList = new Content[numOfContents];
			String genre;
			String name;
			User creator;
			double cost;
			int age;
			for(int i = 0; i < numOfContents; i++) {
				genre = in.readLine();
				name = in.readLine();
				creator = userManagement.findUserByID(Integer.parseInt(in.readLine()));
				cost = Double.parseDouble(in.readLine());
				age = Integer.parseInt(in.readLine());
				if(genre.equals("Anime")) {
					contentList[i] = new Anime(name, creator, cost, age);
				} else if(genre.equals("Comic")) {
					contentList[i] = new Comic(name, creator, cost, age);
				} else if (genre.equals("Game")) {
					contentList[i] = new Game(name, creator, cost, age);
				}
				
				creator.createContent(contentList[i]);
			}	
			
			store.addData(contentList);
			in.close();
		} catch(IOException iox) {
			System.out.println("error readin ADD_DATA");
		} catch(Exception x) {
			x.printStackTrace();
		}
	}
	// accessors
	public UserManagement getUserManagement() {
		return userManagement;
	}
	
	public Community getCommunity() {
		return community;
	}
	
	public Store getStore() {
		return store;
	}
	
	public static User getCurrentUser(){
		return currentUser;
	}
	
	public Chat getCurrentChat() {
		return currentChat;
	}
	
	public void setCurrentChat(Chat current) {
		currentChat = current;
	}
	
	public boolean login(String idOrEmail, String password) {

		User attempt = null;
		try {													// determines if the user entered an int
			attempt = userManagement.findUserByID(Integer.parseInt(idOrEmail));
		} catch (NumberFormatException nfx) {					// logs in with email if login isn't an int
			attempt = userManagement.findUserByEmail(idOrEmail);	
		}
		if(attempt != null) {									// if such user is found
			if(password.equals(attempt.getPassword())) {		// checks password
				currentUser = attempt;							// logs in
				return true;									// successful
			} else {											// wrong password
				//System.out.println("Wrong password!");
				return false;									// unsuccessful
			}
		}else {													// no such user
			//System.out.println("No such user!");
			return false;										// unsuccessful
		}
	}
	
	public void logout() {
		
		// serialized Database
		try
	      {
	         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZE_FILE));
	         out.writeObject(this);
	         out.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		
		try {
			String str = "";
			str += userManagement.toString() + "\n";
			str += community.toString() + "\n";
			str += store.toString() + "\n";
			BufferedWriter out = new BufferedWriter(new FileWriter(HUMAN_DATA));
			for(int i = 0; i < str.length(); i++) {
				out.write(str.charAt(i));
				if(str.charAt(i) == '\n') {
					out.newLine();
				}
			}
			out.close();
		} catch(IOException iox) {
			System.out.println("Error writing data");
		} 
		
	}
}
