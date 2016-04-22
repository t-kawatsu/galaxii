package com.galaxii.common.util.filter;

import org.apache.commons.lang3.StringUtils;

public class ExceptSearchWordFilter {

	private static final char[] EXCEPT_SURFACES = {
        '-', '(', ')', '#', '$', '\'', '!', '%', '&', '"', 
		'=', '^', '~', '|', '\\', '<', '>', ',', '.', '/', 
		'?', '{', '}', '@', '`', '+', '*', ':', ';', '[',
		']', '：', '；', '／', '＜', '＞', '？', '！', '_', 
		'＿', '＝', '”', '＃', '＄', '％', '＆', '’', '（', '）', 
		'〜', '＾', '『', '』', '＠', '＊', '＋', '「', '」', '～', 
		'』', '！', ' ', '・', '　', '»', '。', '、',
    //  '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 
    //  '１', '２', '３', '４', '５', '６', '７', '８', '９', '０', 
	};

	public static String filter(String word) {
		return StringUtils.replaceChars(word, new String(EXCEPT_SURFACES), null);
	}
}
