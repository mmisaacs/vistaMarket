import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class finalProject{
    JPanel Beverages, Entraes, Utilities, Groceries, shoppingCart;
    JFrame jfrm = new JFrame("Vista Market");
    JLabel quant = new JLabel("Quantity");
    JTabbedPane jtp;
    JLabel totalPrice;
    JLabel swipesLabel= new JLabel("Swipes needed: 0");
    JLabel pointsLabel= new JLabel("Meal Points needed: 0");
    JScrollPane scroll;
    ImageIcon vista;

    JButton getTotal;
    JComponent comps[][] =
            {{new JLabel("Beverages ($4.00)"),new item("Pepsi",4.0),new item("Starry",4.0),new item("Gatorade",4.0)},

                    {new JLabel("Entraes ($4.00)"),new item("Sandwich",4.0),new item("Salad",4.0),new item("Stouffer's Mac and Cheese",4.0)},

                    {new JLabel("Utilities ($3.00)"),new item("Dawn Soap",3.0,true),new item("Sponge",3.0,true),new item("Advil",3.0,true)},

                    {new JLabel("Groceries ($6.00)"),new item("Bread", 6.0),new item("Eggs", 6.0), new item("Milk", 6.0)},
                    {new JLabel("Selected Items: ")}};

    JCheckBox Swipes = new JCheckBox("Use Swipes ($8 each, 2 per purchase)");
    final double SWIPE_VALUE = 8.0;

    JCheckBox mP = new JCheckBox("Use Points ($1 each)");
    LinkedList<item> selectedItems = new LinkedList<>();
    finalProject(){
        jfrm.setSize(500, 500);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setVisible(true);

        jtp = new JTabbedPane();

        //beverage tab
        Beverages = createPanel(0);
        //food tab
        Entraes = createPanel(1);
        //Groceries tab
        Utilities = createPanel(2);
        //Utility Tab
        Groceries = createPanel(3);

        shoppingCart = createPanel(4);

        totalPrice = new JLabel("Total: $0.00");

        getTotal = new JButton("total");
        getTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = 0.00;
                int swipesNeeded = 0;
                int pointsNeeded = 0;

                for (Object i : selectedItems) {
                    //System.out.println("Selected Item: " + i);
                    if (i instanceof item) {
                        item selected = (item) i;
                        int quantity = (int) selected.jcombo.getSelectedItem();
                        total += quantity * selected.price;
                    }
                }

                if (mP.isSelected()) {

                    pointsNeeded = (int) Math.ceil(total);
                    swipesLabel.setText("Swipes needed: 0");
                    pointsLabel.setText("Meal Points needed: " + pointsNeeded);
                } else {

                    swipesNeeded = (int) Math.min(Math.ceil(total / SWIPE_VALUE), 2);
                    pointsNeeded = (int) Math.ceil(Math.max((total - swipesNeeded * SWIPE_VALUE), 0));

                    swipesLabel.setText("Swipes needed: " + swipesNeeded);
                    pointsLabel.setText("Meal Points needed: " + pointsNeeded);
                }
                totalPrice.setText("Total: $" + String.format("%.2f", total));

                swipesLabel.setText("Swipes needed: " + swipesNeeded);

                pointsLabel.setText("Meal Points needed: " + pointsNeeded);

            }
        });

        vista = new ImageIcon(getClass().getResource("vistaLogo.png"));
        JLabel logo = new JLabel(vista);

        JButton shop = new JButton("Shop Now!");

        shop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shop.setVisible(false);
                logo.setVisible(false);
                jfrm.add(jtp);
            }
        });

        jtp.add("Beverages", Beverages);
        jtp.add("Snacks", Entraes);
        jtp.add("Utilities", Utilities);
        jtp.add("Groceries", Groceries);
        jtp.add("Shopping Cart", shoppingCart);

        scroll = new JScrollPane(jtp);

        jfrm.add(logo);
        jfrm.add(shop, BorderLayout.PAGE_END);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new finalProject();
            }
        });

    }

    public JPanel createPanel(int panelIndex) {
        JPanel thisPanel = new JPanel();

        thisPanel.setLayout(new BoxLayout(thisPanel,BoxLayout.Y_AXIS));
        if(comps[panelIndex].length == 0) {

        }else if(comps[panelIndex][0] instanceof JLabel){
            JPanel firstPan = new JPanel();
            firstPan.setMaximumSize(new Dimension(500,30));
            firstPan.add(comps[panelIndex][0]);
            firstPan.add(Box.createHorizontalGlue());
            firstPan.add(Box.createHorizontalStrut(270));
            firstPan.add(new JLabel("Quantity"));
            thisPanel.add(firstPan);
        }
        if(panelIndex < 4) {
            for (int i = 1; i < comps[panelIndex].length; i++) {
                JPanel currRow = new JPanel();

                currRow.setMaximumSize(new Dimension(500, 30));
                currRow.setLayout(new BoxLayout(currRow, BoxLayout.LINE_AXIS));
                if (comps[panelIndex][i] instanceof item) {
                    currRow.add(Box.createHorizontalStrut(30));
                    currRow.add(((item) comps[panelIndex][i]).getJCheckBox());
                    currRow.add(Box.createHorizontalGlue());

                    currRow.add(((item) comps[panelIndex][i]).getJComboBox());
                    currRow.add(Box.createHorizontalStrut(25));
                } else {
                    currRow.add(comps[panelIndex][i]);
                }
                thisPanel.add(currRow);
            }
        } else {
            Swipes.setEnabled(true);
            for(int i = 0; i < selectedItems.size(); ++i) {
                JPanel currItem = new JPanel();
                currItem.setMaximumSize(new Dimension(500, 30));
                currItem.setLayout(new BoxLayout(currItem, BoxLayout.LINE_AXIS));
                currItem.add(Box.createHorizontalStrut(30));
                JLabel item = new JLabel(((item) selectedItems.get(i)).getJCheckBox().getText());
                currItem.add(item);
                currItem.add(Box.createHorizontalGlue());
                currItem.add(((item) selectedItems.get(i)).getDupCombo());
                currItem.add(Box.createHorizontalStrut(25));
                thisPanel.add(currItem);
                if(((item) selectedItems.get(i)).isUtility){
                    Swipes.setEnabled(false);
                }
                thisPanel.add(Swipes);
                thisPanel.add(mP);

                thisPanel.add(totalPrice);
                thisPanel.add(getTotal);
                thisPanel.add(swipesLabel);
                thisPanel.add(pointsLabel);
            }



        }
        return thisPanel;

    }

    private class item extends JComponent{

        public JCheckBox jcb;
        public boolean isUtility;
        public JComboBox jcombo, dupCombo;
        Double price;
        public JCheckBox getJCheckBox() {
            return jcb;
        }public JComboBox getJComboBox() {
            return jcombo;
        }public JComboBox getDupCombo() {
            return dupCombo;
        }
        item(){
            jcb = null;
            jcombo = null;
            dupCombo = null;
            isUtility = false;
        }

        item(String jcb,double Price,boolean isUtil){
            item itself = this;
            this.isUtility = isUtil;
            Integer quants[] = new Integer[51];
            this.price = Price;
            for(int i = 1; i < quants.length;i++) {//Number range for each combobox(1-50)
                quants[i] = i;
            }
            this.jcb = new JCheckBox(jcb);
            this.jcb.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(((AbstractButton) e.getSource()).isSelected()) {
                        jcombo.setVisible(true);
                        dupCombo.setVisible(true);
                        selectedItems.add(itself);
                        jtp.remove(4);
                        shoppingCart = createPanel(4);
                        jtp.add("Shopping Cart", shoppingCart);
                    }else {
                        jcombo.setVisible(false);
                        selectedItems.remove(itself);
                        jtp.remove(4);
                        shoppingCart = createPanel(4);
                        jtp.add("Shopping Cart", shoppingCart);
                    }
                }

            });
            this.jcombo = new JComboBox<Integer>(quants);
            this.jcombo.setVisible(false);
            this.jcombo.removeItemAt(0);
            this.jcombo.setMaximumSize(new Dimension(30,95));
            this.jcombo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dupCombo.setSelectedItem(jcombo.getSelectedItem());
                    if(selectedItems.contains(itself)) {
                        //Do nothing
                    }else {
                        selectedItems.add(itself);
                    }
                }

            });

            this.dupCombo = new JComboBox<Integer>(quants);
            this.dupCombo.setVisible(false);
            this.dupCombo.removeItemAt(0);
            this.dupCombo.setMaximumSize(new Dimension(30,95));
            this.dupCombo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jcombo.setSelectedItem(dupCombo.getSelectedItem());
                    if(selectedItems.contains(itself)) {
                        //Do nothing
                    }else {
                        selectedItems.add(itself);
                    }
                }

            });
        }item(String jcb, double Price){
            this(jcb,Price,false);
        }

    }
}
