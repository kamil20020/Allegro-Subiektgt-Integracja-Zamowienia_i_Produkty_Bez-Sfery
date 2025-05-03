package pl.kamil_dywan.gui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class ProductsGui {

    private JPanel mainPanel;
    private JPanel productsPanelPlaceholder;
    private JButton exportButton;
    private JButton deliveryButton;

    public ProductsGui() {

        exportButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(mainPanel, "Powiadomienie", "AAAAA", JOptionPane.INFORMATION_MESSAGE);
        });

        deliveryButton.addActionListener(e -> {

            FileDialog fileDialog = new FileDialog((Frame) null, "Zapisywanie dostawy do pliku", FileDialog.SAVE);

            File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();

            fileDialog.setDirectory(homeDirectory.getAbsolutePath());
            fileDialog.setFile("dostawa.epp");
            fileDialog.setFilenameFilter((directory, name) -> name.endsWith(".epp"));

            fileDialog.setVisible(true);

            String savedFilename = fileDialog.getFile();

            if(savedFilename != null){

                JOptionPane.showMessageDialog(mainPanel, "Powiadomienie", "Zapisano plik", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public JPanel getMainPanel(){

        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        String[] columns = {"Identyfikator", "Nazwa", "Cena netto", "Cena brutto", "Podatek"};

        PaginationTableGui paginationTableGui = new PaginationTableGui(columns);

        productsPanelPlaceholder = paginationTableGui.getMainPanel();
    }
}
