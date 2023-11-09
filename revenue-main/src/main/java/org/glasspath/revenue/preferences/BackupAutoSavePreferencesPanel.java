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
package org.glasspath.revenue.preferences;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.glasspath.common.os.preferences.BoolPref;
import org.glasspath.common.os.preferences.IntPref;
import org.glasspath.revenue.resources.Resources;

public class BackupAutoSavePreferencesPanel extends JPanel {

	public static final int MIN_AUTO_SAVE_INTERVAL = 1;
	public static final int DEFAULT_AUTO_SAVE_INTERVAL = 10;
	public static final int MAX_AUTO_SAVE_INTERVAL = 300;

	public static final IntPref MAX_PROJECT_BACKUPS = new IntPref("maxProjectBackups", 100); //$NON-NLS-1$
	public static final IntPref MAX_SYNC_BACKUPS = new IntPref("maxSyncBackups", 100); //$NON-NLS-1$
	public static final BoolPref AUTO_SAVE_CHANGES_ENABLED = new BoolPref("autoSaveChangesEnabled", true); //$NON-NLS-1$
	public static final IntPref AUTO_SAVE_INTERVAL = new IntPref("autoSaveInterval", DEFAULT_AUTO_SAVE_INTERVAL); //$NON-NLS-1$
	public static final BoolPref AUTO_SAVE_SYNC_STEP_2_ENABLED = new BoolPref("autoSaveSyncStep2Enabled", true); //$NON-NLS-1$
	public static final BoolPref AUTO_SAVE_EXIT_ENABLED = new BoolPref("autoSaveExitEnabled", true); //$NON-NLS-1$

	private final Preferences preferences;
	private final JSpinner maxProjectBackupsSpinner;
	private final JSpinner maxSyncBackupsSpinner;
	private final JCheckBox autoSaveChangesEnabledCheckBox;
	private final JSpinner autoSaveIntervalSpinner;
	private final JCheckBox autoSaveSyncStep2EnabledCheckBox;
	private final JCheckBox autoSaveExitEnabledCheckBox;

	public BackupAutoSavePreferencesPanel(Preferences preferences) {

		this.preferences = preferences;

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.1 };
		layout.rowHeights = new int[] { 25, 5, 25, 5 };
		layout.columnWeights = new double[] { 0.1 };
		layout.columnWidths = new int[] { 250 };
		setLayout(layout);

		JPanel backupPanel = new JPanel();
		add(backupPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		GridBagLayout backupPanelLayout = new GridBagLayout();
		backupPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		backupPanelLayout.rowHeights = new int[] { 7, 23, 5, 23, 7 };
		backupPanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.1 };
		backupPanelLayout.columnWidths = new int[] { 7, 150, 5, 100, 7 };
		backupPanel.setLayout(backupPanelLayout);
		backupPanel.setBorder(BorderFactory.createTitledBorder(Resources.getString("Backup"))); //$NON-NLS-1$

		maxProjectBackupsSpinner = new JSpinner(new SpinnerNumberModel(MAX_PROJECT_BACKUPS.get(preferences), 10, 1000, 1));
		backupPanel.add(new JLabel(Resources.getString("MaxProjectBackups")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		backupPanel.add(maxProjectBackupsSpinner, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		maxSyncBackupsSpinner = new JSpinner(new SpinnerNumberModel(MAX_SYNC_BACKUPS.get(preferences), 10, 1000, 1));
		backupPanel.add(new JLabel(Resources.getString("MaxSyncBackups")), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		backupPanel.add(maxSyncBackupsSpinner, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		JPanel autoSavePanel = new JPanel();
		add(autoSavePanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		GridBagLayout autoSavePanelLayout = new GridBagLayout();
		autoSavePanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		autoSavePanelLayout.rowHeights = new int[] { 7, 23, 5, 23, 5, 23, 5, 23, 7 };
		autoSavePanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
		autoSavePanelLayout.columnWidths = new int[] { 7, 150, 5, 100, 5, 100 };
		autoSavePanel.setLayout(autoSavePanelLayout);
		autoSavePanel.setBorder(BorderFactory.createTitledBorder(Resources.getString("AutoSave"))); //$NON-NLS-1$

		autoSaveChangesEnabledCheckBox = new JCheckBox(Resources.getString("AutomaticallySaveChanges")); //$NON-NLS-1$
		autoSaveChangesEnabledCheckBox.setSelected(AUTO_SAVE_CHANGES_ENABLED.get(preferences));
		autoSavePanel.add(new JLabel(Resources.getString("EnableAutoSave")), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		autoSavePanel.add(autoSaveChangesEnabledCheckBox, new GridBagConstraints(3, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		autoSaveChangesEnabledCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				autoSaveIntervalSpinner.setEnabled(autoSaveChangesEnabledCheckBox.isSelected());
			}
		});

		autoSaveIntervalSpinner = new JSpinner(new SpinnerNumberModel(AUTO_SAVE_INTERVAL.get(preferences), MIN_AUTO_SAVE_INTERVAL, MAX_AUTO_SAVE_INTERVAL, 1));
		autoSaveIntervalSpinner.setEnabled(autoSaveChangesEnabledCheckBox.isSelected());
		autoSavePanel.add(new JLabel(Resources.getString("AutoSaveInterval")), new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		autoSavePanel.add(autoSaveIntervalSpinner, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		autoSavePanel.add(new JLabel(Resources.getString("Seconds")), new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$

		autoSaveSyncStep2EnabledCheckBox = new JCheckBox(Resources.getString("AutomaticallySaveProjectAfterSynchronizing")); //$NON-NLS-1$
		autoSaveSyncStep2EnabledCheckBox.setSelected(AUTO_SAVE_SYNC_STEP_2_ENABLED.get(preferences));
		autoSavePanel.add(new JLabel(Resources.getString("AutoSaveAfterSync")), new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		autoSavePanel.add(autoSaveSyncStep2EnabledCheckBox, new GridBagConstraints(3, 5, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		autoSaveExitEnabledCheckBox = new JCheckBox(Resources.getString("AutomaticallySaveProjectAtExit")); //$NON-NLS-1$
		autoSaveExitEnabledCheckBox.setSelected(AUTO_SAVE_EXIT_ENABLED.get(preferences));
		autoSavePanel.add(new JLabel(Resources.getString("AutoSaveAtExit")), new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
		autoSavePanel.add(autoSaveExitEnabledCheckBox, new GridBagConstraints(3, 7, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	}

	protected boolean submit() {

		MAX_PROJECT_BACKUPS.put(preferences, (Integer) maxProjectBackupsSpinner.getValue());
		MAX_SYNC_BACKUPS.put(preferences, (Integer) maxSyncBackupsSpinner.getValue());

		AUTO_SAVE_CHANGES_ENABLED.put(preferences, autoSaveChangesEnabledCheckBox.isSelected());
		AUTO_SAVE_INTERVAL.put(preferences, (Integer) autoSaveIntervalSpinner.getValue());
		AUTO_SAVE_SYNC_STEP_2_ENABLED.put(preferences, autoSaveSyncStep2EnabledCheckBox.isSelected());
		AUTO_SAVE_EXIT_ENABLED.put(preferences, autoSaveExitEnabledCheckBox.isSelected());

		return false;

	}

}
