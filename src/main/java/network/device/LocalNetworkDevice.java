package network.device;

import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.util.NifSelector;

public class LocalNetworkDevice {
    private static final PcapNetworkInterface device = setup();

    public static PcapNetworkInterface getNetworkDevice() {
        return device;
    }

    public static String getMac() {
        return LocalNetworkDevice.getNetworkDevice().getLinkLayerAddresses().get(0).toString();
    }

    private static PcapNetworkInterface setup() {

        try {
            PcapNetworkInterface dev = new NifSelector().selectNetworkInterface();

            if (dev == null) {
                System.out.println("No device chosen.");
                System.exit(1);
            } else {
                System.out.println("Starting listing and sending on device: " + dev.getName() + " (" + dev.getDescription() + ")");
            }

            return dev;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
