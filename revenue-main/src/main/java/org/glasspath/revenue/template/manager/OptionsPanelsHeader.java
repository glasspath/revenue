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

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.glasspath.common.icons.Icons;
import org.glasspath.common.os.preferences.Pref;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.color.ColorUtils;
import org.glasspath.common.swing.preferences.PreferencesUtils;
import org.glasspath.common.swing.theme.Theme;

public class OptionsPanelsHeader extends JPanel {

	public static final Pref TEMPLATE_SPECIFIC_OPTIONS_ENABLED = new Pref("enabled", false); //$NON-NLS-1$

	public OptionsPanelsHeader(PreferencesProvider provider, ActionListener editGlobalOptionsAction) {

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		layout.rowHeights = new int[] { 7, 10, 15 };
		layout.columnWeights = new double[] { 0.0, 0.0, 0.1, 0.0, 0.0 };
		layout.columnWidths = new int[] { 7, 140, 5, 200, 12 };
		setLayout(layout);

		JCheckBox enableCheckBox = PreferencesUtils.createCheckBox(provider, TEMPLATE_SPECIFIC_OPTIONS_ENABLED, "Enable template specific options");
		add(enableCheckBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0)); // $NON-NLS-1$
		enableCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				provider.setEnabled(enableCheckBox.isSelected());
			}
		});
		enableCheckBox.putClientProperty(PreferencesUtils.PROPERTY_IGNORE_STATE_CHANGED, Boolean.TRUE);

		JLabel editGlobalOptionsLabel = new JLabel("Configure global template options");
		editGlobalOptionsLabel.setIcon(Icons.cogBlue);
		editGlobalOptionsLabel.setForeground(Theme.isDark() ? ColorUtils.BLUE : ColorUtils.DARK_BLUE);
		editGlobalOptionsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(editGlobalOptionsLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0)); // $NON-NLS-1$
		editGlobalOptionsLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				editGlobalOptionsAction.actionPerformed(null);
			}
		});

	}

}
