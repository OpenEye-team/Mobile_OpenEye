package com.txtlabs.openeye.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.txtlabs.openeye.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArticleAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleAdapter(getData())
        binding.rvArticle.adapter = adapter
        binding.rvArticle.setHasFixedSize(true)
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData(): List<ExamData> {
        val list: MutableList<ExamData> = ArrayList()
        list.add(
            ExamData(
                "6 Ciri Kolestrol Tinggi pada Pria",
                "Gejala kolesterol tinggi pada pria perlu diwaspadai. Pasalnya, kolesterol tinggi yang diabaikan dapat berujung pada masalah kesehatan yang lebih parah. Itulah mengapa pria perlu waspada meskipun kolesterol tinggi cenderung lebih berisiko dialami wanita karena hormon yang dimilikinya.\n",
                "https://cdn-image.hipwee.com/wp-content/uploads/2017/05/hipwee-CIRI2-1-750x422.jpg"
            )
        )
        list.add(
            ExamData(
                "6 Ciri Kolestrol Tinggi pada Pria",
                "Gejala kolesterol tinggi pada pria perlu diwaspadai. Pasalnya, kolesterol tinggi yang diabaikan dapat berujung pada masalah kesehatan yang lebih parah. Itulah mengapa pria perlu waspada meskipun kolesterol tinggi cenderung lebih berisiko dialami wanita karena hormon yang dimilikinya.\n",
                "https://cdn-image.hipwee.com/wp-content/uploads/2017/05/hipwee-CIRI2-1-750x422.jpg"
            )
        )
        list.add(
            ExamData(
                "6 Ciri Kolestrol Tinggi pada Pria",
                "Gejala kolesterol tinggi pada pria perlu diwaspadai. Pasalnya, kolesterol tinggi yang diabaikan dapat berujung pada masalah kesehatan yang lebih parah. Itulah mengapa pria perlu waspada meskipun kolesterol tinggi cenderung lebih berisiko dialami wanita karena hormon yang dimilikinya.\n",
                "https://cdn-image.hipwee.com/wp-content/uploads/2017/05/hipwee-CIRI2-1-750x422.jpg"
            )
        )
        return list
    }
}