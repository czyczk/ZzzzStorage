package model.transferModel;

import model.libraryModel.FileAssociatedItem;

/**
 * Created by czycz on 2017-6-25.
 */
public class UploadTask extends TransferTask {
    public UploadTask(FileAssociatedItem item, boolean isRunning) {
        super(item, isRunning);
        setType(TransferTaskTypeEnum.UPLOAD);
    }
}
