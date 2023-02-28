package ttd.textToDatabase.service.read

import org.apache.ibatis.annotations.Mapper

@Mapper
interface ReadMapper {

    public fun isTableExist(tableName : String) : Int

    public fun tableDataCount(tableName : String) : Int

}