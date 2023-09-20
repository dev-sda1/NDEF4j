package com.pyxlwuff.ndef4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TextRecord {
    private String recordContent = "";
    private String languageCode = "";

    public TextRecord(String txt, String lang) throws IllegalArgumentException {
        if (txt.isEmpty() || lang.isEmpty()) {
            throw new IllegalArgumentException("Text input or language code cannot be empty.");
        }

        if (lang.length() != 2) {
            throw new IllegalArgumentException("Language code should be 2 characters long (i.e 'en')");
        }

        recordContent = txt;
        languageCode = lang;
    }

    /**
     * Returns the text record content as a String.
     *
     * @return A string containing the set text record. Null if no record content is set.
     */
    public String getRecordContent() {
        if (recordContent.isEmpty()) {
            return null;
        }

        return recordContent;
    }

    /**
     * Returns the language code as a string.
     *
     * @return A string containing the set language code. Null if theres no language code set.
     */
    public String getRecordLanguage() {
        if (languageCode.isEmpty()) {
            return null;
        }

        return languageCode;
    }

    /**
     * Encodes the text record, and returns it as a byte array.
     *
     * @return A byte array containing the full record.
     */
    public byte[] encodeRecord() throws IllegalArgumentException {
        String fullRecord = languageCode + recordContent;
        int fullRecordLength = fullRecord.length() + 1;

        if (fullRecord.length() + 8 > 256) {
            throw new IllegalArgumentException("Total payload size exceeds 256 bytes. Try reducing record content.");
        }

        if (languageCode.isEmpty() || recordContent.isEmpty()) {
            throw new IllegalArgumentException("Payload content is missing either language code or record content.");
        }

        // Create capability container.
        CapabilityContainer cc = new CapabilityContainer();

        // Create NDEF Record Header.
        byte[] header = new byte[7];

        header[0] = (byte) 0x03;
        header[1] = (byte) (fullRecordLength + header.length);
        header[2] = (byte) 0xD1;
        header[3] = 0x01;
        header[4] = (byte) fullRecordLength;
        header[5] = 0x54;
        header[6] = 0x02;

        byte[] payloadBytes = fullRecord.getBytes(StandardCharsets.UTF_8);
        int totalPayloadLength = cc.getCcData().length + header.length + fullRecord.length() + 1;

        ByteBuffer ndefRecordBuffer = ByteBuffer.allocate(totalPayloadLength);
        ndefRecordBuffer.put(cc.getCcData());
        ndefRecordBuffer.put(header);
        ndefRecordBuffer.put(payloadBytes);
        ndefRecordBuffer.put((byte) 0xfe);

        return ndefRecordBuffer.array();
    }
}
