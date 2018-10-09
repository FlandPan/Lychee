import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Chat implements Serializable{
	
	private static final long serialVersionUID = 4372794019198527037L;
	
	private LinkedList<Message> log;
	private User user1;
	private User user2;
	
	private final int SIZE_LIMIT = 11;
	
	class Message implements Serializable {
		private static final long serialVersionUID = -4441643472653996726L;
		private String log;
		private String textPerson;
		private String time;
		
		public Message(String log, String person, String time) {
			this.log = log;
			textPerson = person;
			this.time = time;
		}
		
		public String getLog() {
			return log;
		}
		
		public String getTextPerson() {
			return textPerson;
		}
		
		public String getTime() {
			return time;
		}
	}
	
	//Constructor
	public Chat(User me, User other){
		log = new LinkedList<Message>();
		user1 = me;
		user2 = other;
	}
	
	public void addToLog(String newText, User cur){
		String timeStamp = new SimpleDateFormat("yyyy MM dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		log.add(new Message(newText, cur.getUsername(), timeStamp));
	}
	
	public User getUser1() {
		return user1;
	}
	
	public User getUser2() {
		return user2;
	}
	
	public LinkedList<Message> getLog() {
		return log;
	}
	
	public void removeExtra() {
		while(log.size() > SIZE_LIMIT)
			log.poll();
	}
	
}
