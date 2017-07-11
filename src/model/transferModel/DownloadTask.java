package model.transferModel;

import model.libraryModel.FileAssociatedItem;

/**
 * Created by czyczk on 2017-6-25.
 */
public class DownloadTask extends TransferTask {
    private String indicatedFilename;
    public String getIndicatedFilename() {
        return indicatedFilename;
    }
    public void setIndicatedFilename(String indicatedFilename) {
        this.indicatedFilename = indicatedFilename;
    }

    public DownloadTask(FileAssociatedItem item, String indicatedFilename, boolean isRunning) {
        super(item, isRunning);
        setType(TransferTaskTypeEnum.DOWNLOAD);
        setIndicatedFilename(indicatedFilename);
    }
}
