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
package org.glasspath.revenue.template.timesheet;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JMenu;

import org.glasspath.aerialist.AerialistUtils;
import org.glasspath.aerialist.Alignment;
import org.glasspath.aerialist.BorderType;
import org.glasspath.aerialist.FitPolicy;
import org.glasspath.aerialist.HeightPolicy;
import org.glasspath.aerialist.Image;
import org.glasspath.aerialist.Padding;
import org.glasspath.aerialist.Table;
import org.glasspath.aerialist.editor.DocumentEditorPanel;
import org.glasspath.aerialist.editor.actions.InsertElementAction;
import org.glasspath.aerialist.icons.Icons;
import org.glasspath.aerialist.swing.view.ColorUtils;
import org.glasspath.aerialist.template.TemplateMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.CategoryMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.FieldMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.TableMetadata;
import org.glasspath.revenue.template.KeyUtils;
import org.glasspath.revenue.template.TemplateUtils;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

@SuppressWarnings("nls")
public class TimeSheetTemplateUtils extends KeyUtils {

	// Ratio 3:2
	public static final int PREVIEW_IMAGE_WIDTH = 1880;
	public static final int PREVIEW_IMAGE_HEIGHT = 1253;

	private TimeSheetTemplateUtils() {

	}

	public static TemplateMetadata createTimeSheetTemplateMetadata() {

		TemplateMetadata templateMetadata = new TemplateMetadata();

		CategoryMetadata templateFields = new CategoryMetadata("");
		templateMetadata.setTemplateFields(templateFields);

		templateFields.getChildren().add(TemplateUtils.createMyCompanyTemplateMetadata());
		templateFields.getChildren().add(TemplateUtils.createClientTemplateMetadata());

		CategoryMetadata timesheetMetadata = new CategoryMetadata("Time sheet");
		templateFields.getChildren().add(timesheetMetadata);

		CategoryMetadata invoiceDetailsMetadata = new CategoryMetadata("Time sheet details");
		timesheetMetadata.getChildren().add(invoiceDetailsMetadata);

		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(YEAR), timeSheetKey(YEAR)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(WEEK_NUMBER), timeSheetKey(WEEK_NUMBER)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(HOURS), timeSheetTotalKey(HOURS)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(MILEAGE), timeSheetTotalKey(MILEAGE)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(CALLS), timeSheetTotalKey(CALLS)));

		timesheetMetadata.getChildren().add(createTotalsTableTemplateMetada());
		timesheetMetadata.getChildren().add(TemplateUtils.createHoursTableTemplateMetada());
		timesheetMetadata.getChildren().add(TemplateUtils.createMileagesTableTemplateMetada());
		timesheetMetadata.getChildren().add(TemplateUtils.createCallsTableTemplateMetada());

		CategoryMetadata visibilityFields = new CategoryMetadata("");
		templateMetadata.setVisibilityFields(visibilityFields);

		visibilityFields.getChildren().add(new FieldMetadata(description(HOURS_INCLUDED), key(HOURS_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(MILEAGE_INCLUDED), key(MILEAGE_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(CALLS_INCLUDED), key(CALLS_INCLUDED)));

		return templateMetadata;

	}

	public static TableMetadata createTotalsTableTemplateMetada() {

		TableMetadata totalsTableMetadata = new TableMetadata("Totals table");

		totalsTableMetadata.getChildren().add(new FieldMetadata(description(PROJECT), totalKey(PROJECT)));
		totalsTableMetadata.getChildren().add(new FieldMetadata(description(HOURS), totalKey(HOURS)));
		totalsTableMetadata.getChildren().add(new FieldMetadata(description(MILEAGE), totalKey(MILEAGE)));
		totalsTableMetadata.getChildren().add(new FieldMetadata(description(CALLS), totalKey(CALLS)));

		return totalsTableMetadata;

	}

	public static void populateInsertElementMenu(DocumentEditorPanel context, JMenu menu) {

		menu.add(TemplateUtils.createMyCompanyInsertMenu(context));

		JMenu timeSheetMenu = new JMenu("Time sheet");
		menu.add(timeSheetMenu);

		timeSheetMenu.add(new InsertElementAction(context, createTotalsTable(), "Totals table", Icons.tablePlus));
		timeSheetMenu.add(new InsertElementAction(context, createPreviewImage(), description(PREVIEW_IMAGE), Icons.imagePlus) {

			@Override
			public void actionPerformed(ActionEvent e) {
				context.getMediaCache().putImage(tKey(timeSheetKey(PREVIEW_IMAGE)), TemplateUtils.createImage(timeSheetKey(PREVIEW_IMAGE), PREVIEW_IMAGE_WIDTH / 3, PREVIEW_IMAGE_HEIGHT / 3, Color.black, new Color(250, 250, 250)));
				super.actionPerformed(e);
			}
		});
		timeSheetMenu.add(new InsertElementAction(context, createHoursTable(), "Hours table", Icons.tablePlus));
		timeSheetMenu.add(new InsertElementAction(context, createMileagesTable(), "Mileages table", Icons.tablePlus));
		timeSheetMenu.add(new InsertElementAction(context, createCallsTable(), "Calls table", Icons.tablePlus));

	}

	public static Image createPreviewImage() {

		Image image = new Image();
		image.setWidth(470);
		image.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		image.setFit(FitPolicy.WIDTH.stringValue);
		image.setSrc(tKey(timeSheetKey(PREVIEW_IMAGE)));

		return image;

	}

	public static Table createTotalsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 230));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 80));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 80));

		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell("Project", 1, 1, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Hours", 1, 2, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Mileage", 1, 3, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Calls", 1, 4, Alignment.RIGHT.stringValue, 11, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(PROJECT), 2, 1, Alignment.DEFAULT.stringValue, 9, false, tKey(totalKey(PROJECT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(HOURS), 2, 2, Alignment.RIGHT.stringValue, 9, false, tKey(totalKey(HOURS))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(MILEAGE), 2, 3, Alignment.RIGHT.stringValue, 9, false, tKey(totalKey(MILEAGE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(CALLS), 2, 4, Alignment.RIGHT.stringValue, 9, false, tKey(totalKey(CALLS))));

		table.getTableCells().add(AerialistUtils.createTableCell("Total", 3, 1, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(key(HOURS), 3, 2, Alignment.RIGHT.stringValue, 11, true, tKey(timeSheetTotalKey(HOURS))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(MILEAGE), 3, 3, Alignment.RIGHT.stringValue, 11, true, tKey(timeSheetTotalKey(MILEAGE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(CALLS), 3, 4, Alignment.RIGHT.stringValue, 11, true, tKey(timeSheetTotalKey(CALLS))));

		return table;

	}

	public static Table createHoursTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 80));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 160));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 50));

		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell("Date", 1, 1, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("From", 1, 2, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("To", 1, 3, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Project", 1, 4, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Total", 1, 5, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Comments", 1, 6, Alignment.DEFAULT.stringValue, 11, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell("Date", 2, 1, Alignment.DEFAULT.stringValue, 9, false, tKey(hourKey(DATE_FROM))));
		table.getTableCells().add(AerialistUtils.createTableCell("From", 2, 2, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(TIME_FROM))));
		table.getTableCells().add(AerialistUtils.createTableCell("To", 2, 3, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(TIME_TO))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(PROJECT), 2, 4, Alignment.DEFAULT.stringValue, 9, false, tKey(hourKey(PROJECT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 5, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(COMMENTS), 2, 6, Alignment.DEFAULT.stringValue, 9, false, tKey(hourKey(COMMENTS))));

		return table;

	}

	public static Table createMileagesTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 80));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 100));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 160));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 50));

		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell("Date", 1, 1, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Route", 1, 2, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Project", 1, 3, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Total", 1, 4, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Comments", 1, 5, Alignment.DEFAULT.stringValue, 11, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell("Date", 2, 1, Alignment.DEFAULT.stringValue, 9, false, tKey(mileageKey(DATE_FROM))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(ROUTE), 2, 2, Alignment.DEFAULT.stringValue, 9, false, tKey(mileageKey(ROUTE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(PROJECT), 2, 3, Alignment.DEFAULT.stringValue, 9, false, tKey(mileageKey(PROJECT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 4, Alignment.RIGHT.stringValue, 9, false, tKey(mileageKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(COMMENTS), 2, 5, Alignment.DEFAULT.stringValue, 9, false, tKey(mileageKey(COMMENTS))));

		return table;

	}

	public static Table createCallsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 80));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 100));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 110));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 50));

		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell("Date", 1, 1, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Time", 1, 2, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Name", 1, 3, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Company", 1, 4, Alignment.DEFAULT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Total", 1, 5, Alignment.RIGHT.stringValue, 11, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Comments", 1, 6, Alignment.DEFAULT.stringValue, 11, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(DATE), 2, 1, Alignment.DEFAULT.stringValue, 9, false, tKey(callKey(DATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TIME), 2, 2, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(TIME))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(NAME), 2, 3, Alignment.DEFAULT.stringValue, 9, false, tKey(callKey(NAME))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(COMPANY), 2, 4, Alignment.DEFAULT.stringValue, 9, false, tKey(callKey(COMPANY))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 5, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(COMMENTS), 2, 6, Alignment.DEFAULT.stringValue, 9, false, tKey(callKey(COMMENTS))));

		return table;

	}

	public static IXDocReport prepareXDocReport(File template) throws Exception {

		IXDocReport xDocReport = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(template), TemplateEngineKind.Velocity);

		FieldsMetadata metadata = xDocReport.createFieldsMetadata();
		metadata.addFieldAsList(totalKey(PROJECT));
		metadata.addFieldAsList(totalKey(HOURS));
		metadata.addFieldAsList(totalKey(MILEAGE));
		metadata.addFieldAsList(totalKey(CALLS));

		// XDocReport doesn't support dot in image key
		metadata.addFieldAsImage(timeSheetKey(PREVIEW_IMAGE).replace(".", ""));

		TemplateUtils.addHourXDocMetadata(metadata);
		TemplateUtils.addMileageXDocMetadata(metadata);
		TemplateUtils.addCallXDocMetadata(metadata);

		return xDocReport;

	}

}
