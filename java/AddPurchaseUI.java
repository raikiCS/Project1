import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;
import java.util.Calendar;

public class AddPurchaseUI {
    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtPurchaseID = new JTextField(10);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtProductID = new JTextField(10);
    public JTextField txtQuantity = new JTextField(10);

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Date of Purchase: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");


    public JLabel labCost = new JLabel("Cost: $0.00 ");
    public JLabel labTax = new JLabel("Tax: $0.00");
    public JLabel labTotalCost = new JLabel("Total Cost: $0.00");

    ProductModel product;
    CustomerModel customer;
    double totalCost = 0;

    public AddPurchaseUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("PurchaseID "));
        line.add(txtPurchaseID);
        line.add(labDate);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID "));
        line.add(txtCustomerID);
        line.add(labCustomerName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID "));
        line.add(txtProductID);
        line.add(labProductName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity "));

        line.add(txtQuantity);
        line.add(labPrice);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labCost);
        line.add(labTax);
        line.add(labTotalCost);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(btnAdd);
        line.add(btnCancel);
        view.getContentPane().add(line);

        btnAdd.addActionListener(new AddButtonListerner());

        txtProductID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                process();
            }

            private void process() {
                String s = txtProductID.getText();

                if (s.length() == 0) {
                    labProductName.setText("Product Name: [not specified!]");
                    return;
                }

                System.out.println("ProductID = " + s);

                int id;

                try {
                    id = Integer.parseInt(s);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Invalid ProductID", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                product = StoreManager.getInstance().getDataAdapter().loadProduct(id);

                if (product == null) {
                    JOptionPane.showMessageDialog(null,
                            "Error: No product with id = " + id + " in store!", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                            labProductName.setText("Product Name: ");

                    return;
                }


                String c = txtCustomerID.getText();

                if (c.length() == 0) {
                    labCustomerName.setText("Customer Name: [not specified!]");
                    return;
                }

                System.out.println("CustomerID = " + c);

                int cid;

                try {
                    cid = Integer.parseInt(c);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Invalid ProductID", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                customer = StoreManager.getInstance().getDataAdapter().loadCustomer(cid);

                if (customer == null) {
                    JOptionPane.showMessageDialog(null,
                            "Error: No customer with id = " + cid + " in store!", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                    labProductName.setText("Customer Name: ");

                    return;
                }

                labCustomerName.setText("Customer Name: " + customer.mName);
                labProductName.setText("Product Name: " + product.mName);
                labPrice.setText("Product Price: " + product.mPrice);

                DecimalFormat df = new DecimalFormat("#,###,##0.00");
                double price = 0;
                double tax = 0;

                try {
                    price = (product.mPrice * Integer.parseInt(txtQuantity.getText()));
                    labCost.setText("Cost: $" + df.format(price));

                } catch (NumberFormatException ex) {

                    return;
                }

                try {
                    tax = (product.mPrice * Integer.parseInt(txtQuantity.getText())) * 0.1;
                    labTax.setText("Cost: $" + df.format(tax));

                } catch (NumberFormatException ex) {

                    return;
                }

                try {
                    totalCost = price + tax;
                    labTotalCost.setText("Cost: $" + df.format(totalCost));

                } catch (NumberFormatException ex) {

                    return;
                }

            }

        });


//        txtCustomerID.getDocument().addDocumentListener(new DocumentListener() {
//            public void changedUpdate(DocumentEvent e) {
//                process();
//            }
//
//            public void removeUpdate(DocumentEvent e) {
//                process();
//            }
//            public void insertUpdate(DocumentEvent e) {
//                process();
//            }
//
//            private void process() {
//                String s = txtCustomerID.getText();
//
//                if (s.length() == 0) {
//                    labCustomerName.setText("Customer Name: [not specified!]");
//                    return;
//                }
//
//                System.out.println("CustomerID = " + s);
//
//                try {
//                    int id = Integer.parseInt(s);
//
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null,
//                            "Error: Please enter number bigger than 0", "Error Message",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//
//            }
//        });

    }

    public void run() {

        labDate.setText("Date of purchase: " + Calendar.getInstance().getTime());
        view.setVisible(true);
    }

    class AddButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchase = new PurchaseModel();

            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }



            String customerID = txtCustomerID.getText();
            try {
                purchase.mCustomerID = Integer.parseInt(customerID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            String productID = txtProductID.getText();
            try {
                purchase.mProductID = Integer.parseInt(productID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }



            String quantity = txtQuantity.getText();
            try {
                purchase.mQuantity = (int) Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }




            switch (StoreManager.getInstance().getDataAdapter().savePurchase(purchase)) {
                case SQLiteDataAdapter.PRODUCT_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Purchase NOT added successfully! Duplicate product ID!");
                default:
                    String recipe = "Recipe\n + PurchaseID: " + txtPurchaseID.getText() + "\n"
                            + "CustomerID: " + txtCustomerID.getText() + "\n"
                            + "ProductID: " + txtProductID.getText() + "\n"
                            + "TotalCost: " + totalCost + "\n";
                    JOptionPane.showMessageDialog(null, recipe);
            }
        }
    }


}
