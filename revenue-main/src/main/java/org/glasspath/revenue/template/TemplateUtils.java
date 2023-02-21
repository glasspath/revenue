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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JMenu;

import org.glasspath.aerialist.AerialistUtils;
import org.glasspath.aerialist.Padding;
import org.glasspath.aerialist.TextBox;
import org.glasspath.aerialist.editor.DocumentEditorPanel;
import org.glasspath.aerialist.editor.actions.InsertElementAction;
import org.glasspath.aerialist.icons.Icons;
import org.glasspath.aerialist.template.TemplateMetadata.CategoryMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.FieldMetadata;
import org.glasspath.aerialist.template.TemplateMetadata.TableMetadata;
import org.glasspath.common.Common;

import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

@SuppressWarnings("nls")
public class TemplateUtils extends KeyUtils {

	public static final Color TABLE_HEADER_ROW_COLOR = new Color(217, 217, 217);
	public static final Color TABLE_ALTERNATING_ROW_COLOR = new Color(242, 242, 242);
	public static final Color TABLE_VERTICAL_LINE_COLOR = new Color(165, 165, 165);

	private TemplateUtils() {

	}

	public static CategoryMetadata createMyCompanyTemplateMetadata() {

		CategoryMetadata companyMetadata = new CategoryMetadata(description(MY_COMPANY));

		companyMetadata.getChildren().add(new FieldMetadata(description(NAME), myCompanyKey(NAME)));
		companyMetadata.getChildren().add(new FieldMetadata(description(BUSINESS_NUMBER), myCompanyKey(BUSINESS_NUMBER)));
		companyMetadata.getChildren().add(new FieldMetadata(description(VAT_NUMBER), myCompanyKey(VAT_NUMBER)));
		companyMetadata.getChildren().add(new FieldMetadata(description(BANK_ACCOUNT), myCompanyKey(BANK_ACCOUNT)));
		companyMetadata.getChildren().add(new FieldMetadata(description(WEBSITE), myCompanyKey(WEBSITE)));
		companyMetadata.getChildren().add(new FieldMetadata(description(EMAIL), myCompanyKey(EMAIL)));
		companyMetadata.getChildren().add(new FieldMetadata(description(TELEPHONE), myCompanyKey(TELEPHONE)));

		CategoryMetadata clientAddress1Metadata = new CategoryMetadata(description(ADDRESS_1));
		companyMetadata.getChildren().add(clientAddress1Metadata);

		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(STREET), myCompanyAddress1Key(STREET)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(PO_BOX), myCompanyAddress1Key(PO_BOX)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(POSTAL_CODE), myCompanyAddress1Key(POSTAL_CODE)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(CITY), myCompanyAddress1Key(CITY)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(STATE), myCompanyAddress1Key(STATE)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(COUNTRY), myCompanyAddress1Key(COUNTRY)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(TELEPHONE), myCompanyAddress1Key(TELEPHONE)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(FAX), myCompanyAddress1Key(FAX)));

		CategoryMetadata clientAddress2Metadata = new CategoryMetadata(description(ADDRESS_2));
		companyMetadata.getChildren().add(clientAddress2Metadata);

		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(STREET), myCompanyAddress2Key(STREET)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(PO_BOX), myCompanyAddress2Key(PO_BOX)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(POSTAL_CODE), myCompanyAddress2Key(POSTAL_CODE)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(CITY), myCompanyAddress2Key(CITY)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(STATE), myCompanyAddress2Key(STATE)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(COUNTRY), myCompanyAddress2Key(COUNTRY)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(TELEPHONE), myCompanyAddress2Key(TELEPHONE)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(FAX), myCompanyAddress2Key(FAX)));

		return companyMetadata;

	}

	public static JMenu createMyCompanyInsertMenu(DocumentEditorPanel context) {

		JMenu menu = new JMenu(description(MY_COMPANY));

		menu.add(new InsertElementAction(context, createMyCompanyDetailsTextBox(false), "Company details text box, address 1", Icons.textBoxPlus));
		menu.add(new InsertElementAction(context, createMyCompanyDetailsTextBox(true), "Company details text box, address 2", Icons.textBoxPlus));

		return menu;

	}

	public static TextBox createMyCompanyDetailsTextBox(boolean address2) {

		TextBox textBox = new TextBox();
		textBox.setWidth(250);
		textBox.setHeight(180);
		textBox.setPadding(Padding.from(3));

		textBox.setText("My company name\n"
				+ "Street\n"
				+ "Postal code City\n"
				+ "tel. Telephone\n"
				+ "Email\n"
				+ "Website\n"
				+ "Bank account: Bank account\n"
				+ "Business number: Business number\n"
				+ "VAT-number: VAT number");

		// TODO: We are adding empty text styles here, otherwise fields are not applied correctly..
		textBox.getStyles().add(AerialistUtils.createTextStyle(0, 15, tKey(myCompanyKey(NAME))));
		textBox.getStyles().add(AerialistUtils.createTextStyle(15));
		textBox.getStyles().add(AerialistUtils.createTextStyle(16, 22, address2 ? tKey(myCompanyAddress2Key(STREET)) : tKey(myCompanyAddress1Key(STREET))));
		textBox.getStyles().add(AerialistUtils.createTextStyle(22));
		textBox.getStyles().add(AerialistUtils.createTextStyle(23, 34, address2 ? tKey(myCompanyAddress2Key(POSTAL_CODE)) : tKey(myCompanyAddress1Key(POSTAL_CODE))));
		textBox.getStyles().add(AerialistUtils.createTextStyle(34));
		textBox.getStyles().add(AerialistUtils.createTextStyle(35, 39, address2 ? tKey(myCompanyAddress2Key(CITY)) : tKey(myCompanyAddress1Key(CITY))));
		textBox.getStyles().add(AerialistUtils.createTextStyle(39));
		textBox.getStyles().add(AerialistUtils.createTextStyle(40, 45, null, 10.0F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(45, 54, tKey(myCompanyKey(TELEPHONE)), 10.0F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(54, 10.0F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(55, 60, tKey(myCompanyKey(EMAIL)), 10.0F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(60, 10.F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(61, 68, tKey(myCompanyKey(WEBSITE))));
		textBox.getStyles().add(AerialistUtils.createTextStyle(68));
		textBox.getStyles().add(AerialistUtils.createTextStyle(69, 83, null, 10.0F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(83, 95, tKey(myCompanyKey(BANK_ACCOUNT)), 10.0F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(95, 10.0F));
		textBox.getStyles().add(AerialistUtils.createTextStyle(113, 128, tKey(myCompanyKey(BUSINESS_NUMBER))));
		textBox.getStyles().add(AerialistUtils.createTextStyle(128));
		textBox.getStyles().add(AerialistUtils.createTextStyle(129, 141));
		textBox.getStyles().add(AerialistUtils.createTextStyle(141, 151, tKey(myCompanyKey(VAT_NUMBER))));

		return textBox;

	}

	public static CategoryMetadata createClientTemplateMetadata() {

		CategoryMetadata clientMetadata = new CategoryMetadata(description(CLIENT));

		clientMetadata.getChildren().add(new FieldMetadata(description(NUMBER), clientKey(NUMBER)));
		clientMetadata.getChildren().add(new FieldMetadata(description(FORMATTED_NUMBER), clientKey(FORMATTED_NUMBER)));
		clientMetadata.getChildren().add(new FieldMetadata(description(NAME), clientKey(NAME)));
		clientMetadata.getChildren().add(new FieldMetadata(description(BUSINESS_NUMBER), clientKey(BUSINESS_NUMBER)));
		clientMetadata.getChildren().add(new FieldMetadata(description(VAT_NUMBER), clientKey(VAT_NUMBER)));
		clientMetadata.getChildren().add(new FieldMetadata(description(BANK_ACCOUNT), clientKey(BANK_ACCOUNT)));
		clientMetadata.getChildren().add(new FieldMetadata(description(WEBSITE), clientKey(WEBSITE)));
		clientMetadata.getChildren().add(new FieldMetadata(description(EMAIL), clientKey(EMAIL)));
		clientMetadata.getChildren().add(new FieldMetadata(description(TELEPHONE), clientKey(TELEPHONE)));

		CategoryMetadata clientAddress1Metadata = new CategoryMetadata(description(ADDRESS_1));
		clientMetadata.getChildren().add(clientAddress1Metadata);

		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(STREET), clientAddress1Key(STREET)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(PO_BOX), clientAddress1Key(PO_BOX)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(POSTAL_CODE), clientAddress1Key(POSTAL_CODE)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(CITY), clientAddress1Key(CITY)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(STATE), clientAddress1Key(STATE)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(COUNTRY), clientAddress1Key(COUNTRY)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(TELEPHONE), clientAddress1Key(TELEPHONE)));
		clientAddress1Metadata.getChildren().add(new FieldMetadata(description(FAX), clientAddress1Key(FAX)));

		CategoryMetadata clientAddress2Metadata = new CategoryMetadata(description(ADDRESS_2));
		clientMetadata.getChildren().add(clientAddress2Metadata);

		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(STREET), clientAddress2Key(STREET)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(PO_BOX), clientAddress2Key(PO_BOX)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(POSTAL_CODE), clientAddress2Key(POSTAL_CODE)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(CITY), clientAddress2Key(CITY)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(STATE), clientAddress2Key(STATE)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(COUNTRY), clientAddress2Key(COUNTRY)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(TELEPHONE), clientAddress2Key(TELEPHONE)));
		clientAddress2Metadata.getChildren().add(new FieldMetadata(description(FAX), clientAddress2Key(FAX)));

		return clientMetadata;

	}

	public static TableMetadata createHoursTableTemplateMetada() {

		TableMetadata hoursTableMetadata = new TableMetadata("Hours table");

		hoursTableMetadata.getChildren().add(new FieldMetadata(description(DATE_FROM), hourKey(DATE_FROM)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(TIME_FROM), hourKey(TIME_FROM)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(DATE_TO), hourKey(DATE_TO)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(TIME_TO), hourKey(TIME_TO)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL), hourKey(TOTAL)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), hourKey(TOTAL_FOR_INVOICE)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(CLIENT), hourKey(CLIENT)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(PROJECT), hourKey(PROJECT)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(RATE), hourKey(RATE)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), hourKey(AMOUNT)));
		hoursTableMetadata.getChildren().add(new FieldMetadata(description(COMMENTS), hourKey(COMMENTS)));

		return hoursTableMetadata;

	}

	public static TableMetadata createMileagesTableTemplateMetada() {

		TableMetadata mileagesTableMetadata = new TableMetadata("Mileages table");

		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(DATE_FROM), mileageKey(DATE_FROM)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(TIME_FROM), mileageKey(TIME_FROM)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(DATE_TO), mileageKey(DATE_TO)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(TIME_TO), mileageKey(TIME_TO)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(ROUTE), mileageKey(ROUTE)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL), mileageKey(TOTAL)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), mileageKey(TOTAL_FOR_INVOICE)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(CLIENT), mileageKey(CLIENT)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(PROJECT), mileageKey(PROJECT)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(RATE), mileageKey(RATE)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), mileageKey(AMOUNT)));
		mileagesTableMetadata.getChildren().add(new FieldMetadata(description(COMMENTS), mileageKey(COMMENTS)));

		return mileagesTableMetadata;

	}

	public static TableMetadata createCallsTableTemplateMetada() {

		TableMetadata callsTableMetadata = new TableMetadata("Calls table");

		callsTableMetadata.getChildren().add(new FieldMetadata(description(DATE), callKey(DATE)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(TIME), mileageKey(TIME)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(NAME), mileageKey(NAME)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(NUMBER), mileageKey(NUMBER)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(DURATION), mileageKey(DURATION)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL), mileageKey(TOTAL)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(TOTAL_FOR_INVOICE), mileageKey(TOTAL_FOR_INVOICE)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(COMPANY), mileageKey(COMPANY)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(RATE), mileageKey(RATE)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(AMOUNT), mileageKey(AMOUNT)));
		callsTableMetadata.getChildren().add(new FieldMetadata(description(COMMENTS), mileageKey(COMMENTS)));

		return callsTableMetadata;

	}

	public static void addHourXDocMetadata(FieldsMetadata metadata) {

		metadata.addFieldAsList(hourKey(DATE_FROM));
		metadata.addFieldAsList(hourKey(TIME_FROM));
		metadata.addFieldAsList(hourKey(DATE_TO));
		metadata.addFieldAsList(hourKey(TIME_TO));
		metadata.addFieldAsList(hourKey(TOTAL));
		metadata.addFieldAsList(hourKey(TOTAL_FOR_INVOICE));
		metadata.addFieldAsList(hourKey(CLIENT));
		metadata.addFieldAsList(hourKey(PROJECT));
		metadata.addFieldAsList(hourKey(RATE));
		metadata.addFieldAsList(hourKey(AMOUNT));
		metadata.addFieldAsList(hourKey(COMMENTS));

	}

	public static void addMileageXDocMetadata(FieldsMetadata metadata) {

		metadata.addFieldAsList(mileageKey(DATE_FROM));
		metadata.addFieldAsList(mileageKey(TIME_FROM));
		metadata.addFieldAsList(mileageKey(DATE_TO));
		metadata.addFieldAsList(mileageKey(TIME_TO));
		metadata.addFieldAsList(mileageKey(ROUTE));
		metadata.addFieldAsList(mileageKey(TOTAL));
		metadata.addFieldAsList(mileageKey(TOTAL_FOR_INVOICE));
		metadata.addFieldAsList(mileageKey(CLIENT));
		metadata.addFieldAsList(mileageKey(PROJECT));
		metadata.addFieldAsList(mileageKey(RATE));
		metadata.addFieldAsList(mileageKey(AMOUNT));
		metadata.addFieldAsList(mileageKey(COMMENTS));

	}

	public static void addCallXDocMetadata(FieldsMetadata metadata) {

		metadata.addFieldAsList(callKey(DATE));
		metadata.addFieldAsList(callKey(TIME));
		metadata.addFieldAsList(callKey(NAME));
		metadata.addFieldAsList(callKey(NUMBER));
		metadata.addFieldAsList(callKey(DURATION));
		metadata.addFieldAsList(callKey(TOTAL));
		metadata.addFieldAsList(callKey(TOTAL_FOR_INVOICE));
		metadata.addFieldAsList(callKey(COMPANY));
		metadata.addFieldAsList(callKey(RATE));
		metadata.addFieldAsList(callKey(AMOUNT));
		metadata.addFieldAsList(callKey(COMMENTS));

	}

	public static byte[] createImage(String text, int width, int height, Color textColor, Color bgColor) {

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.setColor(bgColor);
		g2d.fillRect(0, 0, width, height);

		g2d.setColor(textColor);
		g2d.setFont(g2d.getFont().deriveFont(18.0F));
		g2d.drawString(text, 15, 30);

		return createImage(image);

	}

	public static byte[] createImage(BufferedImage image) {

		try {

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", byteArrayOutputStream);
			byteArrayOutputStream.flush();

			byte[] imageBytes = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();

			return imageBytes;

		} catch (Exception e) {
			Common.LOGGER.error("Exception while creating template image", e);
		}

		return null;

	}

}
