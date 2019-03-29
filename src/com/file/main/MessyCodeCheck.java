package com.file.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验是否乱码
 *
 * @author learrings
 * @createDate 2017年12月29日
 */
public class MessyCodeCheck {

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (char c : ch) {
			if (Character.isLetterOrDigit(c)) {
				continue;
			}

			if (!isChinese(c)) {
				count = count + 1;
			}
		}
		return count / chLength > 0.4;
	}

	public static void main(String[] args) {
		// 乱码判断
		System.out.println(isMessyCode("*��JTP.jar�ļ����JTP�ļ���ȡ��ͼƬ��Դ"));
		System.out.println(isMessyCode("dd"));
		System.out.println(isMessyCode("你好"));
	}
}