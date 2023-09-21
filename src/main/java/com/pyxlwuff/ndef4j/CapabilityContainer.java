package com.pyxlwuff.ndef4j;

/**
 * Capability Container for an NDEF record.
 */
public class CapabilityContainer {
    final byte[] ccData = new byte[8];

    CapabilityContainer(boolean readOnly){
        // Set magic number and version
        ccData[0] = (byte) 0xE2; // Magic Number
        //ccData[1] = 0x40; // Mapping Version
        ccData[1] = (byte) 0x40; // Mapping Version & Access?
        ccData[2] = 0x00; // Does Nothing
        ccData[3] = 0x01; // Additional Feature Information. (Defined as 0x01 Well Known)
        ccData[4] = 0x00; // This seems to do something not sure what though
        ccData[5] = 0x00; // Reserved for Future Use
        ccData[6] = 0x01; // Memory Length (Byte 1)
        ccData[7] = 0x00; // Memory Length (Byte 2)

        if(readOnly){
            ccData[1] = (byte) 0x43;
        }
    }

    byte[] getCcData(){
        return ccData;
    }
}
