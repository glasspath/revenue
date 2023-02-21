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
package org.glasspath.revenue.template.report;

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
import org.glasspath.revenue.template.KeyUtils;
import org.glasspath.revenue.template.TemplateUtils;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

@SuppressWarnings("nls")
public class ReportTemplateUtils extends KeyUtils {

	private ReportTemplateUtils() {

	}

	public static TemplateMetadata createReportTemplateMetadata() {

		TemplateMetadata templateMetadata = new TemplateMetadata();

		CategoryMetadata templateFields = new CategoryMetadata("");
		templateMetadata.setTemplateFields(templateFields);

		templateFields.getChildren().add(TemplateUtils.createMyCompanyTemplateMetadata());
		templateFields.getChildren().add(TemplateUtils.createClientTemplateMetadata());

		CategoryMetadata reportMetadata = new CategoryMetadata("Report");
		templateFields.getChildren().add(reportMetadata);

		CategoryMetadata reportDetailsMetadata = new CategoryMetadata("Report details");
		reportMetadata.getChildren().add(reportDetailsMetadata);

		reportDetailsMetadata.getChildren().add(new FieldMetadata(description(FROM), reportKey(FROM)));
		reportDetailsMetadata.getChildren().add(new FieldMetadata(description(TO), reportKey(TO)));

		reportMetadata.getChildren().add(createInvoicesTableTemplateMetada());
		reportMetadata.getChildren().add(createInvoiceTotalsTemplateMetada());
		reportMetadata.getChildren().add(createExpensesTableTemplateMetada());
		reportMetadata.getChildren().add(createExpenseTotalsTemplateMetada());
		reportMetadata.getChildren().add(createQuotesTableTemplateMetada());
		reportMetadata.getChildren().add(createHoursTableTemplateMetada());
		reportMetadata.getChildren().add(createHourTotalsTemplateMetada());
		reportMetadata.getChildren().add(createMileageTableTemplateMetada());
		reportMetadata.getChildren().add(createMileageTotalsTemplateMetada());
		reportMetadata.getChildren().add(createCallsTableTemplateMetada());
		reportMetadata.getChildren().add(createCallTotalsTemplateMetada());

		CategoryMetadata visibilityFields = new CategoryMetadata("");
		templateMetadata.setVisibilityFields(visibilityFields);

		visibilityFields.getChildren().add(new FieldMetadata(description(INVOICES_INCLUDED), key(INVOICES_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(EXPENSES_INCLUDED), key(EXPENSES_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(QUOTES_INCLUDED), key(QUOTES_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(HOURS_INCLUDED), key(HOURS_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(MILEAGE_INCLUDED), key(MILEAGE_INCLUDED)));
		visibilityFields.getChildren().add(new FieldMetadata(description(CALLS_INCLUDED), key(CALLS_INCLUDED)));

		return templateMetadata;

	}

	public static TableMetadata createInvoicesTableTemplateMetada() {

		TableMetadata invoicesTableMetadata = new TableMetadata("Invoices table");

		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(NUMBER), invoiceKey(NUMBER)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(CLIENT), invoiceKey(CLIENT)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), invoiceKey(DESCRIPTION)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(DATE), invoiceKey(DATE)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(DUE_DATE), invoiceKey(DUE_DATE)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(STATUS), invoiceKey(STATUS)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), invoiceKey(AMOUNT)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(VAT_RATE), invoiceKey(VAT_RATE)));
		invoicesTableMetadata.getChildren().add(new FieldMetadata(description(VAT), invoiceKey(VAT)));

		return invoicesTableMetadata;

	}

