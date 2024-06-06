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
package org.glasspath.revenue.template.invoice;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JMenu;

import org.glasspath.aerialist.AerialistUtils;
import org.glasspath.aerialist.Alignment;
import org.glasspath.aerialist.BorderType;
import org.glasspath.aerialist.HeightPolicy;
import org.glasspath.aerialist.Padding;
import org.glasspath.aerialist.Table;
import org.glasspath.aerialist.TableCell;
import org.glasspath.aerialist.TextStyle;
import org.glasspath.aerialist.editor.DocumentEditorPanel;
import org.glasspath.aerialist.editor.actions.InsertElementAction;
import org.glasspath.aerialist.icons.Icons;
import org.glasspath.aerialist.swing.view.ColorUtils;
import org.glasspath.aerialist.template.TemplateMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.CategoryMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.FieldMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.TableMetadata;
import org.glasspath.common.swing.resources.CommonResources;
import org.glasspath.revenue.resources.Resources;
import org.glasspath.revenue.template.KeyUtils;
import org.glasspath.revenue.template.TemplateUtils;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

public class InvoiceTemplateUtils extends KeyUtils {

	private InvoiceTemplateUtils() {

	}

	public static TemplateMetadata createInvoiceTemplateMetadata() {

		TemplateMetadata templateMetadata = new TemplateMetadata();

		CategoryMetadata templateFields = new CategoryMetadata(""); //$NON-NLS-1$
		templateMetadata.setTemplateFields(templateFields);

		templateFields.getChildren().add(TemplateUtils.createMyCompanyTemplateMetadata());
		templateFields.getChildren().add(TemplateUtils.createClientTemplateMetadata());

		CategoryMetadata invoiceMetadata = new CategoryMetadata(Resources.getString("Invoice")); //$NON-NLS-1$
		templateFields.getChildren().add(invoiceMetadata);

		CategoryMetadata invoiceDetailsMetadata = new CategoryMetadata(Resources.getString("InvoiceDetails")); //$NON-NLS-1$
		invoiceMetadata.getChildren().add(invoiceDetailsMetadata);

		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(NUMBER), invoiceKey(NUMBER)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), invoiceKey(DESCRIPTION)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(REFERENCE), invoiceKey(REFERENCE)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(PERIOD), invoiceKey(PERIOD)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(COMMENTS), invoiceKey(COMMENTS)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(DATE), invoiceKey(DATE)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(DUE_DATE), invoiceKey(DUE_DATE)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_EXCL), invoiceKey(TOTAL_EXCL)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(VAT_RATE), invoiceKey(VAT_RATE)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(VAT), invoiceKey(VAT)));
		invoiceDetailsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_INCL), invoiceKey(TOTAL_INCL)));

		TableMetadata invoiceLinesTableMetadata = new TableMetadata(Resources.getString("InvoiceLinesTable")); //$NON-NLS-1$
		invoiceMetadata.getChildren().add(invoiceLinesTableMetadata);

		invoiceLinesTableMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), invoiceLineKey(DESCRIPTION)));
		invoiceLinesTableMetadata.getChildren().add(new FieldMetadata(description(QUANTITY), invoiceLineKey(QUANTITY)));
		invoiceLinesTableMetadata.getChildren().add(new FieldMetadata(description(PRICE), invoiceLineKey(PRICE)));
		invoiceLinesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), invoiceLineKey(AMOUNT)));
		invoiceLinesTableMetadata.getChildren().add(new FieldMetadata(description(VAT_RATE), invoiceLineKey(VAT_RATE)));
		invoiceLinesTableMetadata.getChildren().add(new FieldMetadata(description(VAT), invoiceLineKey(VAT)));

		TableMetadata vatRatesTableMetadata = new TableMetadata(Resources.getString("VATRatesTable")); //$NON-NLS-1$
		invoiceMetadata.getChildren().add(vatRatesTableMetadata);

		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), vatRateKey(DESCRIPTION)));
		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(RATE), vatRateKey(RATE)));
		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), vatRateKey(AMOUNT)));

		invoiceMetadata.getChildren().add(TemplateUtils.createHoursTableTemplateMetada());
		invoiceMetadata.getChildren().add(TemplateUtils.createMileagesTableTemplateMetada());
		invoiceMetadata.getChildren().add(TemplateUtils.createCallsTableTemplateMetada());

		CategoryMetadata visibilityFields = new CategoryMetadata(""); //$NON-NLS-1$
		templateMetadata.setVisibilityFields(visibilityFields);

		visibilityFields.getChildren().add(new FieldMetadata(description(HOURS_INCLUDED), key(HOURS_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(MILEAGE_INCLUDED), key(MILEAGE_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(CALLS_INCLUDED), key(CALLS_INCLUDED)));

		return templateMetadata;

	}

	public static void populateInsertElementMenu(DocumentEditorPanel context, JMenu menu) {

		menu.add(TemplateUtils.createMyCompanyInsertMenu(context));

		JMenu invoiceMenu = new JMenu(Resources.getString("Invoice")); //$NON-NLS-1$
		menu.add(invoiceMenu);

		invoiceMenu.add(new InsertElementAction(context, createInvoiceDetailsTable(), Resources.getString("InvoiceDetailsTable"), Icons.tablePlus)); //$NON-NLS-1$
		invoiceMenu.add(new InsertElementAction(context, createInvoiceLinesTable(), Resources.getString("InvoiceLinesTable"), Icons.tablePlus)); //$NON-NLS-1$
		invoiceMenu.add(new InsertElementAction(context, createInvoiceTotalsTable(), Resources.getString("InvoiceTotalsTable"), Icons.tablePlus)); //$NON-NLS-1$

		invoiceMenu.addSeparator();

		invoiceMenu.add(new InsertElementAction(context, createHoursTable(), Resources.getString("HoursTable"), Icons.tablePlus)); //$NON-NLS-1$
		invoiceMenu.add(new InsertElementAction(context, createMileagesTable(), Resources.getString("MileagesTable"), Icons.tablePlus)); //$NON-NLS-1$
		invoiceMenu.add(new InsertElementAction(context, createCallsTable(), Resources.getString("CallsTable"), Icons.tablePlus)); //$NON-NLS-1$

	}

	public static Table createInvoiceDetailsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(1, 3, 1, 3));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 120));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 140));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 100));

		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("InvoiceNumber") + ":", 1, 1, Alignment.DEFAULT.stringValue, 12, false, null)); //$NON-NLS-1$ //$NON-NLS-2$
		table.getTableCells().add(AerialistUtils.createTableCell(invoiceKey(NUMBER), 1, 2, Alignment.DEFAULT.stringValue, 12, false, tKey(invoiceKey(NUMBER))));
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Period") + ":", 1, 3, Alignment.DEFAULT.stringValue, 12, false, null)); //$NON-NLS-1$ //$NON-NLS-2$
		table.getTableCells().add(AerialistUtils.createTableCell(invoiceKey(PERIOD), 1, 4, Alignment.RIGHT.stringValue, 12, false, tKey(invoiceKey(PERIOD))));

		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("ClientNumber") + ":", 2, 1, Alignment.DEFAULT.stringValue, 12, false, null)); //$NON-NLS-1$ //$NON-NLS-2$
		table.getTableCells().add(AerialistUtils.createTableCell(clientKey(NUMBER), 2, 2, Alignment.DEFAULT.stringValue, 12, false, tKey(clientKey(FORMATTED_NUMBER))));
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("InvoiceDate") + ":", 2, 3, Alignment.DEFAULT.stringValue, 12, false, null)); //$NON-NLS-1$ //$NON-NLS-2$
		table.getTableCells().add(AerialistUtils.createTableCell(invoiceKey(DATE), 2, 4, Alignment.RIGHT.stringValue, 12, false, tKey(invoiceKey(DATE))));

		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Reference") + ":", 3, 1, Alignment.DEFAULT.stringValue, 12, false, null)); //$NON-NLS-1$ //$NON-NLS-2$
		table.getTableCells().add(AerialistUtils.createTableCell(invoiceKey(REFERENCE), 3, 2, Alignment.DEFAULT.stringValue, 12, false, tKey(invoiceKey(REFERENCE))));
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("DueDate") + ":", 3, 3, Alignment.DEFAULT.stringValue, 12, false, null)); //$NON-NLS-1$ //$NON-NLS-2$
		table.getTableCells().add(AerialistUtils.createTableCell(invoiceKey(DUE_DATE), 3, 4, Alignment.RIGHT.stringValue, 12, false, tKey(invoiceKey(DUE_DATE))));

		return table;

	}

	public static Table createInvoiceLinesTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 120));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 70));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 70));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 70));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 70));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Description"), 1, 1, Alignment.DEFAULT.stringValue, 12, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Quantity"), 1, 2, Alignment.RIGHT.stringValue, 12, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Price"), 1, 3, Alignment.RIGHT.stringValue, 12, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("VatRate"), 1, 4, Alignment.RIGHT.stringValue, 12, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Vat"), 1, 5, Alignment.RIGHT.stringValue, 12, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Amount"), 1, 6, Alignment.RIGHT.stringValue, 12, true, null)); //$NON-NLS-1$

		table.getTableCells().add(AerialistUtils.createTableCell(key(DESCRIPTION), 2, 1, Alignment.DEFAULT.stringValue, 11, false, tKey(invoiceLineKey(DESCRIPTION))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(QUANTITY), 2, 2, Alignment.RIGHT.stringValue, 11, false, tKey(invoiceLineKey(QUANTITY))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(PRICE), 2, 3, Alignment.RIGHT.stringValue, 11, false, tKey(invoiceLineKey(PRICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(VAT_RATE), 2, 4, Alignment.RIGHT.stringValue, 11, false, tKey(invoiceLineKey(VAT_RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(VAT), 2, 5, Alignment.RIGHT.stringValue, 11, false, tKey(invoiceLineKey(VAT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 6, Alignment.RIGHT.stringValue, 11, false, tKey(invoiceLineKey(AMOUNT))));

		return table;

	}

	public static Table createInvoiceTotalsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(1, 3, 1, 3));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 360));

		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Subtotal"), 1, 1, Alignment.RIGHT.stringValue, 12, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell("###", 1, 2, Alignment.RIGHT.stringValue, 12, true, tKey(invoiceKey(TOTAL_EXCL)))); //$NON-NLS-1$

		TableCell tableCell = new TableCell();
		tableCell.setText(Resources.getString("Vat") + " ??"); //$NON-NLS-1$ //$NON-NLS-2$
		tableCell.setRow(2);
		tableCell.setCol(1);
		tableCell.setAlignment(Alignment.RIGHT.stringValue);

		TextStyle textStyle = new TextStyle();
		textStyle.start = 0;
		textStyle.end = 4;
		textStyle.fontSize = 12;
		textStyle.bold = true;
		tableCell.getStyles().add(textStyle);

		textStyle = new TextStyle();
		textStyle.start = 4;
		textStyle.end = 6;
		textStyle.fontSize = 12;
		textStyle.bold = true;
		textStyle.source = tKey(vatRateKey(RATE));
		tableCell.getStyles().add(textStyle);

		table.getTableCells().add(tableCell);
		table.getTableCells().add(AerialistUtils.createTableCell("###", 2, 2, Alignment.RIGHT.stringValue, 12, true, tKey(vatRateKey(AMOUNT)))); //$NON-NLS-1$

		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Total"), 3, 1, Alignment.RIGHT.stringValue, 12, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell("###", 3, 2, Alignment.RIGHT.stringValue, 12, true, tKey(invoiceKey(TOTAL_INCL)))); //$NON-NLS-1$

		return table;

	}

	public static Table createHoursTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 90));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 40));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 40));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 70));
		table.getColStyles().add(AerialistUtils.createColStyle(6, 70));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Date"), 1, 1, Alignment.DEFAULT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("From"), 1, 2, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("To"), 1, 3, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Total"), 1, 4, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Rate"), 1, 5, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Amount"), 1, 6, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Comments"), 1, 7, Alignment.DEFAULT.stringValue, 11, true, null)); //$NON-NLS-1$

		table.getTableCells().add(AerialistUtils.createTableCell("Date", 2, 1, Alignment.DEFAULT.stringValue, 9, false, tKey(hourKey(DATE_FROM)))); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell("From", 2, 2, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(TIME_FROM)))); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell("To", 2, 3, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(TIME_TO)))); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 4, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(RATE), 2, 5, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 6, Alignment.RIGHT.stringValue, 9, false, tKey(hourKey(AMOUNT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(COMMENTS), 2, 7, Alignment.DEFAULT.stringValue, 9, false, tKey(hourKey(COMMENTS))));

		return table;

	}

	public static Table createMileagesTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 90));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 80));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 70));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 70));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Date"), 1, 1, Alignment.DEFAULT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Route"), 1, 2, Alignment.DEFAULT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Total"), 1, 3, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Rate"), 1, 4, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Amount"), 1, 5, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Comments"), 1, 6, Alignment.DEFAULT.stringValue, 11, true, null)); //$NON-NLS-1$

		table.getTableCells().add(AerialistUtils.createTableCell("Date", 2, 1, Alignment.DEFAULT.stringValue, 9, false, tKey(mileageKey(DATE_FROM)))); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(key(ROUTE), 2, 2, Alignment.DEFAULT.stringValue, 9, false, tKey(mileageKey(ROUTE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 3, Alignment.RIGHT.stringValue, 9, false, tKey(mileageKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(RATE), 2, 4, Alignment.RIGHT.stringValue, 9, false, tKey(mileageKey(RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 5, Alignment.RIGHT.stringValue, 9, false, tKey(mileageKey(AMOUNT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(COMMENTS), 2, 6, Alignment.DEFAULT.stringValue, 9, false, tKey(mileageKey(COMMENTS))));

		return table;

	}

	public static Table createCallsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 70));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 40));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(6, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(7, 60));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Date"), 1, 1, Alignment.DEFAULT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Time"), 1, 2, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Name"), 1, 3, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Number"), 1, 4, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Total"), 1, 5, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Rate"), 1, 6, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(Resources.getString("Amount"), 1, 7, Alignment.RIGHT.stringValue, 11, true, null)); //$NON-NLS-1$
		table.getTableCells().add(AerialistUtils.createTableCell(CommonResources.getString("Comments"), 1, 8, Alignment.DEFAULT.stringValue, 11, true, null)); //$NON-NLS-1$

		table.getTableCells().add(AerialistUtils.createTableCell(key(DATE), 2, 1, Alignment.DEFAULT.stringValue, 9, false, tKey(callKey(DATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TIME), 2, 2, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(TIME))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(NAME), 2, 3, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(NAME))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(NUMBER), 2, 4, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(NUMBER))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 5, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(RATE), 2, 6, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 7, Alignment.RIGHT.stringValue, 9, false, tKey(callKey(AMOUNT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(COMMENTS), 2, 8, Alignment.DEFAULT.stringValue, 9, false, tKey(callKey(COMMENTS))));

		return table;

	}

	public static IXDocReport prepareXDocReport(File template) throws Exception {

		IXDocReport xDocReport = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(template), TemplateEngineKind.Velocity);

		FieldsMetadata metadata = xDocReport.createFieldsMetadata();
		metadata.addFieldAsList(invoiceLineKey(DESCRIPTION));
		metadata.addFieldAsList(invoiceLineKey(QUANTITY));
		metadata.addFieldAsList(invoiceLineKey(PRICE));
		metadata.addFieldAsList(invoiceLineKey(AMOUNT));
		metadata.addFieldAsList(invoiceLineKey(VAT_RATE));
		metadata.addFieldAsList(invoiceLineKey(VAT));

		metadata.addFieldAsList(vatRateKey(DESCRIPTION));
		metadata.addFieldAsList(vatRateKey(RATE));
		metadata.addFieldAsList(vatRateKey(AMOUNT));

		TemplateUtils.addHourXDocMetadata(metadata);
		TemplateUtils.addMileageXDocMetadata(metadata);
		TemplateUtils.addCallXDocMetadata(metadata);

		return xDocReport;

	}

}
