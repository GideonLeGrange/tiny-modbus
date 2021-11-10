package me.legrange.modbus;

/**
 * An abstract representation of a Modbus frame. Subclassed for specifc use cases. 
 * 
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
abstract class ModbusFrame {

    protected final byte frame[];

    /**
     * Create a new frame from the supplied raw Modbus data. 
     * @param frame The data.
     * @throws CrcException Thrown if the data fails the CRC check. 
     */
    ModbusFrame(byte frame[]) throws CrcException {
        this.frame = new byte[frame.length];
        System.arraycopy(frame, 0, this.frame, 0, frame.length);
        FrameUtil.validate(frame);
    }
    
    /** Return the Modbus function code */
    public byte getFunction() { 
        return frame[1];
    }
        
    public int getSlaveId() {
        return frame[0];
    }

    /** Return the frame as raw Modbus data. */
    byte[] asBytes() {
        return frame;
    }

    @Override
    public String toString() {
        return FrameUtil.hexString(frame);
    }


}
