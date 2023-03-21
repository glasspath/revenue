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
package org.glasspath.revenue.maps;

import java.util.ArrayList;
import java.util.List;

import org.glasspath.common.list.ListUtils;
import org.glasspath.common.swing.DesktopUtils;

public class MapUtils {

	private MapUtils() {

	}

	public static void showCoordinateInGoogleMaps(Coordinate coordinate) {
		DesktopUtils.browse("http://maps.google.com/maps?q=" + coordinate.getLatitude() + ",+" + coordinate.getLongitude()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static void showRouteInGoogleMaps(List<Coordinate> coordinates) {

		if (coordinates.size() > 1) {

			List<Coordinate> coords = new ArrayList<>();
			ListUtils.limit(10, coordinates, coords);

			if (coords.size() > 1) {

				Coordinate coordinate = coords.get(0);
				String routeString = "http://maps.google.com/maps?saddr=" + coordinate.getLatitude() + ",+" + coordinate.getLongitude() + "&daddr="; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

				for (int i = 1; i < coords.size(); i++) {
					coordinate = coords.get(i);
					routeString += coordinate.getLatitude() + "," + coordinate.getLongitude(); //$NON-NLS-1$
					routeString += "+to:"; //$NON-NLS-1$
				}

				coordinate = coordinates.get(coordinates.size() - 1);
				routeString += coordinate.getLatitude() + "," + coordinate.getLongitude(); //$NON-NLS-1$

				DesktopUtils.browse(routeString);

			}

		}

	}

	public static void showRouteInBingMapsApp(List<Coordinate> coordinates) {

		if (coordinates.size() > 1) {

			List<Coordinate> coords = new ArrayList<>();
			ListUtils.limit(25, coordinates, coords);

			if (coords.size() > 1) {

				String routeString = "bingmaps:?collection=name.Route"; //$NON-NLS-1$

				for (Coordinate coord : coords) {
					routeString += "~point." + coord.getLatitude() + "_" + coord.getLongitude(); //$NON-NLS-1$ //$NON-NLS-2$
				}

				DesktopUtils.browse(routeString);

			}

		}

	}

}
