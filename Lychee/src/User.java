import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class User implements Serializable{
	
	private static final long serialVersionUID = 684946418087905199L;
	private int userId;
	private String username;
	private String password;
	private String email;
	private int yearOfBirth;
	private int age;
	private String location;
	private int numOfFriends;
	private int potentialNumberOfFriends;
	private Wallet bag;
	private User[] friendsList;
	private Chat[] chats;
	private ArrayList<String> transactionHistory;
	private ArrayList<Double> rating;
	private ArrayList<Content> creationsMade;
	private ArrayList<Content> creationsOwned;
	private Queue<User> receivedRequest = new LinkedList<User>();
	private final int MAXFRIENDS = 20;
	
	//Constructors
	public User(int uID, String mail, String uName, String pass){
		userId = uID;
		email = mail;
		location = "";
		age = -1;
		username = uName;
		password = pass;
		numOfFriends = 0;
		potentialNumberOfFriends = 0;
		bag = new Wallet();
		transactionHistory = new ArrayList<String>();
		rating = new ArrayList<Double>();
		creationsMade = new ArrayList<Content>();
		creationsOwned = new ArrayList<Content>();
		friendsList = new User[MAXFRIENDS];
		chats = new Chat[20];
	}
	public User(){
		numOfFriends = 0;
		location = "";
		age = -1;
		username = "";
		password = "";
		potentialNumberOfFriends = 0;
		bag = new Wallet();
		transactionHistory = new ArrayList<String>();
		rating = new ArrayList<Double>();
		creationsMade = new ArrayList<Content>();
		creationsOwned = new ArrayList<Content>();
		friendsList = new User[MAXFRIENDS];
		chats = new Chat[20];
	}
	
	//Mutators
	public void setEmail(String newEmail){
		email = newEmail;
	}
	public void setPassword(String newPass){
		password = newPass;
	}
	public void setUsername(String newUsername){
		username = newUsername;
	}
	public void setLocation(String newLocation){
		location = newLocation;
	}
	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
		setAge();
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setNumOfFriends(int numOfFriends) {
		this.numOfFriends = numOfFriends;
	}
	public void setPotentialNumberOfFriends(int potentialNumberOfFriends) {
		this.potentialNumberOfFriends = potentialNumberOfFriends;
	}
	
	//Accessors
	public int getAge() {
		return age;
	}
	public double getMoney() {
		return bag.getAmount();
	}
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public String getLocation() {
		return location;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public int getUserId() {
		return userId;
	}
	public User getFirstRequest() {
		return receivedRequest.peek();
	}
	public int getNumOfFriends() {
		return numOfFriends;
	}
	public User[] getFriendsList() {
		return friendsList;
	}
	public Wallet getBag() {
		return bag;
	}
	public Chat[] getChats() {
		return chats;
	}
	public ArrayList<Content> getCreationsMade() {
		return creationsMade;
	}
	public ArrayList<Content> getCreationsOwned() {
		return creationsOwned;
	}
	
	//General Methods
	public String printTransactionHistory(){
		String s = "";
		for (int i = 0;i < transactionHistory.size();i ++)
			s += transactionHistory.get(i) + "\n";
		return s;
	}
	
	public void setAge(){
		age = Calendar.getInstance().get(Calendar.YEAR)-yearOfBirth;
	}
	public boolean isOfAge(Content cObject){
		return cObject.getAgeRestriction() <= age;
	}
	public boolean isFriend(User other){
		for (int i = 0;i < MAXFRIENDS;i ++)
			if (other.equals(friendsList[i]))
				return true;
		return false;
	}
	public String toString(){
		String result = "";
		result += "User ID: " + userId + "\n";
		result += "Username: " + username + "\n";
		result += "Password: " + password + "\n";
		result += "Location: " + location + "\n";
		return result;
	}
	
	//Chat methods
	public boolean newChat(User other){
		int indexOfOther = -1;
		boolean found = false;
		for(int i = 0;i < MAXFRIENDS && !found;i ++){ //searches 'friendslist' for 'other'
			if (other.equals(friendsList[i])){
				indexOfOther = i;
				found = true;
			}
		}
		if (indexOfOther == -1)
			return false;
		
		chats[indexOfOther] = new Chat(this, other);
		//sets 'this' as user1 and 'other' as user2
		
		other.addChat(chats[indexOfOther]);
		//adds created chat to chats array of other user, aka user2
		return true;
	}
	public boolean addChat(Chat chat){
		int indexOfOtherChat = -1;
		boolean found = false;
		for (int i = 0;i < MAXFRIENDS && !found;i ++){ //searches friendslist for chat creator aka user1
			if (chat.getUser1().equals(friendsList[i])){
				indexOfOtherChat = i;
				found = true;
			}
		}
		if (indexOfOtherChat == -1)
			return false;
		
		chats[indexOfOtherChat] = chat;
		//sets chat with creator as parallel to friendslist user
		return true;
	}
	public int getChatIndex(User other){
		int indexUser = -1;
		boolean found = false;
		for(int i = 0;i < MAXFRIENDS && !found;i ++){ //searches 'friendslist' for 'other'
			if (other.equals(friendsList[i])){
				indexUser = i;
				found = true;
			}
		}
		return indexUser;
	}
	public Chat getChat(User other){
		int indexUser = -1;
		boolean found = false;
		for(int i = 0;i < MAXFRIENDS && !found;i ++){ //searches 'friendslist' for 'other'
			if (other.equals(friendsList[i])){
				indexUser = i;
				found = true;
			}
		}
		if (indexUser == -1)
			return null;
		else if(chats[indexUser] == null)
			this.newChat(other);
		
		return chats[indexUser];
	}
	public void addTextToChat(Chat chat, String text){
		chat.addToLog(text, this);
	}
	
	//Rate and Review Method
	public boolean rate(Content cObject, double ratingValue){
		boolean found = false;
		int indexOfContent = -1;
		for (int i = 0;i < creationsOwned.size() && !found;i ++){
			if (cObject.equals(creationsOwned.get(i))){
				found = true;
				indexOfContent = i;
			}
		}
		if (!found)
			return false;
		if (rating.get(indexOfContent) == 0)
			cObject.addRating(ratingValue);
		else
			cObject.changeRating(rating.get(indexOfContent), ratingValue);
		return true;
	}
	public boolean review(Content cObject, String reviewText){
		boolean found = false;
		int indexOfContent = -1;
		for (int i = 0;i < creationsOwned.size() && !found;i ++){
			if (cObject.equals(creationsOwned.get(i))){
				found = true;
				indexOfContent = i;
			}
		}
		if (!found)
			return false;
		creationsOwned.get(indexOfContent).addReview(reviewText);
		return true;
	}
	
	//Content methods
	public void createContent(Content c) {
		creationsMade.add(c);
	}
	public Content createContent(String genre, String name, double cost, int age){

		Content newContent;
		if(genre.equals("Anime")) {
			newContent = new Anime(name, this, cost, age);
		} else if(genre.equals("Comic")) {
			newContent = new Comic(name, this, cost, age);
		} else {
			newContent = new Game(name, this, cost, age);
		}
		
		newContent.setCreator(this);
		creationsMade.add(newContent);
		
		return newContent;
		
	}
	public boolean addTag(Content cObject, String tag){
		int indexOfContent = -1;
		boolean found = false;
		for (int i = 0;i < creationsMade.size() && !found;i ++){
			if (creationsMade.get(i).equals(cObject)){
				indexOfContent = i;
				found = true;
			}
		}
		if (!found)
			return false;
		return creationsMade.get(indexOfContent).addTag(tag);
	}
	public boolean purchaseContent(Content cObject){
		boolean buy = bag.spend(cObject.getCost());
		//if enough money and over age restriction of content then content is bought and added to creationsOwned
		if (buy && isOfAge(cObject)){
			creationsOwned.add(cObject);
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			transactionHistory.add("\nContent: " + cObject + " Price: " + cObject.getCost() + " Time bought: " + timeStamp);
			return true;
		}
		else
			return false;
	}
	public boolean deleteContentMade(Content cObject){
		boolean found = false;
		for (int i = 0;i < creationsMade.size();i ++){
			if (cObject.equals(creationsMade.get(i))){
				creationsMade.set(i, null);
				found = true;
			}
			if (creationsMade.get(i) != null && found){
				creationsMade.set(i-1, creationsMade.get(i));
				creationsMade.set(i, null);
			}
		}
		if (found)
			return true;
		else 
			return false;
	}
	public boolean deletePurchasedContent(Content cObject){
		boolean found = false;
		for (int i = 0;i < creationsOwned.size();i ++){
			if (cObject.equals(creationsOwned.get(i))){
				creationsOwned.set(i, null);
				found = true;
			}
			if (creationsOwned.get(i) != null && found){
				creationsOwned.set(i-1, creationsOwned.get(i));
				creationsOwned.set(i, null);
			}
		}
		if (found)
			return true;
		else 
			return false;
	}
	
	//Friend methods
	public void sendRequest(User other) {
		if(potentialNumberOfFriends<MAXFRIENDS && other.potentialNumberOfFriends<MAXFRIENDS) {
			other.receivedRequest.add(this);
			addPotentialNumberOfFriends();
			other.addPotentialNumberOfFriends();
		}
	}
	
	public void addPotentialNumberOfFriends() {
		potentialNumberOfFriends ++;
	}
	
	public void subtractPotentialNumberOfFriends() {
		potentialNumberOfFriends --;
	}
	
	public boolean acceptFirstRequest() {
		// if friend list is not full, accept the request, return true
		if(numOfFriends < MAXFRIENDS) {
			addFriend(receivedRequest.poll());
			return true;
		}
		// else return false and does not remove this request
		return false;
	}
	
	public void rejectFirstRequest() {
		// remove the first friend request
		receivedRequest.poll().subtractPotentialNumberOfFriends();
		subtractPotentialNumberOfFriends();
	}
	
	public boolean addFriend(User other){ // "other" comes from the request queue
		if (numOfFriends < MAXFRIENDS){
			friendsList[numOfFriends] = other;
			numOfFriends ++;
			
			(other.getFriendsList())[other.getNumOfFriends()] = this;
			other.setNumOfFriends(other.getNumOfFriends()+1);
			return true;
		}
		else
			return false;
	}
	
}
