import java.util.*;

public class MovieRunnerWithFilters {

    public void printAverageRatings() {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        int minimalRaters = 1;
        ArrayList<Rating> ratings = tr.getAverageRatings(minimalRaters);
        Collections.sort(ratings);
        System.out.println("found " + ratings.size() + " movies");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }

    public void printAverageRatingsByYear() {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        int minimalRaters = 1;
        int year = 2000;
        Filter f = new YearAfterFilter(year);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, f);
        Collections.sort(ratings);
        System.out.println("found " + ratings.size() + " movies");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getYear(r.getItem()) + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }

    public void printAverageRatingsByGenre() {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        int minimalRaters = 1;
        String genre = "Crime";
        Filter f = new GenreFilter(genre);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, f);
        Collections.sort(ratings);
        System.out.println("found " + ratings.size() + " movies");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
        }
    }

    public void printAverageRatingsByMinutes() {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        int minimalRaters = 1;
        int minMin = 110;
        int maxMin = 170;
        Filter f = new MinutesFilter(minMin, maxMin);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, f);
        Collections.sort(ratings);
        System.out.println("found " + ratings.size() + " movies");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " Time: " + MovieDatabase.getMinutes(r.getItem()) + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }

    public void printAverageRatingsByDirectors() {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        int minimalRaters = 1;
        String directors = "Charles Chaplin,Michael Mann,Spike Jonze";
        Filter f = new DirectorsFilter(directors);
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, f);
        Collections.sort(ratings);
        System.out.println("found " + ratings.size() + " movies");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
            System.out.println("    " + MovieDatabase.getDirector(r.getItem()));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        int minimalRaters = 1;
        int year = 1980;
        String genre = "Romance";
        
        AllFilters all = new AllFilters();
        all.addFilter(new YearAfterFilter(year));
        all.addFilter(new GenreFilter(genre));
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, all);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movie matched");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getYear(r.getItem()) + " " + MovieDatabase.getTitle(r.getItem()));
            System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
        }
    }

    public void printAverageRatingsByDirectorsAndMinutes() {
        ThirdRatings tr = new ThirdRatings("ratings_short.csv");
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        MovieDatabase.initialize("ratedmovies_short.csv");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        int minimalRaters = 1;
        int minMin = 30;
        int maxMin = 170;
        String directors = "Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola";
        
        AllFilters all = new AllFilters();
        all.addFilter(new MinutesFilter(minMin, maxMin));
        all.addFilter(new DirectorsFilter(directors));
        
        ArrayList<Rating> ratings = tr.getAverageRatingsByFilter(minimalRaters, all);
        Collections.sort(ratings);
        System.out.println(ratings.size() + " movies matched");
        
        for (Rating r : ratings) {
            System.out.println(r.getValue() + " Time: " + MovieDatabase.getMinutes(r.getItem()) + " " + MovieDatabase.getTitle(r.getItem()));
            System.out.println("    " + MovieDatabase.getDirector(r.getItem()));
        }
    }

    public static void main(String[] args) {
        MovieRunnerWithFilters runner = new MovieRunnerWithFilters();
        System.out.println("--- printAverageRatings ---");
        runner.printAverageRatings();
        System.out.println("\n--- printAverageRatingsByYear ---");
        runner.printAverageRatingsByYear();
        System.out.println("\n--- printAverageRatingsByGenre ---");
        runner.printAverageRatingsByGenre();
        System.out.println("\n--- printAverageRatingsByMinutes ---");
        runner.printAverageRatingsByMinutes();
        System.out.println("\n--- printAverageRatingsByDirectors ---");
        runner.printAverageRatingsByDirectors();
        System.out.println("\n--- printAverageRatingsByYearAfterAndGenre ---");
        runner.printAverageRatingsByYearAfterAndGenre();
        System.out.println("\n--- printAverageRatingsByDirectorsAndMinutes ---");
        runner.printAverageRatingsByDirectorsAndMinutes();
    }
}
