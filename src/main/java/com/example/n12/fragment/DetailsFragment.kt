package com.example.n12.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.n12.databinding.FragmentDetailsBinding
import com.example.n12.model.GithubUserDetails
import com.example.n12.retrofit.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val counter = args.username

            GitHubService.githubApi.getUserDetails(counter)
                .enqueue(object : Callback<GithubUserDetails> {
                    override fun onResponse(
                        call: Call<GithubUserDetails>,
                        response: Response<GithubUserDetails>
                    ) {
                        if (response.isSuccessful) {
                            val userDetails = response.body() ?: return

                            countD.text = userDetails.following.toString()
                            countD2.text = userDetails.login
                            countD3.text = userDetails.followers.toString()
                            imageD.load(userDetails.avatarUrl)
                        } else {
                            HttpException(response).message()
                        }
                    }

                    override fun onFailure(call: Call<GithubUserDetails>, t: Throwable) {
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}