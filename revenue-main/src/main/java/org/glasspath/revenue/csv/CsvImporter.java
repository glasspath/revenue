/*
 * This file is part of Glasspath Revenue.
 * Copyright (C) 2011 - 2022 Remco Poelstra
 * Authors: Remco Poelstra
 * 
 * This program is offered under a commercial and under the AGPL license.
 * For commercial licensing, contact us at https://glasspath.org. For AGPL licensing, see below.
 * 
 * AGPL licensing:
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.glasspath.revenue.csv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;

import org.glasspath.common.date.DateUtils;
import org.glasspath.common.date.Time;
import org.glasspath.common.format.FormatUtils;
import org.glasspath.revenue.resources.Resources;

public abstract class CsvImporter {

	public static final String[][] DATE_FORMATS = {

			{ "^\\d{8}$", "yyyyMMdd" }, //$NON-NLS-1$ //$NON-NLS-2$

			{ "^[a-z]{2,3}\\s\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "E dd MMM yyyy" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^[a-z]{2,3}\\s\\d{1,2}-[a-z]{3}-\\d{4}$", "E dd-MMM-yyyy" }, //$NON-NLS-1$ //$NON-NLS-2$

			{ "^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{12}$", "yyyyMMddHHmm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{14}$", "yyyyMMddHHmmss" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss" }, //$NON-NLS-1$ //$NON-NLS-2$
			{ "^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss" }, //$NON-NLS-1$ //$NON-NLS-2$
	};

	private final List<CsvField> fields;

	public CsvImporter() {
		this.fields = createFields();
	}

	public abstract String getPreferencesKey();

	protected abstract List<CsvField> createFields();

	public abstract JCheckBox getCheckForExistingItemsCheckBox1();

	public abstract JCheckBox getCheckForExistingItemsCheckBox2();

	public abstract void startRow();

	public abstract boolean finishRow();

	public List<CsvField> getFields() {
		return fields;
	}

	public int getFieldCount() {
		return fields.size();
	}

	public CsvField getField(int fieldIndex) {
		return fields.get(fieldIndex);
	}

	public String getFieldName(int fieldIndex) {
		return fields.get(fieldIndex).getName();
	}

	public static Integer getInteger(Object value) {

		if (value != null) {

			try {
				return Integer.parseInt(value.toString());
			} catch (Exception e) {
				// TODO?
			}

		}

		return null;

	}

	public static Float getFloat(Object value) {

		if (value != null) {

			try {
				return FormatUtils.DECIMAL_FORMAT.parse(value.toString()).floatValue();
			} catch (Exception e) {
				// TODO?
			}

		}

		return null;

	}

	public static Long getLong(Object value) {

		if (value != null) {

			try {
				return Long.parseLong(value.toString());
			} catch (Exception e) {
				// TODO?
			}

		}

		return null;

	}

	public static Date getDate(Object value) {

		if (value != null) {

			try {

				String s = value.toString().toLowerCase();
				for (String[] dateFormat : DATE_FORMATS) {

					if (s.matches(dateFormat[0])) {

						DateFormat format = new SimpleDateFormat(dateFormat[1]);
						format.setTimeZone(DateUtils.GMT_TIME_ZONE);

						return format.parse(s);

					}

				}

			} catch (Exception e) {
				// TODO?
			}

		}

		return null;

	}

	public static Time getTime(Object value) {

		if (value != null) {

			try {
				return Time.parseTime(value.toString());
			} catch (Exception e) {
				// TODO?
			}

		}

		return null;

	}

	public static boolean getBoolean(Object value) {
		return getBoolean(value, false);
	}

	public static boolean getBoolean(Object value, boolean defaultValue) {

		if (value != null) {

			String s = value.toString().toLowerCase();

			if (defaultValue) {

				if (Resources.getString("No").toLowerCase().equals(s) || "no".equals(s) || "false".equals(s) || "0".equals(s)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					return false;
				}

			} else {

				if (Resources.getString("Yes").toLowerCase().equals(s) || "yes".equals(s) || "true".equals(s) || "1".equals(s)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					return true;
				}

			}

		}

		return defaultValue;

	}

}
