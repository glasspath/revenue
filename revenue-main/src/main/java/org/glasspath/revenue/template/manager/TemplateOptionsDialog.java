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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;

import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.FrameContext;
import org.glasspath.common.swing.dialog.DefaultDialog;
import org.glasspath.common.swing.resources.CommonResources;
import org.glasspath.revenue.icons.Icons;

public class TemplateOptionsDialog extends DefaultDialog {

	public TemplateOptionsDialog(FrameContext context, PreferencesProvider provider, JComponent optionsComponent) {

		super(context);

		setTitle("Template options");
		getHeader().setTitle("Template options");
		getHeader().setIcon(Icons.cogBoxXLarge);
		setPreferredSize(DIALOG_SIZE_SQUARE);
		setKeyListenerEnabled(false);

		getContentPanel().setLayout(new BorderLayout());
		getContentPanel().setBorder(BorderFactory.createEmptyBorder());

		if (optionsComponent != null) {
			getContentPanel().add(optionsComponent, BorderLayout.CENTER);
		}

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				provider.clearPreferences();
			}
		});
		
		getFooter().add(Box.createRigidArea(new Dimension(getHelpButton().isVisible() ? 5 : 0, 5)), 1);
		getFooter().add(resetButton, 2);

		
		getFooter().remove(getOkButton());
		getCancelButton().setText(CommonResources.getString("Close"));

		pack();
		setLocationRelativeTo(context.getFrame());
		setVisible(true);

	}

}
