package SRC.COM.SUNRISE.CLIENT;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

//added by geetha
// this class makes a cell in the dtm to be made editable
public class MyComboBoxEditor extends  DefaultCellEditor{

    static final long serialVersionUID = 1L;
    
	public MyComboBoxEditor(String[] array_string_items) 
	{
            super(new JComboBox(array_string_items));
        }
}
