package com.pyxlwuff.ndef4j;

/**
 * Capability Container for an NDEF record.
 */
public class CapabilityContainer {
    final byte[] ccData = new byte[8];

    CapabilityContainer(){
        // Set magic number and version
        ccData[0] = (byte) 0xE2; // Magic Number
        ccData[1] = 0x40; // Mapping Version

        // Set memory size and read/write access
        ccData[2] = 0x00; // Memory size i think (read-only)
        ccData[3] = 0x03; // lets you write to it?
        ccData[4] = 0x00; // Blank values (Reserved for future use).
        ccData[5] = 0x00;
        ccData[6] = 0x01; //// dunno what this does
        ccData[7] = 0x00;
    }

    byte[] getCcData(){
        return ccData;
    }
}
