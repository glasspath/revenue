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
package org.glasspath.revenue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.glasspath.common.locale.LocaleUtils;
import org.glasspath.common.os.OsUtils;
import org.glasspath.common.os.preferences.PreferencesProvider;
import org.glasspath.common.swing.color.ColorUtils;
import org.glasspath.common.swing.file.chooser.FileChooser;
import org.glasspath.common.swing.preferences.CurrencyAndSymbolPreferenceComboBox;
import org.glasspath.common.swing.preferences.CurrencyPreferenceComboBox;
import org.glasspath.common.swing.preferences.LanguagePreferenceComboBox;
import org.glasspath.common.swing.preferences.UnitOfMeasurementPreferenceComboBox;
import org.glasspath.common.swing.resources.Resources;
import org.glasspath.common.swing.theme.Theme;
import org.glasspath.common.swing.theme.ThemeChooserPanel;
import org.glasspath.revenue.icons.Icons;

public class FirstLaunchFrame {

	public static final int ACTION_DEFAULT = 0;
	public static final int ACTION_SUBMIT = 1;
	public static final int ACTION_CANCEL = 2;

	private final Listener listener;
	private final Preferences preferences;
	private final File applicationDir;
	private final JFrame frame;
	private final ContentPanel contentPanel;
	private final JLabel statusLabel;
	private final JButton okButton;
	private final JButton cancelButton;

	private int action = ACTION_DEFAULT;

	public FirstLaunchFrame(Class<?> applicationClass, Listener listener) {

		this.listener = listener;

		preferences = Preferences.userNodeForPackage(applicationClass);

		// TODO: Check for null? (should not be possible)
		applicationDir = OsUtils.getApplicationJarFile(applicationClass).getParentFile();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle("Welcome to Glasspath - Revenue");
		frame.setIconImages(Icons.appIcon);
		frame.setResizable(false); // TODO: Disabled because the frame was being sent to the back quite often -> Enabled again after adding frame.toFront()
		frame.setSize(800, 600);

		frame.addWindowListener(new WindowAdapter() {

			boolean inited = false;

			@Override
			public void windowActivated(WindowEvent e) {
				if (!inited) {
					frame.toFront();
					inited = true;
				}
			}

			@Override
			public void windowClosing(WindowEvent e) {
				performAction(ACTION_CANCEL);
			}
		});

		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[] { 0.1, 0.0, 0.0 };
		layout.rowHeights = new int[] { 100, 1, 10 };
		layout.columnWeights = new double[] { 0.1 };
		layout.columnWidths = new int[] { 100 };
		frame.getContentPane().setLayout(layout);

		contentPanel = new ContentPanel();
		frame.getContentPane().add(contentPanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

		JSeparator footerSeparator = new JSeparator();
		footerSeparator.setMinimumSize(new Dimension(100, 4));
		frame.getContentPane().add(footerSeparator, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		JPanel footer = new JPanel();
		footer.setLayout(new BoxLayout(footer, BoxLayout.LINE_AXIS));
		footer.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		statusLabel = new JLabel();
		footer.add(statusLabel);

		footer.add(Box.createHorizontalGlue());

		okButton = new JButton(Resources.getString("Ok")); //$NON-NLS-1$
		footer.add(okButton);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performAction(ACTION_SUBMIT);
			}
		});

		footer.add(Box.createRigidArea(new Dimension(5, 5)));

