package project.akshay.finalyear.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Adapter.GridViewAdapter;
import project.akshay.finalyear.Interface.FragmentInterface;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Utility.Utilities;

public class BottomDialogFragment extends BottomSheetDialogFragment {

    @BindView(R.id.gridView)
    GridView gridView;

    @BindView(R.id.doneImage)
    ImageView doneImage;

    private Context context;
    private GridViewAdapter gridViewAdapter;
    private FragmentInterface fragmentInterface;

    public BottomDialogFragment() {
    }

    public BottomDialogFragment(Context context) {
        this.context = context;
    }

    public void setFragmentInterface(FragmentInterface fragmentInterface) {
        this.fragmentInterface = fragmentInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView.setVerticalScrollBarEnabled(false);
        gridViewAdapter = new GridViewAdapter(context, Utilities.userRoles);
        gridView.setAdapter(gridViewAdapter);

        doneImage.setOnClickListener(view1 -> {

            if(!gridViewAdapter.getSelected().isEmpty()){

                fragmentInterface.callBackMethod(Utilities.userRoles.get(gridViewAdapter.getSelected().get(0)));

            }

        });

    }
}
