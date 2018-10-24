package Geschwindigkeitsmessung;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class VelocityTableRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Measurement m = (Measurement) value;
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        
        MeasurementColumnEnum enumIndex = MeasurementColumnEnum.values()[column];
        
        switch(enumIndex) {
            case DATUM: this.setText(m.getDateAsString());
                    break;
            case UHRZEIT: this.setText(m.getTimeAsString());
                    break;
            case KENNZEICHEN: this.setText(m.getKennz());
                    break;
            case GEMESSEN: this.setText(m.getGeschw()+"");
                    break;
            case ERLAUBT: this.setText(m.getErl()+"");
                    break;
            case UEBERTRETUNG: int diff = m.getUeber();
                                this.setText(diff+"");
                                if(diff >= 30) {
                                    this.setBackground(Color.RED);
                                } else if(diff >= 20) {
                                    this.setBackground(Color.ORANGE);
                                } else if(diff >= 10) {
                                    this.setBackground(Color.YELLOW);
                                } else {
                                    this.setBackground(Color.BLUE);
                                }
                    break;
        }
        return this;
    }  
}
