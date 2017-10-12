package com.atguigu.datingsite.test;

import com.atguigu.datingsite.util.FastDFSUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FastDFSTest {

    @Test
    public void test(){
        String localFileName = "E:\\SGG\\CODE\\IdeaProjects\\Advanced\\DatingSiteSolr\\主仆.bmp";
        String[] resultArray = FastDFSUtil.doUpload("主仆.bmp", localFileName);
        for (int i = 0; i < resultArray.length; i++){
            System.out.println(resultArray[i]);
        }
    }


}
