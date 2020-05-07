package project.akshay.finalyear.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import project.akshay.finalyear.R;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;
    private ArrayList<Integer> selected;

    public GridViewAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        selected = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View gridView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        gridView = inflater.inflate(R.layout.grid_item, null);

        TextView gridText = gridView.findViewById(R.id.userRole);

        gridText.setText(list.get(position));

        if(selected.contains(position)) {
            gridView.setBackground(context.getDrawable(R.drawable.grid_selected));
            gridText.setTextColor(context.getResources().getColor(R.color.white));
        }

        gridView.setOnClickListener(view -> {


                if(selected.contains(position)) {

                    view.setBackground(context.getDrawable(R.drawable.grid_normal));
                    gridText.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    selected.clear();

                } else if(selected.isEmpty()) {

                    view.setBackground(context.getDrawable(R.drawable.grid_selected));
                    gridText.setTextColor(context.getResources().getColor(R.color.white));
                    selected.add(position);

                } else {

                    View selectedView = viewGroup.getChildAt(selected.get(0));
                    TextView selectedText = selectedView.findViewById(R.id.userRole);

                    selectedView.setBackground(context.getDrawable(R.drawable.grid_normal));
                    selectedText.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    selected.clear();

                    view.setBackground(context.getDrawable(R.drawable.grid_selected));
                    gridText.setTextColor(context.getResources().getColor(R.color.white));
                    selected.add(position);

                }

        });

        return gridView;

    }

    public ArrayList<Integer> getSelected() {
        return selected;
    }

}
