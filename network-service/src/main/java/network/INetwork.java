package network;

import network.device.RemoteNetworkDevice;


import java.util.ArrayList;

public interface INetwork {
    ArrayList<RemoteNetworkDevice> getDevices();
}
