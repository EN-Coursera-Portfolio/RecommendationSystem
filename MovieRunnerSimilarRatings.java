import java.util.*;

public class MovieRunnerSimilarRatings {

    public void printAverageRatings() {
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        FourthRatings fr = new FourthRatings();
        int minimalRaters = 1;
        ArrayList<Rating> ratings = fr.getAverageRatings(minimalRaters);
        Collections.sort(ratings);
        System.out.println("found " + ratings.size() + " movies");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        RaterDatabase.initialize("ratings_short.csv");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        FourthRatings fr = new FourthRatings();
        int minimalRaters = 1;
        int year = 1980;
        String genre = "Romance";
        
        AllFilters all = new AllFilters();
        all.addFilter(new YearAfterFilter(year));
        all.addFilter(new GenreFilter(genre));
        
        ArrayList<Rating> ratings = fr.getAverageRatingsByFilter(minimalRaters, all);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movie matched");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getYear(r.getItem()) + " " + MovieDatabase.getTitle(r.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
        }
    }

    public void printSimilarRatings() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> recs = fr.getSimilarRatings("65", 20, 5);
        if (recs.size() > 0) {
            System.out.println("Top rated average is: " + MovieDatabase.getTitle(recs.get(0).getItem()));
        }
    }

    public void printSimilarRatingsByGenre() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings fr = new FourthRatings();
        Filter f = new GenreFilter("Action");
        ArrayList<Rating> recs = fr.getSimilarRatingsByFilter("65", 20, 5, f);
        if (recs.size() > 0) {
            System.out.println("Top rated average by genre is: " + MovieDatabase.getTitle(recs.get(0).getItem()));
        }
    }

    public void printSimilarRatingsByDirector() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings fr = new FourthRatings();
        Filter f = new DirectorsFilter("Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone");
        ArrayList<Rating> recs = fr.getSimilarRatingsByFilter("1034", 10, 3, f);
        if (recs.size() > 0) {
            System.out.println("Top rated average by director is: " + MovieDatabase.getTitle(recs.get(0).getItem()));
        }
    }

    public void printSimilarRatingsByGenreAndMinutes() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings fr = new FourthRatings();
        AllFilters all = new AllFilters();
        all.addFilter(new GenreFilter("Adventure"));
        all.addFilter(new MinutesFilter(100, 200));
        ArrayList<Rating> recs = fr.getSimilarRatingsByFilter("65", 10, 5, all);
        if (recs.size() > 0) {
            System.out.println("Top rated average by genre/minutes is: " + MovieDatabase.getTitle(recs.get(0).getItem()));
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes() {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings fr = new FourthRatings();
        AllFilters all = new AllFilters();
        all.addFilter(new YearAfterFilter(2000));
        all.addFilter(new MinutesFilter(80, 100));
        ArrayList<Rating> recs = fr.getSimilarRatingsByFilter("65", 10, 5, all);
        if (recs.size() > 0) {
            System.out.println("Top rated average by year/minutes is: " + MovieDatabase.getTitle(recs.get(0).getItem()));
        }
    }

    public static void main(String[] args) {
        MovieRunnerSimilarRatings runner = new MovieRunnerSimilarRatings();
        // System.out.println("--- printAverageRatings ---");
        // runner.printAverageRatings();
        // System.out.println("\n--- printAverageRatingsByYearAfterAndGenre ---");
        // runner.printAverageRatingsByYearAfterAndGenre();
        System.out.println("\n--- printSimilarRatings ---");
        runner.printSimilarRatings();
        System.out.println("\n--- printSimilarRatingsByGenre ---");
        runner.printSimilarRatingsByGenre();
        System.out.println("\n--- printSimilarRatingsByDirector ---");
        runner.printSimilarRatingsByDirector();
        System.out.println("\n--- printSimilarRatingsByGenreAndMinutes ---");
        runner.printSimilarRatingsByGenreAndMinutes();
        System.out.println("\n--- printSimilarRatingsByYearAfterAndMinutes ---");
        runner.printSimilarRatingsByYearAfterAndMinutes();
    }
}
