package packet.builder;

import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;

import java.net.InetAddress;

class PacketBuilder implements IPacketBuilder {

    @Override
    public EthernetPacket buildArp(ArpOperation operation, String srcMac, String srcIP, String dstMac, String dstIP) {
        if (! isValidMac(srcMac) || ! isValidMac(dstMac) ) {
            throw new IllegalArgumentException("Source or destination MAC address not valid! Only \":\" are allowed");
        }

        if (! isValidIP(srcIP) || ! isValidIP(dstIP)) {
            throw new IllegalArgumentException("Source or destination IP address not valid!");
        }

        try {
            MacAddress srcMacValid = MacAddress.getByName(srcMac);
            InetAddress srcIpValid = InetAddress.getByName(srcIP);
            MacAddress dstMacValid = MacAddress.getByName(dstMac);
            InetAddress dstIpValid = InetAddress.getByName(dstIP);

            return buildArp(operation, srcMacValid, srcIpValid, dstMacValid, dstIpValid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EthernetPacket buildArp(ArpOperation operation, MacAddress srcMac, InetAddress srcIP, MacAddress dstMac, InetAddress dstIP) {
        ArpPacket.Builder payloadBuilder = buildArpPacketPayload(operation, srcMac, srcIP, dstMac, dstIP);
        return buildArpPacketHeader(srcMac, dstMac, payloadBuilder);
    }

    private EthernetPacket buildArpPacketHeader(MacAddress src, MacAddress dst, ArpPacket.Builder payloadBuilder) {
        EthernetPacket.Builder builder = new EthernetPacket.Builder();

        builder
                .srcAddr(src)
                .dstAddr(dst)
                .type(EtherType.ARP)
                .payloadBuilder(payloadBuilder)
                .paddingAtBuild(true);

        return builder.build();
    }

    private ArpPacket.Builder buildArpPacketPayload(ArpOperation operation, MacAddress srcMac, InetAddress srcIP, MacAddress dstMac, InetAddress dstIP) {
        ArpPacket.Builder builder = new ArpPacket.Builder();

        try {
            builder
                    .hardwareType(ArpHardwareType.ETHERNET)
                    .protocolType(EtherType.IPV4)
                    .hardwareAddrLength((byte) MacAddress.SIZE_IN_BYTES)
                    .protocolAddrLength((byte) ByteArrays.INET4_ADDRESS_SIZE_IN_BYTES)
                    .operation(operation)
                    .srcHardwareAddr(srcMac)
                    .srcProtocolAddr(srcIP)
                    .dstHardwareAddr(dstMac)
                    .dstProtocolAddr(dstIP);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return builder;
    }

    private boolean isValidIP(String address) {
        return address.matches("^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$");
    }

    private boolean isValidMac(String address) {
        return address.matches("^([0-9A-Fa-f]{2}[:]){5}([0-9A-Fa-f]{2})$");
    }
}
