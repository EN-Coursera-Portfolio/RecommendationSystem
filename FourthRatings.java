import java.util.*;

public class FourthRatings {
    
    private double getAverageByID(String id, int minimalRaters) {
        int raterCount = 0;
        double totalRating = 0.0;
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        for (Rater r : raters) {
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

    private double dotProduct(Rater me, Rater r) {
        double dotProd = 0.0;
        for (String item : me.getItemsRated()) {
            if (r.hasRating(item)) {
                double meRating = me.getRating(item) - 5.0;
                double rRating = r.getRating(item) - 5.0;
                dotProd += (meRating * rRating);
            }
        }
        return dotProd;
    }

    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> list = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        if (me == null) return list;
        
        for (Rater r : RaterDatabase.getRaters()) {
            if (!r.getID().equals(id)) {
                double dotProd = dotProduct(me, r);
                if (dotProd > 0) {
                    list.add(new Rating(r.getID(), dotProd));
                }
            }
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> ret = new ArrayList<>();
        ArrayList<Rating> similarRaters = getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        
        int limit = Math.min(numSimilarRaters, similarRaters.size());
        
        for (String movieID : movies) {
            int count = 0;
            double weightedTotal = 0.0;
            
            for (int k = 0; k < limit; k++) {
                Rating raterRating = similarRaters.get(k);
                String raterID = raterRating.getItem();
                double weight = raterRating.getValue();
                
                Rater r = RaterDatabase.getRater(raterID);
                if (r.hasRating(movieID)) {
                    count++;
                    weightedTotal += weight * r.getRating(movieID);
                }
            }
            
            if (count >= minimalRaters) {
                ret.add(new Rating(movieID, weightedTotal / count));
            }
        }
        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }
}
