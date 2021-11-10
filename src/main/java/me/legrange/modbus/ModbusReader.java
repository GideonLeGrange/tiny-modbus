package me.legrange.modbus;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.legrange.modbus.ModbusError;
import me.legrange.modbus.ModbusException;
import me.legrange.modbus.ReadInputRegisters;
import me.legrange.modbus.ResponseFrame;
import me.legrange.modbus.SerialModbusPort;

/**
 *
 * @since 1.0
 * @author Gideon le Grange https://github.com/GideonLeGrange
 */
public final class ModbusReader {

    private boolean running;
    private long pollInterval = 60000;
    private String name;
    private final int deviceId;
    private SerialModbusPort modbus;
    private final List<ModbusListener> listeners = new LinkedList<>();
    private final List<ModbusRegister> registers = new LinkedList<>();
    private List<Poll> polls = null;
    private final boolean zeroBased;

    public ModbusReader(SerialModbusPort modbus, String name, int deviceId, boolean zeroBased) {
        this.name = name;
        this.deviceId = deviceId;
        this.zeroBased = zeroBased;
        this.modbus = modbus;
    }

    public void setPollInterval(int interval) {
        this.pollInterval = interval * 1000;
    }

    public void addRegister(ModbusRegister reg) {
        registers.add(reg);
        polls = null;
    }

    public void addListener(ModbusListener listener) {
        listeners.add(listener);
    }

    public void start() {
        Thread t = new Thread(this::run, "Modbus poller");
        t.setDaemon(true);
        t.start();
    }

    public void stop() {
        running = false;
    }

    private void run() {
        running = true;
        while (running) {
            long start = System.currentTimeMillis();
            List<Poll> polls = getPolls();
            for (Poll poll : polls) {
                try {
                    int addr = (zeroBased ? poll.getAddress() - 1 : poll.getAddress());
                    ReadInputRegisters req = new ReadInputRegisters(deviceId, addr, poll.getSize());
                    ResponseFrame res = modbus.poll(req);
                    for (ModbusListener l : listeners) {
                        if (!res.isError()) {
                            poll.applyBytes(res.getBytes(), l);
                        } else {
                            l.error(new ModbusReaderException(String.format("Modbus error: %s", ModbusError.valueOf(res.getFunction()))));
                        }
                    }

                } catch (ModbusException ex) {
                    for (ModbusListener l : listeners) {
                        l.error(new ModbusReaderException(ex.getMessage(), ex));
                    }
                }
            }
            long stop = System.currentTimeMillis();
            try {
                TimeUnit.MILLISECONDS.sleep(pollInterval - (stop - start));
            } catch (InterruptedException ex) {
            }
        }

    }

    private List<Poll> getPolls() {
        if (polls == null) {
            polls = Poll.generatePolls(registers);
        }
        return polls;
    }

}
