package ttd.textToDatabase.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
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

    override fun uploadFile(file: MultipartFile?): Map<String, Any?> {

        val returnObject : MutableMap<String,Any?> = HashMap()
        val fileName = file?.originalFilename

        val ext = fileName?.substring(fileName.lastIndexOf("."));

        if(ext.equals("txt", true)) {

        } else if (ext.equals("xls", true)) {

        } else if (ext.equals("xlsx", true)) {

        } else {
            returnObject["status"] = "WARNING";
            returnObject["statusDescription"] = "지원하지 않는 확장자 입니다.";
            return returnObject
        }


        returnObject["status"] = "SUCCESS"
        returnObject["file_name"] = fileName
        returnObject["file_size"] = fileSizeMeasure(file?.size)
        returnObject["data_count"] = 0
        returnObject["upload_count"] = 0
        returnObject["file_id"] = UUID.randomUUID().toString().replace(Regex("-"), "")

        return returnObject
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