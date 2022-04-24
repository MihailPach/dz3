package com.example.n12.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.n12.databinding.ItemUserBinding
import com.example.n12.model.GithubUserDetails
import com.example.n12.retrofit.GitHubService
import retrofit2.Call
import retrofit2.Callback

import retrofit2.HttpException
import retrofit2.Response

class DetailsFragment : Fragment() {
    private var _binding: ItemUserBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<DetailsFragmentArgs>()
    private var currentCall: Call<GithubUserDetails>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ItemUserBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentCall = GitHubService.githubApi.getUserDetails(args.username)
        currentCall?.enqueue(object : Callback<GithubUserDetails> {
            override fun onResponse(
                call: Call<GithubUserDetails>,
                response: Response<GithubUserDetails>
            ) {
                if (response.isSuccessful) {
                    val userDetails = response.body() ?: return
//                    adapter.(userDetails)
                    with(binding) {
                        var details = userDetails.toString()
                        count.text = details
                    }
                } else {
                    HttpException(response).message()
                }
            }

            override fun onFailure(call: Call<GithubUserDetails>, t: Throwable) {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentCall = null
        _binding = null
    }
}









