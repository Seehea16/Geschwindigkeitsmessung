package Geschwindigkeitsmessung;

import java.util.Comparator;

public class CompUeber<T> implements Comparator<T> {

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(this == obj) {
            return true;
        }
        if(this.getClass() == obj.getClass()) {
            return true;
        }
        return false;
    }
    
    @Override
    public int compare(T o1, T o2) {
        int eins = ((Measurement)o1).getUeber();
        int zwei = ((Measurement)o2).getUeber();
        if(eins > zwei) {
            return 1;
        } else if(zwei > eins) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}
