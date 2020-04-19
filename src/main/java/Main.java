import interceptor.IInterceptor;
import interceptor.InterceptorFactory;
import network.INetwork;
import network.NetworkFactory;
import network.device.RemoteNetworkDevice;

public class Main {
    public static void main(String[] args)  {
        RemoteNetworkDevice target = new RemoteNetworkDevice("08:00:27:e4:cd:64", "192.168.56.101");

        INetwork network = NetworkFactory.getNetwork();
        network.addDevice("08:00:27:e4:cd:64", "192.168.56.101");
        network.addDevice("08:00:27:7a:57:63", "192.168.56.102");

        IInterceptor interceptor = InterceptorFactory.getInterceptor();

        interceptor.intercept(target);
    }
}