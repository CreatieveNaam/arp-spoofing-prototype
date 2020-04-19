package network;

import network.device.RemoteNetworkDevice;
import org.pcap4j.util.MacAddress;

import java.util.ArrayList;

class Network implements INetwork {
    private static final ArrayList<RemoteNetworkDevice> remoteDevices = new ArrayList<>();

    @Override
    public void addDevice(String MacAddress, String ip) {
        remoteDevices.add(new RemoteNetworkDevice(MacAddress, ip));
    }

    @Override
    public MacAddress getMacAddress(String ip) {
        for (RemoteNetworkDevice device : remoteDevices) {
            if (device.getIp().getHostAddress().equals(ip)) {
                return device.getMacAddress();
            }
        }

        throw new RuntimeException("Couldn't find IP");
    }

    @Override
    public ArrayList<RemoteNetworkDevice> getDevices() {
        return remoteDevices;
    }
}
