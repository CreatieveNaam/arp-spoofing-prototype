package packet.builder;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.namednumber.ArpOperation;

public interface IPacketBuilder {
    EthernetPacket buildArp(ArpOperation operation, String srcMac, String srcIP, String dstMac, String dstIP);
}
