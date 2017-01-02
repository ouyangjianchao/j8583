package com.solab.iso8583.util;

/**
 * String实用方法
 * @author shijq
 *
 */
public class StringHelper {
    /**
     * 检查字符串是否为空
     * 
     * @param str
     * @return
     */
    public static boolean isNullOrWhiteSpace(String str) {
        if (str == null) {
            return true;
        }
        str = str.trim();
        if (str.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 检查字符串是否不为空，
     * 
     * @param str
     * @return
     */
    public static boolean notNullAndWhitSpace(String str) {
        if (str == null) {
            return false;
        }
        str = str.trim();
        if (str.isEmpty()) {
            return false;
        }
        return true;
    }
    /**
     * 数组中是否包括某字符串
     * @param array
     * @param key
     * @return
     */
    public static boolean arrayContainsString(String[] array, String key) {
        for (String tmpString : array) {
            if (tmpString.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param source
     * @param strTail
     * @return
     */
    public static String GetLeftString(String source, String strTail) {
        return GetLeftString(source, strTail, false);
    }

    /**
     * 
     * @param source
     * @param strTail
     * @param KeepHeadAndTail
     * @return
     */
    public static String GetLeftString(String source, String strTail, boolean KeepHeadAndTail) {
        return GetMiddleString(source, "", strTail, KeepHeadAndTail);
    }

    /**
     * 
     * @param source
     * @param strHead
     * @return
     */
    public static String GetRightString(String source, String strHead) {
        return GetRightString(source, strHead, false);
    }

    /**
     * 
     * @param source
     * @param strHead
     * @param KeepHeadAndTail
     * @return
     */
    public static String GetRightString(String source, String strHead, boolean KeepHeadAndTail) {
        return GetMiddleString(source, strHead, "", KeepHeadAndTail);
    }

    /**
     * 
     * @param source
     * @param strHead
     * @param strTail
     * @return
     */
    public static String GetMiddleString(String source, String strHead, String strTail) {
        return GetMiddleString(source, strHead, strTail, false);
    }

    /**
     * 
     * @param source
     * @param strHead
     * @param strTail
     * @param KeepHeadAndTail
     * @return
     */
    public static String GetMiddleString(String source, String strHead, String strTail, boolean KeepHeadAndTail) {
        try {
            int indexHead, indexTail;

            if (strHead == null || strHead.isEmpty()) {
                indexHead = 0;
            } else {
                indexHead = source.indexOf(strHead);
            }

            if (strTail == null || strTail.isEmpty()) {
                indexTail = source.length();
            } else {
                indexTail = source.indexOf(strTail, indexHead + strHead.length());
            }
            if (indexTail < 0) {
                indexTail = source.length();
            }

            String rtnStr = "";
            if ((indexHead >= 0) && (indexTail >= 0)) {
                if (KeepHeadAndTail) {
                    rtnStr = source.substring(indexHead, indexTail + strTail.length());
                } else {
                    rtnStr = source.substring(indexHead + strHead.length(), indexTail);
                }
            }
            return rtnStr;
        } catch (Exception ex) {
            return "";
        }
    }
    
    public static void dumpHex(String msg, byte[] bytes) {
    	if (null == bytes) {
    		System.out.println("bytes is null");
    		return;
    	}
        int length = bytes.length;
        msg = (msg == null) ? "" : msg;
        System.out.printf("-------------------------- " + msg + "(len:%d) --------------------------\n", length);
        for (int i = 0; i < bytes.length; i++) {
            if (i % 16 == 0) {
                if (i != 0) System.out.println();
                System.out.printf("0x%08X    ", i);
            }
            System.out.printf("%02X ", bytes[i]);
        }
        System.out.println("");
    }
}
