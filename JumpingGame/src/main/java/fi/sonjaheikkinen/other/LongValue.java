
package fi.sonjaheikkinen.other;

/**
 *Luokka kapseloi long-tyyppisen muuttujan.
 */
public class LongValue {
    
    long value;
    
    public LongValue(long value) {
        this.value = value;
    }
    
    public long getValue() {
        return this.value;
    }
    
    public void setValue(long value) {
        this.value = value;
    }
}
