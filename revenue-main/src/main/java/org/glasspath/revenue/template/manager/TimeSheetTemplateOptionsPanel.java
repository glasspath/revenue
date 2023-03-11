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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.glasspath.common.os.preferences.BoolPref;
import org.glasspath.common.os.preferences.IntPref;
import org.glasspath.common.os.preferences.Pref;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.preferences.PreferencesUtils;
import org.glasspath.revenue.resources.Resources;

public class TimeSheetTemplateOptionsPanel extends JPanel {

	public static final int SORTING_DATE_TIME_ASCENDING = 0;
	public static final int SORTING_DATE_TIME_DESCENDING = 1;
	public static final String[] SORTING_OPTIONS_LIST = new String[] {
			Resources.getString("DateTimeAscending"), //$NON-NLS-1$
			Resources.getString("DateTimeDecsending") //$NON-NLS-1$
	};

	public static final IntPref SORTING = new IntPref("sorting", SORTING_DATE_TIME_ASCENDING); //$NON-NLS-1$

	public static final Pref UNSPECIFIED_PROJECTS = new Pref("unspecifiedProjects", "No project specified"); //$NON-NLS-1$

	public static final BoolPref INCLUDE_HOURS = new BoolPref("includeHours", true); //$NON-NLS-1$
	public static final BoolPref INCLUDE_MILEAGE = new BoolPref("includeMileage", true); //$NON-NLS-1$
	public static final BoolPref INCLUDE_CALLS = new BoolPref("includeCalls", false); //$NON-NLS-1$

	public TimeSheetTemplateOptionsPanel(PreferencesProvider provider) {

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 20, 23, 3, 23, 5, 23, 3, 23, 3, 23, 5 };
		layout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.0, 0.1, 0.0 };
		layout.columnWidths = new int[] { 7, 140, 5, 20, 5, 20, 7 };
		setLayout(layout);

		add(new JLabel(Resources.getString("Sorting")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, SORTING, SORTING_OPTIONS_LIST), new GridBagConstraints(3, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Unspecified projects"), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, UNSPECIFIED_PROJECTS), new GridBagConstraints(3, 3, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Include"), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(PreferencesUtils.createCheckBox(provider, INCLUDE_HOURS, Resources.getString("CategoryHours")), new GridBagConstraints(3, 5, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, INCLUDE_MILEAGE, Resources.getString("CategoryMileages")), new GridBagConstraints(3, 7, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, INCLUDE_CALLS, Resources.getString("CategoryCalls")), new GridBagConstraints(3, 9, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

	}

}
