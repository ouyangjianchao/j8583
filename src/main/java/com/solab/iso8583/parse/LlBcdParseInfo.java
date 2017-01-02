package com.solab.iso8583.parse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.solab.iso8583.CustomField;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;
import com.solab.iso8583.codecs.BcdCodec;

public class LlBcdParseInfo extends FieldParseInfo {

	public LlBcdParseInfo() {
		super(IsoType.LLBCD, 0);
	}

	@Override
	public <T> IsoValue<?> parse(int field, byte[] buf, int pos,
			CustomField<T> custom) throws ParseException,
			UnsupportedEncodingException {
		if (pos < 0) {
			throw new ParseException(String.format(
					"Invalid LLBCD field %d position %d", new Object[] {
							Integer.valueOf(field), Integer.valueOf(pos) }),
					pos);
		}
		int len = BcdCodec.bcdToInt(buf, pos, 1);
		int subLen = len / 2 + len % 2;
		if (pos + 1 + subLen > buf.length) {
			throw new ParseException(
					String.format(
							"Insufficient data for LLBCD field %d of length %d, pos %d",
							new Object[] { Integer.valueOf(field),
									Integer.valueOf(subLen),
									Integer.valueOf(pos) }), pos);
		}

		String val = BcdCodec.bcdToStr(buf, pos + 1, subLen);
		if (val.length() != len) {
			val = val.substring(0, len);
		}
		if (custom == null) {
			return new IsoValue(this.type, val, subLen, null);
		}

		IsoValue v = new IsoValue(this.type, custom.decodeField(new String(buf,
				pos + 1, subLen, getCharacterEncoding())), subLen, custom);
		if (v.getValue() == null) {
			return new IsoValue(this.type, val, subLen, null);
		}
		return v;
	}

	@Override
	public <T> IsoValue<?> parseBinary(int field, byte[] buf, int pos,
			CustomField<T> custom) throws ParseException,
			UnsupportedEncodingException {
		return parse(field, buf, pos, custom);
	}

}
