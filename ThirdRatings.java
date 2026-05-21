import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters("data/" + ratingsfile);
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        int raterCount = 0;
        double totalRating = 0.0;
        for (Rater r : myRaters) {
            if (r.hasRating(id)) {
                raterCount++;
                totalRating += r.getRating(id);
            }
        }
        if (raterCount >= minimalRaters) {
            return totalRating / raterCount;
        }
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> avgRatings = new ArrayList<>();
        for (String id : movies) {
            double avg = getAverageByID(id, minimalRaters);
            if (avg > 0.0) {
                avgRatings.add(new Rating(id, avg));
            }
        }
        return avgRatings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> avgRatings = new ArrayList<>();
        for (String id : movies) {
            double avg = getAverageByID(id, minimalRaters);
            if (avg > 0.0) {
                avgRatings.add(new Rating(id, avg));
            }
        }
        return avgRatings;
    }
}
