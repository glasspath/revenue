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
package org.glasspath.revenue.template;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.Icon;
import javax.swing.JMenu;

import org.glasspath.aerialist.Aerialist;
import org.glasspath.aerialist.XDoc;
import org.glasspath.aerialist.editor.DocumentEditorContext;
import org.glasspath.aerialist.editor.DocumentEditorPanel;
import org.glasspath.common.Common;
import org.glasspath.common.os.OsUtils;
import org.glasspath.common.os.preferences.BasicFilePreferences;
import org.glasspath.common.swing.DesktopUtils;
import org.glasspath.common.swing.FrameContext;
import org.glasspath.common.swing.dialog.AboutDialog.IAbout;
import org.glasspath.common.swing.file.manager.FileManagerDialog.Category;
import org.glasspath.communique.Communique;
import org.glasspath.communique.editor.EmailEditorContext;
import org.glasspath.communique.editor.EmailEditorPanel;
import org.glasspath.revenue.icons.Icons;
import org.glasspath.revenue.template.invoice.InvoiceTemplateUtils;
import org.glasspath.revenue.template.report.ReportTemplateUtils;
import org.glasspath.revenue.template.timesheet.TimeSheetTemplateUtils;
import org.glasspath.revenue.template.word.WordUtils;

public class TemplateFiles {

	public static final int FILE_TYPE_UNKNOWN = 0;
	public static final int FILE_TYPE_GPDX = 1;
	public static final int FILE_TYPE_GPEX = 2;
	public static final int FILE_TYPE_DOCX = 3;
	public static final int FILE_TYPE_ODT = 4;

	public static final String WORD_DOCUMENT_EXTENSION = "docx"; //$NON-NLS-1$
	public static final String OPEN_OFFICE_DOCUMENT_EXTENSION = "odt"; //$NON-NLS-1$
	public static final String TEMPLATE_PREFERENCES_EXTENSION = "prefs"; //$NON-NLS-1$

	public static final FileFilter DOCUMENT_TEMPLATE_FILE_FILTER = OsUtils.createExtensionsFileFilter(
			Arrays.asList(XDoc.DOCUMENT_EXTENSION, WORD_DOCUMENT_EXTENSION, OPEN_OFFICE_DOCUMENT_EXTENSION));

	public static final FileFilter EMAIL_TEMPLATE_FILE_FILTER = OsUtils.createExtensionsFileFilter(Arrays.asList(XDoc.EMAIL_EXTENSION));

	public static final List<String> LINKED_FILE_EXTENSIONS = Arrays.asList(TEMPLATE_PREFERENCES_EXTENSION);

	public static final String INVOICE_TEMPLATES_DIR = "templates/invoice"; //$NON-NLS-1$
	public static final String REPORT_TEMPLATES_DIR = "templates/report"; //$NON-NLS-1$
	public static final String TIME_SHEET_TEMPLATES_DIR = "templates/timesheet"; //$NON-NLS-1$
	public static final String INVOICE_EMAIL_TEMPLATES_DIR = "templates/email/invoice"; //$NON-NLS-1$
	public static final String REPORT_EMAIL_TEMPLATES_DIR = "templates/email/report"; //$NON-NLS-1$
	public static final String TIME_SHEET_EMAIL_TEMPLATES_DIR = "templates/email/timesheet"; //$NON-NLS-1$

	public static final Category INVOICE_TEMPLATES = new Category("Invoice templates", DOCUMENT_TEMPLATE_FILE_FILTER, LINKED_FILE_EXTENSIONS);
	public static final Category REPORT_TEMPLATES = new Category("Report templates", DOCUMENT_TEMPLATE_FILE_FILTER, LINKED_FILE_EXTENSIONS);
	public static final Category TIME_SHEET_TEMPLATES = new Category("Time sheet templates", DOCUMENT_TEMPLATE_FILE_FILTER, LINKED_FILE_EXTENSIONS);
	public static final Category INVOICE_EMAIL_TEMPLATES = new Category("Invoice email templates", EMAIL_TEMPLATE_FILE_FILTER, LINKED_FILE_EXTENSIONS);
	public static final Category REPORT_EMAIL_TEMPLATES = new Category("Report email templates", EMAIL_TEMPLATE_FILE_FILTER, LINKED_FILE_EXTENSIONS);
	public static final Category TIME_SHEET_EMAIL_TEMPLATES = new Category("Time sheet email templates", EMAIL_TEMPLATE_FILE_FILTER, LINKED_FILE_EXTENSIONS);

	private TemplateFiles() {

	}

