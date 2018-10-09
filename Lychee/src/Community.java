import java.io.Serializable;
import java.util.*;


public class Community implements Serializable {

	private static final long serialVersionUID = -5963466139037346047L;
	private ArrayList<Post> posts= new ArrayList<Post>();										// list of posts
	
	public Community(Post[] list) {																// constructor
		
		for(int i = 0 ;i < list.length; i++) {
			posts.add(list[i]);
		}
	}
	public ArrayList<Post> getPosts(){
		return posts;
	}
	public ArrayList<Post> showPosts() {																// shows the posts viewed by the current user
		ArrayList<Post> result = new ArrayList<Post>();
		for(int i = 0; i < Database.getCurrentUser().getNumOfFriends(); i++) {							// loops through all friends
			for(int j = 0; j < posts.size(); j++) {														// loops through all posts
				if(posts.get(j).getPostedBy().equals(Database.getCurrentUser().getFriendsList()[i])){	// adds all friends' posts
					posts.add(posts.get(j));
				}
			}
		}
		return result;																					// return the list of all friends' posts
	}
	
	public void uploadPost(User postedBy, String content) {											// uploads a post
		Post newPost = new Post(postedBy, content);
		posts.add(newPost);
	}

	public void addData(Post[] postList) {															// adds an array of posts to all posts
		for(int i = 0; i < postList.length; i++) {
			posts.add(postList[i]);
		}
	}
	
	public String toString() {
		String result = "Posts: \n";
		for(int i = 0; i < posts.size(); i++) {
			result += posts.get(i) + "\n";
		}
		return result;
	}
}
