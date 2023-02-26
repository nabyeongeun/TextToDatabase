package ttd.textToDatabase.service.read

interface ReadService {

    fun initPage() : Map<String,Any>

    fun uploadFile(filePath:String) : MutableMap<String, Any>

}