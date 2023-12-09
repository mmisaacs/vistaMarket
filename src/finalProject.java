import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.util.*;


public class finalProject{
	
	JFrame jfrm = new JFrame("Vista Market");
	JLabel quant = new JLabel("Quantity");
	JComponent comps[][] = 
		{{new JLabel("Beverages ($4.00)"),new item("Pepsi",4.0),new item("Starry",4.0),new item("Gatorade",4.0)},
		 
		 {new JLabel("Entraes ($4.00)"),new item("Sandwich",4.0),new item("Salad",4.0),new item("Stouffer's Mac and Cheese",4.0)},
		 
		 {new JLabel("Utilities ($3.00)"),new item("Dawn Soap",3.0),new item("Sponge",3.0),new item("Advil",3.0)},
		 
		 {},
		 
		 {}};
	JCheckBox Swipes = new JCheckBox("Swipes($8 each)");
	JCheckBox mP = new JCheckBox("Points");
	LinkedList<Object> selectedItems = new LinkedList<>();
	finalProject(){
        jfrm.setSize(500, 500);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setVisible(true);
        
        JTabbedPane jtp = new JTabbedPane();
		
        //beverage tab
        JPanel Beverages = createPanel(0);
        //food tab
        JPanel Entraes = createPanel(1);
        //Groceries tab
        JPanel Utilities = createPanel(2);
        //Utility Tab
        JPanel Groceries = createPanel(3);
        //shopping cart tab
        JPanel shoppingCart = new JPanel();
        
        
        jtp.add("Beverages", Beverages);
        jtp.add("Snacks", Entraes);
        jtp.add("Utilities", Utilities);
        jtp.add("Groceries", Groceries);
        jtp.add("Shopping Cart", shoppingCart);
        
        jfrm.add(jtp);
        
        
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
		JPanel firstPan = new JPanel();
		firstPan.setMaximumSize(new Dimension(500,30));
		firstPan.add(comps[panelIndex][0]);
		firstPan.add(Box.createHorizontalGlue());
		firstPan.add(Box.createHorizontalStrut(270));
		firstPan.add(new JLabel("Quantity"));
		thisPanel.add(firstPan);
		for(int i = 1;i< comps[panelIndex].length;i++) {
			JPanel currRow = new JPanel();
			
			currRow.setMaximumSize(new Dimension(500,30));
			currRow.setLayout(new BoxLayout(currRow, BoxLayout.LINE_AXIS));
			if(comps[panelIndex][i] instanceof item) {
				currRow.add(Box.createHorizontalStrut(30));
				currRow.add(((item) comps[panelIndex][i]).getJCheckBox());
				currRow.add(Box.createHorizontalGlue());
				
				currRow.add(((item) comps[panelIndex][i]).getJComboBox());
				currRow.add(Box.createHorizontalStrut(25));
			}else {
				currRow.add(comps[panelIndex][i]);
			}
			thisPanel.add(currRow);
			
		}
		return thisPanel;
		
	}
	
	
	private class item extends JComponent{
		
		public JCheckBox jcb;
		public JComboBox jcombo;
		Double price;
		public JCheckBox getJCheckBox() {
			return jcb;
		}public JComboBox getJComboBox() {
			return jcombo;
		}
		item(){
			jcb = null;
			jcombo = null;
		}
		item(String jcb,double Price){
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
						selectedItems.add(this);
					}else {
						jcombo.setVisible(false);
						selectedItems.remove(this);
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
					if(selectedItems.contains(this)) {
						//Do nothing
					}else {
						selectedItems.add(this);
					}
				}
				
			});

		}
		
	}


}
