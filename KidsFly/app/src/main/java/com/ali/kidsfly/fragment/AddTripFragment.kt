package com.ali.kidsfly.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ali.kidsfly.R
import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.model.TripToPost
import com.ali.kidsfly.ui.HomepageActivity
import com.ali.kidsfly.viewmodel.UserViewModel


class AddTripFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var firstView: View
    private lateinit var secondView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_trip, container, false)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java) //gets the view model from the attached activity
        firstView = view.findViewById(R.id.first_add_trip_screen)
        secondView = view.findViewById(R.id.second_add_trip_screen)

        firstView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            //both views will have this button...just close out of this fragment
            activity?.onBackPressed()
        }

        secondView.findViewById<Button>(R.id.btn_cancel).setOnClickListener{
            activity?.onBackPressed()
        }

        firstView.findViewById<Button>(R.id.btn_next).setOnClickListener {
            firstView.visibility = View.GONE
            secondView.visibility = View.VISIBLE
        }

        secondView.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            val airportText = firstView.findViewById<EditText>(R.id.et_airport_code).text.toString()
            val dateText = firstView.findViewById<EditText>(R.id.et_trip_date).text.toString()
            val numPassengersText = secondView.findViewById<EditText>(R.id.et_num_passengers).text.toString()
            val numChildrenText = secondView.findViewById<EditText>(R.id.et_num_children).text.toString()
            val luggage = secondView.findViewById<EditText>(R.id.et_luggage).text.toString()

            if(airportText != "" && dateText != "" && numPassengersText != "" && numChildrenText != ""){
                val trip = TripToPost(dateText, airportText, numPassengersText.toInt(), numChildrenText.toInt(), luggage, HomepageActivity.user)
                userViewModel.addTripsToCurrentTrips(trip)
                userViewModel.postTripToApi(trip)

                activity?.onBackPressed()
            }
        }

        secondView.findViewById<Button>(R.id.btn_previous).setOnClickListener {
            firstView.visibility = View.VISIBLE
            secondView.visibility = View.GONE
        }

        return view
    }
}
