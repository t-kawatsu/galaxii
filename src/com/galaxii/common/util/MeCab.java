package com.galaxii.common.util;

import java.util.ArrayList;
import java.util.List;

import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.impl.StandardTagger;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeCab {

	protected static Logger logger = LoggerFactory.getLogger(MeCab.class);

    private static String[] EXCEPT_SPEECHES = {
        "副詞","連体詞","接続詞","感動詞","助動詞","助詞","記号"};

	public static String[] split(String str) {
		List<MeCabDto> res = perse(str);
		if(res == null || res.isEmpty()) {
			return null;
		}
		List<String> ret = new ArrayList<String>();
		for(MeCabDto row : res) {
			ret.add(row.getSurface());
		}
		return ret.toArray(new String[0]);
	}

	public static List<MeCabDto> perse(String str) {
		List<MeCabDto> res = MeCab.exec(str);
		if(res == null || res.isEmpty()) {
			return null;
		}
		List<MeCabDto> ret = new ArrayList<MeCabDto>();
		for(MeCabDto row : res) {
			if(ArrayUtils.contains(EXCEPT_SPEECHES, row.getSurface())) {
				continue;
			}
			if(StringUtils.isEmpty(row.getSurface())) {
				continue;
			}
			ret.add(row);
		}
		return ret;
	}
/*
	private static String exec(String str) 
		throws Exception {
		StringBuffer cmd = new StringBuffer();
		str = StringUtils.replace(str, "'", "\'");
		cmd.append("echo ")
		   .append("'" + str + "'")
		   .append(" | mecab");
		ExecCommand ec = new ExecCommand(cmd.toString());
		if(!StringUtils.isEmpty(new String(ec.getStderr()))) {
			throw new Exception(new String(ec.getStderr()));
		}
		return new String(ec.getStdout());
	}
*/
	/**
	 * @see http://code.google.com/p/cmecab-java/wiki/HowToUse
	 */
	private static List<MeCabDto> exec(String str) {
		// Taggerを構築。
		// 引数には、MeCabのcreateTagger()関数に与える引数を与える。
		StandardTagger tagger = new StandardTagger("");

		// Lattice（形態素解析に必要な実行時情報が格納されるオブジェクト）を構築
		Lattice lattice = tagger.createLattice();
		// 解析対象文字列をセット
		lattice.setSentence(str);

		// tagger.parse()を呼び出して、文字列を形態素解析する。
		tagger.parse(lattice);

		// 一つずつ形態素をたどりながら、表層形と素性を出力
		List<MeCabDto> meCabDtos = new ArrayList<MeCabDto>();
		Node node = lattice.bosNode();
		while (node != null) {
			meCabDtos.add(new MeCabDto(node.surface(), node.feature()));
			node = node.next();
		}

		// lattice, taggerを破壊
		lattice.destroy();
		tagger.destroy();
		return meCabDtos;
	}

}
