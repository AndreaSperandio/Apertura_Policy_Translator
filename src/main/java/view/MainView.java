package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.ExportCore;
import control.ImportCore;
import model.ExportRow;
import model.ImportedRow;
import model.Language;
import model.Translation;
import view.component.TRButton;
import view.component.TRCheckBox;
import view.component.TRTextField;
import view.util.TRColor;
import view.util.TRLocalizator;
import view.util.TRMessage;
import view.util.TRResource;

public class MainView extends JFrame {
	private static final long serialVersionUID = 846132440578478084L;

	private static final TRLocalizator LOC = new TRLocalizator(MainView.class);
	private static final String DESKTOP_FOLDER = System.getProperty("user.home") + File.separator + "Desktop";
	private static final String FILE_EXTENSION = ".xlsx";

	private final JLabel lblInstruction = new JLabel(MainView.LOC.getRes("lblInstruction"));
	private final JLabel lblDocument = new JLabel(MainView.LOC.getRes("lblDocument"));
	private final TRButton btnDocument = new TRButton(MainView.LOC.getRes("btnLoad"));
	private final JLabel lblDocumentFile = new JLabel();
	private final JLabel lblTable = new JLabel(MainView.LOC.getRes("lblTable"));
	private final TRTextField txtTable = new TRTextField();
	private final JLabel lblColumnApertura = new JLabel(MainView.LOC.getRes("lblColumnApertura"));
	private final TRTextField txtColumnApertura = new TRTextField();
	private final JLabel lblColumnPolicy = new JLabel(MainView.LOC.getRes("lblColumnPolicy"));
	private final TRTextField txtColumnPolicy = new TRTextField();
	private final TRCheckBox chkQuery = new TRCheckBox(MainView.LOC.getRes("chkQuery"));
	private final TRCheckBox chkPolicy = new TRCheckBox(MainView.LOC.getRes("chkPolicy"));
	private final TRButton btnTransform = new TRButton(TRResource.getStartImage());
	private final JLabel lblTransform = new JLabel(MainView.LOC.getRes("lblTransform"));
	private final JLabel lblExportPath = new JLabel(MainView.LOC.getRes("lblExportPath"));
	private final JLabel lblExportPathWrn = new JLabel(MainView.LOC.getRes("lblExportPathWrn"));
	private final TRButton btnExportPath = new TRButton(MainView.LOC.getRes("btnExportPath"));
	private final JLabel lblExportFile = new JLabel();
	private final TRButton btnExport = new TRButton(TRResource.getExportImage());
	private final JLabel lblExport = new JLabel(MainView.LOC.getRes("lblExport"));
	private final JLabel lblAuthor = new JLabel(MainView.LOC.getRes("lblAuthor"));

	private File documentFile = null;
	private File exportFile = null;

	private List<ImportedRow> importedRows = null;
	private List<ExportRow> exportRows = null;

	public MainView() {
		this.setup();
		this.init();
	}

