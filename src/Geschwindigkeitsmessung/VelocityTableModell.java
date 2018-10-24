package Geschwindigkeitsmessung;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class VelocityTableModell extends AbstractTableModel {
    private List<Measurement> liste = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return liste.size();
    }

    @Override
    public int getColumnCount() {
        return MeasurementColumnEnum.values().length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return MeasurementColumnEnum.values()[columnIndex].getName();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Measurement m = liste.get(rowIndex);
        return m;
    }
    
    public double getAvgUeber() {
        double avg = 0;
        for(Measurement m : this.liste) {
            avg += m.getUeber(); 
        }
        avg /= this.getRowCount();
        return avg;
    }
}
