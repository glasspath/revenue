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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.glasspath.common.Compare;
import org.glasspath.common.locale.LocaleUtils;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.preferences.CurrencyAndSymbolPreferenceComboBox;
import org.glasspath.common.swing.preferences.LanguagePreferenceComboBox;
import org.glasspath.common.swing.preferences.UnitOfMeasurementPreferenceComboBox;
import org.glasspath.common.swing.resources.CommonResources;
import org.glasspath.common.swing.theme.Theme;

public class GeneralPreferencesPanel extends JPanel {

	public static final String[] THEMES_LIST = new String[] {
			CommonResources.getString("Light"), //$NON-NLS-1$
			CommonResources.getString("Dark") //$NON-NLS-1$
	};

	private final Preferences preferences;
	private final LanguagePreferenceComboBox languageComboBox;
	private final CurrencyAndSymbolPreferenceComboBox currencyComboBox;
	private final UnitOfMeasurementPreferenceComboBox unitOfMeasurementComboBox;
	private final JComboBox<String> themeComboBox;

	private String previousLanguage = GeneralPreferences.LANGUAGE.defaultValue;
	private String previousCurrency = GeneralPreferences.CURRENCY.defaultValue;
	private String previousCurrencySymbol = GeneralPreferences.CURRENCY_SYMBOL.defaultValue;
	private String previousUnitOfMeasurement = GeneralPreferences.UNIT_OF_MEASUREMENT.defaultValue;
	private String previousTheme = GeneralPreferences.THEME.defaultValue;

	public GeneralPreferencesPanel(Preferences preferences) {

		this.preferences = preferences;

		PreferencesProvider preferencesProvider = new PreferencesProvider(preferences);

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 25, 5, 25, 5, 25, 5 };
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
		regionalSettingsPanel.setBorder(BorderFactory.createTitledBorder(CommonResources.getString("RegionalSettings"))); //$NON-NLS-1$

		languageComboBox = new LanguagePreferenceComboBox(preferencesProvider, GeneralPreferences.LANGUAGE.key, GeneralPreferences.LANGUAGE.defaultValue, LocaleUtils.BASIC_LANGUAGE_TAGS, false) {

			@Override
			protected boolean isLanguageSupported(String languageTag) {
				return languageTag != null && ( //
				languageTag.startsWith("en") || //$NON-NLS-1$
						languageTag.startsWith("nl")); //$NON-NLS-1$
			}
		};
		regionalSettingsPanel.add(new JLabel(CommonResources.getString("Language")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		regionalSettingsPanel.add(languageComboBox, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		languageComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedLocaleChanged();
			}
		});

		currencyComboBox = new CurrencyAndSymbolPreferenceComboBox(preferencesProvider, GeneralPreferences.CURRENCY.key, GeneralPreferences.CURRENCY.defaultValue, GeneralPreferences.CURRENCY_SYMBOL.key, GeneralPreferences.CURRENCY_SYMBOL.defaultValue, false);
		currencyComboBox.setBorder(BorderFactory.createCompoundBorder(currencyComboBox.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 3)));
		regionalSettingsPanel.add(new JLabel(CommonResources.getString("CurrencySymbol")), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		regionalSettingsPanel.add(currencyComboBox, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		unitOfMeasurementComboBox = new UnitOfMeasurementPreferenceComboBox(preferencesProvider, GeneralPreferences.UNIT_OF_MEASUREMENT.key, GeneralPreferences.UNIT_OF_MEASUREMENT.defaultValue, false);
		unitOfMeasurementComboBox.setBorder(BorderFactory.createCompoundBorder(unitOfMeasurementComboBox.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 3)));
		regionalSettingsPanel.add(new JLabel(CommonResources.getString("UnitOfMeasurement")), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		regionalSettingsPanel.add(unitOfMeasurementComboBox, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		JPanel appearancePanel = new JPanel();
		add(appearancePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		GridBagLayout appearancePanelLayout = new GridBagLayout();
		appearancePanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		appearancePanelLayout.rowHeights = new int[] { 7, 23, 7 };
		appearancePanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.0 };
		appearancePanelLayout.columnWidths = new int[] { 7, 150, 5, 250, 7 };
		appearancePanel.setLayout(appearancePanelLayout);
		appearancePanel.setBorder(BorderFactory.createTitledBorder(CommonResources.getString("Appearance"))); //$NON-NLS-1$

		themeComboBox = new JComboBox<>(THEMES_LIST);
		appearancePanel.add(new JLabel(CommonResources.getString("Theme")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		appearancePanel.add(themeComboBox, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
		if (Theme.THEME_DARK.getId().equals(GeneralPreferences.THEME.get(preferences))) {
			themeComboBox.setSelectedIndex(1);
		}

		previousLanguage = GeneralPreferences.LANGUAGE.get(preferences);
		previousCurrency = GeneralPreferences.CURRENCY.get(preferences);
		previousCurrencySymbol = GeneralPreferences.CURRENCY_SYMBOL.get(preferences);
		previousUnitOfMeasurement = GeneralPreferences.UNIT_OF_MEASUREMENT.get(preferences);
		previousTheme = GeneralPreferences.THEME.get(preferences);

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
			preferences.put(GeneralPreferences.THEME.key, Theme.THEME_DARK.getId());
		} else {
			preferences.remove(GeneralPreferences.THEME.key);
		}

		GeneralPreferences.applyRegionalSettings(preferences);

		String newLanguage = GeneralPreferences.LANGUAGE.get(preferences);
		String newCurrency = GeneralPreferences.CURRENCY.get(preferences);
		String newCurrencySymbol = GeneralPreferences.CURRENCY_SYMBOL.get(preferences);
		String newUnitOfMeasurement = GeneralPreferences.UNIT_OF_MEASUREMENT.get(preferences);
		String newTheme = GeneralPreferences.THEME.get(preferences);

		return !Compare.string(newLanguage, previousLanguage)
				|| !Compare.string(newCurrency, previousCurrency)
				|| !Compare.string(newCurrencySymbol, previousCurrencySymbol)
				|| !Compare.string(newUnitOfMeasurement, previousUnitOfMeasurement)
				|| !Compare.string(newTheme, previousTheme);

	}

}