	public static void setApplicationDirectory(File applicationDir) {

		if (applicationDir != null) {

			INVOICE_TEMPLATES.setSourceDirectory(new File(applicationDir, INVOICE_TEMPLATES_DIR));
			REPORT_TEMPLATES.setSourceDirectory(new File(applicationDir, REPORT_TEMPLATES_DIR));
			TIME_SHEET_TEMPLATES.setSourceDirectory(new File(applicationDir, TIME_SHEET_TEMPLATES_DIR));
			INVOICE_EMAIL_TEMPLATES.setSourceDirectory(new File(applicationDir, INVOICE_EMAIL_TEMPLATES_DIR));
			REPORT_EMAIL_TEMPLATES.setSourceDirectory(new File(applicationDir, REPORT_EMAIL_TEMPLATES_DIR));
			TIME_SHEET_EMAIL_TEMPLATES.setSourceDirectory(new File(applicationDir, TIME_SHEET_EMAIL_TEMPLATES_DIR));

		} else {

			INVOICE_TEMPLATES.setSourceDirectory(null);
			REPORT_TEMPLATES.setSourceDirectory(null);
			TIME_SHEET_TEMPLATES.setSourceDirectory(null);
			INVOICE_EMAIL_TEMPLATES.setSourceDirectory(null);
			REPORT_EMAIL_TEMPLATES.setSourceDirectory(null);
			TIME_SHEET_EMAIL_TEMPLATES.setSourceDirectory(null);

		}

	}

	public static void setProjectDirectory(File projectDir) {

		if (projectDir != null) {

			INVOICE_TEMPLATES.setDirectory(new File(projectDir, INVOICE_TEMPLATES_DIR));
			REPORT_TEMPLATES.setDirectory(new File(projectDir, REPORT_TEMPLATES_DIR));
			TIME_SHEET_TEMPLATES.setDirectory(new File(projectDir, TIME_SHEET_TEMPLATES_DIR));
			INVOICE_EMAIL_TEMPLATES.setDirectory(new File(projectDir, INVOICE_EMAIL_TEMPLATES_DIR));
			REPORT_EMAIL_TEMPLATES.setDirectory(new File(projectDir, REPORT_EMAIL_TEMPLATES_DIR));
			TIME_SHEET_EMAIL_TEMPLATES.setDirectory(new File(projectDir, TIME_SHEET_EMAIL_TEMPLATES_DIR));

		} else {

			INVOICE_TEMPLATES.setDirectory(null);
			REPORT_TEMPLATES.setDirectory(null);
			TIME_SHEET_TEMPLATES.setDirectory(null);
			INVOICE_EMAIL_TEMPLATES.setDirectory(null);
			REPORT_EMAIL_TEMPLATES.setDirectory(null);
			TIME_SHEET_EMAIL_TEMPLATES.setDirectory(null);

		}

	}

	public static int getFileType(File file) {

		if (file != null) {
			return getFileType(file.getName());
		}

		return FILE_TYPE_UNKNOWN;

	}

	public static int getFileType(String fileName) {

		if (fileName != null) {

			fileName = fileName.toLowerCase();
			if (fileName.endsWith("." + XDoc.DOCUMENT_EXTENSION)) { //$NON-NLS-1$
				return FILE_TYPE_GPDX;
			} else if (fileName.endsWith("." + XDoc.EMAIL_EXTENSION)) { //$NON-NLS-1$
				return FILE_TYPE_GPEX;
			} else if (fileName.endsWith("." + WORD_DOCUMENT_EXTENSION)) { //$NON-NLS-1$
				return FILE_TYPE_DOCX;
			} else if (fileName.endsWith("." + OPEN_OFFICE_DOCUMENT_EXTENSION)) { //$NON-NLS-1$
				return FILE_TYPE_ODT;
			}

		}

		return FILE_TYPE_UNKNOWN;

	}

	public static String getTemplateDescription(File file) {

		switch (getFileType(file)) {

		case FILE_TYPE_GPDX:
			return "Template";

		case FILE_TYPE_GPEX:
			return "Email Template";

		case FILE_TYPE_DOCX:
			return "Word Template";

		case FILE_TYPE_ODT:
			return "Open Office Template";

		default:
			return "Unknown";

		}

	}

	public static Icon getTemplateIcon(File file) {
		return getTemplateIcon(getFileType(file));
	}

	public static Icon getTemplateIcon(String fileName) {
		return getTemplateIcon(getFileType(fileName));
	}

	public static Icon getTemplateIcon(int fileType) {

		switch (fileType) {

		case FILE_TYPE_GPDX:
			return Icons.fileDocumentOutlineGreenLarge;

		case FILE_TYPE_GPEX:
			return Icons.emailOutlineGreenLarge;

		default:
			return Icons.fileDocumentOutlineLarge;

		}

	}

