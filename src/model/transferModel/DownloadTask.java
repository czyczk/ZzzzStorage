package model.transferModel;

import model.libraryModel.FileAssociatedItem;

/**
 * Created by czycz on 2017-6-25.
 */
public class DownloadTask extends TransferTask {
    public DownloadTask(FileAssociatedItem item) {
        super(item);
        setType(TransferTaskTypeEnum.DOWNLOAD);
    }
}
