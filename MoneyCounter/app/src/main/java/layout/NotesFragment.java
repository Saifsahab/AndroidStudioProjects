package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kpgsoftworks.apps.moneycounter.Util;
import com.kpgsoftworks.apps.moneycounter.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "mc.NotesFragment";
    private static Bundle mSavedInstanceState;
    private LinearLayout notesLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Util.logDebug(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Util.logDebug(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        notesLayout = (LinearLayout) view.findViewById(R.id.notes_layout);
        // Restore values
        mSavedInstanceState = Util.getSavedInstanceState();
        String notes = Util.getNOTES().toLowerCase();
        for (String key : mSavedInstanceState.keySet()) {
            if (key.contains(notes)) {
                Object entry = mSavedInstanceState.get(key);
                if (entry != null) {
                    String value = entry.toString();
                    value = value.equals("") ? "0" : value;
                    Util.logDebug(TAG, "key: " + key + " value: " + value);
                    if (!value.equals("0")) {
                        EditText editText = (EditText) notesLayout.findViewWithTag(key.replace(notes, ""));
                        editText.setText(value);
                    }
                }
            }
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Util.logDebug(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Util.logDebug(TAG, "onDetach");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        Util.logDebug(TAG, "onPause");
        Util.saveValues(notesLayout, Util.getNOTES());
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Util.logDebug(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedState) {
        Util.logDebug(TAG, "onActivityCreated");
        super.onActivityCreated(savedState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setData(Bundle data) {
        this.mSavedInstanceState = data;
    }

    public Bundle getData() {
        return mSavedInstanceState;
    }
}
