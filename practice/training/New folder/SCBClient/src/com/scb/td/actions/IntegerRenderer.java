package com.scb.td.actions;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class IntegerRenderer implements TableCellRenderer{    
    private JTextField textField;
    NumberFormat nf = NumberFormat.getInstance(Locale.UK);
        
    public IntegerRenderer(){
        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        nf.setGroupingUsed(true);
    }        
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(value!=null && value.toString().trim().length()>0)
            textField.setText(nf.format(Integer.valueOf(value.toString())));
        else textField.setText("");
        textField.setFont(table.getFont());
        if(hasFocus)        
            textField.setBorder(BorderFactory.createLineBorder(new Color(0.4f,0.55f,0.6f)));
        else {
            if(isSelected)
                textField.setBorder(BorderFactory.createLineBorder(table.getSelectionBackground(),1));
            else
                textField.setBorder(BorderFactory.createEmptyBorder());
        }
        
        if(isSelected) {
            textField.setForeground(table.getSelectionForeground());
            textField.setBackground(table.getSelectionBackground());
        }
        else {
            textField.setForeground(table.getForeground());
            textField.setBackground(table.getBackground());
        }
        return textField;
    }    
}