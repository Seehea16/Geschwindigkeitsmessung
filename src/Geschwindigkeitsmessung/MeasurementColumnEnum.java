package Geschwindigkeitsmessung;

public enum MeasurementColumnEnum {
    DATUM("Datum"), UHRZEIT("Uhrzeit"), KENNZEICHEN("Kennzeichen"),
    GEMESSEN("Gemessen"), ERLAUBT("Erlaubt"), UEBERTRETUNG("Übertretung");
    
    private String name;

    private MeasurementColumnEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
