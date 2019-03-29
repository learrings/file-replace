package com.file.main;

import java.util.ArrayList;
import java.util.List;

public class Replacement {

	private List<ReplaceObj> includeList = new ArrayList<>();// 替换信息

	public Replacement addInclude(String oldStr, String newStr) {
		this.includeList.add(new ReplaceObj(oldStr, newStr));
		return this;
	}

	public List<ReplaceObj> getIncludeList() {
		return includeList;
	}

	public class ReplaceObj {

		ReplaceObj(String oldStr, String newStr) {
			this.oldStr = oldStr;
			this.newStr = newStr;
		}

		private String oldStr;
		private String newStr;

		public String getOldStr() {
			return oldStr;
		}

		public String getNewStr() {
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
		CONTENT, FILE, All
	}
}