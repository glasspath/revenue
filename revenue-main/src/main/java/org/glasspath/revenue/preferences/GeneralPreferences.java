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
package org.glasspath.revenue.preferences;

import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JPanel;

import org.glasspath.common.format.FormatUtils;
import org.glasspath.common.locale.LocaleUtils;
import org.glasspath.common.locale.LocaleUtils.CurrencyCode;
import org.glasspath.common.locale.LocaleUtils.SystemOfUnits;
import org.glasspath.common.os.preferences.Pref;
import org.glasspath.common.swing.theme.Theme;

public class GeneralPreferences extends JPanel {

	public static final Pref LANGUAGE = new Pref("language", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref CURRENCY = new Pref("currency", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref CURRENCY_SYMBOL = new Pref("currencySymbol", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref UNIT_OF_MEASUREMENT = new Pref("unitOfMeasurement", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref THEME = new Pref("theme", Theme.THEME_DEFAULT.getId()); //$NON-NLS-1$

	private GeneralPreferences() {

	}
	
	public static Locale getLocale(Preferences preferences) {

		Locale locale = LocaleUtils.getLocaleForTag(LANGUAGE.get(preferences));
		if (locale == null) {
			locale = LocaleUtils.getDefaultLocale();
		}

		return locale;

	}

	public static CurrencyCode getCurrencyCode(Preferences preferences, Locale locale) {

		CurrencyCode currencyCode = null;

		String currency = CURRENCY.get(preferences);
		if (currency != null && currency.length() > 0) {
			currencyCode = LocaleUtils.getCurrencyCode(currency);
		}

		if (currencyCode == null) {
			currencyCode = LocaleUtils.getCurrencyCodeForLocale(locale);
		}

		return currencyCode;

	}

	public static SystemOfUnits getSystemOfUnits(Preferences preferences, Locale locale) {

		SystemOfUnits systemOfUnits = null;

		String systemOfUnitsCode = UNIT_OF_MEASUREMENT.get(preferences);
		if (systemOfUnitsCode != null && systemOfUnitsCode.length() > 0) {
			systemOfUnits = LocaleUtils.getSystemOfUnits(systemOfUnitsCode);
		}

		if (systemOfUnits == null) {
			systemOfUnits = LocaleUtils.getSystemOfUnitsForLocale(locale);
		}

		return systemOfUnits;

	}

	public static void applyRegionalSettings(Preferences preferences) {

		Locale locale = getLocale(preferences);

		String currencySymbol = CURRENCY_SYMBOL.get(preferences);
		if (currencySymbol != null && currencySymbol.length() > 0) {
			FormatUtils.setDefaultCurrencySymbol(currencySymbol);
		} else {

			CurrencyCode currencyCode = getCurrencyCode(preferences, locale);
			if (currencyCode != null) {
				FormatUtils.setDefaultCurrencySymbol(currencyCode.symbol);
			}

		}

		FormatUtils.setDefaultSystemOfUnits(getSystemOfUnits(preferences, locale));

	}

}
