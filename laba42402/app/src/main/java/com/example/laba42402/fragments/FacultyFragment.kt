package com.example.laba42402.fragments

import android.animation.LayoutTransition
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo
import com.example.laba42402.Interface.MainActivityCallbacks
import com.example.laba42402.MainActivity
import com.example.laba42402.NamesOfFragment
import com.example.laba42402.R
import com.example.laba42402.data.Faculty
import com.example.laba42402.databinding.FragmentFacultyBinding

class FacultyFragment : Fragment(), MainActivity.Edit{

    companion object {
        private var INSTANCE : FacultyFragment? = null
        fun getInstance(): FacultyFragment{
            if(INSTANCE== null) INSTANCE=FacultyFragment()
            return INSTANCE ?: throw Exception("FacultyFragment not created")
        }
    }

    private lateinit var viewModel: FacultyViewModel
    private lateinit var _binding: FragmentFacultyBinding
    val binding
        get()=_binding
    override fun onCreateView(
            inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?):View{
                val ma =(requireActivity() as MainActivityCallbacks)
            ma.newTitle("List of faculty")
            _binding= FragmentFacultyBinding.inflate(inflater,container,false)
            binding.rvFaculty.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FacultyViewModel::class.java)
        viewModel.facultyList.observe(viewLifecycleOwner){
            if(it!=null){
                binding.rvFaculty.adapter=FacultyAdapter(it.items)
            }
        }
    }

    override fun append() {
        editFaculty()
    }

    override fun update(){
        editFaculty(viewModel.faculty?.name?:"")
    }

    override fun delete() {
        deleteDialog()
    }
    private fun deleteDialog(){

    }

    private fun editFaculty(facultyName:String=""){
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_string,null)
        val messageText = mDialogView.findViewById<TextView>(R.id.tvInfo)
        val inputString = mDialogView.findViewById<EditText>(R.id.etString)
        inputString.setText(facultyName)
        messageText.text="Укажите наименование факультета"
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("ИЗМЕНЕНИЕ ДАННЫХ")
            .setView(mDialogView)
            .setPositiveButton("подтвердаю"){_,_->
                if(facultyName.isBlank()){
                    viewModel.appendFaculty(inputString.text.toString())

                }
                else{
                    viewModel.updateFaculty(inputString.text.toString())
                }
            }
            .setNegativeButton("отмена",null)
            .setCancelable(true)
            .create().show()

    }

    private inner class FacultyAdapter (private val items:List<Faculty>)
        :RecyclerView.Adapter <FacultyAdapter.ItemHolder>(){

            override fun onCreateViewHolder(
                parent:ViewGroup,
                viewType:Int
            ):FacultyAdapter.ItemHolder{
                val view = layoutInflater.inflate(R.layout.element_faculty_list,parent,false)
                return ItemHolder(view)
            }
        override fun getItemCount():Int=items.size
        override fun onBindViewHolder(holder:FacultyAdapter.ItemHolder,position:Int){
            holder.bind(viewModel.facultyList.value!!.items[position])
        }
        private var lastView:View?=null
        private fun updateCurrentView(view:View){
            lastView?.findViewById<ConstraintLayout>(R.id.clFaculty)?.setBackgroundColor(
                    ContextCompat.getColor(requireContext(),R.color.white))
                view.findViewById<ConstraintLayout>(R.id.clFaculty).setBackgroundColor(
                    ContextCompat.getColor(requireContext(),R.color.yellow))
                lastView=view
            }
        private inner class ItemHolder(view:View)
            :RecyclerView.ViewHolder(view){
            private lateinit var faculty: Faculty
            fun bind(faculty: Faculty){
                this.faculty=faculty
                if(faculty==viewModel.faculty)
                    updateCurrentView(itemView)
                val tv = itemView.findViewById<TextView>(R.id.tvFaculty)
                tv.text=faculty.name
                tv.setOnClickListener{
                    viewModel.setCurrentFaculty(faculty)
                    updateCurrentView(itemView)
                }
                tv.setOnClickListener{
                    tv.callOnClick()
                    mainActivity?.showFragment(NamesOfFragment.GROUP)
                    true
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }


}