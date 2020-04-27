package packet.handler;

import network.device.LocalNetworkDevice;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.util.MacAddress;
import packet.builder.IPacketBuilder;
import packet.builder.PacketBuilderFactory;
import packet.sender.IPacketSender;
import packet.sender.PacketSenderFactory;

class PacketHandler implements IPacketHandler {
    private final IPacketBuilder packetBuilder;
    private final IPacketSender packetSender;
    private Packet lastSendPacket;

    public PacketHandler() {
        packetBuilder = PacketBuilderFactory.getPacketBuilder();
        packetSender = PacketSenderFactory.getPacketSender();
    }

    @Override
    public void handle(Packet packet) {
        EthernetPacket p = (EthernetPacket) packet;

        if (! p.getHeader().getSrcAddr().toString().equals(LocalNetworkDevice.getMac())) {
            if (packet.getPayload() instanceof ArpPacket) {
                ArpPacket p1 = (ArpPacket) packet.getPayload();
                handleArpPacket(p1);
            } else {
                handlePacket((EthernetPacket) packet);
            }
        }
    }

    private void handlePacket(EthernetPacket packet) {


        if (packet.equals(lastSendPacket)) {
            // Do nothing
        } else {
            MacAddress dst;

            // Fixme
            if (packet.getHeader().getSrcAddr().toString().equals("08:00:27:e4:cd:64")) {
                dst = MacAddress.getByName("08:00:27:7a:57:63");
            } else {
                dst = MacAddress.getByName("08:00:27:e4:cd:64");
            }

            EthernetPacket p = packet.getBuilder().dstAddr(dst).build();

            try {
                Thread.sleep(250);
            } catch (Exception e){
                throw new RuntimeException(e);
            }

            lastSendPacket = p;
            packetSender.send(p);
        }
    }

    private void handleArpPacket(ArpPacket packet) {
        ArpPacket.ArpHeader header = packet.getHeader();

        if (header.getOperation().equals(ArpOperation.REQUEST)) {
            ArpOperation operation = ArpOperation.REPLY;
            String srcMac = LocalNetworkDevice.getMac();
            String srcIP = header.getDstProtocolAddr().getHostAddress();
            String dstMac = header.getSrcHardwareAddr().toString();
            String dstIP = header.getSrcProtocolAddr().getHostAddress();

            // Craft reply packet
            Packet reply = packetBuilder.buildArp(operation, srcMac, srcIP, dstMac, dstIP);

            try {
                Thread.sleep(500);
            } catch (Exception e){
                throw new RuntimeException(e);
            }

            packetSender.send(reply);
        }
    }
}
