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
package org.glasspath.revenue.template.manager;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.glasspath.common.Common;
import org.glasspath.common.date.DateUtils;
import org.glasspath.common.format.FormatUtils;
import org.glasspath.common.locale.LocaleUtils;
import org.glasspath.common.os.preferences.IntPref;
import org.glasspath.common.os.preferences.Pref;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.preferences.LanguagePreferenceComboBox;
import org.glasspath.common.swing.preferences.PreferencesUtils;
import org.glasspath.revenue.resources.Resources;

public class FieldFormatPanel extends JPanel {

	public static final String[] DATE_FORMATS = new String[] {
			"E d-MMM-yyyy", //$NON-NLS-1$
			"E dd-MMM-yyyy", //$NON-NLS-1$
			"EEEE d-MMMM-yyyy", //$NON-NLS-1$
			"EEEE dd-MMMM-yyyy", //$NON-NLS-1$
			"d-MMM-yyyy", //$NON-NLS-1$
			"dd-MMM-yyyy", //$NON-NLS-1$
			"d-MMM", //$NON-NLS-1$
			"dd-MMM", //$NON-NLS-1$
			"d-MM-yyyy", //$NON-NLS-1$
			"dd-MM-yyyy", //$NON-NLS-1$
			"d-MM-yy", //$NON-NLS-1$
			"dd-MM-yy", //$NON-NLS-1$
			"E d/MMM/yyyy", //$NON-NLS-1$
			"E dd/MMM/yyyy", //$NON-NLS-1$
			"EEEE d/MMMM/yyyy", //$NON-NLS-1$
			"EEEE dd/MMMM/yyyy", //$NON-NLS-1$
			"d/MMM/yyyy", //$NON-NLS-1$
			"dd/MMM/yyyy", //$NON-NLS-1$
			"d/MMM", //$NON-NLS-1$
			"dd/MMM", //$NON-NLS-1$
			"d/MM/yyyy", //$NON-NLS-1$
			"dd/MM/yyyy", //$NON-NLS-1$
			"d/MM/yy", //$NON-NLS-1$
			"dd/MM/yy", //$NON-NLS-1$
			"E d MMM yyyy", // Default //$NON-NLS-1$
			"E dd MMM yyyy", //$NON-NLS-1$
			"EEEE d MMMM yyyy", //$NON-NLS-1$
			"EEEE dd MMMM yyyy", //$NON-NLS-1$
			"d MMM yyyy", //$NON-NLS-1$
			"dd MMM yyyy", //$NON-NLS-1$
			"d MMM", //$NON-NLS-1$
			"dd MMM", //$NON-NLS-1$
			"d MM yyyy", //$NON-NLS-1$
			"dd MM yyyy", //$NON-NLS-1$
			"d MM yy", //$NON-NLS-1$
			"dd MM yy" //$NON-NLS-1$
	};
	public static final int DEFAULT_DATE_FORMAT = 9;

	public static final String[] TIME_FORMATS = new String[] {
			"HH:mm", //$NON-NLS-1$
			"HH.mm", //$NON-NLS-1$
			"H:mm", //$NON-NLS-1$
			"H.mm", //$NON-NLS-1$
			"hh:mm aa", //$NON-NLS-1$
			"hh.mm aa", //$NON-NLS-1$
			"h:mm aa", //$NON-NLS-1$
			"h.mm aa" //$NON-NLS-1$
	};
	public static final int DEFAULT_TIME_FORMAT = 0;

	public static final Date SAMPLE_DATE_1;
	public static final Date SAMPLE_DATE_2;
	static {
		Calendar calendar = Calendar.getInstance(DateUtils.DEFAULT_TIME_ZONE);
		// calendar.set(2012, 3, 2);
		calendar.set(DateUtils.getYear(), 3, 2, 8, 30);
		SAMPLE_DATE_1 = calendar.getTime();
		calendar.set(DateUtils.getYear(), 3, 2, 17, 5);
		SAMPLE_DATE_2 = calendar.getTime();
	}

	public static final int DECIMAL_FORMAT_SYMBOLS_AUTOMATIC = 0;
	public static final int DECIMAL_FORMAT_SYMBOLS_COMMA_DOT = 1;
	public static final int DECIMAL_FORMAT_SYMBOLS_DOT = 2;
	public static final int DECIMAL_FORMAT_SYMBOLS_DOT_COMMA = 3;
	public static final int DECIMAL_FORMAT_SYMBOLS_COMMA = 4;
	public static final String[] DECIMAL_FORMAT_SYMBOLS_SAMPLES = new String[] {
			"Automatic",
			"1,234,567.89", //$NON-NLS-1$
			"1234567.89", //$NON-NLS-1$
			"1.234.567,89", //$NON-NLS-1$
			"1234567,89" //$NON-NLS-1$
	};
	public static final int DEFAULT_DECIMAL_FORMAT_SYMBOLS = 0;

