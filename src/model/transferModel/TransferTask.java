package model.transferModel;

import model.libraryModel.FileAssociatedItem;

/**
 * Created by czyczk on 2017-6-25.
 */
public abstract class TransferTask {
    private TransferTaskTypeEnum type;
    private FileAssociatedItem item;
    private boolean isRunning;
    private long bytesTransferred;

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

    public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }

    public long getBytesTransferred() {
        return bytesTransferred;
    }
    public void setBytesTransferred(long bytesTransferred) {
        this.bytesTransferred = bytesTransferred;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransferTask{\n");
        sb.append("type=").append(type);
        sb.append(", item=").append(item);
        sb.append(", isRunning=").append(isRunning);
        sb.append(", bytesTransferred=").append(bytesTransferred);
        sb.append("\n");
        return sb.toString();
    }

    public TransferTask(FileAssociatedItem item, boolean isRunning) {
        setItem(item);
        setRunning(isRunning);
    }
}

enum TransferTaskTypeEnum {
    UPLOAD, DOWNLOAD
}