package com.atguigu.datingsite.util;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class FastDFSUtil {

    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;

    static {
        try {
            String path = FastDFSUtil.class.getResource("/tracker.conf").getPath();
            ClientGlobal.init(path);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = null;
            storageClient = new StorageClient(trackerServer, storageServer);
            System.out.println(storageClient);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public static int deleteFile(String groupName, String remoteFilename){
        int result = 0;
        try {
             result = storageClient.delete_file(groupName, remoteFilename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] doUpload(String fileName, byte[] bytes){
        String extName = getExtName(fileName);
        String[] resultArray = null;
        try {
            resultArray = storageClient.upload_file(bytes, extName, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return resultArray;
    }

    public static String[] doUpload(String fileName, String localFileName){
        String extName = getExtName(fileName);
        String[] resultArray = null;
        try {
            resultArray = storageClient.upload_file(localFileName, extName, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return resultArray;
    }


    public static String getExtName(String fileName){
        if (fileName == null || "".equals(fileName)){
            return null;
        }
        int lastIndex = fileName.lastIndexOf(".") + 1;
        String extName = fileName.substring(lastIndex);
        return extName;
    }


}
