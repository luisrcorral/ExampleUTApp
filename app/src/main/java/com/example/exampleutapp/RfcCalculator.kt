package com.example.exampleutapp

import java.util.Locale

object RfcCalculator {
    /**
     * Calculates a simplified Mexican RFC.
     * Logic: 1st 2 letters of Paternal + 1st letter of Maternal + 1st letter of Name + Date (YYMMDD)
     */
    fun calculate(name: String, paternalLastName: String, maternalLastName: String, dob: String): String {
        if (name.isBlank() || paternalLastName.length < 2) return "INVALID"
        
        val p1 = paternalLastName.take(2).uppercase(Locale.ROOT)
        val m1 = if (maternalLastName.isNotBlank()) maternalLastName.take(1).uppercase(Locale.ROOT) else "X"
        val n1 = name.take(1).uppercase(Locale.ROOT)
        
        // dob format expected: YYYY-MM-DD
        val parts = dob.split("-")
        if (parts.size != 3) return "000000"
        
        val yy = parts[0].takeLast(2)
        val mm = parts[1]
        val dd = parts[2]
        
        return "$p1$m1$n1$yy$mm$dd"
    }
}
