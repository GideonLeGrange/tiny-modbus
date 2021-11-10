package me.legrange.modbus;

/**
 * Thrown if there a problem with the Modbus serial port.
 *
 * @author Gideon le Grange https://github.com/GideonLeGrange
 * @since 1.0
 */
public final class SerialException extends ModbusException {

    SerialException(String message) {
        super(message);
    }

    SerialException(String message, Throwable cause) {
        super(message, cause);
    }


}
