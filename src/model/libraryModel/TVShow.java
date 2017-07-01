package model.libraryModel;

import model.libraryModel.LibraryItem;

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
            this.rating = rating;
        else
            this.rating = rating;
    }
}
