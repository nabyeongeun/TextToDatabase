package ttd.textToDatabase.service.insert

interface InsertService{

    fun insertFile(filePath:String) : MutableMap<String, Any>


}