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
package org.glasspath.revenue.icons;

import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.glasspath.common.swing.icon.SvgIcon;
import org.glasspath.common.swing.icon.SvgIcon.OffsetSvgIcon;

@SuppressWarnings("nls")
public class Icons {

	public static final Icons INSTANCE = new Icons();
	public static final ClassLoader CLASS_LOADER = INSTANCE.getClass().getClassLoader();

	private Icons() {

	}

	private static URL getSvg(String name) {
		return CLASS_LOADER.getResource("org/glasspath/revenue/icons/svg/" + name);
	}

	public static final ArrayList<Image> appIcon = new ArrayList<Image>();
	static {
		appIcon.add(new ImageIcon(CLASS_LOADER.getResource("org/glasspath/revenue/icons/16x16/app_icon.png")).getImage());
		appIcon.add(new ImageIcon(CLASS_LOADER.getResource("org/glasspath/revenue/icons/22x22/app_icon.png")).getImage());
		appIcon.add(new ImageIcon(CLASS_LOADER.getResource("org/glasspath/revenue/icons/24x24/app_icon.png")).getImage());
		appIcon.add(new ImageIcon(CLASS_LOADER.getResource("org/glasspath/revenue/icons/32x32/app_icon.png")).getImage());
		appIcon.add(new ImageIcon(CLASS_LOADER.getResource("org/glasspath/revenue/icons/48x48/app_icon.png")).getImage());
	}

	public static final ImageIcon null_16x16 = new ImageIcon(CLASS_LOADER.getResource("org/glasspath/revenue/icons/16x16/null_16x16.png"));
	public static final ImageIcon null_1x16 = new ImageIcon(CLASS_LOADER.getResource("org/glasspath/revenue/icons/16x16/null_1x16.png"));

