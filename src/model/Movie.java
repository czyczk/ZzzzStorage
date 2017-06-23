package model;

/**
 * Created by czyczk on 2017-6-23.
 */
public class Movie extends FileAssociatedItem {
    private Integer imdb;
    private Integer releaseYear;
    private Integer duration;
    private String plot;
    private String thumbUrl;
    private Double rating;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("imdb=").append(imdb);
        sb.append(", releaseYear=").append(releaseYear);
        sb.append(", duration=").append(duration);
        sb.append(", plot='").append(plot).append('\'');
        sb.append(", thumbUrl='").append(thumbUrl).append('\'');
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }
}
