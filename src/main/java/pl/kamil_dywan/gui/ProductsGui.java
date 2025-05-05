package pl.kamil_dywan.gui;

import pl.kamil_dywan.exception.UnloggedException;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProduct;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProductResponse;
import pl.kamil_dywan.external.allegro.generated.offer_product.ProductOffer;
import pl.kamil_dywan.external.subiektgt.own.product.ProductType;
import pl.kamil_dywan.service.AuthService;
import pl.kamil_dywan.service.ProductService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductsGui implements ChangeableGui {

    private JPanel mainPanel;

    private JPanel productsPanelPlaceholder;
    private PaginationTableGui paginationTableGui;

    private JButton exportButton;
    private JButton deliveryButton;

    private List<ProductOffer> products;

    private final ProductService productService;
    private final Runnable handleLogout;

    public ProductsGui(ProductService productService, Runnable handleLogout) {

        this.productService = productService;
        this.handleLogout = handleLogout;

        $$$setupUI$$$();

        exportButton.addActionListener(e -> saveProductsToFile());

        deliveryButton.addActionListener(e -> saveDeliveryToFile());
    }

    private PaginationTableGui.PaginationTableData loadProductsPage(int offset, int limit) {

        OfferProductResponse generalProductsPage;

        try {
            generalProductsPage = productService.getGeneralProductsPage(offset, limit);
        } catch (UnloggedException e) {

            handleLogout.run();

            return null;
        }

        List<OfferProduct> gotGeneralProducts = generalProductsPage.getOffersProducts();

        List<Long> productsIds = gotGeneralProducts.stream()
                .map(offerProduct -> offerProduct.getId())
                .collect(Collectors.toList());

        products = productService.getDetailedProductsByIds(productsIds);

        int totalNumberOfRows = generalProductsPage.getTotalCount();

        PaginationTableGui.PaginationTableData tableData = new PaginationTableGui.PaginationTableData(
                products,
                totalNumberOfRows
        );

        return tableData;
    }

    private Object[] convertProductToRow(Object rawProductOffer) {

        ProductOffer productOffer = (ProductOffer) rawProductOffer;

        return new Object[]{
                productOffer.getId(),
                productOffer.getName(),
                productOffer.getPriceWithoutTax().toString() + " zł",
                productOffer.getPriceWithTax() + " zł",
                productOffer.getTaxRate().toString() + '%'
        };
    }

    private void saveDeliveryToFile() {

        FileDialog fileDialog = runEppFileSaveDialog("dostawa.epp", "Zapisywanie dostawy do pliku");

        String savedFileName = fileDialog.getFile();

        if (savedFileName == null) {
            return;
        }

        String savedFilePath = fileDialog.getDirectory() + savedFileName;

        ProductOffer deliveryService = productService.getDeliveryService();

        try {

            productService.writeProductsToFile(List.of(deliveryService), savedFilePath, ProductType.SERVICES);
        } catch (IllegalStateException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Nie udało się zapisać produktów do pliku",
                    "Powiadomienie o błędzie",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                mainPanel,
                "Zapisano dostawę do pliku " + savedFilePath,
                "Powiadomienie",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void saveProductsToFile() {

        if (products == null) {

            JOptionPane.showMessageDialog(mainPanel, "Brak produktów do zapisania", "Błąd", JOptionPane.ERROR_MESSAGE);

            return;
        }

        FileDialog fileDialog = runEppFileSaveDialog("produkty.epp", "Zapisywanie produktów do pliku");

        String savedFileName = fileDialog.getFile();

        if (savedFileName != null) {

            String savedFilePath = fileDialog.getDirectory() + savedFileName;

            productService.writeProductsToFile(products, savedFilePath, ProductType.GOODS);

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Zapisano produkty do pliku " + savedFilePath,
                    "Powiadomienie",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private FileDialog runEppFileSaveDialog(String fileName, String message) {

        FileDialog fileDialog = new FileDialog((Frame) null, message, FileDialog.SAVE);

        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();

        fileDialog.setDirectory(homeDirectory.getAbsolutePath());
        fileDialog.setFile(fileName);
        fileDialog.setFilenameFilter((directory, name) -> name.endsWith(".epp"));

        fileDialog.setVisible(true);

        return fileDialog;
    }

    @Override
    public void load() {

        paginationTableGui.handleLoadTableExceptions();
    }

    public JPanel getMainPanel() {

        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        String[] columnsHeaders = {"Identyfikator", "Nazwa", "Cena netto", "Cena brutto", "Podatek"};
        Integer[] columnsWidths = {50, 100, 50, 80, 80, 50};

        paginationTableGui = new PaginationTableGui(columnsHeaders, columnsWidths, this::loadProductsPage, this::convertProductToRow);

        productsPanelPlaceholder = paginationTableGui.getMainPanel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setAlignmentX(0.0f);
        mainPanel.setAlignmentY(0.0f);
        mainPanel.setPreferredSize(new Dimension(1920, 980));
        mainPanel.setRequestFocusEnabled(true);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 50, 40, 50), "            ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 26, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(0);
        label1.setText("Produkty");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(label1, gbc);
        productsPanelPlaceholder.setFocusable(false);
        productsPanelPlaceholder.setOpaque(false);
        productsPanelPlaceholder.setRequestFocusEnabled(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 10.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(productsPanelPlaceholder, gbc);
        productsPanelPlaceholder.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        toolBar1.setOpaque(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(toolBar1, gbc);
        exportButton = new JButton();
        exportButton.setMaximumSize(new Dimension(180, 30));
        exportButton.setMinimumSize(new Dimension(180, 30));
        exportButton.setPreferredSize(new Dimension(180, 30));
        exportButton.setText("Zapisz produkty do pliku");
        toolBar1.add(exportButton);
        final JToolBar.Separator toolBar$Separator1 = new JToolBar.Separator();
        toolBar1.add(toolBar$Separator1);
        deliveryButton = new JButton();
        deliveryButton.setMaximumSize(new Dimension(180, 30));
        deliveryButton.setMinimumSize(new Dimension(180, 30));
        deliveryButton.setPreferredSize(new Dimension(180, 30));
        deliveryButton.setText("Zapisz dostawę do pliku");
        toolBar1.add(deliveryButton);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
