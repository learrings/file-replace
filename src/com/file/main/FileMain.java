package com.file.main;

import com.file.main.Replacement.ReplaceType;

/**
 * 批量替换文件名/文件内容
 *
 * @author learrings
 * @createDate 2017年12月29日
 */
public class FileMain {

	public static void main(String[] args) {
		String path = "D:\\Eclipse-Workspace3\\test";
		Replacement replacement = new Replacement();
		replacement.addInclude("aa", "ww");

		System.out.println("============开始替换============");
		ReplacementUtil.multiRename(path, replacement, ReplaceType.All);
		System.out.println("============结束替换============");
	}
}
