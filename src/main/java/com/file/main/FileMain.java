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
		// 文件路径
		String path = "D:\\workspace\\test";
		Replacement replacement = new Replacement();
		replacement.addInclude("aa", "ww");
		replacement.addInclude("bb", "vv");

		System.out.println("============开始替换============");
		ReplacementUtil.multiRename(path, replacement, ReplaceType.CONTENT);
		ReplacementUtil.multiRename(path, replacement, ReplaceType.FILE);
		System.out.println("============结束替换============");
	}
}
