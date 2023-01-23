package sig.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HeaderFrameView extends JDialog {
    private final JTextField _CustomerNameTextField;
    private final JTextField _DateTextField;
    private final JLabel _customerNameLabel;
    private final JLabel _DateLabel;
    private final JButton _saveChangesButton;
    private final JButton _CancelButton;

    public HeaderFrameView(UI frame) {
         _CustomerNameTextField = new JTextField(20);
         _customerNameLabel = new JLabel(" Customer Name");
         _DateTextField = new JTextField(20);

         _DateLabel = new JLabel(" Date ");
       _CancelButton = new JButton(" Cancel" );
        _saveChangesButton = new JButton(" Save ");
        
        _CancelButton.setActionCommand("newInvoiceCancel");
        _saveChangesButton.setActionCommand("newInvoiceSave");
        
        
        _saveChangesButton.addActionListener(frame.getTheSelectedActionHandler());
        _CancelButton.addActionListener(frame.getTheSelectedActionHandler());
        setLayout(new GridLayout(3, 2));
        

        add(_CustomerNameTextField);
        add(_saveChangesButton);
        add(_CancelButton);
        add(_DateLabel);
        add(_DateTextField);
        add(_customerNameLabel);
      
        
        pack();
        
    }

    public JTextField getCustomerNameTextField() {
        return _CustomerNameTextField;
    }

    public JTextField getDateTextField() {
        return _DateTextField;
    }
    
}
