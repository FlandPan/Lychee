import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

public class Store implements Serializable {

	private static final long serialVersionUID = -4257152626569315144L;
	private ArrayList<Content> creations;

	// instantiates the arraylist of content
	public Store(Content[] contentList) {
		creations = new ArrayList<Content>();
		addData(contentList);
	}

	public void addData(Content[] contentList) {
		for (int i = 0; i < contentList.length; i++) {
			creations.add(contentList[i]);
		}
	}
	
	// accessor
	public ArrayList<Content> getCreations(){
		return creations;
	}
	
	// adds a content object into the list of creations
	public void addContent(Content c) {
		creations.add(c);
	}

	// removes a content object from the list of creations
	public void removeContent(Content c) {
		creations.remove(c); // removes c if the object already exists in list
	}

	// searches for contents with given name, returns an array with content that
	// matches the name
	// uses binary search
	public TreeSet<Content> searchBar(String s){
		Content[] searchByName = searchContent(s);
		Content[] searchByTag = show(s);
		TreeSet<Content> set = new TreeSet<Content>();
		for(int i = 0; i < searchByName.length; i++) {
			set.add(searchByName[i]);
		}
		for(int i = 0; i < searchByTag.length; i++) {
			set.add(searchByTag[i]);
		}
		
		return set;
	}
	
	public Content[] searchContent(String name) {
		Content[] matchCreations; // array of creations that match the name

		int matches = 0; // counter for num of creations in store list that matches the name
		Content[] list = sortByName(true); // the store list sorted alphabetically
		int bottomLimit = 0; // bottom limit of creations that matches the name
		int topLimit = list.length - 1; // upper limit of creations that matches the name

		int top = list.length - 1;
		int bot = 0;
		int mid;

		while (bot <= top) {
			mid = (top + bot) / 2;

			// upon finding a creation that matches the name, iterate up and down the
			// indices around it
			// to look for more matches
			if (name.equalsIgnoreCase(list[mid].getName())) {
				matches++;
				for (int i = mid - 1; i >= 0 && list[i].getName().equalsIgnoreCase(name); i--) {
					matches++;
					bottomLimit = i;
				}

				for (int i = mid + 1; i < list.length && list[i].getName().equalsIgnoreCase(name); i++) {
					matches++;
					topLimit = i;
				}
			} else if (name.compareToIgnoreCase(list[mid].getName()) > 0)
				bot = mid + 1;
			else
				top = mid - 1;
		}

		// create the array with right size
		matchCreations = new Content[matches];

		// insert creations that matches the name from bot limit to upper limit
		for (int i = 0; i < matches; i++) {
			matchCreations[i] = list[bottomLimit + i];
		}
		return matchCreations;
	}

	// returns an array of all the current contents in the list
	private Content[] currentList() {
		Content[] list = new Content[creations.size()];

		for (int i = 0; i < list.length; i++) {
			list[i] = creations.get(i);
		}

		return list;
	}

	// sort contents in the list in alphabetic or reverse alphabetic order and
	// returns sorted array
	// uses insertion sort
	public Content[] sortByName(boolean alpha) {
		Content[] sortArray = currentList();

		Content temp;

		// sets temp to current index object, searches array before to insert
		for (int i = 1; i < sortArray.length; i++) {
			temp = sortArray[i];
			int index = i - 1;

			if (alpha) {
				while (index >= 0 && temp.getName().compareToIgnoreCase(sortArray[index].getName()) < 0) {
					sortArray[index + 1] = sortArray[index];
					index--;
				}
			} else {
				while (index >= 0 && temp.getName().compareToIgnoreCase(sortArray[index].getName()) > 0) {
					sortArray[index + 1] = sortArray[index];
					index--;
				}
			}
			sortArray[index + 1] = temp;

		}

		return sortArray;
	}

	// sort contents in the list from highest price to lowest or vice versa and
	// returns sorted array
	// uses selection sort
	public Content[] sortByCost(boolean highToLow) {
		Content[] sortArray = new Content[creations.size()];

		// looks for creation objects that costs more/less and swaps accordingly
		for (int i = 0; i < sortArray.length; i++) {
			int index = i;
			for (int j = i + 1; j < sortArray.length; j++) {
				if (highToLow) {
					if (sortArray[j].getCost() > sortArray[index].getCost())
						index = j;
				} else {
					if (sortArray[j].getCost() < sortArray[index].getCost())
						index = j;
				}
			}

			Content temp = sortArray[index];
			sortArray[index] = sortArray[i];
			sortArray[i] = temp;
		}

		return sortArray;
	}

