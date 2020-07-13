package com.genesis.apps.comm.util.crypt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Bit 연산 유틸리티
 */
public class ByteUtils {
    public static final int BIG_ENDIAN = 0;
    public static final int LITTLE_ENDIAN = -1;

    public static byte toBytes(boolean x) {
        return (byte) (x ? 1 : 0);
    }

    public static byte[] toBytes(char c) {
        return toBytes(c, BIG_ENDIAN);
    }

    public static byte[] toBytes(char c, int flag) {
        byte[] b = new byte[2];
        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                b[0] = (byte) ((c >> 8) & 0xff);
                b[1] = (byte) (c & 0xff);
                break;

            case ByteUtils.LITTLE_ENDIAN:
                b[1] = (byte) ((c >> 8) & 0xff);
                b[0] = (byte) (c & 0xff);
                break;
            default:
                throw new IllegalArgumentException("wrong flag");
        }

        return b;
    }

    public static byte[] toBytes(int i) {
        return toBytes(i, BIG_ENDIAN);
    }

    public static byte[] toBytes(int i, int flag) {
        byte[] b = new byte[4];
        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                b[0] = (byte) ((i >> 24) & 0xff);
                b[1] = (byte) ((i >> 16) & 0xff);
                b[2] = (byte) ((i >> 8) & 0xff);
                b[3] = (byte) (i & 0xff);
                break;
            case ByteUtils.LITTLE_ENDIAN:
                b[3] = (byte) ((i >> 24) & 0xff);
                b[2] = (byte) ((i >> 16) & 0xff);
                b[1] = (byte) ((i >> 8) & 0xff);
                b[0] = (byte) (i & 0xff);
                break;
            default:
                throw new IllegalArgumentException("wrong flag");
        }
        return b;
    }

    public static byte[] toBytes(long i) {
        return toBytes(i, BIG_ENDIAN);
    }

    public static byte[] toBytes(long i, int flag) {
        byte[] b = new byte[8];
        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                b[0] = (byte) ((i >> 56) & 0xff);
                b[1] = (byte) ((i >> 48) & 0xff);
                b[2] = (byte) ((i >> 40) & 0xff);
                b[3] = (byte) ((i >> 32) & 0xff);
                b[4] = (byte) ((i >> 24) & 0xff);
                b[5] = (byte) ((i >> 16) & 0xff);
                b[6] = (byte) ((i >> 8) & 0xff);
                b[7] = (byte) ((i >> 0) & 0xff);
                break;
            case ByteUtils.LITTLE_ENDIAN:
                b[7] = (byte) ((i >> 56) & 0xff);
                b[6] = (byte) ((i >> 48) & 0xff);
                b[5] = (byte) ((i >> 40) & 0xff);
                b[4] = (byte) ((i >> 32) & 0xff);
                b[3] = (byte) ((i >> 24) & 0xff);
                b[2] = (byte) ((i >> 16) & 0xff);
                b[1] = (byte) ((i >> 8) & 0xff);
                b[0] = (byte) ((i >> 0) & 0xff);
                break;
            default:
                throw new IllegalArgumentException("wrong flag");
        }
        return b;
    }

    public static byte[] toBytes(short s) {
        return toBytes(s, BIG_ENDIAN);
    }

    public static byte[] toBytes(short s, int flag) {
        byte[] b = new byte[2];
        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                b[0] = (byte) ((s >> 8) & 0xff);
                b[1] = (byte) (s & 0xff);
                break;

            case ByteUtils.LITTLE_ENDIAN:
                b[1] = (byte) ((s >> 8) & 0xff);
                b[0] = (byte) (s & 0xff);
                break;
            default:
                throw new IllegalArgumentException("wrong flag");
        }

        return b;
    }

    public static char toChar(byte[] b) {
        return toChar(b, BIG_ENDIAN);
    }

    public static char toChar(byte[] b, int flag) {
        if (b.length != 2) {
            throw new IllegalArgumentException("byte array length");
        }

        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                return (char) (((b[0] & 0xff) << 8) | (b[1] & 0xff));
            case ByteUtils.LITTLE_ENDIAN:
                return (char) (((b[1] & 0xff) << 8) | (b[0] & 0xff));
            default:
                throw new IllegalArgumentException("wrong flag");
        }
    }

    public static int toInt(byte[] b) {
        return toInt(b, BIG_ENDIAN);
    }

    public static int toInt(byte[] b, int flag) {
        if (b.length != 4) {
            throw new IllegalArgumentException("byte array length");
        }

        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                return (int) (((b[0] & 0xff) << 24) | ((b[1] & 0xff) << 16)
                        | ((b[2] & 0xff) << 8) | (b[3] & 0xff));
            case ByteUtils.LITTLE_ENDIAN:
                return (int) (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16)
                        | ((b[1] & 0xff) << 8) | (b[0] & 0xff));
            default:
                throw new IllegalArgumentException("wrong flag");
        }
    }

    public static long toLong(byte[] b) {
        return toLong(b, BIG_ENDIAN);
    }

    public static long toLong(byte[] b, int flag) {
        if (b.length != 8) {
            throw new IllegalArgumentException("byte array length");
        }

        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                return (((long) (b[0] & 0xff) << 56) | ((long) (b[1] & 0xff) << 48)
                        | ((long) (b[2] & 0xff) << 40)
                        | ((long) (b[3] & 0xff) << 32)
                        | ((long) (b[4] & 0xff) << 24)
                        | ((long) (b[5] & 0xff) << 16)
                        | ((long) (b[6] & 0xff) << 8) | ((long) (b[7] & 0xff)));
            case ByteUtils.LITTLE_ENDIAN:
                return (((long) (b[7] & 0xff) << 56) | ((long) (b[6] & 0xff) << 48)
                        | ((long) (b[5] & 0xff) << 40)
                        | ((long) (b[4] & 0xff) << 32)
                        | ((long) (b[3] & 0xff) << 24)
                        | ((long) (b[2] & 0xff) << 16)
                        | ((long) (b[1] & 0xff) << 8) | ((long) (b[0] & 0xff)));
            default:
                throw new IllegalArgumentException("wrong flag");
        }
    }

    public static short toShort(byte[] b) {
        return toShort(b, BIG_ENDIAN);
    }

    public static short toShort(byte[] b, int flag) {
        if (b.length != 2) {
            throw new IllegalArgumentException("byte array length");
        }

        switch (flag) {
            case ByteUtils.BIG_ENDIAN:
                return (short) (((b[0] & 0xff) << 8) | (b[1] & 0xff));
            case ByteUtils.LITTLE_ENDIAN:
                return (short) (((b[1] & 0xff) << 8) | (b[0] & 0xff));
            default:
                throw new IllegalArgumentException("wrong flag");
        }
    }

    public static boolean toBoolean(byte b) {
        return b == 1 ? true : false;
    }

    public static byte[] reverseArray(byte[] array) {
        for (int i = 0, j = array.length - 1; i < j; i++, j--) {
            byte b = array[i];
            array[i] = array[j];
            array[j] = b;
        }

        return array;
    }

    public static byte[] copyByteArray(byte[] array2Copy) {
        if (array2Copy == null) {
            //return new byte[0] instead?
            throw new IllegalArgumentException("Argument 'array2Copy' cannot be null");
        }
        return copyByteArray(array2Copy, 0, array2Copy.length);
    }

    public static byte[] copyByteArray(byte[] array2Copy, int startPos, int length) {
        if (array2Copy == null) {
            //return new byte[0] instead?
            throw new IllegalArgumentException("Argument 'array2Copy' cannot be null");
        }
        if (array2Copy.length < startPos + length) {
            throw new IllegalArgumentException("startPos(" + startPos + ")+length(" + length + ") > byteArray.length(" + array2Copy.length + ")");
        }
        byte[] copy = new byte[length];
        System.arraycopy(array2Copy, startPos, copy, 0, length);
        return copy;
    }

    public static byte[] copyOfRange(byte[] origin, int form, int to) {
        return Arrays.copyOfRange(origin, form, to);
    }

    public static byte[] append(byte[]... data) {

        int len = 0;
        for (byte[] d : data) {
            len += d.length;
        }
        byte[] res = new byte[len];
        ByteBuffer bb = ByteBuffer.wrap(res);
        for (byte[] d : data) {
            bb.put(d);
        }
        return res;
    }

    public static final String prettyHexString(String hexString, int indent) {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < hexString.length(); i++) {
            char c = hexString.charAt(i);
            buf.append(c);

            int nextPos = i + 1;
            if (nextPos % 32 == 0 && nextPos != hexString.length()) {
                for (int k = 0; k < indent; k++) {
                    buf.append(" ");
                }
            } else if (nextPos % 2 == 0 && nextPos != hexString.length()) {
                buf.append(" ");
            }
        }
        return buf.toString();
    }

    public static final String prettyHexString(byte[] array, int indent) {
        return prettyHexString(HexStringConverter.hexToString(array), indent);
    }

    public static final String prettyHexString(byte[] array) {
        return prettyHexString(HexStringConverter.hexToString(array), 0);
    }

    public static final String prettyHexString(String hexString) {
        return prettyHexString(hexString, 0);
    }

    public static final byte[] trim(byte[] d) {
        int i = 0;
        for (i = d.length-1; i > 0; i--) {
            if (d[i] == 0x00) {

            } else {
                break;
            }
        }

        return copyByteArray(d, 0, i + 1);
    }

    public static byte[] intToByteArray(final int integer) {
        ByteBuffer buff = ByteBuffer.allocate(Integer.SIZE / 16);
        buff.putInt(integer);
        buff.order(ByteOrder.BIG_ENDIAN);
        //buff.order(ByteOrder.LITTLE_ENDIAN);
        return buff.array();
    }

    public static int byteArrayToInt(byte[] bytes) {
        final int size = Integer.SIZE / 16;
        ByteBuffer buff = ByteBuffer.allocate(size);
        final byte[] newBytes = new byte[size];
        for (int i = 0; i < size; i++) {
            if (i + bytes.length < size) {
                newBytes[i] = (byte) 0x00;
            } else {
                newBytes[i] = bytes[i + bytes.length - size];
            }
        }
        buff = ByteBuffer.wrap(newBytes);
        buff.order(ByteOrder.BIG_ENDIAN); // Endian에 맞게 세팅
        return buff.getInt();
    }

    public static int toInt(byte b) {
        return (int) b & 0xFF;
    }
}
