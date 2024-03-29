package me.legrange.modbus;

/**
 * A Modbus request frame (function 4).
 *
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
public final class ReadInputRegisters extends ModbusFrame {

    /**
     * Create a new request frame to read a sequence of words from the given
     * device at the register and of the given size.
     *
     * @param deviceId The device (slave) to talk to.
     * @param register The register to start reading.
     * @param size The number of words to read.
     * @throws CrcException
     */
    public ReadInputRegisters(int deviceId, int register, int size) throws CrcException {
        super(FrameUtil.withCrc(new byte[]{
            (byte) deviceId,
            (byte) 4, // function code
            (byte) ((register & 0xff00) >> 8),
            (byte) (register & 0xff),
            (byte) ((size & 0xff00) >> 8),
            (byte) (size & 0xff)})
        );
    }

}
