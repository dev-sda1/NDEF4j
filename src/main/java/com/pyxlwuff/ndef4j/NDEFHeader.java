package com.pyxlwuff.ndef4j;

class NDEFHeader {
    byte[] header = new byte[7];

    /**
     * Creates an NDEF Record header for a URL, using the length of the payload.
     * @param recordLength The length of your payload.
     */
    NDEFHeader(int recordLength, byte uriProtocol){
        header[0] = 0x03; // idk
        header[1] = (byte) (recordLength + header.length); // Full length
        header[2] = (byte) 0xD1; // not sure
        header[3] = 0x01; // not sure
        header[4] = (byte) recordLength; // length of just the record
        header[6] = uriProtocol;
    }

    /**
     * Creates an NDEF Record header for a text record, using the length of the payload.
     * @param recordLength The length of your payload.
     */
    NDEFHeader(int recordLength){
        header[0] = 0x03; // idk
        header[1] = (byte) (recordLength + header.length); // Full length
        header[2] = (byte) 0xD1; // not sure
        header[3] = 0x01; // not sure
        header[4] = (byte) recordLength; // length of just the record
        header[6] = 0x02;
    }

    public byte[] getHeader(){
        return header;
    }
}
