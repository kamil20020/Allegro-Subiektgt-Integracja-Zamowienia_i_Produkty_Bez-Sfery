package pl.kamil_dywan.gui;

import pl.kamil_dywan.service.ProductService;

import javax.swing.*;

public class MainGui {

    private JTabbedPane tabbedPane;
    private JPanel mainPanel;

    public MainGui(ProductService productService){

        ProductsGui productsGui = new ProductsGui(productService);
        OrdersGui ordersGui = new OrdersGui();

        tabbedPane.addTab("Zamówienia", ordersGui.getMainPanel());
        tabbedPane.addTab("Produkty", productsGui.getMainPanel());

        JFrame frame = new JFrame("Integracja Allegro i Subiekt GT");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 680);

        frame.add(mainPanel);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
