package model;

import com.google.common.base.Charsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class Converter {

    private static final byte[] ZERO = new byte[]{0b00000000};
    private static final byte[] ONE = new byte[]{0b00000001};
    private static final byte[] TWO = new byte[]{0b00000010};
    private static final byte[] EMPTY = {};

    private static MessageDigest digest;
    private static Base64.Encoder encoderBase64 = Base64.getEncoder();
    private static Base64.Decoder decoderBase64 = Base64.getDecoder();

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static byte[] encode(String str) {
        if (str != null) {
            return encode(str.getBytes(Charsets.UTF_8));
        } else {
            return null;
        }
    }

    public static byte[] encode(byte[] bytes1, byte[] bytes2) {
        if (bytes1 != null && bytes2 != null) {
            return encode(combine(bytes1, bytes2));
        } else if (bytes1 != null) {
            return encode(bytes1);
        } else if (bytes2 != null) {
            return encode(bytes2);
        } else {
            return null;
        }
    }

    public static String countLeafHash(String value) {
        return encoderBase64.encodeToString(encode(combine(ZERO, value.getBytes())));
    }

    public static String countHash(String left, String right) {
        byte[] bytes = encode(combine(ONE, decodeBase64(left), TWO, decodeBase64(right)));
        return encoderBase64.encodeToString(bytes);
    }

    private static byte[] decodeBase64(String s) {
        if (s == null) {
            return EMPTY;
        } else {
            return decoderBase64.decode(s);
        }
    }

    private static byte[] encode(byte[] bytes) {
        return digest.digest(bytes);
    }

    private static byte[] combine(byte[]... bytes) {
        int length = 0;
        for (byte[] seg : bytes)
            length += seg.length;
        byte[] concatenated = new byte[length];
        int index = 0;
        for (byte[] seq : bytes) {
            for (byte b : seq) {
                concatenated[index] = b;
                index++;
            }
        }
        return concatenated;
    }
}
