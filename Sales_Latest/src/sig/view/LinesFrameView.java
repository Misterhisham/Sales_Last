
package sig.view;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

    public class LinesFrameView extends JDialog{
    private final JTextField _ItemNameTextField;
    private final JTextField _ItemCountTextField;
    private final JTextField _ItemPriceTextField;
    private final JLabel _ItemNameLabel;
    private final JLabel _ItemCountLabel;
    private final JLabel _ItemPriceLabel;
    private JButton _CreateItemButton;
    private JButton _DeleteItemButton;
    
    public LinesFrameView(UI frame) {

       _ItemCountTextField = new JTextField(20);
        _ItemCountLabel = new JLabel(" Item Count ");

        _ItemPriceTextField = new JTextField(20);
        _ItemPriceLabel = new JLabel(" Item Price ");

        _ItemNameTextField = new JTextField(20);
        _ItemNameLabel = new JLabel(" Item Name ");
        
         _DeleteItemButton = new JButton(" Delete Item ");
        _CreateItemButton = new JButton(" Create Item ");
        
        _DeleteItemButton.setActionCommand("newLineCancel");
        _CreateItemButton.setActionCommand("newLineSave");
        
         _DeleteItemButton.addActionListener(frame.getTheSelectedActionHandler());
        _CreateItemButton.addActionListener(frame.getTheSelectedActionHandler());
       
        setLayout(new GridLayout(4, 2));
        

        add(_ItemPriceLabel);
        add(_ItemPriceTextField);
        add(_CreateItemButton);
        add(_DeleteItemButton);
        add(_ItemNameLabel);
        add(_ItemNameTextField);
        add(_ItemCountLabel);
        add(_ItemCountTextField);
   
        
        pack();
    }

    public JTextField getItemNameTextField() {
        return _ItemNameTextField;
    }

    public JTextField getItemCountTextField() {
        return _ItemCountTextField;
    }

    public JTextField getItemPriceTextField() {
        return _ItemPriceTextField;
    }
}

    

