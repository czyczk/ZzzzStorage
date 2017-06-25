package model.libraryModel;

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
        this.SHA256 = SHA256;
    }

    public Long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
}
