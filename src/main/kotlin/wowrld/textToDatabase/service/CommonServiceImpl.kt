package wowrld.textToDatabase.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CommonServiceImpl : CommonService {
    override fun uploadFile(file: MultipartFile?): Map<String, Any> {

        val returnObject : MutableMap<String,Any> = HashMap()
        returnObject["file_name"] = "SEX"
        returnObject["file.size"] = 123456789
        returnObject["file.data_count"] = 0
        returnObject["file.upload_count"] = 0

        return returnObject
    }
}