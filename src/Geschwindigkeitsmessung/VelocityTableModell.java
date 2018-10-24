package Geschwindigkeitsmessung;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import javax.swing.table.AbstractTableModel;

public class VelocityTableModell extends AbstractTableModel {
    protected List<Measurement> liste = new ArrayList<>();
    
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
        if(Double.isNaN(avg)) {
            return 0;
        } else {
            return avg;
        }
    }
    
    public void add(Measurement m) {
        this.liste.add(m);
        this.sort();
    }
    
    public void sort() {
        Collections.sort(this.liste, new CompUeber());
        this.fireTableDataChanged();
    }
    
    public void change(int index, Measurement neu) {
        this.liste.set(index, neu);
        this.sort();
    }
    
    public void delete(int[] indices) {
        for(int i = 0; i < indices.length; i++) {
            this.liste.remove(indices[i]-i);
        }
        this.fireTableDataChanged();
    }
    
    public void save(File f, ListIterator<Measurement> ms) throws Exception {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            while(ms.hasNext()) {
                Measurement m = ms.next();
                oos.writeObject(m);
            }
        } catch(IOException e) {
            System.err.println("Save:" + e.getMessage());
            throw e;
        } finally {
            fos.close();
        }
    }
    
    public ArrayList<Measurement> load(File f) throws Exception {
        InputStream fis = null;
        ArrayList<Measurement> objs = new ArrayList<>();
        try {
            fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while(true) {
                Measurement m = (Measurement) ois.readObject();
                objs.add(m);
            }
        } catch(EOFException e) {
            System.err.println("Load: EOF erkannt!");
            return objs;
        } catch(IOException e) {
            System.err.println("Load:" + e.getMessage());
        } finally {
            fis.close();
        }
        return null;
    }

    public void clear() {
        this.liste.clear();
    }
}
