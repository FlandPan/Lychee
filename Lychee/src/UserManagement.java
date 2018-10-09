import java.io.Serializable;
import java.util.*;

public class UserManagement implements Serializable {

	private static final long serialVersionUID = -2422501676600903927L;
	private ArrayList<User> users = new ArrayList<User>();
	
	//constuctor
	public UserManagement(User[] userList) {
		addData(userList);
	}
	
	// accessor
	public ArrayList<User> getUsers(){
		return users;
	}
	
	// adds new user to a users
	public void addUser(User newUser) {
		users.add(newUser);
	}
	
	// creates a new user and calls addUser
	public int registerUser(String username, String password, String email) {
		User newUser;
		int id = -1;
		if(findUserByEmail(email) == null) {
			id = users.size()+1;
			newUser = new User(id, email, username, password);
			addUser(newUser);
		}
		return id;
	}
	
	// remove a user from the list
	public void removeUser(User toBeRemoved) {
		users.remove(toBeRemoved);
	}
	
	// search for user with this id
	public User findUserByID(int lookFor) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUserId() == lookFor) {
				return users.get(i);
			}
		}
		return null;
	}
	
	// search for user by email
	public User findUserByEmail(String lookFor) {
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getEmail().equalsIgnoreCase(lookFor)) {					// upper case or lower case should not matter
				return users.get(i);
			}
		}
		return null;
	}
	
	// search for all users with this username
	public ArrayList<User> findUserByUsername(String lookFor) {
		ArrayList<User> results = new ArrayList<User>();
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(lookFor)) {
				results.add(users.get(i));
			}
		}
		return results;
	}
	
	// redirects to the actual recursion method
	private ArrayList<User> searchByCloseness(int index){
		return searchByCloseness(index, Database.getCurrentUser());
	}
	
	
	private ArrayList<User> searchByCloseness(int index, User cur) {
		ArrayList<User> results = new ArrayList<User>();								// result
		if(index == 0) {																// exit condition
			return results;
		} else {
			for(int i = 0; i < cur.getNumOfFriends(); i++) {							// loops through the friend list.
				if(!Database.getCurrentUser().isFriend(cur.getFriendsList()[i])) {		// find anyone who is not friended
					results.add(cur.getFriendsList()[i]);								// adds to the result
				}
				results.addAll(searchByCloseness(index-1, cur.getFriendsList()[i]));	// look for friends' friends who are not friended
			}
			return results;
		}
	}
	
	// search bar
	public ArrayList<User> findUser(String search) {
		ArrayList<User> results = new ArrayList<User>();
		try {
			int asInteger = Integer.parseInt(search);
			if(asInteger < 5) {
				results.addAll(searchByCloseness(asInteger));
			} else {
				results.add(findUserByID(asInteger));
			}	
		} catch(NumberFormatException nfx) {
			
		}
		results.addAll(findUserByUsername(search));
		
		return results;
		
	}
	
	// adds an array of users to the array list.
	public void addData(User[] userList) {
		for(int i = 0; i < userList.length; i++) {
			users.add(userList[i]);
		}
	}
	
	public String toString() {
		String str = "Users: \n"; 
		for(int i = 0; i < users.size(); i++) {
			str += users.get(i).toString() + "\n";
		}
		return str;
	}

}
