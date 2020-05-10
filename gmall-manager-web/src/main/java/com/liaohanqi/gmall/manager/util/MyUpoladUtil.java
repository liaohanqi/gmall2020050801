package com.liaohanqi.gmall.manager.util;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MyUpoladUtil {

    public static String upload_image(MultipartFile multipartFile) {
        // 初始化客户端
        String trackerPath = MyUpoladUtil.class.getClassLoader().getResource("tracker.conf").getPath();
        try {
            ClientGlobal.init(trackerPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        // 先获得tracker
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer connection = null;
        try {
            connection = trackerClient.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 再获得storage
        StorageClient storageClient = new StorageClient(connection, null);

        String url = "http://192.168.37.100";
        try {
            byte[] bytes = multipartFile.getBytes();
            // 获得图片扩展名
            String originalFilename = multipartFile.getOriginalFilename();

            int lastPoint = originalFilename.lastIndexOf(".");

            String ext = originalFilename.substring(lastPoint + 1);

            String[] uris = storageClient.upload_file(bytes, ext, null);
            for (String uri : uris) {
                System.out.println(uri);
                url = url + "/" + uri;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return url;
    }

}
