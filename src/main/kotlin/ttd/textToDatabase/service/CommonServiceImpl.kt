package ttd.textToDatabase.service

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToInt

@Service
class CommonServiceImpl : CommonService {
    override fun initPage(): Map<String,Any> {

        val returnObject : MutableMap<String,Any> = HashMap()
        returnObject["status"] = "SUCCESS"

        return returnObject
    }

    override fun uploadFile(filePath:String): MutableMap<String, Any> {

        val returnObject = HashMap<String,Any>()

        val ext = filePath.substring(filePath.lastIndexOf("."))

        if(ext.equals(".txt", true)) {
            readText(filePath)
        } else if (ext.equals(".xls", true)) {
            readXls(filePath)
        } else if (ext.equals(".xlsx", true)) {
            readXlsx(filePath)
        } else {
            returnObject["status"] = "WARNING";
            returnObject["statusDescription"] = "지원하지 않는 확장자 입니다.";
            return returnObject
        }

        returnObject["status"] = "SUCCESS"
        returnObject["data_count"] = 0
        returnObject["upload_count"] = 0

        return returnObject
    }

    fun readText(filePath:String) {

    }

    fun readXls(filePath:String) {

    }

    fun readXlsx(filePath:String) {

        var workBook : Workbook ?= null

        FileInputStream(File(filePath)).use{
            workBook = WorkbookFactory.create(it)
        }

        val sheet = workBook?.getSheetAt(0)

        val columnList = ArrayList<String>()

        val rowCount = sheet?.physicalNumberOfRows!!
        val columnCount = sheet?.getRow(0)?.physicalNumberOfCells!!

        for(index in 0 until columnCount) {
            columnList.add(getCellContents(sheet!!, 0, index)!!)
        }

        println(columnList.toString())
        println(rowCount)

    }

    fun getCellContents(sheet: Sheet, rowIndex: Int, cellIndex: Int) : String? {

        val cell = getCell(sheet, rowIndex, cellIndex)

        if(cell == null)
            return null
        else {
            val type = cell.cellType

            if(type == CellType.NUMERIC) {
                return cell.numericCellValue.toLong().toString()
            } else if (type == CellType.STRING) {
                return cell.stringCellValue
            }
        }

        return null
    }

    fun getCell(sheet: Sheet, rowIndex: Int, cellIndex: Int) : Cell? {
        return sheet.getRow(rowIndex)?.getCell(rowIndex)
    }



}