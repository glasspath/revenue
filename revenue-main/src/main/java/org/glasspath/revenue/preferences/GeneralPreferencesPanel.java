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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.glasspath.common.Compare;
import org.glasspath.common.format.FormatUtils;
import org.glasspath.common.locale.LocaleUtils;
import org.glasspath.common.locale.LocaleUtils.CurrencyCode;
import org.glasspath.common.locale.LocaleUtils.SystemOfUnits;
import org.glasspath.common.os.preferences.BoolPref;
import org.glasspath.common.os.preferences.Pref;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.preferences.CurrencyAndSymbolPreferenceComboBox;
import org.glasspath.common.swing.preferences.CurrencyPreferenceComboBox;
import org.glasspath.common.swing.preferences.LanguagePreferenceComboBox;
import org.glasspath.common.swing.preferences.UnitOfMeasurementPreferenceComboBox;
import org.glasspath.common.swing.theme.Theme;
import org.glasspath.revenue.resources.Resources;

public class GeneralPreferencesPanel extends JPanel {

	public static final Pref LANGUAGE = new Pref("language", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref CURRENCY = new Pref("currency", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref CURRENCY_SYMBOL = new Pref("currencySymbol", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref UNIT_OF_MEASUREMENT = new Pref("unitOfMeasurement", ""); //$NON-NLS-1$ //$NON-NLS-2$
	public static final Pref THEME = new Pref("theme", Theme.THEME_DEFAULT.getId()); //$NON-NLS-1$
	public static final String[] THEMES_LIST = new String[] {
			"Light",
			"Dark"
	};
	public static final BoolPref OPEN_LAST_FILE = new BoolPref("openLastFileAtStartup", true); //$NON-NLS-1$
	public static final BoolPref CREATE_BACKUP = new BoolPref("createBackupOnOpen", true); //$NON-NLS-1$

	private final Preferences preferences;
	private final LanguagePreferenceComboBox languageComboBox;
	private final CurrencyAndSymbolPreferenceComboBox currencyComboBox;
	private final UnitOfMeasurementPreferenceComboBox unitOfMeasurementComboBox;
	private final JComboBox<String> themeComboBox;
	private final JCheckBox openLastFileAtStartupCheckBox;
	private final JCheckBox backupOnOpenCheckBox;

	private String previousLanguage = LANGUAGE.defaultValue;
	private String previousCurrency = CURRENCY.defaultValue;
	private String previousCurrencySymbol = CURRENCY_SYMBOL.defaultValue;
	private String previousUnitOfMeasurement = UNIT_OF_MEASUREMENT.defaultValue;
	private String previousTheme = THEME.defaultValue;

	public GeneralPreferencesPanel(Preferences preferences) {

		this.preferences = preferences;

		PreferencesProvider preferencesProvider = new PreferencesProvider(preferences);

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 25, 5, 25, 5, 25, 5, 25, 5 };
		layout.columnWeights = new double[] { 0.1 };
		layout.columnWidths = new int[] { 250 };
		setLayout(layout);

		JPanel regionalSettingsPanel = new JPanel();
		add(regionalSettingsPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		GridBagLayout regionalSettingsPanelLayout = new GridBagLayout();
		regionalSettingsPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		regionalSettingsPanelLayout.rowHeights = new int[] { 7, 23, 5, 23, 5, 23, 7 };
		regionalSettingsPanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.0 };
		regionalSettingsPanelLayout.columnWidths = new int[] { 7, 150, 5, 250, 7 };
		regionalSettingsPanel.setLayout(regionalSettingsPanelLayout);
		regionalSettingsPanel.setBorder(BorderFactory.createTitledBorder("Regional settings"));

		languageComboBox = new LanguagePreferenceComboBox(preferencesProvider, LANGUAGE.key, LANGUAGE.defaultValue, LocaleUtils.BASIC_LANGUAGE_TAGS, false) {

			@Override
			protected boolean isLanguageSupported(String languageTag) {
				return languageTag != null && ( //
				languageTag.startsWith("en") || //$NON-NLS-1$
						languageTag.startsWith("nl")); //$NON-NLS-1$
			}
		};
		regionalSettingsPanel.add(new JLabel(Resources.getString("Language")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		regionalSettingsPanel.add(languageComboBox, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		languageComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedLocaleChanged();
			}
		});

		currencyComboBox = new CurrencyAndSymbolPreferenceComboBox(preferencesProvider, CURRENCY.key, CURRENCY.defaultValue, CURRENCY_SYMBOL.key, CURRENCY_SYMBOL.defaultValue, false);
		currencyComboBox.setBorder(BorderFactory.createCompoundBorder(currencyComboBox.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 3)));
		regionalSettingsPanel.add(new JLabel("Currency & Symbol"), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		regionalSettingsPanel.add(currencyComboBox, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		unitOfMeasurementComboBox = new UnitOfMeasurementPreferenceComboBox(preferencesProvider, UNIT_OF_MEASUREMENT.key, UNIT_OF_MEASUREMENT.defaultValue, false);
		unitOfMeasurementComboBox.setBorder(BorderFactory.createCompoundBorder(unitOfMeasurementComboBox.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 3)));
		regionalSettingsPanel.add(new JLabel("Unit of measurement"), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		regionalSettingsPanel.add(unitOfMeasurementComboBox, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		JPanel appearancePanel = new JPanel();
		add(appearancePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		GridBagLayout appearancePanelLayout = new GridBagLayout();
		appearancePanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		appearancePanelLayout.rowHeights = new int[] { 7, 23, 7 };
		appearancePanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.0 };
		appearancePanelLayout.columnWidths = new int[] { 7, 150, 5, 250, 7 };
		appearancePanel.setLayout(appearancePanelLayout);
		appearancePanel.setBorder(BorderFactory.createTitledBorder("Appearance"));

		themeComboBox = new JComboBox<>(THEMES_LIST);
		appearancePanel.add(new JLabel("Theme"), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		appearancePanel.add(themeComboBox, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		if (Theme.THEME_DARK.getId().equals(THEME.get(preferences))) {
			themeComboBox.setSelectedIndex(1);
		}

		JPanel startupPanel = new JPanel();
		add(startupPanel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		GridBagLayout startupPanelLayout = new GridBagLayout();
		startupPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		startupPanelLayout.rowHeights = new int[] { 7, 23, 5, 23, 7 };
		startupPanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.0 };
		startupPanelLayout.columnWidths = new int[] { 7, 150, 5, 250, 7 };
		startupPanel.setLayout(startupPanelLayout);
		startupPanel.setBorder(BorderFactory.createTitledBorder("Startup"));

		openLastFileAtStartupCheckBox = new JCheckBox(Resources.getString("OpenLastFileAtStartup")); //$NON-NLS-1$
		openLastFileAtStartupCheckBox.setSelected(OPEN_LAST_FILE.get(preferences));
		startupPanel.add(new JLabel("Load project"), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		startupPanel.add(openLastFileAtStartupCheckBox, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		backupOnOpenCheckBox = new JCheckBox(Resources.getString("CreateBackupWhenFileOpened")); //$NON-NLS-1$
		backupOnOpenCheckBox.setSelected(CREATE_BACKUP.get(preferences));
		startupPanel.add(new JLabel("Create backup"), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		startupPanel.add(backupOnOpenCheckBox, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		previousLanguage = LANGUAGE.get(preferences);
		previousCurrency = CURRENCY.get(preferences);
		previousCurrencySymbol = CURRENCY_SYMBOL.get(preferences);
		previousUnitOfMeasurement = UNIT_OF_MEASUREMENT.get(preferences);
		previousTheme = THEME.get(preferences);

		selectedLocaleChanged();

	}

	private void selectedLocaleChanged() {

		Locale locale = null;

		String languageTag = languageComboBox.getSelectedLanguageTag();
		if (languageTag != null) {
			locale = LocaleUtils.getLocaleForTag(languageTag);
		}

		if (locale == null) {
			locale = Locale.getDefault();
		}

		currencyComboBox.setAutomaticLocale(locale, true);
		unitOfMeasurementComboBox.setAutomaticLocale(locale, true);

	}

	protected boolean submit() {

		languageComboBox.commit();
		currencyComboBox.commit();
		unitOfMeasurementComboBox.commit();

		if (themeComboBox.getSelectedIndex() == 1) {
			preferences.put(THEME.key, Theme.THEME_DARK.getId());
		} else {
			preferences.remove(THEME.key);
		}

		OPEN_LAST_FILE.put(preferences, openLastFileAtStartupCheckBox.isSelected());
		CREATE_BACKUP.put(preferences, backupOnOpenCheckBox.isSelected());

		applyRegionalSettings(preferences);

		String newLanguage = LANGUAGE.get(preferences);
		String newCurrency = CURRENCY.get(preferences);
		String newCurrencySymbol = CURRENCY_SYMBOL.get(preferences);
		String newUnitOfMeasurement = UNIT_OF_MEASUREMENT.get(preferences);
		String newTheme = THEME.get(preferences);

		return !Compare.string(newLanguage, previousLanguage)
				|| !Compare.string(newCurrency, previousCurrency)
				|| !Compare.string(newCurrencySymbol, previousCurrencySymbol)
				|| !Compare.string(newUnitOfMeasurement, previousUnitOfMeasurement)
				|| !Compare.string(newTheme, previousTheme);

	}

	public static void applyRegionalSettings(Preferences preferences) {

		Locale locale = LocaleUtils.getLocaleForTag(LANGUAGE.get(preferences));
		if (locale == null) {
			locale = LocaleUtils.getDefaultLocale();
		}

		String currencySymbol = CURRENCY_SYMBOL.get(preferences);
		if (currencySymbol != null && currencySymbol.length() > 0) {
			FormatUtils.setDefaultCurrencySymbol(currencySymbol);
		} else {

			CurrencyCode currencyCode = null;

			String currency = CURRENCY.get(preferences);
			if (currency != null && currency.length() > 0) {
				currencyCode = LocaleUtils.getCurrencyCode(currency);
			}

			if (currencyCode == null) {
				currencyCode = LocaleUtils.getCurrencyCodeForLocale(locale);
			}

			if (currencyCode != null) {
				FormatUtils.setDefaultCurrencySymbol(currencyCode.symbol);
			}

		}

		SystemOfUnits systemOfUnits = null;

		String systemOfUnitsCode = UNIT_OF_MEASUREMENT.get(preferences);
		if (systemOfUnitsCode != null && systemOfUnitsCode.length() > 0) {
			systemOfUnits = LocaleUtils.getSystemOfUnits(systemOfUnitsCode);
		}

		if (systemOfUnits == null) {
			systemOfUnits = LocaleUtils.getSystemOfUnitsForLocale(locale);
		}

		FormatUtils.setDefaultSystemOfUnits(systemOfUnits);

	}

}
