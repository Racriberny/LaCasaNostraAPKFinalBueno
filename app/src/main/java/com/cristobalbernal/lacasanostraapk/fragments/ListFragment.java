package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cristobalbernal.lacasanostraapk.R;

public class ListFragment extends Fragment {
    public interface IOnAttachListener {
        ListingType getListingType();
    }

    public enum ListingType {
        RECEIVED, SENT, UNREADED, DELETED, SPAM
    }
    private ListingType listingType;

    public ListFragment() {
       super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttachListener attachListener = (IOnAttachListener) context;
        listingType = attachListener.getListingType();
        updateList(listingType);
    }

    public void updateList(ListingType listingType) {
        this.listingType = listingType;
    }
}
