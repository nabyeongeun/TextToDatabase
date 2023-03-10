package ttd.textToDatabase.service.read

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import kotlin.math.roundToInt

@RestController
class ReadController {

    @Autowired
    lateinit var service: ReadService

    @PostMapping("/uploadFile.do")
    fun uploadFile(request : HttpServletRequest, @RequestParam("file") file:MultipartFile) : Map<String,Any> {

        var saveFile :File ?= null
        var returnObject : MutableMap<String,Any> ?= null

        try {
            val dir = File(request.servletContext.getRealPath("/Upload"))
            if (!dir.isDirectory)
                dir.mkdirs()

            var fileName = file.originalFilename!!
            val tableName = fileName.substring(0, fileName.lastIndexOf("."))
            val fileExt = fileName.substring(fileName.lastIndexOf("."))

            val fileId = UUID.randomUUID().toString().replace(Regex("-"), "")

            saveFile = File(dir.toString() + System.getProperty("file.separator") + tableName + "_" + fileId + fileExt)
            file.transferTo(saveFile)

            println(dir.toString() + System.getProperty("file.separator") + tableName + "_" + fileId + fileExt)

            returnObject = service.uploadFile(tableName, dir.toString() + System.getProperty("file.separator") + tableName + "_" + fileId + fileExt)

            if(returnObject["status"].toString() == "SUCCESS") {
                returnObject["file_name"] = fileName
                returnObject["file_size"] = fileSizeMeasure(file.size)
                returnObject["file_id"] = fileId
            }

        } catch (e : Exception) {
            e.printStackTrace()
            returnObject = mutableMapOf<String,Any>("status" to "ERROR")
        } finally {
            saveFile?.delete()
        }

        return returnObject!!
    }

    fun fileSizeMeasure(fileSize: Long?): String{

        if(fileSize == null)
            return ""

        val size = fileSize.toDouble().roundToInt()

        if(fileSize < 1024)
            return size.toString() + "Bytes"
        else if(fileSize < 1024*1024)
            return (size/1024).toString() + "KB"
        else if(fileSize < 1024*1024*1024)
            return (size/1024/1024).toString() + "MB"
        else if(fileSize < 1024*1024*1024*1024)
            return (size/1024/1024/1024).toString() + "GB"

        return ""
    }

}
