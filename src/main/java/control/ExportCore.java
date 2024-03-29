package control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.ExportRow;
import view.MainView;
import view.util.TRLocalizator;
import view.util.TRMessage;

public class ExportCore {
	private static final TRLocalizator LOC = new TRLocalizator(ExportCore.class);

	public static boolean doExport(final MainView parent, final String path, final String sheetName,
			final List<ExportRow> exportRows, final int nCols) {

		if (parent == null || sheetName == null || exportRows == null || nCols == 0) {
			return false;
		}

		try {
			TRMessage.showInfoDialog(parent, ExportCore.LOC.getRes("infExportStarted"));

			try (final Workbook workbook = new XSSFWorkbook();
					final FileOutputStream fileOut = new FileOutputStream(path)) {
				final Sheet sheet = workbook.createSheet(sheetName);

				sheet.setMargin(Sheet.LeftMargin, 0.6);
				sheet.setMargin(Sheet.RightMargin, 0.6);
				sheet.setMargin(Sheet.TopMargin, 1);
				sheet.setMargin(Sheet.BottomMargin, 1);
				sheet.setMargin(Sheet.HeaderMargin, 0.5);
				sheet.setMargin(Sheet.FooterMargin, 0.5);

				ExportCore.createBody(workbook, sheet, exportRows, nCols);

				for (int i = 0; i < exportRows.get(0).getCells().size(); i++) {
					sheet.autoSizeColumn(i);
				}

				workbook.write(fileOut);
			}
		} catch (final FileNotFoundException e) {
			TRMessage.showErrDialog(parent, ExportCore.LOC.getRes("errFileNotFoundException"));
			e.printStackTrace();
			return false;
		} catch (final IOException e) {
			TRMessage.showErrDialog(parent, ExportCore.LOC.getRes("errIOException"));
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static void createBody(final Workbook workbook, final Sheet sheet, final List<ExportRow> exportRows,
			final int nCols) {
		final CellStyle bodyCellStyle = workbook.createCellStyle();
		ExportCore.addCellBorder(bodyCellStyle);

		Row row;
		ExportRow exportRow;
		for (int i = 0; i < exportRows.size(); i++) {
			row = sheet.createRow(i);
			exportRow = exportRows.get(i);
			if (exportRow == null) {
				continue;
			}
			ExportCore.fillBodyRow(row, exportRow, bodyCellStyle, nCols);
		}
	}

	private static void fillBodyRow(final Row row, final ExportRow exportRow, final CellStyle bodyCellStyle,
			final int nCols) {
		Cell cell;
		String value;
		for (int i = 0; i < nCols; i++) {
			cell = ExportCore.createCell(row, i, bodyCellStyle);
			value = exportRow.getCellAt(i);
			cell.setCellValue(value != null ? value : "");
		}
	}

	private static void addCellBorder(final CellStyle cellStyle) {
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
	}

	private static Cell createCell(final Row row, final int pos, final CellStyle cellStyle) {
		final Cell cell = row.createCell(pos);
		cell.setCellStyle(cellStyle);
		return cell;
	}
}
