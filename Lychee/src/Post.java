import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Post implements Serializable {

	private static final long serialVersionUID = -5908130294368526905L;
	private User postedBy;
	private String content;
	private String time;
	
	// constructor
	public Post(User postedBy, String content) {
		this.postedBy = postedBy;
		this.content = content;
		
		Calendar cal = Calendar.getInstance();										// gets the time at the moment
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm MMM d, yyyy");			// format of time
        
        time = sdf.format(cal.getTime()) ;											// time as a string
		
	}
	
	public Post(User postedBy, String time, String content) {						// constructs the post given the time
		this.time = time;
		this.postedBy = postedBy;
		this.content = content;
	}
	
	// accessors
	public User getPostedBy() {
		return postedBy;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getContent() {
		return content;
	}

	public String toString() {
		return "\"" + content + "\"\n\tposted by " + postedBy.getUsername() + " at " + time;
	}
	
}
