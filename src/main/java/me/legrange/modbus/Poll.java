package me.legrange.modbus;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A modbus poll operation. 
 * @author gideon
 */
class Poll {
 
    private final int address;
    private int size;
    private final Map<Integer, ModbusRegister> registers = new HashMap<>();
    
    private Poll(ModbusRegister reg) {
        this.address = reg.getAddress();
        this.size = reg.getLength();
        registers.put(0, reg);
    }
    
    private void add(ModbusRegister reg) { 
        size = size + reg.getLength();
        registers.put(reg.getAddress() - address, reg);
    }
    
    int getAddress() {
        return address;
    }

    int getSize() {
        return size;
    };
    
    /** call listener.received() for each register and it's subset of bytes in the response received 
     * 
     * @param bytes The bytes received for all registers polled. 
     * @param listener The listener to call. 
     */
    void applyBytes(byte bytes[], ModbusListener listener) {
        registers.keySet().stream().map((offset) -> registers.get(offset)).forEach((ModbusRegister reg) -> {
            byte buf[] = new byte[reg.getLength()*2];
            System.arraycopy(bytes, (reg.getAddress() - address)*2, buf, 0, reg.getLength()*2);
            listener.received(reg, buf);
        });
    }
    
    static List<Poll> generatePolls(List<ModbusRegister> registers) {
        Collections.sort(registers, Comparator.comparingInt(ModbusRegister::getAddress));
        Poll poll = null;
        List<Poll> result = new LinkedList<>();
        for (ModbusRegister reg : registers) {
            if ((poll != null) && ((poll.address + poll.size) == reg.getAddress())) { 
                poll.add(reg);
            }
            else {
                poll = new Poll(reg);
                result.add(poll);
            }
        }
        return result;
    }
    
}
