package network.device;

import org.pcap4j.util.MacAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class NetworkDevice {
    protected InetAddress ip; // Should be final
    protected final MacAddress macAddress;

    public NetworkDevice(String mac, String ip) {
        this.macAddress = MacAddress.getByName(mac);

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
