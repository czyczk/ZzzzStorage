package model.libraryModel;

import java.util.Arrays;

/**
 * Created by czyczk on 2017-6-23.
 */
public class Music extends FileAssociatedItem {
    public Music() {
        setMediaType(MediaTypeEnum.MUSIC);
    }

    private String album;
    private Integer track;
    private Integer duration;
    private Integer rating;
    private String thumbUrl;
    private String[] artist;
    private String[] genre;

    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getTrack() {
        return track;
    }
    public void setTrack(int track) {
        if (track == 0)
            this.track = null;
        else
            this.track = track;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(int rating) {
        if (rating == 0)
            this.rating = null;
        else
            this.rating = rating;
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

    public String[] getArtist() {
        return artist;
    }
    public void setArtist(String[] artist) {
        if (artist == null || artist.length == 0)
            this.artist = null;
        else
            this.artist = artist;
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
        final StringBuilder sb = new StringBuilder("Music {\n");
        sb.append("SHA256=").append(getSHA256());
        sb.append(", size=").append(getSize());
        sb.append('\n');
        sb.append("ownerId=").append(getOwnerId());
        sb.append('\n');
        sb.append("album='").append(album).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", track=").append(track);
        sb.append(", rating=").append(rating);
        sb.append(", thumb_url='").append(thumbUrl).append('\'');
        sb.append('\n');
        sb.append("artist=").append(Arrays.toString(artist));
        sb.append(", genre=").append(Arrays.toString(genre));
        sb.append("\n}");
        return sb.toString();
    }

    @Override
    public boolean isDeterministic() {
        return !(getOwnerId() == null || getSHA256() == null || getSize() == null);
    }
}
