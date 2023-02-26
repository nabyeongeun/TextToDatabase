package ttd.textToDatabase.service.read

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.sql.Connection
import java.util.*
import kotlin.collections.HashMap

@Service
class ReadServiceImpl : ReadService {

    @Autowired
    lateinit var jdbcTemplate : JdbcTemplate

    override fun initPage(): Map<String,Any> {

        val returnObject : MutableMap<String,Any> = HashMap()

        jdbcTemplate.dataSource!!.connection.use {
            val metaData = it.metaData
            val url = metaData.url
            val productName = metaData.databaseProductName

            returnObject["status"] = "SUCCESS"
            returnObject["url"] = url
            returnObject["productName"] = productName
        }

        return returnObject
    }

    override fun uploadFile(filePath:String): MutableMap<String, Any> {

        var returnObject : MutableMap<String,Any> = HashMap()

        when(filePath.substring(filePath.lastIndexOf(".")).lowercase(Locale.getDefault())) {
            ".txt"  -> returnObject = readText(filePath)
            ".csv"  -> returnObject = readCsv(filePath)
            ".xls"  -> returnObject = readXls(filePath)
            ".xlsx" -> returnObject = readXlsx(filePath)
            else -> {
                returnObject["status"] = "WARNING"
                returnObject["statusDescription"] = "지원하지 않는 확장자 입니다."
                return returnObject
            }
        }

        if(returnObject["status"].toString() != "SUCCESS")
            return returnObject

        returnObject["row_count"] = returnObject["row_count"].toString()
        returnObject["upload_count"] = 0

        return returnObject
    }

    fun readText(filePath:String) : MutableMap<String,Any>{
        return mapOf("status" to "WARNING").toMutableMap()
    }

    fun readCsv(filePath:String) : MutableMap<String,Any>{
        return mapOf("status" to "WARNING").toMutableMap()
    }

    fun readXls(filePath:String) : MutableMap<String,Any>{
        return mapOf("status" to "WARNING").toMutableMap()
    }

    fun readXlsx(filePath:String) : MutableMap<String,Any>{

        var workBook : Workbook ?

        FileInputStream(File(filePath)).use{
            workBook = WorkbookFactory.create(it)
        }

        val sheet = workBook!!.getSheetAt(0)

        val columnList = ArrayList<String>()

        val rowCount = sheet?.physicalNumberOfRows!!
        val columnCount = sheet.getRow(0)?.physicalNumberOfCells!!

        for(index in 0 until columnCount) {
            columnList.add(getCellContents(sheet, 0, index)!!)
        }

        columnList.forEach {
            if(it.matches("^\\d.*".toRegex()))
                return mapOf("status" to "WARNING", "statusDescription" to "column name should not starts with numeric character ($it)").toMutableMap()
        }

        // empty cell with format(background color, font-size, etc...) can be recognized as not empty cell
        // trimming row without content
        var actualRowCount = 0
        for(i in 1 until rowCount) { // actual row measure

            var blankCheck = ""

            for(j in 0 until columnList.size) {
                blankCheck += getCellContents(sheet, i, j) ?: ""
            }

            if(blankCheck != "")
                actualRowCount += 1
        }

        return mapOf("status" to "SUCCESS", "row_count" to actualRowCount).toMutableMap()

    }

    fun getCellContents(sheet: Sheet, rowIndex: Int, cellIndex: Int) : String? {

        val cell = getCell(sheet, rowIndex, cellIndex)

//        println("rowIndex: $rowIndex cellIndex: $cellIndex / ${cell?.cellType.toString()} / $cell")

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