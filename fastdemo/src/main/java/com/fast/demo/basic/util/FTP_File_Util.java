package com.fast.demo.basic.util;


import com.fast.demo.basic.constants.BaseHttpStatusConstants;
import com.fast.demo.basic.vo.FTP_File_Param;
import com.fast.demo.basic.vo.ResponseMsg;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author C3006248
 * ftp文件下載或上傳工具類
 * @create 2020/9/15  10:27
 */
@Component
@Data
@Slf4j
@PropertySource("classpath:configuration/ftp.properties")
public class FTP_File_Util {
//    public static final int imageCutSize = 300;

    @Value("${ftp.username}")
    private String userName;

    @Value("${ftp.password}")
    private String passWord;

    @Value("${ftp.address}")
    private String ip;


    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.filepath}")
    private String CURRENT_DIR;     // 文件存放的目录

    public static final String DIRSPLIT = "/";

    // 下载的文件目录
    private String DOWNLOAD_DIR;

    // ftp客户端
    private FTPClient ftpClient = new FTPClient();

    /**
     * 功能：上传文件附件到文件服务器
     *
     * @param buffIn:上传文件流
     * @param fileName：保存文件名称
     * @param needDelete：是否同时删除
     * @return
     * @throws IOException
     */
    /*public boolean uploadToFtp(InputStream buffIn, String fileName, boolean needDelete)
            throws Exception {
        boolean returnValue = false;
        // 上传文件
        try {

            // 建立连接
            connectToServer();
            // 设置传输二进制文件
            setFileType(FTP.BINARY_FILE_TYPE);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("failed to connect to the FTP Server:" + ip);
            }
            ftpClient.enterLocalPassiveMode();

          *//*  if(!existDirectory(CURRENT_DIR)){
                this.createDirectory(CURRENT_DIR);
            }
            ftpClient.changeWorkingDirectory(CURRENT_DIR);*//*

            // 上传文件到ftp
            returnValue = ftpClient.storeFile(fileName, buffIn);
            if (needDelete) {
                ftpClient.deleteFile(fileName);
            }
            // 输出操作结果信息
            if (returnValue) {
                log.info("uploadToFtp INFO: upload file  to ftp : succeed!");
            } else {
                log.info("uploadToFtp INFO: upload file  to ftp : failed!");
            }
            buffIn.close();
            // 关闭连接
            closeConnect();
        } catch (FTPConnectionClosedException e) {
            log.error("ftp连接被关闭！", e);
            throw e;
        } catch (Exception e) {
            returnValue = false;
            log.error("ERR : upload file  to ftp : failed! ", e);
            throw e;
        } finally {
            try {
                if (buffIn != null) {
                    buffIn.close();
                }
            } catch (Exception e) {
                log.error("ftp关闭输入流时失败！", e);
            }
            if (ftpClient.isConnected()) {
                closeConnect();
            }
        }
        return returnValue;
    }


    *//**
     * 功能：根据文件名称，下载文件流
     *
     * @param filename
     * @return
     * @throws IOException
     *//*
    public InputStream downloadFile(String filename)
            throws IOException {
        InputStream in = null;
        try {

            // 建立连接
            connectToServer();
            ftpClient.enterLocalPassiveMode();
            // 设置传输二进制文件
            setFileType(FTP.BINARY_FILE_TYPE);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new IOException("failed to connect to the FTP Server:" + ip);
            }
            ftpClient.changeWorkingDirectory(CURRENT_DIR);

            // ftp文件获取文件
            in = ftpClient.retrieveFileStream(filename);

        } catch (FTPConnectionClosedException e) {
            log.error("ftp连接被关闭！", e);
            throw e;
        } catch (Exception e) {
            log.error("ERR : upload file " + filename + " from ftp : failed!", e);
        }
        return in;
    }*/

