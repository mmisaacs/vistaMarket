# Vista Market Shopping Cart GUI

This Java Swing program provides an online shopping experience for customers of Vista Market, the market on Cal Poly Pomona’s campus. It provides various items shoppers can purchase from the market and gives them an estimated total and multiple options they can use to pay.

Vista Market offers multiple ways to pay, including Cal Poly Pomona dining meal swipes and meal points. The market equivalates one meal swipe for 8 US dollars. There is a limit to two meal swipes per meal period, breakfast, lunch, and dinner; thus, allowing students to spend 16 dollars are food items. For other items such as dish soap or medicine, shoppers must use meal points or cash. As our program is geared towards students, it only includes meal swipes and meal points at this time.

### Layout

After the opening image of the Vista Market logo is displayed, the program displays four categories of items, including Drinks, Entreés, Utilities, and Groceries. These different categories are separated by the different tabs using a tabbed pane at the top of the GUI.

### How It Works

The program opens with the Vista Market logo and a button to start shopping. After pressing the start shopping button, it displays various food and item tabs. In each product tab, there is a list of checkboxes for each item in the category. When a checkbox is selected, a quantity box is added for each item, and the item is added to a list of selected items found in the “Shopping Cart” tab. The shopping cart tab includes the items, a quantity box for each item, a button to calculate the total, and two checkboxes for meal swipes and meal points along with information on how many swipes the user would need to use to pay. If anything from the utility tab is selected, the swipes option is disabled, as meal swipes are only allowed to be used on food items.
