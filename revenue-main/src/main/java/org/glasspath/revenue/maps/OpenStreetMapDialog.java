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

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.event.MouseInputListener;

import org.glasspath.common.swing.FrameContext;
import org.glasspath.common.swing.SwingUtils;
import org.glasspath.common.swing.border.HidpiMatteBorder;
import org.glasspath.common.swing.dialog.DefaultDialog;
import org.glasspath.common.swing.resources.CommonResources;
import org.glasspath.common.swing.theme.Theme;
import org.glasspath.revenue.icons.Icons;
import org.glasspath.revenue.resources.Resources;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

public class OpenStreetMapDialog extends DefaultDialog {

	public OpenStreetMapDialog(FrameContext context, List<Coordinate> coordinates) {
		super(context);

		setTitle(Resources.getString("Route")); //$NON-NLS-1$
		getHeader().setIcon(Icons.mapXLarge);
		getHeader().setTitle(Resources.getString("Route")); //$NON-NLS-1$
		setPreferredSize(DIALOG_SIZE_LARGE);

		JXMapViewer mapViewer = new JXMapViewer() {

			@Override
			public void paint(Graphics g) {
				super.paint(g);

				Graphics2D g2d = (Graphics2D) g;

				String s = "© OpenStreetMap contributors"; //$NON-NLS-1$

				g2d.setFont(g2d.getFont().deriveFont(10.0F));
				FontMetrics fontMetrics = g2d.getFontMetrics();
				Rectangle2D fontRect = fontMetrics.getStringBounds(s, g2d);

				int w = (int) fontRect.getWidth() + 10;
				int h = (int) fontRect.getHeight() + 6;

				g2d.setColor(new Color(150, 150, 150, 150));
				g2d.fillRect(getWidth() - w, getHeight() - h, w, h);

				g2d.setColor(new Color(51, 51, 51));
				SwingUtils.drawString(this, g2d, s, getWidth() - w + 4, getHeight() - 6);

			}
		};
		mapViewer.setBorder(new HidpiMatteBorder(new Insets(1, 0, 1, 0), Theme.isDark() ? Color.black : Color.lightGray));

		DefaultTileFactory tileFactory = new DefaultTileFactory(new OSMTileFactoryInfo());
		tileFactory.setUserAgent(context.getClass().getName());
		tileFactory.setThreadPoolSize(2);
		mapViewer.setTileFactory(tileFactory);

		List<GeoPosition> route = new ArrayList<>();
		for (Coordinate coordinate : coordinates) {
			route.add(new GeoPosition(coordinate.getLatitude(), coordinate.getLongitude()));
		}

		RoutePainter routePainter = new RoutePainter(route);

		Coordinate initialCoordinate = coordinates.get(0);
		if (coordinates.size() > 3) {
			initialCoordinate = coordinates.get(coordinates.size() / 2);
		}
		GeoPosition geoPosition = new GeoPosition(initialCoordinate.getLatitude(), initialCoordinate.getLongitude());
		mapViewer.setZoom(9);
		mapViewer.setAddressLocation(geoPosition);

		MouseInputListener mouseInputListener = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mouseInputListener);
		mapViewer.addMouseMotionListener(mouseInputListener);

		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));

		SelectionAdapter selectionAdapter = new SelectionAdapter(mapViewer);
		SelectionPainter selectionPainter = new SelectionPainter(selectionAdapter);
		mapViewer.addMouseListener(selectionAdapter);
		mapViewer.addMouseMotionListener(selectionAdapter);

		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(routePainter);
		painters.add(selectionPainter);

		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
		mapViewer.setOverlayPainter(painter);

		getContentPanel().setBorder(BorderFactory.createEmptyBorder());
		getContentPanel().setLayout(new BorderLayout());
		getContentPanel().add(mapViewer, BorderLayout.CENTER);

		remove(getHeaderSeparator());
		remove(getFooterSeparator());
		getFooter().remove(getOkButton());
		getCancelButton().setText(CommonResources.getString("Close")); //$NON-NLS-1$

		pack();
		setLocationRelativeTo(context.getFrame());
		setVisible(true);

	}

	public static class RoutePainter implements Painter<JXMapViewer> {

		public static final Color ROUTE_STOKE_COLOR = new Color(25, 103, 210);
		public static final Color ROUTE_COLOR = new Color(102, 157, 246);

		private List<GeoPosition> route;

		public RoutePainter(List<GeoPosition> route) {
			this.route = route;
		}

		@Override
		public void paint(Graphics2D g2d, JXMapViewer map, int w, int h) {

			g2d = (Graphics2D) g2d.create(); // TODO?
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Rectangle rect = map.getViewportBounds();
			g2d.translate(-rect.x, -rect.y);

			g2d.setColor(ROUTE_STOKE_COLOR);
			g2d.setStroke(new BasicStroke(6));
			drawRoute(g2d, map);

			g2d.setColor(ROUTE_COLOR);
			g2d.setStroke(new BasicStroke(3));
			drawRoute(g2d, map);

			g2d.dispose(); // TODO?

		}

		private void drawRoute(Graphics2D g2d, JXMapViewer map) {

			int lastX = 0;
			int lastY = 0;

			boolean first = true;

			for (GeoPosition geoPosition : route) {

				Point2D p = map.getTileFactory().geoToPixel(geoPosition, map.getZoom());

				if (first) {
					first = false;
				} else {
					g2d.drawLine(lastX, lastY, (int) p.getX(), (int) p.getY());
				}

				lastX = (int) p.getX();
				lastY = (int) p.getY();

			}

		}

	}

	public static class SelectionAdapter extends MouseAdapter {

		private final JXMapViewer viewer;
		private boolean dragging = false;
		private Point2D startPos = new Point2D.Double();
		private Point2D endPos = new Point2D.Double();

		public SelectionAdapter(JXMapViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public void mousePressed(MouseEvent e) {

			if (e.getButton() != MouseEvent.BUTTON3) {
				return;
			}

			startPos.setLocation(e.getX(), e.getY());
			endPos.setLocation(e.getX(), e.getY());

			dragging = true;

		}

		@Override
		public void mouseDragged(MouseEvent e) {

			if (!dragging) {
				return;
			}

			endPos.setLocation(e.getX(), e.getY());

			viewer.repaint();

		}

		@Override
		public void mouseReleased(MouseEvent e) {

			if (!dragging) {
				return;
			}

			if (e.getButton() != MouseEvent.BUTTON3) {
				return;
			}

			viewer.repaint();

			dragging = false;

		}

		public Rectangle getRectangle() {

			if (dragging) {

				int x1 = (int) Math.min(startPos.getX(), endPos.getX());
				int y1 = (int) Math.min(startPos.getY(), endPos.getY());
				int x2 = (int) Math.max(startPos.getX(), endPos.getX());
				int y2 = (int) Math.max(startPos.getY(), endPos.getY());

				return new Rectangle(x1, y1, x2 - x1, y2 - y1);

			}

			return null;

		}

	}

	public static class SelectionPainter implements Painter<JXMapViewer> {

		public static final Color FILL_COLOR = new Color(128, 192, 255, 128);
		public static final Color STROKE_COLOR = new Color(0, 0, 255, 128);

		private final SelectionAdapter adapter;

		public SelectionPainter(SelectionAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public void paint(Graphics2D g, JXMapViewer t, int width, int height) {

			Rectangle rect = adapter.getRectangle();
			if (rect != null) {

				g.setColor(STROKE_COLOR);
				g.draw(rect);
				g.setColor(FILL_COLOR);
				g.fill(rect);

			}

		}

	}

}
