package com.genesis.apps.comm.net.ga;

import android.util.Log;

import com.genesis.apps.comm.MyApplication;
import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.UserProfileVO;
import com.genesis.apps.comm.util.crypt.AesUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.genesis.apps.comm.model.constants.GAInfo.TAG_MSG_LOGININFO;

@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public @Data
class LoginInfoDTO extends BaseData {
    private final String TAG = LoginInfoDTO.class.getSimpleName();
    private byte[] key = new byte[]{
            (byte)0xDC, 0x0F, 0x79, (byte)0xCA, 0x39, 0x7E, (byte)0xB3, (byte)0x8C, 0x0A, 0x2E, (byte)0xB8, (byte)0x80, (byte)0xB2, 0x39, (byte)0x8B, 0x7D};

    @Expose
    private String accessToken;
    @Expose
    private String refreshToken;
    @Expose
    private long expiresDate;
    @Expose
    private UserProfileVO profile;
    @Expose
    private long refreshTokenExpriesDate;
    @Expose
    private String tokenCode;


    public LoginInfoDTO(){

    }

    public void refereshData(String accessToken, String refreshToken, long expiresDate, UserProfileVO profile, long refreshTokenExpriesDate, String tokenCode){
        this.accessToken =accessToken;
        this.refreshToken = refreshToken;
        this.expiresDate = expiresDate;
        this.profile = profile;
        this.refreshTokenExpriesDate = refreshTokenExpriesDate;
        this.tokenCode = tokenCode;
    }


    public LoginInfoDTO loadLoginInfo() {
        LoginInfoDTO loginInfoDTO = null;

        try {

            File dir = MyApplication.getInstance().getFilesDir();
            File dataDir = new File(dir, "/data");
            if (!dataDir.exists()) dataDir.mkdirs();

            // added by mkpark
            // ???????????? ??????????????????(????????? ??????)
            // ?????? ????????? ?????? ?????? ????????? ????????? ????????? ?????? ??? ??????
            File encDataFile = new File(dataDir, "cLoginInfo.json");
            if (!encDataFile.exists()) {
                // ???????????? ????????? ???????????? ??????

                // ????????? ????????? ?????? ?????? ????????? ????????? ??????
                File dataFile = new File(dataDir, "loginInfo.json");
                byte[] bytes;

                if (dataFile.exists()) {

                    // ????????? ??????
                    BufferedInputStream buf = null;
                    try {
                        int size = (int) dataFile.length();
                        bytes = new byte[size];
                        buf = new BufferedInputStream(new FileInputStream(dataFile));
                        buf.read(bytes, 0, bytes.length);
                        buf.close();
                        buf = null;
                    } catch (FileNotFoundException e) {
                        bytes = null;
                        e.printStackTrace();
                    } catch (IOException e) {
                        bytes = null;
                        e.printStackTrace();
                    } finally {
                        if (buf != null) {
                            try {
                                buf.close();
                            } catch (IOException ignored) {
                            }
                        }
                    }

                    byte[] result = null;
                    if (bytes != null && bytes.length > 0) {
                        try {
                            result = AesUtils.encryptAES128_CTR(key, new byte[16], bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (result != null && result.length > 0) {
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(encDataFile);
                            fos.write(result);
                            fos.close();
                            fos = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException ignored) {
                                }
                            }
                        }

                        dataFile.delete();
                    }
                }
            } else {   // ?????? ???????????? ????????? ?????? ???????????? ?????? ????????? ???????????? ??????
                File dataFile = new File(dataDir, "loginInfo.json");
                if (dataFile.exists()) dataFile.delete();
            }

            if (encDataFile.exists()) {
                Gson gson = new Gson();
                //Reader reader = null;
                BufferedInputStream buf = null;
                try {
                    byte[] bytes = null;
                    try {
                        int size = (int) encDataFile.length();
                        bytes = new byte[size];
                        buf = new BufferedInputStream(new FileInputStream(encDataFile));
                        buf.read(bytes, 0, bytes.length);
                        buf.close();
                        buf = null;
                    } catch (FileNotFoundException e) {
                        bytes = null;
                        e.printStackTrace();
                    } catch (IOException e) {
                        bytes = null;
                        e.printStackTrace();
                    } finally {
                        if (buf != null) {
                            buf.close();
                            buf = null;
                        }
                    }

                    byte[] result = null;
                    if (bytes != null && bytes.length > 0) {
                        try {
                            result = AesUtils.decryptAES128_CTR(key, new byte[16], bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (result != null && result.length > 0) {
//                reader = new FileReader(new String(result));
                        //loginInfo = gson.fromJson(reader, LoginInfo.class);
                        loginInfoDTO = gson.fromJson(new String(result), LoginInfoDTO.class);
                        //reader.close();
                        //reader = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }catch (Exception ignore){

        }

        Log.d(TAG, TAG_MSG_LOGININFO + (loginInfoDTO !=null ? loginInfoDTO.toString() : "null"));
        return loginInfoDTO;
    }

    public boolean updateLoginInfo(LoginInfoDTO loginInfoDTO) {
        Log.d(TAG, TAG_MSG_LOGININFO + (loginInfoDTO !=null ? loginInfoDTO.toString() : "null"));
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(loginInfoDTO);
        File dir = MyApplication.getInstance().getFilesDir();
        File dataDir = new File(dir, "/data");

        // ?????? ??????
        // ???????????? ??????????????? ???????????? ?????? ????????? ????????? ????????? ??????
        File dataFile = new File(dataDir, "loginInfo.json");
        if (dataFile.exists()) dataFile.delete();

        if (!dataDir.exists()) dataDir.mkdirs();
        File encDataFile = new File(dataDir, "cLoginInfo.json");
        if (encDataFile.exists()) encDataFile.delete();

        byte[] bytes;
        bytes = json.getBytes();
        byte[] result = null;
        if( bytes != null && bytes.length > 0 )
        {
            try {
                result = AesUtils.encryptAES128_CTR(key, new byte[16], bytes);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if( result != null && result.length > 0 )
        {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(encDataFile);
                fos.write(result);
                fos.close();
                fos = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        return true;
    }

    public void clearLoginInfo() {
        try {
            File dir = MyApplication.getInstance().getFilesDir();
            File dataDir = new File(dir, "/data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
            File dataFile = new File(dataDir, "cLoginInfo.json");
            if (dataFile.exists()) {
                dataFile.delete();
            }

            // ?????? ??????
            dataFile = new File(dataDir, "loginInfo.json");
            if (dataFile.exists()) {
                dataFile.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        this.accessToken ="";
        this.refreshToken = "";
        this.expiresDate = 0;
        this.profile = null;
        this.refreshTokenExpriesDate = 0;
        this.tokenCode = "";

    }

}
