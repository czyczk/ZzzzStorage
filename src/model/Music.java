package model;

import java.util.Arrays;

/**
 * Created by czyczk on 2017-6-23.
 */
public class Music extends FileAssociatedItem {
    private String album;
    private Integer track;
    private Integer rating;
    private String thumb_url;
    private String[] artist;
    private String[] genre;

    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getTrack() {
        return track;
    }
    public void setTrack(int track) {
        this.track = track;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getThumb_url() {
        return thumb_url;
    }
    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String[] getArtist() {
        return artist;
    }
    public void setArtist(String[] artist) {
        this.artist = artist;
    }

    public String[] getGenre() {
        return genre;
    }
    public void setGenre(String[] genre) {
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
        sb.append(", track=").append(track);
        sb.append(", rating=").append(rating);
        sb.append(", thumb_url='").append(thumb_url).append('\'');
        sb.append('\n');
        sb.append("artist=").append(Arrays.toString(artist));
        sb.append(", genre=").append(Arrays.toString(genre));
        sb.append("\n}");
        return sb.toString();
    }
}
