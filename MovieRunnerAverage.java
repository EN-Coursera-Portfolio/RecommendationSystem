import java.util.*;

public class MovieRunnerAverage {
    
    public void printAverageRatings() {
        SecondRatings sr = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
        // SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        System.out.println("Number of movies: " + sr.getMovieSize());
        System.out.println("Number of raters: " + sr.getRaterSize());
        
        int minimalRaters = 3;
        ArrayList<Rating> ratings = sr.getAverageRatings(minimalRaters);
        Collections.sort(ratings);
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
        }
        System.out.println("Total movies with at least " + minimalRaters + " ratings: " + ratings.size());
    }
    
    public void getAverageRatingOneMovie() {
        SecondRatings sr = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
        String title = "The Godfather";
        String id = sr.getID(title);
        
        if (!id.equals("NO SUCH TITLE")) {
            double avg = 0;
            for (Rating r : sr.getAverageRatings(1)) {
                if (r.getItem().equals(id)) {
                    avg = r.getValue();
                }
            }
            System.out.println("Average rating for " + title + " is: " + avg);
        } else {
            System.out.println("Title not found.");
        }
    }

    public static void main(String[] args) {
        MovieRunnerAverage runner = new MovieRunnerAverage();
        System.out.println("--- printAverageRatings ---");
        runner.printAverageRatings();
        System.out.println("\n--- getAverageRatingOneMovie ---");
        runner.getAverageRatingOneMovie();
    }
}
