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

public class ReportTemplateOptionsPanel extends JPanel {

	public static final int GROUP_HOURS_BY_RATE = 0;
	public static final int GROUP_HOURS_BY_RATE_AND_CLIENT = 1;
	public static final int GROUP_HOURS_BY_RATE_AND_CLIENT_AND_PROJECT = 2;
	public static final String[] GROUP_HOURS_OPTIONS_LIST = {
			Resources.getString("GroupByRate"), //$NON-NLS-1$
			Resources.getString("GroupByRateAndClient"), //$NON-NLS-1$
			Resources.getString("GroupByRateClientAndProject") //$NON-NLS-1$
	};

	public static final int GROUP_MILEAGES_BY_RATE = 0;
	public static final int GROUP_MILEAGES_BY_RATE_AND_CLIENT = 1;
	public static final int GROUP_MILEAGES_BY_RATE_AND_CLIENT_AND_PROJECT = 2;
	public static final String[] GROUP_MILEAGES_OPTIONS_LIST = {
			Resources.getString("GroupByRate"), //$NON-NLS-1$
			Resources.getString("GroupByRateAndClient"), //$NON-NLS-1$
			Resources.getString("GroupByRateClientAndProject") //$NON-NLS-1$
	};

	public static final int GROUP_CALLS_BY_RATE = 0;
	public static final int GROUP_CALLS_BY_RATE_AND_COMPANY = 1;
	public static final String[] GROUP_CALLS_OPTIONS_LIST = {
			Resources.getString("GroupByRate"), //$NON-NLS-1$
			Resources.getString("GroupByRateAndCompany") //$NON-NLS-1$
	};

	public static final int SORTING_DATE_TIME_ASCENDING = 0;
	public static final int SORTING_DATE_TIME_DESCENDING = 1;
	public static final String[] SORTING_OPTIONS_LIST = new String[] {
			Resources.getString("DateTimeAscending"), //$NON-NLS-1$
			Resources.getString("DateTimeDecsending") //$NON-NLS-1$
	};

	public static final IntPref HOUR_GROUPING = new IntPref("hourGrouping", GROUP_HOURS_BY_RATE_AND_CLIENT_AND_PROJECT); //$NON-NLS-1$
	public static final IntPref MILEAGE_GROUPING = new IntPref("mileageGrouping", GROUP_MILEAGES_BY_RATE_AND_CLIENT_AND_PROJECT); //$NON-NLS-1$
	public static final IntPref CALL_GROUPING = new IntPref("callGrouping", GROUP_CALLS_BY_RATE_AND_COMPANY); //$NON-NLS-1$

	public static final IntPref SORTING = new IntPref("sorting", SORTING_DATE_TIME_ASCENDING); //$NON-NLS-1$

	public static final Pref UNSPECIFIED_CLIENTS = new Pref("unspecifiedClients", "No client specified"); //$NON-NLS-1$
	public static final Pref UNSPECIFIED_COMPANIES = new Pref("unspecifiedCompanies", "No company specified"); //$NON-NLS-1$
	public static final Pref UNSPECIFIED_PROJECTS = new Pref("unspecifiedProjects", "No project specified"); //$NON-NLS-1$
	public static final BoolPref INCLUDE_INVOICES = new BoolPref("includeInvoices", true); //$NON-NLS-1$
	public static final BoolPref INCLUDE_EXPENSES = new BoolPref("includeExpenses", true); //$NON-NLS-1$
	public static final BoolPref INCLUDE_QUOTES = new BoolPref("includeQuotes", true); //$NON-NLS-1$
	public static final BoolPref INCLUDE_HOURS = new BoolPref("includeHours", true); //$NON-NLS-1$
	public static final BoolPref INCLUDE_MILEAGE = new BoolPref("includeMileage", true); //$NON-NLS-1$
	public static final BoolPref INCLUDE_CALLS = new BoolPref("includeCalls", true); //$NON-NLS-1$

	public ReportTemplateOptionsPanel(PreferencesProvider provider) {

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 20, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 5, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 5 };
		layout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.0, 0.1, 0.0 };
		layout.columnWidths = new int[] { 7, 140, 5, 20, 5, 20, 7 };
		setLayout(layout);

		add(new JLabel(Resources.getString("Hours")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, HOUR_GROUPING, GROUP_HOURS_OPTIONS_LIST), new GridBagConstraints(3, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("Mileages")), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, MILEAGE_GROUPING, GROUP_MILEAGES_OPTIONS_LIST), new GridBagConstraints(3, 3, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("Calls")), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, CALL_GROUPING, GROUP_CALLS_OPTIONS_LIST), new GridBagConstraints(3, 5, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("Sorting")), new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, SORTING, SORTING_OPTIONS_LIST), new GridBagConstraints(3, 7, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Unspecified clients"), new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, UNSPECIFIED_CLIENTS), new GridBagConstraints(3, 9, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Unspecified companies"), new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, UNSPECIFIED_COMPANIES), new GridBagConstraints(3, 11, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Unspecified projects"), new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createTextField(provider, UNSPECIFIED_PROJECTS), new GridBagConstraints(3, 13, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Include"), new GridBagConstraints(1, 15, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(PreferencesUtils.createCheckBox(provider, INCLUDE_INVOICES, Resources.getString("CategoryInvoices")), new GridBagConstraints(3, 15, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, INCLUDE_EXPENSES, Resources.getString("CategoryExpenses")), new GridBagConstraints(3, 17, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, INCLUDE_QUOTES, Resources.getString("CategoryQuotes")), new GridBagConstraints(3, 19, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, INCLUDE_HOURS, Resources.getString("CategoryHours")), new GridBagConstraints(3, 21, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, INCLUDE_MILEAGE, Resources.getString("CategoryMileages")), new GridBagConstraints(3, 23, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, INCLUDE_CALLS, Resources.getString("CategoryCalls")), new GridBagConstraints(3, 25, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

	}

}
