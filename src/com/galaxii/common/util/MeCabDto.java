package com.galaxii.common.util;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MeCabDto 
	implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String surface;
		private String speech;
		private String speechDetail1;
		private String speechDetail2;
		private String speechDetail3;
		private String conjugatedForm;
		private String conjugatedType;
		private String read;
		private String pronunciation;

		public MeCabDto(String surface, String feature) {
			this.surface = surface;
			String[] features = StringUtils.split(feature, ',');
			speech = features[0];
			speechDetail1 = features[1];
			speechDetail2 = features[2];
			speechDetail3 = features[3];
			conjugatedForm = features[4];
			conjugatedType = features[5];
			read = 8 <= features.length ? features[7] : null;
			pronunciation = 9 <= features.length ? features[8] : null;
		}

		public String getSurface() {
			return surface;
		}

		public String getSpeech() {
			return speech;
		}

		public String getSpeechDetail1() {
			return speechDetail1;
		}

		public String getSpeechDetail2() {
			return speechDetail2;
		}

		public String getSpeechDetail3() {
			return speechDetail3;
		}

		public String getConjugatedForm() {
			return conjugatedForm;
		}

		public String getConjugatedType() {
			return conjugatedType;
		}

		public String getRead() {
			return read;
		}

		public String getPronunciation() {
			return pronunciation;
		}

	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
}

