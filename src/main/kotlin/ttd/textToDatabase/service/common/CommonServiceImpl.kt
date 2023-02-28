package ttd.textToDatabase.service.common

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
class CommonServiceImpl : CommonService {

    @Autowired
    lateinit var jdbcTemplate : JdbcTemplate

    override fun initPage(): Map<String,Any> {

        val returnObject : MutableMap<String,Any> = HashMap()

        jdbcTemplate.dataSource!!.connection.use {
            val metaData = it.metaData

            returnObject["status"] = "SUCCESS"
            returnObject["url"] = metaData.url
            returnObject["productName"] = metaData.databaseProductVersion
            returnObject["username"] = metaData.userName
        }

        return returnObject
    }

}