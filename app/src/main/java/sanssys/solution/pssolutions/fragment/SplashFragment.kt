package sanssys.solution.pssolutions.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentSplashBinding
import sanssys.solution.pssolutions.fragment.login.LoginFragment

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, LoginFragment())
                .addToBackStack("login")
                .commit()

        }, 3000)
    }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}