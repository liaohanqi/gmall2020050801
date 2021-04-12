package com.liaohanqi.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.bean.PmsBaseCatalog1;
import com.liaohanqi.gmall.bean.PmsBaseCatalog2;
import com.liaohanqi.gmall.bean.PmsBaseCatalog3;
import com.liaohanqi.gmall.service.CatalogService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchWebApplicationTests {

    @Reference
    CatalogService catalogService;

    //静态分类文件地生成
    @Test
    public void contextLoads() throws IOException {

        List<PmsBaseCatalog1> catalog1 = catalogService.getCatalog1();

        for (PmsBaseCatalog1 pmsBaseCatalog1 : catalog1) {

            List<PmsBaseCatalog2> catalog2 = catalogService.getCatalog2(pmsBaseCatalog1.getId());

            for (PmsBaseCatalog2 pmsBaseCatalog2 : catalog2) {

                List<PmsBaseCatalog3> catalog3 = catalogService.getCatalog3(pmsBaseCatalog2.getId());

                pmsBaseCatalog2.setCatalog3List(catalog3);
            }
            pmsBaseCatalog1.setCatalog2s(catalog2);

        }
        System.out.println(catalog1.size());

        //到路径，即可看到json类的静态文件
        File file = new File("D:/staticFile/catalog.json");

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        fileOutputStream.write(JSON.toJSONString(catalog1).getBytes());

    }

    /**
     * 获取文件夹中相同文件名的文件个数
     *
     * @param filePath
     * @param fileName
     * @return
     */
    private int getFileMax(String filePath, String fileName) {
        File file = new File(filePath);
        File[] files;
        int number = 0;
        if (file.isDirectory()) {
            files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.isFile()
                        && (f.getName().substring(0, f.getName().lastIndexOf("."))
                        .contains(fileName.substring(0, fileName.lastIndexOf(".")))
                        && f.getName().substring(f.getName().lastIndexOf("."))
                        .equals(fileName.substring(fileName.lastIndexOf("."))))) {
                    number = number + 1;
                }
            }
        }
        return number;
    }

    /**
     * 上传文件
     *
     * @return
     */
    /*public ServiceResult<FileInfoAO> uploadFile(MultipartFile file) throws Exception {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //使用System.getProperty("user.dir")获取当前程序运行的工作根目录
        String uploadFilePath = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
        File dest = new File(uploadFilePath);
        // 检测是否存在目录
        if (!dest.exists()) {
            dest.mkdirs();
        }
        //文件重命名，防止覆盖
        int fileMax = getFileMax(uploadFilePath, fileName);
        StringBuilder destFilePath = new StringBuilder();
        if (fileMax > 0) {
            destFilePath
                    .append(uploadFilePath)
                    .append(fileName.substring(0, fileName.lastIndexOf(".")))
                    .append("(" + fileMax + ")")
                    .append(fileName.substring(fileName.lastIndexOf(".")));
        } else {
            destFilePath.append(uploadFilePath).append(fileName);
        }
        File destFile = new File(destFilePath.toString());
        file.transferTo(destFile);
        FileInfoAO fileInfoAO = new FileInfoAO();
        fileInfoAO.setCreateTime(DateTimeUtil.formatDateTime(new Date()));
        fileInfoAO.setFileName(fileName);
        fileInfoAO.setFilePath(destFile.getAbsolutePath());
        fileInfoAO.setFileType(StringUtils.substringAfter(fileName, "."));// 获取文件的扩展名
        UserAO user = AuthUtil.getCurrentUser();
        fileInfoAO.setPulisher(user != null ? user.getUserName() : null);
        insert(fileInfoAO);
        return ServiceResultHelper.genResultWithSuccess(fileInfoAO);
    }*/

}
