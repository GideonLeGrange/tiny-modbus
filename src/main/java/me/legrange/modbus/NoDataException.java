package me.legrange.modbus;

/**
 * Thrown if no Modbus response is received for a request. 
 * 
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
public final class NoDataException extends ModbusException {

    NoDataException(String message) {
        super(message);
    }

    

}
