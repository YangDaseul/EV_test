package com.genesis.apps.comm.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 파일 I/O 관련 클래스
 */
public class FileUtil {
    public static void MakeDir(String sFN) {
        try {
            sFN = substr(sFN, 0, sFN.lastIndexOf("/"));
            File f = new File(sFN);
            if (!f.isDirectory()) f.mkdirs();
            f = null;
        } catch (Exception ignore) {
        }
    }

    public static void RmFile(String sFile) {
        try {
            File fi = new File(sFile);
            fi.delete();
        } catch (Exception e) {
        }
    }

    public static void RmAllFiles(String sPath) {
        RmAllFiles(sPath, 0);
    }

    public static void RmAllFiles(String sPath, long tm) {
        try {
            File p = new File(sPath);

            if (p.isDirectory()) {
                File[] af = p.listFiles();
                for (int i = 0; i < af.length; i++) {
                    if (af[i].isDirectory()) {
                        RmAllFiles(af[i].getAbsolutePath(), tm);
                        af[i].delete();
                        af[i] = null;
                    } else {
                        if (af[i].lastModified() < tm) {
                            af[i].delete();
                            af[i] = null;
                        }
                    }
                }
            }
            p = null;
        } catch (Exception e) {
            //
        }
    }

    public static ArrayList<String> getAllFile(String sPath) {
        ArrayList<String> r = new ArrayList<String>();
        try {
            File p = new File(sPath);

            if (p.isDirectory()) {
                File[] af = p.listFiles();

                for (int i = 0; i < af.length; i++) {
                    r.add(af[i].getName());
                }
            }
            p = null;
        } catch (Exception e) {
        }
        return r;
    }

    public static long FileLength(String filename) {
        long r = -1;
        File f = new File(filename);
        if (f.exists() && f.isFile()) r = f.length();
        return r;
    }

    public static String ReadFileString(String filename) {
        byte[] r = ReadFile(filename);
        if (r == null) return "";
        return new String(r);
    }

    public static byte[] ReadFile(String filename) {
        return ReadFile(filename, 0, 0);
    }

    public static byte[] ReadFile(String filename, int offset, int count) {
        byte[] r = null;
        byte[] t = null;
        int filesize = (int) FileLength(filename);

        if (filesize > 0) {
            try (FileInputStream fis = new FileInputStream(filename)) {

                if (offset < 0) offset = 0;
                if (count <= 0) count = filesize - offset;
                if (count > filesize) count = filesize;
                if (offset + count > filesize) offset = filesize - count;

                if (offset > 0) t = new byte[offset];
                r = new byte[count];
                if (t != null) fis.read(t, 0, offset);
                fis.read(r, 0, count);
            } catch (Exception e) {
                r = null;
            }
        }

        return r;
    }

    public static boolean WriteFile(String filename, byte[] buf) {
        RmFile(filename);
        return WriteFile(filename, buf, false);
    }

    public static boolean WriteFile(String filename, byte[] buf, boolean isAppend) {
        MakeDir(filename);
        try (FileOutputStream fos = new FileOutputStream(filename, isAppend)) {
            fos.write(buf);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean WriteFile(String filename, InputStream fis) {
        MakeDir(filename);
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            byte[] tmp = new byte[1024];
            int nb = 0;
            while ((nb = fis.read(tmp)) != -1) fos.write(tmp, 0, nb);
            fis.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void FileCopy(String src, String tar) {
        if (exists(src)) {
            try (FileInputStream fis = new FileInputStream(src);
                 FileOutputStream fos = new FileOutputStream(tar)) {
                byte[] tmp = new byte[1024];
                int nb = 0;
                while ((nb = fis.read(tmp)) != -1) fos.write(tmp, 0, nb);
            } catch (Exception e) {
                //
            }
        }
    }

    public static boolean exists(String filename) {
        if (TextUtils.isEmpty(filename)) return false;
        File f = new File(filename);
        return (f.exists() && f.isFile());
    }

    private static String substr(String src, int s, int l) {
        try {
            return src.substring(s, l);
        } catch (Exception e) {
            return "";
        }
    }
}
