package com.softsage;

public abstract class Pack {
  public static int bigEndianToInt(byte[] bArr, int i) {
    int i2 = i + 1;
    int i3 = i2 + 1;
    return (bArr[i3 + 1] & 255) | (bArr[i] << 24) | ((bArr[i2] & 255) << 16) | ((bArr[i3] & 255) << 8);
  }

  public static void intToBigEndian(int i, byte[] bArr, int i2) {
    bArr[i2] = (byte) (i >>> 24);
    int i3 = i2 + 1;
    bArr[i3] = (byte) (i >>> 16);
    int i4 = i3 + 1;
    bArr[i4] = (byte) (i >>> 8);
    bArr[i4 + 1] = (byte) i;
  }

  public static long bigEndianToLong(byte[] bArr, int i) {
    int bigEndianToInt = bigEndianToInt(bArr, i);
    return (((long) bigEndianToInt(bArr, i + 4)) & 4294967295L) | ((((long) bigEndianToInt) & 4294967295L) << 32);
  }

  public static void longToBigEndian(long j, byte[] bArr, int i) {
    intToBigEndian((int) (j >>> 32), bArr, i);
    intToBigEndian((int) (j & 4294967295L), bArr, i + 4);
  }
}
