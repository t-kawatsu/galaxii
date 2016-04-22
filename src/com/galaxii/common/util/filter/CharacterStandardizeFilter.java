package com.galaxii.common.util.filter;

import com.ibm.icu.text.Normalizer;

public class CharacterStandardizeFilter {

	//「半角カタカナ」を「全角カタカナ」に変換します。
	//「全角」英数字を「半角」に変換します。
	public static String filter(String str) {
		return Normalizer.normalize(str, Normalizer.NFKC);
	}
}
