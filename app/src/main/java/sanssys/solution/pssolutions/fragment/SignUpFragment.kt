package sanssys.solution.pssolutions.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentSignUpBinding
import sanssys.solution.pssolutions.fragment.login.viewmodal.LoginViewModal
import sanssys.solution.pssolutions.modal.LoginBody
import sanssys.solution.pssolutions.util.Left
import sanssys.solution.pssolutions.util.Right
import sanssys.solution.pssolutions.util.hideKeyboardFrom

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private val scope = MainScope()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    val modal by viewModels<LoginViewModal>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionbar.appName.text = "SignUp"
        binding.actionbar.cart.visibility = View.INVISIBLE
        binding.actionbar.expandedMenu.setOnClickListener {
            // restarting app
        }
        binding.ButtonSignUp.setOnClickListener {
            hideKeyboardFrom(requireContext(),it)
            if(binding.EditTextName.text.toString().isEmpty()) {
                Snackbar.make(it,"Enter your name ",Snackbar.LENGTH_SHORT).show()
            }else if(binding.EditTextPhone.text.toString().isEmpty()) {
                Snackbar.make(it,"Enter your phone number ",Snackbar.LENGTH_SHORT).show()
            } else if(binding.EditTextPassword.text.toString().isEmpty()) {
                Snackbar.make(it,"Enter your password",Snackbar.LENGTH_SHORT).show()
            } else if(binding.EditTextRePassword.text.toString().isEmpty()) {
                Snackbar.make(it,"Enter your confirm password",Snackbar.LENGTH_SHORT).show()
            }else if(binding.EditTextRePassword.text.toString() != binding.EditTextPassword.text.toString()) {
                Snackbar.make(it,"password and confirm password is not matching try again later ",Snackbar.LENGTH_SHORT).show()
            } else {
                val name = binding.EditTextName.text.trim().toString()
                val phone = binding.EditTextPhone.text.trim().toString()
                val password = binding.EditTextPassword.text.trim().toString()
                //Snackbar.make(it," name is $name phone is $phone password $password",Snackbar.LENGTH_LONG).show()
                var data = LoginBody(
                    id = password,
                    name = phone,
                    email = "$phone@phone.com",
                    imageUrl = "www.google.com"
                )
                //binding.spinSignUp.visibility = View.VISIBLE
                showSpinner()
                Log.d("data",data.toString())
                withOutGoogleIdtest(data)
            }
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private  fun withOutGoogleIdtest(data: LoginBody) {
        //binding.spinSignUp.visibility = View.VISIBLE
        scope.launch (Dispatchers.Main){
            val status = modal.createUser(data)
            when(status) {
                is Left -> {
                    //binding.spinSignUp.visibility = View.INVISIBLE
                    hideSpinner()
                    requireActivity()
                        .supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, DashboardFragment())
                        .commit()
                }
                is Right -> {
                    var data = status.b
                    Log.e("login",data.toString());
                    //binding.spinSignUp.visibility = View.INVISIBLE
                    println(data.message)
                    hideSpinner()
                    Snackbar.make(_binding!!.root,data.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun showSpinner(){
        requireActivity().window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.spinSignUp.visibility = View.VISIBLE
    }
    private fun hideSpinner() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.spinSignUp.visibility = View.INVISIBLE
    }
}