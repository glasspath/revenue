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
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.glasspath.common.os.preferences.BoolPref;
import org.glasspath.common.os.preferences.IntPref;
import org.glasspath.revenue.resources.Resources;

@SuppressWarnings("nls")
public class CsvPreferencesPanel extends JPanel {

	public static final IntPref CSV_SEPARATOR = new IntPref("csvSeparator", 0);
	public static final String[] CSV_SEPARATOR_NAMES = new String[] {
			";",
			",",
			"tab"
	};
	public static final char[] CSV_SEPARATOR_CHARS = new char[] {
			';',
			',',
			'\t'
	};
	public static final BoolPref USE_QUOTES = new BoolPref("csvExportWithQuotes", true);

	private final Preferences preferences;
	private final JComboBox<String> csvSeperatorComboBox;
	private final JCheckBox csvExportWithQuotesCheckBox;

	public CsvPreferencesPanel(Preferences preferences) {

		this.preferences = preferences;

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 25, 5, 25, 5, 25, 5 };
		layout.columnWeights = new double[] { 0.1 };
		layout.columnWidths = new int[] { 250 };
		setLayout(layout);

		JPanel csvFilesPanel = new JPanel();
		add(csvFilesPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		GridBagLayout csvFilesPanelLayout = new GridBagLayout();
		csvFilesPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		csvFilesPanelLayout.rowHeights = new int[] { 7, 23, 5, 23, 7 };
		csvFilesPanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.1, 0.0 };
		csvFilesPanelLayout.columnWidths = new int[] { 7, 100, 5, 75, 100, 7 };
		csvFilesPanel.setLayout(csvFilesPanelLayout);
		csvFilesPanel.setBorder(BorderFactory.createTitledBorder(Resources.getString("CsvFiles")));

		csvSeperatorComboBox = new JComboBox<String>(new DefaultComboBoxModel<String>(CSV_SEPARATOR_NAMES));
		csvSeperatorComboBox.setSelectedIndex(CSV_SEPARATOR.get(preferences));
		csvFilesPanel.add(new JLabel(Resources.getString("SeparatorSign")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		csvFilesPanel.add(csvSeperatorComboBox, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		csvExportWithQuotesCheckBox = new JCheckBox(Resources.getString("PlaceValuesBetweenQuotes"));
		csvExportWithQuotesCheckBox.setSelected(USE_QUOTES.get(preferences));
		csvFilesPanel.add(new JLabel("Quotes"), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		csvFilesPanel.add(csvExportWithQuotesCheckBox, new GridBagConstraints(3, 3, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	}

	protected boolean submit() {

		CSV_SEPARATOR.put(preferences, csvSeperatorComboBox.getSelectedIndex());
		USE_QUOTES.put(preferences, csvExportWithQuotesCheckBox.isSelected());

		return false;

	}

	public static char getCsvSeparator(Preferences preferences) {

		int charIndex = CSV_SEPARATOR.get(preferences);

		if (charIndex >= 0 && charIndex < CSV_SEPARATOR_CHARS.length) {
			return CSV_SEPARATOR_CHARS[charIndex];
		} else {
			return CSV_SEPARATOR_CHARS[0];
		}

	}

}
