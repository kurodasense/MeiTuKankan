package com.example.thelastworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {
    private List<IndexImage> classifyImageList=new ArrayList<>();
    private String url="http://134.175.231.43/classifypictures/";
    private IndexImage[] classifyImages={new IndexImage(url+"hydpk/01.jpg"),
            new IndexImage(url+"hydpk/02.jpg"),
            new IndexImage(url+"hydpk/03.jpg"),
            new IndexImage(url+"hydpk/04.jpg"),
            new IndexImage(url+"hydpk/05.jpg"),
            new IndexImage(url+"hydpk/06.jpg"),
            new IndexImage(url+"hydpk/03.jpg"),
            new IndexImage(url+"hydpk/04.jpg"),
            new IndexImage(url+"hydpk/05.jpg"),
            new IndexImage(url+"hydpk/06.jpg")};

    public static Fragment newInstance(int cla){
        TabFragment fragment=new TabFragment();
        fragment.setClassifyImages(cla);
        return fragment;
    }
    public void setClassifyImages(int cla){
        switch(cla){
            case 0:
                classifyImages[0].setImageUrl(url+"zzx/01.jpg");
                classifyImages[1].setImageUrl(url+"zzx/02.jpg");
                classifyImages[2].setImageUrl(url+"zzx/03.jpg");
                classifyImages[3].setImageUrl(url+"zzx/04.jpg");
                classifyImages[4].setImageUrl(url+"zzx/05.jpg");
                classifyImages[5].setImageUrl(url+"zzx/06.jpg");
                classifyImages[6].setImageUrl(url+"zzx/07.jpg");
                classifyImages[7].setImageUrl(url+"zzx/08.jpg");
                classifyImages[8].setImageUrl(url+"zzx/09.jpg");
                classifyImages[9].setImageUrl(url+"zzx/10.jpg");
                break;
            case 1:
                classifyImages[0].setImageUrl(url+"smhy/01.jpg");
                classifyImages[1].setImageUrl(url+"smhy/02.jpg");
                classifyImages[2].setImageUrl(url+"smhy/03.jpg");
                classifyImages[3].setImageUrl(url+"smhy/04.jpg");
                classifyImages[4].setImageUrl(url+"smhy/05.jpg");
                classifyImages[5].setImageUrl(url+"smhy/06.jpg");
                classifyImages[6].setImageUrl(url+"smhy/07.jpg");
                classifyImages[7].setImageUrl(url+"smhy/08.jpg");
                classifyImages[8].setImageUrl(url+"smhy/09.jpg");
                classifyImages[9].setImageUrl(url+"smhy/10.jpg");
                break;
            case 2:
                classifyImages[0].setImageUrl(url+"swgq/01.jpg");
                classifyImages[1].setImageUrl(url+"swgq/02.jpg");
                classifyImages[2].setImageUrl(url+"swgq/03.jpg");
                classifyImages[3].setImageUrl(url+"swgq/04.jpg");
                classifyImages[4].setImageUrl(url+"swgq/05.jpg");
                classifyImages[5].setImageUrl(url+"swgq/06.jpg");
                classifyImages[6].setImageUrl(url+"swgq/07.jpg");
                classifyImages[7].setImageUrl(url+"swgq/08.jpg");
                classifyImages[8].setImageUrl(url+"swgq/09.jpg");
                classifyImages[9].setImageUrl(url+"swgq/10.jpg");
                break;
            case 3:
                classifyImages[0].setImageUrl(url+"xy/01.jpg");
                classifyImages[1].setImageUrl(url+"xy/02.jpg");
                classifyImages[2].setImageUrl(url+"xy/03.jpg");
                classifyImages[3].setImageUrl(url+"xy/04.jpg");
                classifyImages[4].setImageUrl(url+"xy/05.jpg");
                classifyImages[5].setImageUrl(url+"xy/06.jpg");
                classifyImages[6].setImageUrl(url+"xy/07.jpg");
                classifyImages[7].setImageUrl(url+"xy/08.jpg");
                classifyImages[8].setImageUrl(url+"xy/09.jpg");
                classifyImages[9].setImageUrl(url+"xy/10.jpg");
                break;
            case 4:
                classifyImages[0].setImageUrl(url+"zhshz/01.jpg");
                classifyImages[1].setImageUrl(url+"zs/02.jpg");
                classifyImages[2].setImageUrl(url+"zhshz/03.jpg");
                classifyImages[3].setImageUrl(url+"zl/04.jpg");
                classifyImages[4].setImageUrl(url+"zs/05.jpg");
                classifyImages[5].setImageUrl(url+"zl/01.jpg");
                classifyImages[6].setImageUrl(url+"zs/03.jpg");
                classifyImages[7].setImageUrl(url+"zhshz/03.jpg");
                classifyImages[8].setImageUrl(url+"zhshz/08.jpg");
                classifyImages[9].setImageUrl(url+"zl/05.jpg");
                break;
            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_tab,container,false);
        RecyclerView recyclerView=(RecyclerView) rootView.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        initClassityImage();
        recyclerView.setAdapter(new classifyAdapter(classifyImageList));
        return rootView;
    }
    private void initClassityImage(){
        if(classifyImageList.isEmpty()!=true){
        classifyImageList.clear();}
        for(int i=0;i<classifyImages.length;i++)
        {
            classifyImageList.add(classifyImages[i]);
        }
    }
}
