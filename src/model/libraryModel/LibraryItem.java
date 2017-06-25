package model.libraryModel;

import model.IJsonable;

/**
 * Created by czyczk on 2017-6-23.
 */
public abstract class LibraryItem implements IJsonable {
    private MediaTypeEnum mediaType;
    private String title;
    private Integer ownerId;

    public MediaTypeEnum getMediaType() {
        return mediaType;
    }
    public void setMediaType(MediaTypeEnum mediaType) {
        this.mediaType = mediaType;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
