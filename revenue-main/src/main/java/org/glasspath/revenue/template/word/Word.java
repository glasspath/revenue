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

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.WinDef.LONG;
import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.COM.IDispatch;

@SuppressWarnings("nls")
public class Word extends COMLateBindingObject {

	public static final int FILE_TYPE_PDF = 17;
	
	public Word() throws COMException {
		super("Word.Application", false);
	}

	public String getVersion() throws COMException {
		return getStringProperty("Version");
	}

	public Application getApplication() throws COMException {
		IDispatch iDispatch = getAutomationProperty("Application");
		if (iDispatch != null) {
			return new Application(iDispatch);
		} else {
			return null;
		}
	}

	public void setVisible(boolean visible) throws COMException {
		this.setProperty("Visible", visible);
	}

	public ActiveDocument getActiveDocument() {

		IDispatch iDispatch = getAutomationProperty("ActiveDocument");
		if (iDispatch != null) {
			return new ActiveDocument(iDispatch);
		} else {
			return null;
		}

	}

	public void quit() throws COMException {
		invokeNoReply("Quit");
	}

	public static class Application extends COMLateBindingObject {

		public Application(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}

		public Documents getDocuments() {

			IDispatch iDispatch = getAutomationProperty("Documents");
			if (iDispatch != null) {
				return new Documents(iDispatch);
			} else {
				return null;
			}

		}

	}

	public static class Documents extends COMLateBindingObject {

		public Documents(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}

		public void open(String filePath) throws COMException {
			this.invokeNoReply("Open", new VARIANT(filePath));
		}

	}

	public static class ActiveDocument extends COMLateBindingObject {

		public ActiveDocument(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}

		public void saveAs(String filePath, int fileType) throws COMException {
			invokeNoReply("SaveAs", new VARIANT(filePath), new VARIANT(new LONG(fileType)));
		}

		public void close(boolean save) throws COMException {
			invokeNoReply("Close", new VARIANT(save));
		}

	}

}
