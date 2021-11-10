package me.legrange.modbus;

import java.nio.ByteBuffer;
import net.objecthunter.exp4j.Expression;

/**
 *
 * @author gideon
 */
public interface ModbusRegister {
    
    enum Type { FLOAT, INT; }
    
    /**
     * Return the name of the register.
     * @return The name.
     */
    String getName();
    
    /** 
     * Return the register address. 
     * @return The address.
     */
    int getAddress();
    
    /**
     * Return the register length in 2 byte words. 
     * @return The length. 
     */
    int getLength();

    /** 
     * Return the transformation to apply to the register. 
     * @return The transformation expression. 
     */
    Expression getTransform();
 
    /** 
     * Return the register type. 
     * @return The typpe.
     */
    Type getType();

    
    /** 
     * Decode the given bytes received for the given register into a double value. 
     * 
     * @param reg The register for which the bytes was received. 
     * @param bytes The bytes to decode. 
     * @return The decoded value
     */
    static double decode(ModbusRegister reg, byte bytes[]) {
        switch (reg.getType()) {
            case FLOAT :
                return decodeFloat(reg, bytes);
            default : return 0.0;
        }
    }
    
    static double decodeFloat(ModbusRegister reg, byte bytes[]) {
        float f = ByteBuffer.wrap(new byte[]{bytes[2], bytes[3], bytes[0], bytes[1]}).getFloat();
        return reg.getTransform().setVariable("_", f).evaluate();
    }
    
    static double decodeInt(ModbusRegister reg, int words[]) {
        long lval = 0;
        for (int i = 0; i < words.length; ++i) {
            lval = (lval << 8) | words[i];
        }
        return reg.getTransform().setVariable("_", lval).evaluate();
    }

}
