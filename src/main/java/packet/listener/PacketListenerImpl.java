package packet.listener;

import network.device.LocalNetworkDevice;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.packet.Packet;
import packet.handler.PacketHandlerFactory;
import packet.handler.IPacketHandler;

class PacketListenerImpl implements IPacketListenerImpl  {
    private static final int MAX_PACKETS = 500;
    private final PcapHandle handle = setHandle();
    private final IPacketHandler packetHandler;

    public PacketListenerImpl() {
        packetHandler = PacketHandlerFactory.getArpPacketHandler();
    }

    @Override
    public void gotPacket(Packet packet) {
        packetHandler.handle(packet);
    }

    @Override
    public void startListening() {
        try {
            handle.loop(MAX_PACKETS, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        handle.close();
    }

    private PcapHandle setHandle() {
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
