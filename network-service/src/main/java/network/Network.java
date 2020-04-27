package network;

import network.device.RemoteNetworkDevice;
import java.util.ArrayList;

class Network implements INetwork {
    private static final ArrayList<RemoteNetworkDevice> remoteDevices = new ArrayList<>();

    public Network() {
        discoverDevicesOnInterface();
    }

    @Override
    public ArrayList<RemoteNetworkDevice> getDevices() {
        return remoteDevices;
    }

    private void discoverDevicesOnInterface() {
        remoteDevices.add(new RemoteNetworkDevice("08:00:27:5d:cd:42", "192.168.56.101"));
        remoteDevices.add(new RemoteNetworkDevice("08:00:27:74:f8:f0", "192.168.56.102"));
    }

}
