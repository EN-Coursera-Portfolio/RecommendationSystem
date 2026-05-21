import java.util.*;

public class DirectorsFilter implements Filter {
    private String[] myDirectors;
    
    public DirectorsFilter(String directors) {
        myDirectors = directors.split(",");
    }
    
    @Override
    public boolean satisfies(String id) {
        String movieDirectors = MovieDatabase.getDirector(id);
        if (movieDirectors == null) return false;
        
        for (String dir : myDirectors) {
            if (movieDirectors.contains(dir.trim())) {
                return true;
            }
        }
        return false;
    }
}
