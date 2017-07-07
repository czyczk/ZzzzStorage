package model.libraryModel;

import model.libraryModel.LibraryItem;

import java.util.Arrays;

/**
 * Created by czyczk on 2017-7-1.
 */
public class TVShow extends LibraryItem {
    public TVShow() {
        setMediaType(MediaTypeEnum.TV_SHOW);
    }

    private Integer imdb;
    private Integer season;
    private Integer releaseYear;
    private Integer runtime;
    private String plot;
    private String thumbUrl;
    private Double rating;
    private String[] genre;

    public Integer getImdb() {
        return imdb;
    }
    public void setImdb(int imdb) {
        this.imdb = imdb;
    }

    public Integer getSeason() {
        return season;
    }
    public void setSeason(int season) {
        this.season = season;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        if (releaseYear == 0)
            this.releaseYear = null;
        else
            this.releaseYear = releaseYear;
    }

    public Integer getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        if (runtime == 0)
            this.runtime = null;
        else
            this.runtime = runtime;
    }

    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        if (plot == null || plot.trim().isEmpty())
            this.plot = null;
        else
            this.plot = plot;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
    public void setThumbUrl(String thumbUrl) {
        if (thumbUrl == null || thumbUrl.trim().isEmpty())
            this.thumbUrl = null;
        else
            this.thumbUrl = thumbUrl;
    }

    public Double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        if (rating == 0.0)
            this.rating = null;
        else
            this.rating = rating;
    }

    public String[] getGenre() {
        return genre;
    }
    public void setGenre(String[] genre) {
        if (genre == null || genre.length == 0)
            this.genre = null;
        else
            this.genre = genre;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TVShow {\n");
        sb.append("imdb=").append(imdb);
        sb.append(", title=").append(getTitle());
        sb.append(", season=").append(season);
        sb.append(", ownerId=").append(getOwnerId());
        sb.append("\nreleaseYear=").append(releaseYear);
        sb.append(", runtime=").append(runtime);
        sb.append(", plot='").append(plot).append('\'');
        sb.append(", thumbUrl='").append(thumbUrl).append('\'');
        sb.append(", rating=").append(rating);
        sb.append("\ngenre=").append(Arrays.toString(genre));
        sb.append("\n}");
        return sb.toString();
    }

    public boolean isDeterministic() {
        return !(getOwnerId() == null || imdb == null || season == null);
    }
}
