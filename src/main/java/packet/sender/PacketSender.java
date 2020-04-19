package packet.sender;

import network.device.LocalNetworkDevice;
import org.pcap4j.core.PcapHandle;

import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.Packet;

class PacketSender implements IPacketSender {
    private final PcapHandle sendHandle = setSendHandle();

    @Override
    public void send(Packet packet) {

        try {
            sendHandle.sendPacket(packet);
            sendHandle.breakLoop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Todo: close handle?
    }

    private PcapHandle setSendHandle() {
        try {
            PcapNetworkInterface device = LocalNetworkDevice.getNetworkDevice();
            int snapshotLength = 65536; // in bytes
            int readTimeout = 50; // in milliseconds

            PcapHandle dev = device.openLive(snapshotLength, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, readTimeout);
            dev.setBlockingMode(PcapHandle.BlockingMode.BLOCKING);
            return dev;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
