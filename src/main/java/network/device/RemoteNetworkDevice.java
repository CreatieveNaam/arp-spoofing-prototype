package network.device;

import org.pcap4j.util.MacAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RemoteNetworkDevice {
    private InetAddress ip; // Should be final
    private final MacAddress macAddress;

    public RemoteNetworkDevice(String macAddress, String ip) {
        this.macAddress = MacAddress.getByName(macAddress);

        try {
            this.ip = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public MacAddress getMacAddress() {
        return macAddress;
    }

    public InetAddress getIp() {
        return ip;
    }
}
