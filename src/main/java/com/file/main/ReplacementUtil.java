package com.file.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.file.main.Replacement.ReplaceObj;
import com.file.main.Replacement.ReplaceType;

class ReplacementUtil {

    private static final String ENCODING = "UTF-8";

    /**
     * 批量重命名，不含根路径
     *
     * @param path        - 文件路径
     * @param replacement - 替换信息
     * @param replaceType - 替换方式
     */
    static void multiRename(String path, Replacement replacement, ReplaceType replaceType) {
        directoryReplace(new File(path), replacement, replaceType);
    }

    /**
     * 目录处理
     *
     * @param file        -
     * @param replaceType -
     */
    private static void directoryReplace(File file, Replacement replacement, ReplaceType replaceType) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            /* 递归遍历所有文件* */
            for (File file1 : files) {
                directoryReplace(file1, replacement, replaceType);
            }
        } else {
            contentReplace(file, replacement, replaceType);
        }

        // 替换自身
        fileReName(file, replacement, replaceType);
    }

    /**
     * 文件/文件夹重命名
     *
     * @param file        -
     * @param replacement -
     * @param replaceType -
     */
    private static void fileReName(File file, Replacement replacement, ReplaceType replaceType) {
        if (ReplaceType.CONTENT == replaceType) {
            return;
        }

        int extendIndex = file.getName().lastIndexOf(".");

        String fileName;
        if (extendIndex > 0) {
            fileName = file.getName().substring(0, extendIndex);
        } else {
            fileName = file.getName();
        }

        for (ReplaceObj replaceObj : replacement.getIncludeList()) {
            fileName = fileName.replace(replaceObj.getOldStr(), replaceObj.getNewStr());
        }

        String newFilePath = file.getParent() + "\\" + fileName;
        if (extendIndex > 0) {
            newFilePath += file.getName().substring(extendIndex);
        }

        boolean renameResult = file.renameTo(new File(newFilePath));
        if (renameResult) {
            System.out.println("======>【" + file.getAbsolutePath() + "】重命名成功。");
        } else {
            System.out.println("======>【" + file.getAbsolutePath() + "】重命名失败。");
        }
    }

    /**
     * 文件内容替换
     *
     * @param file        -
     * @param replacement -
     * @param replaceType -
     */
    private static void contentReplace(File file, Replacement replacement, ReplaceType replaceType) {

        if (ReplaceType.FILE == replaceType) {
            return;
        }

        try {

            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), ENCODING);
            BufferedReader read = new BufferedReader(isr);

            StringBuilder content = new StringBuilder();

            String lineWriter;
            while ((lineWriter = read.readLine()) != null) {
                content.append(lineWriter);
                content.append("\r\n");
            }
            read.close();
            isr.close();

            String contentStr = content.toString();
            if (MessyCodeCheck.isMessyCode(contentStr)) {
                throw new Exception("此文件编码方式不是" + ENCODING + "，无法转换。");
            }
            for (ReplaceObj replaceObj : replacement.getIncludeList()) {
                contentStr = contentStr.replace(replaceObj.getOldStr(), replaceObj.getNewStr());
            }

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), ENCODING);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(contentStr);
            bw.flush();
            bw.close();

            System.out.println("======>【" + file.getAbsolutePath() + "】文件内容替换成功。");
        } catch (Exception e) {
            System.out.println("======>【" + file.getAbsolutePath() + "】文件内容替换失败：" + e.getMessage());
        }
    }

}