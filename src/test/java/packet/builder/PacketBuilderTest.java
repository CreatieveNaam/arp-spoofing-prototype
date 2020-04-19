package packet.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;

import static org.junit.jupiter.api.Assertions.*;


class PacketBuilderTest {
    private IPacketBuilder sut;

    @BeforeEach
    public void setup() {
        sut = new PacketBuilder();
    }

    @Test
    public void craftArpReplyPacket() {
        // Given
        String srcMac = "0a:00:27:00:00:13";
        String srcIP = "192.168.2.10";
        String dstMac = "08:00:27:e4:cd:64";
        String dstIP = "192.168.2.76";

        // When
        EthernetPacket packet = sut.buildArp(ArpOperation.REPLY, srcMac, srcIP, dstMac, dstIP);

        // Then
        assertEquals(srcMac, packet.getHeader().getSrcAddr().toString());
        assertEquals(dstMac, packet.getHeader().getDstAddr().toString());
        assertEquals(EtherType.ARP, packet.getHeader().getType());

        assertTrue(packet.getPayload() instanceof ArpPacket);
        ArpPacket arpPacket = (ArpPacket) packet.getPayload();

        assertEquals(srcMac, arpPacket.getHeader().getSrcHardwareAddr().toString());
        assertEquals(srcIP, arpPacket.getHeader().getSrcProtocolAddr().getHostAddress());
        assertEquals(dstMac, arpPacket.getHeader().getDstHardwareAddr().toString());
        assertEquals(dstIP, arpPacket.getHeader().getDstProtocolAddr().getHostAddress());
        assertEquals(ArpOperation.REPLY, arpPacket.getHeader().getOperation());
    }

    @Test
    public void throwsExceptionWhenBuildingArpWithInvalidMacAddress() {
        // Given
        String invalidMac = "0A-00-27-00-00-13";

        // When & then
        assertThrows(IllegalArgumentException.class, () -> sut.buildArp(ArpOperation.REPLY, invalidMac, "", "", ""));
        assertThrows(IllegalArgumentException.class, () -> sut.buildArp(ArpOperation.OP_EXP2, "", "", invalidMac, ""));
    }

    @Test
    public void throwsExecptionWhenBuildingArpWithInvalidIP() {
        String invalidIP = "999.999.999.999";

        // When & then
        assertThrows(IllegalArgumentException.class, () -> sut.buildArp(ArpOperation.ARP_NAK, "", invalidIP, "", ""));
        assertThrows(IllegalArgumentException.class, () -> sut.buildArp(ArpOperation.REQUEST, "", "", "", invalidIP));
    }
}