import interceptor.IInterceptor;
import interceptor.InterceptorFactory;
import network.INetwork;
import network.NetworkFactory;
import network.device.RemoteNetworkDevice;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        RemoteNetworkDevice target = null;

        INetwork network = NetworkFactory.getNetwork();
        ArrayList<RemoteNetworkDevice> remoteNetworkDevices = network.getDevices();

        for (RemoteNetworkDevice r : remoteNetworkDevices) {
            if ("192.168.56.101".equals(r.getIp().getHostAddress())) {
                target = r;
            }
        }

        if (target == null) {
            throw new RuntimeException("Target not found");
        }

        IInterceptor interceptor = InterceptorFactory.getInterceptor();

        interceptor.intercept(target);
    }
}