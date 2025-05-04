package pl.kamil_dywan.gui;

import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProduct;
import pl.kamil_dywan.external.allegro.generated.offer_product.OfferProductResponse;
import pl.kamil_dywan.external.allegro.generated.offer_product.ProductOffer;
import pl.kamil_dywan.external.subiektgt.own.product.ProductType;
import pl.kamil_dywan.service.ProductService;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsGui {

    private JPanel mainPanel;

    private JPanel productsPanelPlaceholder;
    private PaginationTableGui paginationTableGui;

    private JButton exportButton;
    private JButton deliveryButton;

    private ProductService productService;

    private List<ProductOffer> products;

    public ProductsGui(ProductService productService) {

        this.productService = productService;

        exportButton.addActionListener(e -> saveProductsToFile());

        deliveryButton.addActionListener(e -> saveDeliveryToFile());

        paginationTableGui.handleLoadTableExceptions();
    }

    private PaginationTableGui.PaginationTableData loadProductsPage(int offset, int limit){

        OfferProductResponse generalProductsPage = productService.getGeneralProductsPage(offset, limit);

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

    private Object[] convertProductToRow(Object rawProductOffer){

        ProductOffer productOffer = (ProductOffer) rawProductOffer;

        return new Object[]{
            productOffer.getId(),
            productOffer.getName(),
            productOffer.getPriceWithoutTax().toString() + " zł",
            productOffer.getPriceWithTax() + " zł",
            productOffer.getTaxRate().toString() + '%'
        };
    }

    private void saveDeliveryToFile(){

        FileDialog fileDialog = new FileDialog((Frame) null, "Zapisywanie dostawy do pliku", FileDialog.SAVE);

        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();

        fileDialog.setDirectory(homeDirectory.getAbsolutePath());
        fileDialog.setFile("dostawa.epp");
        fileDialog.setFilenameFilter((directory, name) -> name.endsWith(".epp"));

        fileDialog.setVisible(true);

        String savedFileName = fileDialog.getFile();

        if(savedFileName != null){

            String savedFilePath = fileDialog.getDirectory() + savedFileName;

            ProductOffer deliveryService = productService.getDeliveryService();

            productService.writeProductsToFile(List.of(deliveryService), savedFilePath, ProductType.SERVICES);

            JOptionPane.showMessageDialog(
                mainPanel,
                "Zapisano dostawę do pliku " + savedFilePath,
                "Powiadomienie",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void saveProductsToFile(){

        if(products == null){

            JOptionPane.showMessageDialog(mainPanel, "Brak produktów do zapisania", "Błąd", JOptionPane.ERROR_MESSAGE);

            return;
        }

        FileDialog fileDialog = new FileDialog((Frame) null, "Zapisywanie produktów do pliku", FileDialog.SAVE);

        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();

        fileDialog.setDirectory(homeDirectory.getAbsolutePath());
        fileDialog.setFile("products.epp");
        fileDialog.setFilenameFilter((directory, name) -> name.endsWith(".epp"));

        fileDialog.setVisible(true);

        String savedFileName = fileDialog.getFile();

        if(savedFileName != null){

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

    public JPanel getMainPanel(){

        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        String[] columnsHeaders = {"Identyfikator", "Nazwa", "Cena netto", "Cena brutto", "Podatek"};
        Integer[] columnsWidths = {50, 100, 50, 80, 80, 50};

        paginationTableGui = new PaginationTableGui(columnsHeaders, columnsWidths, this::loadProductsPage, this::convertProductToRow);

        productsPanelPlaceholder = paginationTableGui.getMainPanel();
    }
}