	private void setup() {
		this.setTitle(MainView.LOC.getRes("title"));
		final Dimension dimension = new Dimension(510, 610);// Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dimension);
		this.setPreferredSize(dimension);
		// this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setIconImages(TRResource.getLogoIcons());
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				//if (TMMessage.showConfirmWarnDialog(MainView.this, MainView.LOC.getRes("cnfExit"))) {
				MainView.this.dispose();
				System.exit(0);
				//}
			}
		});
		this.setLayout(null);

		this.add(this.lblInstruction);
		this.add(this.lblDocument);
		this.add(this.btnDocument);
		this.add(this.lblDocumentFile);
		this.add(this.lblTable);
		this.add(this.txtTable);
		this.add(this.lblColumnApertura);
		this.add(this.txtColumnApertura);
		this.add(this.lblColumnPolicy);
		this.add(this.txtColumnPolicy);
		this.add(this.chkQuery);
		this.add(this.chkPolicy);
		this.add(this.btnTransform);
		this.add(this.lblTransform);
		this.add(this.lblExportPath);
		this.add(this.lblExportPathWrn);
		this.add(this.btnExportPath);
		this.add(this.lblExportFile);
		this.add(this.btnExport);
		this.add(this.lblExport);
		this.add(this.lblAuthor);

		final int height = 20;
		final int margin = height + 10;
		final int x = 20;
		int y = 10;
		this.lblInstruction.setBounds(x, y, 560, height * 3);
		y += 80;
		this.lblDocument.setBounds(x, y, 500, height);
		y += margin;
		this.btnDocument.setBounds(x, y, 100, height);
		this.lblDocumentFile.setBounds(x + 120, y, 400, height);
		y += 50;
		this.chkQuery.setBounds(x, y, 200, height);
		this.chkPolicy.setBounds(x + 200, y, 200, height);
		y += 40;
		this.lblTable.setBounds(x, y, 100, height);
		this.txtTable.setBounds(130, y, 200, height);
		y += margin;
		this.lblColumnApertura.setBounds(x, y, 100, height);
		this.txtColumnApertura.setBounds(130, y, 200, height);
		y += margin;
		this.lblColumnPolicy.setBounds(x, y, 100, height);
		this.txtColumnPolicy.setBounds(130, y, 200, height);
		y += 50;
		this.btnTransform.setBounds(x, y, 35, height + 10);
		this.lblTransform.setBounds(x + 45, y, 200, height + 10);
		y += 70;
		this.lblExportPath.setBounds(x, y, 100, height);
		this.lblExportPathWrn.setBounds(x + 120, y, 400, height);
		y += margin;
		this.btnExportPath.setBounds(x, y, 100, height);
		this.lblExportFile.setBounds(x + 120, y, 400, height);
		y += 50;
		this.btnExport.setBounds(x, y, 35, height + 10);
		this.lblExport.setBounds(x + 45, y, 200, height + 10);
		y += 50;
		this.lblAuthor.setBounds(dimension.width - 150, y, 120, height);

		this.lblDocument.setToolTipText(MainView.LOC.getRes("lblDocumentToolTip"));
		this.lblTable.setToolTipText(MainView.LOC.getRes("lblTableToolTip"));
		this.lblColumnApertura.setToolTipText(MainView.LOC.getRes("lblColumnAperturaToolTip"));
		this.lblColumnPolicy.setToolTipText(MainView.LOC.getRes("lblColumnPolicyToolTip"));
		this.chkQuery.setToolTipText(MainView.LOC.getRes("chkQueryToolTip"));
		this.chkPolicy.setToolTipText(MainView.LOC.getRes("chkPolicyToolTip"));
		this.lblTransform.setToolTipText(MainView.LOC.getRes("lblTransformToolTip"));
		this.lblExportPath.setToolTipText(MainView.LOC.getRes("lblExportPathToolTip"));

		this.lblDocumentFile.setForeground(TRColor.LBL_BLUE);
		this.lblExportFile.setForeground(TRColor.LBL_BLUE);
		this.lblExportPathWrn.setForeground(TRColor.LBL_ORANGE);
		this.lblAuthor.setForeground(TRColor.LBL_BLUE);

		this.btnDocument.addActionListener(e -> {
			this.btnFileChooserActionPerformed(this.btnDocument, MainView.LOC.getRes("jfcDocument"),
					this.lblDocumentFile);
			this.updateGraphics();
		});
		this.btnTransform.addActionListener(e -> {
			this.btnMatchActionPerformed();
			this.updateGraphics();
		});
		this.btnExportPath.addActionListener(e -> {
			this.btnFileChooserActionPerformed(this.btnExport, MainView.LOC.getRes("jfcExport"), this.lblExportFile);
			this.updateGraphics();
		});
		this.btnExport.addActionListener(e -> this.btnExportActionPerformed());

		this.chkQuery.addChangeListener(e -> {
			this.updateGraphics();
		});
		this.chkPolicy.addChangeListener(e -> {
			this.updateGraphics();
		});
	}

	private void init() {
		this.chkQuery.setSelected(true);
		this.chkPolicy.setSelected(true);

		this.updateGraphics();
		this.setVisible(true);
	}

	private void updateGraphics() {
		this.chkQuery.setEnabled(this.documentFile != null);
		this.chkPolicy.setEnabled(this.documentFile != null);
		this.txtTable.setEnabled(this.documentFile != null && this.chkQuery.isSelected());
		this.txtColumnApertura.setEnabled(this.documentFile != null && this.chkQuery.isSelected());
		this.txtColumnPolicy
				.setEnabled(this.documentFile != null && this.chkQuery.isSelected() && this.chkPolicy.isSelected());
		this.btnTransform.setEnabled(this.documentFile != null);
		this.btnExportPath.setEnabled(this.exportRows != null && !this.exportRows.isEmpty());
		this.btnExport.setEnabled(this.exportFile != null);

		this.lblExportPathWrn.setVisible(MainView.checkFile(this.exportFile, false));
	}

	private void btnFileChooserActionPerformed(final TRButton caller, final String title, final JLabel label) {
		final FileNameExtensionFilter filter = new FileNameExtensionFilter("MS Excel Document (2007+)", "xlsx");
		final JFileChooser jfc = new JFileChooser(MainView.DESKTOP_FOLDER);
		jfc.setFileFilter(filter);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setMultiSelectionEnabled(false);
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setDialogTitle(title);

		final int retVaule = jfc.showOpenDialog(caller);
		if (retVaule == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			if (caller == this.btnDocument) {
				this.documentFile = file;
				this.importedRows = null;
				this.exportRows = null;
			} else {
				if (!file.getName().endsWith(MainView.FILE_EXTENSION)) {
					file = new File(file.getAbsolutePath() + MainView.FILE_EXTENSION);
				}
				this.exportFile = file;
			}
			label.setText(file.getName());
		} else if (retVaule == JFileChooser.CANCEL_OPTION) {
			if (caller == this.btnDocument) {
				this.documentFile = null;
				this.importedRows = null;
				this.exportRows = null;
			} else {
				this.exportFile = null;
			}
			label.setText(null);
		}
	}

	private void btnMatchActionPerformed() {
		if (!this.checkParams()) {
			return;
		}

		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		ImportCore.importRows(this, this.documentFile);
	}

	private void btnExportActionPerformed() {
		if (!this.checkExportParams()) {
			return;
		}

		int nCols = 11;
		if (this.chkQuery.isSelected()) {
			nCols = this.chkPolicy.isSelected() ? 11 : 6;
		} else {
			nCols = this.chkPolicy.isSelected() ? 8 : 4;
		}

		if (ExportCore.doExport(this, this.exportFile.getAbsolutePath(), "Foglio 1", this.exportRows, nCols)) {
			if (TRMessage.showConfirmDialog(this, MainView.LOC.getRes("cnfExported"))) {
				try {
					Desktop.getDesktop().open(this.exportFile);
				} catch (final IOException e) {
					TRMessage.showErrDialog(this, MainView.LOC.getRes("errInternalError"));
					e.printStackTrace();
				}
			}

			this.lblDocumentFile.setText(null);
			this.lblExportFile.setText(null);
			this.documentFile = null;
			this.exportFile = null;
			this.importedRows = null;
			this.exportRows = null;
			this.updateGraphics();
		}
	}

	private boolean checkParams() {
		if (!MainView.checkFile(this.documentFile, false)) {
			TRMessage.showErrDialog(this, MainView.LOC.getRes("errDocumentFile"));
			return false;
		}

		if (this.chkQuery.isSelected()) {
			if (this.txtTable.isEmpty()) {
				TRMessage.showErrDialog(this, MainView.LOC.getRes("errTable"));
				return false;
			}
			if (this.txtColumnApertura.isEmpty()) {
				TRMessage.showErrDialog(this, MainView.LOC.getRes("errColumnApertura"));
				return false;
			}
			if (this.chkPolicy.isSelected() && this.txtColumnPolicy.isEmpty()) {
				TRMessage.showErrDialog(this, MainView.LOC.getRes("errColumnPolicy"));
				return false;
			}
		}

		return true;
	}

	private boolean checkExportParams() {
		if (!MainView.checkFile(this.exportFile, true)) {
			TRMessage.showErrDialog(this, MainView.LOC.getRes("errExportPath"));
			return false;
		}

		return true;
	}

	private static boolean checkFile(final File file, final boolean createIfAbsent) {
		if (file == null || !file.exists() || !file.isFile()) {
			if (createIfAbsent) {
				try {
					file.createNewFile();
					return true;
				} catch (final IOException e) {
					//TRMessage.showErrDialog(this, MainView.LOC.getRes("errInternalError"));
					e.printStackTrace();
				}
			}
			return false;
		}

		return file.getName().endsWith(MainView.FILE_EXTENSION);
	}

	public void notifyImportCompleted(final List<ImportedRow> _importedRows) {
		if (_importedRows == null || _importedRows.isEmpty()) {
			TRMessage.showWarnDialog(this, MainView.LOC.getRes("wrnNoRowsFound"));
			return;
		}

		int nCols = 3;
		if (this.chkQuery.isSelected()) {
			nCols = this.chkPolicy.isSelected() ? 3 : 2;
		} else {
			nCols = this.chkPolicy.isSelected() ? 2 : 1;
		}

		if (_importedRows.get(0).getCells() == null || _importedRows.get(0).getCells().size() < nCols) {
			TRMessage.showErrDialog(this, MainView.LOC.getRes("errNotEnoughData"));
			return;
		}

		this.importedRows = _importedRows;
		this.importStrings();
	}

	private void importStrings() {
		final List<ExportRow> _exportRows = new ArrayList<>();
		final ExportRow header = new ExportRow();

		int posId = 0;
		int posApertura = 1;
		int posPolicy = 2;
		if (this.chkQuery.isSelected()) {
			if (this.chkPolicy.isSelected()) {
				posId = 0;
				posApertura = 1;
				posPolicy = 2;
			} else {
				posId = 0;
				posApertura = 1;
			}
		} else {
			if (this.chkPolicy.isSelected()) {
				posApertura = 0;
				posPolicy = 1;
			} else {
				posApertura = 0;
			}
		}

		if (this.chkQuery.isSelected()) {
			header.addCell(this.importedRows.get(0).getStringAt(posId));
		}
		final String headerApertura = this.importedRows.get(0).getStringAt(posApertura);
		header.addCell(headerApertura);
		header.addCell(headerApertura + " - " + Language.EN);
		header.addCell(headerApertura + " - " + Language.DE);
		header.addCell(headerApertura + " - " + Language.FR);
		if (this.chkQuery.isSelected()) {
			header.addCell("Query");
		}
		if (this.chkPolicy.isSelected()) {
			final String headerPolicy = this.importedRows.get(0).getStringAt(posPolicy);
			header.addCell(headerPolicy);
			header.addCell(headerPolicy + " - " + Language.EN);
			header.addCell(headerPolicy + " - " + Language.DE);
			header.addCell(headerPolicy + " - " + Language.FR);
			if (this.chkQuery.isSelected()) {
				header.addCell("Query");
			}
		}

		_exportRows.add(header);

		ImportedRow importedRow;
		ExportRow exportRow;
		final StringBuilder notImported = new StringBuilder();
		int importedCount = 0;
		int notImportedCount = 0;
		for (int i = 1; i < this.importedRows.size(); i++) { //Ignore header
			importedRow = this.importedRows.get(i);
			exportRow = new ExportRow();

			if (this.chkQuery.isSelected()) {
				exportRow.addCell(importedRow.getStringAt(posId));
			}
			if (!this.importData(importedRow, true, posApertura, exportRow, importedRow.getStringAt(posId))) {
				notImported.append(importedRow.getStringAt(0) + "- Apertura\n");
				notImportedCount++;
			}
			if (this.chkPolicy.isSelected()) {
				if (!this.importData(importedRow, false, posPolicy, exportRow, importedRow.getStringAt(posId))) {
					notImported.append(importedRow.getStringAt(0) + "- Policy\n");
					notImportedCount++;
				}
			}
			importedCount++;
			_exportRows.add(exportRow);
		}

		TRMessage.showInfoDialog(this, MainView.LOC.getRes("infNImported", importedCount));

		if (!notImported.isEmpty()) {
			TRMessage.showWarnDialog(this, MainView.LOC.getRes("wrnNotImported", notImportedCount, notImported));
		}

		this.exportRows = _exportRows;
		this.setCursor(Cursor.getDefaultCursor());
		this.updateGraphics();
	}

	private boolean importData(final ImportedRow importedRow, final boolean isApertura, final int pos,
			final ExportRow exportRow, final String id) {
		final String value = importedRow.getStringAt(pos);
		final String column = isApertura ? this.txtColumnApertura.getText() : this.txtColumnPolicy.getText();
		final String queryStart = "UPDATE " + this.txtTable.getText() + " SET ";
		final String queryEnd = " WHERE ID = " + id + ";";

		exportRow.addCell(value);
		final String[] valueTransl = Translation.translate(value);
		if (valueTransl == null) {
			for (int j = 0; j < Language.LANGUAGES - 1; j++) {
				exportRow.addCell("");
			}

			if (this.chkQuery.isSelected()) {
				final StringBuilder queryB = new StringBuilder();
				for (final Language lang : Language.values()) {
					queryB.append(column + lang.toString().toLowerCase() + " = NULL, ");
				}

				exportRow.addCell(
						queryStart + queryB.toString().substring(0, queryB.toString().length() - 2) + queryEnd);
			}
			return value == null || "".equals(value);
		}

		for (int j = 0; j < Language.LANGUAGES - 1; j++) {
			exportRow.addCell(valueTransl[j]);
		}
		if (this.chkQuery.isSelected()) {
			final StringBuilder queryB = new StringBuilder();
			queryB.append(column + Language.IT.toString().toLowerCase() + " = \""
					+ value.replace("'", "\\\\'").replace("\"", "\\\\\"") + "\", ");
			for (int j = 0; j < Language.LANGUAGES - 1; j++) {
				queryB.append(column + Language.values()[j + 1].toString().toLowerCase() + " = \""
						+ valueTransl[j].replace("'", "\\\\'").replace("\"", "\\\\\"") + "\", ");
			}
			exportRow.addCell(queryStart + queryB.toString().substring(0, queryB.toString().length() - 2) + queryEnd);
		}
		return true;
	}

	public static void main(final String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				final MainView frame = new MainView();
				frame.setVisible(true);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});

	}
}
