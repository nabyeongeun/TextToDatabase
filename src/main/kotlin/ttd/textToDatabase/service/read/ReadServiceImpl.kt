package ttd.textToDatabase.service.read

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ttd.textToDatabase.common.Xlsx
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.HashMap

@Service
class ReadServiceImpl : ReadService {

    @Autowired
    lateinit var mapper : ReadMapper

    override fun uploadFile(tableName:String, filePath:String): MutableMap<String, Any> {

        var returnObject : MutableMap<String,Any> = HashMap()

        when(filePath.substring(filePath.lastIndexOf(".")).lowercase(Locale.getDefault())) {
            ".txt"  -> returnObject = readText(filePath)
            ".csv"  -> returnObject = readCsv(filePath)
            ".xlsx" -> returnObject = readXlsx(filePath)
            else -> {
                returnObject["status"] = "WARNING"
                returnObject["statusDescription"] = "unsupported file extension"
                return returnObject
            }
        }

        if(returnObject["status"].toString() != "SUCCESS")
            return returnObject

        returnObject["row_count"] = returnObject["row_count"].toString()
        returnObject["table_data_count"] = if(mapper.isTableExist(tableName.uppercase()) > 0) mapper.tableDataCount(tableName.uppercase()) else "table not found"

        return returnObject
    }

    fun readText(filePath:String) : MutableMap<String,Any>{
        return mapOf("status" to "WARNING").toMutableMap()
    }

    fun readCsv(filePath:String) : MutableMap<String,Any>{
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
            columnList.add(Xlsx.getCellContents(sheet, 0, index)!!)
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
                blankCheck += Xlsx.getCellContents(sheet, i, j) ?: ""
            }

            if(blankCheck != "")
                actualRowCount += 1
        }

        return mapOf("status" to "SUCCESS", "row_count" to actualRowCount).toMutableMap()

    }





}