package Geschwindigkeitsmessung;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class VelocityTableRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        VelocityTableModell model = (VelocityTableModell) table.getModel();
        Measurement m = model.getMeasurement(row);
        this.setBackground(Color.WHITE);
        
        MeasurementColumnEnum enumIndex = MeasurementColumnEnum.values()[column];
        
        if(enumIndex == MeasurementColumnEnum.UEBERTRETUNG) {
            int diff = m.getUeber();
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
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }  
}
