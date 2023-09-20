# NDEF4j

A pure java library for creating NFC NDEF records, if you're not developing for Android. Wrote this for an [ST25DV16K chip from Adafruit](https://www.adafruit.com/product/4701) after bashing my head against the NFC Type 5 spec for about 4 days. I have no idea if the output from here will work on other NFC tags / chips since I don't have any tags, nor a dedicated reader/writer to check.

### Currently supported:
- Text Records
- URL Records (http:// and https:// only)


### Using in your project
it's not fully ready yet so i'll probably put it on maven central sooner or later. for now, you'll need to clone the 
repository and build the jar yourself with ``mvn package`` and import that into your project.
