package uk.co.hexeption.darkforgereborn.mod.options;

/**
 * ValueDouble
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 09/11/2019 - 11:37 am
 */
public class ValueDouble extends Value {

    public double[] limit;
    public double step;

    public ValueDouble(double value, double[] limit, double step) {
        super(value);
        this.limit = limit;
        this.step = step;
    }
}
