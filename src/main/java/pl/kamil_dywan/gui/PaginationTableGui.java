package pl.kamil_dywan.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PaginationTableGui extends JPanel{

    private JPanel mainPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane tableScroll;

    private JToolBar pagination;
    private JButton previousButton;
    private JToolBar paginationNumbers;
    private JButton nextButton;

    private JToolBar paginationSize;
    private JComboBox<Integer> paginationSizeSelector;
    private JLabel rowsInfo;

    private final String[] tableHeaders;
    private final Integer[] columnsWidths;

    private final BiFunction<Integer, Integer, PaginationTableData> loadData;
    private final Function<Object, Object[]> convertToRow;

    private int offset = 0;
    private int pageSize = 10;
    private int totalNumberOfRows = 0;

    public PaginationTableGui(
        String[] tableHeaders,
        Integer[] columnsWidths,
        BiFunction<Integer, Integer, PaginationTableData> loadData,
        Function<Object, Object[]> convertToRow
    ){
        this.tableHeaders = tableHeaders;
        this.columnsWidths = columnsWidths;
        this.loadData = loadData;
        this.convertToRow = convertToRow;

        previousButton.addActionListener(e -> handlePreviousButton());

        nextButton.addActionListener(e -> handleNextButton());

        paginationSizeSelector.addActionListener(e -> handlePageSizeSelectorChange());
    }

    public record PaginationTableData(

        List<?> data,
        int totalNumberOfRows
    ){}

    private void handlePreviousButton(){

        if(offset == 0){
            return;
        }

        offset -= pageSize;

        handleLoadTableExceptions();
    }

    private void handleNextButton(){

        if(offset + pageSize >= totalNumberOfRows){
            return;
        }

        offset += pageSize;

        handleLoadTableExceptions();
    }

    private void handlePageSizeSelectorChange(){

        pageSize = getCurrentPageSize();

        handleLoadTableExceptions();
    }

    public void handleLoadTableExceptions(){

        try{
            loadTable();
        }
        catch(IllegalAccessException e){

            e.printStackTrace();

            JOptionPane.showMessageDialog(mainPanel, "Nie udało się załadować tabeli", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable() throws IllegalAccessException {

        PaginationTableData gotRawTableData = loadData.apply(offset, pageSize);

        tableModel.setNumRows(0);

        for(Object gotDataRawRow : gotRawTableData.data){

            Object[] newRow = convertToRow.apply(gotDataRawRow);

            tableModel.addRow(newRow);
        }

        totalNumberOfRows = gotRawTableData.totalNumberOfRows();
        rowsInfo.setText("Wiersze " + (offset + 1) + " - " + (offset + tableModel.getRowCount()) + " z " + totalNumberOfRows);

        handleLoadPaginationNumbersButtons();
    }

    private void handleLoadPaginationNumbersButtons(){

        paginationNumbers.removeAll();

        int numberOfPages = getNumberOfPages();
        int actualPage = getActualPage();

        int numberOfButtons = Math.min(numberOfPages, 4);
        int actualButtonIndex = actualPage % 4;

        int minPageButton = actualPage - actualButtonIndex;
        int minOffsetButton = minPageButton * offset;

        for(int i = 0; i < numberOfButtons; i++){

            int buttonPage = minPageButton + i;

            JButton pageButton = new JButton(String.valueOf(buttonPage + 1));

            if(i == actualButtonIndex){

                pageButton.setBackground(Color.GRAY);
            }

            int finalButtonPageOffset = minOffsetButton + i;

            pageButton.addActionListener(e -> {

                offset += finalButtonPageOffset;

                handleLoadTableExceptions();
            });

            paginationNumbers.add(pageButton);
            paginationNumbers.repaint();
        }
    }

    private int getActualPage(){

        return offset / pageSize;
    }

    private int getNumberOfPages(){

        int numberOfPages = 1;

        if(totalNumberOfRows > pageSize){

            numberOfPages = totalNumberOfRows / pageSize;
        }

        return numberOfPages;
    }

    private Integer getCurrentPageSize(){

        String currentSelectedPageSize = (String) paginationSizeSelector.getSelectedItem();

        return Integer.valueOf(currentSelectedPageSize);
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

            Integer columnWidth = columnsWidths[i];

            TableColumn column = table.getColumnModel().getColumn(i);

            column.setCellRenderer(cellsCenterRenderer);
//            column.setMaxWidth(columnWidth);
        }
    }
}
