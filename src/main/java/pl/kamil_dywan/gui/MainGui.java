package pl.kamil_dywan.gui;

import javax.swing.*;

public class MainGui {

    private JTabbedPane tabbedPane;
    private JPanel mainPanel;

    public MainGui(OrdersGui ordersGui, ProductsGui productsGui){

        tabbedPane.addTab("Zamówienia", ordersGui.getMainPanel());
        tabbedPane.addTab("Produkty", productsGui.getMainPanel());
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//
//        Locale.setDefault(new Locale("pl", "PL"));

        JFrame frame = new JFrame("Integracja Allegro i Subiekt GT");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 680);

        ProductsGui productsGui = new ProductsGui();
        OrdersGui ordersGui = new OrdersGui();

        MainGui main = new MainGui(ordersGui, productsGui);

        frame.add(main.mainPanel);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
