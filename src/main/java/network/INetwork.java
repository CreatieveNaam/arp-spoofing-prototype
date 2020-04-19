package network;

import network.device.RemoteNetworkDevice;
import org.pcap4j.util.MacAddress;

import java.util.ArrayList;

public interface INetwork {
    void addDevice(String MacAddress, String ip);
    MacAddress getMacAddress(String ip);
    ArrayList<RemoteNetworkDevice> getDevices();
}
