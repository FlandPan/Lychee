import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Content implements Serializable {
	private static final long serialVersionUID = -7233660888496940209L;
	protected String name;
	protected double cost;
	protected double rating;
	protected double totalRating;
	protected int numOfRatings;
	protected int numOfPurchases;
	protected ArrayList<String> reviews;
	protected ArrayList<String> tags;
	protected int ageRestriction;
	protected String imageName;
	
	private final String DEFAULT_IMAGE = "NA.jpeg";
	
	protected User creator;
	protected String dateOfRelease;
	
	// constructor
	public Content(String name, User creator, double cost, int age) {
		this.name = name;
		this.creator = creator;
		this.cost = cost;
		imageName = DEFAULT_IMAGE;
		rating = 0;
		totalRating = 0;
		numOfRatings = 0;
		numOfPurchases = 0;
		reviews = new ArrayList<String>();
		tags = new ArrayList<String>();
		ageRestriction = age;	
		Calendar cal = Calendar.getInstance();										// gets the time at the moment
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");			// format of time
        dateOfRelease = sdf.format(cal.getTime()) ;								
	}

	// accessors & mutators
	public String getName() {
		return name;
	}
  
	public double getCost() {
		return cost;
	}

	public double getRating() {
		return rating;
	}

	public double getNumOfPurchases() {
		return numOfPurchases;
	}

	public double getNumOfReviews() {
		return reviews.size();
	}

	public int getAgeRestriction() {
		return ageRestriction;
	}

	public String getImageName() {
		return imageName;
	}
	
	public ArrayList<String> getReviews() {
		return reviews;
	}

	public ArrayList<String> getTags() {
		return tags;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public String getDateOfRelease() {
		return dateOfRelease;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public void setImageName(String name) {
		imageName = name;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setNumOfRatings(int numOfRatings) {
		this.numOfRatings = numOfRatings;
	}

	public void setNumOfPurchases(int numOfPurchases) {
		this.numOfPurchases = numOfPurchases;
	}

	// -------------------------------------------------------------------------------------------------
	private void updateRating() {
		rating = totalRating / numOfRatings;
	}

	// adds a rating to sum of ratings; update rating
	public void addRating(double rating) {
		this.rating += rating;
		numOfRatings++;
		updateRating();
	}

	// subtracts a rating previously made from sum of ratings and adds the new one;
	// update rating
	public void changeRating(double oldRating, double newRating) {
		rating -= oldRating;
		rating += newRating;
		updateRating();
	}

	// adds a review to the arraylist of reviews
	public void addReview(String review) {
		reviews.add(review);
	}

	// adds a tag to the arraylist, returning true; if tag already exists, return
	// false
	public boolean addTag(String tag) {
		for (int i = 0; i < tags.size(); i++) {
			if (tag.equalsIgnoreCase(tags.get(i)))
				return false;
		}

		tags.add(tag);
		return true;
	}

	public abstract String toString();
	
	
}