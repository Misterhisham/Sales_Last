
package sig.controller;
    
import sig.model.HeaderModel;
import sig.model.LineModel;
import sig.model.InvoiceLinesTable;
import sig.view.UI;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListOfListener implements ListSelectionListener {

    private  UI UIFrame;

    public ListOfListener(UI theframe) {
        this.UIFrame = theframe;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int idx = UIFrame.gettheInvoicetbl().getSelectedRow();
        
        if (idx != -1) {
            HeaderModel Invoice = UIFrame.getlstofLines().get(idx);
            ArrayList<LineModel> Model = Invoice.getLines();
            InvoiceLinesTable Drawtbl = new InvoiceLinesTable(Model);
            UIFrame.setlstofLines(Model);
            UIFrame.getTheLinetbl().setModel(Drawtbl);
            UIFrame.getTheNameofCustLbl().setText(Invoice.getCustomer());
            UIFrame.getNumberOfTheInvoiceInsideLabel().setText("" + Invoice.getNum());
            UIFrame.getTheTotalOfTheInvoicelabel().setText("" + Invoice.getItemTotal());
            UIFrame.getTheDateINsideTheLabel().setText(UI.dateFormat.format(Invoice.getDate()));
        }
    }

}

    

