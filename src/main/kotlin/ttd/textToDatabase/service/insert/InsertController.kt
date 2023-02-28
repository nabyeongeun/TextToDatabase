package ttd.textToDatabase.service.insert

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@RestController
class InsertController {

    @Autowired
    lateinit var service: InsertService

    @PostMapping("/insertFile.do")
    fun insertFile(request : HttpServletRequest, @RequestParam("file") file:MultipartFile) : Map<String,Any> {

        var saveFile :File ?= null
        var returnObject : MutableMap<String,Any> ?= null

        try {
            val dir = File(request.servletContext.getRealPath("/Upload"))
            if (!dir.isDirectory)
                dir.mkdirs()
            println(dir)

            var fileName = file.originalFilename!!
            val fileNameOnly = fileName.substring(0, fileName.lastIndexOf("."))
            val fileExt = fileName.substring(fileName.lastIndexOf("."))

            val fileId = UUID.randomUUID().toString().replace(Regex("-"), "")

            saveFile = File(dir.toString() + System.getProperty("file.separator") + fileNameOnly + "_" + fileId + fileExt)
            file.transferTo(saveFile)

            println(dir.toString() + System.getProperty("file.separator") + fileNameOnly + "_" + fileId + fileExt)

            returnObject = service.insertFile(dir.toString() + System.getProperty("file.separator") + fileNameOnly + "_" + fileId + fileExt)

            if(returnObject["status"].toString() == "SUCCESS") {
                returnObject["file_name"] = fileName
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
}