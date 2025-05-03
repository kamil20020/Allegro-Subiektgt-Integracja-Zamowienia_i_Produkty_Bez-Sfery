package pl.kamil_dywan.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PaginationTableGui extends JPanel{

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane tableScroll;
    private JPanel mainPanel;
    private JButton nextButton;
    private JButton previousButton;
    private JToolBar pagination;
    private JButton a1Button;
    private JButton a4Button;
    private JButton a3Button;
    private JButton a2Button;
    private JToolBar paginationNumbers;
    private JComboBox<Integer> paginationSizeSelector;
    private JToolBar paginationSize;
    private JLabel rowsInfo;

    private final String[] tableHeaders;
    private String[][] tableData;

    private int pageSize = 10;

    public PaginationTableGui(String[] tableHeaders){

        this.tableHeaders = tableHeaders;

        tableData = new String[pageSize][tableHeaders.length];

        paginationSizeSelector.addActionListener(e -> {

            int newPageSize = Integer.valueOf((String) paginationSizeSelector.getSelectedItem());

            tableModel.setNumRows(0);

            for(int i = 0; i < newPageSize; i++){

                tableModel.addRow(new String[]{String.valueOf(i + 1), "2", "3", "4", "5"});
            }
        });
    }

    public JPanel getMainPanel(){

        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        DefaultTableCellRenderer cellsCenterRenderer = new DefaultTableCellRenderer();

        cellsCenterRenderer.setHorizontalAlignment(JLabel.CENTER);

        tableModel = new DefaultTableModel(tableHeaders, 0);

        table = new JTable(tableModel);

        for(int i=0; i < tableHeaders.length; i++){

            table.getColumnModel().getColumn(i).setCellRenderer(cellsCenterRenderer);
        }
    }
}
