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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.glasspath.aerialist.IFieldContext;

@SuppressWarnings("nls")
public class VelocityTemplateFieldContext extends VelocityContext implements IFieldContext {

	private String defaultValue = "";

	public VelocityTemplateFieldContext() {

	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String getString(String key) {

		if (key != null) {

			Object value;
			if (key.indexOf(".") > 0) {
				value = get(key.split("[.]"));
			} else {
				value = get(key);
			}

			if (value instanceof String) {
				return (String) value;
			}

		}

		return defaultValue;

	}

	@Override
	public List<String> getList(String key) {

		if (key != null) {

			if (key.indexOf(".") > 0) {
				return getList(key.split("[.]"));
			} else {
				Object value = get(key);
				if (value instanceof List<?>) {
					return toStringList((List<?>) value);
				}
			}

		}

		return null;

	}

	@Override
	public Object getObject(String key) {
		return get(key);
	}

	private Object get(String[] keys) {

		if (keys != null && keys.length > 0) {

			int i = 0;
			Map<?, ?> map = null;
			String key = keys[i];
			Object value = get(key);
			// System.out.println(i + ", " + key + ", " + value);

			if (value instanceof Map<?, ?>) {

				map = (Map<?, ?>) value;

				while (i < keys.length - 1) {

					i++;
					key = keys[i];
					value = map.get(key);
					// System.out.println(i + ", " + key + ", " + value);

					if (value instanceof Map<?, ?>) {
						map = (Map<?, ?>) value;
					} else {
						break;
					}

				}

			}

			if (i == keys.length - 1) {
				return value;
			}

		}

		return null;

	}

	private List<String> getList(String[] keys) {

		if (keys != null && keys.length > 0) {

			List<?> list = null;

			int i = 0;
			Map<?, ?> map = null;
			String key = keys[i];
			Object value = get(key);
			// System.out.println(i + ", " + key + ", " + value);

			if (value instanceof Map<?, ?>) {

				map = (Map<?, ?>) value;

				while (i < keys.length - 1) {

					i++;
					key = keys[i];
					value = map.get(key);
					// System.out.println(i + ", " + key + ", " + value);

					if (value instanceof Map<?, ?>) {
						map = (Map<?, ?>) value;
					} else {

						if (value instanceof List<?>) {
							list = (List<?>) value;
						}

						break;

					}

				}

			} else if (value instanceof List<?>) {
				list = (List<?>) value;
			}

			if (list != null) {

				if (i == keys.length - 1) { // List of values
					return toStringList(list);
				} else if (i == keys.length - 2) { // List of Map<key, value>

					List<String> values = new ArrayList<>();

					for (Object o : list) {
						if (o instanceof Map<?, ?>) {
							value = ((Map<?, ?>) o).get(keys[keys.length - 1]);
							values.add(value == null ? defaultValue : value.toString());
						}
					}

					return values;

				} else {

					// TODO?
					return null;

				}

			}

		}

		return null;

	}

	private List<String> toStringList(List<?> list) {

		List<String> values = new ArrayList<>();

		for (Object o : list) {
			values.add(o == null ? defaultValue : o.toString());
		}

		return values;

	}

}
