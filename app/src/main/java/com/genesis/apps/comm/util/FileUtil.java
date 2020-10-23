package com.genesis.apps.comm.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

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



    public static String getRealPathFromURI(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                } else {
                    String SDcardpath = getRemovableSDCardPath(context).split("/Android")[0];
                    return SDcardpath +"/"+ split[1];
                }
            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public static String getRemovableSDCardPath(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return storages[1].toString();
        else
            return "";
    }


    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }


    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }


    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

}
