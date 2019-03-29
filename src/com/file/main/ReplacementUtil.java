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

public class ReplacementUtil {

	private static String ENCODING = "UTF-8";
	
	/**
	 * 批量重命名，不含根路径
	 * 
	 * @param replacementChain
	 *            - 替换信息
	 * @param replaceType
	 *            - 替换方式
	 */
	public static void multiRename(String path, Replacement replacement, ReplaceType replaceType) {
		directoryReplace(new File(path), replacement, replaceType);
	}

	/**
	 * 目录处理
	 *
	 * @param file
	 * @param replaceType
	 */
	private static void directoryReplace(File file, Replacement replacement, ReplaceType replaceType) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			/** 递归遍历所有文件* */
			for (int i = 0; i < files.length; i++) {
				directoryReplace(files[i], replacement, replaceType);
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
	 * @param file
	 * @param replacement
	 * @param replaceType
	 */
	private static void fileReName(File file, Replacement replacement, ReplaceType replaceType) {
		if (ReplaceType.CONTENT == replaceType) {
			return;
		}

		int extendIndex = file.getName().lastIndexOf(".");

		String fileName = null;
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
			newFilePath += file.getName().substring(extendIndex, file.getName().length());
		}

		file.renameTo(new File(newFilePath));
		System.out.println("======>【"+file.getAbsolutePath() + "】重命名成功。");
	}

	/**
	 * 文件内容替换
	 *
	 * @param filePath
	 * @return
	 */
	private static void contentReplace(File file, Replacement replacement, ReplaceType replaceType) {

		if (ReplaceType.FILE == replaceType) {
			return;
		}

		try {

			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), ENCODING);
			BufferedReader read = new BufferedReader(isr);

			StringBuilder content = new StringBuilder();

			String lineWriter = null;
			while ((lineWriter = read.readLine()) != null) {
				content.append(lineWriter);
				content.append("\r\n");
			}
			read.close();
			isr.close();

			String contentStr = content.toString();
			if (MessyCodeCheck.isMessyCode(contentStr)) {
				throw new Exception("此文件编码方式不是"+ENCODING+"，无法转换。");
			}
			for (ReplaceObj replaceObj : replacement.getIncludeList()) {
				contentStr = contentStr.replace(replaceObj.getOldStr(), replaceObj.getNewStr());
			}

			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), ENCODING);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(contentStr);
			bw.flush();
			bw.close();
			
			System.out.println("======>【"+file.getAbsolutePath() + "】文件内容替换成功。");
		} catch (Exception e) {
			System.out.println("======>【"+file.getAbsolutePath() + "】文件内容替换失败："+e.getMessage());
		}
	}

}