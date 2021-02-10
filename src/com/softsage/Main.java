package com.softsage;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class Main {
  public static String mURL = "time.windows.com";
  public static String strSep2 = "##";
  public static String mSerial = null;

  private static void GetKey() {
    try {
      File file = new File ("strRecordSystems.txt");
      String[] split = new BufferedReader(new InputStreamReader(new FileInputStream(file))).readLine().toString().split(strSep2);
      mSerial = split[1];
    } catch (Exception unused) {
    }
  }

  public static byte[] Hash(byte[] bArr, byte[] bArr2) {
    byte[] bArr3 = new byte[20];
    byte[] bArr4 = new byte[64];
    byte[] bArr5 = new byte[64];
    int i = 0;
    while (i < bArr.length) {
      try {
        bArr4[i] = (byte) (bArr[i] ^ 54);
        bArr5[i] = (byte) (bArr[i] ^ 92);
        i++;
      } catch (Exception unused) {
        return null;
      }
    }
    for (int length = bArr.length; length < 64; length++) {
      bArr4[length] = 54;
      bArr5[length] = 92;
    }
    byte[] bArr6 = new byte[(bArr4.length + bArr2.length)];
    System.arraycopy(bArr4, 0, bArr6, 0, bArr4.length);
    System.arraycopy(bArr2, 0, bArr6, bArr4.length, bArr2.length);
    byte[] hash = DigestUtils.sha1(bArr6);
    byte[] bArr7 = new byte[(bArr5.length + hash.length)];
    System.arraycopy(bArr5, 0, bArr7, 0, bArr5.length);
    System.arraycopy(hash, 0, bArr7, bArr5.length, hash.length);
    return DigestUtils.sha1(bArr7);
  }

  public static String GenerateOTP(byte[] bArr, long j) {
    //byte[] hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, String.valueOf(j)).hmac(bArr);
    //System.out.println(hmac.toString());
    byte[] Hash = Hash(bArr, String.valueOf(j).getBytes());
    //System.out.println(Hash.toString());
    byte b = (byte) (Hash[Hash.length - 1] & 15);
    String valueOf = String.valueOf(((Hash[b + 3] & 255) | ((((Hash[b] & Byte.MAX_VALUE) << 24) | ((Hash[b + 1] & 255) << 16)) | ((Hash[b + 2] & 255) << 8))) % new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000}[6]);
    int length = valueOf.length();
    if (length < 6) {
      for (int i = 0; i < 6 - length; i++) {
        valueOf = "0" + valueOf;
      }
    }
    return String.valueOf(valueOf);
  }

  public static void main(String[] args) {
    GetKey();
    String GenerateOTP = GenerateOTP(mSerial.getBytes(), (new Date().getTime()) / 30000);
    System.out.println(GenerateOTP);
  }
}
