package com.example.loginandsignupapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.loginandsignupapp.Model.LoginData
import com.example.loginandsignupapp.Model.SignUpData

const val DB_NAME = "LoginTGDatabase"
const val TABLE_NAME = "myTable"
const val ID_COL = "id"
const val NAME_COL = "Name"
const val EMAIL_COL = "Email"
const val Password_COl = "Password"

class SQLiteDatabase(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val table =
            "CREATE TABLE $TABLE_NAME($ID_COL INTEGER PRIMARY KEY, $NAME_COL TEXT, $EMAIL_COL TEXT, $Password_COl TEXT)"
        p0?.execSQL(table)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val deleteTable = "DROP TABLE  IF EXISTS $TABLE_NAME"
        p0?.execSQL(deleteTable)
    }

    fun addUSer(info: SignUpData) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, info.name)
        values.put(EMAIL_COL, info.email)
        values.put(Password_COl, info.password)
        db.insert(TABLE_NAME, null, values)
        db.close()
        Toast.makeText(context, "User ${info.name} has been added.", Toast.LENGTH_SHORT).show()
    }

    fun clearAllData() {
        val db = readableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun loginValidation(userInfo: LoginData): Boolean {
        val db = readableDatabase
        val selectQuery =
            "SELECT * FROM $TABLE_NAME WHERE $EMAIL_COL = '${userInfo.email}' AND $Password_COl = '${userInfo.password}'"
        val cursor = db.rawQuery(selectQuery, null)
        return cursor.count > 0
    }

    fun verifyName(name: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $NAME_COL = '$name'"
        val result = db.rawQuery(query, null)
        return result.count > 0
    }
}