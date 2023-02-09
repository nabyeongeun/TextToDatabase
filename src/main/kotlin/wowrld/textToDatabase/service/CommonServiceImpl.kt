package wowrld.textToDatabase.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.roundToInt

@Service
class CommonServiceImpl : CommonService {
    override fun uploadFile(file: MultipartFile?): Map<String, Any?> {

        val fileName = file?.originalFilename
        val fileSize = file?.size

        val returnObject : MutableMap<String,Any?> = HashMap()
        returnObject["file_name"] = fileName
        returnObject["file_size"] = fileSizeMeasure(fileSize)
        returnObject["data_count"] = 0
        returnObject["upload_count"] = 0

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