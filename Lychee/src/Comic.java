public class Comic extends Content {
	private static final long serialVersionUID = -6535723056677242118L;

	public Comic(String name, User creator, double cost, int age){
		super(name, creator, cost, age);
	}
  
	public String toString(){
		String result = "Comic\n";
		result += "Name: " + name + "\n";
		result += "Creator: " + creator.getUsername() + "\n";
		result += "Cost: " + cost + "\n";
		result += "Date of Release: " + dateOfRelease + "\n";
		result += "Rating: " + rating + "\n";
		result += "Number of Ratings: " + numOfRatings + "\n";
		result += "Number of Purchases: " + numOfPurchases + "\n";
		result += "Number of Reviews: " + reviews.size() + "\n";
		result += "Age Restriction: " + ageRestriction + "\n";
		return result;
	}
}
