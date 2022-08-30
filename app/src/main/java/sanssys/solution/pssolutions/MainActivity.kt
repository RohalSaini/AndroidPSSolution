package sanssys.solution.pssolutions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.razorpay.Checkout
import dagger.hilt.android.AndroidEntryPoint
import sanssys.solution.pssolutions.fragment.DashboardFragment
import sanssys.solution.pssolutions.fragment.SplashFragment
import sanssys.solution.pssolutions.util.Session


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var session:Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fresco.initialize(this)
        Checkout.preload(this);
        session = Session(this)
        if(session.loggedIn()) {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack("home")
                .replace(R.id.container, DashboardFragment())
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SplashFragment())
                .addToBackStack("splashScreen")
                .commit()
        }
    }
}