	// sort contents in the list from highest rating to lowest or vice versa and
	// returns sorted array
	// uses bubble sort
	public Content[] sortByRating(boolean highToLow) {
		Content[] sortArray = new Content[creations.size()];

		boolean sorted = false;

		// bubbles lowest/highest rating creations to the end of the list accordingly
		for (int i = sortArray.length - 1; i >= 0 && !sorted; i--) {
			sorted = true;

			for (int j = 0; j < i; j++) {
				if (highToLow) {
					if (sortArray[j].getRating() < sortArray[j + 1].getRating()) {
						sorted = false;
						Content temp = sortArray[j];
						sortArray[j] = sortArray[j + 1];
						sortArray[j + 1] = temp;
					}
				} else {
					if (sortArray[j].getRating() > sortArray[j + 1].getRating()) {
						sorted = false;
						Content temp = sortArray[j];
						sortArray[j] = sortArray[j + 1];
						sortArray[j + 1] = temp;
					}
				}
			}
		}

		return sortArray;
	}

	// sort contents in the list from most purchases to least or vice versa and
	// returns sorted array
	public Content[] sortByPurchases(boolean mostToLeast) {
		Content[] sortArray = new Content[creations.size()];

		boolean sorted = false;

		// bubbles least/most purchased creations to the end of the list accordingly
		for (int i = sortArray.length - 1; i >= 0 && !sorted; i--) {
			sorted = true;

			for (int j = 0; j < i; j++) {
				if (mostToLeast) {
					if (sortArray[j].getNumOfPurchases() < sortArray[j + 1].getNumOfPurchases()) {
						sorted = false;
						Content temp = sortArray[j];
						sortArray[j] = sortArray[j + 1];
						sortArray[j + 1] = temp;
					}
				} else {
					if (sortArray[j].getNumOfPurchases() > sortArray[j + 1].getNumOfPurchases()) {
						sorted = false;
						Content temp = sortArray[j];
						sortArray[j] = sortArray[j + 1];
						sortArray[j + 1] = temp;
					}
				}
			}
		}

		return sortArray;
	}

	// sort contents in the list from most reviews to least or vice versa and
	// returns sorted array
	public Content[] sortByReviews(boolean mostToLeast) {
		Content[] sortArray = new Content[creations.size()];

		boolean sorted = false;

		// bubbles least/most reviwed creations to the end of the list accordingly
		for (int i = sortArray.length - 1; i >= 0 && !sorted; i--) {
			sorted = true;

			for (int j = 0; j < i; j++) {
				if (mostToLeast) {
					if (sortArray[j].getNumOfReviews() < sortArray[j + 1].getNumOfReviews()) {
						sorted = false;
						Content temp = sortArray[j];
						sortArray[j] = sortArray[j + 1];
						sortArray[j + 1] = temp;
					}
				} else {
					if (sortArray[j].getNumOfReviews() > sortArray[j + 1].getNumOfReviews()) {
						sorted = false;
						Content temp = sortArray[j];
						sortArray[j] = sortArray[j + 1];
						sortArray[j + 1] = temp;
					}
				}
			}
		}

		return sortArray;
	}
// returns an array of contents with the given tag
	public Content[] show(String tag) {
		Content[] includeGivenTag;
		int listSize = 0;

		// determines the size of array
		for (int i = 0; i < creations.size(); i++) {
			boolean included = false;
			for (int j = 0; j < creations.get(i).getTags().size() && !included; j++) {
				if (tag.equalsIgnoreCase(creations.get(i).getTags().get(j))) {
					listSize++;
					included = true;
				}
			}
		}

		// creates array with the right size
		includeGivenTag = new Content[listSize];
		int index = 0;

		// fills the array with the right content objects from the original store list
		for (int i = 0; i < creations.size(); i++) {
			boolean included = false;
			for (int j = 0; j < creations.get(i).getTags().size() && !included; j++) {
				if (tag.equalsIgnoreCase(creations.get(i).getTags().get(j))) {
					includeGivenTag[index] = creations.get(i);
					index++;
					included = true;
				}
			}
		}

		return includeGivenTag;
	}
	
	public String toString() {
		String result = "Contents: \n";
		for(int i = 0; i < creations.size(); i++) {
			result += creations.get(i) + "\n";
		}
		return result;
	}
}