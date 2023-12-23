package com.example.healthai.Controllers.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthai.R;
import com.example.healthai.Views.Assessment.RunDiabetesRiskAssessmentActivity;
import com.example.healthai.Views.Assessment.RunHeartRiskAssessmentActivity;
import com.example.healthai.Views.Assessment.RunLungRiskAssessmentActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RiskAssesmentAdapter extends RecyclerView.Adapter<RiskAssesmentAdapter.ViewHolder>{

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public RiskAssesmentAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls){
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        int resourceId = mContext.getResources().getIdentifier(
                mImageUrls.get(position),
                "drawable",
                mContext.getPackageName()
        );

        Glide.with(mContext)
                .asBitmap()
                .load(resourceId)
                .into(holder.image);

        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String itemName = mNames.get(position);
                switch (itemName) {
                    case "Heart Disease":
                        Intent heart = new Intent(mContext, RunHeartRiskAssessmentActivity.class);
                        mContext.startActivity(heart);
                        break;
                    case "Lung Cancer":
                        Intent lung = new Intent(mContext, RunLungRiskAssessmentActivity.class);
                        mContext.startActivity(lung);
                        break;
                    case "Diabetes":
                        Intent diabetes = new Intent(mContext, RunDiabetesRiskAssessmentActivity.class);
                        mContext.startActivity(diabetes);
                        break;
                    default:
                        Toast.makeText(mContext, "Unknown activity: " + itemName, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name;
        public ViewHolder(View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);

        }
    }
}