	// SVG
	public static final SvgIcon accountBox = new SvgIcon(getSvg("account-box.svg"));
	public static final SvgIcon accountBoxXLarge = new SvgIcon(36, 0, getSvg("account-box.svg"));
	public static final SvgIcon accountMultipleBlue = new SvgIcon(getSvg("account-multiple.svg"));
	public static final SvgIcon accountMultipleBlueLarge = new SvgIcon(22, 0, getSvg("account-multiple.svg"));
	public static final SvgIcon accountMultipleWhiteLarge = new SvgIcon(22, 0, getSvg("account-multiple.svg"));
	public static final SvgIcon accountMultipleBlueXLarge = new SvgIcon(36, 0, getSvg("account-multiple.svg"));
	public static final SvgIcon calculatorVariantOutlineContrast = new SvgIcon(getSvg("calculator-variant-outline.svg"));
	public static final SvgIcon calculatorVariantOutlineLarge = new SvgIcon(22, 0, getSvg("calculator-variant-outline.svg"));
	public static final SvgIcon calculatorVariantOutlineWhiteLarge = new SvgIcon(22, 0, getSvg("calculator-variant-outline.svg"));
	public static final SvgIcon calendarBlank = new SvgIcon(getSvg("calendar-blank.svg"));
	public static final SvgIcon calendarLarge = new SvgIcon(22, 0, getSvg("calendar.svg"));
	public static final SvgIcon calendarMonth = new SvgIcon(getSvg("calendar-month.svg"));
	public static final SvgIcon calendarRange = new SvgIcon(getSvg("calendar-range.svg"));
	public static final SvgIcon calendarText = new SvgIcon(getSvg("calendar-text.svg"));
	public static final SvgIcon calendarTextXLarge = new SvgIcon(36, 0, getSvg("calendar-text.svg"));
	public static final SvgIcon calendarToday = new SvgIcon(getSvg("calendar-today.svg"));
	public static final SvgIcon calendarWeek = new SvgIcon(getSvg("calendar-week.svg"));
	public static final SvgIcon calendarWhiteLarge = new SvgIcon(22, 0, getSvg("calendar.svg"));
	public static final SvgIcon car = new SvgIcon(getSvg("car.svg"));
	public static final SvgIcon carContrast = new SvgIcon(16, 1, getSvg("car.svg"));
	public static final SvgIcon carLarge = new SvgIcon(22, 0, getSvg("car.svg"));
	public static final SvgIcon carPurple = new SvgIcon(getSvg("car.svg"));
	public static final SvgIcon carWarning = new SvgIcon(16, 1, getSvg("car.svg"));
	public static final SvgIcon cardAccountPhone = new SvgIcon(getSvg("card-account-phone.svg"));
	public static final SvgIcon carWhiteLarge = new SvgIcon(22, 0, getSvg("car.svg"));
	public static final SvgIcon cellphone = new SvgIcon(getSvg("cellphone.svg"));
	public static final SvgIcon chartBoxOutline = new SvgIcon(getSvg("chart-box-outline.svg"));
	public static final SvgIcon chartBoxOutlineLarge = new SvgIcon(22, 0, getSvg("chart-box-outline.svg"));
	public static final SvgIcon chartBoxOutlineWhite = new SvgIcon(getSvg("chart-box-outline.svg"));
	public static final SvgIcon chartBoxOutlineWhiteLarge = new SvgIcon(22, 0, getSvg("chart-box-outline.svg"));
	public static final SvgIcon checkBlueXLarge = new SvgIcon(36, 0, getSvg("check.svg"));
	public static final SvgIcon chevronDown = new SvgIcon(getSvg("chevron-down.svg"));
	public static final SvgIcon chevronUp = new SvgIcon(getSvg("chevron-up.svg"));
	public static final SvgIcon close = new SvgIcon(getSvg("close.svg"));
	public static final SvgIcon closeRed = new SvgIcon(getSvg("close.svg"));
	public static final SvgIcon cogBoxXLarge = new SvgIcon(36, 0, getSvg("cog-box.svg"));
	public static final SvgIcon contentSave = new SvgIcon(getSvg("content-save.svg"));
	public static final SvgIcon currencyUsd = new SvgIcon(getSvg("currency-usd.svg"));
	public static final SvgIcon currencyUsdLarge = new SvgIcon(22, 0, getSvg("currency-usd.svg"));
	public static final SvgIcon currencyUsdWhiteLarge = new SvgIcon(22, 0, getSvg("currency-usd.svg"));
	public static final SvgIcon currencyUsdBlueXLarge = new SvgIcon(36, 0, getSvg("currency-usd.svg"));
	public static final SvgIcon dotsHorizontal = new OffsetSvgIcon(16, 0, 4, 0, getSvg("dots-horizontal.svg"));
	public static final SvgIcon download = new SvgIcon(getSvg("download.svg"));
	public static final SvgIcon downloadXLarge = new SvgIcon(getSvg("download.svg"));
	public static final SvgIcon emailArrowRight = new SvgIcon(getSvg("email-arrow-right.svg"));
	public static final SvgIcon emailArrowRightOutline = new SvgIcon(getSvg("email-arrow-right-outline.svg"));
	public static final SvgIcon emailOutlineGreenLarge = new SvgIcon(22, 0, getSvg("email-outline.svg"));
	public static final SvgIcon fileDelimitedOutline = new SvgIcon(getSvg("file-delimited-outline.svg"));
	public static final SvgIcon fileDocumentOutline = new SvgIcon(getSvg("file-document-outline.svg"));
	public static final SvgIcon fileDocumentOutlineBlue = new SvgIcon(getSvg("file-document-outline.svg"));
	public static final SvgIcon fileDocumentOutlineBlueLarge = new SvgIcon(22, 0, getSvg("file-document-outline.svg"));
	public static final SvgIcon fileDocumentOutlineGreen = new SvgIcon(getSvg("file-document-outline.svg"));
	public static final SvgIcon fileDocumentOutlineGreenLarge = new SvgIcon(22, 0, getSvg("file-document-outline.svg"));
	public static final SvgIcon fileDocumentOutlineGreenXLarge = new SvgIcon(36, 0, getSvg("file-document-outline.svg"));
	public static final SvgIcon fileDocumentOutlineLarge = new SvgIcon(22, 0, getSvg("file-document-outline.svg"));
	public static final SvgIcon fileDocumentOutlineWhiteLarge = new SvgIcon(22, 0, getSvg("file-document-outline.svg"));
	public static final SvgIcon fileContrast = new SvgIcon(getSvg("file.svg"));
	public static final SvgIcon fileGreen = new SvgIcon(getSvg("file.svg"));
	public static final SvgIcon fileOutlineContrast = new SvgIcon(getSvg("file-outline.svg"));
	public static final SvgIcon fileReplaceOutlineBlueXXLarge = new SvgIcon(56, 0, getSvg("file-replace-outline.svg"));
	public static final SvgIcon fileReplaceOutlineGreenXXLarge = new SvgIcon(56, 0, getSvg("file-replace-outline.svg"));
	public static final SvgIcon fileReplaceOutlinePurpleXXLarge = new SvgIcon(56, 0, getSvg("file-replace-outline.svg"));
	public static final SvgIcon folderOutline = new SvgIcon(getSvg("folder-outline.svg"));
	public static final SvgIcon hammerScrewdriver = new SvgIcon(getSvg("hammer-screwdriver.svg"));
	public static final SvgIcon hammerScrewdriverBlueXLarge = new SvgIcon(36, 0, getSvg("hammer-screwdriver.svg"));
	public static final SvgIcon hammerScrewdriverLarge = new SvgIcon(22, 0, getSvg("hammer-screwdriver.svg"));
	public static final SvgIcon hammerScrewdriverWhiteLarge = new SvgIcon(22, 0, getSvg("hammer-screwdriver.svg"));
	public static final SvgIcon magnify = new SvgIcon(getSvg("magnify.svg"));
	public static final SvgIcon map = new SvgIcon(getSvg("map.svg"));
	public static final SvgIcon mapXLarge = new SvgIcon(36, 0, getSvg("map.svg"));
	public static final SvgIcon percentBox = new SvgIcon(getSvg("percent-box.svg"));
	public static final SvgIcon percentBoxXLarge = new SvgIcon(36, 0, getSvg("percent-box.svg"));
	public static final SvgIcon phoneInTalk = new SvgIcon(getSvg("phone-in-talk.svg"));
	public static final SvgIcon phoneInTalkContrast = new SvgIcon(getSvg("phone-in-talk.svg"));
	public static final SvgIcon phoneInTalkLarge = new SvgIcon(22, 0, getSvg("phone-in-talk.svg"));
	public static final SvgIcon phoneInTalkPurple = new SvgIcon(getSvg("phone-in-talk.svg"));
	public static final SvgIcon phoneInTalkWhiteLarge = new SvgIcon(22, 0, getSvg("phone-in-talk.svg"));
	public static final SvgIcon qrcode = new SvgIcon(getSvg("qrcode.svg"));
	public static final SvgIcon qrcodeScanBlueLarge = new SvgIcon(22, 0, getSvg("qrcode-scan.svg"));
	public static final SvgIcon receiptOutlineContrast = new SvgIcon(getSvg("receipt-outline.svg"));
	public static final SvgIcon receiptOutlineLarge = new SvgIcon(22, 0, getSvg("receipt-outline.svg"));
	public static final SvgIcon receiptOutlineWhiteLarge = new SvgIcon(22, 0, getSvg("receipt-outline.svg"));
	public static final SvgIcon redo = new SvgIcon(getSvg("redo.svg"));
	public static final SvgIcon refreshBlueLarge = new SvgIcon(22, 0, getSvg("refresh.svg"));
	public static final SvgIcon share = new SvgIcon(getSvg("share.svg"));
	public static final SvgIcon sortAscending = new SvgIcon(getSvg("sort-ascending.svg"));
	public static final SvgIcon sortDescending = new SvgIcon(getSvg("sort-descending.svg"));
	public static final SvgIcon sortCalendarAscending = new SvgIcon(getSvg("sort-calendar-ascending.svg"));
	public static final SvgIcon sortCalendarDescending = new SvgIcon(getSvg("sort-calendar-descending.svg"));
	public static final SvgIcon sync = new SvgIcon(getSvg("sync.svg"));
	public static final SvgIcon syncXLarge = new SvgIcon(36, 0, getSvg("sync.svg"));
	public static final SvgIcon timerOutline = new SvgIcon(getSvg("timer-outline.svg"));
	public static final SvgIcon timerOutlineContrast = new SvgIcon(16, 1, getSvg("timer-outline.svg"));
	public static final SvgIcon timerOutlinePurple = new SvgIcon(getSvg("timer-outline.svg"));
	public static final SvgIcon timerOutlineWarning = new SvgIcon(16, 1, getSvg("timer-outline.svg"));
	public static final SvgIcon timerOutlineLarge = new SvgIcon(22, 0, getSvg("timer-outline.svg"));
	public static final SvgIcon timerOutlineWhiteLarge = new SvgIcon(22, 0, getSvg("timer-outline.svg"));
	public static final SvgIcon truckFastOutline = new SvgIcon(getSvg("truck-fast-outline.svg"));
	public static final SvgIcon truckFastOutlineLarge = new SvgIcon(22, 0, getSvg("truck-fast-outline.svg"));
	public static final SvgIcon truckFastOutlineWhiteLarge = new SvgIcon(22, 0, getSvg("truck-fast-outline.svg"));
	public static final SvgIcon truckFastOutlineXLarge = new SvgIcon(36, 0, getSvg("truck-fast-outline.svg"));
	public static final SvgIcon undo = new SvgIcon(getSvg("undo.svg"));
	public static final SvgIcon upload = new SvgIcon(getSvg("upload.svg"));
	public static final SvgIcon uploadXLarge = new SvgIcon(getSvg("upload.svg"));

