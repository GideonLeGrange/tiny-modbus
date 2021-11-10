package me.legrange.modbus;

/**
 *
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
public final class ModbusReaderException extends ModbusException {

    public ModbusReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModbusReaderException(String message) {
        super(message);
    }
    

}