	public static final String[] DECIMAL_FORMATS = new String[] {
			"0", //$NON-NLS-1$
			"0.0", //$NON-NLS-1$
			"0.00", //$NON-NLS-1$
			"0.000", //$NON-NLS-1$
			"#.#", //$NON-NLS-1$
			"#.##", //$NON-NLS-1$
			"#.###" //$NON-NLS-1$
	};
	public static final int DEFAULT_DECIMAL_FORMAT = 2;
	public static final int DEFAULT_INTEGER_DECIMAL_FORMAT = 5;
	public static final float DECIMAL_FORMAT_SAMPLE_1 = 100.0F;
	public static final float DECIMAL_FORMAT_SAMPLE_2 = 12.3456F;

	public static final Pref LANGUAGE = new Pref("language", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final IntPref DATE_FORMAT = new IntPref("dateFormat", DEFAULT_DATE_FORMAT); //$NON-NLS-1$
	public static final IntPref TIME_FORMAT = new IntPref("timeFormat", DEFAULT_TIME_FORMAT); //$NON-NLS-1$
	public static final IntPref DECIMAL_FORMAT_SYMBOLS = new IntPref("decimalFormatSymbols", DEFAULT_DECIMAL_FORMAT_SYMBOLS); //$NON-NLS-1$

	public static final Pref HOUR_TOTAL_PREPEND_TEXT = new Pref("hourTotalPrependText", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final IntPref HOUR_TOTAL_FORMAT = new IntPref("hourTotalFormat", DEFAULT_DECIMAL_FORMAT); //$NON-NLS-1$
	public static final Pref HOUR_TOTAL_APPEND_TEXT = new Pref("hourTotalAppendText", Resources.getString("spaceHour")); //$NON-NLS-1$ //$NON-NLS-2$

	public static final Pref HOUR_RATE_PREPEND_TEXT = new Pref("hourRatePrependText", FormatUtils.getDefaultCurrencySymbol()); //$NON-NLS-1$
	public static final IntPref HOUR_RATE_FORMAT = new IntPref("hourRateFormat", DEFAULT_DECIMAL_FORMAT); //$NON-NLS-1$
	public static final Pref HOUR_RATE_APPEND_TEXT = new Pref("hourRateAppendText", Resources.getString("slashHour")); //$NON-NLS-1$ //$NON-NLS-2$

	public static final Pref MILEAGE_TOTAL_PREPEND_TEXT = new Pref("mileageTotalPrependText", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final IntPref MILEAGE_TOTAL_FORMAT = new IntPref("mileageTotalFormat", DEFAULT_DECIMAL_FORMAT); //$NON-NLS-1$
	public static final Pref MILEAGE_TOTAL_APPEND_TEXT = new Pref("mileageTotalAppendText", " " + FormatUtils.getDefaultMileageUnit()); //$NON-NLS-1$ //$NON-NLS-2$

	public static final Pref MILEAGE_RATE_PREPEND_TEXT = new Pref("mileageRatePrependText", FormatUtils.getDefaultCurrencySymbol()); //$NON-NLS-1$
	public static final IntPref MILEAGE_RATE_FORMAT = new IntPref("mileageRateFormat", DEFAULT_DECIMAL_FORMAT); //$NON-NLS-1$
	public static final Pref MILEAGE_RATE_APPEND_TEXT = new Pref("mileageRateAppendText", "/" + FormatUtils.getDefaultMileageUnit()); //$NON-NLS-1$ //$NON-NLS-2$

	public static final Pref VAT_RATE_PREPEND_TEXT = new Pref("vatRatePrependText", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final IntPref VAT_RATE_FORMAT = new IntPref("vatRateFormat", DEFAULT_DECIMAL_FORMAT); //$NON-NLS-1$
	public static final Pref VAT_RATE_APPEND_TEXT = new Pref("vatRateAppendText", "%"); //$NON-NLS-1$ //$NON-NLS-2$

	public static final Pref AMOUNT_PREPEND_TEXT = new Pref("amountPrependText", FormatUtils.getDefaultCurrencySymbol()); //$NON-NLS-1$
	public static final IntPref AMOUNT_FORMAT = new IntPref("amountFormat", DEFAULT_DECIMAL_FORMAT); //$NON-NLS-1$
	public static final Pref AMOUNT_APPEND_TEXT = new Pref("amountAppendText", ""); //$NON-NLS-1$ //$NON-NLS-2$

	public static final Pref QUANTITY_PREPEND_TEXT = new Pref("quantityPrependText", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final IntPref QUANTITY_FORMAT = new IntPref("quantityFormat", DEFAULT_INTEGER_DECIMAL_FORMAT); //$NON-NLS-1$
	public static final Pref QUANTITY_APPEND_TEXT = new Pref("quantityAppendText", ""); //$NON-NLS-1$ //$NON-NLS-2$

	public FieldFormatPanel(PreferencesProvider provider) {

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 20, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 5 };
		layout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0 };
		layout.columnWidths = new int[] { 7, 140, 5, 75, 3, 50, 3, 75, 7 };
		setLayout(layout);

		LanguagePreferenceComboBox languageComboBox = new LanguagePreferenceComboBox(provider, LANGUAGE.key, LANGUAGE.defaultValue);
		add(new JLabel(Resources.getString("Language")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(languageComboBox, new GridBagConstraints(3, 1, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); // $NON-NLS-1$
		languageComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});

		add(new JLabel("Date format"), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, DATE_FORMAT, DATE_FORMATS, new DateFormatListCellRenderer(provider)), new GridBagConstraints(3, 3, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Time format"), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, TIME_FORMAT, TIME_FORMATS, new TimeFormatListCellRenderer(provider)), new GridBagConstraints(3, 5, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		JComboBox<String> decimalSymbolsComboBox = PreferencesUtils.createComboBox(provider, DECIMAL_FORMAT_SYMBOLS, DECIMAL_FORMAT_SYMBOLS_SAMPLES);
		add(new JLabel("Decimal symbols"), new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(decimalSymbolsComboBox, new GridBagConstraints(3, 7, 5, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); // $NON-NLS-1$
		decimalSymbolsComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});

		add(new JLabel(Resources.getString("HoursTotal")), new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, HOUR_TOTAL_PREPEND_TEXT, JTextField.RIGHT), new GridBagConstraints(3, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, HOUR_TOTAL_FORMAT, DECIMAL_FORMATS, new DecimalFormatListCellRenderer(provider)), new GridBagConstraints(5, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, HOUR_TOTAL_APPEND_TEXT), new GridBagConstraints(7, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("HourRate")), new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, HOUR_RATE_PREPEND_TEXT, JTextField.RIGHT), new GridBagConstraints(3, 11, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, HOUR_RATE_FORMAT, DECIMAL_FORMATS, new DecimalFormatListCellRenderer(provider)), new GridBagConstraints(5, 11, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, HOUR_RATE_APPEND_TEXT), new GridBagConstraints(7, 11, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		// TODO: Change resource key
		add(new JLabel(Resources.getString("KilometersTotal")), new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, MILEAGE_TOTAL_PREPEND_TEXT, JTextField.RIGHT), new GridBagConstraints(3, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, MILEAGE_TOTAL_FORMAT, DECIMAL_FORMATS, new DecimalFormatListCellRenderer(provider)), new GridBagConstraints(5, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, MILEAGE_TOTAL_APPEND_TEXT), new GridBagConstraints(7, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("MileageRate")), new GridBagConstraints(1, 15, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, MILEAGE_RATE_PREPEND_TEXT, JTextField.RIGHT), new GridBagConstraints(3, 15, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, MILEAGE_RATE_FORMAT, DECIMAL_FORMATS, new DecimalFormatListCellRenderer(provider)), new GridBagConstraints(5, 15, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, MILEAGE_RATE_APPEND_TEXT), new GridBagConstraints(7, 15, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("VatRate")), new GridBagConstraints(1, 17, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, VAT_RATE_PREPEND_TEXT, JTextField.RIGHT), new GridBagConstraints(3, 17, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, VAT_RATE_FORMAT, DECIMAL_FORMATS, new DecimalFormatListCellRenderer(provider)), new GridBagConstraints(5, 17, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, VAT_RATE_APPEND_TEXT), new GridBagConstraints(7, 17, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("Amount")), new GridBagConstraints(1, 19, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, AMOUNT_PREPEND_TEXT, JTextField.RIGHT), new GridBagConstraints(3, 19, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, AMOUNT_FORMAT, DECIMAL_FORMATS, new DecimalFormatListCellRenderer(provider)), new GridBagConstraints(5, 19, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, AMOUNT_APPEND_TEXT), new GridBagConstraints(7, 19, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("Quantity")), new GridBagConstraints(1, 21, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, QUANTITY_PREPEND_TEXT, JTextField.RIGHT), new GridBagConstraints(3, 21, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, QUANTITY_FORMAT, DECIMAL_FORMATS, new DecimalFormatListCellRenderer(provider)), new GridBagConstraints(5, 21, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, QUANTITY_APPEND_TEXT), new GridBagConstraints(7, 21, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

	}

	public static Locale getLanguage(Preferences preferences) {
		return LocaleUtils.getLocaleForTag(LANGUAGE.get(preferences));
	}

	public static String getDateFormat(Preferences preferences) {
		int index = DATE_FORMAT.get(preferences);
		if (index >= 0 && index < DATE_FORMATS.length) {
			return DATE_FORMATS[index];
		} else {
			return DATE_FORMATS[DEFAULT_DATE_FORMAT];
		}
	}

	public static String getTimeFormat(Preferences preferences) {
		int index = TIME_FORMAT.get(preferences);
		if (index >= 0 && index < TIME_FORMATS.length) {
			return TIME_FORMATS[index];
		} else {
			return TIME_FORMATS[DEFAULT_TIME_FORMAT];
		}
	}

	public static DecimalFormat getDecimalFormat(IntPref pref, Preferences preferences) {

		int index = pref.get(preferences);
		if (index >= 0 && index < DECIMAL_FORMATS.length) {
			return getDecimalFormat(DECIMAL_FORMATS[index], preferences);
		} else {
			return getDecimalFormat(DECIMAL_FORMATS[DEFAULT_DECIMAL_FORMAT], preferences);
		}

	}

	public static DecimalFormat getDecimalFormat(String format, Preferences preferences) {

		try {

			DecimalFormat decimalFormat = new DecimalFormat(format);

			int index = DECIMAL_FORMAT_SYMBOLS.get(preferences);

			DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();

			Locale locale = getLanguage(preferences);
			if (locale != null) {
				decimalSymbols = new DecimalFormatSymbols(locale);
				decimalFormat.setDecimalFormatSymbols(decimalSymbols);
			}

			switch (index) {

			case DECIMAL_FORMAT_SYMBOLS_AUTOMATIC:
				decimalSymbols = null;
				break;

			case DECIMAL_FORMAT_SYMBOLS_COMMA_DOT:
				decimalSymbols.setGroupingSeparator(',');
				decimalSymbols.setDecimalSeparator('.');
				decimalFormat.setGroupingSize(3);
				decimalFormat.setGroupingUsed(true);
				break;

			case DECIMAL_FORMAT_SYMBOLS_DOT:
				decimalSymbols.setDecimalSeparator('.');
				decimalFormat.setGroupingUsed(false);
				break;

			case DECIMAL_FORMAT_SYMBOLS_DOT_COMMA:
				decimalSymbols.setGroupingSeparator('.');
				decimalSymbols.setDecimalSeparator(',');
				decimalFormat.setGroupingSize(3);
				decimalFormat.setGroupingUsed(true);
				break;

			case DECIMAL_FORMAT_SYMBOLS_COMMA:
				decimalSymbols.setDecimalSeparator(',');
				decimalFormat.setGroupingUsed(false);
				break;

			default:
				break;
			}

			if (decimalSymbols != null) {
				decimalFormat.setDecimalFormatSymbols(decimalSymbols);
			}

			return decimalFormat;

		} catch (Exception e) {
			Common.LOGGER.error("Exception while creating DecimalFormat instance for format: " + format, e);
		}

		// TODO? Return null?
		return new DecimalFormat();

	}

	public static class DateFormatListCellRenderer extends DefaultListCellRenderer {

		private final PreferencesProvider provider;

		public DateFormatListCellRenderer(PreferencesProvider provider) {
			this.provider = provider;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value instanceof String) {
				setText(FormatUtils.createSimpleDateFormat((String) value, getLanguage(provider.getPreferences())).format(SAMPLE_DATE_1));
			}

			return this;

		}

	}

	public static class TimeFormatListCellRenderer extends DefaultListCellRenderer {

		private final PreferencesProvider provider;

		public TimeFormatListCellRenderer(PreferencesProvider provider) {
			this.provider = provider;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value instanceof String) {
				Locale locale = getLanguage(provider.getPreferences());
				setText(FormatUtils.createSimpleDateFormat((String) value, locale).format(SAMPLE_DATE_1) + " | " + FormatUtils.createSimpleDateFormat((String) value, locale).format(SAMPLE_DATE_2)); //$NON-NLS-1$
			}

			return this;

		}

	}

	public static class DecimalFormatListCellRenderer extends DefaultListCellRenderer {

		private final PreferencesProvider provider;

		public DecimalFormatListCellRenderer(PreferencesProvider provider) {
			this.provider = provider;
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			if (value instanceof String) {
				DecimalFormat decimalFormat = getDecimalFormat((String) value, provider.getPreferences());
				setText("100" + decimalFormat.getDecimalFormatSymbols().getDecimalSeparator() + "0 = " + decimalFormat.format(DECIMAL_FORMAT_SAMPLE_1) + " \t| 12" + decimalFormat.getDecimalFormatSymbols().getDecimalSeparator() + "3456 = " + decimalFormat.format(DECIMAL_FORMAT_SAMPLE_2)); //$NON-NLS-1$
			}

			return this;

		}

	}

}
