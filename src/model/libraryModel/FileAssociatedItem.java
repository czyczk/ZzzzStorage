package model.libraryModel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by czyczk on 2017-6-23.
 */
public abstract class FileAssociatedItem extends LibraryItem {
    private String SHA256;
    private Long size;

    public String getSHA256() {
        return SHA256;
    }
    public void setSHA256(String SHA256) {
        this.SHA256 = SHA256.toUpperCase();
    }

    public Long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    public abstract boolean isDeterministic();

    public static FileAssociatedItem createItem(MediaTypeEnum mediaType, String SHA256, long size) {
        if (SHA256 == null || SHA256.trim().isEmpty()) {
            throw new IllegalArgumentException("SHA256 is not specified.");
        }
        FileAssociatedItem item = null;
        switch (mediaType) {
            case MOVIE:
                item = new Movie();
                break;
            case MUSIC:
                item = new Music();
                break;
            case EPISODE:
                item = new Episode();
                break;
            default:
                throw new IllegalArgumentException("Not supported media type.");
        }
        item.setSHA256(SHA256);
        item.setSize(size);
        return item;
    }
}
