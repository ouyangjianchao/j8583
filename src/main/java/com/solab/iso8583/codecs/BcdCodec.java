package com.solab.iso8583.codecs;

public class BcdCodec {
	public static byte[] strToBcd(String str) {
		return strToBcd(str, str.length());
	}

	public static byte[] strToBcd(String str, int numlen) {
		if (numlen % 2 != 0) {
			numlen++;
		}
		while (str.length() < numlen) {
			str = "0" + str;
		}

		byte[] bStr = new byte[str.length() / 2];
		char[] cs = str.toCharArray();
		int i = 0;
		int iNum = 0;
		for (i = 0; i < cs.length; i += 2) {
			int iTemp = 0;
			if ((cs[i] >= '0') && (cs[i] <= '9')) {
				iTemp = cs[i] - '0' << 4;
			} else {
				if ((cs[i] >= 'a') && (cs[i] <= 'f')) {
					int tmp119_117 = i;
					char[] tmp119_116 = cs;
					tmp119_116[tmp119_117] = ((char) (tmp119_116[tmp119_117] - ' '));
				}
				iTemp = cs[i] - '0' - 7 << 4;
			}

			if ((cs[(i + 1)] >= '0') && (cs[(i + 1)] <= '9')) {
				iTemp += cs[(i + 1)] - '0';
			} else {
				if ((cs[(i + 1)] >= 'a') && (cs[(i + 1)] <= 'f')) {
					int tmp206_205 = (i + 1);
					char[] tmp206_201 = cs;
					tmp206_201[tmp206_205] = ((char) (tmp206_201[tmp206_205] - ' '));
				}
				iTemp += cs[(i + 1)] - '0' - 7;
			}
			bStr[iNum] = ((byte) iTemp);
			iNum++;
		}
		return bStr;
	}

	public static int bcdToInt(byte[] bcdNum, int offset, int numlen) {
		return Integer.parseInt(bcdToStr(bcdNum, offset, numlen));
	}

	public static String bcdToStr(byte[] bcdNum, int offset, int numlen) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < numlen; i++) {
			sb.append(Integer.toHexString((bcdNum[(i + offset)] & 0xF0) >> 4));
			sb.append(Integer.toHexString(bcdNum[(i + offset)] & 0xF));
		}
		return sb.toString();
	}
}