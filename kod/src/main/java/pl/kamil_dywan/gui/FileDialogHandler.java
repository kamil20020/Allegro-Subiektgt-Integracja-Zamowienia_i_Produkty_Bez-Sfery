package pl.kamil_dywan.gui;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public interface FileDialogHandler {

    static String getSaveFileDialogSelectedPath(String dialogTitle, String fileName, String fileExtension){

        FileDialog fileDialog = new FileDialog((Frame) null, dialogTitle, FileDialog.SAVE);

        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();

        fileDialog.setDirectory(homeDirectory.getAbsolutePath());
        fileDialog.setFile(fileName);
        fileDialog.setFilenameFilter((directory, name) -> name.endsWith(fileExtension));

        fileDialog.setVisible(true);

        String savedFileName = fileDialog.getFile();

        if (savedFileName == null) {
            return "";
        }

        return fileDialog.getDirectory() + savedFileName;
    }
}
