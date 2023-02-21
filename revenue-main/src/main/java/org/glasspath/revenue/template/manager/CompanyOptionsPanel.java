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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.glasspath.common.os.preferences.Pref;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.preferences.PreferencesUtils;
import org.glasspath.revenue.icons.Icons;
import org.glasspath.revenue.resources.Resources;

public class CompanyOptionsPanel extends JPanel {

	public static final Pref COMPANY_INDEX = new Pref("company", 0); //$NON-NLS-1$

	private final PreferencesProvider preferencesProvider;
	private final CompanyNamesProvider companyNamesProvider;
	private JComboBox<String> companiesComboBox = null;

	public CompanyOptionsPanel(PreferencesProvider preferencesProvider, CompanyNamesProvider companyNamesProvider) {

		this.preferencesProvider = preferencesProvider;
		this.companyNamesProvider = companyNamesProvider;

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 20, 23, 5, 35, 5 };
		layout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1, 0.0, 0.1, 0.0 };
		layout.columnWidths = new int[] { 7, 140, 5, 20, 5, 20, 7 };
		setLayout(layout);

		add(new JLabel(Resources.getString("Company")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

		JButton editCompaniesButton = new JButton("My company details");
		editCompaniesButton.setIcon(Icons.accountBox);
		add(editCompaniesButton, new GridBagConstraints(3, 3, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		editCompaniesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				companyNamesProvider.showCompaniesDialog();
				updateCompaniesComboBox();
			}
		});

		updateCompaniesComboBox();

	}

	private void updateCompaniesComboBox() {

		if (companiesComboBox != null) {
			remove(companiesComboBox);
		}

		List<String> companyNames = companyNamesProvider.getCompanyNames();
		companyNames.add(0, "Automatic");

		companiesComboBox = PreferencesUtils.createComboBox(preferencesProvider, COMPANY_INDEX, companyNames.toArray(new String[0]));
		companiesComboBox.setEnabled(preferencesProvider.isEnabled());
		add(companiesComboBox, new GridBagConstraints(3, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		invalidate();
		validate();
		repaint();

	}

	public static interface CompanyNamesProvider {

		public List<String> getCompanyNames();

		public void showCompaniesDialog();

	}

}