	static {
		accountBox.setColorFilter(SvgIcon.BLUE);
		accountBoxXLarge.setColorFilter(SvgIcon.BLUE);
		accountMultipleBlue.setColorFilter(SvgIcon.BLUE);
		accountMultipleBlueLarge.setColorFilter(SvgIcon.BLUE);
		accountMultipleWhiteLarge.setColorFilter(SvgIcon.WHITE);
		accountMultipleBlueXLarge.setColorFilter(SvgIcon.BLUE);
		calculatorVariantOutlineContrast.setColorFilter(SvgIcon.CONTRAST);
		calculatorVariantOutlineLarge.setColorFilter(SvgIcon.GREEN);
		calculatorVariantOutlineWhiteLarge.setColorFilter(SvgIcon.WHITE);
		calendarLarge.setColorFilter(SvgIcon.GREEN);
		calendarText.setColorFilter(SvgIcon.BLUE);
		calendarTextXLarge.setColorFilter(SvgIcon.BLUE);
		calendarWhiteLarge.setColorFilter(SvgIcon.WHITE);
		carContrast.setColorFilter(SvgIcon.CONTRAST);
		cardAccountPhone.setColorFilter(SvgIcon.BLUE);
		carLarge.setColorFilter(SvgIcon.PURPLE);
		carPurple.setColorFilter(SvgIcon.PURPLE);
		carWarning.setColorFilter(SvgIcon.WARNING);
		carWhiteLarge.setColorFilter(SvgIcon.WHITE);
		cellphone.setColorFilter(SvgIcon.BLUE);
		chartBoxOutline.setColorFilter(SvgIcon.BLUE);
		chartBoxOutlineLarge.setColorFilter(SvgIcon.GREEN);
		chartBoxOutlineWhite.setColorFilter(SvgIcon.WHITE);
		chartBoxOutlineWhiteLarge.setColorFilter(SvgIcon.WHITE);
		checkBlueXLarge.setColorFilter(SvgIcon.BLUE);
		closeRed.setColorFilter(SvgIcon.RED);
		cogBoxXLarge.setColorFilter(SvgIcon.BLUE);
		currencyUsd.setColorFilter(SvgIcon.BLUE);
		currencyUsdLarge.setColorFilter(SvgIcon.BLUE);
		currencyUsdWhiteLarge.setColorFilter(SvgIcon.WHITE);
		currencyUsdBlueXLarge.setColorFilter(SvgIcon.BLUE);
		emailOutlineGreenLarge.setColorFilter(SvgIcon.GREEN);
		fileDocumentOutlineBlue.setColorFilter(SvgIcon.BLUE);
		fileDocumentOutlineBlueLarge.setColorFilter(SvgIcon.BLUE);
		fileDocumentOutlineGreen.setColorFilter(SvgIcon.GREEN);
		fileDocumentOutlineGreenLarge.setColorFilter(SvgIcon.GREEN);
		fileDocumentOutlineWhiteLarge.setColorFilter(SvgIcon.WHITE);
		fileDocumentOutlineGreenXLarge.setColorFilter(SvgIcon.GREEN);
		fileContrast.setColorFilter(SvgIcon.CONTRAST);
		fileGreen.setColorFilter(SvgIcon.GREEN);
		fileOutlineContrast.setColorFilter(SvgIcon.CONTRAST);
		fileReplaceOutlineBlueXXLarge.setColorFilter(SvgIcon.BLUE);
		fileReplaceOutlineGreenXXLarge.setColorFilter(SvgIcon.GREEN);
		fileReplaceOutlinePurpleXXLarge.setColorFilter(SvgIcon.PURPLE);
		hammerScrewdriver.setColorFilter(SvgIcon.BLUE);
		hammerScrewdriverBlueXLarge.setColorFilter(SvgIcon.BLUE);
		hammerScrewdriverLarge.setColorFilter(SvgIcon.BLUE);
		hammerScrewdriverWhiteLarge.setColorFilter(SvgIcon.WHITE);
		map.setColorFilter(SvgIcon.YELLOW);
		mapXLarge.setColorFilter(SvgIcon.YELLOW);
		percentBox.setColorFilter(SvgIcon.RED);
		percentBoxXLarge.setColorFilter(SvgIcon.RED);
		phoneInTalkContrast.setColorFilter(SvgIcon.CONTRAST);
		phoneInTalkLarge.setColorFilter(SvgIcon.PURPLE);
		phoneInTalkPurple.setColorFilter(SvgIcon.PURPLE);
		phoneInTalkWhiteLarge.setColorFilter(SvgIcon.WHITE);
		qrcodeScanBlueLarge.setColorFilter(SvgIcon.BLUE);
		receiptOutlineContrast.setColorFilter(SvgIcon.CONTRAST);
		receiptOutlineLarge.setColorFilter(SvgIcon.RED);
		receiptOutlineWhiteLarge.setColorFilter(SvgIcon.WHITE);
		redo.setColorFilter(SvgIcon.BLUE);
		refreshBlueLarge.setColorFilter(SvgIcon.BLUE);
		sync.setColorFilter(SvgIcon.GREEN);
		syncXLarge.setColorFilter(SvgIcon.GREEN);
		timerOutlineContrast.setColorFilter(SvgIcon.CONTRAST);
		timerOutlineLarge.setColorFilter(SvgIcon.PURPLE);
		timerOutlinePurple.setColorFilter(SvgIcon.PURPLE);
		timerOutlineWarning.setColorFilter(SvgIcon.WARNING);
		timerOutlineWhiteLarge.setColorFilter(SvgIcon.WHITE);
		truckFastOutline.setColorFilter(SvgIcon.BLUE);
		truckFastOutlineLarge.setColorFilter(SvgIcon.BLUE);
		truckFastOutlineWhiteLarge.setColorFilter(SvgIcon.WHITE);
		truckFastOutlineXLarge.setColorFilter(SvgIcon.BLUE);
		undo.setColorFilter(SvgIcon.BLUE);
	}

}