/*----------------------------------------------以上兩個方法暫時不用----------------------------------------------------- */
    /**
     * 文件上傳
     * @param type_base_path 類型根路徑（例如： user_pic為用戶頭像圖片類型的文件的根路徑）
     * @param file 文件
     * @return 響應結果
     */
    public ResponseMsg fileUpload(String type_base_path, MultipartFile file) throws IOException {
        String file_origin_name = file.getOriginalFilename();
//        InputStream input = null;
//        try {
//            input = file.getInputStream();
//        } catch (IOException e) {
//            //e.printStackTrace();
//            if (input!=null){
//                input.close();
//            }
//            return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500_VALUE, "上傳文件失敗", e.getMessage());
//        }

        String suffix = "";
        int lastIndexOfDot = file_origin_name.lastIndexOf(".");
        if(lastIndexOfDot != -1) {
            suffix = file_origin_name.substring(lastIndexOfDot);
        }else {
            return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500_VALUE, "上傳文件失敗,文件無後綴名");
        }

        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        String month = new SimpleDateFormat("MM").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        //String file_save_path = type_base_path + File.separator + year + File.separator + month + File.separator + day;// 取得配置文件路徑
      //  type_base_path=Big5ToGbk(type_base_path);
        String file_save_path = new String(type_base_path.getBytes("BIG5"),"GBK");
        String file_save_name = file_origin_name;


        try {
            int replay;
            connectToServer();
            // 设置传输二进制文件
            setFileType(FTP.BINARY_FILE_TYPE);
            replay = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replay)) {
                ftpClient.disconnect();
                return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500_VALUE, "上傳文件失敗");
            }
            String workingDirectory = ftpClient.printWorkingDirectory();
            String[] paths = file_save_path.split(("\\\\"));

            for(int i = 0; i < paths.length; i++) {
                ftpClient.makeDirectory(paths[i]);
                boolean changeWorkingDirectory_flag = ftpClient.changeWorkingDirectory(paths[i]);
                if(!changeWorkingDirectory_flag) {
                    return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500_VALUE, "上傳文件失敗",workingDirectory);
                }
            }
           // file_save_name= new String(file_save_name.getBytes("BIG5"),"GBK");

            boolean b = ftpClient.storeFile(new String(file_save_name.getBytes("BIG5"),"GBK"),  file.getInputStream());
            if (!b){
                return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500_VALUE, "上傳文件失敗");
            }
            //input.close();
            ftpClient.logout();
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500_VALUE, "上傳文件失敗",e.getMessage());
        } finally {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
//                if (input!=null){
//                    input.close();
//                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        }
        FTP_File_Param ftp_file_param = new FTP_File_Param();
        file_save_path=new String(file_save_path.getBytes("GBK"),"BIG5");
        String file_url=file_save_path + File.separator + file_save_name;
        ftp_file_param.setFile_url(file_url);
        ftp_file_param.setFile_origin_name(file_origin_name);
        return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "上傳文件成功",ftp_file_param);
    }

    /**
     * 文件下載
     * @param file_save_path 文件保存路徑
     * @param file_save_name 文件保存名稱
     * @return 響應結果
     */
    public ResponseMsg fileDownload(String file_save_path, String file_save_name) {
        InputStream is = null;
        try {
            int replay;
            connectToServer();
            replay = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replay)) {
                ftpClient.disconnect();
                return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "下載文件失敗");
            }

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            if(file_save_path.lastIndexOf("\\")!=-1) {
                file_save_path = file_save_path.substring(0, file_save_path.lastIndexOf(File.separator));
                String[] paths = file_save_path.split("\\\\");
                for (int i = 0; i < paths.length; i++) {
                    //ftp.makeDirectory(paths[i]); //切換目錄
                    boolean changeWorkingDirectory_flag = ftpClient.changeWorkingDirectory(paths[i]);
                    if (!changeWorkingDirectory_flag) {
                        return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "下載文件失敗");
                    }
                }
            }


            is =ftpClient.retrieveFileStream(file_save_name);
            //ftp.logout();// 必須把登出的代碼注釋掉，不然會導致大文件無法下載
        } catch (Exception e) {
            return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "下載文件失敗");
        } finally {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }

        return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "下載文件成功", is);
    }

    /**
     * 刪除文件
     * @param file_url
     * @return
     */
  /* public ResponseMsg fileDelete(String file_url) {

        ftpClient.setControlEncoding("GBK");
        try {
            connectToServer();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //String delfile_url = file_save_path + File.separator + file_save_name;
            String delfile_url = file_url;

            boolean deleteFile_flag = ftpClient.deleteFile(delfile_url);

            if(!deleteFile_flag) {
                return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "刪除文件失敗,文件未刪除/文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "刪除文件失敗,"+e.getMessage());
        } finally {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "刪除文件失敗,"+e.getMessage());
            }
        }

        return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "刪除文件成功");
    }

*/

    /**
     * 刪除目錄或者文件
     * @param file_url 文件路徑
     * @return
     */
    public ResponseMsg deleteBlankDirOrFile(String file_url) {
        if(file_url == null || ".".equals(file_url)||file_url.substring(file_url.length()-1,file_url.length()).equals(File.separator)) {
            return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "參數錯誤");
        }
        ResponseMsg rm=null;
        boolean flag;
        try {
            connectToServer();
            // 根据remoteDirOrRemoteFile是文件还是目录,来切换changeWorkingDirectory
            if (file_url.lastIndexOf(".") < 0) { //DOT_STR .
                // 出于保护机制:如果当前文件夹中是空的,那么才能删除成功
                flag = ftpClient.removeDirectory(file_url);
                // 不排除那些 没有后缀名的文件 存在的可能;
                // 如果删除空文件夹失败,那么其可能是没有后缀名的文件,那么尝试着删除文件
                if (!flag) {
                    flag=ftpClient.deleteFile(file_url);
                    return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "目錄非空");
                }
                rm= new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "刪除目錄成功");
                // 如果是文件,那么直接删除该文件
            } else {
                flag = ftpClient.deleteFile(file_url);
                if(!flag) {
                    return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "刪除文件失敗,文件未刪除/文件不存在");
                }
                rm= new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "刪除文件成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return rm;
    }


    public ResponseMsg recursiveDeleteBlankDirOrFile(String deletedBlankDirOrFile){
        ResponseMsg rm=null;
        try{
            connectToServer();
            FTPFile[] subFiles = ftpClient.listFiles(deletedBlankDirOrFile);
            String filePath="";
            if (subFiles != null && subFiles.length > 0) {
                for (FTPFile aFile : subFiles) {
                    String currentFileName = aFile.getName();
                    filePath = deletedBlankDirOrFile+ "/" + currentFileName;

                    if (!currentFileName.contains(".")||aFile.isDirectory()) {
                        FTPFile[] Files= ftpClient.listFiles(filePath);
                        if (Files != null && subFiles.length > 0){
                            recursiveDeleteBlankDirOrFile(filePath);
                        }else {
                            return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "刪除失敗");
                        }
                        rm=new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "刪除成功");
                    }else {
                      boolean flag = ftpClient.deleteFile(filePath);
                      if(!flag){
                          return new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "刪除失敗");
                      }
                        rm=new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "刪除成功");
                    }
                }
            }
            rm=deleteBlankDirOrFile(deletedBlankDirOrFile);

        }catch (Exception e) {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException b) {
                log.error(e.getMessage());
            }
        }
        return rm;

    }

    /**
     * 转码[GBK -> ISO-8859-1] 不同的平台需要不同的转码
     *
     * @param obj
     * @return
     */
