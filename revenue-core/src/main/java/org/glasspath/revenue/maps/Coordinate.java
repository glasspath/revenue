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

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@SuppressWarnings("nls")
public class Coordinate {

	public static final double EARTH_RADIUS = 3958.75;
	public static final float MILES_TO_KILOMETERS_FACTOR = 1.609F;

	@JacksonXmlProperty(isAttribute = true, localName = "date")
	private long date = 0;

	@JacksonXmlProperty(isAttribute = true, localName = "lat")
	private double latitude = 0.0;

	@JacksonXmlProperty(isAttribute = true, localName = "lng")
	private double longitude = 0.0;

	public Coordinate() {

	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public static float distanceKilometers(List<Coordinate> coordinates) {
		float total = 0;
		for (int i = 0; i < coordinates.size() - 1; i++) {
			total += Coordinate.distanceKilometers(coordinates.get(i), coordinates.get(i + 1));
		}
		return total;
	}

	public static float distanceKilometers(Coordinate from, Coordinate to) {
		return distanceMiles(from, to) * MILES_TO_KILOMETERS_FACTOR;
	}

	public static float distanceMiles(List<Coordinate> coordinates) {
		float total = 0;
		for (int i = 0; i < coordinates.size() - 1; i++) {
			total += Coordinate.distanceMiles(coordinates.get(i), coordinates.get(i + 1));
		}
		return total;
	}

	public static float distanceMiles(Coordinate from, Coordinate to) {

		double dLat = Math.toRadians(to.getLatitude() - from.getLatitude());
		double dLng = Math.toRadians(to.getLongitude() - from.getLongitude());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(from.getLatitude())) * Math.cos(Math.toRadians(to.getLatitude())) * Math.sin(dLng / 2) * Math.sin(dLng / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = EARTH_RADIUS * c;

		return distance.floatValue();

	}

	@Override
	public String toString() {
		return "Latitude: " + latitude + " longitude: " + longitude;
	}

}
