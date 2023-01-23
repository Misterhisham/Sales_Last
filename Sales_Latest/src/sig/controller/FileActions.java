package sig.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import sig.view.UI;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import sig.model.HeaderModel;
import sig.model.InoviceHeaderTable;
import sig.model.InvoiceLinesTable;
import sig.model.LineModel;
import sig.view.HeaderFrameView;
import sig.view.LinesFrameView;





 public class FileActions implements ActionListener{
         private UI _UIFrame;
         private HeaderFrameView _InvoiceheaderPopup;
         private LinesFrameView _InvoiceLinePopup;

    public FileActions(UI eframe) {
         
        this._UIFrame = eframe;
    }
    
    @Override
    public void actionPerformed(ActionEvent action) {
        System.out.println(action.getActionCommand());
  
        switch(action.getActionCommand()){
            
            case "Create New Invoice" : 
            
                CreateNewInvoice();
            break;
    
          case "Delete Invoice" : 
         
            DeleteInvoice();
              break;
    
              case "Create Item" : 
         
                 SaveChanges();
              break;
    
                 case "Delete Item" : 
           
                    cancel();
                  break;
             case "newInvoiceSave":
                newInvoiceDialogOK();
                break;

            case "newInvoiceCancel":
                newInvoiceDialogCancel();
                break;

            case "newLineCancel":
                newLineDialogCancel();
                break;

            case "newLineSave":
                newLineDialogOK();
                break;
    
              case "load file" : 
          
                     loadfile();
                  break;
    
                 case "save file" : 
                     savefile();
                    break;
                 default:
                     System.out.println("invalid selection");

       
}
    }
 
 
    
      private void loadfile() {
        JFileChooser _ChooserFile = new JFileChooser();
        try {
            int _res = _ChooserFile.showOpenDialog(_UIFrame);
            if (_res == JFileChooser.APPROVE_OPTION) {
                File _InvoiceHeaderFile = _ChooserFile.getSelectedFile();
                Path _InvoiceHeaderPath = Paths.get(_InvoiceHeaderFile.getAbsolutePath());
                if(!_InvoiceHeaderFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                   
                List<String> headerLines = Files.readAllLines(_InvoiceHeaderPath);
                ArrayList<HeaderModel> Headers = new ArrayList<>();
                for (String headerLine : headerLines) {
                    String[] Strings = headerLine.split(",");
                    String StringNum1 = Strings[0];
                    String StringNum2 = Strings[1];
                    String StringNum3 = Strings[2];
                    int code = Integer.parseInt(StringNum1);
                   try{
                       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                
                         Date invoiceDate = dateFormat.parse(StringNum2);     
                         
                         HeaderModel header = new HeaderModel(code, StringNum3, invoiceDate);
                         Headers.add(header);
                   }
                   catch(Exception e ){
                       System.out.println(e);
                            
                        JOptionPane.showMessageDialog(_UIFrame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                   }
                   
                  
                }
                _UIFrame.setarries(Headers);

                _res = _ChooserFile.showOpenDialog(_UIFrame);
                if (_res == JFileChooser.APPROVE_OPTION) {
                    File lineFile = _ChooserFile.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    
                    if(!_InvoiceHeaderFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                    
                    List<String> lineLines = Files.readAllLines(linePath);
                    ArrayList<LineModel> invoiceLines = new ArrayList<>();
                    for (String line : lineLines) {
                        String[] Strings = line.split(",");
                        String arr1 = Strings[0];    // invoice num (int)
                        String arr2 = Strings[1];    // item name   (String)
                        String arr3 = Strings[2];    // price       (double)
                        String arr4 = Strings[3];    // count       (int)
                        int Code = Integer.parseInt(arr1);
                        double price = Double.parseDouble(arr3);
                        int count = Integer.parseInt(arr4);
                        HeaderModel inv = _UIFrame.getInvoices(Code);
                        LineModel invoiceLine = new LineModel(arr2, price, count, inv);
                        inv.getLines().add(invoiceLine);
                    }
                }
                InoviceHeaderTable headerTable = new InoviceHeaderTable(Headers);
                _UIFrame.setTheheaderTable(headerTable);
                _UIFrame.gettheInvoicetbl().setModel(headerTable);
                System.out.println("files read");
            }

        } 
        
      
      
        catch (Exception ex) {
            JOptionPane.showMessageDialog(_UIFrame, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }

    private void CreateNewInvoice() {
        _InvoiceheaderPopup = new HeaderFrameView(_UIFrame);
        _InvoiceheaderPopup.setVisible(true);
    }

    private void DeleteInvoice() {
        int selectedInvoiceIndex = _UIFrame.gettheInvoicetbl().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            _UIFrame.getlstofLines().remove(selectedInvoiceIndex);
            _UIFrame.getTheheaderTable().fireTableDataChanged();

            _UIFrame.getTheLinetbl().setModel(new InvoiceLinesTable(null));
            _UIFrame.setlstofLines(null);
            _UIFrame.getTheNameofCustLbl().setText("");
            _UIFrame.getNumberOfTheInvoiceInsideLabel().setText("");
            _UIFrame.getTheTotalOfTheInvoicelabel().setText("");
            _UIFrame.getTheDateINsideTheLabel().setText("");
        }
    }

    private void SaveChanges() {
        _InvoiceLinePopup = new LinesFrameView(_UIFrame);
        _InvoiceLinePopup.setVisible(true);
    }

    private void cancel() {
        int selectedLineIndex = _UIFrame.getTheLinetbl().getSelectedRow();
        int selectedInvoiceIndex = _UIFrame.gettheInvoicetbl().getSelectedRow();
        if (selectedLineIndex != -1) {
            _UIFrame.getLinesArray().remove(selectedLineIndex);
            InvoiceLinesTable lineTableModel = (InvoiceLinesTable) _UIFrame.getTheLinetbl().getModel();
            lineTableModel.fireTableDataChanged();
            _UIFrame.getTheTotalOfTheInvoicelabel().setText("" + _UIFrame.getlstofLines().get(selectedInvoiceIndex).getItemTotal());
            _UIFrame.getTheheaderTable().fireTableDataChanged();
            _UIFrame.gettheInvoicetbl().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void savefile() {
        ArrayList<HeaderModel> invoicesArray = _UIFrame.getlstofLines();
        JFileChooser fc = new JFileChooser();
        try {
            int _res = fc.showSaveDialog(_UIFrame);
            if (_res == JFileChooser.APPROVE_OPTION) {
                File _InvoiceHeaderFile = fc.getSelectedFile();
                if(!_InvoiceHeaderFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                   
                FileWriter hfw = new FileWriter(_InvoiceHeaderFile);
                String headers = "";
                String lines = "";
                for (HeaderModel invoice : invoicesArray) {
                    headers += invoice.toString();
                    headers += "\n";
                    for (LineModel line : invoice.getLines()) {
                        lines += line.toString();
                        lines += "\n";
                    }
                }
                
                headers = headers.substring(0, headers.length()-1);
                lines = lines.substring(0, lines.length()-1);
                _res = fc.showSaveDialog(_UIFrame);
                File lineFile = fc.getSelectedFile();
                 if(!lineFile.getAbsolutePath().endsWith(".csv")){
                     JOptionPane.showMessageDialog(_UIFrame, "Wrong file format", "Error", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                FileWriter lfw = new FileWriter(lineFile);
                hfw.write(headers);
                lfw.write(lines);
                hfw.close();
                lfw.close();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Folder/File path is not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void  newLineDialogCancel() {
        _InvoiceLinePopup.setVisible(false);
        _InvoiceLinePopup.dispose();
        _InvoiceLinePopup = null;
    }
  

    private void newInvoiceDialogOK() {
        _InvoiceheaderPopup.setVisible(false);

        String custName = _InvoiceheaderPopup.getCustomerNameTextField().getText();
        String str = _InvoiceheaderPopup.getDateTextField().getText();
        Date d = new Date();
        
 
        try {
             SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
             d = dateFormat.parse(str);
             int invNum = 0;
        for (HeaderModel inv : _UIFrame.getlstofLines()) {
            if (inv.getNum() > invNum) {
                invNum = inv.getNum();
            }
        }
        invNum++;
        HeaderModel newInv = new HeaderModel(invNum, custName, d);
        _UIFrame.getlstofLines().add(newInv);
       _UIFrame.getTheheaderTable().fireTableDataChanged();
       _InvoiceheaderPopup.dispose();
        _InvoiceheaderPopup = null;
        } 
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Wrong date format", "Invalid date format", JOptionPane.ERROR_MESSAGE);
        }

        
    }

    private void newInvoiceDialogCancel() {
        _InvoiceheaderPopup.setVisible(false);
        _InvoiceheaderPopup.dispose();
        _InvoiceheaderPopup = null;
    }

    private void newLineDialogOK() {
        _InvoiceLinePopup.setVisible(false);

        String name = _InvoiceLinePopup.getItemNameTextField().getText();
        String str1 = _InvoiceLinePopup.getItemCountTextField().getText();
        String StringNum2 = _InvoiceLinePopup.getItemPriceTextField().getText();
        int count = 1;
        double price = 1;
        try {
            count = Integer.parseInt(str1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Cannot convert number", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }

        try {
            price = Double.parseDouble(StringNum2);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(_UIFrame, "Cannot convert price", "Invalid number format", JOptionPane.ERROR_MESSAGE);
        }
        int selectedInvHeader = _UIFrame.gettheInvoicetbl().getSelectedRow();
                 if (selectedInvHeader != -1) {
            HeaderModel invHeader = _UIFrame.getlstofLines().get(selectedInvHeader);
            LineModel line = new LineModel(name, price, count, invHeader);
            //invHeader.getLines().add(line);
            _UIFrame.getLinesArray().add(line);
            InvoiceLinesTable lineTable = (InvoiceLinesTable) _UIFrame.getTheLinetbl().getModel();
            lineTable.fireTableDataChanged();
            _UIFrame.getTheheaderTable().fireTableDataChanged();
        }
        _UIFrame.gettheInvoicetbl().setRowSelectionInterval(selectedInvHeader, selectedInvHeader);
        _InvoiceLinePopup.dispose();
        _InvoiceLinePopup = null;
    }

    

  

}