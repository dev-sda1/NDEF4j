package com.pyxlwuff.ndef4j;

class NDEFHeader {
    byte[] header = new byte[7];

    /**
     * Creates an NDEF Record header for a specific type, using the length of the payload.
     * <b>Currently Supported:</b>
     * <ul>
     *     <li>Text Records (T)</li>
     *     <li>URI Records (U)</li>
     * </ul>
     * @param recordLength The length of your payload.
     * @param recordType The type of record you wish to create. See above for currently supported.
     * @throws IllegalArgumentException If the record type is invalid.
     */
    NDEFHeader(int recordLength, char recordType, int uriProtocol) throws IllegalArgumentException{
        header[0] = 0x03;
        header[1] = (byte) (recordLength + header.length);
        header[2] = (byte) 0xD1;
        header[3] = 0x01;
        header[4] = (byte) recordLength;
        header[6] = 0x02;

        switch(recordType){
            case 'T':
                header[5] = 0x54;
                break;
            case 'U':
                header[5] = 0x55;
                break;
            default:
                throw new IllegalArgumentException("Invalid record type.");
        }

        if(recordType == 'U'){
            header[6] = (byte) uriProtocol;
        }
    }

    public byte[] getHeader(){
        return header;
    }
}
