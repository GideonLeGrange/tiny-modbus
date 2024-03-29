package me.legrange.modbus;

/**
 * Common Modbus erorrs with their codes and messages. 
 * 
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
public enum ModbusError {
    
    ILLEGAL_FUNCTION(1, "Illegal Function"),
    ILLEGAL_DATA_ADDRESS(2, "Illegal Data Address"),
    ILLEGAL_DATA_VALUE(3, "Illegal Data Value"),
    SLAVE_DEVICE_FAILURE(4, "Slave Device Failure"),
    ACKNOWLEDGE(5, "Acknowledge"),
    SLAVE_DEVICE_BUSY(6, "Slave Device Busy"),
    NEGATIVE_ACKNOWLEDGE(7, "Negative Acknowledge"),
    MEMORY_PARITY_ERROR(8, "Memory Parity Error"),
    UNKNOWN_ERROR(127,"Unknown Error");
    
    /**
     * Find the error best describing the given error code. 
     * @param code The code.
     * @return The error.
     */
     static ModbusError valueOf(byte code) {
        switch (code) {
            case 1 : return ILLEGAL_FUNCTION;
            case 2 : return ILLEGAL_DATA_ADDRESS; //(2, "Illegal Data Address"),
            case 3 : return ILLEGAL_DATA_VALUE; //(3, "Illegal Data Value"),
            case 4 : return SLAVE_DEVICE_FAILURE; //(4, "Slave Device Failure"),
            case 5 : return ACKNOWLEDGE; //(5, "Acknowledge"),
            case 6 : return SLAVE_DEVICE_BUSY;//(6, "Slave Device Busy"),
            case 7 : return NEGATIVE_ACKNOWLEDGE; //(7, "Negative Acknowledge"),
            case 8 : return MEMORY_PARITY_ERROR; //(8, "Memory Parity Error"),
            default : return UNKNOWN_ERROR; //(127,"Unknown Error");
                
        }
    }
    
    /** 
     * Get the text description of an error.
     * 
     * @return The description. 
     */
    public String getText() { 
        return text;
    }
    
    private ModbusError(int code, String text) {
        this.code = code;
        this.text = text;
    }
    
    private final int code;
    private final String text;
    
}