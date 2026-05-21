import java.util.*;

public class RecommendationRunner implements Recommender {

    public ArrayList<String> getItemsToRate() {
        ArrayList<String> moviesToRate = new ArrayList<>();
        ArrayList<String> allMovies = MovieDatabase.filterBy(new TrueFilter());
        
        // Select 15 movies spread systematically across the list to ensure diversity
        int size = allMovies.size();
        if (size > 0) {
            int numToRecommend = Math.min(size, 15);
            int step = size / numToRecommend;
            for (int i = 0; i < numToRecommend; i++) {
                moviesToRate.add(allMovies.get(i * step));
            }
        }
        return moviesToRate;
    }

    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        
        // Multi-stage fallback strategy to guarantee we get recommendations
        ArrayList<Rating> recs = fr.getSimilarRatings(webRaterID, 20, 5);
        
        if (recs.size() == 0) {
            recs = fr.getSimilarRatings(webRaterID, 10, 2);
        }
        
        if (recs.size() == 0) {
            recs = fr.getSimilarRatings(webRaterID, 5, 1);
        }
        
        if (recs.size() == 0) {
            // Fallback to general popular movies if similarity matches are empty
            recs = fr.getAverageRatings(1);
            Collections.sort(recs, Collections.reverseOrder());
        }
        
        // Filter out movies already rated by the user
        Rater userRater = RaterDatabase.getRater(webRaterID);
        ArrayList<Rating> filteredRecs = new ArrayList<>();
        for (Rating r : recs) {
            String mID = r.getItem();
            if (userRater == null || !userRater.hasRating(mID)) {
                filteredRecs.add(r);
            }
        }
        
        // Print recommendations page
        System.out.println("<div class=\"recommender-container\">");
        
        // Premium Dark CSS styling
        System.out.println("<style>");
        System.out.println("  .recommender-container {");
        System.out.println("    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;");
        System.out.println("    color: #f3f4f6;");
        System.out.println("    background: linear-gradient(135deg, #111827 0%, #1f2937 100%);");
        System.out.println("    padding: 30px;");
        System.out.println("    border-radius: 16px;");
        System.out.println("    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);");
        System.out.println("    margin: 20px auto;");
        System.out.println("    max-width: 960px;");
        System.out.println("  }");
        System.out.println("  .recommender-title {");
        System.out.println("    font-size: 28px;");
        System.out.println("    font-weight: 800;");
        System.out.println("    margin-bottom: 25px;");
        System.out.println("    text-align: center;");
        System.out.println("    letter-spacing: -0.025em;");
        System.out.println("    background: linear-gradient(90deg, #60a5fa, #a78bfa);");
        System.out.println("    -webkit-background-clip: text;");
        System.out.println("    -webkit-text-fill-color: transparent;");
        System.out.println("  }");
        System.out.println("  .recommender-table {");
        System.out.println("    width: 100%;");
        System.out.println("    border-collapse: separate;");
        System.out.println("    border-spacing: 0;");
        System.out.println("    margin-top: 15px;");
        System.out.println("    border-radius: 12px;");
        System.out.println("    overflow: hidden;");
        System.out.println("    border: 1px solid #374151;");
        System.out.println("  }");
        System.out.println("  .recommender-table th {");
        System.out.println("    background-color: #1f2937;");
        System.out.println("    color: #9ca3af;");
        System.out.println("    font-weight: 600;");
        System.out.println("    text-transform: uppercase;");
        System.out.println("    font-size: 11px;");
        System.out.println("    letter-spacing: 0.05em;");
        System.out.println("    padding: 14px 16px;");
        System.out.println("    border-bottom: 1px solid #374151;");
        System.out.println("    text-align: left;");
        System.out.println("  }");
        System.out.println("  .recommender-table td {");
        System.out.println("    padding: 16px;");
        System.out.println("    border-bottom: 1px solid #374151;");
        System.out.println("    vertical-align: middle;");
        System.out.println("    background-color: #111827;");
        System.out.println("    transition: background-color 0.2s ease;");
        System.out.println("  }");
        System.out.println("  .recommender-table tr:last-child td {");
        System.out.println("    border-bottom: none;");
        System.out.println("  }");
        System.out.println("  .recommender-table tr:hover td {");
        System.out.println("    background-color: #1f2937;");
        System.out.println("  }");
        System.out.println("  .poster-container {");
        System.out.println("    display: flex;");
        System.out.println("    justify-content: center;");
        System.out.println("    align-items: center;");
        System.out.println("  }");
        System.out.println("  .movie-poster {");
        System.out.println("    width: 60px;");
        System.out.println("    height: auto;");
        System.out.println("    border-radius: 6px;");
        System.out.println("    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.4);");
        System.out.println("    transition: transform 0.2s ease;");
        System.out.println("  }");
        System.out.println("  .movie-poster:hover {");
        System.out.println("    transform: scale(1.1);");
        System.out.println("  }");
        System.out.println("  .no-poster {");
        System.out.println("    width: 60px;");
        System.out.println("    height: 90px;");
        System.out.println("    background: #374151;");
        System.out.println("    color: #9ca3af;");
        System.out.println("    border-radius: 6px;");
        System.out.println("    display: flex;");
        System.out.println("    align-items: center;");
        System.out.println("    justify-content: center;");
        System.out.println("    font-size: 10px;");
        System.out.println("    text-align: center;");
        System.out.println("    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.4);");
        System.out.println("  }");
        System.out.println("  .movie-title {");
        System.out.println("    font-size: 16px;");
        System.out.println("    font-weight: 700;");
        System.out.println("    color: #ffffff;");
        System.out.println("    margin-bottom: 4px;");
        System.out.println("  }");
        System.out.println("  .movie-genres {");
        System.out.println("    display: flex;");
        System.out.println("    flex-wrap: wrap;");
        System.out.println("    gap: 4px;");
        System.out.println("    margin-top: 4px;");
        System.out.println("  }");
        System.out.println("  .genre-tag {");
        System.out.println("    background: #374151;");
        System.out.println("    color: #e5e7eb;");
        System.out.println("    font-size: 11px;");
        System.out.println("    padding: 2px 8px;");
        System.out.println("    border-radius: 12px;");
        System.out.println("  }");
        System.out.println("  .movie-details {");
        System.out.println("    color: #d1d5db;");
        System.out.println("    font-size: 14px;");
        System.out.println("  }");
        System.out.println("  .movie-director {");
        System.out.println("    color: #9ca3af;");
        System.out.println("    font-size: 13px;");
        System.out.println("  }");
        System.out.println("  .score-badge {");
        System.out.println("    background: rgba(167, 139, 250, 0.15);");
        System.out.println("    color: #c084fc;");
        System.out.println("    border: 1px solid rgba(167, 139, 250, 0.3);");
        System.out.println("    padding: 4px 10px;");
        System.out.println("    border-radius: 20px;");
        System.out.println("    font-size: 13px;");
        System.out.println("    font-weight: 600;");
        System.out.println("    display: inline-block;");
        System.out.println("  }");
        System.out.println("  .no-recs {");
        System.out.println("    text-align: center;");
        System.out.println("    padding: 40px 20px;");
        System.out.println("    color: #9ca3af;");
        System.out.println("  }");
        System.out.println("  .no-recs h3 {");
        System.out.println("    color: #ffffff;");
        System.out.println("    margin-bottom: 12px;");
        System.out.println("    font-size: 20px;");
        System.out.println("  }");
        System.out.println("</style>");
        
