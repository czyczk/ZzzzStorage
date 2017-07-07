package model.libraryModel;

/**
 * Created by czycz on 2017-7-1.
 */
public class Episode extends FileAssociatedItem {
    public Episode() {
        setMediaType(MediaTypeEnum.EPISODE);
    }

    private TVShow tvShow;
    private Integer episodeNo;
    private Integer runtime;
    private String storyline;
    private String thumbUrl;
    private Double rating;

    public TVShow getTvShow() {
        return tvShow;
    }
    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    public Integer getEpisodeNo() {
        return episodeNo;
    }
    public void setEpisodeNo(int episodeNo) {
        this.episodeNo = episodeNo;
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

    public String getStoryline() {
        return storyline;
    }
    public void setStoryline(String storyline) {
        if (storyline == null || storyline.length() == 0)
            this.storyline = null;
        else
            this.storyline = storyline;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }
    public void setThumbUrl(String thumbUrl) {
        if (thumbUrl == null || thumbUrl.length() == 0)
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

    @Override
    public boolean isDeterministic() {
        if (tvShow == null || !tvShow.isDeterministic()) return false;
        if (episodeNo == null) {
            if (getSHA256() == null || getSize() == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Episode {\n");
        sb.append("tvShow=").append(tvShow);
        sb.append("\nepisodeNo=").append(episodeNo);
        sb.append(", runtime=").append(runtime);
        sb.append(", storyline='").append(storyline).append('\'');
        sb.append(", thumbUrl='").append(thumbUrl).append('\'');
        sb.append(", rating=").append(rating);
        sb.append("\n}");
        return sb.toString();
    }
}
