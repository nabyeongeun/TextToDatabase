package ttd.textToDatabase.common

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet

class Xlsx {

    companion object {
        fun getCellContents(sheet: Sheet, rowIndex: Int, cellIndex: Int) : String? {

            val cell = getCell(sheet, rowIndex, cellIndex)

            return if(cell == null)
                null
            else {
                when(cell.cellType) {
                    CellType.STRING  -> cell.stringCellValue
                    CellType.NUMERIC -> cell.numericCellValue.toLong().toString()
                    else -> {null}
                }
            }
        }

        fun getCell(sheet: Sheet, rowIndex: Int, cellIndex: Int) : Cell? {
            return sheet.getRow(rowIndex)?.getCell(cellIndex)
        }
    }
}