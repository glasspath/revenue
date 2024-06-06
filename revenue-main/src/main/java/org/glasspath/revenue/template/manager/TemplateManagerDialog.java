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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.glasspath.common.os.OsUtils;
import org.glasspath.common.os.preferences.BasicFilePreferences;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.FrameContext;
import org.glasspath.common.swing.dialog.AboutDialog.IAbout;
import org.glasspath.common.swing.file.manager.FileManagerDialog;
import org.glasspath.revenue.resources.Resources;
import org.glasspath.revenue.template.TemplateFiles;
import org.glasspath.revenue.template.manager.CompanyOptionsPanel.CompanyNamesProvider;

public class TemplateManagerDialog extends FileManagerDialog {

	protected final FrameContext context;
	protected final IAbout about;
	protected final CompanyNamesProvider companyNamesProvider;

	public TemplateManagerDialog(FrameContext context, IAbout about, CompanyNamesProvider companyNamesProvider) {
		this(context, about, companyNamesProvider, null);
	}

	public TemplateManagerDialog(FrameContext context, IAbout about, CompanyNamesProvider companyNamesProvider, Category selectedCategory) {

		super(context);

		this.context = context;
		this.about = about;
		this.companyNamesProvider = companyNamesProvider;

		setTitle(Resources.getString("TemplateManager")); //$NON-NLS-1$
		addButton.setText(Resources.getString("AddTemplateFromLibrary")); //$NON-NLS-1$

		List<Category> categories = new ArrayList<>();
		categories.add(TemplateFiles.INVOICE_TEMPLATES);
		categories.add(TemplateFiles.INVOICE_EMAIL_TEMPLATES);
		categories.add(TemplateFiles.REPORT_TEMPLATES);
		categories.add(TemplateFiles.REPORT_EMAIL_TEMPLATES);
		categories.add(TemplateFiles.TIME_SHEET_TEMPLATES);
		categories.add(TemplateFiles.TIME_SHEET_EMAIL_TEMPLATES);
		setCategories(categories);

		if (selectedCategory != null) {
			setSelectedCategory(selectedCategory);
		}

		refresh();

		pack();
		setLocationRelativeTo(context.getFrame());
		setVisible(true);

	}

	@Override
	protected JComponent getOptionsComponent(Category selectedCategory, File selectedFile) {

		JComponent optionsComponent = null;

		if (selectedCategory != null) {

			File globalPreferencesFile = new File(selectedCategory.getDirectory(), "." + TemplateFiles.TEMPLATE_PREFERENCES_EXTENSION); //$NON-NLS-1$
			PreferencesProvider globalProvider = new PreferencesProvider(new BasicFilePreferences(globalPreferencesFile));

			if (selectedFile != null) {

				File templatePreferencesFile = OsUtils.getFileByAddingExtension(selectedFile, TemplateFiles.TEMPLATE_PREFERENCES_EXTENSION);
				PreferencesProvider templateProvider = new PreferencesProvider(new BasicFilePreferences(templatePreferencesFile));

				optionsComponent = getOptionsComponent(selectedCategory, templateProvider, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						new TemplateOptionsDialog(context, globalProvider, getOptionsComponent(selectedCategory, globalProvider, null));
					}
				});

				templateProvider.setEnabled(OptionsPanelsHeader.TEMPLATE_SPECIFIC_OPTIONS_ENABLED.get(templateProvider.getPreferences()));

			}

		}

		return optionsComponent;

	}

	protected JPanel getOptionsComponent(Category selectedCategory, PreferencesProvider provider, ActionListener headerAction) {

		JPanel optionsPanel = new JPanel();

		if (selectedCategory != null && provider != null) {

			optionsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

			GridBagLayout layout = new GridBagLayout();
			layout.rowWeights = new double[] { 0.0, 0.1 };
			layout.rowHeights = new int[] { 0, 100 };
			layout.columnWeights = new double[] { 0.1 };
			layout.columnWidths = new int[] { 100 };
			optionsPanel.setLayout(layout);

			if (headerAction != null) {
				optionsPanel.add(new OptionsPanelsHeader(provider, headerAction), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}

			JTabbedPane optionsTabbedPane = new JTabbedPane();
			optionsTabbedPane.setBorder(BorderFactory.createEmptyBorder());
			optionsPanel.add(optionsTabbedPane, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 8, 0, 8), 0, 0));

			if (selectedCategory == TemplateFiles.INVOICE_TEMPLATES) {
				optionsTabbedPane.addTab(Resources.getString("InvoiceOptions"), createOptionsTab(new InvoiceTemplateOptionsPanel(provider))); //$NON-NLS-1$
			} else if (selectedCategory == TemplateFiles.REPORT_TEMPLATES) {
				optionsTabbedPane.addTab(Resources.getString("ReportOptions"), createOptionsTab(new ReportTemplateOptionsPanel(provider))); //$NON-NLS-1$
			} else if (selectedCategory == TemplateFiles.TIME_SHEET_TEMPLATES) {
				optionsTabbedPane.addTab(Resources.getString("TimeSheetOptions"), createOptionsTab(new TimeSheetTemplateOptionsPanel(provider))); //$NON-NLS-1$
			} else if (selectedCategory == TemplateFiles.INVOICE_EMAIL_TEMPLATES) {
				// No options yet
			} else if (selectedCategory == TemplateFiles.REPORT_EMAIL_TEMPLATES) {
				// No options yet
			} else if (selectedCategory == TemplateFiles.TIME_SHEET_EMAIL_TEMPLATES) {
				// No options yet
			}

			optionsTabbedPane.addTab(Resources.getString("FieldFormatting"), createOptionsTab(new FieldFormatPanel(provider))); //$NON-NLS-1$
			optionsTabbedPane.addTab(Resources.getString("MyCompanyDetails"), createOptionsTab(new CompanyOptionsPanel(provider, companyNamesProvider))); //$NON-NLS-1$

		}

		return optionsPanel;

	}

	protected JScrollPane createOptionsTab(JComponent component) {

		JScrollPane scrollPane = new JScrollPane(component);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		return scrollPane;

	}

	@Override
	protected String getFileDescription(File file) {
		return TemplateFiles.getTemplateDescription(file);
	}

	@Override
	protected Icon getFileIcon(File file) {
		return TemplateFiles.getTemplateIcon(file);
	}

	@Override
	protected void editFile(Category selectedCategory, File file) {
		TemplateFiles.editDocument(context, about, selectedCategory, file);
	}

}
