package interceptor;

import network.device.RemoteNetworkDevice;

public interface IInterceptor {
    void intercept(RemoteNetworkDevice device);
}
