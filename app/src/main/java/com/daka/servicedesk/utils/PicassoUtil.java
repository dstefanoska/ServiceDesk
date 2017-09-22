package com.daka.servicedesk.utils;

import com.daka.servicedesk.app.ContextProvider;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by Dana on 22-Sep-17.
 */

public class PicassoUtil {

    public static RequestCreator loadCached(String url) {
        return Picasso.with(ContextProvider.getContext()).load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
    }
}

