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

public class ProjectUtils {

	private ProjectUtils() {

	}

	public static boolean isValidParentPathForNewProject(String projectParentDirPath) {

		if (projectParentDirPath != null && projectParentDirPath.length() > 0) {
			return new File(projectParentDirPath).exists();
		}

		return false;

	}

	public static boolean isValidNameForNewProject(String projectParentDirPath, String projectDirName) {

		if (OsUtils.isValidFileName(projectDirName) && projectParentDirPath != null && projectParentDirPath.length() > 0) {

			File parentDirFile = new File(projectParentDirPath);
			if (parentDirFile.exists()) {

				File projectDirFile = new File(parentDirFile, projectDirName);

				return !projectDirFile.exists();

			}

		}

		return false;

	}

	public static File createNewProject(String projectParentDirPath, String projectDirName) {

		File projectDir = new File(projectParentDirPath, projectDirName);

		// TODO

		return projectDir;
		
	}

	public static boolean isValidProject(String projectDirPath) {

		File projectDirFile = new File(projectDirPath);
		if (projectDirFile.exists()) {

			// TODO

		}

		return false;

	}

}