//    private String Big5ToGbk(Object obj) {
//        try {
//            if (obj == null)
//                return "";
//            else
//                return new String(obj.toString().getBytes("BIG5"), "GBK");
//        } catch (Exception e) {
//            return "";
//        }
//    }

    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     *
     * @param fileType --BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    private void setFileType(int fileType) {
        try {
            ftpClient.setFileType(fileType);
        } catch (Exception e) {
            log.error("ftp设置传输文件的类型时失败！", e);
        }
    }

    /**
     * 功能：关闭连接
     */
    public void closeConnect() {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            log.error("ftp连接关闭失败！", e);
        }
    }

    /**
     * 连接到ftp服务器
     */
    private void connectToServer() throws IOException {
        if (!ftpClient.isConnected()) {
            int reply;
            try {
                //ftpClient = new FTPClient();
                ftpClient.setControlEncoding("GBK");
                ftpClient.enterLocalPassiveMode();
                ftpClient.connect(ip, port);
                ftpClient.login(userName, passWord);

                reply = ftpClient.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    log.info("connectToServer FTP server refused connection.");
                }

            } catch (FTPConnectionClosedException ex) {
                log.error("服务器:IP：" + ip + "没有连接数！there are too many connected users,please try later", ex);
                throw ex;
            } catch (Exception e) {
                log.error("登录ftp服务器【" + ip + "】失败", e);
                throw e;
            }
        }
    }

    //判斷是否存在目錄
    public boolean existDirectory(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory()
                    && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

   /**
    * @author :C300628
    * @Description:创建FTP文件夹目录
    * @create : 2020/9/16 10:03
    */
    public boolean createDirectory(String pathName) throws IOException {

        boolean isSuccess = false;
        try {
            connectToServer();
            isSuccess = ftpClient.makeDirectory(pathName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return isSuccess;
    }
    /**
     * @author :C300628
     * @Description:刪除ftp文件夾目錄
     * @create : 2020/9/16 10:03
     */
    public boolean deleteDirectory(String pathName) throws IOException{
        boolean isSuccess = false;
        try {
            connectToServer();
            isSuccess = ftpClient.removeDirectory(pathName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return isSuccess;
    }

    /**
     * 带点的
     *
     * @param fileName
     * @return
     */
    public static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }

    /**
     * 不带点
     *
     * @param fileName
     * @return
     */
    public static String getNoPointExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos + 1);
    }

    /**
     * 功能：根据当前时间获取文件目录
     *
     * @return String
     */
    public static String getDateDir(Date dateParam) {
        Calendar cal = Calendar.getInstance();
        if (null != dateParam) {
            cal.setTime(dateParam);
        }
        int currentYear = cal.get(Calendar.YEAR);
        int currentMouth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        //int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        //return currentYear+FtpOperation.DIRSPLIT+currentMouth+FtpOperation.DIRSPLIT+currentDay+FtpOperation.DIRSPLIT+currentHour;
        return currentYear + FTP_File_Util.DIRSPLIT + currentMouth + FTP_File_Util.DIRSPLIT + currentDay;
    }

    /**
     * 返回FTP目录下的文件列表
     * @param ftpDirectory
     * @return
     */
    public ResponseMsg getFileNameList(String ftpDirectory) {
        List<String> list = new ArrayList<String>();
        ResponseMsg rm=null;
        try
        {
            connectToServer();

            String[] strings = ftpClient.listNames(ftpDirectory);
            if(strings!=null) {
                for (String filename : strings) {
                    filename = filename.replace("\\", "/");
                    String[] paths = filename.split("/");
                    String name = paths[paths.length - 1];
                    name = new String(name.getBytes("GBK"), "BIG5");
                    list.add(name);
                }
            }else{
                rm=new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_500, "查詢目錄不存在");
            }
            rm=new ResponseMsg(BaseHttpStatusConstants.HTTP_RES_CODE_200, "查詢成功",list);

        } catch (Exception e) {
        }finally {
            try {
                if(ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
              log.error(e.getMessage());
            }
        }
        return rm;
    }

    /**
     * 重命名
     * @param srcFname
     * @param targetFname
     * @return
     */
    public boolean renameFile(String srcFname,String targetFname) {
        boolean flag = false;
        try {
            connectToServer();
            flag = ftpClient.rename(srcFname, targetFname);
        } catch (Exception e) {

        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return flag;
    }

}
