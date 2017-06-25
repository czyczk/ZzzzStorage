package model.transferModel;

import model.libraryModel.FileAssociatedItem;

/**
 * Created by czyczk on 2017-6-25.
 */
public abstract class TransferTask {
    private TransferTaskTypeEnum type;
    private FileAssociatedItem item;

    public TransferTaskTypeEnum getType() {
        return type;
    }
    public void setType(TransferTaskTypeEnum type) {
        this.type = type;
    }

    public FileAssociatedItem getItem() {
        return item;
    }
    public void setItem(FileAssociatedItem item) {
        this.item = item;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransferTask {\n");
        sb.append("type=").append(type);
        sb.append(", item=").append(item);
        sb.append("\n}");
        return sb.toString();
    }

    public TransferTask(FileAssociatedItem item) {
        setItem(item);
    }
}

enum TransferTaskTypeEnum {
    UPLOAD, DOWNLOAD
}