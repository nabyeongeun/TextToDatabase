package ttd.textToDatabase.service.read

interface ReadService {

    fun uploadFile(tableName : String, filePath:String) : MutableMap<String, Any>

}