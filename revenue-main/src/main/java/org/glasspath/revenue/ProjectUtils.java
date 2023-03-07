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
package org.glasspath.revenue;

import java.io.File;

import org.glasspath.common.os.OsUtils;
import org.glasspath.common.xml.XmlUtils;

import com.fasterxml.jackson.annotation.JsonRootName;

@SuppressWarnings("nls")
public class ProjectUtils {

	public static final String CONTENT_XML = "content.xml";
	public static final String INVOICES_DIR = "invoices";
	public static final String REPORTS_DIR = "reports";
	public static final String TIME_SHEETS_DIR = "timesheets";
	public static final String TEMPLATES_DIR = "templates";
	public static final String INVOICE_TEMPLATES_DIR = "templates/invoice";
	public static final String REPORT_TEMPLATES_DIR = "templates/report";
	public static final String TIME_SHEET_TEMPLATES_DIR = "templates/timesheet";
	public static final String EMAIL_TEMPLATES_DIR = "templates/email";
	public static final String INVOICE_EMAIL_TEMPLATES_DIR = "templates/email/invoice";
	public static final String REPORT_EMAIL_TEMPLATES_DIR = "templates/email/report";
	public static final String TIME_SHEET_EMAIL_TEMPLATES_DIR = "templates/email/timesheet";

	public static final String[] PROJECT_DIRS = new String[] {
			INVOICES_DIR,
			REPORTS_DIR,
			TIME_SHEETS_DIR,
			TEMPLATES_DIR,
			INVOICE_TEMPLATES_DIR,
			REPORT_TEMPLATES_DIR,
			TIME_SHEET_TEMPLATES_DIR,
			EMAIL_TEMPLATES_DIR,
			INVOICE_EMAIL_TEMPLATES_DIR,
			REPORT_EMAIL_TEMPLATES_DIR,
			TIME_SHEET_EMAIL_TEMPLATES_DIR,
	};

	private ProjectUtils() {

	}

	public static boolean isValidParentPathForNewProject(String projectParentDirPath) {

		if (projectParentDirPath != null && projectParentDirPath.length() > 0) {
			return new File(projectParentDirPath).exists();
		}

		return false;

	}

	public static File getDirFileForNewProject(String projectParentDirPath, String projectDirName) {

		if (OsUtils.isValidFileName(projectDirName) && projectParentDirPath != null && projectParentDirPath.length() > 0) {

			File parentDirFile = new File(projectParentDirPath);
			if (parentDirFile.exists()) {

				File projectDirFile = new File(parentDirFile, projectDirName);
				if (!projectDirFile.exists()) {
					return projectDirFile;
				} else {
					// TODO: Throw exception
				}

			} else {
				// TODO: Throw exception
			}

		} else {
			// TODO: Throw exception
		}

		return null;

	}

	public static File createNewProject(String projectParentDirPath, String projectDirName, File applicationDir) {

		File projectDir = getDirFileForNewProject(projectParentDirPath, projectDirName);
		if (projectDir != null && !projectDir.exists()) {

			if (applicationDir.isDirectory()) {

				try {

					projectDir.mkdir();

					for (String dir : PROJECT_DIRS) {
						new File(projectDir, dir).mkdir();
					}

					if (!copyBundledFile(applicationDir, projectDir, INVOICE_TEMPLATES_DIR, "Basic invoice template 01.gpdx")) {
						// TODO: return null?
					}
					if (!copyBundledFile(applicationDir, projectDir, REPORT_TEMPLATES_DIR, "Basic report template 01.gpdx")) {
						// TODO: return null?
					}
					if (!copyBundledFile(applicationDir, projectDir, TIME_SHEET_TEMPLATES_DIR, "Basic time sheet template 01.gpdx")) {
						// TODO: return null?
					}
					if (!copyBundledFile(applicationDir, projectDir, INVOICE_EMAIL_TEMPLATES_DIR, "Basic invoice email template 01.gpex")) {
						// TODO: return null?
					}
					if (!copyBundledFile(applicationDir, projectDir, REPORT_EMAIL_TEMPLATES_DIR, "Basic report email template 01.gpex")) {
						// TODO: return null?
					}
					if (!copyBundledFile(applicationDir, projectDir, TIME_SHEET_EMAIL_TEMPLATES_DIR, "Basic time sheet email template 01.gpex")) {
						// TODO: return null?
					}

					XmlUtils.createXmlMapper().writeValue(new File(projectDir, CONTENT_XML), new DefaultContent());

				} catch (Exception e) {
					// TODO: Throw exception
					return null;
				}

			} else {
				// TODO: Throw exception
			}

		} else {
			// TODO: Throw exception
		}

		return projectDir;

	}

	private static boolean copyBundledFile(File applicationDir, File projectDir, String path, String fileName) {
		return OsUtils.copyFile(new File(applicationDir, path + "/" + fileName), new File(projectDir, path + "/" + fileName)) != null;
	}

	public static boolean isValidProject(String contentXmlPath) {

		// TODO: Should the file always be named content.xml? (for now we allow other names)
		if (contentXmlPath != null && contentXmlPath.toLowerCase().endsWith(".xml")) {

			File contentXmlFile = new File(contentXmlPath);
			if (contentXmlFile.exists()) {

				File projectDir = contentXmlFile.getParentFile();
				if (projectDir != null && projectDir.isDirectory()) {

					for (String dir : PROJECT_DIRS) {
						if (!new File(projectDir, dir).isDirectory()) {
							return false;
						}
					}

					return true;

				}

			}

		}

		return false;

	}

	// TODO: For now we use a empty implementation because the actual implementation is not yet open source..
	@JsonRootName(value = "Content")
	private static class DefaultContent extends AbstractContent {

		public DefaultContent() {

		}

		@Override
		public void init() {

		}

		@Override
		public void clear() {

		}

	}

}
