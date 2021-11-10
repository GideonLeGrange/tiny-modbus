package me.legrange.modbus;

/**
 * A Modbus request frame (function 4).
 *
 * @author Gideon le Grange https://github.com/GideonLeGrange
 * @since 1.0
 */
public final class WriteRegister extends ModbusFrame {

    /**
     * Create a new request frame to write a single register value
     *
     * @param deviceId The device (slave) to talk to.
     * @param register The register to start writing.
     * @throws CrcException
     */
    public WriteRegister(int deviceId, int register, int data) throws CrcException {
        super(FrameUtil.withCrc(
                new byte[]{
                        (byte) deviceId,
                        (byte) 6, // function code
                        (byte) ((register & 0xff00) >> 8),
                        (byte) (register & 0xff),
                        (byte) ((data & 0xff00) >> 8),
                        (byte) (data & 0xff)}));
    }

}