		cancelButton = new JButton(Resources.getString("Cancel")); //$NON-NLS-1$
		footer.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				performAction(ACTION_CANCEL);
			}
		});

		frame.getContentPane().add(footer, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// TODO? This is a little bit of a hack, we always disable the ok/cancel buttons when the user
		// submits/cancels, this way the buttons are repainted before the main application is started
		okButton.addPropertyChangeListener("enabled", new PropertyChangeListener() { //$NON-NLS-1$

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (action == ACTION_SUBMIT && !okButton.isEnabled()) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							submit();
						}
					});
				}
			}
		});
		cancelButton.addPropertyChangeListener("enabled", new PropertyChangeListener() { //$NON-NLS-1$

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (action == ACTION_CANCEL && !cancelButton.isEnabled()) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							cancel();
						}
					});
				}
			}
		});

		validateInput();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	private void validateInput() {

		boolean projectLocationValid = ProjectUtils.isValidParentPathForNewProject(contentPanel.projectLocationTextField.getText());
		if (projectLocationValid) {
			contentPanel.projectLocationTextField.setBackground(contentPanel.defaultFieldBackground);
		} else {
			contentPanel.projectLocationTextField.setBackground(ColorUtils.INVALID_INPUT_BACKGROUND);
		}

		boolean projecNameValid = ProjectUtils.getDirFileForNewProject(contentPanel.projectLocationTextField.getText(), contentPanel.projectNameTextField.getText()) != null;
		if (projecNameValid) {
			contentPanel.projectNameTextField.setBackground(contentPanel.defaultFieldBackground);
		} else {
			contentPanel.projectNameTextField.setBackground(ColorUtils.INVALID_INPUT_BACKGROUND);
		}

		if (!projectLocationValid) {
			statusLabel.setIcon(org.glasspath.common.icons.Icons.alertLarge);
			statusLabel.setText("Project location is not valid");
		} else if (!projecNameValid) {
			statusLabel.setIcon(org.glasspath.common.icons.Icons.alertLarge);
			statusLabel.setText("Project name is not valid");
		} else if (contentPanel.themeChooserPanel.isDarkThemeSelected()) {
			statusLabel.setIcon(org.glasspath.common.icons.Icons.alertLarge);
			statusLabel.setText("Your theme will be applied the next time the application is launched");
		} else {
			statusLabel.setIcon(null);
			statusLabel.setText("");
		}

		okButton.setEnabled(projectLocationValid && projecNameValid);

	}

	private void performAction(int action) {

		if (this.action == ACTION_DEFAULT) {

			this.action = action;

			statusLabel.setIcon(null);
			statusLabel.setText(""); //$NON-NLS-1$

			cancelButton.setEnabled(false); // Property listener will perform the actual submit action
			okButton.setEnabled(false); // Property listener will perform the actual cancel action

		}

	}

	private void submit() {

		if (contentPanel.themeChooserPanel.isDarkThemeSelected()) {
			preferences.put("theme", Theme.THEME_DARK.getId()); //$NON-NLS-1$
		} else {
			preferences.remove("theme"); //$NON-NLS-1$
		}

		contentPanel.languageComboBox.commit();
		contentPanel.currencyComboBox.commit();
		contentPanel.unitOfMeasurementComboBox.commit();

		statusLabel.setText("Creating Project..");

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				File projectDir = ProjectUtils.createNewProject(contentPanel.projectLocationTextField.getText(), contentPanel.projectNameTextField.getText(), applicationDir);

				// TODO: Check if project was successfully created? For now we prefer
				// to launch Revenue (will show a message if project cannot be opened)

				statusLabel.setText("Launching Revenue..");

				listener.submit(frame, projectDir);

			}
		});

	}

	private void cancel() {
		listener.cancel(frame);
	}

	private class ContentPanel extends JPanel {

		private final ThemeChooserPanel themeChooserPanel;
		private final LanguagePreferenceComboBox languageComboBox;
		private final CurrencyAndSymbolPreferenceComboBox currencyComboBox;
		private final UnitOfMeasurementPreferenceComboBox unitOfMeasurementComboBox;
		private final JTextField projectLocationTextField;
		private final JTextField projectNameTextField;
		private final Color defaultFieldBackground;

		public ContentPanel() {

			GridBagLayout layout = new GridBagLayout();
			layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1 };
			layout.rowHeights = new int[] { 25, 250, 30, 20, 5, 35, 30, 20, 5, 35, 25 };
			layout.columnWeights = new double[] { 0.1, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.1 };
			layout.columnWidths = new int[] { 25, 40, 200, 15, 200, 15, 200, 40, 25 };
			setLayout(layout);

			themeChooserPanel = new ThemeChooserPanel() {

				@Override
				public void selectionChanged(boolean darkThemeSelected) {
					validateInput();
				}
			};
			add(themeChooserPanel, new GridBagConstraints(1, 1, 7, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			PreferencesProvider preferencesProvider = new PreferencesProvider(preferences);

			languageComboBox = new LanguagePreferenceComboBox(preferencesProvider, "language", "", LocaleUtils.BASIC_LANGUAGE_TAGS, false) { //$NON-NLS-1$ //$NON-NLS-2$

				// TODO: Move to Utils
				@Override
				protected boolean isLanguageSupported(String languageTag) {
					return languageTag != null && ( //
					languageTag.startsWith("en") || //$NON-NLS-1$
							languageTag.startsWith("nl")); //$NON-NLS-1$
				}
			};
			languageComboBox.setBorder(BorderFactory.createCompoundBorder(languageComboBox.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 3)));
			languageComboBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					selectedLocaleChanged();
				}
			});

			currencyComboBox = new CurrencyAndSymbolPreferenceComboBox(preferencesProvider, "currency", "", "currencySymbol", "", false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			currencyComboBox.setBorder(BorderFactory.createCompoundBorder(currencyComboBox.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 3)));

			add(new JLabel("Currency & Symbol"), new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			add(currencyComboBox, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			unitOfMeasurementComboBox = new UnitOfMeasurementPreferenceComboBox(preferencesProvider, "unitOfMeasurement", "", false); //$NON-NLS-1$ //$NON-NLS-2$
			unitOfMeasurementComboBox.setBorder(BorderFactory.createCompoundBorder(unitOfMeasurementComboBox.getBorder(), BorderFactory.createEmptyBorder(0, 0, 0, 3)));

			add(new JLabel("Unit of measurement"), new GridBagConstraints(6, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			add(unitOfMeasurementComboBox, new GridBagConstraints(6, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			// TODO: LanguageComboBox is causing layout problems, this is a workaround..
			JPanel languageComboBoxWrapper = new JPanel() {

				@Override
				public Dimension getPreferredSize() {
					return currencyComboBox.getPreferredSize();
				}
			};
			languageComboBoxWrapper.setLayout(new BorderLayout());
			languageComboBoxWrapper.add(languageComboBox, BorderLayout.CENTER);

			add(new JLabel("Language"), new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			add(languageComboBoxWrapper, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			// TODO: LanguageComboBox is causing layout problems, this is a workaround..
			JPanel projectPathPanel = new JPanel();
			add(projectPathPanel, new GridBagConstraints(2, 7, 5, 3, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			GridBagLayout projectPathPanelLayout = new GridBagLayout();
			projectPathPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
			projectPathPanelLayout.rowHeights = new int[] { 20, 5, 35 };
			projectPathPanelLayout.columnWeights = new double[] { 0.1, 0.0, 0.1 };
			projectPathPanelLayout.columnWidths = new int[] { 190, 15, 190 };
			projectPathPanel.setLayout(projectPathPanelLayout);

			String userHomeDir = System.getProperty("user.home"); //$NON-NLS-1$
			userHomeDir = userHomeDir.replace("\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$

			JButton browseButton = new JButton();
			browseButton.setIcon(Icons.folderOutline);
			browseButton.setBorderPainted(false);
			browseButton.setFocusable(false);
			browseButton.setCursor(Cursor.getDefaultCursor());

			projectLocationTextField = new JTextField(userHomeDir) {

				@Override
				public void doLayout() {
					super.doLayout();

					int size = getHeight() - 8;
					if (size < 10) {
						size = 10;
					}

					browseButton.setBounds(getWidth() - (size + 4), 4, size, size);

				}
			};
			projectLocationTextField.setMargin(new Insets(2, 5, 2, 32));
			projectLocationTextField.setLayout(null);
			projectLocationTextField.add(browseButton);

			browseButton.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {

					String dirPath = FileChooser.browseForDir(frame, preferences, "lastSelectedProjectLocation", null);
					if (dirPath != null) {
						projectLocationTextField.setText(dirPath);
					}

				}
			});

			projectPathPanel.add(new JLabel("Project location"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			projectPathPanel.add(projectLocationTextField, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			projectNameTextField = new JTextField("Glasspath Revenue Files");
			projectPathPanel.add(new JLabel("Project name"), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			projectPathPanel.add(new JLabel("/"), new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0)); //$NON-NLS-1$
			projectPathPanel.add(projectNameTextField, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

			defaultFieldBackground = projectNameTextField.getBackground();

			DocumentListener documentListener = new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					validateInput();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					validateInput();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					validateInput();
				}
			};
			projectLocationTextField.getDocument().addDocumentListener(documentListener);
			projectNameTextField.getDocument().addDocumentListener(documentListener);

			selectedLocaleChanged();

		}

		private void selectedLocaleChanged() {

			Locale locale = null;

			String languageTag = languageComboBox.getSelectedLanguageTag();
			if (languageTag != null) {
				locale = LocaleUtils.getLocaleForTag(languageTag);
			}

			if (locale == null) {
				locale = Locale.getDefault();
			}

			currencyComboBox.setAutomaticLocale(locale, true);
			unitOfMeasurementComboBox.setAutomaticLocale(locale, true);

		}

	}

	public static interface Listener {

		public abstract void submit(JFrame frame, File projectDir);

		public abstract void cancel(JFrame frame);

	}

}
