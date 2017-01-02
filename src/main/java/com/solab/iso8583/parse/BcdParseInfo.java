package com.solab.iso8583.parse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.solab.iso8583.CustomField;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;
import com.solab.iso8583.codecs.BcdCodec;

public class BcdParseInfo extends FieldParseInfo {

	public BcdParseInfo(int len) {
		super(IsoType.BCD, len);
	}

	@Override
	public <T> IsoValue<?> parse(int field, byte[] buf, int pos,
			CustomField<T> custom) throws ParseException,
			UnsupportedEncodingException {
		if (pos < 0) {
			throw new ParseException(
					String.format(
							"Invalid BCD field %d position %d",
							new Object[] { Integer.valueOf(field),
									Integer.valueOf(pos) }), pos);
		}
		int len = this.length / 2 + this.length % 2;
		if (pos + len > buf.length) {
			throw new ParseException(String.format(
					"Insufficient data for BCD field %d of length %d, pos %d",
					new Object[] { Integer.valueOf(field),
							Integer.valueOf(len), Integer.valueOf(pos) }), pos);
		}

		String val = BcdCodec.bcdToStr(buf, pos, len);
		if (val.length() > this.length) {
			val = val.substring(0, this.length);
		}
		if (custom == null) {
			return new IsoValue(this.type, val, this.length, null);
		}

		IsoValue v = new IsoValue(this.type, custom.decodeField(new String(buf,
				pos, this.length, getCharacterEncoding())), this.length, custom);
		if (v.getValue() == null) {
			return new IsoValue(this.type, val, val.length(), null);
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
