package com.pyxlwuff;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * NDEF4j
 * A pure java library to create NDEF records for NFC Type 5 in a byte array format.
 * Currently supported: TXT, URL (http/https)
 */
public class NDEF
{
    /**
     * Creates a Capability Container (CC) for a Type 5 NFC tag.
     * 0xE2 Is a magic number that tells the nfc tag this is a CC.
     * 0x40 is the processing version.
     * @return A byte array containing the full generated CC container.
     */
    private static byte[] createCapabilityContainer() {
        // Define CC data as per NFC Forum Type 5 Tag Operation Specification
        byte[] ccData = new byte[8];

        // Set magic number and version
        ccData[0] = (byte) 0xE2; // Magic Number
        ccData[1] = 0x40; // Mapping Version

        // Set memory size and read/write access
        ccData[2] = 0x00; // Memory size i think (read-only)
        ccData[3] = 0x01; // lets you write to it?
        ccData[4] = 0x00; // Blank values (Reserved for future use).
        ccData[5] = 0x00;
        ccData[6] = 0x01; // dunno what this does
        ccData[7] = 0x00;

        return ccData;
    }

    /**
     * Creates a header for a NDEF File with a Text record.
     * @return A byte array containing a generated text record header.
     */
    private static byte[] createTextRecordHeader(String inputText){
        //byte[] header = new byte[9];
        byte[] header = new byte[7];
        //int payloadLength = inputText.getBytes().length + 1;
        int payloadLength = inputText.length() + 1;

        int guess = 256 - payloadLength;

        //char type = 'T';

        if(payloadLength > 256){
            System.out.println("uh oh too big :(((");
            return null;
        }

        header[0] = (byte) 0x03; // Doesn't change
        header[1] = (byte) (payloadLength + header.length);
        header[2] = (byte) 0xD1; // Doesn't change
        header[3] = 0x01; // ID length (we have no id so 0) // Doesn't change
        header[4] = (byte) payloadLength;
        header[5] = 0x54; // T Record. // Doesn't change
        header[6] = 0x02; // // Doesn't change

        return header;
    }

    /**
     * Creates a header for an NDEF File with a URL record.
     * 0x00 - Entire URI is in payload.
     * 0x01 - http://www.
     * 0x02 - https://www.
     * 0x03 - http://
     * 0x04 - https://
     * @return A byte array containing a generated URL record header.
     */
    private static byte[] createURLRecordHeader(String inputText, byte urlFormat){
        byte[] header = new byte[7];
        int payloadLength = inputText.length() + 1;

        if(payloadLength > 256){
            System.out.println("uh oh too big :(((");
            return null;
        }

        if(urlFormat != 0x00 && urlFormat != 0x01 && urlFormat != 0x02 && urlFormat != 0x03 && urlFormat != 0x04){
            System.out.println("URL Format specified is not valid!");
        }

        header[0] = (byte) 0x03; // Doesn't change
        header[1] = (byte) (payloadLength + header.length);
        header[2] = (byte) 0xD1; // Doesn't change
        header[3] = 0x01; // ID length (we have no id so 0) // Doesn't change
        header[4] = (byte) payloadLength;
        header[5] = 0x55; // U Record. // Doesn't change
        header[6] = urlFormat; // HTTPS protocol

        return header;
    }

    /**
     * Creates a full NDEF document with a text record.
     * @param inputText The string you want encoded into the record.
     * @return A byte array contaning the Capability Container (CC), Record Header, and Record content.
     */
    public static byte[] createTextFile(String inputText){
        inputText = "en"+inputText;

        byte[] cc = createCapabilityContainer();
        byte[] textRecordHeader = createTextRecordHeader(inputText);
        byte[] textBytes = inputText.getBytes(StandardCharsets.UTF_8);

        int totalPayloadLength = cc.length + textRecordHeader.length + textBytes.length + 1;

        ByteBuffer ndefRecordBuffer = ByteBuffer.allocate(totalPayloadLength);
        ndefRecordBuffer.put(cc);
        ndefRecordBuffer.put(textRecordHeader);
        ndefRecordBuffer.put(textBytes);
        ndefRecordBuffer.put((byte) 0xfe);

        return ndefRecordBuffer.array();
    }

    /**
     * Creates a full NDEF document with a URL record.
     * Valid urlTypes include:
     * <ul>
     *     <li>0x00 - Entire URI is in inputText. (e.g https://google.com)</li>
     *     <li>0x01 - http://www.</li>
     *     <li>0x02 - https://www.</li>
     *     <li>0x03 - http://</li>
     *     <li>0x04 - https://</li>
     * </ul>
     * @param inputText URL (without protocol such as http://)
     * @param urlType The type of protocol you wish to use (see above)
     * @return A byte array containing a fully generated NDEF document.
     */
    public static byte[] createURLFile(String inputText, int urlType){
        byte[] cc = createCapabilityContainer();
        byte[] urlHeader = createURLRecordHeader(inputText, (byte) urlType);
        byte[] urlBytes = inputText.getBytes(StandardCharsets.UTF_8);

        int totalPayloadLength = cc.length + urlHeader.length + urlBytes.length + 1;

        ByteBuffer ndefRecordBuffer = ByteBuffer.allocate(totalPayloadLength);
        ndefRecordBuffer.put(cc);
        ndefRecordBuffer.put(urlHeader);
        ndefRecordBuffer.put(urlBytes);
        ndefRecordBuffer.put((byte) 0xfe);

        return ndefRecordBuffer.array();
    }
}