	public static void editDocument(FrameContext context, Category category, File file) {

		switch (getFileType(file)) {

		case FILE_TYPE_GPDX:
			editAerialistDocument(context, category, file);
			break;

		case FILE_TYPE_GPEX:
			editCommuniqueEmail(context, category, file);
			break;

		case FILE_TYPE_DOCX:

			try {

				// First try using COM (docx documents might not be associated with Word)
				WordUtils.open(file.getAbsolutePath());

			} catch (Exception e) {

				Common.LOGGER.error("Exception while opening word document: " + file.getAbsolutePath(), e); //$NON-NLS-1$

				// Try the default way
				DesktopUtils.open(file, context.getFrame(), "Document could not be opened", "The document could not be opened..");

			}

			break;

		case FILE_TYPE_ODT:
			DesktopUtils.open(file, context.getFrame(), "Document could not be opened", "The document could not be opened..");
			break;

		default:
			break;

		}

	}

	public static void editAerialistDocument(FrameContext context, Category category, File file) {

		DocumentEditorContext editorContext = new DocumentEditorContext() {

			@Override
			public void populateInsertElementMenu(DocumentEditorPanel context, JMenu menu) {
				if (category == INVOICE_TEMPLATES) {
					InvoiceTemplateUtils.populateInsertElementMenu((DocumentEditorPanel) context, menu);
				} else if (category == REPORT_TEMPLATES) {
					ReportTemplateUtils.populateInsertElementMenu((DocumentEditorPanel) context, menu);
				} else if (category == TIME_SHEET_TEMPLATES) {
					TimeSheetTemplateUtils.populateInsertElementMenu((DocumentEditorPanel) context, menu);
				}
			}
		};

		if (category == INVOICE_TEMPLATES) {
			editorContext.setTemplateMetadata(InvoiceTemplateUtils.createInvoiceTemplateMetadata());
		} else if (category == REPORT_TEMPLATES) {
			editorContext.setTemplateMetadata(ReportTemplateUtils.createReportTemplateMetadata());
		} else if (category == TIME_SHEET_TEMPLATES) {
			editorContext.setTemplateMetadata(TimeSheetTemplateUtils.createTimeSheetTemplateMetadata());
		}

		if (context instanceof IAbout) {
			editorContext.setAbout((IAbout) context);
		}

		// TODO
		Aerialist.TEMP_TEST_STANDALONE = false;
		Aerialist.APPLICATION_CLASS = context.getClass();
		new Aerialist(editorContext, null, file.getAbsolutePath());

	}

	public static void editCommuniqueEmail(FrameContext context, Category category, File file) {

		EmailEditorContext editorContext = new EmailEditorContext() {

			@Override
			public void populateInsertElementMenu(EmailEditorPanel context, JMenu menu) {
				// TODO?
			}
		};
		editorContext.setSendButtonVisible(false);

		if (category == INVOICE_EMAIL_TEMPLATES) {
			editorContext.setTemplateMetadata(InvoiceTemplateUtils.createInvoiceTemplateMetadata());
		} else if (category == REPORT_EMAIL_TEMPLATES) {
			editorContext.setTemplateMetadata(ReportTemplateUtils.createReportTemplateMetadata());
		} else if (category == TIME_SHEET_EMAIL_TEMPLATES) {
			editorContext.setTemplateMetadata(TimeSheetTemplateUtils.createTimeSheetTemplateMetadata());
		}

		if (context instanceof IAbout) {
			editorContext.setAbout((IAbout) context);
		}

		// TODO
		Communique.TEMP_TEST_STANDALONE = false;
		Communique.APPLICATION_CLASS = context.getClass();
		new Communique(editorContext, null, file.getAbsolutePath());

	}

	public static Preferences getTemplatePreferences(File template) {

		if (template != null && template.exists()) {

			// TODO: Add extension instead of replacing
			File templatePrefs = OsUtils.getFileWithOtherExtension(template, TEMPLATE_PREFERENCES_EXTENSION);
			if (templatePrefs != null && templatePrefs.exists()) {

				BasicFilePreferences preferences = new BasicFilePreferences(templatePrefs);
				if (preferences.getBoolean("enabled", false)) { //$NON-NLS-1$
					return preferences;
				}

			}

			return getDefaultTemplatePreferences(template);

		}

		return null;

	}

	public static Preferences getDefaultTemplatePreferences(File template) {

		if (template != null && template.exists()) {

			File defaultPrefs = new File(template.getParent(), "." + TEMPLATE_PREFERENCES_EXTENSION); //$NON-NLS-1$
			if (defaultPrefs != null && defaultPrefs.exists()) {
				return new BasicFilePreferences(defaultPrefs);
			}

		}

		return null;

	}

}