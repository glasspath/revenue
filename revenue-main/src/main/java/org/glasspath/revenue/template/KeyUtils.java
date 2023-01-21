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
package org.glasspath.revenue.template;

import java.util.ResourceBundle;

@SuppressWarnings("nls")
public class KeyUtils {

	public static final ResourceBundle BUNDLE = ResourceBundle.getBundle("org.glasspath.revenue.template.keys");

	public static final Key AMOUNT = new Key("amount");
	public static final Key CLIENT = new Key("client");
	public static final Key COMMENTS = new Key("comments");
	public static final Key DATE = new Key("date");
	public static final Key DATE_FROM = new Key("date_from");
	public static final Key DATE_TO = new Key("date_to");
	public static final Key HOUR = new Key("hour");
	public static final Key NUMBER = new Key("number");
	public static final Key PROJECT = new Key("project");
	public static final Key RATE = new Key("rate");
	public static final Key TIME_FROM = new Key("time_from");
	public static final Key TIME_TO = new Key("time_to");
	public static final Key INVOICE = new Key("invoice");

	protected KeyUtils() {

	}

	public static String key(Key key) {

		if (key.value == null) {
			try {
				key.value = BUNDLE.getString(key.key);
			} catch (Exception e) {
				key.value = key.key;
			}
		}

		return key.value;

	}

	public static String clientKey(Key key) {
		return key(CLIENT, key);
	}

	public static String hourKey(Key key) {
		return key(HOUR, key);
	}

	public static String invoiceKey(Key key) {
		return key(INVOICE, key);
	}

	public static String tKey(String key) {
		return "t:" + key;
	}

	public static String key(Key key1, Key key2) {
		return key(key1, key2, ".");
	}

	public static String key(Key key1, Key key2, String separator) {
		return key(key1) + separator + key(key2);
	}

	public static class Key {

		private final String key;
		private String value = null;

		protected Key(String key) {
			this.key = key;
		}

	}

}
