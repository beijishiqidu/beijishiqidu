package com.test.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;

public class FileOperator {

    public static void main(String[] args) throws IOException {
        File srcFile = new File("D:\\work\\centos\\ubuntu\\ubuntu_16_4.vdi");
        File destFile01 = new File("D:\\work\\centos\\ubuntu\\ubuntu_16_4_001.vdi");
        File destFile02 = new File("D:\\work\\centos\\ubuntu\\ubuntu_16_4_002.vdi");

        long startMillions = System.currentTimeMillis();
        copyFileByIO(srcFile, destFile01);
        System.out.println("IO:=" + (System.currentTimeMillis() - startMillions));

        startMillions = System.currentTimeMillis();
        copyFileByNIO(srcFile, destFile02);
        System.out.println("NIO:=" + (System.currentTimeMillis() - startMillions));
    }

    private static void copyFileByIO(File srcFile, File destFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            byte[] b = new byte[31457280];
            int len = 0;
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
            }
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(fis);
        }
    }

    private static void copyFileByNIO(File srcFile, File destFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            long size = input.size();
            long pos = 0;
            long count = 0;
            while (pos < size) {
                count = (size - pos) > 31457280 ? 31457280 : (size - pos);
                pos += output.transferFrom(input, pos, count);
            }
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fis);
        }
    }
}
