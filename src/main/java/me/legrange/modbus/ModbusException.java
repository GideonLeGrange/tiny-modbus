package me.legrange.modbus;

/**
 * Thrown by the Modbus library if there an error while handling Modbus. Different implementations
 * signal different error conditions. 
 * 
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
public abstract class ModbusException extends Exception {

     ModbusException(String message) {
        super(message);
    }

     ModbusException(String message, Throwable cause) {
        super(message, cause);
    }

}
