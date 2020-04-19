package packet.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.mock;


class PacketListenerImplTest {
    private PacketListenerImpl sut;

    @BeforeEach
    public void setup() {
        sut = mock(PacketListenerImpl.class);
    }

    @Test
    public void handlesArpRequests() {
//        // Given
//        ArpPacket packet = mock(ArpPacket.class);
//
//        // When
//        sut.gotPacket(packet);
//
//        // Then
//        Mockito.verify(sut, times(1)).handleArpPacket(packet);
    }



}