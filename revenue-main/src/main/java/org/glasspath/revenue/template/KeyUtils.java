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
package org.glasspath.revenue.template;

import java.util.ResourceBundle;

import org.glasspath.aerialist.Field.FieldType;

@SuppressWarnings("nls")
public class KeyUtils {

	public static final ResourceBundle KEYS = ResourceBundle.getBundle("org.glasspath.revenue.template.keys");
	public static final ResourceBundle DESCRIPTIONS = ResourceBundle.getBundle("org.glasspath.revenue.template.descriptions");

	public static final Key ADDRESS = new Key("Address");
	public static final Key ADDRESS_1 = new Key("Address1");
	public static final Key ADDRESS_2 = new Key("Address2");
	public static final Key AMOUNT = new Key("Amount");

	public static final Key BANK_ACCOUNT = new Key("BankAccount");
	public static final Key BUSINESS_NUMBER = new Key("BusinessNumber");

	public static final Key CALL = new Key("Call");
	public static final Key CALLS = new Key("Calls");
	public static final Key CALLS_INCLUDED = new Key("CallsIncluded");
	public static final Key CITY = new Key("City");
	public static final Key CLIENT = new Key("Client");
	public static final Key COMMENTS = new Key("Comments");
	public static final Key COMPANY = new Key("Company");
	public static final Key COUNTRY = new Key("Country");

	public static final Key DATE = new Key("Date");
	public static final Key DATE_FROM = new Key("DateFrom");
	public static final Key DATE_TO = new Key("DateTo");
	public static final Key DATE_PAID = new Key("DatePaid");
	public static final Key DESCRIPTION = new Key("Description");
	public static final Key DUE_DATE = new Key("DueDate");
	public static final Key DURATION = new Key("Duration");

	public static final Key EMAIL = new Key("Email");
	public static final Key EXPENSE = new Key("Expense");
	public static final Key EXPENSES = new Key("Expenses");
	public static final Key EXPENSES_INCLUDED = new Key("ExpensesIncluded");
	public static final Key EXPENSES_VAT_RATE = new Key("ExpensesVatRate");

	public static final Key FAX = new Key("Fax");
	public static final Key FORMATTED_NUMBER = new Key("FormattedNumber");
	public static final Key FROM = new Key("From");

	public static final Key HOUR = new Key("Hour");
	public static final Key HOURS = new Key("Hours");
	public static final Key HOURS_INCLUDED = new Key("HoursIncluded");

	public static final Key INVOICE = new Key("Invoice");
	public static final Key INVOICES = new Key("Invoices");
	public static final Key INVOICES_INCLUDED = new Key("InvoicesIncluded");
	public static final Key INVOICES_VAT_RATE = new Key("InvoicesVatRate");
	public static final Key INVOICE_LINE = new Key("InvoiceLine");

	public static final Key MILEAGE = new Key("Mileage");
	public static final Key MILEAGE_INCLUDED = new Key("MileageIncluded");
	public static final Key MILEAGES = new Key("Mileages");
	public static final Key MY_COMPANY = new Key("MyCompany");

	public static final Key NAME = new Key("Name");
	public static final Key NUMBER = new Key("Number");

	public static final Key PERIOD = new Key("Period");
	public static final Key PO_BOX = new Key("PoBox");
	public static final Key POSTAL_CODE = new Key("PostalCode");
	public static final Key PREVIEW_IMAGE = new Key("PreviewImage");
	public static final Key PRICE = new Key("Price");
	public static final Key PROJECT = new Key("Project");

	public static final Key QUANTITY = new Key("Quantity");
	public static final Key QUOTE = new Key("Quote");
	public static final Key QUOTES_INCLUDED = new Key("QuotesIncluded");

	public static final Key RATE = new Key("Rate");
	public static final Key REFERENCE = new Key("Reference");
	public static final Key REPORT = new Key("Report");
	public static final Key ROUTE = new Key("Route");

	public static final Key STATE = new Key("State");
	public static final Key STATUS = new Key("Status");
	public static final Key STREET = new Key("Street");
	public static final Key SUPPLIER = new Key("Supplier");

