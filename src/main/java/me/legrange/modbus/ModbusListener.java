package me.legrange.modbus;

/**
 *
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
public interface ModbusListener {
    
    /** A Modbus response was received
     * 
     * @param reg The register form which the response was received.
     * @param bytes The response value as byte array
     */
    void received(ModbusRegister reg, byte bytes[]);
    
    /** An error occurred while doing a Modbus request 
     * 
     * @param e The exception that occurred. 
     */
    void error(Throwable e);

}
