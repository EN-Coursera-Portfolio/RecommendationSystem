import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {

    public ArrayList<Movie> loadMovies(String filename) {
        ArrayList<Movie> movies = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        for (CSVRecord rec : fr.getCSVParser()) {
            Movie movie = new Movie(
                rec.get("id"),
                rec.get("title"),
                rec.get("year"),
                rec.get("genre"),
                rec.get("director"),
                rec.get("country"),
                rec.get("poster"),
                Integer.parseInt(rec.get("minutes"))
            );
            movies.add(movie);
        }
        return movies;
    }

    public void testLoadMovies() {
        // String filename = "data/ratedmovies_short.csv";
        String filename = "data/ratedmoviesfull.csv";
        ArrayList<Movie> movies = loadMovies(filename);
        System.out.println("Number of movies: " + movies.size());
        
        int comedyCount = 0;
        int longMoviesCount = 0;
        HashMap<String, Integer> directorCounts = new HashMap<>();

        for (Movie m : movies) {
            if (m.getGenres().contains("Comedy")) {
                comedyCount++;
            }
            if (m.getMinutes() > 150) {
                longMoviesCount++;
            }
            String[] directors = m.getDirector().split(",");
            for (String dir : directors) {
                dir = dir.trim();
                directorCounts.put(dir, directorCounts.getOrDefault(dir, 0) + 1);
            }
        }
        
        System.out.println("Number of Comedy movies: " + comedyCount);
        System.out.println("Number of movies > 150 min: " + longMoviesCount);

        int maxMovies = 0;
        for (int count : directorCounts.values()) {
            if (count > maxMovies) {
                maxMovies = count;
            }
        }
        
        ArrayList<String> maxDirectors = new ArrayList<>();
        for (String dir : directorCounts.keySet()) {
            if (directorCounts.get(dir) == maxMovies) {
                maxDirectors.add(dir);
            }
        }
        
        System.out.println("Maximum number of movies by any director: " + maxMovies);
        System.out.println("Directors with max movies: " + maxDirectors);
    }

    public ArrayList<Rater> loadRaters(String filename) {
        ArrayList<Rater> raters = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        
        for (CSVRecord rec : fr.getCSVParser()) {
            String rater_id = rec.get("rater_id");
            String movie_id = rec.get("movie_id");
            double rating = Double.parseDouble(rec.get("rating"));

            Rater existingRater = null;
            for (Rater r : raters) {
                if (r.getID().equals(rater_id)) {
                    existingRater = r;
                    break;
                }
            }

            if (existingRater == null) {
                Rater newRater = new EfficientRater(rater_id);
                newRater.addRating(movie_id, rating);
                raters.add(newRater);
            } else {
                existingRater.addRating(movie_id, rating);
            }
        }
        return raters;
    }

    public void testLoadRaters() {
        // String filename = "data/ratings_short.csv";
        String filename = "data/ratings.csv";
        ArrayList<Rater> raters = loadRaters(filename);
        System.out.println("Total number of raters: " + raters.size());
        
        String specificRaterId = "193";
        for (Rater r : raters) {
            if (r.getID().equals(specificRaterId)) {
                System.out.println("Number of ratings for rater " + specificRaterId + ": " + r.numRatings());
                break;
            }
        }
        
        int maxRatings = 0;
        for (Rater r : raters) {
            if (r.numRatings() > maxRatings) {
                maxRatings = r.numRatings();
            }
        }
        
        ArrayList<String> maxRaters = new ArrayList<>();
        for (Rater r : raters) {
            if (r.numRatings() == maxRatings) {
                maxRaters.add(r.getID());
            }
        }
        System.out.println("Maximum number of ratings: " + maxRatings);
        System.out.println("Raters with max ratings: " + maxRaters);
        
        String specificMovieId = "1798709";
        int movieRatingsCount = 0;
        for (Rater r : raters) {
            if (r.hasRating(specificMovieId)) {
                movieRatingsCount++;
            }
        }
        System.out.println("Number of ratings for movie " + specificMovieId + ": " + movieRatingsCount);
        
        HashSet<String> uniqueMovies = new HashSet<>();
        for (Rater r : raters) {
            ArrayList<String> items = r.getItemsRated();
            uniqueMovies.addAll(items);
        }
        System.out.println("Total number of unique movies rated: " + uniqueMovies.size());
    }

    public static void main(String[] args) {
        FirstRatings fr = new FirstRatings();
        System.out.println("--- testLoadMovies ---");
        fr.testLoadMovies();
        System.out.println("\n--- testLoadRaters ---");
        fr.testLoadRaters();
    }
}
