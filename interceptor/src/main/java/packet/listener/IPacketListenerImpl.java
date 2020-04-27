package packet.listener;

import org.pcap4j.core.PacketListener;

public interface IPacketListenerImpl extends PacketListener {
    void startListening();
}
