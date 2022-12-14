import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.sadoyanzadanie2.R
import java.util.*
import java.util.Collections.emptyList

private const val TAG = "CrimeListFragment"
class CrimeListFragment: Fragment()
{
    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }
    private var callbacks: Callbacks? = null
    private var adapter: CrimeAdapter = CrimeAdapter(emptyList())
    private lateinit var crimeRecyclerView: RecyclerView
    private val crimeListViewModel:
            CrimeListViewModel by lazy{ ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_fragment_crime,container,false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG,"Got crimes ${crimes.size}")
                    updataUI(crimes)
                }
            })
    }

    private fun updataUI(crimes: List<Crime>) {
        TODO("Not yet implemented")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_crime, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.new_crime ->{
                val crime = Crime()
                crimeListViewModel.addCrime(crime)
                callbacks?.onCrimeSelected(crime.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private  inner class CrimeHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var crime:Crime
        private     val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private   val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        fun bind(crime: Crime)
        {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text =
                this.crime.date.toString()

        }
        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View) {
            callbacks?.onCrimeSelected(crime.id)
            TODO("Not yet implemented")
        }
    }
    private inner class CrimeAdapter(var crimes:List<Crime>)
        :RecyclerView.Adapter<CrimeHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                CrimeHolder {
            val  view = layoutInflater.inflate(R.layout.activity_list_item,parent,false)
            return CrimeHolder(view)
            TODO("Not yet implemented")
        }
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }
        override fun getItemCount() = crimes.size
    }
    private  fun UpdateUI(crimes: List<Crime>){

    }
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}