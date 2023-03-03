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
package org.glasspath.revenue.template.word;

import java.io.File;

import org.glasspath.revenue.template.word.Word.ActiveDocument;
import org.glasspath.revenue.template.word.Word.Application;
import org.glasspath.revenue.template.word.Word.Documents;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;

public class WordUtils {

	private WordUtils() {

	}

	public static void open(File docxFile) throws Exception {

		if (docxFile != null && docxFile.exists()) {

			boolean inited = false;

			try {

				Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);

				inited = true;

				Word word = new Word();

				Application app = word.getApplication();
				if (app != null) {

					word.setVisible(true);

					Documents documents = app.getDocuments();
					if (documents != null) {
						documents.open(docxFile.getAbsolutePath());
					}

				} else {
					// TODO?
				}

			} catch (Exception e) {
				throw e;
			} finally {
				if (inited) {
					Ole32.INSTANCE.CoUninitialize();
				}
			}

		}

	}

	public static void exportToPdf(File docxFile, File pdfFile) throws Exception {

		if (docxFile != null && docxFile.exists() && pdfFile != null) {

			boolean inited = false;

			try {

				Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);

				inited = true;

				Word word = new Word();

				Application app = word.getApplication();
				if (app != null) {

					word.setVisible(false);

					Documents documents = app.getDocuments();
					if (documents != null) {

						documents.open(docxFile.getAbsolutePath());

						ActiveDocument activeDocument = word.getActiveDocument();
						if (activeDocument != null) {

							activeDocument.saveAs(pdfFile.getAbsolutePath(), Word.FILE_TYPE_PDF);
							activeDocument.close(false);

						}

					}

					word.quit();

				} else {
					// TODO?
				}

			} catch (Exception e) {
				throw e;
			} finally {
				if (inited) {
					Ole32.INSTANCE.CoUninitialize();
				}
			}

		}

	}

}
