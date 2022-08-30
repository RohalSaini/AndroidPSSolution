package sanssys.solution.pssolutions.fragment.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.ybq.android.spinkit.style.Circle
import com.github.ybq.android.spinkit.style.WanderingCubes
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import sanssys.solution.pssolutions.R
import sanssys.solution.pssolutions.databinding.FragmentLoginBinding
import sanssys.solution.pssolutions.fragment.DashboardFragment
import sanssys.solution.pssolutions.fragment.SignUpFragment
import sanssys.solution.pssolutions.fragment.login.viewmodal.LoginViewModal
import sanssys.solution.pssolutions.fragment.product.ProductFragment
import sanssys.solution.pssolutions.modal.LoginBody
import sanssys.solution.pssolutions.util.*


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val scope = MainScope()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    val modal by viewModels<LoginViewModal>()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spinKit.setIndeterminateDrawable(Circle())

        binding.google.setOnClickListener {
            println(" Sign in with Google")
            googleLogin()
        }

        binding.ButtonLogin.setOnClickListener {
            if(binding.EditTextEmail.text.isEmpty()) {
                Snackbar.make(it,"Enter email address",Snackbar.LENGTH_SHORT).show()
            } else if(binding.EditTextPassword.text.isEmpty()) {
                Snackbar.make(it,"Enter Password ",Snackbar.LENGTH_SHORT).show()
            } else {
                hideKeyboardFrom(requireContext(),it)
                var email = binding.EditTextEmail.text.trim()
                var password = binding.EditTextPassword.text.trim()
                var data: LoginBody? = null
                if(email =="test@test.com" && password=="test") {
                    data = LoginBody(
                        id = "${email.toString().trim()}${password.toString().trim()}",
                        name = "Test User",
                        email = email.trim().toString(),
                        imageUrl = "www.google.com"
                    )
                } else {
                    data = LoginBody(
                        id = password.trim().toString(),
                        name = email.trim().toString(),
                        email = "${email.trim()}@phone.com",
                        imageUrl = "www.google.com"
                    )
                }
                showSpinner()
                Log.d("data",data.toString())
                withOutGoogleIdtest(data)
            }

        }
        binding.ButtonSignUp.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SignUpFragment())
                .addToBackStack("signup")
                .commit()
        }
    }
    private fun googleLogin() {
        checkForLastSignIn(
            GoogleSignIn
            .getClient(this.requireActivity(),
                GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()))
    }
    private fun checkForLastSignIn(mGoogleSignInClient: GoogleSignInClient) {
        signOut(mGoogleSignInClient)
        val account = GoogleSignIn.getLastSignedInAccount(this.requireActivity())
        if(account == null) {
            signIn(mGoogleSignInClient)
        } else {
            signOut(mGoogleSignInClient)
        }
    }
    private fun signIn(mGoogleSignInClient: GoogleSignInClient) {
        resultLauncher.launch(mGoogleSignInClient.signInIntent)
    }
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data) as Task<GoogleSignInAccount>)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            login(completedTask.getResult(ApiException::class.java))
        } catch (e: ApiException) {
            Snackbar.make(_binding!!.root, "Error While Fetching Dara from Google", Snackbar.LENGTH_SHORT).show()
        }
    }
    private fun signOut(mGoogleSignInClient: GoogleSignInClient) {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this.requireActivity(), OnCompleteListener<Void?> {
                println("LogOut succesfully")
            })
    }
    private fun login(account: GoogleSignInAccount) {
        showSpinner()
        scope.launch (Dispatchers.Main){
            val status = modal.login(LoginBody(
                id = account.id.toString(),
                name = account.displayName.toString(),
                email = account.email.toString(),
                imageUrl = "null"
            ))
            when(status) {
                is Left-> {
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
                    hideSpinner()
                    println(data.message)
                    Snackbar.make(_binding!!.root, data.message.toString(), Snackbar.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private  fun withOutGoogleIdtest(data: LoginBody) {
        binding.spinKit.visibility = View.VISIBLE
        scope.launch (Dispatchers.Main){
            val status = modal.authenticateUser(data)
            when(status) {
                is Left-> {
                    //binding.spinKit.visibility = View.INVISIBLE
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
                    //binding.spinKit.visibility = View.INVISIBLE
                    hideSpinner()
                    println(data.message)
                    Snackbar.make(_binding!!.root, data.message.toString(), Snackbar.LENGTH_SHORT).show()
                }

            }
        }
    }
    private fun showSpinner(){
        requireActivity().window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.spinKit.visibility = View.VISIBLE
    }
    private fun hideSpinner() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.spinKit.visibility = View.INVISIBLE
    }
}