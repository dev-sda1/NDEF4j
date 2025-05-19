# NDEF4j

NDEF4j is a small project I worked on during a placement at my University while we were exploring various dynamic RFID chipsets to use on a project. A pure java library for creating NFC NDEF records, if you're not developing for Android. Wrote this for an [ST25DV16K chip from Adafruit](https://www.adafruit.com/product/4701) after bashing my head against the NFC Type 5 spec for about 4 days. (and I still have no idea for some of it). Not sure if the output from here will work on other NFC tags / chips since I don't have any tags, nor a dedicated reader/writer to check.

### Currently supported:
- Text Records
- URI Records

### Usage
```java
TextRecord ndefText = new TextRecord("Hello world!", "en", false);
byte[] ndefTextTest = ndefText.encodeRecord();

URIRecord ndefURI = new URIRecord("https://pyxlwuff.dev", 0x00, false);
byte[] ndefURITest = ndefURI.encodeRecord();
```

### Limitations
- Records can only go up to 256 bytes in size. (This is probably arbitrary and can be removed)
- No support for multiple fields in one NDEF record.

### Using in your project
As it's not part of an online Maven repository, you'll need to clone the git repo and manually add/compile it yourself.
