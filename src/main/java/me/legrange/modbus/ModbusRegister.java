package me.legrange.modbus;

import java.nio.ByteBuffer;

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
        return ByteBuffer.wrap(new byte[]{bytes[2], bytes[3], bytes[0], bytes[1]}).getFloat();
    }

}
