package com.file.main;

import java.util.ArrayList;
import java.util.List;

class Replacement {

	private List<ReplaceObj> includeList = new ArrayList<>();// 替换信息

	void addInclude(String oldStr, String newStr) {
		this.includeList.add(new ReplaceObj(oldStr, newStr));
	}

	List<ReplaceObj> getIncludeList() {
		return includeList;
	}

	class ReplaceObj {

		ReplaceObj(String oldStr, String newStr) {
			this.oldStr = oldStr;
			this.newStr = newStr;
		}

		private String oldStr;
		private String newStr;

		String getOldStr() {
			return oldStr;
		}

		String getNewStr() {
			return newStr;
		}

	}

	/**
	 * 替换类型
	 *
	 * @author learrings
	 * @createDate 2017年12月29日
	 */
	enum ReplaceType {
		/* 只更新文件内容 */
		CONTENT,
		/* 只更新文件名 */
		FILE,
		/* 全部 */
		All
	}
}