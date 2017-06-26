package model.libraryModel;

import java.util.Arrays;

/**
 * Created by czyczk on 2017-6-23.
 */
public class Movie extends FileAssociatedItem {
    public Movie() {
        setMediaType(MediaTypeEnum.MOVIE);
    }

    private Integer imdb;
    private Integer releaseYear;
    private Integer duration;
    private String plot;
    private String thumbUrl;
    private Double rating;
    private String[] genre;
    private String[] director;

    public Integer getImdb() {
        return imdb;
    }
    public void setImdb(int imdb) {
        this.imdb = imdb;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public String[] getGenre() {
        return genre;
    }
    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public String[] getDirector() {
        return director;
    }
    public void setDirector(String[] director) {
        this.director = director;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie {").append('\n');
        sb.append("imdb=").append(imdb);
        sb.append(", ownerId=").append(getOwnerId());
        sb.append(", title=").append(getTitle());
        sb.append(", SHA256=").append(getSHA256());
        sb.append(", size=").append(getSize());
        sb.append('\n');
        sb.append("releaseYear=").append(releaseYear);
        sb.append(", duration=").append(duration);
        sb.append(", plot='").append(plot).append('\'');
        sb.append(", thumbUrl='").append(thumbUrl).append('\'');
        sb.append(", rating=").append(rating);
        sb.append('\n');
        sb.append("movieGenre=").append(Arrays.toString(genre));
        sb.append(", movieDirector=").append(Arrays.toString(director));
        sb.append("\n}");
        return sb.toString();
    }

    @Override
    // Check if the sample item contains the deterministic characteristics of the target item.
    public boolean isDeterministic() {
        if (getOwnerId() == null) {
            return false;
        }
        if (getImdb() == null) {
            if (getSHA256() == null || getSize() == null) {
                return false;
            }
        }
        return true;
    }

}
