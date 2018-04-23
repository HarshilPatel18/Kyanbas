package com.anomaly.android.kyanbas.View.Main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.anomaly.android.kyanbas.Adapter.EmptyRecyclerViewAdapter;
import com.anomaly.android.kyanbas.Adapter.RecyclerGridViewAdapter;
import com.anomaly.android.kyanbas.Adapter.ViewPagerAdapter;
import com.anomaly.android.kyanbas.Modal.Art;
import com.anomaly.android.kyanbas.Modal.Category;
import com.anomaly.android.kyanbas.Modal.User;
import com.anomaly.android.kyanbas.Network.Constants;
import com.anomaly.android.kyanbas.Network.RequestHandler;
import com.anomaly.android.kyanbas.Network.ResponseKeys;
import com.anomaly.android.kyanbas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {

    String category;
    List<Art> listArt;
    RecyclerView recyclerGrid;
    Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_category, container, false);
        if(getArguments()!=null)
        {
            category= String.valueOf(getArguments().getInt(ResponseKeys.CATEGORY_PARENT_ID));
        }
        mContext=container.getContext();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerGrid = (RecyclerView) view.findViewById(R.id.gridRecycler);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Cate_swipe_refresh_layout);

        listArt = new ArrayList<>();
        displayArtbyCategory();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayArtbyCategory();
            }
        });

    }

    public static CategoryFragment newInstance(Integer param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ResponseKeys.CATEGORY_PARENT_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public void displayArtbyCategory()
    {

        String data="{\n" +
                "    \"success\": true,\n" +
                "    \"data\": {\n" +
                "        \"object\": [\n" +
                "            [\n" +
                "                {\n" +
                "                    \"id\": 2,\n" +
                "                    \"name\": \"O'Kon Port\",\n" +
                "                    \"description\": \"Dolores cumque nostrum nihil quo.\",\n" +
                "                    \"parent_id\": 1,\n" +
                "                    \"nicename\": \"112Lake\",\n" +
                "                    \"created_at\": \"2018-04-22 09:54:21\",\n" +
                "                    \"updated_at\": \"2018-04-22 09:54:21\"\n" +
                "                },\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"id\": 4,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Jerde Harbors\",\n" +
                "                        \"product\": \"storage/images/product/original/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"description\": \"Tenetur debitis voluptas vel qui. Quae quasi eaque quia velit. Et hic inventore officia ea et.\",\n" +
                "                        \"price\": 9879,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 10,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"620Reina\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:30\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:30\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 5,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Madeline Extension\",\n" +
                "                        \"product\": \"storage/images/product/original/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"description\": \"Unde sed voluptatem veniam assumenda. Accusantium sapiente repellat dolores sed qui rerum inventore. Illo odio eos ipsam libero voluptatem enim et.\",\n" +
                "                        \"price\": 6730,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 4,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"011Lyda\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 13,\n" +
                "                        \"category_id\": 14,\n" +
                "                        \"name\": \"Ally Dam\",\n" +
                "                        \"product\": \"storage/images/product/original/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"description\": \"Voluptatem aspernatur harum harum et quidem sit. Molestiae omnis et rerum dolores distinctio ab.\",\n" +
                "                        \"price\": 7448,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"116Katelyn\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 14,\n" +
                "                        \"category_id\": 25,\n" +
                "                        \"name\": \"Lowe Spurs\",\n" +
                "                        \"product\": \"storage/images/product/original/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"description\": \"Iusto suscipit dicta omnis odit ullam et. Expedita nemo sint et ducimus officia. Delectus exercitationem cumque enim esse occaecati dolor.\",\n" +
                "                        \"price\": 5438,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 8,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"915Lenore\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 17,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Winifred Station\",\n" +
                "                        \"product\": \"storage/images/product/original/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"description\": \"Ea nihil id laudantium doloribus architecto. Nihil voluptatibus totam eveniet autem dolore est aliquam. Molestias quidem numquam ipsam eaque.\",\n" +
                "                        \"price\": 2345,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"119Bella\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 26,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Braeden Mission\",\n" +
                "                        \"product\": \"storage/images/product/original/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"description\": \"Atque repellat quis aut sit ut. Unde repudiandae porro ut ea quo tempore ut.\",\n" +
                "                        \"price\": 7434,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 7,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"419Ali\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 29,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Schimmel Road\",\n" +
                "                        \"product\": \"storage/images/product/original/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"description\": \"Sed facere earum aut id qui. Quia possimus alias labore sint. Hic nam quae tenetur sit eius.\",\n" +
                "                        \"price\": 2208,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 6,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"612Giuseppe\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 30,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Kerluke Unions\",\n" +
                "                        \"product\": \"storage/images/product/original/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"description\": \"Iste error iste esse distinctio ut voluptates qui. Nam laudantium omnis nemo. Suscipit sunt id doloribus vero molestias sunt ducimus.\",\n" +
                "                        \"price\": 9890,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"315Beaulah\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            [\n" +
                "                {\n" +
                "                    \"id\": 3,\n" +
                "                    \"name\": \"Reva Place\",\n" +
                "                    \"description\": \"Fuga sit modi aut deserunt.\",\n" +
                "                    \"parent_id\": 1,\n" +
                "                    \"nicename\": \"313North\",\n" +
                "                    \"created_at\": \"2018-04-22 09:54:21\",\n" +
                "                    \"updated_at\": \"2018-04-22 09:54:21\"\n" +
                "                },\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"id\": 4,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Jerde Harbors\",\n" +
                "                        \"product\": \"storage/images/product/original/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"description\": \"Tenetur debitis voluptas vel qui. Quae quasi eaque quia velit. Et hic inventore officia ea et.\",\n" +
                "                        \"price\": 9879,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 10,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"620Reina\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:30\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:30\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 5,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Madeline Extension\",\n" +
                "                        \"product\": \"storage/images/product/original/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"description\": \"Unde sed voluptatem veniam assumenda. Accusantium sapiente repellat dolores sed qui rerum inventore. Illo odio eos ipsam libero voluptatem enim et.\",\n" +
                "                        \"price\": 6730,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 4,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"011Lyda\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 6,\n" +
                "                        \"category_id\": 21,\n" +
                "                        \"name\": \"Bernhard Mountain\",\n" +
                "                        \"product\": \"storage/images/product/original/806d24a3d7ff6ac5dd1c2c4821adeba1.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/806d24a3d7ff6ac5dd1c2c4821adeba1.jpg\",\n" +
                "                        \"description\": \"Recusandae magnam labore dicta voluptates et autem ab. Est soluta ab ducimus et recusandae et mollitia.\",\n" +
                "                        \"price\": 5013,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 6,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"220Rickie\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 13,\n" +
                "                        \"category_id\": 14,\n" +
                "                        \"name\": \"Ally Dam\",\n" +
                "                        \"product\": \"storage/images/product/original/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"description\": \"Voluptatem aspernatur harum harum et quidem sit. Molestiae omnis et rerum dolores distinctio ab.\",\n" +
                "                        \"price\": 7448,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"116Katelyn\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 14,\n" +
                "                        \"category_id\": 25,\n" +
                "                        \"name\": \"Lowe Spurs\",\n" +
                "                        \"product\": \"storage/images/product/original/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"description\": \"Iusto suscipit dicta omnis odit ullam et. Expedita nemo sint et ducimus officia. Delectus exercitationem cumque enim esse occaecati dolor.\",\n" +
                "                        \"price\": 5438,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 8,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"915Lenore\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 17,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Winifred Station\",\n" +
                "                        \"product\": \"storage/images/product/original/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"description\": \"Ea nihil id laudantium doloribus architecto. Nihil voluptatibus totam eveniet autem dolore est aliquam. Molestias quidem numquam ipsam eaque.\",\n" +
                "                        \"price\": 2345,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"119Bella\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 22,\n" +
                "                        \"category_id\": 24,\n" +
                "                        \"name\": \"Alden Gardens\",\n" +
                "                        \"product\": \"storage/images/product/original/2567843ad4fe01a933f1cf0965d996e3.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/2567843ad4fe01a933f1cf0965d996e3.jpg\",\n" +
                "                        \"description\": \"Voluptas doloremque nisi sint incidunt fugiat cum. Minima omnis quaerat ex et cum. Et aperiam eligendi placeat dolores.\",\n" +
                "                        \"price\": 4812,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 1,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"417Isadore\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 26,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Braeden Mission\",\n" +
                "                        \"product\": \"storage/images/product/original/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"description\": \"Atque repellat quis aut sit ut. Unde repudiandae porro ut ea quo tempore ut.\",\n" +
                "                        \"price\": 7434,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 7,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"419Ali\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 29,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Schimmel Road\",\n" +
                "                        \"product\": \"storage/images/product/original/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"description\": \"Sed facere earum aut id qui. Quia possimus alias labore sint. Hic nam quae tenetur sit eius.\",\n" +
                "                        \"price\": 2208,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 6,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"612Giuseppe\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 30,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Kerluke Unions\",\n" +
                "                        \"product\": \"storage/images/product/original/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"description\": \"Iste error iste esse distinctio ut voluptates qui. Nam laudantium omnis nemo. Suscipit sunt id doloribus vero molestias sunt ducimus.\",\n" +
                "                        \"price\": 9890,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"315Beaulah\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            [\n" +
                "                {\n" +
                "                    \"id\": 10,\n" +
                "                    \"name\": \"Jaskolski Rapid\",\n" +
                "                    \"description\": \"Sed sed consequatur id perferendis optio.\",\n" +
                "                    \"parent_id\": 1,\n" +
                "                    \"nicename\": \"1017Port\",\n" +
                "                    \"created_at\": \"2018-04-22 09:54:24\",\n" +
                "                    \"updated_at\": \"2018-04-22 09:54:24\"\n" +
                "                },\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"id\": 4,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Jerde Harbors\",\n" +
                "                        \"product\": \"storage/images/product/original/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"description\": \"Tenetur debitis voluptas vel qui. Quae quasi eaque quia velit. Et hic inventore officia ea et.\",\n" +
                "                        \"price\": 9879,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 10,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"620Reina\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:30\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:30\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 5,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Madeline Extension\",\n" +
                "                        \"product\": \"storage/images/product/original/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"description\": \"Unde sed voluptatem veniam assumenda. Accusantium sapiente repellat dolores sed qui rerum inventore. Illo odio eos ipsam libero voluptatem enim et.\",\n" +
                "                        \"price\": 6730,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 4,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"011Lyda\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 6,\n" +
                "                        \"category_id\": 21,\n" +
                "                        \"name\": \"Bernhard Mountain\",\n" +
                "                        \"product\": \"storage/images/product/original/806d24a3d7ff6ac5dd1c2c4821adeba1.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/806d24a3d7ff6ac5dd1c2c4821adeba1.jpg\",\n" +
                "                        \"description\": \"Recusandae magnam labore dicta voluptates et autem ab. Est soluta ab ducimus et recusandae et mollitia.\",\n" +
                "                        \"price\": 5013,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 6,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"220Rickie\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 9,\n" +
                "                        \"category_id\": 10,\n" +
                "                        \"name\": \"Beier Ridges\",\n" +
                "                        \"product\": \"storage/images/product/original/786682688bd35865a638486eac1ec548.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/786682688bd35865a638486eac1ec548.jpg\",\n" +
                "                        \"description\": \"Aut corporis cupiditate quidem odio est aut. Et repellat harum quia cupiditate consequatur deserunt. Distinctio et quia rerum neque ab tempora a.\",\n" +
                "                        \"price\": 5067,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 10,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"011Sedrick\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 13,\n" +
                "                        \"category_id\": 14,\n" +
                "                        \"name\": \"Ally Dam\",\n" +
                "                        \"product\": \"storage/images/product/original/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"description\": \"Voluptatem aspernatur harum harum et quidem sit. Molestiae omnis et rerum dolores distinctio ab.\",\n" +
                "                        \"price\": 7448,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"116Katelyn\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 14,\n" +
                "                        \"category_id\": 25,\n" +
                "                        \"name\": \"Lowe Spurs\",\n" +
                "                        \"product\": \"storage/images/product/original/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"description\": \"Iusto suscipit dicta omnis odit ullam et. Expedita nemo sint et ducimus officia. Delectus exercitationem cumque enim esse occaecati dolor.\",\n" +
                "                        \"price\": 5438,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 8,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"915Lenore\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 17,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Winifred Station\",\n" +
                "                        \"product\": \"storage/images/product/original/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"description\": \"Ea nihil id laudantium doloribus architecto. Nihil voluptatibus totam eveniet autem dolore est aliquam. Molestias quidem numquam ipsam eaque.\",\n" +
                "                        \"price\": 2345,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"119Bella\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 22,\n" +
                "                        \"category_id\": 24,\n" +
                "                        \"name\": \"Alden Gardens\",\n" +
                "                        \"product\": \"storage/images/product/original/2567843ad4fe01a933f1cf0965d996e3.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/2567843ad4fe01a933f1cf0965d996e3.jpg\",\n" +
                "                        \"description\": \"Voluptas doloremque nisi sint incidunt fugiat cum. Minima omnis quaerat ex et cum. Et aperiam eligendi placeat dolores.\",\n" +
                "                        \"price\": 4812,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 1,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"417Isadore\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 25,\n" +
                "                        \"category_id\": 10,\n" +
                "                        \"name\": \"Herzog Turnpike\",\n" +
                "                        \"product\": \"storage/images/product/original/919ea366f33fba48c7ff9d7d7e46d1f5.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/919ea366f33fba48c7ff9d7d7e46d1f5.jpg\",\n" +
                "                        \"description\": \"Magni tenetur architecto molestiae eaque quidem omnis. Voluptatibus molestias omnis cupiditate impedit ea minima velit. Omnis est quia at est.\",\n" +
                "                        \"price\": 5253,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 8,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"015Ivah\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 26,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Braeden Mission\",\n" +
                "                        \"product\": \"storage/images/product/original/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"description\": \"Atque repellat quis aut sit ut. Unde repudiandae porro ut ea quo tempore ut.\",\n" +
                "                        \"price\": 7434,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 7,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"419Ali\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 29,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Schimmel Road\",\n" +
                "                        \"product\": \"storage/images/product/original/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"description\": \"Sed facere earum aut id qui. Quia possimus alias labore sint. Hic nam quae tenetur sit eius.\",\n" +
                "                        \"price\": 2208,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 6,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"612Giuseppe\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 30,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Kerluke Unions\",\n" +
                "                        \"product\": \"storage/images/product/original/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"description\": \"Iste error iste esse distinctio ut voluptates qui. Nam laudantium omnis nemo. Suscipit sunt id doloribus vero molestias sunt ducimus.\",\n" +
                "                        \"price\": 9890,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"315Beaulah\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            ],\n" +
                "            [\n" +
                "                {\n" +
                "                    \"id\": 16,\n" +
                "                    \"name\": \"Predovic Unions\",\n" +
                "                    \"description\": \"Ut enim est odit dignissimos aliquam eum.\",\n" +
                "                    \"parent_id\": 1,\n" +
                "                    \"nicename\": \"311South\",\n" +
                "                    \"created_at\": \"2018-04-22 09:54:26\",\n" +
                "                    \"updated_at\": \"2018-04-22 09:54:26\"\n" +
                "                },\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"id\": 4,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Jerde Harbors\",\n" +
                "                        \"product\": \"storage/images/product/original/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/b770a96ffa3302756a65851b50c1b782.jpg\",\n" +
                "                        \"description\": \"Tenetur debitis voluptas vel qui. Quae quasi eaque quia velit. Et hic inventore officia ea et.\",\n" +
                "                        \"price\": 9879,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 10,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"620Reina\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:30\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:30\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 5,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Madeline Extension\",\n" +
                "                        \"product\": \"storage/images/product/original/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/139e763ffd5150a8efc332f2052baae3.jpg\",\n" +
                "                        \"description\": \"Unde sed voluptatem veniam assumenda. Accusantium sapiente repellat dolores sed qui rerum inventore. Illo odio eos ipsam libero voluptatem enim et.\",\n" +
                "                        \"price\": 6730,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 4,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"011Lyda\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 6,\n" +
                "                        \"category_id\": 21,\n" +
                "                        \"name\": \"Bernhard Mountain\",\n" +
                "                        \"product\": \"storage/images/product/original/806d24a3d7ff6ac5dd1c2c4821adeba1.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/806d24a3d7ff6ac5dd1c2c4821adeba1.jpg\",\n" +
                "                        \"description\": \"Recusandae magnam labore dicta voluptates et autem ab. Est soluta ab ducimus et recusandae et mollitia.\",\n" +
                "                        \"price\": 5013,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 6,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"220Rickie\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 9,\n" +
                "                        \"category_id\": 10,\n" +
                "                        \"name\": \"Beier Ridges\",\n" +
                "                        \"product\": \"storage/images/product/original/786682688bd35865a638486eac1ec548.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/786682688bd35865a638486eac1ec548.jpg\",\n" +
                "                        \"description\": \"Aut corporis cupiditate quidem odio est aut. Et repellat harum quia cupiditate consequatur deserunt. Distinctio et quia rerum neque ab tempora a.\",\n" +
                "                        \"price\": 5067,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 10,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"011Sedrick\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:31\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:31\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 13,\n" +
                "                        \"category_id\": 14,\n" +
                "                        \"name\": \"Ally Dam\",\n" +
                "                        \"product\": \"storage/images/product/original/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7f529d187da239c95cfcfdd9e2f6d961.jpg\",\n" +
                "                        \"description\": \"Voluptatem aspernatur harum harum et quidem sit. Molestiae omnis et rerum dolores distinctio ab.\",\n" +
                "                        \"price\": 7448,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"116Katelyn\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 14,\n" +
                "                        \"category_id\": 25,\n" +
                "                        \"name\": \"Lowe Spurs\",\n" +
                "                        \"product\": \"storage/images/product/original/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/0ac80ee663c7046e42c8444b10888765.jpg\",\n" +
                "                        \"description\": \"Iusto suscipit dicta omnis odit ullam et. Expedita nemo sint et ducimus officia. Delectus exercitationem cumque enim esse occaecati dolor.\",\n" +
                "                        \"price\": 5438,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 8,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"915Lenore\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 17,\n" +
                "                        \"category_id\": 9,\n" +
                "                        \"name\": \"Winifred Station\",\n" +
                "                        \"product\": \"storage/images/product/original/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/3178d7c3a50292cf7aa2cf735bc06e6e.jpg\",\n" +
                "                        \"description\": \"Ea nihil id laudantium doloribus architecto. Nihil voluptatibus totam eveniet autem dolore est aliquam. Molestias quidem numquam ipsam eaque.\",\n" +
                "                        \"price\": 2345,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"119Bella\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:32\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:32\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 22,\n" +
                "                        \"category_id\": 24,\n" +
                "                        \"name\": \"Alden Gardens\",\n" +
                "                        \"product\": \"storage/images/product/original/2567843ad4fe01a933f1cf0965d996e3.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/2567843ad4fe01a933f1cf0965d996e3.jpg\",\n" +
                "                        \"description\": \"Voluptas doloremque nisi sint incidunt fugiat cum. Minima omnis quaerat ex et cum. Et aperiam eligendi placeat dolores.\",\n" +
                "                        \"price\": 4812,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 1,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"417Isadore\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 25,\n" +
                "                        \"category_id\": 10,\n" +
                "                        \"name\": \"Herzog Turnpike\",\n" +
                "                        \"product\": \"storage/images/product/original/919ea366f33fba48c7ff9d7d7e46d1f5.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/919ea366f33fba48c7ff9d7d7e46d1f5.jpg\",\n" +
                "                        \"description\": \"Magni tenetur architecto molestiae eaque quidem omnis. Voluptatibus molestias omnis cupiditate impedit ea minima velit. Omnis est quia at est.\",\n" +
                "                        \"price\": 5253,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 8,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"015Ivah\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 26,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Braeden Mission\",\n" +
                "                        \"product\": \"storage/images/product/original/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/a4abf606e119ecea24733cffbf0606b7.jpg\",\n" +
                "                        \"description\": \"Atque repellat quis aut sit ut. Unde repudiandae porro ut ea quo tempore ut.\",\n" +
                "                        \"price\": 7434,\n" +
                "                        \"has_specification\": 1,\n" +
                "                        \"user_id\": 7,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"419Ali\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:33\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:33\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 29,\n" +
                "                        \"category_id\": 18,\n" +
                "                        \"name\": \"Schimmel Road\",\n" +
                "                        \"product\": \"storage/images/product/original/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/ca5c662edd80665b123b5f1e2b6b0af6.jpg\",\n" +
                "                        \"description\": \"Sed facere earum aut id qui. Quia possimus alias labore sint. Hic nam quae tenetur sit eius.\",\n" +
                "                        \"price\": 2208,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 6,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"shippable\",\n" +
                "                        \"nicename\": \"612Giuseppe\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"id\": 30,\n" +
                "                        \"category_id\": 6,\n" +
                "                        \"name\": \"Kerluke Unions\",\n" +
                "                        \"product\": \"storage/images/product/original/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"thumbnail_picture\": \"storage/images/product/thumbnail/7b9d3c6990930262e05933bb2354778b.jpg\",\n" +
                "                        \"description\": \"Iste error iste esse distinctio ut voluptates qui. Nam laudantium omnis nemo. Suscipit sunt id doloribus vero molestias sunt ducimus.\",\n" +
                "                        \"price\": 9890,\n" +
                "                        \"has_specification\": 0,\n" +
                "                        \"user_id\": 2,\n" +
                "                        \"product_type\": \"art\",\n" +
                "                        \"delivery_type\": \"digital\",\n" +
                "                        \"nicename\": \"315Beaulah\",\n" +
                "                        \"created_at\": \"2018-04-22 09:55:34\",\n" +
                "                        \"updated_at\": \"2018-04-22 09:55:34\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            ]\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        try {


            JSONObject jsonObject = new JSONObject(data);

            if (jsonObject.getBoolean("success"))
            {

                JSONObject jsonData = jsonObject.getJSONObject("data");
                JSONArray jsonArray=jsonData.getJSONArray("object");
                JSONArray jsonArrayObject= (JSONArray) jsonArray.get(0);


                for (int i = 1; i < jsonArrayObject.length(); i++) {

                    JSONArray jsonArrayData = jsonArrayObject.getJSONArray(i);

                    for (int j = 0; j < jsonArrayData.length(); j++) {

                        JSONObject producJson=jsonArrayData.getJSONObject(j);



                        User user=new User(0,"Artsit","Name","profile_picture","thumbnail_picture","nicename");

                        Art art=new Art(producJson.getInt("id"),producJson.getString("name"),producJson.getString("thumbnail_picture"),producJson.getString("description"),producJson.getInt("price"),user);
                        //Toast.makeText(getContext(),user.getFirstName(),Toast.LENGTH_SHORT);
                        listArt.add(art);


                    }

                }
                swipeRefreshLayout.setRefreshing(false);

                RecyclerGridViewAdapter myAdapter = new RecyclerGridViewAdapter(getContext(),listArt);
                recyclerGrid.setLayoutManager(new GridLayoutManager(getContext(),2));
                recyclerGrid.setAdapter(myAdapter);
            }








        } catch (JSONException e) {
            swipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
            Toast.makeText(mContext,"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }

        /*swipeRefreshLayout.setRefreshing(true);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, Constants.URL_ARTBY_CATEGORY+category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean("success"))
                    {

                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        JSONArray jsonArray=jsonData.getJSONArray("object");
                        JSONArray jsonArrayObject= (JSONArray) jsonArray.get(0);


                        for (int i = 1; i < jsonArrayObject.length(); i++) {

                            JSONArray jsonArrayData = jsonArrayObject.getJSONArray(i);

                            for (int j = 0; j < jsonArrayData.length(); j++) {

                                JSONObject producJson=jsonArrayData.getJSONObject(j);

                                *//*JSONObject userJson=producJson.getJSONObject("user");
                                User user=new User(userJson.getInt("id"),userJson.getString("first_name"),userJson.getString("last_name"),userJson.getString("profile_picture"),userJson.getString("thumbnail_picture"),userJson.getString("nicename"));
                                *//*

                                User user=new User(0,"Artsit","Name","profile_picture","thumbnail_picture","nicename");

                                Art art=new Art(producJson.getInt("id"),producJson.getString("name"),producJson.getString("thumbnail_picture"),producJson.getString("description"),producJson.getInt("price"),user);
                                //Toast.makeText(getContext(),user.getFirstName(),Toast.LENGTH_SHORT);
                                listArt.add(art);


                            }

                        }
                        swipeRefreshLayout.setRefreshing(false);

                        RecyclerGridViewAdapter myAdapter = new RecyclerGridViewAdapter(getContext(),listArt);
                        recyclerGrid.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerGrid.setAdapter(myAdapter);
                    }








                } catch (JSONException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                    Toast.makeText(mContext,"Response Error : "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(mContext,"Network issue : "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){



            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(jsonString, cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Cache.Entry entry = this.getCacheEntry();
                    if(entry != null) {
                        Response<String> response = parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                        deliverResponse(response.result);
                        return;
                    }
                }
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);*/
    }



}
