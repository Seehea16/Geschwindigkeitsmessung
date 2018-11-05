package Geschwindigkeitsmessung;

import java.awt.Color;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class VelocityTableModell extends AbstractTableModel {
    protected List<Measurement> liste = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return liste.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getColumnCount() {
        return MeasurementColumnEnum.values().length;
    }
    
    public Measurement getMeasurement(int rowIndex) {
        return this.liste.get(rowIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return MeasurementColumnEnum.values()[columnIndex].getName();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Measurement m = this.liste.get(rowIndex);

        MeasurementColumnEnum enumIndex = MeasurementColumnEnum.values()[columnIndex];
        switch (enumIndex) {
            case DATUM: return m.getDateAsString();
            case UHRZEIT: return m.getTimeAsString();
            case KENNZEICHEN: return m.getKennz();
            case GEMESSEN: return m.getGeschw();
            case ERLAUBT: return m.getErl();
            case UEBERTRETUNG: return m.getUeber();
            default: return "?";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Measurement mAlt = this.liste.get(rowIndex);

        LocalDate dateAlt = mAlt.getDate();
        LocalTime timeAlt = mAlt.getTime();
        String kennzAlt = mAlt.getKennz();
        int gemAlt = mAlt.getGeschw();
        int erlAlt = mAlt.getErl();

        MeasurementColumnEnum enumIndex = MeasurementColumnEnum.values()[columnIndex];
        try {
            switch(enumIndex) {
                case DATUM: dateAlt = LocalDate.parse(aValue+"", Measurement.DTFDATE);
                                break;
                case UHRZEIT: timeAlt = LocalTime.parse(aValue+"", Measurement.DTFTIME);
                                break;
                case KENNZEICHEN: kennzAlt = aValue+"";
                                break;
                case GEMESSEN: gemAlt = Integer.parseInt(aValue+"");
                                break;
                case ERLAUBT: erlAlt = Integer.parseInt(aValue+"");
                                break;
            }
            Measurement mNeu = new Measurement(dateAlt, timeAlt, kennzAlt, gemAlt, erlAlt);
            this.change(rowIndex, mNeu);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Fehler: " + e.toString(), "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex != 5) {
            return true;
        }
        return false;
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