	public static CategoryMetadata createInvoiceTotalsTemplateMetada() {

		CategoryMetadata invoiceTotalsMetadata = new CategoryMetadata("Invoice totals");

		invoiceTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_EXCL), invoicesKey(TOTAL_EXCL)));
		invoiceTotalsMetadata.getChildren().add(new FieldMetadata(description(VAT), invoicesKey(VAT)));
		invoiceTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_INCL), invoicesKey(TOTAL_INCL)));

		TableMetadata vatRatesTableMetadata = new TableMetadata("VAT rates table");
		invoiceTotalsMetadata.getChildren().add(vatRatesTableMetadata);

		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), invoicesVatRateKey(DESCRIPTION)));
		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(RATE), invoicesVatRateKey(RATE)));
		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), invoicesVatRateKey(AMOUNT)));

		return invoiceTotalsMetadata;

	}

	public static TableMetadata createExpensesTableTemplateMetada() {

		TableMetadata expensesTableMetadata = new TableMetadata("Expenses table");

		expensesTableMetadata.getChildren().add(new FieldMetadata(description(NUMBER), expenseKey(NUMBER)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(SUPPLIER), expenseKey(SUPPLIER)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), expenseKey(DESCRIPTION)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(DATE), expenseKey(DATE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(DUE_DATE), expenseKey(DUE_DATE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(STATUS), expenseKey(STATUS)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), expenseKey(AMOUNT)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(VAT_RATE), expenseKey(VAT_RATE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(VAT), expenseKey(VAT)));

		return expensesTableMetadata;

	}

	public static CategoryMetadata createExpenseTotalsTemplateMetada() {

		CategoryMetadata expenseTotalsMetadata = new CategoryMetadata("Expense totals");

		expenseTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_EXCL), expensesKey(TOTAL_EXCL)));
		expenseTotalsMetadata.getChildren().add(new FieldMetadata(description(VAT), expensesKey(VAT)));
		expenseTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_INCL), expensesKey(TOTAL_INCL)));

		TableMetadata vatRatesTableMetadata = new TableMetadata("VAT rates table");
		expenseTotalsMetadata.getChildren().add(vatRatesTableMetadata);

		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), expensesVatRateKey(DESCRIPTION)));
		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(RATE), expensesVatRateKey(RATE)));
		vatRatesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), expensesVatRateKey(AMOUNT)));

		return expenseTotalsMetadata;

	}

	public static TableMetadata createQuotesTableTemplateMetada() {

		TableMetadata expensesTableMetadata = new TableMetadata("Quotes table");

		expensesTableMetadata.getChildren().add(new FieldMetadata(description(NUMBER), quoteKey(NUMBER)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(CLIENT), quoteKey(CLIENT)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(DESCRIPTION), quoteKey(DESCRIPTION)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(DATE), quoteKey(DATE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(VALID_UNTIL_DATE), quoteKey(VALID_UNTIL_DATE)));

		return expensesTableMetadata;

	}

	public static TableMetadata createHoursTableTemplateMetada() {

		TableMetadata expensesTableMetadata = new TableMetadata("Hours table");

		expensesTableMetadata.getChildren().add(new FieldMetadata(description(CLIENT), hourKey(CLIENT)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(PROJECT), hourKey(PROJECT)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(RATE), hourKey(RATE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL), hourKey(TOTAL)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), hourKey(TOTAL_FOR_INVOICE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), hourKey(AMOUNT)));

		return expensesTableMetadata;

	}

	public static CategoryMetadata createHourTotalsTemplateMetada() {

		CategoryMetadata hourTotalsMetadata = new CategoryMetadata("Hour totals");

		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL), hoursKey(TOTAL)));
		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), hoursKey(TOTAL_FOR_INVOICE)));
		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), hoursKey(AMOUNT)));

		return hourTotalsMetadata;

	}

	public static TableMetadata createMileageTableTemplateMetada() {

		TableMetadata expensesTableMetadata = new TableMetadata("Mileage table");

		expensesTableMetadata.getChildren().add(new FieldMetadata(description(CLIENT), mileageKey(CLIENT)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(PROJECT), mileageKey(PROJECT)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(RATE), mileageKey(RATE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL), mileageKey(TOTAL)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), mileageKey(TOTAL_FOR_INVOICE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), mileageKey(AMOUNT)));

		return expensesTableMetadata;

	}

	public static CategoryMetadata createMileageTotalsTemplateMetada() {

		CategoryMetadata hourTotalsMetadata = new CategoryMetadata("Mileage totals");

		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL), mileagesKey(TOTAL)));
		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), mileagesKey(TOTAL_FOR_INVOICE)));
		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), mileagesKey(AMOUNT)));

		return hourTotalsMetadata;

	}

	public static TableMetadata createCallsTableTemplateMetada() {

		TableMetadata expensesTableMetadata = new TableMetadata("Calls table");

		expensesTableMetadata.getChildren().add(new FieldMetadata(description(COMPANY), callKey(COMPANY)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(RATE), callKey(RATE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL), callKey(TOTAL)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), callKey(TOTAL_FOR_INVOICE)));
		expensesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), callKey(AMOUNT)));

		return expensesTableMetadata;

	}

	public static CategoryMetadata createCallTotalsTemplateMetada() {

		CategoryMetadata hourTotalsMetadata = new CategoryMetadata("Call totals");

		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL), callsKey(TOTAL)));
		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), callsKey(TOTAL_FOR_INVOICE)));
		hourTotalsMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), callsKey(AMOUNT)));

		return hourTotalsMetadata;

	}

	public static void populateInsertElementMenu(DocumentEditorPanel context, JMenu menu) {

		menu.add(TemplateUtils.createMyCompanyInsertMenu(context));

		JMenu reportMenu = new JMenu("Report");
		menu.add(reportMenu);

		reportMenu.add(new InsertElementAction(context, createInvoicesTable(), "Invoices table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createInvoiceTotalsTable(), "Invoice totals table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createExpensesTable(), "Expenses table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createExpenseTotalsTable(), "Expense totals table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createQuotesTable(), "Quotes table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createHoursTable(), "Hours table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createHourTotalsTable(), "Hour totals table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createMileageTable(), "Mileage table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createMileageTotalsTable(), "Mileage totals table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createCallsTable(), "Calls table", Icons.tablePlus));
		reportMenu.add(new InsertElementAction(context, createCallTotalsTable(), "Call totals table", Icons.tablePlus));

	}

	public static Table createInvoicesTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 40));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 90));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(6, 45));
		table.getColStyles().add(AerialistUtils.createColStyle(7, 45));
		table.getColStyles().add(AerialistUtils.createColStyle(8, 45));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell("Number", 1, 1, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Client", 1, 2, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Description", 1, 3, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Invoice date", 1, 4, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Due date", 1, 5, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Status", 1, 6, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Amount", 1, 7, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("VAT rate", 1, 8, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("VAT", 1, 9, Alignment.RIGHT.stringValue, 9, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(NUMBER), 2, 1, Alignment.DEFAULT.stringValue, 8, false, tKey(invoiceKey(NUMBER))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(CLIENT), 2, 2, Alignment.DEFAULT.stringValue, 8, false, tKey(invoiceKey(CLIENT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DESCRIPTION), 2, 3, Alignment.DEFAULT.stringValue, 8, false, tKey(invoiceKey(DESCRIPTION))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DATE), 2, 4, Alignment.DEFAULT.stringValue, 8, false, tKey(invoiceKey(DATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DUE_DATE), 2, 5, Alignment.DEFAULT.stringValue, 8, false, tKey(invoiceKey(DUE_DATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(STATUS), 2, 6, Alignment.DEFAULT.stringValue, 8, false, tKey(invoiceKey(STATUS))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 7, Alignment.RIGHT.stringValue, 8, false, tKey(invoiceKey(AMOUNT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(VAT_RATE), 2, 8, Alignment.RIGHT.stringValue, 8, false, tKey(invoiceKey(VAT_RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(VAT), 2, 9, Alignment.RIGHT.stringValue, 8, false, tKey(invoiceKey(VAT))));

		return table;

	}

	public static Table createInvoiceTotalsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(1, 3, 1, 3));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 360));

		table.getTableCells().add(AerialistUtils.createTableCell("Subtotal", 1, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 1, 2, Alignment.RIGHT.stringValue, 12, true, tKey(invoicesKey(TOTAL_EXCL))));

		TableCell tableCell = new TableCell();
		tableCell.setText("VAT ??");
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
		textStyle.source = tKey(invoicesVatRateKey(RATE));
		tableCell.getStyles().add(textStyle);

		table.getTableCells().add(tableCell);
		table.getTableCells().add(AerialistUtils.createTableCell("###", 2, 2, Alignment.RIGHT.stringValue, 12, true, tKey(invoicesVatRateKey(AMOUNT))));

		table.getTableCells().add(AerialistUtils.createTableCell("Total", 3, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 3, 2, Alignment.RIGHT.stringValue, 12, true, tKey(invoicesKey(TOTAL_INCL))));

		return table;

	}

	public static Table createExpensesTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 40));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 90));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(6, 45));
		table.getColStyles().add(AerialistUtils.createColStyle(7, 45));
		table.getColStyles().add(AerialistUtils.createColStyle(8, 45));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell("Number", 1, 1, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Supplier", 1, 2, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Description", 1, 3, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Invoice date", 1, 4, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Due date", 1, 5, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Status", 1, 6, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Amount", 1, 7, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("VAT rate", 1, 8, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("VAT", 1, 9, Alignment.RIGHT.stringValue, 9, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(NUMBER), 2, 1, Alignment.DEFAULT.stringValue, 8, false, tKey(expenseKey(NUMBER))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(SUPPLIER), 2, 2, Alignment.DEFAULT.stringValue, 8, false, tKey(expenseKey(SUPPLIER))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DESCRIPTION), 2, 3, Alignment.DEFAULT.stringValue, 8, false, tKey(expenseKey(DESCRIPTION))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DATE), 2, 4, Alignment.DEFAULT.stringValue, 8, false, tKey(expenseKey(DATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DUE_DATE), 2, 5, Alignment.DEFAULT.stringValue, 8, false, tKey(expenseKey(DUE_DATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(STATUS), 2, 6, Alignment.DEFAULT.stringValue, 8, false, tKey(expenseKey(STATUS))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 7, Alignment.RIGHT.stringValue, 8, false, tKey(expenseKey(AMOUNT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(VAT_RATE), 2, 8, Alignment.RIGHT.stringValue, 8, false, tKey(expenseKey(VAT_RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(VAT), 2, 9, Alignment.RIGHT.stringValue, 8, false, tKey(expenseKey(VAT))));

		return table;

	}

	public static Table createExpenseTotalsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(1, 3, 1, 3));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 360));

		table.getTableCells().add(AerialistUtils.createTableCell("Subtotal", 1, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 1, 2, Alignment.RIGHT.stringValue, 12, true, tKey(expensesKey(TOTAL_EXCL))));

		TableCell tableCell = new TableCell();
		tableCell.setText("VAT ??");
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
		textStyle.source = tKey(expensesVatRateKey(RATE));
		tableCell.getStyles().add(textStyle);

		table.getTableCells().add(tableCell);
		table.getTableCells().add(AerialistUtils.createTableCell("###", 2, 2, Alignment.RIGHT.stringValue, 12, true, tKey(expensesVatRateKey(AMOUNT))));

		table.getTableCells().add(AerialistUtils.createTableCell("Total", 3, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 3, 2, Alignment.RIGHT.stringValue, 12, true, tKey(expensesKey(TOTAL_INCL))));

		return table;

	}

	public static Table createQuotesTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 50));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 150));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 150));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 60));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell("Number", 1, 1, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Client", 1, 2, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Description", 1, 3, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Quote date", 1, 4, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("Valid until", 1, 5, Alignment.DEFAULT.stringValue, 9, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(NUMBER), 2, 1, Alignment.DEFAULT.stringValue, 8, false, tKey(quoteKey(NUMBER))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(CLIENT), 2, 2, Alignment.DEFAULT.stringValue, 8, false, tKey(quoteKey(CLIENT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DESCRIPTION), 2, 3, Alignment.DEFAULT.stringValue, 8, false, tKey(quoteKey(DESCRIPTION))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DATE), 2, 4, Alignment.DEFAULT.stringValue, 8, false, tKey(quoteKey(DATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(DATE), 2, 5, Alignment.DEFAULT.stringValue, 8, false, tKey(quoteKey(VALID_UNTIL_DATE))));

		return table;

	}

	public static Table createHoursTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 115));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 115));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 60));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell(description(CLIENT), 1, 1, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(PROJECT), 1, 2, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(RATE), 1, 3, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL), 1, 4, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL_FOR_INVOICE), 1, 5, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(AMOUNT), 1, 6, Alignment.RIGHT.stringValue, 9, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(CLIENT), 2, 1, Alignment.DEFAULT.stringValue, 8, false, tKey(hourKey(CLIENT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(PROJECT), 2, 2, Alignment.DEFAULT.stringValue, 8, false, tKey(hourKey(PROJECT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(RATE), 2, 3, Alignment.DEFAULT.stringValue, 8, false, tKey(hourKey(RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 4, Alignment.RIGHT.stringValue, 8, false, tKey(hourKey(TOTAL))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 5, Alignment.RIGHT.stringValue, 8, false, tKey(hourKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 6, Alignment.RIGHT.stringValue, 8, false, tKey(hourKey(AMOUNT))));

		return table;

	}

	public static Table createHourTotalsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(1, 3, 1, 3));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 360));

		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL), 1, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 1, 2, Alignment.RIGHT.stringValue, 12, true, tKey(hoursKey(TOTAL))));

		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL_FOR_INVOICE), 2, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 2, 2, Alignment.RIGHT.stringValue, 12, true, tKey(hoursKey(TOTAL_FOR_INVOICE))));

		table.getTableCells().add(AerialistUtils.createTableCell(description(AMOUNT), 3, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 3, 2, Alignment.RIGHT.stringValue, 12, true, tKey(hoursKey(AMOUNT))));

		return table;

	}

	public static Table createMileageTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 115));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 115));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(5, 60));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell(description(CLIENT), 1, 1, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(PROJECT), 1, 2, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(RATE), 1, 3, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL), 1, 4, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL_FOR_INVOICE), 1, 5, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(AMOUNT), 1, 6, Alignment.RIGHT.stringValue, 9, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(CLIENT), 2, 1, Alignment.DEFAULT.stringValue, 8, false, tKey(mileageKey(CLIENT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(PROJECT), 2, 2, Alignment.DEFAULT.stringValue, 8, false, tKey(mileageKey(PROJECT))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(RATE), 2, 3, Alignment.DEFAULT.stringValue, 8, false, tKey(mileageKey(RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 4, Alignment.RIGHT.stringValue, 8, false, tKey(mileageKey(TOTAL))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 5, Alignment.RIGHT.stringValue, 8, false, tKey(mileageKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 6, Alignment.RIGHT.stringValue, 8, false, tKey(mileageKey(AMOUNT))));

		return table;

	}

	public static Table createMileageTotalsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(1, 3, 1, 3));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 360));

		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL), 1, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 1, 2, Alignment.RIGHT.stringValue, 12, true, tKey(mileagesKey(TOTAL))));

		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL_FOR_INVOICE), 2, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 2, 2, Alignment.RIGHT.stringValue, 12, true, tKey(mileagesKey(TOTAL_FOR_INVOICE))));

		table.getTableCells().add(AerialistUtils.createTableCell(description(AMOUNT), 3, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 3, 2, Alignment.RIGHT.stringValue, 12, true, tKey(mileagesKey(AMOUNT))));

		return table;

	}

	public static Table createCallsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeaderRows(1);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(3));
		table.getBorders().add(AerialistUtils.createBorder(BorderType.VERTICAL, 1, TemplateUtils.TABLE_VERTICAL_LINE_COLOR));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 230));
		table.getColStyles().add(AerialistUtils.createColStyle(2, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(3, 60));
		table.getColStyles().add(AerialistUtils.createColStyle(4, 60));

		table.getRowStyles().add(AerialistUtils.createRowStyle(0, 0, ColorUtils.toHex(TemplateUtils.TABLE_HEADER_ROW_COLOR)));
		table.getRowStyles().add(AerialistUtils.createRowStyle(1, 2, ColorUtils.toHex(TemplateUtils.TABLE_ALTERNATING_ROW_COLOR)));

		table.getTableCells().add(AerialistUtils.createTableCell(description(COMPANY), 1, 1, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(RATE), 1, 2, Alignment.DEFAULT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL), 1, 3, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL_FOR_INVOICE), 1, 4, Alignment.RIGHT.stringValue, 9, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell(description(AMOUNT), 1, 5, Alignment.RIGHT.stringValue, 9, true, null));

		table.getTableCells().add(AerialistUtils.createTableCell(key(COMPANY), 2, 1, Alignment.DEFAULT.stringValue, 8, false, tKey(callKey(COMPANY))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(RATE), 2, 2, Alignment.DEFAULT.stringValue, 8, false, tKey(callKey(RATE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 3, Alignment.RIGHT.stringValue, 8, false, tKey(callKey(TOTAL))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(TOTAL), 2, 4, Alignment.RIGHT.stringValue, 8, false, tKey(callKey(TOTAL_FOR_INVOICE))));
		table.getTableCells().add(AerialistUtils.createTableCell(key(AMOUNT), 2, 5, Alignment.RIGHT.stringValue, 8, false, tKey(callKey(AMOUNT))));

		return table;

	}

	public static Table createCallTotalsTable() {

		Table table = new Table();
		table.setWidth(470);
		table.setHeightPolicy(HeightPolicy.AUTO.stringValue);
		table.setCellPadding(Padding.from(1, 3, 1, 3));

		table.getColStyles().add(AerialistUtils.createColStyle(1, 360));

		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL), 1, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 1, 2, Alignment.RIGHT.stringValue, 12, true, tKey(callsKey(TOTAL))));

		table.getTableCells().add(AerialistUtils.createTableCell(description(TOTAL_FOR_INVOICE), 2, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 2, 2, Alignment.RIGHT.stringValue, 12, true, tKey(callsKey(TOTAL_FOR_INVOICE))));

		table.getTableCells().add(AerialistUtils.createTableCell(description(AMOUNT), 3, 1, Alignment.RIGHT.stringValue, 12, true, null));
		table.getTableCells().add(AerialistUtils.createTableCell("###", 3, 2, Alignment.RIGHT.stringValue, 12, true, tKey(callsKey(AMOUNT))));

		return table;

	}

	public static IXDocReport prepareXDocReport(File template) throws Exception {

		IXDocReport xDocReport = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(template), TemplateEngineKind.Velocity);

		FieldsMetadata metadata = xDocReport.createFieldsMetadata();
		metadata.addFieldAsList(invoiceKey(NUMBER));
		metadata.addFieldAsList(invoiceKey(CLIENT));
		metadata.addFieldAsList(invoiceKey(DESCRIPTION));
		metadata.addFieldAsList(invoiceKey(DATE));
		metadata.addFieldAsList(invoiceKey(DUE_DATE));
		metadata.addFieldAsList(invoiceKey(STATUS));
		metadata.addFieldAsList(invoiceKey(AMOUNT));
		metadata.addFieldAsList(invoiceKey(VAT_RATE));
		metadata.addFieldAsList(invoiceKey(VAT));

		metadata.addFieldAsList(invoicesVatRateKey(DESCRIPTION));
		metadata.addFieldAsList(invoicesVatRateKey(RATE));
		metadata.addFieldAsList(invoicesVatRateKey(AMOUNT));

		metadata.addFieldAsList(expenseKey(NUMBER));
		metadata.addFieldAsList(expenseKey(SUPPLIER));
		metadata.addFieldAsList(expenseKey(DESCRIPTION));
		metadata.addFieldAsList(expenseKey(DATE));
		metadata.addFieldAsList(expenseKey(DUE_DATE));
		metadata.addFieldAsList(expenseKey(STATUS));
		metadata.addFieldAsList(expenseKey(AMOUNT));
		metadata.addFieldAsList(expenseKey(VAT_RATE));
		metadata.addFieldAsList(expenseKey(VAT));

		metadata.addFieldAsList(expensesVatRateKey(DESCRIPTION));
		metadata.addFieldAsList(expensesVatRateKey(RATE));
		metadata.addFieldAsList(expensesVatRateKey(AMOUNT));

		metadata.addFieldAsList(quoteKey(NUMBER));
		metadata.addFieldAsList(quoteKey(CLIENT));
		metadata.addFieldAsList(quoteKey(DESCRIPTION));
		metadata.addFieldAsList(quoteKey(DATE));
		metadata.addFieldAsList(quoteKey(VALID_UNTIL_DATE));

		TemplateUtils.addHourXDocMetadata(metadata);
		TemplateUtils.addMileageXDocMetadata(metadata);
		TemplateUtils.addCallXDocMetadata(metadata);

		return xDocReport;

	}

}
