package com.example.plifeterminaldesktop;
import com.sun.javafx.print.Units;
public final class MyPaper {
    private String name;
    private double width;
    private double height;
    private Units units;
    public static final MyPaper A0;
    public static final MyPaper A1;
    public static final MyPaper A2;
    public static final MyPaper A3;
    public static final MyPaper A4;
    public static final MyPaper A5;
    public static final MyPaper A6;
    public static final MyPaper DESIGNATED_LONG;
    public static final MyPaper NA_LETTER;
    public static final MyPaper LEGAL;
    public static final MyPaper TABLOID;
    public static final MyPaper EXECUTIVE;
    public static final MyPaper NA_8X10;
    public static final MyPaper MONARCH_ENVELOPE;
    public static final MyPaper NA_NUMBER_10_ENVELOPE;
    public static final MyPaper C;
    public static final MyPaper JIS_B4;
    public static final MyPaper JIS_B5;
    public static final MyPaper JIS_B6;
    public static final MyPaper JAPANESE_POSTCARD;
    MyPaper(String var1, double var2, double var4, Units var6) throws IllegalArgumentException {
        if (!(var2 <= 0.0) && !(var4 <= 0.0)) {
            if (var1 == null) {
                throw new IllegalArgumentException("Null name");
            } else {
                this.name = var1;
                this.width = var2;
                this.height = var4;
                this.units = var6;
            }
        } else {
            throw new IllegalArgumentException("Illegal dimension");
        }
    }

    public final String getName() {
        return this.name;
    }

    private double getSizeInPoints(double var1) {
        switch (this.units) {
            case POINT:
                return (double)((int)(var1 + 0.5));
            case INCH:
                return (double)((int)(var1 * 72.0 + 0.5));
            case MM:
                return (double)((int)(var1 * 72.0 / 25.4 + 0.5));
            default:
                return var1;
        }
    }

    public final double getWidth() {
        return this.getSizeInPoints(this.width);
    }

    public final double getHeight() {
        return this.getSizeInPoints(this.height);
    }

    public final int hashCode() {
        return (int)this.width + ((int)this.height << 16) + this.units.hashCode();
    }

    public final boolean equals(Object var1) {
        return var1 != null && var1 instanceof MyPaper && this.name.equals(((MyPaper)var1).name) && this.width == ((MyPaper)var1).width && this.height == ((MyPaper)var1).height && this.units == ((MyPaper)var1).units;
    }

    public final String toString() {
        return "Paper: " + this.name + " size=" + this.width + "x" + this.height + " " + this.units;
    }

    static {
        A0 = new MyPaper("A0", 841.0, 1189.0, Units.MM);
        A1 = new MyPaper("A1", 594.0, 841.0, Units.MM);
        A2 = new MyPaper("A2", 420.0, 594.0, Units.MM);
        A3 = new MyPaper("A3", 297.0, 420.0, Units.MM);
        A4 = new MyPaper("A4", 210.0, 297.0, Units.MM);
        A5 = new MyPaper("A5", 148.0, 210.0, Units.MM);
        A6 = new MyPaper("A6", 105.0, 148.0, Units.MM);
        DESIGNATED_LONG = new MyPaper("Designated Long", 110.0, 220.0, Units.MM);
        NA_LETTER = new MyPaper("Letter", 8.5, 11.0, Units.INCH);
        LEGAL = new MyPaper("Legal", 8.4, 14.0, Units.INCH);
        TABLOID = new MyPaper("Tabloid", 11.0, 17.0, Units.INCH);
        EXECUTIVE = new MyPaper("Executive", 7.25, 10.5, Units.INCH);
        NA_8X10 = new MyPaper("8x10", 8.0, 10.0, Units.INCH);
        MONARCH_ENVELOPE = new MyPaper("Monarch Envelope", 3.87, 7.5, Units.INCH);
        NA_NUMBER_10_ENVELOPE = new MyPaper("Number 10 Envelope", 4.125, 9.5, Units.INCH);
        C = new MyPaper("C", 17.0, 22.0, Units.INCH);
        JIS_B4 = new MyPaper("B4", 257.0, 364.0, Units.MM);
        JIS_B5 = new MyPaper("B5", 182.0, 257.0, Units.MM);
        JIS_B6 = new MyPaper("B6", 128.0, 182.0, Units.MM);
        JAPANESE_POSTCARD = new MyPaper("Japanese Postcard", 100.0, 148.0, Units.MM);
    }
}
