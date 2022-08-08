package com.gzy.mymicroide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

var a="no"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> startActivity(Intent(this,HtmlRunActivity::class.java))
            R.id.run -> startActivity(Intent(this,HtmlRunActivity::class.java))
            R.id.html -> a="html"
            R.id.about -> startActivity(Intent(this,AboutActivity::class.java))
            R.id.quit -> finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}