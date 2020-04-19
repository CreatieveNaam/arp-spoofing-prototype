package packet.handler;

public class PacketHandlerFactory {
    public static PacketHandler getArpPacketHandler() {
        return new PacketHandler();
    }
}
