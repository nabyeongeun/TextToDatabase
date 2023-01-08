package wowrld.textToDatabase.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CommonServiceImpl : CommonService {
    override fun uploadFile(file: MultipartFile?): Map<String, Any> {

        val returnObject : MutableMap<String,Any> = HashMap();
        returnObject["status"] = "SUCCESS"
        returnObject["statusDescription"] = "성공!";

        return returnObject;
    }
}