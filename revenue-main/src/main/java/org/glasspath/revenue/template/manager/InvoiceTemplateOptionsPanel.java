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

import org.glasspath.common.os.preferences.Pref;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.preferences.PreferencesUtils;
import org.glasspath.revenue.resources.Resources;

public class InvoiceTemplateOptionsPanel extends JPanel {

	public static final int GROUP_HOURS_BY_RATE = 0;
	public static final int GROUP_HOURS_BY_RATE_AND_PROJECT = 1;
	public static final int DO_NOT_GROUP_HOURS = 2;
	public static final String[] GROUP_HOURS_OPTIONS_LIST = {
			Resources.getString("GroupByRate"), //$NON-NLS-1$
			Resources.getString("GroupByRateAndProject"), //$NON-NLS-1$
			Resources.getString("DontGroup") //$NON-NLS-1$
	};

	public static final int GROUP_MILEAGES_BY_RATE = 0;
	public static final int GROUP_MILEAGES_BY_RATE_AND_PROJECT = 1;
	public static final int DO_NOT_GROUP_MILEAGES = 2;
	public static final String[] GROUP_MILEAGES_OPTIONS_LIST = {
			Resources.getString("GroupByRate"), //$NON-NLS-1$
			Resources.getString("GroupByRateAndProject"), //$NON-NLS-1$
			Resources.getString("DontGroup") //$NON-NLS-1$
	};

	public static final int GROUP_CALLS_BY_RATE = 0;
	public static final int GROUP_CALLS_BY_RATE_AND_CONTACT = 1;
	public static final int DO_NOT_GROUP_CALLS = 2;
	public static final String[] GROUP_CALLS_OPTIONS_LIST = {
			Resources.getString("GroupByRate"), //$NON-NLS-1$
			Resources.getString("GroupByRateAndContact"), //$NON-NLS-1$
			Resources.getString("DontGroup") //$NON-NLS-1$
	};

	public static final int CUSTOM_INVOICE_LINE_POSITION_BOTTOM = 0;
	public static final int CUSTOM_INVOICE_LINE_POSITION_TOP = 1;
	public static final String[] CUSTOM_INVOICE_LINE_POSITION_OPTIONS_LIST = {
			"Place at bottom",
			"PLace at top"
	};

	public static final int SORTING_DATE_TIME_ASCENDING = 0;
	public static final int SORTING_DATE_TIME_DESCENDING = 1;
	public static final String[] SORTING_OPTIONS_LIST = new String[] {
			Resources.getString("DateTimeAscending"), //$NON-NLS-1$
			Resources.getString("DateTimeDecsending") //$NON-NLS-1$
	};

	public static final Pref HOUR_GROUPING = new Pref("hourGrouping", GROUP_HOURS_BY_RATE); //$NON-NLS-1$
	public static final Pref HOUR_PREPEND_TEXT = new Pref("hourPrependText", Resources.getString("Hours") + " "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final Pref EMPTY_LINE_AFTER_HOURS = new Pref("emptyLineAfterHours", false); //$NON-NLS-1$

	public static final Pref MILEAGE_GROUPING = new Pref("mileageGrouping", GROUP_MILEAGES_BY_RATE); //$NON-NLS-1$
	public static final Pref MILEAGE_PREPEND_TEXT = new Pref("mileagePrependText", Resources.getString("MileageExpenditures") + " "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final Pref EMPTY_LINE_AFTER_MILEAGES = new Pref("emptyLineAfterMileages", false); //$NON-NLS-1$

	public static final Pref CALL_GROUPING = new Pref("callGrouping", GROUP_CALLS_BY_RATE); //$NON-NLS-1$
	public static final Pref CALL_PREPEND_TEXT = new Pref("callPrependText", Resources.getString("CallExpenditure") + " "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final Pref EMPTY_LINE_AFTER_CALLS = new Pref("emptyLineAfterCalls", false); //$NON-NLS-1$

	public static final Pref CUSTOM_INVOICE_LINES_POSITION = new Pref("customInvoiceLinesPosition", CUSTOM_INVOICE_LINE_POSITION_BOTTOM); //$NON-NLS-1$
	public static final Pref EMPTY_LINE_AFTER_CUSTOM_INVOICE_LINES = new Pref("emptyLineAfterCustomInvoiceLines", false); //$NON-NLS-1$

	public static final Pref SORTING = new Pref("sorting", SORTING_DATE_TIME_ASCENDING); //$NON-NLS-1$

	public InvoiceTemplateOptionsPanel(PreferencesProvider provider) {

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 20, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 3, 23, 5 };
		layout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.1, 0.0, 0.1, 0.0 };
		layout.columnWidths = new int[] { 7, 140, 5, 20, 20, 3, 20, 7 };
		setLayout(layout);

		add(new JLabel("Hours grouping"), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, HOUR_GROUPING, GROUP_HOURS_OPTIONS_LIST), new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); // $NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, EMPTY_LINE_AFTER_HOURS, Resources.getString("AddEmptyLine")), new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

		add(new JLabel("Hours text"), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, HOUR_PREPEND_TEXT), new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Mileage grouping"), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, MILEAGE_GROUPING, GROUP_MILEAGES_OPTIONS_LIST), new GridBagConstraints(3, 5, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); // $NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, EMPTY_LINE_AFTER_MILEAGES, Resources.getString("AddEmptyLine")), new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

		add(new JLabel("Mileage text"), new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, MILEAGE_PREPEND_TEXT), new GridBagConstraints(3, 7, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel("Calls grouping"), new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createComboBox(provider, CALL_GROUPING, GROUP_CALLS_OPTIONS_LIST), new GridBagConstraints(3, 9, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); // $NON-NLS-1$
		add(PreferencesUtils.createCheckBox(provider, EMPTY_LINE_AFTER_CALLS, Resources.getString("AddEmptyLine")), new GridBagConstraints(6, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

		add(new JLabel("Calls text"), new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createTextField(provider, CALL_PREPEND_TEXT), new GridBagConstraints(3, 11, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		add(new JLabel(Resources.getString("CustomInvoiceLines")), new GridBagConstraints(1, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, CUSTOM_INVOICE_LINES_POSITION, CUSTOM_INVOICE_LINE_POSITION_OPTIONS_LIST), new GridBagConstraints(3, 13, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(PreferencesUtils.createCheckBox(provider, EMPTY_LINE_AFTER_CUSTOM_INVOICE_LINES, Resources.getString("AddEmptyLine")), new GridBagConstraints(6, 13, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

		add(new JLabel(Resources.getString("Sorting")), new GridBagConstraints(1, 15, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		add(PreferencesUtils.createComboBox(provider, SORTING, SORTING_OPTIONS_LIST), new GridBagConstraints(3, 15, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	}

}
