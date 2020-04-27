package packet.handler;

import org.pcap4j.packet.Packet;

public interface IPacketHandler {
    void handle(Packet packet);
}
