package packet.sender;

import org.pcap4j.packet.Packet;

public interface IPacketSender {
    void send(Packet packet);
}
