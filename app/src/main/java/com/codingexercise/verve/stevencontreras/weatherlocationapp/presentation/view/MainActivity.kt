package com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.codingexercise.verve.stevencontreras.weatherlocationapp.R
import com.codingexercise.verve.stevencontreras.weatherlocationapp.WeatherLocApp
import dagger.Subcomponent

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        (application as WeatherLocApp).getWeatherLocAppComponent()
            .inject(this)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

//@Subcomponent(modules = ...)
//public interface YourActivitySubcomponent extends AndroidInjector<YourActivity> {
//    @Subcomponent.Builder
//    public abstract class Builder extends AndroidInjector.Builder<YourActivity> {}
//}