	public static final Key TELEPHONE = new Key("Telephone");
	public static final Key TIME = new Key("Time");
	public static final Key TIME_FROM = new Key("TimeFrom");
	public static final Key TIME_SHEET = new Key("TimeSheet");
	public static final Key TIME_TO = new Key("TimeTo");
	public static final Key TO = new Key("To");
	public static final Key TOTAL = new Key("Total");
	public static final Key TOTAL_EXCL = new Key("TotalExcl");
	public static final Key TOTAL_FOR_INVOICE = new Key("TotalForInvoice");
	public static final Key TOTAL_INCL = new Key("TotalIncl");

	public static final Key VALID_UNTIL_DATE = new Key("ValidUntilDate");
	public static final Key VAT = new Key("Vat");
	public static final Key VAT_NUMBER = new Key("VatNumber");
	public static final Key VAT_RATE = new Key("VatRate");

	public static final Key WEBSITE = new Key("Website");
	public static final Key WEEK_NUMBER = new Key("WeekNumber");

	public static final Key YEAR = new Key("Year");

	protected KeyUtils() {

	}

	public static String key(Key key) {

		if (key.value == null) {
			try {
				key.value = KEYS.getString(key.key);
			} catch (Exception e) {
				key.value = key.key;
			}
		}

		return key.value;

	}

	public static String description(Key key) {

		if (key.description == null) {
			try {
				key.description = DESCRIPTIONS.getString(key.key);
			} catch (Exception e) {
				key.description = key.key;
			}
		}

		return key.description;

	}

	public static String myCompanyKey(Key key) {
		return key(MY_COMPANY, key);
	}

	public static String myCompanyAddress1Key(Key key) {
		return key(MY_COMPANY, ADDRESS_1, key);
	}

	public static String myCompanyAddress2Key(Key key) {
		return key(MY_COMPANY, ADDRESS_2, key);
	}

	public static String clientKey(Key key) {
		return key(CLIENT, key);
	}

	public static String clientAddress1Key(Key key) {
		return key(CLIENT, ADDRESS_1, key);
	}

	public static String clientAddress2Key(Key key) {
		return key(CLIENT, ADDRESS_2, key);
	}

	public static String hourKey(Key key) {
		return key(HOUR, key);
	}

	public static String hoursKey(Key key) {
		return key(HOURS, key);
	}

	public static String mileageKey(Key key) {
		return key(MILEAGE, key);
	}

	public static String mileagesKey(Key key) {
		return key(MILEAGES, key);
	}

	public static String callKey(Key key) {
		return key(CALL, key);
	}

	public static String callsKey(Key key) {
		return key(CALLS, key);
	}

	public static String quoteKey(Key key) {
		return key(QUOTE, key);
	}

	public static String expenseKey(Key key) {
		return key(EXPENSE, key);
	}

	public static String expensesKey(Key key) {
		return key(EXPENSES, key);
	}

	public static String expensesVatRateKey(Key key) {
		return key(EXPENSES_VAT_RATE, key);
	}

	public static String invoiceKey(Key key) {
		return key(INVOICE, key);
	}

	public static String invoiceLineKey(Key key) {
		return key(INVOICE_LINE, key);
	}

	public static String invoicesKey(Key key) {
		return key(INVOICES, key);
	}

	public static String invoicesVatRateKey(Key key) {
		return key(INVOICES_VAT_RATE, key);
	}

	public static String vatRateKey(Key key) {
		return key(VAT_RATE, key);
	}

	public static String reportKey(Key key) {
		return key(REPORT, key);
	}

	public static String timeSheetKey(Key key) {
		return key(TIME_SHEET, key);
	}

	public static String timeSheetTotalKey(Key key) {
		return key(TIME_SHEET, TOTAL, key);
	}

	public static String totalKey(Key key) {
		return key(TOTAL, key);
	}

	public static String tKey(String key) {
		return FieldType.TEMPLATE.getIdentifier() + key;
	}

	public static String key(Key key1, Key key2) {
		return key(key1, key2, ".");
	}

	public static String key(Key key1, Key key2, String separator) {
		return key(key1) + separator + key(key2);
	}

	public static String key(Key key1, Key key2, Key key3) {
		return key(key1, key2, key3, ".");
	}

	public static String key(Key key1, Key key2, Key key3, String separator) {
		return key(key1) + separator + key(key2) + separator + key(key3);
	}

	public static class Key {

		private final String key;
		private String value = null;
		private String description = null;

		protected Key(String key) {
			this.key = key;
		}

	}

}
