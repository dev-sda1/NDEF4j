package com.pyxlwuff.ndef4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * A URI record in an NDEF message.
 */
public class URIRecord {
    private String uriContent = "";
    private int uriProtocol = -1;
    private boolean readOnly = false;

    // Valid URI Protocols.
    private int[] validHex = {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
            0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F,
            0x20, 0x21, 0x22, 0x23
    };

    /**
     * Establishes a URI Record. See https://old.gototags.com/nfc/ndef/uri/ for valid protocol values.
     * @param uri URL (If protocol is not 0x00, without protocol at the start)
     * @param protocol The type of protocol you wish to use.
     * @throws IllegalArgumentException If either the URI or protocol is invalid or missing.
     */
    public URIRecord(String uri, URIRecordTypes protocol, boolean writeProtected) throws IllegalArgumentException{
        if(uri.isEmpty() || protocol == null){
            throw new IllegalArgumentException("URI input, or protocol input cannot be empty or invalid.");
        }

        uriContent = uri;
        uriProtocol = protocol.ordinal();
        readOnly = writeProtected;
    }

    /**
     * Returns the URI content as a String.
     * @return A String containing the set URI content. Null if no content has been set.
     */
    public String getUriContent(){
        if(uriContent.isEmpty()){
            return null;
        }

        return uriContent;
    }

    /**
     * Returns the protocol being used as an integer.
     * @return An integer containing the currently set protocol. -1 if no protocol has been set.
     */
    public int getProtocol(){
        return uriProtocol;
    }

    /**
     * Encodes the URI record, and returns it as a byte array.
     * @return A byte array containing the full record.
     */
    public byte[] encodeRecord() throws IllegalArgumentException{
        int uriLength = uriContent.length() + 1;

        if(uriLength + uriProtocol + 8 > 256){
            throw new IllegalArgumentException("Total payload size exceeds 256 bytes. Try reducing record content.");
        }

        if(uriContent.isEmpty() || uriProtocol == -1){
            throw new IllegalArgumentException("You haven't set the URI content field or the protocol!");
        }

        CapabilityContainer cc = new CapabilityContainer(readOnly);
        NDEFHeader genHeader = new NDEFHeader(uriLength);
        byte[] header = genHeader.getHeader();

        int totalPayloadLength = cc.getCcData().length + header.length + uriLength + 1;
        ByteBuffer ndefRecordBuffer = ByteBuffer.allocate(totalPayloadLength);
        ndefRecordBuffer.put(cc.getCcData());
        ndefRecordBuffer.put(header);
        ndefRecordBuffer.put(uriContent.getBytes(StandardCharsets.UTF_8));
        ndefRecordBuffer.put((byte) 0xfe);

        return ndefRecordBuffer.array();
    }

}
