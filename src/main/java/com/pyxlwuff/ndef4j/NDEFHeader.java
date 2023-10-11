package com.pyxlwuff.ndef4j;

class NDEFHeader {
    byte[] header = new byte[7];

    /**
     * Creates an NDEF Record header for a URL, using the length of the payload.
     * <b>Currently Supported:</b>
     * <ul>
     *     <li>Text Records (T)</li>
     *     <li>URI Records (U)</li>
     * </ul>
     * @param recordLength The length of your payload.
     * @param recordType The type of record you wish to create. See above for currently supported.
     * @throws IllegalArgumentException If the record type is invalid.
     */
    NDEFHeader(int recordLength, char recordType, byte uriProtocol) throws IllegalArgumentException{
        header[0] = 0x03; // idk
        header[1] = (byte) (recordLength + header.length); // Full length
        header[2] = (byte) 0xD1; // not sure
        header[3] = 0x01; // not sure
        header[4] = (byte) recordLength; // length of just the record
        header[6] = uriProtocol;

        switch(recordType){
            case 'U':
                header[5] = 0x55; // url record
                break;
            default:
                throw new IllegalArgumentException("Invalid record type.");
        }
    }

    NDEFHeader(int recordLength, char recordType) throws IllegalArgumentException{
        header[0] = 0x03; // idk
        header[1] = (byte) (recordLength + header.length); // Full length
        header[2] = (byte) 0xD1; // not sure
        header[3] = 0x01; // not sure
        header[4] = (byte) recordLength; // length of just the record
        header[6] = 0x02;

        switch(recordType){
            case 'T':
                header[5] = 0x54; // text record
                break;
            default:
                throw new IllegalArgumentException("Invalid record type.");
        }
    }

    public byte[] getHeader(){
        return header;
    }
}
