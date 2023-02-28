package ttd.textToDatabase.service.insert

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class InsertServiceImpl : InsertService {

    @Autowired
    lateinit var mapper : InsertMapper

    override fun insertFile(filePath: String): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }



}