        if (filteredRecs.size() == 0) {
            System.out.println("<div class=\"no-recs\">");
            System.out.println("  <h3>No Recommendations Found</h3>");
            System.out.println("  <p>We couldn't find any movie recommendations based on your current ratings. Please try rating more movies or adjusting your ratings!</p>");
            System.out.println("</div>");
        } else {
            System.out.println("<div class=\"recommender-title\">Your Personalized Movie Recommendations</div>");
            System.out.println("<table class=\"recommender-table\">");
            System.out.println("  <thead>");
            System.out.println("    <tr>");
            System.out.println("      <th style=\"width: 80px; text-align: center;\">Poster</th>");
            System.out.println("      <th>Movie Details</th>");
            System.out.println("      <th style=\"width: 120px;\">Run Time</th>");
            System.out.println("      <th style=\"width: 100px;\">Match Score</th>");
            System.out.println("    </tr>");
            System.out.println("  </thead>");
            System.out.println("  <tbody>");
            
            int limit = Math.min(15, filteredRecs.size());
            for (int i = 0; i < limit; i++) {
                Rating r = filteredRecs.get(i);
                String movieID = r.getItem();
                Movie m = MovieDatabase.getMovie(movieID);
                
                System.out.println("    <tr>");
                
                // Poster Column
                System.out.println("      <td class=\"poster-container\">");
                String posterUrl = m.getPoster();
                if (posterUrl != null && !posterUrl.trim().equals("") && !posterUrl.equals("N/A") && posterUrl.startsWith("http")) {
                    System.out.println("        <img class=\"movie-poster\" src=\"" + posterUrl + "\" alt=\"" + m.getTitle() + " Poster\">");
                } else {
                    System.out.println("        <div class=\"no-poster\">No Poster</div>");
                }
                System.out.println("      </td>");
                
                // Movie Details Column
                System.out.println("      <td>");
                System.out.println("        <div class=\"movie-title\">" + m.getTitle() + " (" + m.getYear() + ")</div>");
                System.out.println("        <div class=\"movie-director\">Directed by " + m.getDirector() + "</div>");
                
                // Genres Tags
                System.out.println("        <div class=\"movie-genres\">");
                String genresStr = m.getGenres();
                if (genresStr != null && !genresStr.trim().equals("")) {
                    String[] genres = genresStr.split(",\\s*");
                    for (String genre : genres) {
                        System.out.println("          <span class=\"genre-tag\">" + genre + "</span>");
                    }
                }
                System.out.println("        </div>");
                System.out.println("      </td>");
                
                // Run Time Column
                System.out.println("      <td>");
                System.out.println("        <span class=\"movie-details\">" + m.getMinutes() + " mins</span>");
                System.out.println("      </td>");
                
                // Match Score Column
                System.out.println("      <td>");
                double rawValue = r.getValue();
                String scoreStr;
                if (rawValue >= 0.0) {
                    scoreStr = String.format("%.1f", rawValue);
                } else {
                    scoreStr = "N/A";
                }
                System.out.println("        <span class=\"score-badge\">Match: " + scoreStr + "</span>");
                System.out.println("      </td>");
                
                System.out.println("    </tr>");
            }
            System.out.println("  </tbody>");
            System.out.println("</table>");
        }
        System.out.println("</div>");
    }
}
