import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.util.*;


public class finalProject implements ActionListener{
    JLabel labs[] = {new JLabel("Vista Market"),
            new JLabel("Beverages ($4.00)"),
            new JLabel("Quantity"),
            new JLabel("Entrées ($4.00)"),
            new JLabel("Utilities ($3.00)"),
            new JLabel("Swipes Used: "),
            new JLabel("Total: $0.00")};

    JCheckBox beverages[] =
            {new JCheckBox("Pepsi"),
                    new JCheckBox("Starry"),
                    new JCheckBox("Gatorade")};

    JCheckBox food[] =
            {new JCheckBox("Sandwich"),
                    new JCheckBox("Salad"),
                    new JCheckBox("Stouffer's Mac and Cheese")};

    JCheckBox utility[] =
            {new JCheckBox("Dawn Soap"),
                    new JCheckBox("Sponge"),
                    new JCheckBox("Advil")};

    JComboBox<Integer>[] bevQuant;
    JCheckBox Swipes = new JCheckBox("Swipes($8 each)");
    JCheckBox mP = new JCheckBox("Points");
    JButton calc = new JButton("Calculate");
    LinkedList<Integer> selectedIndx = new LinkedList<>();

    finalProject(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHWEST;

        JFrame jfrm = new JFrame("Vista Market");
        jfrm.setSize(500, 500);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLayout(new FlowLayout());
        jfrm.setVisible(true);

        JTabbedPane jtp = new JTabbedPane();

        //beverage tab
        JPanel jp1 = new JPanel();
        //food tab
        JPanel jp2 = new JPanel();
        //Groceries tab
        JPanel jp3 = new JPanel();
        //Utility Tab
        JPanel jp4 = new JPanel();
        //shopping cart tab
        JPanel jp5 = new JPanel();

        jp1.setPreferredSize(new Dimension(300, 400));
        jp1.setLayout(new GridBagLayout());
        jp2.setLayout(new GridBagLayout());
        jp3.setLayout(new GridBagLayout());
        jp4.setLayout(new GridBagLayout());
        jp5.setLayout(new GridBagLayout());

        Integer quants[] = new Integer[51];
        for(int i = 1; i < quants.length;i++) {//Number range for each combobox(1-50)
            quants[i] = i;
        }
        bevQuant = new JComboBox[beverages.length];

        labs[0].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
        jp1.add(labs[0],c);//Title added to jpanel
        jp1.add(labs[1],makeConstraints(0,1,false)); //Adds beverages subheader
        jp1.add(labs[2],makeConstraints(3,1,false));//Adds quantity subheader in the same row as beverages subheader
        int currRow = 2;//The current row for the gridbaglayout

        for(int i = 0; i < bevQuant.length;i++) {//Initializes the comboboxes corresponding to each checkbox, adding them to Jpanel
            bevQuant[i] = new JComboBox<Integer>(quants);
            bevQuant[i].setVisible(false);
            bevQuant[i].setSelectedIndex(1);
            bevQuant[i].removeItemAt(0);
            beverages[i].addActionListener(this);
            if(i == 3) {//When index is 3, the Entries subheader is added to jpanel, incrementing to the next row
                jp1.add(labs[3],makeConstraints(0,currRow,false));
                currRow++;
            }
            else if(i == 6) {//When index is 6, the Utilities subheader is added to jpanel, incrementing to the next row
                jp1.add(labs[4],makeConstraints(0,currRow,false));
                currRow++;

            }
            jp1.add(beverages[i],makeConstraints(0,currRow,false));//Current checkbox is added to jpanel
            jp1.add(bevQuant[i],makeConstraints(3,currRow,true));//Current combobox is added to jpanel
            currRow++;
        }

//		JScrollPane jsp = new JScrollPane(jpanel);
//        jsp.setPreferredSize(new Dimension(275, 300));
//        jfrm.add(jsp);




        jp1.add(labs[labs.length-1], makeConstraints(0,currRow, false));
        currRow++;
        jp1.add(Swipes, makeConstraints(0,currRow, false));
        currRow++;
        jp1.add(mP, makeConstraints(0,currRow, false));

        jtp.add("Beverages", jp1);
        jtp.add("Snacks", jp2);
        jtp.add("Groceries", jp3);
        jtp.add("Utilities", jp4);
        jtp.add("Shopping Cart", jp5);

        jfrm.add(jtp);

        calc.addActionListener(this);
        jfrm.add(calc);
    }
    private static GridBagConstraints makeConstraints(int x, int y,boolean hug) {//Makes a gridbagconstraint for a given component
        GridBagConstraints c = new GridBagConstraints();
        if(hug) {//The bool that decides if a component hugs the left or right side of the jpanel
            c.anchor = GridBagConstraints.LINE_END;
        }else {
            c.anchor = GridBagConstraints.NORTHWEST;
        }
        c.gridx = x;
        c.gridy = y;

        return c;//The gridbagconstraint is returned to be used by the component
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new finalProject();
            }
        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(calc)) {
            Double Total = 0.0;
            for(int i = 0; i < selectedIndx.size();i++) {
                //Converts selected integer of the combobox to a double
                double currQuantValue = (double)(((Integer) bevQuant[selectedIndx.get(i)].getSelectedItem()).intValue());
                //Checks the index of the current selected food item, uses that to decided the price, arrays have food items in order
                //0-2 : Drinks
                //3-5 : Entries
                //>6  : Utilities
                double price = (selectedIndx.get(i)<3)? 4.0 : ((selectedIndx.get(i)<6)?4.0:3.0);
                Total+=price*currQuantValue;
            }if(Swipes.isSelected()) {
                int Swipetotal;
                if(Total%8 == 0) {
                    Swipetotal = (int) (Total/8);
                    labs[labs.length-1].setText("<html>Total: $"+ String.format("%.2f", Total)
                            +"<br>"+Swipetotal+" swipes needed");
                }else{
                    Swipetotal = (int) (1+ (Total/8));
                    labs[labs.length-1].setText("<html>Total: $"+ String.format("%.2f", Total)
                            +"<br>"+Swipetotal+" swipes needed");
                }

            }else {
                labs[labs.length-1].setText("Total: $"+ String.format("%.2f", Total));
            }

        }else {
            Integer i = getIndexOf(e.getSource());// Reference to both combobox and checkbox
            if(((AbstractButton) e.getSource()).isSelected()) {//If the checkbox that caused the event is selected, then its combobox is made visible
                bevQuant[i].setVisible(true);
                selectedIndx.add(i);

            }else {//The combobox is invisible if the checkbox is deselected
                bevQuant[i].setVisible(false);
                selectedIndx.remove(i);
            }
        }


    }
    private int getIndexOf(Object t) {//Checks the checkbox array to find the index for the checkbox that caused the event
        for(int i = 0; i < beverages.length;i++) {
            if(beverages[i].equals(t)) {
                return i;
            }
        }
        return -1;

    }
}