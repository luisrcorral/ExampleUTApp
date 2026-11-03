package com.example.exampleutapp

object UserDataRepository {
    data class UserRecord(val firstName: String, val paternalLastName: String, val maternalLastName: String, val rfc: String)
    
    private val records = mutableListOf<UserRecord>()
    
    fun addRecord(record: UserRecord) {
        records.add(record)
    }
    
    fun getAllRecords(): List<UserRecord> {
        return records.toList()
    }

    fun removeRecord(record: UserRecord) {
        records.remove(record)
    }
}
